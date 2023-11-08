package com.ty.food_store_app_hibernate.service;

import java.util.List;

import com.ty.food_store_app_hibernate.dao.UserDao;
import com.ty.food_store_app_hibernate.dto.User;

public class UserService {

	UserDao  userDao=new UserDao();
	public User saveUser(User user2) {
		User user=userDao.saveUser(user2);
		return user;
		
	}
	public User updateUser(User user) {
		userDao.updateUser(user);
		return user;
		
	}
	public User findById(String email) {
		User user=userDao.getUserById(email);
		if(user!=null)
		{
			return user;
		}
		return null;
	}
	public boolean removeUser(User user2) {
		
		userDao.removeUser(user2);
		return true;
	}
	public List<User> getAllCustomers() {
		List<User> users=userDao.getAllCustomers();
		return users;
	}
	public List<User> getAllStaff() {
		List<User> users=userDao.getAllStaff();
		return users;
	}

}
