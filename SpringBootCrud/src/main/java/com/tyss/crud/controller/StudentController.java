package com.tyss.crud.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tyss.crud.bean.Student;
import com.tyss.crud.dao.StudentDaoImplementation;

@Controller
public class StudentController {
	@Autowired
	StudentDaoImplementation daoImpl;
	

	@GetMapping(path = "/stuInfo")
	public String showLogin(Model m) {
		m.addAttribute("student", new Student());
		return "register";
	}

	@GetMapping(path = "/add")
	public String saveToDb(@ModelAttribute("Stu") Student stu, Model model, HttpServletRequest req) {
		HttpSession ses = req.getSession(false);
		if (ses.getAttribute("name") == null) {
			model.addAttribute("loginStudent", new Student());
			return "login";
		} else {
			daoImpl.saveDb(stu);
			return "success";
		}
	}

	@GetMapping(path = "/viewStudent")
	public String viewStudentDetails(Model model, HttpServletRequest req) {
		String page = "";
		HttpSession ses = req.getSession(false);
		if (ses.getAttribute("name") == null) {
			model.addAttribute("student", new Student());
			page = "login";
		} else {

			List<Student> list = daoImpl.getAllStudent();
			model.addAttribute("list", list);
			page = "viewstu";
		}
		return page;
	}

	@GetMapping(value = "/deleteemp")
	public String delete(@RequestParam int id, Student stu, Model m, HttpServletRequest req) {
		HttpSession ses = req.getSession(false);
		if (ses.getAttribute("name") == null) {
			m.addAttribute("student", new Student());
			return "login";
		} else {
			daoImpl.deleteStud(id);
			List<Student> list = daoImpl.getAllStudent();
			m.addAttribute("list", list);
			return "viewstu";
		}
	}

	@GetMapping(path = "/editemp")
	public String edit(@RequestParam int id, Model model, HttpServletRequest req) {
		HttpSession ses = req.getSession(false);
		if (ses.getAttribute("name") == null) {
			model.addAttribute("student", new Student());
			return "login";
		} else {
			Student stu = daoImpl.getOneStudentById(id);
			model.addAttribute("student", stu);
			return "editstu";
		}
	}

	@PostMapping(path = "/editsave")
	public String editsave(@ModelAttribute("student") Student student, Model m, HttpServletRequest req) {
		HttpSession ses = req.getSession(false);
		if (ses.getAttribute("name") == null) {
			m.addAttribute("student", new Student());
			return "login";
		} else {

			daoImpl.updateStu(student);
			List<Student> list = daoImpl.getAllStudent();
			m.addAttribute("list", list);
			return "viewstu";
		}
	}

	@GetMapping(path = "/logout")
	public String logout(HttpServletRequest req) {
		req.getSession(false).invalidate();
		return "logout";
	}

	@GetMapping(path = "/login")
	public String showLoginForm(Model model) {
		model.addAttribute("student", new Student());
		return "login";
	}

	@PostMapping(path = "/login")
	public String loginSuccess(Model m, Student stu, HttpServletRequest req) {
		boolean status=true;
		HttpSession session = req.getSession(true);
		session.setAttribute("name", stu.getName());
		m.addAttribute("student", stu);
		boolean loginStatus = daoImpl.loginVerification(stu.getName(), stu.getPassword());
		if (loginStatus == status) {
			return "success";
		}
		return "invalid";
	}
}
