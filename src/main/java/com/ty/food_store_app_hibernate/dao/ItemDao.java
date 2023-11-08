package com.ty.food_store_app_hibernate.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.ty.food_store_app_hibernate.dto.Item;
import com.ty.food_store_app_hibernate.dto.User;

public class ItemDao {

	static EntityManagerFactory factory=Persistence.createEntityManagerFactory("manytomany");
	static EntityManager manager=factory.createEntityManager();
	static EntityTransaction transaction=manager.getTransaction();
	
	public Item saveItem(Item item)
	{
		transaction.begin();
		manager.persist(item);
		transaction.commit();
		return item;
	}
	public Item getItemById(int id)
	{
		Item item=manager.find(Item.class,id);
		if(item!=null)
		{
			return item;
		}
		return null;
	}
	public Item updateItem(Item item)
	{
		transaction.begin();
		manager.merge(item);
		transaction.commit();
		return item;
	}
	public boolean deleteItem(int itemId) {
		Item item = manager.find(Item.class, itemId);
		transaction.begin();
		if (item != null) {
			manager.remove(item);
			transaction.commit();
			return true;
		}
		transaction.commit();
		return false;
	}
	public List<Item> getAllItems() {
		String jpql = "from Item";
		Query query = manager.createQuery(jpql);
		List<Item> aList = query.getResultList();
		if (aList.size() > 0) {
			return aList;
		}
		return null;
	}
}
