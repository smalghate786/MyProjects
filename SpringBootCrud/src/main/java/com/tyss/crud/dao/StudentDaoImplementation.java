package com.tyss.crud.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tyss.crud.bean.Student;

@Repository
public class StudentDaoImplementation implements StudentDao {

	@Autowired
	SessionFactory sf;
	@Autowired
	HibernateTemplate hibernateTemplate;


	public void setHibernateTemplate(final HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	/**
	 * Using Hibarnate Template delete.
	 */
	@Override
	@Transactional
	public void saveDb(Student stu) {
		hibernateTemplate.save(stu);
	}

	/**
	 * Using HQL query load all data.
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Student> getStudent() {
		Session ses = sf.getCurrentSession();
		String hql = "FROM Student ";
		Query query = ses.createQuery(hql);
		@SuppressWarnings("rawtypes")
		List results = query.list();
		return results;
	}

	/**
	 * Using Hibarnate Template load all data.
	 */
	@Override
	public List<Student> getAllStudent() {
		return hibernateTemplate.loadAll(Student.class);
	}

	/**
	 * Using HQL query update.
	 */
	@Override
	public void update(Student stu) {
		Session ses=null;
		try {
			ses = sf.openSession();
			Query query = ses.createQuery("update Student set name=?, age=?,address=? where id=" + stu.getId() + "");
			query.setParameter(0, stu.getName());
			query.setParameter(1, stu.getAge());
			query.setParameter(2, stu.getAddress());

			query.executeUpdate();
		} catch ( NullPointerException npe) {
			System.err.println(npe);
		}
	}

	/**
	 * Using Hibernate Template delete.
	 */
	@Override
	@Transactional
	public void updateStu(Student stud) {
		hibernateTemplate.update(stud);
	}

	/**
	 * Using HQL query delete.
	 */
	@Override
	public void deleteStudent(Student stu) {
		Session ses = sf.openSession();
		Query query = ses.createQuery("delete from Student where id=" + stu.getId() + "");
		query.executeUpdate();
	}

	/**
	 * Using Hibernate Template delete.
	 */
	@Override
	@Transactional
	public void deleteStud(int id) {
		Student e = new Student();
		e.setId(id);
		hibernateTemplate.delete(e);
	}

	/**
	 * Using HQL delete.
	 */
	@Override
	public Student getStuById(int id) {
		Session ses = sf.openSession();
		Student stu = null;
		stu = ses.get(Student.class, id);
		return stu;
	}

	/**
	 * Using Hibernate Template delete.
	 */
	@Override
	public Student getOneStudentById(int id) {
		return hibernateTemplate.get(Student.class, id);
	}

	/**
	 * Login Verfication check
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public boolean loginVerification(String stuName, String stuPassword) {
		boolean yes = true;
		boolean no = false;
		List<Long> count = (List<Long>) hibernateTemplate
				.findByCriteria(DetachedCriteria.forClass(Student.class).setProjection(Projections.rowCount()).add(
						Restrictions.and(Restrictions.eq("name", stuName), Restrictions.eq("password", stuPassword))));

		return count.get(0) != 0 ? yes : no;
	}

	/**
	 * Checking for name alreday exists
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean isNameExists(String stunName) {
		boolean yes = true;
		boolean no = false;
		List<Long> count = (List<Long>) hibernateTemplate.findByCriteria(DetachedCriteria.forClass(Student.class)
				.setProjection(Projections.rowCount()).add(Restrictions.eq("name", stunName)));

		return count.get(0) != 0 ? yes : no;

	}

}
