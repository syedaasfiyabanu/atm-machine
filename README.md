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
  - 1: Balance Inquiry
  - 2: Withdrawal
  - 3: Deposit (amount in cents, e.g., 1234 -> $12.34)
  - 4: Exit/Back

## Troubleshooting
- "javac is not recognized": Ensure JDK 17 is installed and PATH updated. On Windows, open a new terminal after installation or invoke the full path, e.g.:
```powershell
& "C:\Program Files\Microsoft\jdk-17.*\bin\javac.exe" *.java
& "C:\Program Files\Microsoft\jdk-17.*\bin\java.exe" ATMCaseStudy
```

- GUI not visible: Check the taskbar for the "ATM" window or ensure your desktop environment allows Swing windows.

