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

//	HttpSession session;
	private static final Logger LOGGER = Logger.getLogger(EmpController.class);

	/*
	 * It displays a form to input data, here "command" is a reserved request
	 * attribute which is used to display object data into form
	 */
	@RequestMapping("/empform")
	public String showform(Model m) {
		m.addAttribute("command", new Emp());
		return "empform";
	}

	/*
	 * It saves object into database. The @ModelAttribute puts request data into
	 * model object. You need to mention RequestMethod.POST method because default
	 * request is GET
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
	 * @return
	 */
	@RequestMapping("/viewemp")
	public String viewemp(Model m, HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		// if (session.getAttribute("name") != null) {
		List<Emp> list = dao.getEmployees();
		m.addAttribute("list", list);
		return "viewemp";
		/*
		 * } else { m.addAttribute("command", new Emp()); return "index"; }
		 */

	}

	/*
	 * It displays object data into form for the given id. The @PathVariable puts
	 * URL data into variable.
	 */
	@RequestMapping(value = "/editemp/{id}")
	public String edit(@PathVariable int id, Model m) {
		Emp emp = dao.getEmpById(id);
		m.addAttribute("command", emp);
		return "empeditform";
	}

	/**
	 * 
	 * @param emp
	 * @return
	 */
	@RequestMapping(value = "/editsave", method = RequestMethod.POST)
	public String editsave(@ModelAttribute("emp") Emp emp) {
		dao.update(emp);
		return "redirect:/viewemp";
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteemp/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable int id) {
		dao.delete(id);
		return "redirect:/viewemp";

	}

	/**
	 * 
	 * @param m =Model ref variable
	 * @return index page
	 */
	@RequestMapping(path = "/login", method = RequestMethod.GET)
	public String ShowloginForm(final Model logModel) {
		logModel.addAttribute("command", new Emp());
		return "index";
	}// method show login form closed

	/**
	 * 
	 * @param         emp= Employee login obj
	 * @param         m= model ref variable
	 * @param request
	 * @return page result(String)
	 * @throws SQLException
	 */
	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public String Verified(@ModelAttribute("EmpLogin") final EmpLogin emp, final HttpServletRequest request)
	
			throws SQLException {
		System.out.println("inside verificationnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn");
		HttpSession session = request.getSession(true);
		session.setAttribute("name", emp.getName());
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
}
	/**
	 * 
	 * @param request
	 * @return String
	 */
	@GetMapping("/logout")
	public String logout(final HttpServletRequest request) {
		final HttpSession session = request.getSession(false);
		// session.removeAttribute("name");
		session.invalidate();
		return "logout";
	}// logout method closed
}// class