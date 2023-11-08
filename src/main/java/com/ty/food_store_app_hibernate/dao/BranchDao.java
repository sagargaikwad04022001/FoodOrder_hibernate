package com.ty.food_store_app_hibernate.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.ty.food_store_app_hibernate.dto.Branch;
import com.ty.food_store_app_hibernate.dto.User;

public class BranchDao {

	static EntityManagerFactory factory=Persistence.createEntityManagerFactory("manytomany");
	static EntityManager manager=factory.createEntityManager();
	static EntityTransaction transaction=manager.getTransaction();
	
	public Branch saveBranch(Branch branch)
	{
		transaction.begin();
		manager.merge(branch);
		transaction.commit();
		return branch;
	}
	public Branch getBranchById(int id)
	{
		Branch branch=manager.find(Branch.class,id);
		if(branch!=null)
		{
			return branch;
		}
		return null;
	}
	public Branch updateBranch(Branch branch)
	{
		transaction.begin();
		manager.merge(branch);
		transaction.commit();
		return branch;
	}
	public boolean deleteBranch(int branchId) {
		Branch branch = manager.find(Branch.class, branchId);
		transaction.begin();
		if (branch != null) {
			manager.remove(branch);
			transaction.commit();
			return true;
		}
		transaction.commit();
		return false;
	}
	public List<Branch> getAllBranches() {
		String jpql = "from Branch";
		Query query = manager.createQuery(jpql);
		List<Branch> aList = query.getResultList();
		if (aList.size() > 0) {
			return aList;
		}
		return null;
	}
}
