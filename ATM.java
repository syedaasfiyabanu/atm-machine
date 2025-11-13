import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

// ATM.java
// Represents an automated teller machine
public class ATM {
   private boolean userAuthenticated; 
   private int currentAccountNumber; 
   private Screen screen; 
   private Keypad keypad; 
   private CashDispenser cashDispenser; 
   private DepositSlot depositSlot; 
   private BankDatabase bankDatabase; 
   private int AdminCheck;
   private String userinput = "";
   private int position = 0;
   private static ATM uniqueinstance;
   Iterator Users = BankDatabase.createIterator();

   private static final int BALANCE_INQUIRY = 1;
   private static final int WITHDRAWAL = 2;
   private static final int DEPOSIT = 3;
   private static final int TRANSFER = 4;
   private static final int CHANGE_PIN = 5;
   private static final int TRANSACTION_HISTORY = 6;
   private static final int EXPORT_STATEMENT = 7;
   private static final int EXIT = 8;

   public ATM() {
      userAuthenticated = false;
      currentAccountNumber = 0;
      screen = new Screen();
      keypad = new Keypad();
      cashDispenser = new CashDispenser();
      depositSlot = new DepositSlot();
      bankDatabase = new BankDatabase();
   }

   // start ATM
   public void run() {
      startlogin(); 
   }

   // attempts to authenticate user against database
   void startlogin() {
      position = 0;
      screen.createlogin();
      userinput = "";

      authenticate check = new authenticate();
      screen.Mainframe.revalidate();
      screen.Inputfield2.setText("");
      keypad.setbuttons();
      addkeypadlisteners();

      screen.Mainframe.add(keypad.addkeypad(), BorderLayout.CENTER);
      screen.Mainframe.revalidate();
      keypad.BEnter.addActionListener(check);
      screen.Mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      screen.Mainframe.setSize(400, 280);
      screen.Mainframe.setVisible(true);
      screen.Mainframe.revalidate();
   }

   public void authenticateuser(int pin) {
      Account account = bankDatabase.getAccountByPin(pin);

      if (account != null && account.getIsLocked()) {
         screen.messageJLabel3.setText(
            "Account is locked due to too many failed login attempts. Please contact your bank.");
         return;
      }

      userAuthenticated = bankDatabase.authenticateUser(pin);

      if (userAuthenticated) {
         if (account != null) account.resetFailedAttempts();

         int accountNumber = bankDatabase.getaccpin(pin);
         AdminCheck = bankDatabase.getadmin(pin);

         if (AdminCheck == 0) {
            currentAccountNumber = accountNumber;
            screen.Mainframe.getContentPane().removeAll();
            screen.Mainframe.revalidate();
            createmenu();
            screen.Mainframe.add(keypad.addkeypad(), BorderLayout.CENTER);
            screen.Mainframe.revalidate();
         } else {
            createAdminGUI();
            Iterator UserIterator = BankDatabase.createIterator();
            Addcheck check = new Addcheck();
            Deletecheck check2 = new Deletecheck();
            screen.button2.addActionListener(check);
            screen.button3.addActionListener(check2);
         }
      } else {
         if (account != null) {
            account.incrementFailedAttempts();
            int remaining = account.getRemainingAttempts();
            if (account.getIsLocked()) {
               screen.messageJLabel3.setText(
                  "Account locked! Too many failed attempts. Please contact your bank.");
            } else {
               screen.messageJLabel3.setText(
                  "Invalid PIN. " + remaining + " attempt(s) remaining.");
            }
         } else {
            screen.messageJLabel3.setText("Invalid account number or PIN. Please try again.");
         }
      }
   }

