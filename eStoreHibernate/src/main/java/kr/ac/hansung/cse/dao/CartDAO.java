package kr.ac.hansung.cse.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kr.ac.hansung.cse.model.Cart;
import kr.ac.hansung.cse.model.Product;

@Repository
@Transactional // 모든 메서드들이 트랜잭션화 된다, AOP (all or nothing)
public class CartDAO {

	@Autowired
	private SessionFactory sessionFactory; // hibernate

	public Cart getCartById(int cartId) {

		Session session = sessionFactory.getCurrentSession(); // 1. Session 생성 (Transactional이기 때문에 openSession 안함)
		Cart cart = (Cart)session.get(Cart.class, cartId); // 2. id에 대한 product를 db에서 얻어옴 (sql문이 없다!)

		// Product product = sessionFactory.getCurrentSession().get(Product.class, id);
		
		return cart;
	}
	
	
}
