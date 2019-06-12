package com.testyantra.springmvc.dao;

import java.util.List;

import com.testyantra.springmvc.entity.Employee;

public interface EmployeeDAO {
	public void createEmployee(Employee employee);
	public Employee getEmployee(Employee employee);
	public List<Employee> getViewData();
	public void updateOne(Employee employee);
	public void delete(Employee employee);
	public Employee getEmpById(int id);
}
