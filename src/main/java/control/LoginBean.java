package control;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import dao.UserDAO;
import dao.UserDAOImp;
import entities.User;
import util.GrowlViewBean;
import util.SessionUtil;

@ManagedBean(name = "LoginBean")
@SessionScoped
public class LoginBean {

	private String usrLogin;
	private String pswLogin;
	
	private List<User> registeredUsers;
	private UserDAO userDao;

	private GrowlViewBean message;

	public LoginBean() {
		this.message = new GrowlViewBean();

		this.registeredUsers = new ArrayList<User>();
		this.userDao = new UserDAOImp();
	}

	public String login() {

		boolean msg = false;

		this.registeredUsers = this.userDao.findAll(null);

		for (User listUsers : registeredUsers) {
			if ((listUsers.getEmail().equals(usrLogin) && listUsers.getPassword().equals(pswLogin))) {
				msg = true;
			}
		}
		if (msg) {
			message.setSuccessMessage("Sucesso no Login!");
			message.saveMessage(true);
			
			User current = new User();
			current = userDao.findAuser(usrLogin);
			
			SessionUtil.setParam("logged", current);
			
			return "search.xhtml";
		} else {
			message.setErrorMessage("Falha no Login! Verifique seu usuário e senha!");
			message.saveMessage(false);
		}
		usrLogin = null;
		pswLogin = null;
		
		return null;
	}

	
	
	
	
	public String getUsrLogin() {
		return usrLogin;
	}
	public void setUsrLogin(String usrLogin) {
		this.usrLogin = usrLogin;
	}

	public String getPswLogin() {
		return pswLogin;
	}
	public void setPswLogin(String pswLogin) {
		this.pswLogin = pswLogin;
	}
}
