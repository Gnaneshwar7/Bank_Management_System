package com.bank.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.bank.DAO.CustomerDAO;
import com.bank.DAO.TransactionDetailsDAO;
import com.bank.DTO.CustomerDetails;
import com.bank.DTO.TransactionDetails;
import com.bank.Exception.CustomerDataInvalidException;
import com.bank.Exception.DepositAmountInvalidExecption;

public class CustomerService {
	Scanner sc = new Scanner(System.in);
	CustomerDAO customerDAO = new CustomerDAO();
	
	public void customerRegistration() {
		boolean state = true;
		CustomerDetails customer = new CustomerDetails();
		System.out.println("Enter Customer Name");
		String name = sc.next();
		boolean nameStatus = true;
		while(nameStatus)
		{
			try {
				boolean isPres = true;
				for(int i=0;i<=name.length()-1;i++)
				{
					char ch = name.charAt(i);
					if(ch>='A' && ch<='Z' || ch>='a' && ch<='z')
					{
						isPres = true;
					}
					else
					{
						isPres = false;
						break;
					}
				}
				if(isPres)
				{
					customer.setName(name);
					nameStatus = false;
					break;
				}
			}
			catch(CustomerDataInvalidException e)
			{
				System.err.println("Enter the Valid Name");
				name = sc.next();
			}
		}
		System.out.println("Enter Customer EmailId");
		List<CustomerDetails> listCustomerDetails = new ArrayList<CustomerDetails>();
		Iterator itr = listCustomerDetails.iterator();
		String emailId = sc.next();
		while(true)
		{
			try {
				boolean isFound = false;
				for(CustomerDetails c: listCustomerDetails)
				{
					if(c.getEmailId().equals(emailId))
					{
						isFound = true;
						break;
					}
				}
				if(isFound)
				{
					throw new CustomerDataInvalidException("Email Already Exists");
				}
				else {
					customer.setEmailId(emailId);
					break;
				}
			}
			catch(CustomerDataInvalidException e)
			{
				e.printStackTrace();
				System.out.println("Enter Another EmailId");
				emailId=sc.next();
			}
		}
		System.out.println("Enter the Mobile Number");
		state = true;
		while(state)
		{
			try {
				long mobileNumber = sc.nextLong();
				if(mobileNumber >=6000000000l && mobileNumber<=9999999999l)
				{
					customer.setMobileNumber(mobileNumber);
					state = false;
				}
				else
				{
					throw new CustomerDataInvalidException("Invalid Mobile Number");
				}
			}
			catch(CustomerDataInvalidException e)
			{
				System.out.println("Re-Enter the Mobile Number");
			}
			
		}
		
		System.out.println("Enter Customer Aadhar Number");
		state = true;
		while(state)
		{
			try {
				long aadharNumber = sc.nextLong();
				if(aadharNumber>=100000000000l && aadharNumber<=999999999999l) {
					customer.setAadharNumber(aadharNumber);
					state =false;
				}
				else
				{
					throw new CustomerDataInvalidException("Invalid Aadhar Number");
				}
			}
			catch(CustomerDataInvalidException e)
			{
				System.out.println("Re-Enter the Valid Aadhar Number");
			}
		}
		System.out.println("Customer Address");
		String address = sc.next();
		System.out.println("Enter Custommer Gender");
		String gender= sc.next();
		System.out.println("Enter Customer Amount");
		double amount = sc.nextDouble();
		customer.setEmailId(emailId);
		customer.setAddress(address);
		customer.setGender(gender);
		customer.setAmount(amount);
		if(customerDAO.insertCustomerDetails(customer))
		{
			System.out.println("Data Inserted");
		}
		else
		{
			System.out.println("Server Error");
		}
		
	}
	public int pin;
	public void customerLogin()
	{
		System.out.println("Enter the Account Number or EmailId");
		String emailIdOrAccountNumber = sc.next();
		System.out.println("Enter the Pin");
		pin = sc.nextInt();
		CustomerDetails cd=customerDAO.selectCustomerDetailsByUsingEmailOrAccountNumberAndPin(emailIdOrAccountNumber, pin);
		if(cd!=null)
		{
//			System.out.println("Login Successfull");
			boolean status = true;
			while(status)
			{
				String[] captchaData = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","a","b","c","d","e","f",
						"g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","1","2","3","4","5","6","7","8","9","0"};
				String captcha ="";
				for(int i=0;i<4;i++)
				{
					Random r = new Random();
					int captchapick = r.nextInt(captchaData.length);
					captcha+=captchaData[captchapick];
				
					
				}
				System.out.println("Captch: "+captcha);
				System.out.println("Enter the Captcha: ");
				String input = sc.next();
				if(input.equals(captcha))
				{
					
					if(cd.getGender().equalsIgnoreCase("male"))
					{
						System.out.println("Hello \n MR: "+cd.getName());
						customerOperations();
						status=false;
					}
					else
					{
						System.out.println("Hello \n Miss: "+cd.getName());
						customerOperations();
						status = false;
					}
				}
				else
				{
					System.err.println("Re-enter The Captcha");
				}
				
			}
			
		}
		else
		{
			System.out.println("Invalid Credentials");
		}
		
		
	}
	public void customerOperations()
	{
		System.out.println("Enter \n 1.For Credit \n 2.For Debit \n 3.For Check Statement \n 4.For check Balance \n 5.Update Pin \n 6.Close Statement");
		switch(sc.nextInt())
		{
		case 1:
			credit();
			break;
		case 2:
			debit();
			break;
		case 3:
			showStatement();
			break;
		case 4:
//			System.out.println("Balance");
			System.out.println("Enter the Pin Number");
			CustomerDetails cd = customerDAO.showBalance(sc.nextInt());
			System.out.println("Account Holder Name: "+cd.getName());
			System.out.println("Account Balance: "+cd.getAmount());
			break;
		case 5:
			updatingPin();
			break;
		default :
			System.out.println("Enter the Valid Choice");
			break;
		}
	}
	TransactionDetailsDAO transactiondatilsDAO = new TransactionDetailsDAO();
	public void debit() {
//		System.out.println("Enter the Amount to Be Debited");
		System.out.println("Enter Account Number");
		long accountNumber= sc.nextLong();
		System.out.println("Enter the PIN Number");
		int pin = sc.nextInt();
		CustomerDetails cd = customerDAO.selectCustomerDetailsByUsingAccountAndPinNumber(accountNumber,pin);
		if(this.pin==pin)
		{
			if(cd!=null)
			{
				System.out.println("Enter the Amount");
				double amount = sc.nextDouble();
				if(amount>=0 && amount<=cd.getAmount())
				{
					CustomerDetails cud = customerDAO.updatedAmountAfterDebiting(amount, pin);
					double balanceAmount = cd.getAmount()-amount;
					if(cud!=null)
					{
						TransactionDetails transactionDetails = new TransactionDetails();
						transactionDetails.setTransactionType("DEBIT");
						transactionDetails.setTransactionAmount(amount);
						transactionDetails.setBalanceAmount(balanceAmount);
						transactionDetails.setTransactionDate(LocalDate.now());
						transactionDetails.setTransactionTime(LocalTime.now());
						transactionDetails.setCustomerAccountNumber(accountNumber);
						transactionDetails.setTransactionStatus("Transferred");
						System.out.println(transactionDetails);
						transactiondatilsDAO.insertTransactionDetailsIntoDatabase(transactionDetails);
						System.out.println("Amount Debited Successfully");
						System.out.println("Your Bank Balance is: "+cud.getAmount());
						
					}
					else
					{
						System.out.println("Server Error");
					}
				}
				else if(amount>=cd.getAmount())
				{
					System.out.println("Insufficient Amount.....");
				}
				else
				{
					System.out.println("Invalid Amount");
				}
			}
			else {
				System.out.println("Invalid Credentials");
			}
		}
		else
		{
			System.err.println("Enter Your Credentials");
		}
	}
	public long accountNumber;
	public void credit() {
		System.out.println("Enter The Account Number");
		accountNumber = sc.nextLong();
		System.out.println("Enter The Pin Number");
		int pin = sc.nextInt();
		CustomerDetails cd = customerDAO.selectCustomerDetailsByUsingAccountAndPinNumber(accountNumber, pin);
		if(this.pin==pin && this.accountNumber==accountNumber)
		{
			if(cd!=null)
			{
				System.out.println("Enter the Amount to Deposit");
				double depositingAmount = sc.nextDouble();
				if(depositingAmount>0)
				{
					CustomerDetails cud = customerDAO.updateCreditedAmount(depositingAmount, pin, accountNumber);
					double balanceAmount = cud.getAmount();
					if(cud!=null)
					{
						TransactionDetails transactionDetails = new TransactionDetails();
						transactionDetails.setTransactionType("CREDIT");
						transactionDetails.setTransactionAmount(depositingAmount);
						transactionDetails.setBalanceAmount(balanceAmount);
						transactionDetails.setTransactionDate(LocalDate.now());
						transactionDetails.setTransactionTime(LocalTime.now());
						transactionDetails.setCustomerAccountNumber(accountNumber);
						transactionDetails.setTransactionStatus("Deposited");
						//System.out.println(transactionDetails);
						transactiondatilsDAO.insertTransactionDetailsIntoDatabase(transactionDetails);
						System.out.println("Your Amount Is Deposited Successfully");
						System.out.println("Your Account Balance is: "+balanceAmount);
						
					}
					
				}
				else
				{
					throw new DepositAmountInvalidExecption();
				}
			}
			else
			{
				System.out.println("Invalid Credentials");
			}
		}
		else
		{
			System.err.println("Enter your Credentials");
		}
	}
	public void showStatement()
	{
		System.out.println("Enter your Account Number");
		long accountNumber = sc.nextLong();
		System.out.println("Enter the Pin");
		int statementPin = sc.nextInt();
		if(this.pin==statementPin)
		{
			TransactionDetailsDAO transactionDAO = new TransactionDetailsDAO();
			if(transactionDAO.showTransactionDetails(accountNumber)!=null)
			{
				ArrayList<TransactionDetails> statement = transactionDAO.showTransactionDetails(accountNumber);
				for(TransactionDetails t: statement)
				{
					System.out.println("Transaction Type   : "+t.getTransactionType());
					System.out.println("Transaction Amount : "+t.getTransactionAmount());
					System.out.println("Transaction Time   : "+t.getTransactionTime());
					System.out.println("Transaction Date   : "+t.getTransactionDate());
					System.out.println("Balance Amount     : "+t.getBalanceAmount());
					System.out.println("Transaction Status : "+t.getTransactionStatus());
					System.out.println("-------------------------------------");
				}
			}
			else
			{
				System.out.println("No Transactions Present Using These Account Number!!");
			}
		}
		else
		{
			System.err.println("Enter your Credentials!!");
		}
		
	}
	public void updatingPin() {
		System.out.println("Enter your Account Number");
		long accountNumber = sc.nextLong();
		System.out.println("Enter your Old Pin");
		int oldPin = sc.nextInt();
		if(this.pin==oldPin )
		{
			boolean status = true;
			while(status)
			{
				System.out.println("Enter your New Pin");
				int firstPin = sc.nextInt();
				System.out.println("Confirm Your Pin");
				int secondPin = sc.nextInt();
				if(firstPin==secondPin)
				{
					if(customerDAO.updatePinUsingAccountNumber(accountNumber, secondPin))
					{
						System.out.println("Pin Updated Successfully");
						status =false;
					}
				}
				else
				{
					System.err.println("Password DoesNot Matched");
				}
			}

		}
	}
}


