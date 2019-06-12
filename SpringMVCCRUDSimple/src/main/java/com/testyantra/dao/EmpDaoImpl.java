package com.testyantra.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.testyantra.beans.Emp;

/**
 * This class is used for Data persistance logic
 * 
 * @author TYSS
 *
 */
@Repository
public class EmpDaoImpl implements EmpDao {
	private static final String INSERT_EMP_DETAILS = "insert into emp(name,salary,designation,password) values(?,?,?,?)";
	private static final String INSERT_USERS_DETAILS = "insert into USER_ROLES(name,role) values(?,?)";
	@Autowired
	JdbcTemplate template;

	public void setTemplate(final JdbcTemplate template) {
		this.template = template;
	}

	/**
	 * Insert data into db
	 */
	@Override
	public int save(Emp p) {
		int sql1 = template.update(INSERT_EMP_DETAILS,
				new Object[] { p.getName(), p.getSalary(), p.getDesignation(), "{noop}" + p.getPassword() });
		int sql2 = template.update(INSERT_USERS_DETAILS, new Object[] { p.getName(), p.getRole() });
		// return template.update(sql1+"")+template.update(sql2+"");
		return sql1;
	}// method save(-)

	
	/**
	 * Update data into db.
	 */
	@Override
	public int update(Emp p) {
		System.out.println("in update");
		String sql = "update emp set name='" + p.getName() + "', salary=" + p.getSalary() + ",designation='"
				+ p.getDesignation() + "'  where id=" + p.getId() + "";
		return template.update(sql);
	}//method update(-)

	/*private static final String Update_EMP_DETAILS = "update emp set name=?,salary=?,designation=?  where id=?";
	private static final String Update_USERS_DETAILS = "update user_roles set name=? where name =?";
	@Override
	public int update(Emp p) {
		int sql1 = template.update(Update_EMP_DETAILS,
				new Object[] { p.getName(), p.getSalary(), p.getDesignation(),+p.getId() });
		int sql2 = template.update(Update_USERS_DETAILS, new Object[] { p.getName() ,p.getName()});
		return sql1;
	}*/
	/**
	 * Delete data into db.
	 */
	@Override
	public int delete(int id) {
		String sql = "delete from emp where id=" + id + "";
		System.out.println(sql + "  Delete querty");
		return template.update(sql);
	}// method delete(-)

	/**
	 * get employee bu id from database.
	 */
	@Override
	public Emp getEmpById(int id) {
		String sql = "select * from emp where id= ?";
		return template.queryForObject(sql, new Object[] { id }, new BeanPropertyRowMapper<Emp>(Emp.class));
	}// method getEmpById(-)

	/**
	 * get all employee data from database.
	 */
	@Override
	public List<Emp> getEmployees() {
		return template.query("select * from emp", new RowMapper<Emp>() {
			public Emp mapRow(ResultSet rs, int row) throws SQLException {
				Emp e = new Emp();
				e.setId(rs.getInt(1));
				e.setName(rs.getString(2));
				e.setSalary(rs.getString(3) + "");
				e.setDesignation(rs.getString(4));
				e.setPassword(rs.getString(5));
				return e;
			}
		});
	}// method getEmployees(-)

	/**
	 * login verify from db.
	 */
	@Override
	public int verification(String name, String password) {
		String sql = null;
		sql = "select count(*) from emp where name=? and password=?";

		System.out.println(sql + " ***");
		System.out.println(name + " ***");
		System.out.println(password + " ***");

		int temp = template.queryForObject(sql, new String[] { name, password }, int.class);
		System.out.println(" before return***");
		System.out.println(temp + "------------>>>>>>>>>>>value of template");
		return temp;
	}// method verification(-)

	static String sql = null;
	static String myConnectionURL = "jdbc:mysql://localhost:3306/db";
	ResultSet rs = null;

	public static boolean verificationJdbc(String name, String password) throws SQLException {
		sql = "select name,password from emp where name=? and password=?";
		try (Connection con = DriverManager.getConnection(myConnectionURL, "root", "root");
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, name);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();

			boolean flag = false;
			if (rs.next()) {
				flag = true;
			}

			return flag;
		}
	}

	public static int deleteJdbc(int id) throws SQLException {
		String sql = "delete from emp where id=" + id + "";
		try (Connection con = DriverManager.getConnection(myConnectionURL, "root", "root");
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			return pstmt.executeUpdate();
		}

	}// method deleteJdbc(-)

	public static int updateJdbc(Emp p) throws SQLException {
		String sql = "update emp set name='" + p.getName() + "', salary=" + p.getSalary() + ",designation='"
				+ p.getDesignation() + "'  where id=" + p.getId() + "";
		try (Connection con = DriverManager.getConnection(myConnectionURL, "root", "root");
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			return pstmt.executeUpdate();
		}
	}

	public static int saveJdbc(Emp e) throws Exception {
		String sql_tableEmp = "insert into emp(name,salary,designation,password) values(?,?,?,?)";
		String sql_tableUser = "insert into USER_ROLES(name,role) values(?,?)";

		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "root");

		PreparedStatement pstmt_emp = con.prepareStatement(sql_tableEmp);
		pstmt_emp.setString(1, e.getName());
		pstmt_emp.setString(2, e.getSalary());
		pstmt_emp.setString(3, e.getDesignation());
		pstmt_emp.setString(4, e.getPassword());

		PreparedStatement pstmt_user = con.prepareStatement(sql_tableUser);
		pstmt_user.setString(1, e.getName());
		pstmt_user.setString(2, e.getRole());
		pstmt_user.executeUpdate();
		return pstmt_user.executeUpdate();

	}
	
	/*public static String getEmployeesJdbc() throws SQLException {
		String sql = "select * from emp where id= ?";
		Emp e =new Emp();
		try (Connection con = DriverManager.getConnection(myConnectionURL, "root", "root");
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			ResultSet rs = pstmt.executeQuery(sql);
			
			while (rs.next()) {
				e.setId(rs.getInt(1));
				e.setName(rs.getString(2));
				e.setSalary(rs.getString(3) + "");
				e.setDesignation(rs.getString(4));
				e.setPassword(rs.getString(5));
			}
		}
		return e+"";
	}*/
}
