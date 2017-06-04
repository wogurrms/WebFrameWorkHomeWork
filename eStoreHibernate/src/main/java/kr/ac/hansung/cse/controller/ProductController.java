package kr.ac.hansung.cse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.ac.hansung.cse.model.Product;
import kr.ac.hansung.cse.service.ProductService;

@Controller
public class ProductController {

	/* Controller -> Service ȣ�� */
	@Autowired
	private ProductService productService;
	
	@RequestMapping("/products")
	public String getProducts(Model model) {
		List<Product> products = productService.getProducts(); // ���� ȣ��
		model.addAttribute("products", products); // �𵨿� ����
		
		return "products"; // ��(Logical name)
	}
	
	@RequestMapping("/viewProduct/{id}") // spring:url �±� �̿��� ��ǰid�� ����
	public String viewProduct(@PathVariable int id, Model model) {
		Product product = productService.getProductById(id);
		model.addAttribute("product", product);
		return "viewProduct";
	}
}
