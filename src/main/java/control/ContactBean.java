package control;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import dao.AdressDAO;
import dao.AdressDAOImp;
import dao.PhoneDAO;
import dao.PhoneDAOImp;
import dao.UserDAO;
import dao.UserDAOImp;
import entities.Adress;
import entities.Phone;
import entities.User;
import util.GrowlViewBean;
import util.SessionUtil;

@ManagedBean(name = "ContactBean")
@SessionScoped

public class ContactBean {
	
	private String keyword;
	
	private User user;
	private List<User> registeredUsers;
	private UserDAO userDao;
	private User selectedUser;
	
	private List<Phone> myListPhones;
	private List<Phone> listPhones;
	private Phone phone;
	private Phone selectedPhone;
	private PhoneDAO phoneDao;
	
	private User newUser;
	private Phone newPhone;
	private Adress newAdress;
	
	private Adress adress;
	private Adress myAdress;
	private AdressDAO adressDao;
	
	private GrowlViewBean message;

	public ContactBean() {
		this.message = new GrowlViewBean();
		
		this.phoneDao = new PhoneDAOImp();
		this.selectedPhone = new Phone();
		this.phone = new Phone();
		
		this.myAdress = new Adress();
		this.adress = new Adress();
		this.adressDao = new AdressDAOImp();
		
		this.registeredUsers = new ArrayList<User>();
		this.user = new User();
		this.userDao = new UserDAOImp();
		this.selectedUser = new User();
		 
		this.myListPhones = new ArrayList<Phone>();
		this.listPhones = new ArrayList<Phone>();
		
		this.newUser = new User();
		this.newAdress = new Adress();
		this.newPhone = new Phone();
		
		this.currentUser();
	}

	public void currentUser() {
		Object obj = SessionUtil.getParam("logged");
		user  = (User) obj;
		if (user !=null) {
			this.myListPhones = phoneDao.findByUser(user);
			this.myAdress = adressDao.findByUser(user);
		}	
	}
	
	
	public String logoff() {
		SessionUtil.remove("logged");
		return "index.xhtml";
	}
	
	public void listPhoneAdd() {
		if (phone != null) {
			listPhones.add(phone);
			this.phone = new Phone();
		}
	}
	
	public void addPhone() {
		if (this.newPhone != null) {
			newPhone.setUser(user);
			phoneDao.insert(this.newPhone);
			message.setSuccessMessage("Adiconado com sucesso!");
			message.saveMessage(true);
		}
	}
	
	public void updatePhone() {
		if (selectedPhone != null) {
			phoneDao.update(selectedPhone);
			message.setSuccessMessage("Atualizado com sucesso!");
			message.saveMessage(true);
		}
	}
	
	public void deletePhone() {
		if (selectedPhone != null) {
			phoneDao.remove(selectedPhone);
			selectedPhone = new Phone();
			message.setSuccessMessage("Excluído com sucesso!");
			message.saveMessage(true);	
		}
	}
	
	public void searchContact() {
		this.registeredUsers = userDao.findAll(keyword);
	}
	
	public void selectContact() {
		if (this.selectedUser != null) {
		this.listPhones = phoneDao.findByUser(selectedUser);
		this.adress = adressDao.findByUser(selectedUser);			
		}
	}
	
	public void selectPhone() {
		if (this.selectedPhone != null) {
			this.phone = this.selectedPhone;
			this.selectedPhone = new Phone();
		}
	}
	
	public void updateAdress() {
		if (this.adress != null) {
			adressDao.update(adress);
		}
	}
	

	
	public String registerUser() {

		boolean msg = true;

		this.registeredUsers = this.userDao.findAll(null);

		for (User listUsers : registeredUsers) {
			if ((listUsers.getEmail().equals(this.newUser.getEmail()))
					|| (listUsers.getCpf().equals(this.newUser.getCpf()))) {
				msg = false;
			}
		}

		if (msg) {
			this.userDao.insert(newUser);
			this.newUser = new User();
			message.setSuccessMessage("Cadastro realizado com sucesso!");
			message.saveMessage(true);
			return "index.xhtml";
		} else {
			this.newUser = new User();
			message.setErrorMessage("CPF ou email já está em uso!");
			message.saveMessage(false);
		}
		return null;
	}
	
	public void registerEmployee() {
		
			this.newPhone.setUser(this.newUser);
			this.newAdress.setUser(this.newUser);
			
			if (phoneDao.findAll(newPhone) != null) {
				phoneDao.insert(this.newPhone);
			}
		
			adressDao.insert(this.newAdress);
			
			registerUser();
			
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public Phone getSelectedPhone() {
		return selectedPhone;
	}

	public void setSelectedPhone(Phone selectedPhone) {
		this.selectedPhone = selectedPhone;
	}

	public List<Phone> getListPhones() {
		return listPhones;
	}

	public Adress getAdress() {
		return adress;
	}

	public void setAdress(Adress adress) {
		this.adress = adress;
	}

	public Phone getPhone() {
		return phone;
	}

	public void setPhone(Phone phone) {
		this.phone = phone;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public List<User> getRegisteredUsers() {
		return registeredUsers;
	}

	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}

	public List<Phone> getMyListPhones() {
		return myListPhones;
	}

	public Adress getMyAdress() {
		return myAdress;
	}

	public void setMyAdress(Adress myAdress) {
		this.myAdress = myAdress;
	}

	public User getNewUser() {
		return newUser;
	}

	public void setNewUser(User newUser) {
		this.newUser = newUser;
	}

	public Phone getNewPhone() {
		return newPhone;
	}

	public void setNewPhone(Phone newPhone) {
		this.newPhone = newPhone;
	}

	public Adress getNewAdress() {
		return newAdress;
	}

	public void setNewAdress(Adress newAdress) {
		this.newAdress = newAdress;
	}

	
}
