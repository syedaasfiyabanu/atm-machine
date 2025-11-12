# ATM Machine System

**Last updated: 2025-11-12**

This desktop application is built using Java Swing for the GUI. The project uses Java Servlets patterns and follows Object-Oriented Design Principles. Database: In-Memory (ArrayList-based).

## 🎉 New Features Added!

This ATM system has been enhanced with several powerful security and user experience features:

### ✨ What's New:

**Security Enhancements (Latest - Nov 12, 2025)**
- **Account Lockout Security** - Accounts are locked after 3 failed PIN attempts
- **Minimum Balance Requirement** - Accounts must maintain $10.00 minimum balance
- **Maximum Transaction Limits** - Single transaction limit of $5,000 to prevent fraud
- **Enhanced Error Handling** - Improved input validation and user-friendly error messages

**Transaction Features (Nov 10, 2025)**
- **Daily Withdrawal Limit** - $1,000 daily withdrawal limit per account
- **Low Balance Warning** - Alerts when balance falls below $50.00
- **Transaction Receipts** - Detailed receipts for all transactions (withdrawal, deposit, transfer)
- **Account Statement Export** - Export complete transaction history to text file

**Core Features (Nov 7, 2025)**
- **Transaction History** - View complete transaction history with timestamps
- **PIN Change** - Secure PIN change with strength validation
- **Money Transfer** - Transfer funds between accounts with validation
- **Account Management** - Enhanced account display and management

## Prerequisites

- **Java JDK 17+** (javac/java on PATH)

### Install JDK 17

**Windows (PowerShell):**
```powershell
winget install --id Microsoft.OpenJDK.17 -e --silent --accept-package-agreements --accept-source-agreements
```

**macOS (Homebrew):**
```bash
brew install openjdk@17
```

**Linux (Debian/Ubuntu):**
```bash
sudo apt update && sudo apt install -y openjdk-17-jdk
```

**Verify Installation:**
```bash
java -version
javac -version
```

## Installation Instructions

1. **Clone or Download the Repository**
   ```bash
   git clone https://github.com/syedaasfiyabanu/atm-machine.git
   cd atm-machine
   ```

2. **Compile the Project**
   From the project root directory:
   ```bash
   javac *.java
   ```
   This compiles all source files and produces `.class` files in the same folder.

3. **Run the Application**
   ```bash
   java ATMCaseStudy
   ```
   This launches the Swing GUI window titled "ATM".

## Project Configuration

The project uses an in-memory database (ArrayList-based) with pre-configured accounts:

**Default Accounts:**
- Customer 1: PIN `11111`, Account #12345, Balance: $1000.00
- Customer 2: PIN `22222`, Account #98765, Balance: $200.00
- Customer 3: PIN `33333`, Account #19234, Balance: $200.00
- Admin: PIN `0`, Account #99999 (Admin access)

To modify accounts, edit the `BankDatabase.java` constructor.

## Quick Start Guide

### Login
1. Launch the application using `java ATMCaseStudy`
2. Enter your PIN using the on-screen keypad
3. Press **Enter** to authenticate

### After Login - Main Menu Options:

1. **Balance Inquiry** - View your account balance (with low balance warning if applicable)
2. **Withdrawal** - Withdraw cash (options: $20, $40, $60, $100, $200). Daily limit: $1,000
3. **Deposit** - Deposit money (enter amount in cents, e.g., 1234 = $12.34)
4. **Transfer** - Transfer money to another account
5. **Change PIN** - Change your PIN (4-6 digits, with strength validation)
6. **Transaction History** - View your recent transaction history (last 5 transactions)
7. **Export Statement** - Export your complete transaction history to a text file
8. **Exit** - Logout and return to login screen

### Admin Features (PIN: 0)
- View all user accounts
- Add new accounts
- Delete accounts
- Navigate through accounts using Next/Previous buttons

## Features Overview

