package com.bank.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.bank.DAO.AdminDAO;
import com.bank.DAO.CustomerDAO;
import com.bank.DTO.CustomerDetails;

public class AdminService {
	AdminDAO adminDAO = new AdminDAO();
	Scanner sc = new Scanner(System.in);
	CustomerDAO customerDAO = new CustomerDAO();
	List<CustomerDetails> listCustomerDetails =customerDAO.getAllCustomersDetailsId();
	List<CustomerDetails> listCustomerPendingDetails = customerDAO.getAllCustomersPendingDetailsId("pending");
	public void adminLogin() {
		System.out.println("Enter Admin EmailId");
		String emailId = sc.next();
		System.out.println("Enter Admin Password");
		String password = sc.next();
		if(adminDAO.selectAdminDetailsByUsingPasswordAndEmailId(emailId, password))
		{
			System.out.println("Enter \n 1.For All Customer Details \n 2.To Get All Account Request Details \n 3.TO Get All Account Closing Request Details");
			switch(sc.nextInt())
			{
			case 1:
				System.out.println("All Account Details");
				
				for(CustomerDetails d : listCustomerDetails)
				{
					System.out.println(d.getName());
					System.out.println(d.getAadharNumber());
					System.out.println(d.getMobileNumber());
					System.out.println(d.getGender());
					System.out.println(d.getAmount());
					System.out.println(d.getEmailId());
					System.out.println("-----------------------");
				}
				break;
			case 2:
				System.out.println("Pending Customer Details");
				listCustomerPendingDetails.stream().forEach((customer->{
					System.out.println("Customer Name:-"+customer.getName());
					System.out.println("Customer emailID:-"+customer.getEmailId());
					long mobileNumber = customer.getMobileNumber();
					String mb=""+mobileNumber;
					long aadharNumber = customer.getAadharNumber();
					String ad=""+aadharNumber;
					System.out.println("Customer Mobile Number:- "+mb.substring(0,3)+ "*****" + mb.substring(7,10));
					System.out.println("CUstomer Aadhar Number:-"+ad.substring(0,3)+"*****"+ ad.substring(9,12));
					System.out.println("****......****.....****");
				}));
				System.out.println("Enter \n 1.To Generate Account Number for one Person \n 2.To To Select All TO Generate Account Number ");
				switch(sc.nextInt())
				{
				case 1:
					System.out.println("Enter the Account Number");
					String customerEmailId = sc.next();
					for(CustomerDetails cd:listCustomerDetails)
					{
						if(cd.getEmailId().equalsIgnoreCase(customerEmailId))
						{
							List<CustomerDetails> acceptedDetails1 = new ArrayList<CustomerDetails>();
							for(int i=0;i<listCustomerPendingDetails.size();i++)
							{
								CustomerDetails customerDetails1=listCustomerPendingDetails.get(i);
								Random random = new Random();
								int ac = random.nextInt(1000000);
								if(ac<1000000)
								{
									ac+=1000000;
								}
								customerDetails1.setAccountNumber(ac);
								int pin = random.nextInt(10000);
								if(pin<1000) {
									pin+=1000;
								}
								customerDetails1.setPin(pin);
								acceptedDetails1.add(customerDetails1);
							}
							customerDAO.updateAccountAndPinByUsingId(listCustomerPendingDetails);
							System.out.println("Account Number is Generated Successfully");
							break;
						}
		
					}
				case 2:
					List<CustomerDetails> acceptedDetails = new ArrayList<CustomerDetails>();
					for(int i=0;i<listCustomerPendingDetails.size();i++)
					{
						CustomerDetails customerDetails2=listCustomerPendingDetails.get(i);
						Random random = new Random();
						int ac = random.nextInt(1000000);
						if(ac<1000000)
						{
							ac+=1000000;
						}
						customerDetails2.setAccountNumber(ac);
						int pin = random.nextInt(10000);
						if(pin<1000) {
							pin+=1000;
						}
						customerDetails2.setPin(pin);
						acceptedDetails.add(customerDetails2);
					}
					customerDAO.updateAccountAndPinByUsingId(listCustomerPendingDetails);
					System.out.println(acceptedDetails);
					break;
				default:
					System.out.println("Invalid Choice");
					break;
				}
				break;
			case 3:
				System.out.println("All Account Closing Request Details");
				break;
			default: System.out.println("Invalid Request");
			break;
			}
		}
		else
		{
			System.out.println("Invalid Admin credentials/Invalid EmaildId or PassWord");
		}
	}
}
