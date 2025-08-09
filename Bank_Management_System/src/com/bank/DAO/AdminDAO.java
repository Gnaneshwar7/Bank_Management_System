package com.bank.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bank.util.DatabaseConnection;

public class AdminDAO {
	private static final String admin_login="select * from admin_details where Admin_EmailId =? and Admin_Password=?";
	public boolean selectAdminDetailsByUsingPasswordAndEmailId(String emailId,String password)
	{
		try {
			Connection connection = DatabaseConnection.formysqlConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(admin_login);
			preparedStatement.setString(1, emailId);
			preparedStatement.setString(2, password);
			ResultSet resultSet =preparedStatement.executeQuery();
			if(resultSet.isBeforeFirst())
			{
				return true;
			}
			else
			{
				return false;
			}
			
		} catch (ClassNotFoundException |SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}

}
