package kr.ac.hansung.cse.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name="users")
public class User implements Serializable{
	
	private static final long serialVersionUID = -5921627991557316398L;

	@Id
	@GeneratedValue
	private int userId;
	
	@NotEmpty(message="The username must not be null")
	private String username;
	
	@NotEmpty(message="The password must not be null")
	private String password;
	
	@NotEmpty(message="The email must not be null")
	private String email;
	
	@OneToOne(optional=false, cascade=CascadeType.ALL)
	@JoinColumn(unique=true)
	private ShippingAddress shippingAddress; // 배송지 주소\
	
	
	@OneToOne(optional=false, cascade=CascadeType.ALL)
	@JoinColumn(unique=true)
	private Cart cart; // 카트
	
	private boolean enabled = false; // 가입자 활성화 정보 // 디폴트값 false
	
	private String authority; // 권한
	
}
