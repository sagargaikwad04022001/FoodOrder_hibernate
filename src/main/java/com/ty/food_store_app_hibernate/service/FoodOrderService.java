package com.ty.food_store_app_hibernate.service;

import java.util.List;

import com.ty.food_store_app_hibernate.dao.FoodOrderDao;
import com.ty.food_store_app_hibernate.dto.FoodOrder;

public class FoodOrderService {

	FoodOrderDao dao=new FoodOrderDao();
	public FoodOrder saveFoodOrder(FoodOrder order) {
		
		dao.saveFoodOrder(order);
		return order;
	}
	public FoodOrder updateOrder(FoodOrder order) {
		dao.updateFoodOrder(order);
		return order;
		
	}
	public FoodOrder findById(int id) {
		FoodOrder foodOrder=dao.getFoodOrderById(id);
		return foodOrder;
	}
	public List<FoodOrder> getAllFoodOrders() {
		List<FoodOrder> flist=dao.getAllFoodOrders();
		return flist;
	}
	public boolean removeFoodOrder(FoodOrder foodOrder) {
		dao.deleteFoodO(foodOrder);
		return true;
		
	}
	

}
