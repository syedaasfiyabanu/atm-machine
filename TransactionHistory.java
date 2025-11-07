import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;

// TransactionHistory.java
// Represents a transaction history log for ATM transactions

public class TransactionHistory
{
   private static ArrayList<TransactionRecord> history = new ArrayList<TransactionRecord>();
   
   // TransactionRecord inner class to store transaction details
   public static class TransactionRecord
   {
      private int accountNumber;
      private String transactionType;
      private double amount;
      private String timestamp;
      private String status;
      
      public TransactionRecord(int accountNumber, String transactionType, 
                              double amount, String status)
      {
         this.accountNumber = accountNumber;
         this.transactionType = transactionType;
         this.amount = amount;
         this.status = status;
         this.timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
      }
      
      public int getAccountNumber() { return accountNumber; }
      public String getTransactionType() { return transactionType; }
      public double getAmount() { return amount; }
      public String getTimestamp() { return timestamp; }
      public String getStatus() { return status; }
      
      @Override
      public String toString()
      {
         return String.format("[%s] Account: %d | Type: %s | Amount: $%.2f | Status: %s",
                              timestamp, accountNumber, transactionType, amount, status);
      }
   }
   
   // Add a transaction to history
   public static void addTransaction(int accountNumber, String transactionType, 
                                     double amount, String status)
   {
      TransactionRecord record = new TransactionRecord(accountNumber, transactionType, amount, status);
      history.add(record);
   }
   
   // Get transaction history for a specific account
   public static ArrayList<TransactionRecord> getAccountHistory(int accountNumber)
   {
      ArrayList<TransactionRecord> accountHistory = new ArrayList<TransactionRecord>();
      for (TransactionRecord record : history)
      {
         if (record.getAccountNumber() == accountNumber)
         {
            accountHistory.add(record);
         }
      }
      return accountHistory;
   }
   
   // Get all transaction history
   public static ArrayList<TransactionRecord> getAllHistory()
   {
      return new ArrayList<TransactionRecord>(history);
   }
   
   // Get last N transactions for an account
   public static ArrayList<TransactionRecord> getRecentHistory(int accountNumber, int count)
   {
      ArrayList<TransactionRecord> accountHistory = getAccountHistory(accountNumber);
      int size = accountHistory.size();
      if (size <= count)
         return accountHistory;
      
      return new ArrayList<TransactionRecord>(
         accountHistory.subList(size - count, size));
   }
}