### Core Banking Features
- **Account Management** - Multiple account support with PIN authentication
- **Cash Withdrawal** - Multiple withdrawal amounts with ATM cash dispenser simulation
- **Deposit** - Deposit money with envelope simulation
- **Balance Inquiry** - Real-time account balance checking

### Security Features
- **Account Lockout** - Automatic lockout after 3 failed PIN attempts
- **PIN Management** - Secure PIN change with strength validation
- **Transaction Limits** - Daily withdrawal limits and maximum transaction amounts
- **Balance Protection** - Minimum balance requirement ($10.00)

### Transaction Management
- **Transaction History** - Complete transaction log with timestamps
- **Transaction Receipts** - Detailed receipts for all transactions
- **Statement Export** - Export transaction history to text file
- **Low Balance Alerts** - Automatic warnings when balance is low

### User Experience
- **Swing GUI** - Modern, user-friendly graphical interface
- **On-Screen Keypad** - Easy number input with clear button
- **Real-time Feedback** - Instant transaction confirmations and error messages
- **Transaction Tracking** - View all past transactions with status

### Advanced Features
- **Admin Panel** - Complete account management for administrators
- **Money Transfer** - Transfer funds between accounts with validation
- **Daily Limits** - Automatic daily withdrawal limit tracking and reset
- **Input Validation** - Comprehensive validation for all user inputs

## Project Structure

```
atm-machine/
├── ATMCaseStudy.java          # Main entry point
├── ATM.java                   # Core ATM logic and GUI controller
├── Account.java               # Account model with security features
├── BankDatabase.java          # In-memory database
├── Transaction.java           # Base transaction class
├── BalanceInquiry.java        # Balance inquiry transaction
├── Withdrawal.java            # Withdrawal transaction
├── Deposit.java               # Deposit transaction
├── Transfer.java              # Transfer transaction
├── ChangePIN.java             # PIN change transaction
├── TransactionHistory.java   # Transaction history management
├── StatementExporter.java     # Statement export utility
├── Screen.java                # GUI screen components
├── Keypad.java                # On-screen keypad
├── CashDispenser.java         # Cash dispenser simulation
├── DepositSlot.java           # Deposit slot simulation
├── AccountFactory.java        # Factory pattern implementation
├── AccountIterator.java       # Iterator pattern implementation
└── README.md                  # This file
```

## Design Patterns Used

- **Singleton Pattern** - ATM instance management
- **Factory Pattern** - Account creation
- **Iterator Pattern** - Account iteration for admin panel
- **Strategy Pattern** - Different transaction types

## Troubleshooting

### "javac is not recognized"
Ensure JDK 17 is installed and PATH is updated. On Windows, open a new terminal after installation or use the full path:
```powershell
& "C:\Program Files\Microsoft\jdk-17.*\bin\javac.exe" *.java
& "C:\Program Files\Microsoft\jdk-17.*\bin\java.exe" ATMCaseStudy
```

### GUI not visible
- Check the taskbar for the "ATM" window
- Ensure your desktop environment supports Swing windows
- Try running with administrator privileges if needed

### Account locked
- Account locks after 3 failed PIN attempts
- Contact administrator to reset (or modify code to reset in BankDatabase)

### Transaction limits exceeded
- Daily withdrawal limit: $1,000 per account
- Maximum single transaction: $5,000
- Minimum balance must be maintained: $10.00

## Development History

- **2025-11-12**: Added security features (lockout, limits, minimum balance)
- **2025-11-10**: Enhanced transaction features (receipts, daily limits, exports)
- **2025-11-07**: Core features (transaction history, PIN change, transfers)
- **2025-11-03**: Project initialization and basic ATM functionality

## Contributors

- **syedaasfiyabanu** - Team Lead
- **Rachana181818** - Collaborator
- **yashaswinmbsc24** - Collaborator

## License

This project is part of an academic case study.

---

**Note**: This is an educational project demonstrating ATM system functionality using Java Swing. For production use, implement proper database connectivity and enhanced security measures.
