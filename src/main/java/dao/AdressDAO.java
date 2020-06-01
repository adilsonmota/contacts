package dao;

import java.util.List;

import entities.Adress;
import entities.User;

public interface AdressDAO {
	
	public void insert(Adress adress);
	public void update(Adress adress);
	public void remove(Integer id);
	public Adress findByUser(User user);
}
