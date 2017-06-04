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
	
	
	/* DAO접근 */
	@Autowired
	private ProductService productService;
	
	@RequestMapping // /admin에 대해 adminPage()메소드 호출
	public String adminPage() {
		return "admin";
	}
	
	@RequestMapping("/productInventory") // /admin/productInventory
	public String getProducts(Model model) {
		List<Product> products = productService.getProducts();
		model.addAttribute("products", products); // view에서 이름으로 model을 접근가능하게
		
		/* Image를 읽어 productInventory.jsp에 띄우기 */
		
		
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
	
	/* addProduct.jsp 에서 submit버튼 누르면 수행되는 메서드 */
	@RequestMapping(value="/productInventory/addProduct", method=RequestMethod.POST)
	/* 사용자가 입력한 Form데이터가 스프링에 의해 DataBinding이 이루어져 Product 인자 객체에 매핑된다. 
	 * public String addProductPost(Product product) { 
	 * 이때, @Valid 애너테이션으로 Product 모델의 Constraints에 따라 검증이 수행이 되며
	 * 검증 결과는 BindingResult에 삽입된다. 
	 * BindingResult객체는 Product 모델객체에 함께 담겨 View로 전달됨. */
	public String addProductPost(@Valid Product product, BindingResult result, HttpServletRequest request) {
		
		/* Data-Validation - BindingResult를 출력 */
		if(result.hasErrors()) {
			System.out.println("Form data has errors");
			List<ObjectError> errors = result.getAllErrors();
			for(ObjectError error:errors) {
				error.getDefaultMessage();
			}
			return "addProduct";
		}
		/* 'imageFileName'는 DB에 저장하고 'image'는  /resources/images/에 저장한다.
		 * 다만, 배포시 / 디렉터리가 동적으로 변할 수 있다. 루트 디렉터리를 알기 위해 인자에 HttpServletRequest를 받는다.*/
		MultipartFile productImage = product.getProductImage();
		String rootDirectory = request.getSession().getServletContext().getRealPath("/"); // 동적 루트디렉터리
		
		Path savePath = Paths.get(rootDirectory + "\\resources\\images\\" + productImage.getOriginalFilename()); // Image저장 경로 설정
		if(productImage != null && !productImage.isEmpty()) {
			try {
				productImage.transferTo(new File(savePath.toString())); // 경로에 Image를 저장
			} catch(Exception e) { e.printStackTrace(); }
		}
		
		product.setImageFileName(productImage.getOriginalFilename()); // DB에 ImageFileName저장
		
		productService.addProduct(product); // hibernate
		// if(!productService.addProduct(product)) { // 실패시
			// System.out.println("Adding Product cannot be done");
		// }
		/* return /admin/productInventory; <- 여기서는 'products'를 받기 때문에 안된다.
		 * 위의 getProducts 메서드가 호출이 되어 DB에서 다시 읽어와야하기때문에
		 * getProducts 메서드로 리다이렉트를 한다. */
		return "redirect:/admin/productInventory"; // 위 getProducts에서 추가가되어 보여주도록 리다이렉트
	}
	
	@RequestMapping("/productInventory/deleteProduct/{id}")
	public String deleteProduct(@PathVariable int id, HttpServletRequest request) { // 어떤 product를 삭제하는지 id를 알게 된다.
		
		/* product 삭제시  DB에는 imageFileName만 있고 실제 image파일은 없다.
		 * /resources/images/ 에서 따로 삭제해줄 필요가 있다. 
		 * HttpRequestServlet을 인자로 추가해 동적으로 변하는 루트를 알아내어 삭제 */
		Product product = productService.getProductById(id);
		
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");
		Path delPath = Paths.get(rootDirectory + "\\resources\\images\\" + product.getImageFileName());
		
		if(Files.exists(delPath)) { // 파일이 존재한다면
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
	public String editProduct(@PathVariable int id, Model model) { // View로 Model을 넘기기 위해 모델을 인자로 받는다.
		
		Product product = productService.getProductById(id);
		
		model.addAttribute("product", product);
		return "editProduct"; // editProduct.jsp에 product의 id를 받는 기능이 없다.
		//return "redirect:/admin/productInventory";
	}
	
	// http://localhost:8080/eStore/admin/productInventory/editProduct/7 에서 Submit버튼을 누르면 수행
	@RequestMapping(value="/productInventory/editProduct", method = RequestMethod.POST)
	/* public String editProductPost(Product product) { // form데이터 값이 Data-binding에 의해 Product객체에 mapping 
	 * 이때, @Valid 애너테이션으로 Product 모델의 Constraints에 따라 검증이 수행이 되며
	 * 검증 결과는 BindingResult에 삽입된다. 
	 * BindingResult객체는 Product 모델객체에 함께 담겨 View로 전달됨. */
	public String editProductPost(@Valid Product product, BindingResult result, HttpServletRequest request) { // Data-binding에 의해 객체에 mapping
		/* Product(id=0, name=휘센, category=가전, price=1800000, manufacturer=삼성전자, unitInStock=30
		 * , description=MyMyQueen 연아가 광고하는 에어컨) 과 함께
		 * Editing product cannot be done <-- 라고 출력된다.
		 * id = 0이기 때문에 DB에 반영이 안됨. - editProductPost메소드는 editProduct메소드 이후에 호출되는데
		 * editProduct.jsp 파일에 product 모델을 넘길때 id를 받는 기능이 없을때 발생. (디폴트 = 0) */
		//System.out.println(product); 		
		
		if(result.hasErrors()) {
			List<ObjectError> errors = result.getAllErrors();
			for(ObjectError error:errors) {
				error.getDefaultMessage();
			}
			return "editProduct";
		}
		
		/* 'imageFileName'는 DB에 저장하고 'image'는  /resources/images/에 저장한다.
		 * 다만, 배포시 / 디렉터리가 동적으로 변할 수 있다. 루트 디렉터리를 알기 위해 인자에 HttpServletRequest를 받는다.*/
		MultipartFile productImage = product.getProductImage();
		String rootDirectory = request.getSession().getServletContext().getRealPath("/"); // 동적 루트디렉터리
		
		Path savePath = Paths.get(rootDirectory + "\\resources\\images\\" + productImage.getOriginalFilename()); // Image저장 경로 설정
		if(productImage != null && !productImage.isEmpty()) {
			try {
				productImage.transferTo(new File(savePath.toString())); // 경로에 Image를 저장
			} catch(Exception e) { e.printStackTrace(); }
		}
		
		product.setImageFileName(productImage.getOriginalFilename()); // DB에 ImageFileName저장

		productService.editProduct(product); // hibernate
		// if(!productService.editProduct(product)) {
			// System.out.println("Editing product cannot be done");
		// }
		return "redirect:/admin/productInventory";
	}
}
