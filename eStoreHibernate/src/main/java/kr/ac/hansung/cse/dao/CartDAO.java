package kr.ac.hansung.cse.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kr.ac.hansung.cse.model.Cart;
import kr.ac.hansung.cse.model.Product;

@Repository
@Transactional // ��� �޼������ Ʈ�����ȭ �ȴ�, AOP (all or nothing)
public class CartDAO {

	@Autowired
	private SessionFactory sessionFactory; // hibernate

	public Cart getCartById(int cartId) {

		Session session = sessionFactory.getCurrentSession(); // 1. Session ���� (Transactional�̱� ������ openSession ����)
		Cart cart = (Cart)session.get(Cart.class, cartId); // 2. id�� ���� product�� db���� ���� (sql���� ����!)

		// Product product = sessionFactory.getCurrentSession().get(Product.class, id);
		
		return cart;
	}
	
	
}
