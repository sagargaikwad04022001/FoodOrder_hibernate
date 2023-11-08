package com.ty.food_store_app_hibernate.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.ty.food_store_app_hibernate.dto.FoodOrder;
import com.ty.food_store_app_hibernate.dto.Item;
import com.ty.food_store_app_hibernate.dto.User;

public class FoodOrderDao {

	
	static EntityManagerFactory factory=Persistence.createEntityManagerFactory("manytomany");
	static EntityManager manager=factory.createEntityManager();
	static EntityTransaction transaction=manager.getTransaction();
	
	public FoodOrder saveFoodOrder(FoodOrder foodOrder)
	{
		transaction.begin();
		manager.persist(foodOrder);
		transaction.commit();
		return foodOrder;
	}
	public FoodOrder getFoodOrderById(int id)
	{
		FoodOrder foodOrder=manager.find(FoodOrder.class,id);
		if(foodOrder!=null)
		{
			return foodOrder;
		}
		return null;
	}
	public FoodOrder updateFoodOrder(FoodOrder foodOrder)
	{
		transaction.begin();
		manager.merge(foodOrder);
		transaction.commit();
		return foodOrder;
	}
	public boolean deleteFoodO(FoodOrder foodOrder2) {
		
		transaction.begin();
		List<Item> items=foodOrder2.getItems();
		if(items!=null && items.size()>0)
		{
			for(Item item:items)
			{
				manager.remove(item);
			}
		}
		manager.remove(foodOrder2);
		transaction.commit();
		return true;
		
	}
	public List<FoodOrder> getAllFoodOrders() {
		String jpql = "from FoodOrder";
		Query query = manager.createQuery(jpql);
		List<FoodOrder> aList = query.getResultList();
		if (aList.size() > 0) {
			return aList;
		}
		return null;
	}
}
