package com.ty.food_store_app_hibernate.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.ty.food_store_app_hibernate.dao.MenuDao;
import com.ty.food_store_app_hibernate.dao.UserDao;
import com.ty.food_store_app_hibernate.dto.Branch;
import com.ty.food_store_app_hibernate.dto.FoodOrder;
import com.ty.food_store_app_hibernate.dto.FoodProduct;
import com.ty.food_store_app_hibernate.dto.Item;
import com.ty.food_store_app_hibernate.dto.Menu;
import com.ty.food_store_app_hibernate.dto.User;
import com.ty.food_store_app_hibernate.service.BranchService;
import com.ty.food_store_app_hibernate.service.FoodOrderService;
import com.ty.food_store_app_hibernate.service.FoodProductService;
import com.ty.food_store_app_hibernate.service.ItemService;
import com.ty.food_store_app_hibernate.service.MenuService;
import com.ty.food_store_app_hibernate.service.UserService;

public class FoodDriver {

	static EntityManagerFactory factory = Persistence.createEntityManagerFactory("manytomany");
	static EntityManager manager = factory.createEntityManager();
	static EntityTransaction transaction = manager.getTransaction();
	static Scanner scanner = new Scanner(System.in);
	static UserDao userDao = new UserDao();
	static BranchService branchService = new BranchService();
	static UserService userService = new UserService();
	static FoodOrderService foodOrderService = new FoodOrderService();
	static FoodProductService foodProductService = new FoodProductService();
	static ItemService itemService = new ItemService();
	static MenuService menuService = new MenuService();

