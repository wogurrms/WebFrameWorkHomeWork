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
@Transactional // ��� �޼������ Ʈ�����ȭ �ȴ�, AOP (all or nothing)
public class ProductDao {

	@Autowired
	private SessionFactory sessionFactory; // hibernate
	
	public Product getProductById(int id) {

		Session session = sessionFactory.getCurrentSession(); // 1. Session ���� (Transactional�̱� ������ openSession ����)
		Product product = session.get(Product.class, id); // 2. id�� ���� product�� db���� ���� (sql���� ����!)

		// Product product = sessionFactory.getCurrentSession().get(Product.class, id);
		
		return product;
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" }) // ������
	public List<Product> getProducts(){ // ��� ���ڵ带 ��ȸ
		Session session = sessionFactory.getCurrentSession(); // 1. Session ����
		Query query = session.createQuery("from Product"); // 2. query��ü ������ HQL�� (Product�� ���̺��� �ƴ϶� Ŭ�����̸�!)
		List<Product> productList = query.list();  // �ٵ� hql. from Product �Ⱦ��� ���ɵ�?
		
		return productList;
	}

	public void addProduct(Product product) { // public boolean addProduct(Product product) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(product); // ������ update ������ save
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
