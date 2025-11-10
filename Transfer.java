import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Transfer.java
// Represents a transfer ATM transaction

public class Transfer extends Transaction
{
   private Keypad keypad; // reference to keypad
   private int targetAccountNumber;
   private double transferAmount;
   private String userInput = "";
   private int step = 1; // 1 = enter target account, 2 = enter amount
   private Screen screen;

   // Transfer constructor
   public Transfer(int userAccountNumber, Screen atmScreen, 
      BankDatabase atmBankDatabase, Keypad atmKeypad)
   {
      super(userAccountNumber, atmScreen, atmBankDatabase);
      keypad = atmKeypad;
      screen = atmScreen;
   }

   // perform transaction
   @Override
   public void execute()
   {
      displayTransferGUI();
   }
   
   private void displayTransferGUI()
   {
      Screen screen = getScreen();
      screen.createTransferGUI();
      screen.Mainframe.add(keypad.addkeypad(), BorderLayout.CENTER);
      
      TransferCheck check = new TransferCheck();
      keypad.BEnter.addActionListener(check);
      
      // Add keypad listeners for input
      KeypadInputHandler inputHandler = new KeypadInputHandler();
      keypad.B1.addActionListener(inputHandler);
      keypad.B2.addActionListener(inputHandler);
      keypad.B3.addActionListener(inputHandler);
      keypad.B4.addActionListener(inputHandler);
      keypad.B5.addActionListener(inputHandler);
      keypad.B6.addActionListener(inputHandler);
      keypad.B7.addActionListener(inputHandler);
      keypad.B8.addActionListener(inputHandler);
      keypad.B9.addActionListener(inputHandler);
      keypad.B0.addActionListener(inputHandler);
      keypad.BClear.addActionListener(new ClearHandler());
      
      screen.Mainframe.revalidate();
   }
   
   private void processTransfer()
   {
      Screen screen = getScreen();
      BankDatabase bankDatabase = getBankDatabase();
      
      try
      {
         if (step == 1)
         {
            // First step: get target account number
            if (userInput.isEmpty())
            {
               screen.messageJLabel3.setText("Please enter a target account number.");
               return;
            }
            
            targetAccountNumber = Integer.parseInt(userInput);
            
            // Validate target account exists
            Account targetAccount = bankDatabase.getAccount(targetAccountNumber);
            if (targetAccount == null)
            {
               screen.messageJLabel3.setText("Target account not found. Please try again.");
               userInput = "";
               screen.Inputfield2.setText("");
               return;
            }
            
            if (targetAccountNumber == getAccountNumber())
            {
               screen.messageJLabel3.setText("Cannot transfer to your own account.");
               userInput = "";
               screen.Inputfield2.setText("");
               return;
            }
            
            step = 2;
            userInput = "";
            screen.messageJLabel2.setText("Enter transfer amount (in cents):");
            screen.messageJLabel3.setText("Target: Account #" + targetAccountNumber);
            screen.Inputfield2.setText("");
         }
         else if (step == 2)
         {
            // Second step: get transfer amount
            if (userInput.isEmpty())
            {
               screen.messageJLabel3.setText("Please enter a transfer amount.");
               return;
            }
            
            int amountInCents = Integer.parseInt(userInput);
            transferAmount = amountInCents / 100.0;
            
            if (transferAmount <= 0)
            {
               screen.messageJLabel3.setText("Transfer amount must be greater than $0.00");
               userInput = "";
               screen.Inputfield2.setText("");
               return;
            }
            
            // Check if user has sufficient funds
            double availableBalance = bankDatabase.getAvailableBalance(getAccountNumber());
            if (transferAmount > availableBalance)
            {
               screen.messageJLabel3.setText("Insufficient funds. Available: $" + 
                                             String.format("%.2f", availableBalance));
               userInput = "";
               screen.Inputfield2.setText("");
               return;
            }
            
            // Perform transfer
            bankDatabase.debit(getAccountNumber(), transferAmount);
            bankDatabase.credit(targetAccountNumber, transferAmount);
            
            // Get new balance for receipt
            double newBalance = bankDatabase.getAvailableBalance(getAccountNumber());
            Account account = bankDatabase.getAccount(getAccountNumber());
            
            // Check for low balance warning
            String lowBalanceWarning = "";
            if (account.isLowBalance())
            {
               lowBalanceWarning = "\n⚠ WARNING: Your account balance is low!";
            }
            
            // Generate receipt
            String receipt = generateReceipt("Transfer Out", transferAmount, newBalance, targetAccountNumber);
            
            screen.messageJLabel3.setText("Transfer successful! $" + 
                                          String.format("%.2f", transferAmount) + 
                                          " transferred to Account #" + targetAccountNumber +
                                          lowBalanceWarning + "\n\n" + receipt);
            
            // Log transaction
            TransactionHistory.addTransaction(getAccountNumber(), "Transfer Out", 
                                            transferAmount, "Success");
            TransactionHistory.addTransaction(targetAccountNumber, "Transfer In", 
                                            transferAmount, "Success");
            
            // Return to menu after 3 seconds
            new Thread(new Runnable() {
               public void run() {
                  try {
                     Thread.sleep(3000);
                  } catch (InterruptedException e) {}
                  java.awt.EventQueue.invokeLater(new Runnable() {
                     public void run() {
                        screen.Exit.doClick();
                     }
                  });
               }
            }).start();
         }
      }
      catch (NumberFormatException e)
      {
         screen.messageJLabel3.setText("Invalid input. Please enter numbers only.");
         userInput = "";
         screen.Inputfield2.setText("");
      }
   }
   
   private class TransferCheck implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         processTransfer();
      }
   }
   
   private class KeypadInputHandler implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         javax.swing.JButton b = (javax.swing.JButton)e.getSource();
         String label = b.getText();
         userInput = userInput + label;
         screen.Inputfield2.setText(userInput);
      }
   }
   
   private class ClearHandler implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         userInput = "";
         screen.Inputfield2.setText("");
      }
   }
   
   // Generate a receipt for the transaction
   private String generateReceipt(String transactionType, double amount, double newBalance, int targetAccount)
   {
      String receipt = "=== TRANSACTION RECEIPT ===\n";
      receipt += "Type: " + transactionType + "\n";
      receipt += "Amount: $" + String.format("%.2f", amount) + "\n";
      receipt += "To Account: #" + targetAccount + "\n";
      receipt += "New Balance: $" + String.format("%.2f", newBalance) + "\n";
      receipt += "Date: " + new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()) + "\n";
      receipt += "========================";
      return receipt;
   }
}

