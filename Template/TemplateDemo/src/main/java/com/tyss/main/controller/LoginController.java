package com.tyss.main.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tyss.main.command.UserCommand;
import com.tyss.main.service.LoginService;


@Controller
public class LoginController {
	@Autowired
	private LoginService service;
	/**
	 * This method is used for view the login form
	 * @param map
	 * @param empCmd
	 * @return
	 */
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String viewLoginForm(Map<String,Object> map,@ModelAttribute("userCmd")UserCommand userCmd){
		//keept empty command object in model/request scope
		map.put("userCmd",userCmd);
		//return lvn
		return "login";
	}//viewForm(-)
	/**
	 * This method is used for process the login form
	 * @param empCmd
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String processLoginForm(@ModelAttribute("userCmd")UserCommand userCmd,Map<String,Object> map) throws Exception {
		String msg=null;
		String fileName=null;
		//set file name
		fileName="Account_Active_Email_Template.txt";
		//use serviceto invoke service method
	//	msg=service.sendMail(userCmd);
		msg=service.getEmailBodyContent(userCmd, fileName);
		//keept msg to request scope
		map.put("msg",msg);
		//return lvn
		return "login";
	}
}
