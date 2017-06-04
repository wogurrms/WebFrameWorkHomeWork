package kr.ac.hansung.cse.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import kr.ac.hansung.cse.model.Product;

public class ProductMapper implements RowMapper<Product> {

	@Override
	public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
		Product product = new Product();
		product.setId(rs.getInt("id"));
		product.setName(rs.getString("name"));
		product.setCategory(rs.getString("category"));
		product.setPrice(rs.getInt("price"));
		product.setManufacturer(rs.getString("manufacturer"));
		product.setUnitInStock(rs.getInt("unitInStock"));
		product.setDescription(rs.getString("description"));
		// DAO에서 getProducts() 메소드 DB접근시 함께 리턴. 
		// products.jsp와 productInventory.jsp에 띄우도록 하자
		product.setImageFileName(rs.getString("imageFileName")); 
		return product;
	}

}
