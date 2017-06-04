package kr.ac.hansung.cse.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class Cart implements Serializable{
	
	private static final long serialVersionUID = 6543542542007951061L;
	
	@Id
	@GeneratedValue
	private int cartId;
	
	// OneToMany default 가 Lazy 로 되어있음.
	@OneToMany(mappedBy = "cart", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<CartItem> cartItems = new ArrayList<CartItem>();
	
	private double grandTotal;
}
