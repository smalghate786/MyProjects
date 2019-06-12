package com.testyantra.controllers;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.testyantra.beans.Emp;
import com.testyantra.beans.EmpLogin;
import com.testyantra.dao.EmpDao;

/**
 * 
 * @author TYSS
 *
 */
@Controller
public class EmpController {
	@Autowired
	EmpDao dao;// will inject dao from xml file

	private static final Logger LOGGER = Logger.getLogger(EmpController.class);

	@RequestMapping("/empform")
	public String showform(Model m) {
		m.addAttribute("command", new Emp());
		return "empform";
	}// method Showform(-)

	/**
	 * 
	 * @param emp
	 * @return page success/fail
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@ModelAttribute("emp") Emp emp) {

		String hashtext = "";

		try {

			// Static getInstance method is called with hashing SHA-1
			MessageDigest msgDigst = MessageDigest.getInstance("SHA-1");

			// digest() method is called to calculate message digest
			// of an input digest() return array of byte
			byte[] messageDigest = msgDigst.digest(emp.getPassword().getBytes());

			// Convert byte array into signum representation
			BigInteger no = new BigInteger(1, messageDigest);

			// Convert message digest into hex value
			hashtext = no.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
		}

		// For specifying wrong message digest algorithms
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		// emp.setPassword(hashtext);
		emp.setPassword(emp.getPassword());
		dao.save(emp);
		return "redirect:/viewemp";// will redirect to viewemp request mapping
	}// save(-)

	/**
	 * 
	 * @param m
	 * @param req
	 * @return view employee form
	 */
	@RequestMapping("/viewemp")
	public String viewemp(Model m, HttpServletRequest req) {
		List<Emp> list = dao.getEmployees();
		m.addAttribute("list", list);
		return "viewemp";
	}// method view employee (-)

	/**
	 * 
	 * @param id
	 * @param model
	 * @return employee edit form
	 */
	@RequestMapping(value = "/editemp/{id}")
	public String edit(@PathVariable int id, Model model) {
		Emp emp = dao.getEmpById(id);
		model.addAttribute("command", emp);
		return "empeditform";
	}// method edit(-)

	/**
	 * 
	 * @param emp
	 * @return if success then redirect to view employee
	 */
	@RequestMapping(value = "/editsave", method = RequestMethod.POST)
	public String editsave(@ModelAttribute("emp") Emp emp) {
		dao.update(emp);
		return "redirect:/viewemp";
	}// method editsave(-)

	/**
	 * 
	 * @param id
	 * @return id success redirect to view employee
	 */
	@RequestMapping(value = "/deleteemp/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable int id) {
		dao.delete(id);
		return "redirect:/viewemp";
	}// delete method(-)

	/**
	 * 
	 * @param m =Model ref variable
	 * @return index page
	 */
	@RequestMapping(path = "/login", method = RequestMethod.GET)
	public String ShowloginForm(final Model logModel) {
		logModel.addAttribute("command", new Emp());
		return "index";
	}// method showloginformclosed(-)

	/**
	 * 
	 * @param         emp= Employee login obj
	 * @param         m= model ref variable
	 * @param request
	 * @return page result(String)
	 * @throws SQLException
	 */
	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public String Verified(@ModelAttribute("EmpLogin") final EmpLogin emp) throws SQLException {
		// HttpSession session = request.getSession(true);
		// session.setAttribute("name", emp.getName());
		int template;
		String page = "";
		String hashtext = "";
		try {
			// Static getInstance method is called with hashing MD5
			MessageDigest msgDigst = MessageDigest.getInstance("SHA-1");
			// digest() method is called to calculate message digest
			// of an input digest() return array of byte
			byte[] messageDigest = msgDigst.digest(emp.getPassword().getBytes());
			// Convert byte array into signum representation
			BigInteger no = new BigInteger(1, messageDigest);
			// Convert message digest into hex value
			hashtext = no.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
		}
		// For specifying wrong message digest algorithms
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		System.out.println("passwordddddddd" + emp.getPassword());
		template = dao.verification(emp.getName(), emp.getPassword());
		if (template == 1) {

			page = "success";
			// page = "invalidlogin";

		} else {
			page = "invalidlogin";
			// page = "success";
		}
		return page;

	}// method Verified closed

	@RequestMapping("/success")
	public String openSuccessPage() {

		return "success";
	}// logout method(-)

	/**
	 * 
	 * @param request
	 * @return String
	 */
	@GetMapping("/logout")
	public String logout() {
		// final HttpSession session = request.getSession(false);
		// session.removeAttribute("name");
		// session.invalidate();
		return "logout";
	}// logout method (-)
}// class end