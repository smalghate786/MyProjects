package com.tyss.taskmanagement.repository;

import org.springframework.data.repository.CrudRepository;

import com.tyss.taskmanagement.model.TaskManagementRegister;

public interface TaskRepository extends CrudRepository<TaskManagementRegister	, Integer> {

}
