package com.testyantra.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.testyantra.beans.Emp;
/**
 * This class is used for Data persistance logic 
 * @author TYSS
 *
 */
@Repository
public class EmpDaoImpl implements EmpDao{
	private static final String INSERT_EMP_DETAILS="insert into emp(name,salary,designation,password) values(?,?,?,?)";
	private static final String INSERT_USERS_DETAILS="insert into USER_ROLES(name,role) values(?,?)";
	private static final String INSERT_ROLES_DETAILS="";
	@Autowired
	JdbcTemplate template;

	public void setTemplate(final JdbcTemplate template) {
		this.template = template;
	}
	@Override
	public int save(Emp p) {
		int sql1=template.update(INSERT_EMP_DETAILS,new Object[] {p.getName(),p.getSalary(),p.getDesignation(),"{noop}"+p.getPassword()});
		int  sql2=template.update(INSERT_USERS_DETAILS,new Object[] {p.getName(),p.getRole()});
      // return template.update(sql1+"")+template.update(sql2+"");
		return sql1;
	}

	@Override
	public int update(Emp p) {
		System.out.println("in update");
		String sql = "update emp set name='" + p.getName() + "', salary=" + p.getSalary() + ",designation='"
				+ p.getDesignation() + "'  where id=" + p.getId() + "";
		return template.update(sql);
	}

	@Override
	public int delete(int id) {
		String sql = "delete from emp where id=" + id + "";
		return template.update(sql);
	}

	@Override
	public Emp getEmpById(int id) {
		String sql = "select * from emp where id= ?";
		return template.queryForObject(sql, new Object[] { id }, new BeanPropertyRowMapper<Emp>(Emp.class));
	}

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
	}

	@Override
	public int verification(String name, String password) {
			   String sql=null;
			    sql ="select count(*) from emp where name=? and password=?";
			    int temp =template.queryForObject(sql, new String[] {name,password}, int.class);
			    System.out.println(temp+"------------>>>>>>>>>>>value of template");
			    return temp;
		  }
	}


