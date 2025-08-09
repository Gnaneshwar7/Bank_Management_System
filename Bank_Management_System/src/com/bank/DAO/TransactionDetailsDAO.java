package com.bank.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import com.bank.DTO.TransactionDetails;
import com.bank.util.DatabaseConnection;

public class TransactionDetailsDAO {
	private final static String insertTransactionDetails = "insert into transaction_details(Transaction_Type,Transaction_Amount,Transaction_TIme,Transaction_Date,Balance_Amount,Transaction_Status,Customer_Account_Number) values(?,?,?,?,?,?,?)";
	private final static String showTransactionStatement = "select * from transaction_details where Customer_Account_Number=?";
	public boolean insertTransactionDetailsIntoDatabase(TransactionDetails transactionDetails) {
	try {
			Connection connection=DatabaseConnection.formysqlConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(insertTransactionDetails);
			preparedStatement.setString(1, transactionDetails.getTransactionType());
			preparedStatement.setDouble(2, transactionDetails.getTransactionAmount());
			preparedStatement.setTime(3, Time.valueOf(transactionDetails.getTransactionTime()));
			preparedStatement.setDate(4, Date.valueOf(transactionDetails.getTransactionDate()));
			preparedStatement.setDouble(5, transactionDetails.getBalanceAmount());
			preparedStatement.setString(6,transactionDetails.getTransactionStatus());
			preparedStatement.setLong(7, transactionDetails.getCustomerAccountNumber());
			int result = preparedStatement.executeUpdate();
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
	public ArrayList<TransactionDetails> showTransactionDetails(long accountNumber) {
		ArrayList<TransactionDetails> transactionList = new ArrayList<TransactionDetails>();
		try {
			Connection connection =DatabaseConnection.formysqlConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(showTransactionStatement);
			preparedStatement.setLong(1, accountNumber);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				TransactionDetails transactionDetails = new TransactionDetails();
				String type=resultSet.getString("Transaction_Type");
				transactionDetails.setTransactionType(type);
				double amount = resultSet.getDouble("Transaction_Amount");
				transactionDetails.setTransactionAmount(amount);
				Time sqlTime = resultSet.getTime("Transaction_TIme");
				if (sqlTime != null) {
				    LocalTime time = sqlTime.toLocalTime();
				    transactionDetails.setTransactionTime(time);
				}
				Date sqlDate = resultSet.getDate("Transaction_Date");
				if (sqlDate != null) {
				    LocalDate date = sqlDate.toLocalDate();
				    transactionDetails.setTransactionDate(date);
				}
				double balanceAmount = resultSet.getDouble("Balance_Amount");
				transactionDetails.setBalanceAmount(balanceAmount);
				String status = resultSet.getString("Transaction_Status");
				transactionDetails.setTransactionStatus(status);
				long customerAccountNumber=resultSet.getLong("Customer_Account_Number");
				transactionDetails.setCustomerAccountNumber(customerAccountNumber);
				transactionList.add(transactionDetails);
			}
			return transactionList;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
