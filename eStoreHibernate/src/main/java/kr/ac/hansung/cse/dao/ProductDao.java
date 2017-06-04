package kr.ac.hansung.cse.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kr.ac.hansung.cse.model.Product;

@Repository
@Transactional // 모든 메서드들이 트랜잭션화 된다, AOP (all or nothing)
public class ProductDao {

	@Autowired
	private SessionFactory sessionFactory; // hibernate
	
	public Product getProductById(int id) {

		Session session = sessionFactory.getCurrentSession(); // 1. Session 생성 (Transactional이기 때문에 openSession 안함)
		Product product = session.get(Product.class, id); // 2. id에 대한 product를 db에서 얻어옴 (sql문이 없다!)

		// Product product = sessionFactory.getCurrentSession().get(Product.class, id);
		
		return product;
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" }) // 경고없앰
	public List<Product> getProducts(){ // 모든 레코드를 조회
		Session session = sessionFactory.getCurrentSession(); // 1. Session 생성
		Query query = session.createQuery("from Product"); // 2. query객체 생성해 HQL문 (Product는 테이블이 아니라 클래스이름!)
		List<Product> productList = query.list();  // 근데 hql. from Product 안쓰면 어케됨?
		
		return productList;
	}

	public void addProduct(Product product) { // public boolean addProduct(Product product) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(product); // 있으면 update 없으면 save
		session.flush();
	}

	public void deleteProduct(Product product) { // public boolean deleteProduct(int id) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(product);
		session.flush();
	}

	public void editProduct(Product product) { // public boolean editProduct(Product product) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(product);
		session.flush();
	}
}
