package kr.ac.hansung.cse.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import kr.ac.hansung.cse.model.Product;
import kr.ac.hansung.cse.service.ProductService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	
	/* DAO���� */
	@Autowired
	private ProductService productService;
	
	@RequestMapping // /admin�� ���� adminPage()�޼ҵ� ȣ��
	public String adminPage() {
		return "admin";
	}
	
	@RequestMapping("/productInventory") // /admin/productInventory
	public String getProducts(Model model) {
		List<Product> products = productService.getProducts();
		model.addAttribute("products", products); // view���� �̸����� model�� ���ٰ����ϰ�
		
		/* Image�� �о� productInventory.jsp�� ���� */
		
		
		return "productInventory";
	}
	
	//@RequestMapping(value="/productInventory/addProduct", method=RequestMethod.GET)
	@RequestMapping("/productInventory/addProduct")
	public String addProduct(Model model) {
		Product product = new Product();
		product.setDescription("New Arrival!");
		model.addAttribute("product", product);
		return "addProduct";
	}
	
	/* addProduct.jsp ���� submit��ư ������ ����Ǵ� �޼��� */
	@RequestMapping(value="/productInventory/addProduct", method=RequestMethod.POST)
	/* ����ڰ� �Է��� Form�����Ͱ� �������� ���� DataBinding�� �̷���� Product ���� ��ü�� ���εȴ�. 
	 * public String addProductPost(Product product) { 
	 * �̶�, @Valid �ֳ����̼����� Product ���� Constraints�� ���� ������ ������ �Ǹ�
	 * ���� ����� BindingResult�� ���Եȴ�. 
	 * BindingResult��ü�� Product �𵨰�ü�� �Բ� ��� View�� ���޵�. */
	public String addProductPost(@Valid Product product, BindingResult result, HttpServletRequest request) {
		
		/* Data-Validation - BindingResult�� ��� */
		if(result.hasErrors()) {
			System.out.println("Form data has errors");
			List<ObjectError> errors = result.getAllErrors();
			for(ObjectError error:errors) {
				error.getDefaultMessage();
			}
			return "addProduct";
		}
		/* 'imageFileName'�� DB�� �����ϰ� 'image'��  /resources/images/�� �����Ѵ�.
		 * �ٸ�, ������ / ���͸��� �������� ���� �� �ִ�. ��Ʈ ���͸��� �˱� ���� ���ڿ� HttpServletRequest�� �޴´�.*/
		MultipartFile productImage = product.getProductImage();
		String rootDirectory = request.getSession().getServletContext().getRealPath("/"); // ���� ��Ʈ���͸�
		
		Path savePath = Paths.get(rootDirectory + "\\resources\\images\\" + productImage.getOriginalFilename()); // Image���� ��� ����
		if(productImage != null && !productImage.isEmpty()) {
			try {
				productImage.transferTo(new File(savePath.toString())); // ��ο� Image�� ����
			} catch(Exception e) { e.printStackTrace(); }
		}
		
		product.setImageFileName(productImage.getOriginalFilename()); // DB�� ImageFileName����
		
		productService.addProduct(product); // hibernate
		// if(!productService.addProduct(product)) { // ���н�
			// System.out.println("Adding Product cannot be done");
		// }
		/* return /admin/productInventory; <- ���⼭�� 'products'�� �ޱ� ������ �ȵȴ�.
		 * ���� getProducts �޼��尡 ȣ���� �Ǿ� DB���� �ٽ� �о�;��ϱ⶧����
		 * getProducts �޼���� �����̷�Ʈ�� �Ѵ�. */
		return "redirect:/admin/productInventory"; // �� getProducts���� �߰����Ǿ� �����ֵ��� �����̷�Ʈ
	}
	
	@RequestMapping("/productInventory/deleteProduct/{id}")
	public String deleteProduct(@PathVariable int id, HttpServletRequest request) { // � product�� �����ϴ��� id�� �˰� �ȴ�.
		
		/* product ������  DB���� imageFileName�� �ְ� ���� image������ ����.
		 * /resources/images/ ���� ���� �������� �ʿ䰡 �ִ�. 
		 * HttpRequestServlet�� ���ڷ� �߰��� �������� ���ϴ� ��Ʈ�� �˾Ƴ��� ���� */
		Product product = productService.getProductById(id);
		
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");
		Path delPath = Paths.get(rootDirectory + "\\resources\\images\\" + product.getImageFileName());
		
		if(Files.exists(delPath)) { // ������ �����Ѵٸ�
			try {
				Files.delete(delPath);
			} catch (IOException e) { e.printStackTrace(); }
		}
		
		productService.deleteProductById(product); // hibernate
		// if(!productService.deleteProductById(id)) {
			// System.out.println("Deleting Product cannot be done");
		// }
		
		return "redirect:/admin/productInventory";
	}
	
	@RequestMapping("/productInventory/editProduct/{id}")
	public String editProduct(@PathVariable int id, Model model) { // View�� Model�� �ѱ�� ���� ���� ���ڷ� �޴´�.
		
		Product product = productService.getProductById(id);
		
		model.addAttribute("product", product);
		return "editProduct"; // editProduct.jsp�� product�� id�� �޴� ����� ����.
		//return "redirect:/admin/productInventory";
	}
	
	// http://localhost:8080/eStore/admin/productInventory/editProduct/7 ���� Submit��ư�� ������ ����
	@RequestMapping(value="/productInventory/editProduct", method = RequestMethod.POST)
	/* public String editProductPost(Product product) { // form������ ���� Data-binding�� ���� Product��ü�� mapping 
	 * �̶�, @Valid �ֳ����̼����� Product ���� Constraints�� ���� ������ ������ �Ǹ�
	 * ���� ����� BindingResult�� ���Եȴ�. 
	 * BindingResult��ü�� Product �𵨰�ü�� �Բ� ��� View�� ���޵�. */
	public String editProductPost(@Valid Product product, BindingResult result, HttpServletRequest request) { // Data-binding�� ���� ��ü�� mapping
		/* Product(id=0, name=�ּ�, category=����, price=1800000, manufacturer=�Ｚ����, unitInStock=30
		 * , description=MyMyQueen ���ư� �����ϴ� ������) �� �Բ�
		 * Editing product cannot be done <-- ��� ��µȴ�.
		 * id = 0�̱� ������ DB�� �ݿ��� �ȵ�. - editProductPost�޼ҵ�� editProduct�޼ҵ� ���Ŀ� ȣ��Ǵµ�
		 * editProduct.jsp ���Ͽ� product ���� �ѱ涧 id�� �޴� ����� ������ �߻�. (����Ʈ = 0) */
		//System.out.println(product); 		
		
		if(result.hasErrors()) {
			List<ObjectError> errors = result.getAllErrors();
			for(ObjectError error:errors) {
				error.getDefaultMessage();
			}
			return "editProduct";
		}
		
		/* 'imageFileName'�� DB�� �����ϰ� 'image'��  /resources/images/�� �����Ѵ�.
		 * �ٸ�, ������ / ���͸��� �������� ���� �� �ִ�. ��Ʈ ���͸��� �˱� ���� ���ڿ� HttpServletRequest�� �޴´�.*/
		MultipartFile productImage = product.getProductImage();
		String rootDirectory = request.getSession().getServletContext().getRealPath("/"); // ���� ��Ʈ���͸�
		
		Path savePath = Paths.get(rootDirectory + "\\resources\\images\\" + productImage.getOriginalFilename()); // Image���� ��� ����
		if(productImage != null && !productImage.isEmpty()) {
			try {
				productImage.transferTo(new File(savePath.toString())); // ��ο� Image�� ����
			} catch(Exception e) { e.printStackTrace(); }
		}
		
		product.setImageFileName(productImage.getOriginalFilename()); // DB�� ImageFileName����

		productService.editProduct(product); // hibernate
		// if(!productService.editProduct(product)) {
			// System.out.println("Editing product cannot be done");
		// }
		return "redirect:/admin/productInventory";
	}
}
