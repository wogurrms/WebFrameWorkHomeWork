package kr.ac.hansung.cse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.hansung.cse.dao.ProductDao;
import kr.ac.hansung.cse.model.Product;

@Service
public class ProductService {

	/* Controller -> Service호출 -> DAO이용해 DB접근 */
	@Autowired
	private ProductDao productDao;
	
	public List<Product> getProducts(){
		return productDao.getProducts();
	}
	

	public void addProduct(Product product) { // public boolean addProduct(Product product) {
		productDao.addProduct(product); // return productDao.addProduct(product);
	}

	public void deleteProductById(Product product) { // public boolean deleteProductById(int id) {
		productDao.deleteProduct(product); // return productDao.deleteProduct(id);
	}

	public Product getProductById(int id) {
		return productDao.getProductById(id);
	}

	public void editProduct(Product product) { // public boolean editProduct(Product product) {
		productDao.editProduct(product); // return productDao.editProduct(product);
	}
}
