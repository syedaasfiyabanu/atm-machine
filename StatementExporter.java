import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

// StatementExporter.java
// Utility class for exporting account statements to file

public class StatementExporter
{
   // Export transaction history to a text file
   public static boolean exportStatement(int accountNumber, String filename)
   {
      try
      {
         ArrayList<TransactionHistory.TransactionRecord> history = 
            TransactionHistory.getAccountHistory(accountNumber);
         
         FileWriter writer = new FileWriter(filename);
         
         // Write header
         writer.write("========================================\n");
         writer.write("ACCOUNT STATEMENT\n");
         writer.write("Account Number: " + accountNumber + "\n");
         writer.write("Generated: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\n");
         writer.write("========================================\n\n");
         
         if (history.isEmpty())
         {
            writer.write("No transactions found.\n");
         }
         else
         {
            writer.write("Transaction History:\n");
            writer.write("----------------------------------------\n");
            
            for (TransactionHistory.TransactionRecord record : history)
            {
               writer.write(String.format("[%s] %s: $%.2f - %s\n",
                  record.getTimestamp(),
                  record.getTransactionType(),
                  record.getAmount(),
                  record.getStatus()));
            }
         }
         
         writer.write("\n========================================\n");
         writer.write("End of Statement\n");
         writer.write("========================================\n");
         
         writer.close();
         return true;
      }
      catch (IOException e)
      {
         System.err.println("Error exporting statement: " + e.getMessage());
         return false;
      }
   }
   
   // Export transaction history with a default filename
   public static boolean exportStatement(int accountNumber)
   {
      String filename = "statement_account_" + accountNumber + "_" + 
                       new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".txt";
      return exportStatement(accountNumber, filename);
   }
}