   private class authenticate implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         int PIN = Integer.parseInt(screen.Inputfield2.getText());
         authenticateuser(PIN);
      }
   }

   private class Addcheck implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         BankDatabase.Adduser();
      }
   }

   private class Deletecheck implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         BankDatabase.Deleteuser(position);
         position = position - 1;
      }
   }

   public void createmenu() {
      screen.setSize(400, 250);
      balancecheck check1 = new balancecheck();
      Depositcheck check2 = new Depositcheck();
      Withdrawcheck check3 = new Withdrawcheck();
      Transfercheck check4 = new Transfercheck();
      ChangePINcheck check5 = new ChangePINcheck();
      TransactionHistorycheck check6 = new TransactionHistorycheck();
      ExportStatementcheck check7 = new ExportStatementcheck();
      Exitcheck check8 = new Exitcheck();
      screen.Mainframe.getContentPane().removeAll();
      screen.Mainframe.revalidate();

      screen.Mainframe.add(keypad.addkeypad(), BorderLayout.CENTER);
      screen.createmenu();

      Account Account1 = bankDatabase.getAccount(currentAccountNumber);
      screen.messageJLabel.setText("Welcome " + Account1.getUsername() +
                                   " (Account #" + currentAccountNumber + ")");

      keypad.B1.addActionListener(check1);
      keypad.B2.addActionListener(check3);
      keypad.B3.addActionListener(check2);
      keypad.B4.addActionListener(check4);
      keypad.B5.addActionListener(check5);
      keypad.B6.addActionListener(check6);
      keypad.B7.addActionListener(check7);
      keypad.B8.addActionListener(check8);
      screen.Mainframe.revalidate();
   }

   private class balancecheck implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         userinput = "";
         performTransactions(1);
      }
   }

   private class Depositcheck implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         userinput = "";
         performTransactions(3);
      }
   }

   private class Withdrawcheck implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         userinput = "";
         performTransactions(2);
      }
   }

   private class Exitcheck implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         startlogin();
      }
   }

   private class Transfercheck implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         userinput = "";
         performTransactions(4);
      }
   }

   private class ChangePINcheck implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         userinput = "";
         performTransactions(5);
      }
   }

   private class TransactionHistorycheck implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         displayTransactionHistory();
      }
   }

   private class ExportStatementcheck implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         exportAccountStatement();
      }
   }

   private void performTransactions(int a) {
      Transaction currentTransaction = null;
      currentTransaction = createTransaction(a);
      keypad.setbuttons();
      addkeypadlisteners();

      userinput = "";
      screen.Inputfield2.setText(userinput);
      currentTransaction.execute();

      Backcheck Back = new Backcheck();
      screen.Exit.addActionListener(Back);
      screen.Mainframe.revalidate();
   }

   public class Backcheck implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         createmenu();
         screen.Mainframe.add(keypad.addkeypad(), BorderLayout.CENTER);
         screen.Mainframe.revalidate();
         userinput = "";
         screen.Inputfield2.setText(userinput);
      }
   }

   private Transaction createTransaction(int type) {
      Transaction temp = null;
      screen.getContentPane().removeAll();
      screen.revalidate();

      if (type == 1)
         temp = new BalanceInquiry(currentAccountNumber, screen, bankDatabase);
      else if (type == 2)
         temp = new Withdrawal(currentAccountNumber, screen, bankDatabase, keypad, cashDispenser);
      else if (type == 3)
         temp = new Deposit(currentAccountNumber, screen, bankDatabase, keypad, depositSlot);
      else if (type == 4)
         temp = new Transfer(currentAccountNumber, screen, bankDatabase, keypad);
      else if (type == 5)
         temp = new ChangePIN(currentAccountNumber, screen, bankDatabase, keypad);

      return temp;
   }

   private void displayTransactionHistory() {
      screen.createTransactionHistoryGUI();
      java.util.ArrayList<TransactionHistory.TransactionRecord> history =
         TransactionHistory.getRecentHistory(currentAccountNumber, 5);

      if (history.isEmpty()) {
         screen.messageJLabel2.setText("No transaction history available.");
      } else {
         int count = Math.min(history.size(), 4);
         for (int i = 0; i < count; i++) {
            TransactionHistory.TransactionRecord record =
               history.get(history.size() - 1 - i);
            String displayText = record.getTransactionType() + ": $" +
                                 String.format("%.2f", record.getAmount()) + " - " +
                                 record.getStatus();
            if (i == 0) screen.messageJLabel2.setText(displayText);
            else if (i == 1) screen.messageJLabel3.setText(displayText);
            else if (i == 2) screen.messageJLabel4.setText(displayText);
            else if (i == 3) screen.messageJLabel5.setText(displayText);
         }
      }

      Backcheck Back = new Backcheck();
      screen.Exit.addActionListener(Back);
      screen.Mainframe.add(keypad.addkeypad(), BorderLayout.CENTER);
      screen.Mainframe.revalidate();
   }

   private void exportAccountStatement() {
      boolean success = StatementExporter.exportStatement(currentAccountNumber);
      screen.createExportStatementGUI();

      if (success) {
         String filename = "statement_account_" + currentAccountNumber + "_" +
                           new java.text.SimpleDateFormat("yyyyMMdd_HHmmss")
                              .format(new java.util.Date()) + ".txt";
         screen.messageJLabel2.setText("Statement exported successfully!");
         screen.messageJLabel3.setText("File: " + filename);
         screen.messageJLabel4.setText("Saved in project directory.");
      } else {
         screen.messageJLabel2.setText("Error exporting statement.");
         screen.messageJLabel3.setText("Please try again.");
      }

      Backcheck Back = new Backcheck();
      screen.Exit.addActionListener(Back);
      screen.Mainframe.add(keypad.addkeypad(), BorderLayout.CENTER);
      screen.Mainframe.revalidate();
   }

   public void createAdminGUI() {
      screen.Mainframe.getContentPane().removeAll();
      Nextcheck Ncheck = new Nextcheck();
      Prevcheck Pcheck = new Prevcheck();
      Exitcheck check4 = new Exitcheck();
      screen.Mainframe.revalidate();
      screen.createAdminpage();
      screen.button1.addActionListener(Ncheck);
      screen.button4.addActionListener(Pcheck);
      screen.Exit.addActionListener(check4);
      screen.Mainframe.revalidate();
   }

   public void addkeypadlisteners() {
      BCheck BC = new BCheck();
      BClear BC1 = new BClear();
      keypad.B1.addActionListener(BC);
      keypad.B2.addActionListener(BC);
      keypad.B3.addActionListener(BC);
      keypad.B4.addActionListener(BC);
      keypad.B5.addActionListener(BC);
      keypad.B6.addActionListener(BC);
      keypad.B7.addActionListener(BC);
      keypad.B8.addActionListener(BC);
      keypad.B9.addActionListener(BC);
      keypad.B0.addActionListener(BC);
      keypad.BClear.addActionListener(BC1);
   }

   public class BCheck implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         JButton b = (JButton) e.getSource();
         String label = b.getText();
         userinput = userinput + label;
         screen.Inputfield2.setText(userinput);
      }
   }

   public class BClear implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         userinput = "";
         screen.Inputfield2.setText(userinput);
      }
   }

   public class Nextcheck implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         IterateUser(BankDatabase.createIterator());
      }
   }

   public class Prevcheck implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         prevIterateUser(BankDatabase.createIterator());
      }
   }

   public void IterateUser(Iterator Iterator) {
      if (Iterator.hasNext(position)) {
         position = position + 1;
         Account AccountItem = (Account) Iterator.next(position);
         screen.messageJLabel2.setText("Username: " + AccountItem.getUsername());
         screen.messageJLabel3.setText("Available Balance: " + AccountItem.getAvailableBalance());
         screen.messageJLabel4.setText("Total Balance: " + AccountItem.getTotalBalance());
      }
   }

   public void prevIterateUser(Iterator Iterator) {
      if (Iterator.hasPrev(position)) {
         position = position - 1;
         Account AccountItem = (Account) Iterator.next(position);
         screen.messageJLabel2.setText("Username: " + AccountItem.getUsername());
         screen.messageJLabel3.setText("Available Balance: " + AccountItem.getAvailableBalance());
         screen.messageJLabel4.setText("Total Balance: " + AccountItem.getTotalBalance());
      }
   }

   public static ATM getinstance() {
      if (uniqueinstance == null) {
         uniqueinstance = new ATM();
      }
      return uniqueinstance;
   }

   // MAIN METHOD ADDED HERE
   public static void main(String[] args) {
      ATM atm = ATM.getinstance();
      atm.run();
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