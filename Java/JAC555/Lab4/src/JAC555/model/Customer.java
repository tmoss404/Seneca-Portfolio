package JAC555.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="CUSTOMERS")
public class Customer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "CUSTOMER_ID", unique = true, nullable = false, updatable = false)
	private int id;
	
	private String email;
	private String name;
	private int age;
	
	@ManyToOne
	@JoinColumn(name="SALES_REP_ID")
	private SalesRepresentative salesRep;
	
	// ------ Constructors ------
	public Customer() {
		super();
	}
	

	public Customer(int id, String email, String name, int age, SalesRepresentative salesRep) {
		super();
		this.id = id;
		this.email = email;
		this.name = name;
		this.age = age;
		this.salesRep = salesRep;
	}
	// ------ ------ ------ ------
	
	
	// ------ Getters / Setters ------
	
	// ------ Id ------
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	// ------ Email ------
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	// ------ Name ------
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	// ------ Age ------
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	// ------ salesRep ------
	@ManyToOne
	public SalesRepresentative getSalesRep() {
		return salesRep;
	}
	
	public void setSalesRep(SalesRepresentative salesRep) {
		this.salesRep = salesRep;
	}
	// ------ ------ ------ ------
	
	
	// ------ toString Method ------
	@Override
	public String toString() {
		return "Customer [id=" + id + ", email=" + email + ", name=" + name + ", age=" + age + "]";
	}
	// ------ ------ ------ ------
	
}
