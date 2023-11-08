package com.ty.food_store_app_hibernate.service;

import com.ty.food_store_app_hibernate.dao.ItemDao;
import com.ty.food_store_app_hibernate.dto.Item;

public class ItemService {

	ItemDao dao=new ItemDao();
	public Item saveItem(Item item) {
		
		dao.saveItem(item);
		return item;
	}

}
