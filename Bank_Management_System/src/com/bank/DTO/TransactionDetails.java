package com.bank.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

public class TransactionDetails {
	private int transactionId;
	private String transactionType;
	private double transactionAmount;
	private LocalDate transactionDate;
	private LocalTime transactionTime;
	private double balanceAmount;
	private String transactionStatus;
	private long customerAccountNumber;
	public TransactionDetails() {}
	public TransactionDetails(int transactionId, String transactionType, double transactionAmount,
			LocalDate transactionDate, LocalTime transactionTime, double balanceAmount, String transactionStatus,
			long customerAccountNumber) {
		super();
		this.transactionId = transactionId;
		this.transactionType = transactionType;
		this.transactionAmount = transactionAmount;
		this.transactionDate = transactionDate;
		this.transactionTime = transactionTime;
		this.balanceAmount = balanceAmount;
		this.transactionStatus = transactionStatus;
		this.customerAccountNumber = customerAccountNumber;
	}
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public double getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public LocalDate getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}
	public LocalTime getTransactionTime() {
		return transactionTime;
	}
	public void setTransactionTime(LocalTime transactionTime) {
		this.transactionTime = transactionTime;
	}
	public double getBalanceAmount() {
		return balanceAmount;
	}
	public void setBalanceAmount(double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	public String getTransactionStatus() {
		return transactionStatus;
	}
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	public long getCustomerAccountNumber() {
		return customerAccountNumber;
	}
	public void setCustomerAccountNumber(long customerAccountNumber) {
		this.customerAccountNumber = customerAccountNumber;
	}
	@Override
	public String toString() {
		return "TransactionDetails \n transactionId=" + transactionId +"\n"+ ", transactionType=" + transactionType
				+ "\n"+", transactionAmount=" + transactionAmount + "\n"+", transactionDate=" + transactionDate
				+ "\n"+", transactionTime=" + transactionTime + "\n"+", balanceAmount=" + balanceAmount + "\n"+", transactionStatus="
				+ transactionStatus + "\n"+", customerAccountNumber=" + customerAccountNumber ;
	}
	
}
