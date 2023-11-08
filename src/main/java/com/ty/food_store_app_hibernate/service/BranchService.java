package com.ty.food_store_app_hibernate.service;

import com.ty.food_store_app_hibernate.dao.BranchDao;
import com.ty.food_store_app_hibernate.dto.Branch;

public class BranchService {

	BranchDao branchDao=new BranchDao();
	public Branch saveBranch(Branch branch) {
		branchDao.saveBranch(branch);
		return branch;
	}
	public Branch findById(int bid) {
		Branch branch=branchDao.getBranchById(bid);
		if(branch!=null)
		{
			return branch;
		}
		return null;
	}
	public Branch updateBranch(Branch branch) {
		branchDao.updateBranch(branch);
		return branch;
		
	}

}
