package com.tyss.crud.dao;

import com.tyss.crud.bean.Student;

import java.util.List;

public interface StudentDao {
	public void saveDb(Student stu);

	public List<Student> getStudent();

	void update(Student emp);

	void deleteStudent(Student stu);

	Student getStuById(int id);

	boolean loginVerification(String name, String password);

	void deleteStud(int id);// using template

	Student getOneStudentById(int id);// using template

	List<Student> getAllStudent();// using template

	void updateStu(Student stud);// using template

	boolean isNameExists(String name);// using template
}
