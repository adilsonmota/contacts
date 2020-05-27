package entities;

import java.util.ArrayList;
import java.util.List;

public class Contact {
	
	private String cpf;
	private String name;
	private String email;
	private Department department;
	private Double salary;
	
	private User user;
	private List<Adress> adresses = new ArrayList<Adress>();
	private List<Phone> phones = new ArrayList<Phone>();
	
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Department getDepartment() {
		return department;
	}
	
	public void setDepartment(Department department) {
		this.department = department;
	}
	
	public Double getSalary() {
		return salary;
	}
	
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public List<Adress> getAdresses() {
		return adresses;
	}
	
	public List<Phone> getPhones() {
		return phones;
	}
}
