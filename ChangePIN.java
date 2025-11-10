import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// ChangePIN.java
// Represents a PIN change ATM transaction

public class ChangePIN extends Transaction
{
   private Keypad keypad; // reference to keypad
   private int newPIN;
   private int confirmPIN;
   private String userInput = "";
   private int step = 1; // 1 = enter new PIN, 2 = confirm PIN
   private Screen screen;

   // ChangePIN constructor
   public ChangePIN(int userAccountNumber, Screen atmScreen, 
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
      displayPINChangeGUI();
   }
   
   private void displayPINChangeGUI()
   {
      Screen screen = getScreen();
      screen.createChangePINGUI();
      screen.Mainframe.add(keypad.addkeypad(), BorderLayout.CENTER);
      
      PINChangeCheck check = new PINChangeCheck();
      keypad.BEnter.addActionListener(check);
      
      // Add keypad listeners for PIN input
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
   
   private void processPINChange()
   {
      Screen screen = getScreen();
      BankDatabase bankDatabase = getBankDatabase();
      
      try
      {
         if (userInput.isEmpty())
         {
            screen.messageJLabel3.setText("Please enter a PIN.");
            return;
         }
         
         if (step == 1)
         {
            // First step: get new PIN
            if (userInput.length() < 4 || userInput.length() > 6)
            {
               screen.messageJLabel3.setText("PIN must be 4-6 digits. Please try again.");
               userInput = "";
               screen.Inputfield2.setText("");
               return;
            }
            
            newPIN = Integer.parseInt(userInput);
            
            // Check if new PIN is same as current PIN
            Account account = bankDatabase.getAccount(getAccountNumber());
            if (account != null && account.GetPin() == newPIN)
            {
               screen.messageJLabel3.setText("New PIN cannot be the same as current PIN. Please try again.");
               userInput = "";
               screen.Inputfield2.setText("");
               return;
            }
            
            // Validate PIN strength (check for weak PINs)
            if (!isValidPIN(userInput))
            {
               screen.messageJLabel3.setText("PIN is too weak. Avoid sequential or repeating digits. Please try again.");
               userInput = "";
               screen.Inputfield2.setText("");
               return;
            }
            
            step = 2;
            userInput = "";
            screen.messageJLabel2.setText("Please confirm your new PIN:");
            screen.messageJLabel3.setText("");
            screen.Inputfield2.setText("");
         }
         else if (step == 2)
         {
            // Second step: confirm PIN
            confirmPIN = Integer.parseInt(userInput);
            
            if (newPIN == confirmPIN)
            {
               // Update PIN in database
               Account account = bankDatabase.getAccount(getAccountNumber());
               if (account != null)
               {
                  account.setPin(newPIN);
                  screen.messageJLabel3.setText("PIN successfully changed!");
                  TransactionHistory.addTransaction(getAccountNumber(), "PIN Change", 0, "Success");
                  
                  // Return to menu after 2 seconds
                  new Thread(new Runnable() {
                     public void run() {
                        try {
                           Thread.sleep(2000);
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
            else
            {
               screen.messageJLabel3.setText("PINs do not match. Please try again.");
               step = 1;
               userInput = "";
               screen.Inputfield2.setText("");
               screen.messageJLabel2.setText("Enter your new PIN (4-6 digits):");
            }
         }
      }
      catch (NumberFormatException e)
      {
         screen.messageJLabel3.setText("Invalid PIN format. Please enter numbers only.");
         userInput = "";
         screen.Inputfield2.setText("");
      }
   }
   
   private class PINChangeCheck implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         processPINChange();
      }
   }
   
   private class KeypadInputHandler implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         javax.swing.JButton b = (javax.swing.JButton)e.getSource();
         String label = b.getText();
         if (userInput.length() < 6)
         {
            userInput = userInput + label;
            screen.Inputfield2.setText(userInput);
         }
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
   
   // Validate PIN strength - check for weak patterns
   private boolean isValidPIN(String pin)
   {
      // Check for all same digits (e.g., 1111, 2222)
      boolean allSame = true;
      for (int i = 1; i < pin.length(); i++)
      {
         if (pin.charAt(i) != pin.charAt(0))
         {
            allSame = false;
            break;
         }
      }
      if (allSame)
         return false;
      
      // Check for sequential digits (e.g., 1234, 4321)
      boolean sequential = true;
      boolean reverseSequential = true;
      for (int i = 1; i < pin.length(); i++)
      {
         if (pin.charAt(i) != pin.charAt(i-1) + 1)
            sequential = false;
         if (pin.charAt(i) != pin.charAt(i-1) - 1)
            reverseSequential = false;
      }
      if (sequential || reverseSequential)
         return false;
      
      return true;
   }
}

