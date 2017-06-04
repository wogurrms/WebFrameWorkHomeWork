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
@Entity // Class�� Entity Bean���� ����Ͽ� DB�� Table�� mapping�� �̷������
@Table(name="product") // ���̺� �̸� ����
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
	
	/* ��������, ���������̸�, ������������ ������ ��ü�� 
	 * Performance������ DB�� ���������� �ʴ´�. 
	 * �̹��� ������ �����Ѵ�. (���� �̸��� DB�� �����Ѵ�) */
	@Transient // DB�� ������� �ʴ´�.
	private MultipartFile productImage;
	private String imageFileName;
	
	@OneToMany(mappedBy="product", cascade= CascadeType.ALL, fetch= FetchType.EAGER)
	@JsonIgnore
	private List<CartItem> cartItemList = new ArrayList<CartItem>();
}
