package com.testyantra.dao;

import java.util.List;

import com.testyantra.beans.Emp;

public interface EmpDao {
public int  save(Emp p);
public int update(Emp p);
public int delete(int id);
public Emp getEmpById(int id);
public List<Emp> getEmployees();
public int verification(String name, String password);
}
