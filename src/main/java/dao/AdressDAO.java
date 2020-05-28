package dao;

import java.util.List;

import entities.Adress;

public interface AdressDAO {
	
	public void insert(Adress adress);
	public void update(Adress adress);
	public void remove(Integer id);
	public List<Adress> findAll(String keyword);
}
