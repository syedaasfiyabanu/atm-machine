# ATM-Machine

ATM machine written in Java (Swing UI).

## Prerequisites
- Java JDK 17+ (javac/java on PATH)

### Install JDK 17
- Windows (PowerShell):
```powershell
winget install --id Microsoft.OpenJDK.17 -e --silent --accept-package-agreements --accept-source-agreements
```

- macOS (Homebrew):
```bash
brew install openjdk@17
```

- Linux (Debian/Ubuntu):
```bash
sudo apt update && sudo apt install -y openjdk-17-jdk
```

Verify:
```bash
java -version
javac -version
```

## Build
From the project root directory (`ATM-Machine`):
```bash
javac *.java
```
This compiles all sources and produces `.class` files in the same folder.

## Run
From the same directory:
```bash
java ATMCaseStudy
```
This launches the Swing GUI window titled "ATM".

## Using the App
- Enter PIN using the on-screen keypad, then press Enter.
- Sample PINs:
  - Customers: 11111, 22222, 33333
  - Admin: 0
- After login:
  - 1: Balance Inquiry - View your account balance
  - 2: Withdrawal - Withdraw cash (options: $20, $40, $60, $100, $200)
  - 3: Deposit - Deposit money (amount in cents, e.g., 1234 -> $12.34)
  - 4: Transfer - Transfer money to another account
  - 5: Change PIN - Change your PIN (4-6 digits)
  - 6: Transaction History - View your recent transaction history
  - 7: Exit/Back - Logout and return to login screen

## New Features
- **Transaction History**: All transactions are logged and can be viewed in the Transaction History menu
- **PIN Change**: Users can securely change their PIN with confirmation
- **Transfer Money**: Transfer funds between accounts with validation
- **Account Number Display**: Account number is now shown in the welcome message
- **Enhanced Logging**: All transactions (successful and failed) are logged with timestamps

## Troubleshooting
- "javac is not recognized": Ensure JDK 17 is installed and PATH updated. On Windows, open a new terminal after installation or invoke the full path, e.g.:
```powershell
& "C:\Program Files\Microsoft\jdk-17.*\bin\javac.exe" *.java
& "C:\Program Files\Microsoft\jdk-17.*\bin\java.exe" ATMCaseStudy
```

- GUI not visible: Check the taskbar for the "ATM" window or ensure your desktop environment allows Swing windows.

