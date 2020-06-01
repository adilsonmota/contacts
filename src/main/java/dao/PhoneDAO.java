package dao;

import java.util.List;

import entities.Phone;
import entities.User;

public interface PhoneDAO {

	public void insert(Phone phone);
	public void update(Phone phone);
	public void remove(Phone phone);
	public List<Phone> findByUser(User user);
	public List<Phone> findAll(Phone phone);
}
