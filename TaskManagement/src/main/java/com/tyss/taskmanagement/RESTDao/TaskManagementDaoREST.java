package com.tyss.taskmanagement.RESTDao;

import java.util.List;
import java.util.Optional;

import com.tyss.taskmanagement.model.CreateTask;
import com.tyss.taskmanagement.model.TaskManagementRegister;

public interface TaskManagementDaoREST {
	
	public TaskManagementRegister saveRegisterDetails(TaskManagementRegister task);
	public int getCredentialsByEmail(String email,String pass);
	public void saveCreateTask(CreateTask task);
	public List<CreateTask> getTaskByEmail(String email);
	public List<TaskManagementRegister> getAlldata();
	List<CreateTask> assignedByMe(String email);
	Optional<CreateTask> getoneById(Integer id);
	List<CreateTask> assignedToMeToDo(String email);
	List<CreateTask> assignedToMeInProgress(String email);
	List<CreateTask> assignedToMeCompleted(String email);
	List<CreateTask> assignedToMeBlocked(String email);

}
