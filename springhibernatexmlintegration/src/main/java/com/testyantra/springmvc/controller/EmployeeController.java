package com.testyantra.springmvc.controller;

import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.testyantra.springmvc.dao.EmployeeDAOImpl;
import com.testyantra.springmvc.entity.Employee;

@Controller
public class EmployeeController {
	//final static Logger logger = Logger.getLogger(EmployeeController.class);
	@Autowired
	EmployeeDAOImpl employeeDao;

	@RequestMapping(value = "register", method = RequestMethod.GET)
	public ModelAndView viewRegister(@ModelAttribute Employee employee) {
		return new ModelAndView("register");
	}

	@RequestMapping(value = "register", method = RequestMethod.POST)
	public ModelAndView createUser(@ModelAttribute Employee employee) {
		employeeDao.createEmployee(employee);
		return new ModelAndView("login");
	}

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public ModelAndView viewLogin(@ModelAttribute Employee employee) {
		System.out.println("inside login");
		return new ModelAndView("login");
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public ModelAndView processLogin(@ModelAttribute Employee employee) {
		Employee emp = employeeDao.getEmployee(employee);
		List<Employee> list = employeeDao.getViewData();
		ModelAndView model = null;
		if (emp == null) {
			model = new ModelAndView("login");
			model.addObject("result", "Invalid Username or Password");
		} else if(emp.getPassword().equals(employee.getPassword())) {
			model = new ModelAndView("home");
			model.addObject("list", list);
			model.addObject("emp", emp.getUsername());

		}else {
			model = new ModelAndView("login");
			System.out.println("else");
			model.addObject("result", "Invalid Username or Password");
		}
		return model;
	}
	@RequestMapping(value = "/deleteemp/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable int id,Employee emp,Model m) {
		employeeDao.delete(emp);
		List<Employee> list = employeeDao.getViewData();
		m.addAttribute("list", list);
		return "home";
	}
	@RequestMapping(value = "/editemp/{id}")
	public String edit(@PathVariable int id, Model m) {
		Employee emp = employeeDao.getEmpById(id);
		m.addAttribute("emp", emp);
		return "empeditform";
	}
//	@RequestMapping(value = "/editsave", method = RequestMethod.POST)
//	public String editsave(@ModelAttribute("emp")  int id,Model model,Employee emp) {
//		try
//		{
//			
//			employeeDao.updateOne(emp);
//			List<Employee> list = employeeDao.getViewData();
//			model.addAttribute("list", list);
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//		return "home";
//	}
	@RequestMapping(value = "/editsave", method = RequestMethod.POST)
	public String editsave(@ModelAttribute("emp") Employee emp,Model m) {
		employeeDao.updateOne(emp);
		List<Employee> list = employeeDao.getViewData();
		m.addAttribute("list", list);
		employeeDao.updateOne(emp);
		return "home";
	}
}