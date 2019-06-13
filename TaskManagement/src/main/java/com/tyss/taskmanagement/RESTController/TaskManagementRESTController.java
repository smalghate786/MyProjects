package com.tyss.taskmanagement.RESTController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tyss.taskmanagement.RESTDao.TaskManagementDaoImplREST;
import com.tyss.taskmanagement.model.CreateTask;
import com.tyss.taskmanagement.model.TaskManagementRegister;

@RestController
public class TaskManagementRESTController {

	@Autowired
	TaskManagementDaoImplREST dao;

	@PostMapping(path = "/saveRegDetails")
	public TaskManagementRegister createRegister(@RequestBody TaskManagementRegister register) {

		return dao.saveRegisterDetails(register);
	}

	static final int loginStatusSuccess = 1;
	static final int loginStatusFail = 0;

	@PostMapping("/verifyLogin")
	public int verifyLogindetails(@RequestBody TaskManagementRegister login) {
		int count = dao.getCredentialsByEmail(login.getUser_Email(), login.getUser_Password());
		if (count == loginStatusSuccess) {
			return loginStatusSuccess;
		} else {
			return loginStatusFail;
		}
	}
	@GetMapping("/getall")
	 public List<TaskManagementRegister> getAllData() {
		return dao.getAlldata();
		 
	 }

}
