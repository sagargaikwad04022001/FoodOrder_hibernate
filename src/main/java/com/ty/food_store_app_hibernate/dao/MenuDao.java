package com.ty.food_store_app_hibernate.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.ty.food_store_app_hibernate.dto.FoodProduct;
import com.ty.food_store_app_hibernate.dto.Menu;
import com.ty.food_store_app_hibernate.dto.User;

public class MenuDao {

	static EntityManagerFactory factory=Persistence.createEntityManagerFactory("manytomany");
	static EntityManager manager=factory.createEntityManager();
	static EntityTransaction transaction=manager.getTransaction();
	
	public Menu saveMenu(Menu menu)
	{
	//	List<FoodProduct> fp=menu.getFoodProducts();
		
		transaction.begin();
		manager.merge(menu);

		
		transaction.commit();
		return menu;
	}
	public Menu getMenuById(int id)
	{
		Menu menu=manager.find(Menu.class,id);
		if(menu!=null)
		{
			return menu;
		}
		return null;
	}
	public Menu updateMenu(Menu menu)
	{
		transaction.begin();
		manager.merge(menu);
		transaction.commit();
		return menu;
	}
	public boolean deleteMenu(int menuId) {
		Menu menu = manager.find(Menu.class, menuId);
		transaction.begin();
		if (menu != null) {
			manager.remove(menu);
			transaction.commit();
			return true;
		}
		transaction.commit();
		return false;
	}
	public List<Menu> getAllMenu() {
		String jpql = "from Menu";
		Query query = manager.createQuery(jpql);
		List<Menu> aList = query.getResultList();
		if (aList.size() > 0) {
			return aList;
		}
		return null;
	}
}
