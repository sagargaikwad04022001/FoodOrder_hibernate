package com.ty.food_store_app_hibernate.service;

import com.ty.food_store_app_hibernate.dao.MenuDao;
import com.ty.food_store_app_hibernate.dto.Menu;

public class MenuService {

	MenuDao menuDao=new MenuDao();
	public Menu saveMenu(Menu menu) {
		
		menuDao.saveMenu(menu);
		return menu; 
		
		
	}
	public Menu updateMenu(Menu menu) {
		menuDao.updateMenu(menu);
		return menu;
		
	}
	public Menu getMenuById(int mid) {
		Menu menu=menuDao.getMenuById(mid);
		return menu;
	}
	public Menu findById(int mid) {
		Menu menu=menuDao.getMenuById(mid);
		if(menu!=null)
		{
			return menu;
		}
		return null;
	}

}
