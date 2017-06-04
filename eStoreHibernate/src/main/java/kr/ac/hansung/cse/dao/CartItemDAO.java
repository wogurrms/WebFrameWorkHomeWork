package kr.ac.hansung.cse.dao;

import java.util.List;

import org.hibernate.query.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kr.ac.hansung.cse.model.Cart;
import kr.ac.hansung.cse.model.CartItem;


@Repository
@Transactional // 모든 메서드들이 트랜잭션화 된다, AOP (all or nothing)
public class CartItemDAO {

	@Autowired
	private SessionFactory sessionFactory; // hibernate

	public void addCartItem(CartItem cartItem) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(cartItem);
		session.flush();
	}

	public void removeCartItem(CartItem cartItem) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.delete(cartItem);
		session.flush();
	}

	public void removeAllCartItems(Cart cart) {
		// TODO Auto-generated method stub
		List<CartItem> cartItems = cart.getCartItems();
		for(CartItem cartItem : cartItems){
			removeCartItem(cartItem);
		}
	}

	public CartItem getCartItemByProductId(int cartId, int productId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from CartItem where cart.cartId = :cid and product.id = :pid ");
		
		query.setParameter("cid", cartId);
		query.setParameter("pid", productId);
		
		
		return (CartItem)query.uniqueResult();
	}
	
	
}
