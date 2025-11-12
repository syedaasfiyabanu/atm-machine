import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Withdrawal.java
// Represents a withdrawal ATM transaction

public class Withdrawal extends Transaction
{
   private int amount; // amount to withdraw
   private Keypad keypad; // reference to keypad
   private CashDispenser cashDispenser; // reference to cash dispenser

   // constant corresponding to menu option to cancel
   private final static int CANCELED = 6;

   // Withdrawal constructor
   public Withdrawal(int userAccountNumber, Screen atmScreen, 
      BankDatabase atmBankDatabase, Keypad atmKeypad, 
      CashDispenser atmCashDispenser)
   {
      // initialize superclass variables
      super(userAccountNumber, atmScreen, atmBankDatabase);
      
      // initialize references to keypad and cash dispenser
      keypad = atmKeypad;
      cashDispenser = atmCashDispenser;
   } // end Withdrawal constructor

   // perform transaction
   @Override
   public void execute()
   {
       // amount available for withdrawal

      // get references to bank database and screen
      

      // loop until cash is dispensed or the user cancels
      displayMenuOfAmounts();
   }
     public void transaction(int amount){
    	 BankDatabase bankDatabase = getBankDatabase();
         Screen screen = getScreen();
    	 boolean cashDispensed = false; // cash was not dispensed yet
         double availableBalance;
         Account account = bankDatabase.getAccount(getAccountNumber());
         
            // get available balance of account involved
            availableBalance = 
               bankDatabase.getAvailableBalance(getAccountNumber());
      
            // Check maximum transaction amount
            if (amount > Account.getMaxTransactionAmount())
            {
               screen.messageJLabel7.setText(
                  "\nTransaction amount exceeds maximum limit." +
                  "\nMaximum single transaction: $" + String.format("%.2f", Account.getMaxTransactionAmount()) +
                  "\n\nPlease choose a smaller amount.");
               TransactionHistory.addTransaction(getAccountNumber(), "Withdrawal", amount, "Failed - Exceeds max transaction amount");
               return;
            }
            
            // Check daily withdrawal limit first
            if (!account.canWithdraw(amount))
            {
               double remainingLimit = account.getDailyWithdrawalLimit() - account.getDailyWithdrawalAmount();
               screen.messageJLabel7.setText(
                  "\nDaily withdrawal limit exceeded." +
                  "\nRemaining daily limit: $" + String.format("%.2f", remainingLimit) +
                  "\n\nPlease choose a smaller amount.");
               TransactionHistory.addTransaction(getAccountNumber(), "Withdrawal", amount, "Failed - Daily limit exceeded");
               return;
            }
            
            // Check minimum balance requirement
            if (!account.hasMinimumBalance(amount))
            {
               screen.messageJLabel7.setText(
                  "\nTransaction would violate minimum balance requirement." +
                  "\nMinimum balance required: $" + String.format("%.2f", Account.getMinimumBalance()) +
                  "\n\nPlease choose a smaller amount.");
               TransactionHistory.addTransaction(getAccountNumber(), "Withdrawal", amount, "Failed - Would violate minimum balance");
               return;
            }
            
            // check whether the user has enough money in the account 
            if (amount <= availableBalance)
            {   
               // check whether the cash dispenser has enough money
               if (cashDispenser.isSufficientCashAvailable(amount))
               {
                  // update the account involved to reflect the withdrawal
                  bankDatabase.debit(getAccountNumber(), amount);
                  account.addToDailyWithdrawal(amount);
                  
                  cashDispenser.dispenseCash(amount); // dispense cash
                  cashDispensed = true; // cash was dispensed

                  // Check for low balance warning
                  String lowBalanceWarning = "";
                  if (account.isLowBalance())
                  {
                     lowBalanceWarning = "\nWARNING: Your account balance is low!";
                  }
                  
                  // instruct user to take cash
                  screen.messageJLabel7.setText("\nYour cash has been" +
                     " dispensed. Please take your cash now." + lowBalanceWarning +
                     "\n\n" + generateReceipt("Withdrawal", amount, availableBalance - amount));
                  
                  // Log transaction
                  TransactionHistory.addTransaction(getAccountNumber(), "Withdrawal", amount, "Success");
               } // end if
               else // cash dispenser does not have enough cash
            	   screen.messageJLabel7.setText(
                     "\nInsufficient cash available in the ATM." +
                     "\n\nPlease choose a smaller amount.");
                  TransactionHistory.addTransaction(getAccountNumber(), "Withdrawal", amount, "Failed - Insufficient ATM cash");
            } // end if
            else // not enough money available in user's account
            {
               screen.messageJLabel7.setText(
                  "\nInsufficient funds in your account." +
                  "\n\nPlease choose a smaller amount.");
               TransactionHistory.addTransaction(getAccountNumber(), "Withdrawal", amount, "Failed - Insufficient funds"); 
            } // end else
         } // end if
         // end else
     

    // end method execute

   // display a menu of withdrawal amounts and the option to cancel;
   // return the chosen amount or 0 if the user chooses to cancel
   private void displayMenuOfAmounts()
   {
	   
      int userChoice = 0; // local variable to store return value

      Screen screen = getScreen(); // get screen reference
      screen.createWithdrawGUI();
      screen.Mainframe.add( keypad.addkeypad(), BorderLayout.CENTER);
      withdraw1 check1 = new withdraw1();
      withdraw2 check2 = new withdraw2();
      withdraw3 check3 = new withdraw3();
      withdraw4 check4 = new withdraw4();
      withdraw5 check5 = new withdraw5();
      Keypad.B1.addActionListener(check1);
      Keypad.B2.addActionListener(check2);
      Keypad.B3.addActionListener(check3);
      Keypad.B4.addActionListener(check4);
      Keypad.B5.addActionListener(check5);
      
      
      
      screen.Mainframe.revalidate();
   }
   
   public class withdraw1 implements ActionListener
   {
      public void actionPerformed( ActionEvent e )
      {
        transaction(20);
      }
   }
   public class withdraw2 implements ActionListener
   {
      public void actionPerformed( ActionEvent e )
      {
        transaction(40);
      }
   }
   public class withdraw3 implements ActionListener
   {
      public void actionPerformed( ActionEvent e )
      {
        transaction(60);
      }
   }
   public class withdraw4 implements ActionListener
   {
      public void actionPerformed( ActionEvent e )
      {
        transaction(100);
      }
   }
   public class withdraw5 implements ActionListener
   {
      public void actionPerformed( ActionEvent e )
      {
        transaction(200);
      }
   }
   
   // Generate a receipt for the transaction
   private String generateReceipt(String transactionType, double amount, double newBalance)
   {
      String receipt = "=== TRANSACTION RECEIPT ===\n";
      receipt += "Type: " + transactionType + "\n";
      receipt += "Amount: $" + String.format("%.2f", amount) + "\n";
      receipt += "New Balance: $" + String.format("%.2f", newBalance) + "\n";
      receipt += "Date: " + new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()) + "\n";
      receipt += "========================";
      return receipt;
   }
} 




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