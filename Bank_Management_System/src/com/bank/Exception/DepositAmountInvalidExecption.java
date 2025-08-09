package com.bank.Exception;

public class DepositAmountInvalidExecption extends RuntimeException{
	@Override
	public String toString() {
		return getClass()+" In-Valid Amount !! The Amount Should Be Greated Than Zero";
	}
}
