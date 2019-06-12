package com.testyantra.test;

import java.sql.SQLException;

import org.junit.Test;

import com.testyantra.beans.Emp;
import com.testyantra.controllers.EmpController;
import com.testyantra.dao.EmpDaoImpl;

import junit.framework.TestCase;

public class JunitTest {

	private EmpController mockMvc = new EmpController();

	EmpDaoImpl daoImpl = new EmpDaoImpl();

	static final String successStatusTrue = "success";
	static final String successStatusFalse = "logout";
	static final boolean verificationStatusSuccess = true;
	static final boolean verificationStatusfail = false;
	/*
	 * @Test public void testForSuccessPage() throws Exception {
	 * 
	 * TestCase.assertEquals(successStatusTrue, mockMvc.openSuccessPage());
	 * 
	 * }//methodtestForSuccessPage(-)
	 */

	@Test
	public void testLogin()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

		TestCase.assertEquals(verificationStatusSuccess, EmpDaoImpl.verificationJdbc("normal", "1234"));
		TestCase.assertEquals(verificationStatusfail, EmpDaoImpl.verificationJdbc("riya", "llll"));
	}// methodtestLogin(-)

	Emp emp = new Emp();

	/*
	 * @Test public void testDelete() throws SQLException {
	 * 
	 * TestCase.assertEquals(1,EmpDaoImpl.deleteJdbc(55));
	 * 
	 * }
	 */

	@Test
	public void testUpdate() throws SQLException {
		emp.setName("lokeshhhhh");
		emp.setPassword("1234");
		emp.setRole("admin");
		emp.setSalary("100");
		emp.setDesignation("teacher");
		emp.setId(50);
		TestCase.assertEquals(1, EmpDaoImpl.updateJdbc(emp));

	}
	
	/*
	 * @Test public void testInsert() throws Exception { emp.setName("KANCHAA");
	 * emp.setPassword("1234"); emp.setRole("admin"); emp.setSalary("76876");
	 * emp.setDesignation("ROWDY");
	 * TestCase.assertEquals(1,EmpDaoImpl.saveJdbc(emp)); }
	 */

	
}