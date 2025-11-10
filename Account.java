

// Account.java
// Represents a bank account

public class Account 
{
   private int accountNumber; // account number
   private int pin; // PIN for authentication
   private double availableBalance; // funds available for withdrawal
   private double totalBalance; // funds available + pending deposits
   private int admin;
   private String username;
   private double dailyWithdrawalAmount; // amount withdrawn today
   private double dailyWithdrawalLimit; // daily withdrawal limit
   private String lastTransactionDate; // last date of transaction (for resetting daily limit)

   // Account constructor initializes attributes
   public Account(String Username, int theAccountNumber, int thePIN, 
      double theAvailableBalance, double theTotalBalance, int isadmin)
   {
	   setUsername(Username);
      setAccountNumber(theAccountNumber);
      setPin(thePIN);
      setAvailableBalance(theAvailableBalance);
      setTotalBalance(theTotalBalance);
      setAdmin(isadmin);
      setDailyWithdrawalLimit(1000.0); // Default daily limit of $1000
      setDailyWithdrawalAmount(0.0);
      setLastTransactionDate(new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()));
   } // end Account constructor

   // determines whether a user-specified PIN matches PIN in Account
   public boolean validatePIN(int userPIN)
   {
      if (userPIN == getPin())
         return true;
      else
         return false;
   } // end method validatePIN
   
   // returns available balance
   public double getAvailableBalance()
   {
      return availableBalance;
   } // end getAvailableBalance

   // returns the total balance
   public double getTotalBalance()
   {
      return totalBalance;
   } // end method getTotalBalance

   // credits an amount to the account
   public void credit(double amount)
   {
      setTotalBalance(getTotalBalance() + amount); // add to total balance
   } // end method credit

   // debits an amount from the account
   public void debit(double amount)
   {
      setAvailableBalance(getAvailableBalance() - amount); // subtract from available balance
      setTotalBalance(getTotalBalance() - amount); // subtract from total balance
   } // end method debit

   // returns account number
   public int getAccountNumber()
   {
      return accountNumber;  
   } // end method getAccountNumber
   
   public int getISadmin()
   {
	   return getAdmin();  
   }
   
   public int GetPin(){
	   return getPin();
   }

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

public void setAccountNumber(int accountNumber) {
	this.accountNumber = accountNumber;
}

public int getPin() {
	return pin;
}

public void setPin(int pin) {
	this.pin = pin;
}

public void setAvailableBalance(double availableBalance) {
	this.availableBalance = availableBalance;
}

public void setTotalBalance(double totalBalance) {
	this.totalBalance = totalBalance;
}

public int getAdmin() {
	return admin;
}

public void setAdmin(int admin) {
	this.admin = admin;
}

public double getDailyWithdrawalAmount() {
	return dailyWithdrawalAmount;
}

public void setDailyWithdrawalAmount(double dailyWithdrawalAmount) {
	this.dailyWithdrawalAmount = dailyWithdrawalAmount;
}

public double getDailyWithdrawalLimit() {
	return dailyWithdrawalLimit;
}

public void setDailyWithdrawalLimit(double dailyWithdrawalLimit) {
	this.dailyWithdrawalLimit = dailyWithdrawalLimit;
}

public String getLastTransactionDate() {
	return lastTransactionDate;
}

public void setLastTransactionDate(String lastTransactionDate) {
	this.lastTransactionDate = lastTransactionDate;
}

// Reset daily withdrawal amount if it's a new day
public void resetDailyWithdrawalIfNewDay() {
	String today = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
	if (!today.equals(getLastTransactionDate())) {
		setDailyWithdrawalAmount(0.0);
		setLastTransactionDate(today);
	}
}

// Check if withdrawal amount exceeds daily limit
public boolean canWithdraw(double amount) {
	resetDailyWithdrawalIfNewDay();
	return (getDailyWithdrawalAmount() + amount) <= getDailyWithdrawalLimit();
}

// Add to daily withdrawal amount
public void addToDailyWithdrawal(double amount) {
	resetDailyWithdrawalIfNewDay();
	setDailyWithdrawalAmount(getDailyWithdrawalAmount() + amount);
}

// Check if balance is low (below $50)
public boolean isLowBalance() {
	return getAvailableBalance() < 50.0;
}
  
   
} // end class Account


/**************************************************************************
 * (C) Copyright 1992-2014 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/