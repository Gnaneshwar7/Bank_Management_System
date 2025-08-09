package com.bank.Main;

import java.util.Scanner;

import com.bank.Service.AdminService;
import com.bank.Service.CustomerService;

public class BankMainClass {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		CustomerService customerService = new CustomerService();
		AdminService adminService = new AdminService();
		//customerService.customerRegistration();
		System.out.println("Enter \n 1.For Customer Registration \n 2.For Customer Login \n 3.For Admin Login");
		switch(sc.nextInt())
		{
		case 1:
			System.out.println("Custpmer Registration");
			customerService.customerRegistration();
			break;
		case 2:
			System.out.println("Customer Login");
			customerService.customerLogin();
			
			break;
		case 3:
			System.out.println("Admin Login");
			adminService.adminLogin();
			break;
		default:
			System.out.println("Invalid Choice/Invalid request");
			break;
		}
	}
}
