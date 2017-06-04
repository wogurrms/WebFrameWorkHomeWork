package kr.ac.hansung.cse.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity // Class를 Entity Bean으로 등록하여 DB에 Table로 mapping이 이루어진다
@Table(name="product") // 테이블 이름 지정
public class Product implements Serializable{
	
	private static final long serialVersionUID = 7325891647483606904L;

	@Id // Primary Key
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="product_id")
	private int id;
	
	/* Data Validation */
	@NotEmpty(message="product name must not be null")
	private String name;
	
	private String category;
	
	@Min(value=0, message="product price must not be less then zero")
	private int price;
	
	@NotEmpty(message="product manufacturer must not be null")
	private String manufacturer;
	
	@Min(value=0, message="product unit in stock must not be less then zero")
	private int unitInStock;
	
	private String description;
	
	/* 사진파일, 사진파일이름, 사진파일정보 포함한 객체로 
	 * Performance때문에 DB에 저장하지는 않는다. 
	 * 이미지 서버에 저장한다. (사진 이름은 DB에 저장한다) */
	@Transient // DB에 저장되지 않는다.
	private MultipartFile productImage;
	private String imageFileName;
	
	@OneToMany(mappedBy="product", cascade= CascadeType.ALL, fetch= FetchType.EAGER)
	@JsonIgnore
	private List<CartItem> cartItemList = new ArrayList<CartItem>();
}
