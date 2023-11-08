package com.ty.food_store_app_hibernate.service;

import java.util.List;

import com.ty.food_store_app_hibernate.dao.FoodProductsDao;
import com.ty.food_store_app_hibernate.dto.FoodProduct;

public class FoodProductService {

	FoodProductsDao foodProductsDao=new FoodProductsDao();
	public FoodProduct saveFoodProduct(FoodProduct foodProduct) {
		
		foodProductsDao.saveFoodProduct(foodProduct);
		return foodProduct;
	}
	public FoodProduct findById(int fid) {
		FoodProduct foodProduct=foodProductsDao.getFoodProductById(fid);
		if(foodProduct!=null)
		{
			return foodProduct;
		}
		return null;
	}
	public FoodProduct updateFoodProduct(FoodProduct fp) {
		
		foodProductsDao.updateFoodProduct(fp);
		return fp;
		
	}
	public boolean removeFP(FoodProduct fp) {
		foodProductsDao.removeFoodProduct(fp);
		return true;
	}
	public List<FoodProduct> getAllProducts() {
		List<FoodProduct> list= foodProductsDao.getAllfoodProduct();
		return list;
	}

}
