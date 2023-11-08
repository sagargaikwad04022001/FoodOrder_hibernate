package com.ty.food_store_app_hibernate.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.ty.food_store_app_hibernate.dto.FoodOrder;
import com.ty.food_store_app_hibernate.dto.FoodProduct;
import com.ty.food_store_app_hibernate.dto.Menu;
import com.ty.food_store_app_hibernate.dto.User;

public class FoodProductsDao {

	
	static EntityManagerFactory factory=Persistence.createEntityManagerFactory("manytomany");
	static EntityManager manager=factory.createEntityManager();
	static EntityTransaction transaction=manager.getTransaction();
	static MenuDao menuDao=new MenuDao();
	public FoodProduct saveFoodProduct(FoodProduct foodProduct)
	{
		transaction.begin();
		manager.merge(foodProduct);
		transaction.commit();
		return foodProduct;
	}
	public FoodProduct getFoodProductById(int id)
	{
		FoodProduct foodProduct=manager.find(FoodProduct.class,id);
		if(foodProduct!=null)
		{
			return foodProduct;
		}
		return null;
	}
	public FoodProduct updateFoodProduct(FoodProduct foodProduct)
	{
		transaction.begin();
		manager.merge(foodProduct);
		transaction.commit();
		return foodProduct;
	}
	public boolean deleteFoodO(int foodProductId) {
		FoodProduct foodProduct = manager.find(FoodProduct.class, foodProductId);
		transaction.begin();
		if (foodProduct != null) {
			manager.remove(foodProduct);
			transaction.commit();
			return true;
		}
		transaction.commit();
		return false;
	}
	public List<FoodProduct> getAllfoodProduct() {
		String jpql = "from FoodProduct";
		Query query = manager.createQuery(jpql);
		List<FoodProduct> aList = query.getResultList();
		if (aList.size() > 0) {
			return aList;
		}
		return null;
	}
	public void removeFoodProduct(FoodProduct fp) {
		transaction.begin();
		Menu menu=fp.getMenu();
		List<FoodProduct> foodList=menu.getFoodProducts();
		if(foodList.size()>0 && foodList!=null)
		{
			foodList.remove(fp);
		}
		menu.setFoodProducts(foodList);
		menuDao.updateMenu(menu);
		manager.remove(fp);
		transaction.commit();
		
	}
}
