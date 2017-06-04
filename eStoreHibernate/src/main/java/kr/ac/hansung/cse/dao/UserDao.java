package kr.ac.hansung.cse.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kr.ac.hansung.cse.model.User;

@Repository
@Transactional
public class UserDao {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void addUser(User user) {
		Session session = sessionFactory.getCurrentSession();
		
		// password encoding
		String encodedPassword = passwordEncoder.encode(user.getPassword()); // plain text�� ���� hashing�� ����
		user.setPassword(encodedPassword);
		
		session.saveOrUpdate(user);
		session.flush();
	}
	
	public User getUserById(int userId) {
		Session session = sessionFactory.getCurrentSession();
		return (User) session.get(User.class, userId);
	}
	
	public List<User> getAllUsers(){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from User");
		List<User> userList = query.list();
		
		return userList;
	}
	
	public User getUserByUsername(String username) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from User where username = ?");
		query.setString(0, username);
		
		return (User) query.uniqueResult();
		
	}
}
