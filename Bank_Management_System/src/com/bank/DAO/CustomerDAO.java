package com.bank.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bank.DTO.CustomerDetails;
import com.bank.util.DatabaseConnection;

public class CustomerDAO {
	private final static String insert = "insert into customer_details(Customer_Name,Customer_EmailId,Customer_MobileNumber,Customer_AadharNumber,Customer_Address,Customer_Amount,Customer_Gender) values(?,?,?,?,?,?,?)";
    private final static String selectCustomerDetails ="select * from customer_details";
    private final static String selectCustomerPendingDetails="select* from customer_details where Customer_Status=?";
    private final static String customerLogin= "select * from customer_details where (Customer_EmailId=? or Customer_AccountNumber=?) and Customer_Pin=?";
    private final static String customerBalance = " select * from customer_details where Customer_Pin=?";
    private final static String debitAmount = "select * from customer_details where Customer_AccountNumber=? and Customer_Pin =? ";
    private final static String updateDebitAmount = " update customer_details set Customer_Amount=Customer_Amount-? where Customer_Pin=?";
    private final static String selectUpdatedAmount = "select * from customer_details where Customer_Pin=? and Customer_AccountNumber=?";
    private final static String updateCreditAmount = "update customer_details set Customer_Amount = Customer_Amount+? where Customer_Pin=? and Customer_AccountNumber=?";
    private final static String updatePin= "update customer_details set Customer_Pin=? where Customer_AccountNumber=?";
    private final static String selectUsingAccountAndPinNumber = " select * from customer_details where Customer_AccountNumber=? and  Customer_Pin=?";
    private final static String updateClosingStatus = "update customer_details set Customer_Status=? where Customer_AccountNumber=?";
//    private final static String selectCustomerToUpdatePin="select Customer_Pin from customer_details where Customer_AccountNumber";
    private final static String closeAccount = "select * from customer_details where Customer_Pin =?";
	public boolean insertCustomerDetails(CustomerDetails customerDetails) {
		try {
			Connection connection = DatabaseConnection.formysqlConnection();
			PreparedStatement preParedStatement = connection.prepareStatement(insert);
			preParedStatement.setString(1, customerDetails.getName());
			preParedStatement.setString(2, customerDetails.getEmailId());
			preParedStatement.setLong(3, customerDetails.getMobileNumber());
			preParedStatement.setLong(4, customerDetails.getAadharNumber());
			preParedStatement.setString(5, customerDetails.getAddress());
			preParedStatement.setDouble(6, customerDetails.getAmount());
			preParedStatement.setString(7, customerDetails.getGender());
			
			int res =preParedStatement.executeUpdate();
			if(res!=0)
			{
				return true;
			}
			else
			{
				return false;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
//		return false;
	}
	public List<CustomerDetails> getAllCustomersDetailsId()
	{
		try {
			Connection connection = DatabaseConnection.formysqlConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(selectCustomerDetails);
			ResultSet resultSet = preparedStatement.executeQuery();
			List<CustomerDetails> listCustomerDetails = new ArrayList<CustomerDetails>();
			if(resultSet.isBeforeFirst())
			{
				while(resultSet.next())
				{
					CustomerDetails customerDetails = new CustomerDetails();
					customerDetails.setName(resultSet.getString("Customer_Name"));
					customerDetails.setEmailId(resultSet.getString("Customer_EmailId"));
					customerDetails.setAadharNumber(resultSet.getLong("Customer_AadharNumber"));
					customerDetails.setMobileNumber(resultSet.getLong("Customer_MobileNumber"));
					listCustomerDetails.add(customerDetails);
					
				}
				return listCustomerDetails;
			}
			else
			{
				return null;
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public List<CustomerDetails> getAllCustomersPendingDetailsId(String status)
	{
		try {
			Connection connection = DatabaseConnection.formysqlConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(selectCustomerPendingDetails);
			preparedStatement.setString(1, status);
			ResultSet resultSet = preparedStatement.executeQuery();
			List<CustomerDetails> listCustomerDetails = new ArrayList<CustomerDetails>();
			if(resultSet.isBeforeFirst())
			{
				while(resultSet.next())
				{
					CustomerDetails customerDetails = new CustomerDetails();
					customerDetails.setName(resultSet.getString("Customer_Name"));
					customerDetails.setEmailId(resultSet.getString("Customer_EmailId"));
					customerDetails.setAadharNumber(resultSet.getLong("Customer_AadharNumber"));
					customerDetails.setMobileNumber(resultSet.getLong("Customer_MobileNumber"));
					listCustomerDetails.add(customerDetails);
					
				}
				return listCustomerDetails;
			}
			else
			{
				return null;
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public void updateAccountAndPinByUsingId(List<CustomerDetails> list)
	{
		String update = "update customer_details set Customer_AccountNumber=? ,Customer_Pin=?,Customer_Status=? where Customer_EmailId=?";
		try {
			Connection connection = DatabaseConnection.formysqlConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(update);
			for(CustomerDetails customerDetails:list)
			{
				preparedStatement.setLong(1, customerDetails.getAccountNumber());
				preparedStatement.setLong(2, customerDetails.getPin());
				preparedStatement.setString(3, "Accepted");
				preparedStatement.setString(4, customerDetails.getEmailId());
				preparedStatement.addBatch();
			}
			System.out.println(preparedStatement);
			int[] batch = preparedStatement.executeBatch();
			System.out.println("Updated");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public CustomerDetails selectCustomerDetailsByUsingEmailOrAccountNumberAndPin(String emailId,int pin)
	{
		try {
			Connection connection = DatabaseConnection.formysqlConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(customerLogin);
			preparedStatement.setString(1, emailId);
			preparedStatement.setString(2, emailId);
			preparedStatement.setInt(3,pin);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next())
			{
				CustomerDetails customerDetails = new CustomerDetails();
				String name =resultSet.getString("Customer_Name");
				String gender = resultSet.getString("Customer_Gender");
				customerDetails.setName(name);
				customerDetails.setGender(gender);
				return customerDetails;
			}

			else
			{
				return null;
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public CustomerDetails showBalance(int pin)
	{
		try {
			Connection connection = DatabaseConnection.formysqlConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(customerBalance);
			preparedStatement.setInt(1,pin);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next())
			{
				CustomerDetails customerDetails = new CustomerDetails();
				double amount = resultSet.getDouble("Customer_Amount");
				String name = resultSet.getString("Customer_Name");
				customerDetails.setAmount(amount);
				customerDetails.setName(name);
				return customerDetails;
			}

			else
			{
				return null;
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public CustomerDetails selectCustomerDetailsByUsingAccountAndPinNumber(long accountNumber,int pin)
	{
		try {
			Connection connection =DatabaseConnection.formysqlConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(debitAmount);
			preparedStatement.setLong(1, accountNumber);
			preparedStatement.setInt(2, pin);
			ResultSet result=preparedStatement.executeQuery();
			if(result.next())
			{
				CustomerDetails customerDetails = new CustomerDetails();
				long accountNumber1 = result.getLong("Customer_AccountNumber");
				double amount = result.getDouble("Customer_Amount");
				String emailId = result.getString("Customer_EmailId");
				customerDetails.setAmount(amount);
				customerDetails.setAccountNumber(accountNumber1);
				customerDetails.setEmailId(emailId);
				return customerDetails;
				
			}
			else
			{	
				return null;
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	public CustomerDetails updatedAmountAfterDebiting(double amount,int pin)
	{
		CustomerDetails customerDetails = new CustomerDetails();
		try {
			Connection connection = DatabaseConnection.formysqlConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(updateDebitAmount);
			preparedStatement.setDouble(1,amount);
			preparedStatement.setInt(2, pin);
			int result =preparedStatement.executeUpdate();
			if(result>0)
			{
				PreparedStatement preStatement = connection.prepareStatement(selectUpdatedAmount);
				preStatement.setInt(1, pin);
				ResultSet resultSet = preStatement.executeQuery();
				if(resultSet.next())
				{
					double updatedAmount = resultSet.getDouble("Customer_Amount");
					customerDetails.setAmount(updatedAmount);
					return customerDetails;
				}
				
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	public CustomerDetails updateCreditedAmount(double amount,int pin,long accountNUmber) {
		try {
			Connection connection=DatabaseConnection.formysqlConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(updateCreditAmount);
			preparedStatement.setDouble(1, amount);
			preparedStatement.setInt(2, pin);
			preparedStatement.setLong(3, accountNUmber);
			int result =preparedStatement.executeUpdate();
			if(result>0)
			{
				PreparedStatement preStatment = connection.prepareStatement(selectUpdatedAmount);
				preStatment.setInt(1, pin);
				preStatment.setLong(2,accountNUmber);
				ResultSet resultSet =preStatment.executeQuery();
				if(resultSet.next())
				{
					CustomerDetails cd = new CustomerDetails();
					double upadtedAmount = resultSet.getDouble("Customer_Amount");
					cd.setAmount(upadtedAmount);
					return cd;
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public boolean updatePinUsingAccountNumber(long accountNumber,int pin)
	{
		try {
			Connection connection = DatabaseConnection.formysqlConnection();
			PreparedStatement preStatement = connection.prepareStatement(updatePin);
			preStatement.setInt(1,pin);
			preStatement.setLong(2, accountNumber);
			int result=preStatement.executeUpdate();
			if(result>0)
			{
				return true;
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	public boolean selectClosingRequestDetails(long accountNumber,int pin,String DeActivate)
	{
		try {
			Connection connection = DatabaseConnection.formysqlConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(selectUsingAccountAndPinNumber);
			preparedStatement.setLong(1, accountNumber);
			preparedStatement.setInt(2, pin);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next())
			{
				PreparedStatement preStatement = connection.prepareStatement(updateClosingStatus);
				preStatement.setString(1, DeActivate);
				int result = preStatement.executeUpdate();
				if(result>0)
				{
					return true;
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public boolean closeAccount(int pin) {
		try {
			Connection connection =DatabaseConnection.formysqlConnection();
			PreparedStatement preStatement = connection.prepareStatement(closeAccount);
			preStatement.setString(1, "Closed");
			preStatement.setInt(2, pin);
			int result = preStatement.executeUpdate();
			if(result>0)
			{
				return true;
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}

