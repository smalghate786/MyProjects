package com.tyss.taskmanagement.controller;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tyss.taskmanagement.dao.TaskManagementDao;
import com.tyss.taskmanagement.model.CreateTask;
import com.tyss.taskmanagement.model.TaskManagementRegister;

@Controller
//@RequestMapping("/task")
@PropertySource("classpath:application.properties")
public class TaskManagementRegisterController {
	@Autowired
	private TaskManagementDao dao;

	@Value("${from}")
	private String from;
	@Value("${pass}")
	private String pass;

	@GetMapping(path = "/register")
	public String goToRegister(Map<String, Object> map) {
		map.put("task", new TaskManagementRegister());

		return "TaskRegister";
	}

	@PostMapping(path = "/save")
	public String loginToTaskRegister(@ModelAttribute("task") TaskManagementRegister task, Map<String, Object> map) {
		String page = "";
		if (task.getUser_Password().equals(task.getUser_Confirm_Password())) {
			dao.saveTask(task);
			page = "Login";
		} else {
			map.put("message", "Please make sure that password and confirm password are same");
			page = "TaskRegister";
		}
		return page;
	}

	@GetMapping(path = "/login")
	public String goToLogin(Map<String, Object> map) {
		map.put("task", new TaskManagementRegister());
		return "Login";
	}

	@PostMapping(path = "/check")
	public String checkLogin(@ModelAttribute("task") TaskManagementRegister task, HttpServletRequest req,
			Map<String, Object> map) {
		String page = "";
		HttpSession ses = req.getSession(true);
		int count = dao.getCredentialsByEmail(task.getUser_Email(), task.getUser_Password());
		if (count == 1) {
			ses.setAttribute("email", task.getUser_Email());
			page = "Success";
		} else {
			map.put("message", "Your Credentials are incorrect");
			page = "Login";
		}
		return page;
	}

	@GetMapping(path = "/createtask")
	public String goTocreateTask(Map<String, Object> map) {
		map.put("create", new CreateTask());
		return "CreateTask";
	}

	@PostMapping(path = "/create")
	public String save(@ModelAttribute("create") CreateTask task, Map<String, Object> map, HttpServletRequest req) {
		HttpSession ses = req.getSession(false);
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.checkserveridentity", "true");
		props.put("mail.smtp.port", "465");
		// get Session
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, pass);
			}
		});
		// compose message
		try {
			MimeMessage message = new MimeMessage(session);
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(task.getEmail()));
			message.setSubject(task.getCategory());
			message.setText(task.getDescription());
			// send message
			Transport.send(message);
			System.err.println("email sent successfully");

		} catch (MessagingException e) {

			System.err.println("problem");
		}
		String mail = (String) ses.getAttribute("email");
		task.setAssignedBy(mail);
		dao.saveCreateTask(task);
		map.put("created", "Task Created and Assigned successfully");
		return "Success";
	}

	/*
	 * Assigned to me
	 */
	@GetMapping(path = "/assignedToMe")
	public String assignedTasks(HttpServletRequest req, Map<String, Object> map) {
		HttpSession ses = req.getSession(false);
		String mail = (String) ses.getAttribute("email");

		List<CreateTask> list = dao.getTaskByEmail(mail);
		map.put("assigned", list);
		return "AssignedTask";
	}

	/*
	 * Assigned by me
	/* @RequestMapping(value="/assignByMe",method=RequestMethod.GET) */
	@GetMapping(path = "/assignByMe")
	public String assigTasks(HttpServletRequest req, Map<String, Object> map) {
		HttpSession ses = req.getSession(false);
		String mail = (String) ses.getAttribute("email");

		List<CreateTask> list = dao.assignedByMe(mail);
		map.put("assigned", list);
		return "AssignedTask";
	}

	@GetMapping(path = "/open")
	public String assigTask(HttpServletRequest req, Map<String, Object> map, @RequestParam("id") Integer id) {

		map.put("progress", dao.getoneById(id));
		return "ChangeStatus";
	}

	@PostMapping(path = "/accept")
	public String inProgress(@ModelAttribute("progress") CreateTask task, Object map, @RequestParam("id") Integer id) {
		task.setStatus(2);
		dao.saveCreateTask(task);
		return "Success";
	}

	@GetMapping(path = "/todo")
	public String assignedToMeToDo(HttpServletRequest req, Map<String, Object> map) {
		HttpSession ses = req.getSession(false);
		String mail = (String) ses.getAttribute("email");

		List<CreateTask> list = dao.assignedToMeToDo(mail);
		map.put("todo", list);
		return "ToDo";
	}

	@GetMapping(path = "/inprogress")
	public String assignedToMeInProgress(HttpServletRequest req, Map<String, Object> map) {
		HttpSession ses = req.getSession(false);
		String mail = (String) ses.getAttribute("email");

		List<CreateTask> list = dao.assignedToMeInProgress(mail);
		map.put("progress", list);
		return "InProgress";
	}

	@GetMapping(path = "/completed")
	public String assignedToMeCompleted(HttpServletRequest req, Map<String, Object> map) {
		HttpSession ses = req.getSession(false);
		String mail = (String) ses.getAttribute("email");

		List<CreateTask> list = dao.assignedToMeCompleted(mail);
		map.put("complete", list);
		return "Completed";
	}
	
	@GetMapping(path = "/acceptCompleted")
	public String assigTask1(HttpServletRequest req, Map<String, Object> map, @RequestParam("id") Integer id) {
		map.put("cmp", dao.getoneById(id));
		return "completedStatus";
	}
	@PostMapping(path = "/acceptCompleted")
	public String inProgress1(@ModelAttribute("cmp") CreateTask task, Object map, @RequestParam("id") Integer id) {
		task.setStatus(3);
		dao.saveCreateTask(task);
		///return "Success";
		return "1";
	}
	

	@GetMapping(path = "/blocked")
	public String assignedToMeInBlocked(HttpServletRequest req, Map<String, Object> map) {
		HttpSession ses = req.getSession(false);
		String mail = (String) ses.getAttribute("email");

		List<CreateTask> list = dao.assignedToMeBlocked(mail);
		map.put("blocked", list);
		return "Blocked";
	}
	
	@GetMapping(path = "/logout")
	public String logoutSesion(HttpServletRequest req,Map<String, Object> map) {
		HttpSession ses = req.getSession();
		map.put("task", new TaskManagementRegister());
	    ses.invalidate();
		return "Login";
	}

}
