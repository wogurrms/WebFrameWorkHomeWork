package kr.ac.hansung.cse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.hansung.cse.dao.CartDAO;
import kr.ac.hansung.cse.model.Cart;

@Service
public class CartService {
	@Autowired
	private CartDAO cartDao;
	
	public Cart getCartById(int cartId){
		return cartDao.getCartById(cartId);
	}
}
