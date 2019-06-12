package com.testyantra.springmvc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.testyantra.springmvc.controller.EmployeeController;
import com.testyantra.springmvc.entity.Employee;
//@Repository
public class EmployeeDAOImpl implements EmployeeDAO {
	final static Logger logger = Logger.getLogger(EmployeeDAOImpl.class);

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void createEmployee(Employee employee) {
		Session session = sessionFactory.openSession();
		//	Transaction tx = session.beginTransaction();
		session.save(employee);
		//	tx.commit();
		session.close();
	}

	@Override
	public Employee getEmployee(Employee employee) {
		Session session = sessionFactory.openSession();
		Criteria criteria = null;
		Employee emp = null;
		try {
			criteria = session.createCriteria(Employee.class);
			Criterion criterion = Restrictions.eq("username",employee.getUsername());
			criteria.add(criterion);

			criteria.setMaxResults(1);
			emp = (Employee) criteria.uniqueResult();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return emp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Employee> getViewData() {
		Session session = sessionFactory.openSession();
		List <Employee> users = session.createQuery("FROM Employee").list(); 
		return users;
	}
	@Override
	public void delete(Employee emp) {
//		Session session = sessionFactory.openSession();
//		Employee e=new Employee();
//		e.setId(id);
//		System.out.println("delete id : "+id);
//		session.delete(e);
//		System.out.println("out----->?>>>");
//		return id;
		Session ses=sessionFactory.openSession();
		Query query = ses.createQuery("delete from Employee where id="+emp.getId()+"");
		query.executeUpdate();

	}

	@Override
	public void  updateOne(Employee emp) {
		Session ses=sessionFactory.openSession();
		Query query = ses.createQuery("update Employee set username=?, name=?,email=?,age=? where id="+emp.getId()+"");
		query.setParameter(0,emp.getUsername());
		query.setParameter(1,emp.getName());
		query.setParameter(2,emp.getEmail());
		query.setParameter(3,emp.getAge());
        query.executeUpdate();
	}

	@Override
	public Employee getEmpById(int id) {
		Session ses=sessionFactory.openSession();
		Employee emp=null;
		emp = (Employee) ses.get(Employee.class, id);
		return emp;
	}


}