	public static void main(String[] args) {

		boolean exit = true;
		while (exit) {
			System.out.println("=========Welcome to food Store Application==========");
			System.out.println("Login to use application");
			System.out.println("Username:-");
			String email = scanner.next();

			System.out.println("Password:-");
			String password = scanner.next();

			User user = manager.find(User.class, email);
			if (user == null) {
				System.out.println("Wrong Email Details");
				continue;
			}
			if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
				String role = user.getRole();
				switch (role) {
				case "Manager": {
					ManagerUI(user);
				}
					break;
				case "Staff": {
					StaffUI(user);
				}
					break;
				case "Customer": {
					CustomerUI(user);
				}
					break;
				}
			} else {
				System.out.println("Invalid user credentials");
			}
		}

	}

	private static void CustomerUI(User user) {

		System.out.println(
				"-------------------------\n======Customer=====\nWelcome to food store app \n 1.View my Order \n 2.View my all Ordes \n 3.EXIT");

		boolean exit = true;
		while (exit) {
			int choic = scanner.nextInt();
			switch (choic) {
			case 1: {
				System.out.println("Enter order id");
				int id = scanner.nextInt();
				FoodOrder foodOrder = foodOrderService.findById(id);
				if (foodOrder != null) {
					System.out.println("Food Order ID:" + foodOrder.getId());
					System.out.println("Food Order ID:" + foodOrder.getStatus());
					List<Item> items = foodOrder.getItems();
					if (items != null && items.size() > 0) {
						for (Item item : items) {
							System.out.println("Item Name:" + item.getName());
							System.out.println("Item Type:" + item.getType());
							System.out.println("Item Quantity:" + item.getQuantity());
						}
					}

				} else {
					System.out.println("No food order exist with given id");
				}
			}
				break;
			case 2: {
				List<FoodOrder> flist = user.getFoodOrders();
				if (flist != null && flist.size() > 0) {
					for (FoodOrder foodOrder : flist) {
						System.out.println("Food Order ID:" + foodOrder.getId());
						System.out.println("Food Order ID:" + foodOrder.getStatus());
						List<Item> items = foodOrder.getItems();
						if (items != null && items.size() > 0) {
							for (Item item : items) {
								System.out.println("Item Name:" + item.getName());
								System.out.println("Item Type:" + item.getType());
								System.out.println("Item Quantity:" + item.getQuantity());
							}
						}
						System.out.println("=====================================");
					}
				} else {
					System.out.println("No Food Orders Exist");
				}
				break;
			}

			case 3: {
				exit = false;
				System.out.println("logOut successfully");
			}
				break;
			default: {
				System.out.println("==========incorrect choice========");
			}
			}
		}

	}

	private static void StaffUI(User user) {
		boolean exit = true;
		while (exit) {
			System.out.println(
					"-------------------------\n======Staff Member=====\nWelcome to food store app \n 1.View Menu \n 2.Create Food Order \n"
							+ " 3.Create Customer \n 4.Update Food Order \n"
							+ " 5.Remove FoodOrder \n 6.View Food Order \n 7.Remove Customer \n 8.View Customer \n 9.EXIT");
			int sch = scanner.nextInt();
			switch (sch) {
			case 1: {

				List<FoodProduct> flist = foodProductService.getAllProducts();
				// List<FoodProduct> fpList=menu.getFoodProducts();
				if (flist.size() > 0 && flist != null) {
					for (FoodProduct foodProduct : flist) {
						System.out.println("Food Product id:" + foodProduct.getId());
						System.out.println("Food Product name:" + foodProduct.getName());
						System.out.println("Food Product type:" + foodProduct.getType());
						System.out.println("Food Product about:" + foodProduct.getAbout());
						System.out.println("Food Product availability:" + foodProduct.getAvailability());
						System.out.println("Food Product price:" + foodProduct.getPrice());
						System.out.println("=================================");
					}
				} else {
					System.out.println("Food Products Not Available");
				}

			}
				break;
			case 2: {
				System.out.println("Enter customer email id");
				String email = scanner.next();
				User user2 = userService.findById(email);
				if (user2 != null && user2.getRole().equals("Customer")) {
					System.out.println("Enter status of food order");
					String status = scanner.next();

					System.out.println("Enter contact number");
					long contact = scanner.nextLong();
					System.out.println("How many items you want to add in this order");
					int num = scanner.nextInt();
					FoodOrder order = new FoodOrder();
					order.setStatus(status);
					order.setTotalPrice(0);
					order.setCustomerName(user2.getName());
					order.setContactNumber(contact);
					order.setUser(user2);
					List<Item> items = new ArrayList<>();
					order.setItems(items);
					foodOrderService.saveFoodOrder(order);
					double sum = 0;
					for (int i = 1; i <= num; i++) {
						System.out.println("Enter Product id");
						int pid = scanner.nextInt();
						FoodProduct fp = foodProductService.findById(pid);
						if (fp != null) {
							System.out.println("Enter quantity you want to add");
							int quantity = scanner.nextInt();
							Item item = new Item();
							item.setProductId(pid);
							item.setName(fp.getName());
							item.setPrice(fp.getPrice());
							item.setQuantity(quantity);
							item.setType(fp.getType());
							items.add(item);
							itemService.saveItem(item);
							sum += fp.getPrice() * quantity;
						} else {
							System.out.println("Food Product Not Exist");
						}
					}
					order.setTotalPrice(sum);
					order.setItems(items);
					foodOrderService.updateOrder(order);
					System.out.println("===============================");
					System.out.println("======Food Order Registered=======");
					System.out.println("===============================");
				} else {
					System.out.println("No Customer Found create customer first");
				}
			}
				break;
			case 3: {
				System.out.println("Enter customer id");
				int sid = scanner.nextInt();
				System.out.println("enter customer name");
				String name = scanner.next();
				System.out.println("enter customer email");
				String email = scanner.next();
				System.out.println("enter customer password");
				String password = scanner.next();
				String role = "Customer";
				User user2 = new User();
				user2.setId(sid);
				user2.setRole(role);
				user2.setEmail(email);
				user2.setPassword(password);
				user2.setName(name);
				userService.saveUser(user2);
				System.out.println("===============================");
				System.out.println("====Customer Registered successfully====");
				System.out.println("===============================");
			}
				break;
			case 4: {
				System.out.println("Enter food order id you want to update");
				int id = scanner.nextInt();
				FoodOrder foodOrder = foodOrderService.findById(id);
				if (foodOrder != null) {
					User user1 = foodOrder.getUser();
					List<FoodOrder> flist = user1.getFoodOrders();
					flist.remove(foodOrder);
					System.out.println("enter status for food order update");
					String status = scanner.next();
					foodOrder.setStatus(status);
					foodOrderService.updateOrder(foodOrder);
					flist.add(foodOrder);
					userService.updateUser(user1);
					System.out.println("=================================");
					System.out.println("========Food Order Updated=======");
					System.out.println("=================================");

				}
			}
				break;
			case 9: {
				exit = false;
				System.out.println("================================");
				System.out.println("======LogOut successfully======");
				System.out.println("================================");
			}
				break;
			case 5: {
				System.out.println("Enter food order id you want to delete");
				int id = scanner.nextInt();
				FoodOrder foodOrder = foodOrderService.findById(id);
				if (foodOrder != null) {
					foodOrderService.removeFoodOrder(foodOrder);
					System.out.println("================================");
					System.out.println("======Food Order Removed========");
					System.out.println("================================");
				} else {
					System.out.println("================================");
					System.out.println("======Food Order Not Exist=====");
					System.out.println("================================");
				}
			}
				break;
			case 6: {
				System.out.println("Which order you want to fetch \n 1. Single FO \n 2.All Food Orers");
				int ch = scanner.nextInt();
				switch (ch) {
				case 1: {
					System.out.println("Enter food order id");
					int id = scanner.nextInt();
					FoodOrder foodOrder = foodOrderService.findById(id);
					if (foodOrder != null) {
						System.out.println("Food Order " + foodOrder.getCustomerName());
						System.out.println("Food Order ID:" + foodOrder.getId());
						System.out.println("Food Order ID:" + foodOrder.getStatus());

						List<Item> items = foodOrder.getItems();
						if (items != null && items.size() > 0) {
							for (Item item : items) {
								System.out.println("Item Name:" + item.getName());
								System.out.println("Item Type:" + item.getType());
								System.out.println("Item Quantity:" + item.getQuantity());
							}
						}
						System.out.println("=====================================");
					} else {
						System.out.println("================================");
						System.out.println("=========Food Order Not Exist=========");
						System.out.println("================================");
					}
				}
					break;
				case 2: {
					List<FoodOrder> flist = foodOrderService.getAllFoodOrders();
					if (flist != null && flist.size() > 0) {
						for (FoodOrder foodOrder : flist) {
							System.out.println("Food Order ID:" + foodOrder.getId());
							System.out.println("Food Order ID:" + foodOrder.getStatus());
							List<Item> items = foodOrder.getItems();
							if (items != null && items.size() > 0) {
								for (Item item : items) {
									System.out.println("Item Name:" + item.getName());
									System.out.println("Item Type:" + item.getType());
									System.out.println("Item Quantity:" + item.getQuantity());
								}
							}
							System.out.println("=====================================");
						}
					} else {
						System.out.println("======================================");
						System.out.println("=====No Food Orders Available=========");
						System.out.println("======================================");
					}
				}
				}
			}
				break;
			case 7: {
				System.out.println("Enter customer email you want to remove");
				String email = scanner.next();
				User user2 = userService.findById(email);
				if (user2 != null && user2.getRole().equals("Customer")) {
					userService.removeUser(user2);
					System.out.println("================================");
					System.out.println("======Customer  Removed========");
					System.out.println("================================");
				} else {
					System.out.println("================================");
					System.out.println("======Customer Not Exist========");
					System.out.println("================================");
				}
			}
				break;
			case 8: {
				System.out.println("Which customer you want to fetch \n 1.Single customer by id \n 2.All Customers");
				int ch = scanner.nextInt();
				switch (ch) {
				case 1: {
					System.out.println("Enter customer email you want to fetch");
					String email = scanner.next();
					User user2 = userService.findById(email);
					if (user2 != null) {
						System.out.println("Customer Id:" + user2.getId());
						System.out.println("Customer Name:" + user2.getName());
						System.out.println("Customer email:" + user2.getEmail());
						System.out.println("Customer Pass:" + user2.getPassword());
						List<FoodOrder> orders = user2.getFoodOrders();
						if (orders != null && orders.size() > 0) {
							for (FoodOrder order : orders) {
								System.out.println("Order Id:" + order.getId());
								System.out.println("Order status:" + order.getStatus());
								System.out.println("Order status:" + order.getTotalPrice());
								System.out.println("============================");
							}
						} else {
							System.out.println("===============================");
							System.out.println("=======No Food Orders Exist=======");
							System.out.println("===============================");
						}
					} else {
						System.out.println("===============================");
						System.out.println("========User Not Exist=======");
						System.out.println("===============================");
					}
				}
					break;
				case 2: {
					List<User> users = userService.getAllCustomers();
					if (users != null && users.size() > 0) {
						for (User user2 : users) {
							System.out.println("User ID:" + user2.getId());
							System.out.println("User Name:" + user2.getName());
							System.out.println("User Email:" + user2.getEmail());
							System.out.println("User Password:" + user2.getPassword());
						}
					} else {
						System.out.println("===============================");
						System.out.println("======No Customers Exist======");
						System.out.println("===============================");
					}
				}
					break;
				}
			}
				break;
			default: {
				System.out.println("Incorrect choice");
			}
			}
		}

	}

	private static void ManagerUI(User user) {
		boolean exit = true;
		while (exit) {
			System.out.println(
					"--------------------------\n======Branch Manager=====\n Welcome to food store application \n Enter Operation \n 1.Add Details \n 2.Get Details \n 3.Update Details"
							+ " \n 4.Remove Details \n 5.EXIT ");
			int choice = scanner.nextInt();
			switch (choice) {
			case 1: {
				System.out.println("Which Details You want to add \n 1.FoodProduct \n 2.Staff");
				int choice2 = scanner.nextInt();
				switch (choice2) {
//				case 1: {
//					System.out.println("enter branch id");
//					int sid = scanner.nextInt();
//					System.out.println("enter branch name");
//					String name = scanner.next();
//					System.out.println("enter branch address");
//					String address = scanner.next();
//					System.out.println("enter branch email");
//					String email = scanner.next();
//					System.out.println("enter branch phone");
//					long phone = scanner.nextLong();
//					Branch branch = new Branch(sid, name, address, email, phone, user);
//					branchService.saveBranch(branch);
//					List<Branch> branches = user.getBranches();
//					branches.add(branch);
//					user.setBranches(branches);
//					userDao.updateUser(user);
//					System.out.println("===============================");
//					System.out.println("========Branch Registered======");
//					System.out.println("===============================");
//				}
//					break;

//				case 2: {
////				System.out.println("enter menu id");
////				int mid=scanner.nextInt();
//					Menu menu = new Menu();
//
//					
//					menu.setUser(user);
//					menu.setFoodProducts(new ArrayList<>());
//
//					menuService.saveMenu(menu);
//
//					// menuService.updateMenu(menu);
//			
//					
//					System.out.println("===============================");
//					System.out.println("====Menu id registered successfully now you can add products in it====");
//					System.out.println("===============================");
//				}
//				

				// break;
				case 2: {
					System.out.println("enter staff id");
					int sid = scanner.nextInt();
					System.out.println("enter staff name");
					String name = scanner.next();
					System.out.println("enter staff email");
					String email = scanner.next();
					System.out.println("enter staff password");
					String password = scanner.next();
					
					String role ="Staff";
					User user2 = new User();
					user2.setId(sid);
					user2.setRole(role);
					user2.setEmail(email);
					user2.setPassword(password);
					user2.setName(name);
					userService.saveUser(user2);
					System.out.println("===============================");
					System.out.println("====Staff Registered successfully====");
					System.out.println("===============================");
				}
					break;
				case 1: {
					System.out.println("Enter menu id for which you are adding products");
					int mid = scanner.nextInt();
					Menu menu = menuService.getMenuById(mid);
					if (menu != null) {
						List<FoodProduct> fplist =menu.getFoodProducts();
						if(fplist==null)
						{
							fplist=new ArrayList<>();
						}
						System.out.println("How many food products you want to add in this menu");
						int num = scanner.nextInt();
						for (int i = 1; i <= num; i++) {
//					System.out.println("Enter no."+i+" food product id");
//					int id=scanner.nextInt();
							System.out.println("Enter food product name");
							String name = scanner.next();
							System.out.println("Enter food product type");
							String type = scanner.next();
							System.out.println("Enter about this product");
							String about = scanner.nextLine();
							about = scanner.nextLine();
							System.out.println("Enter availability of this product");
							String availability = scanner.next();
							System.out.println("Enter price of this product");
							double price = scanner.nextDouble();
							FoodProduct foodProduct = new FoodProduct(name, type, about, availability, price, menu);
							fplist.add(foodProduct);
							foodProductService.saveFoodProduct(foodProduct);
						}

						menu.setFoodProducts(fplist);
						menuService.updateMenu(menu);
						System.out.println("=======================================");
						System.out.println("====Food Product Saved successfully====");
						System.out.println("=======================================");
					}
				}
				}
			}
				break;
			case 2: {

				System.out.println(
						"Which Details you want to fetch \n 1.Staff By ID \n 2.Menu \n 3.Branch \n 4.All Staff");
				int ch = scanner.nextInt();
				switch (ch) {
				case 1: {
					System.out.println("Enter User email id you want to fetch");
					String email = scanner.next();
					User user2 = userService.findById(email);
					if (user2 != null) {
						System.out.println("User ID:" + user2.getId());
						System.out.println("User Name:" + user2.getName());
						System.out.println("User Email:" + user2.getEmail());
						System.out.println("User Password:" + user2.getPassword());
						System.out.println("User Role:" + user2.getRole());
					} else {
						System.out.println("===============================");
						System.out.println("======User Not Exist !!======");
						System.out.println("===============================");
					}
				}
					break;
				case 2: {
					System.out.println("Enter Menu id you want to fetch");
					int mid = scanner.nextInt();
					Menu menu = menuService.findById(mid);
					if (menu != null) {
						System.out.println("Menu Id:" + menu.getId());
						List<FoodProduct> fp = menu.getFoodProducts();
						if (fp.size() > 0 && fp != null) {
							for (FoodProduct foodProduct : fp) {
								System.out.println("======Food Product======");
								System.out.println("Food Product Id:" + foodProduct.getId());
								System.out.println("Food Product Name:" + foodProduct.getName());
								System.out.println("Food Product type:" + foodProduct.getType());
								System.out.println("Food Product about:" + foodProduct.getAbout());
								System.out.println("Food Product Price:" + foodProduct.getPrice());
								System.out.println("Food Product availability:" + foodProduct.getAvailability());
							}
						} else {
							System.out.println("=======================================");
							System.out.println("=======No Food Products Added==========");
							System.out.println("=======================================");
						}
					} else {
						System.out.println("=======================================");
						System.out.println("===========Menu Not Exist !!===========");
						System.out.println("=======================================");
					}

				}
					break;
				case 3: {
					System.out.println("Enter Branch id you want to fetch");
					int bid = scanner.nextInt();
					Branch branch = branchService.findById(bid);
					if (branch != null) {
						System.out.println("Branch ID:" + branch.getId());
						System.out.println("Branch Name:" + branch.getName());
						System.out.println("Branch Email:" + branch.getEmail());
						System.out.println("Branch Password:" + branch.getAddress());
						System.out.println("Branch Role:" + branch.getPhoneNumber());
					} else {
						System.out.println("=======================================");
						System.out.println("======Branch Not Exist !!=======");
						System.out.println("=======================================");
					}
				}
					break;
				case 4: {
					List<User> users = userService.getAllStaff();
					if (users != null && users.size() > 0) {
						for (User user2 : users) {
							System.out.println("Staff ID:" + user2.getId());
							System.out.println("Staff Name:" + user2.getName());
							System.out.println("Staff Email:" + user2.getEmail());
							System.out.println("Staff Password:" + user2.getPassword());
							System.out.println("------------------------------");
						}
					} else {
						System.out.println("==================================");
						System.out.println("======No staff data available=====");
						System.out.println("==================================");
					}
				}

				}
			}
				break;
			case 3: {
				System.out.println("Enter which data you want to update \n 1.User \n 2.Food Product \n 3.Branch");
				int ch = scanner.nextInt();
				switch (ch) {
				case 1: {
					System.out.println("Enter User id you want to update");
					String email = scanner.next();
					User user2 = userService.findById(email);
					if (user2 != null) {
						System.out.println("enter user name");
						String name = scanner.next();
						System.out.println("enter user password");
						String password = scanner.next();
						user2.setName(name);
						user2.setPassword(password);
						userService.updateUser(user2);
						System.out.println("==========================");
						System.out.println("=======User Updated=======");
						System.out.println("==========================");
					} else {
						System.out.println("=======================================");
						System.out.println("======User Not Exist !!=========");
						System.out.println("=======================================");
					}
				}
					break;
				case 2: {
					System.out.println("Enter food product id you want to update");
					int fid = scanner.nextInt();
					FoodProduct fp = foodProductService.findById(fid);
					if (fp != null) {
						System.out.println("Enter availability for update");
						String avai = scanner.next();
						System.out.println("Enter price for update");
						double price = scanner.nextDouble();
						fp.setAvailability(avai);
						fp.setPrice(price);
						foodProductService.updateFoodProduct(fp);
						System.out.println("===============================");
						System.out.println("======Food Product Updated=====");
						System.out.println("===============================");
					} else {
						System.out.println("===============================");
						System.out.println("==Food Product Not Exist !!==");
						System.out.println("===============================");
					}

				}
					break;
				case 3: {
					System.out.println("Enter Branch id you want to update");
					int bid = scanner.nextInt();
					Branch branch = branchService.findById(bid);
					if (branch != null) {
						System.out.println("Enter branch name for update");
						String name = scanner.next();
						System.out.println("branch phone number for update");
						long phone = scanner.nextLong();
						branch.setName(name);
						branch.setPhoneNumber(phone);
						branchService.updateBranch(branch);
						System.out.println("===============================");
						System.out.println("======Branch Data Updated======");
						System.out.println("===============================");
					} else {
						System.out.println("===============================");
						System.out.println("======Branch Not Exist !!=====");
						System.out.println("===============================");
					}
				}
				}
			}
				break;
			case 4: {
				System.out.println("Enter which data you want to remove \n 1.User \n 2.Food Product \n ");
				int choice1 = scanner.nextInt();
				switch (choice1) {
				case 1: {
					System.out.println("Enter User id you want to remove");
					String email = scanner.next();
					User user2 = userService.findById(email);
					if (user2 != null && user2.getRole().equals("Staff")) {
						userService.removeUser(user2);
						System.out.println("==========================");
						System.out.println("=======User Removed=======");
						System.out.println("==========================");
					} else {
						System.out.println("===============================");
						System.out.println("========User Not Exist !!======");
						System.out.println("===============================");
					}
				}
					break;
				case 2: {
					System.out.println("Enter food product id you want to remove");
					int fid = scanner.nextInt();
					FoodProduct fp = foodProductService.findById(fid);
					if (fp != null) {
						foodProductService.removeFP(fp);
						System.out.println("===============================");
						System.out.println("======Food Product Removed=====");
						System.out.println("===============================");
					} else {
						System.out.println("===============================");
						System.out.println("====Food Product Not Exist !!====");
						System.out.println("===============================");
					}

				}
					break;
//				case 3: {
//					System.out.println("Enter Branch id you want to remove");
//					int bid = scanner.nextInt();
//					Branch branch = branchService.findById(bid);
//					if (branch != null) {
//
//						System.out.println("===============================");
//						System.out.println("======Branch Data Removed======");
//						System.out.println("===============================");
//					} else {
//						System.out.println("Branch Not Exist !!");
//					}
//				}
//					break;
				default: {
					System.out.println("==============================");
					System.out.println("========Invalid choice========");
					System.out.println("==============================");
				}
				}

			}
				break;
			case 5: {
				exit = false;
				System.out.println("LogOut successfully");
			}
				break;
			default: {
				System.out.println("Invalid choice");
			}
			}
		}

	}

}
