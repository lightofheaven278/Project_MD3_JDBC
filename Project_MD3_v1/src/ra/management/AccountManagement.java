package ra.management;

import ra.business.AccountBusiness;
import ra.business.EmployeeBusiness;
import ra.entity.Account;
import ra.entity.Employee;

import java.util.List;
import java.util.Scanner;

public class AccountManagement {
    public static Scanner input = new Scanner(System.in);
    public static List<Account> accountList = AccountBusiness.getAllAccounts();

    public static void accountManagementMenu() {
        boolean checkOutAccount = true;
        do {
            accountList = AccountBusiness.getAllAccounts();
            System.out.println("-----------------------ACCOUNT MANAGEMENT----------------------");
            System.out.println("1. Display all accounts");
            System.out.println("2. Add new account");
            System.out.println("3. Update account status");
            System.out.println("4. Search account by username");
            System.out.println("5. Exit");
            System.out.println("Please input the number representing function you wanna choose(1~5):");
            try {
                int choice = Integer.parseInt(input.nextLine());
                switch (choice) {
                    case 1:
                        AccountManagement.displayAllAccounts();
                        break;
                    case 2:
                        AccountManagement.addNewAccount();
                        break;
                    case 3:
                        AccountManagement.updateAccountStatus();
                        break;
                    case 4:
                        AccountManagement.searchAccount();
                        break;
                    case 5:
                        checkOutAccount = false;
                        break;
                    default:
                        System.err.println("The inputted number is out of scope(1~5)!");
                }
            } catch (NumberFormatException ex) {
                System.err.println("The inputted choice does not have an integer format! Please try again! " +
                        ex.getMessage());
            } catch (Exception e) {
                System.err.println("Some errs occur while inputting number of choice! " + e.getMessage());
            }
        } while (checkOutAccount);
    }

    public static void displayAllAccounts() {
        System.out.printf("%-15s%-25s%-25s%-15s%-15s%-15s\n", "Account ID", "User Name", "Password", "Permission",
                "Employee ID", "Account Status");
        accountList.stream().forEach(Account::displayData);
    }

    public static void addNewAccount() {
        Account account = new Account();
        List<Employee> originalEmployeeList = EmployeeBusiness.getAllEmployeesInfo();
        System.out.println("------------------Employee List----------------");
        for (int i = 0; i < originalEmployeeList.size(); i++) {
            System.out.println((i + 1) + ". " + originalEmployeeList.get(i).getEmpName());
        }
        System.out.println("Please input the number representing employee you wanna create an account for:");
        try {
            int choiceEmp = Integer.parseInt(input.nextLine());
            account.setEmpId(originalEmployeeList.get(choiceEmp - 1).getEmpId());
            account.inputData(input, accountList);
            boolean addNewAccountResult = AccountBusiness.addNewAccount(account);
            if (addNewAccountResult) {
                System.out.println("The new account has been updated successfully!");
            } else {
                System.err.println("Cannot add new account due to unexpected errs!");
            }
        } catch (NumberFormatException ex) {
            System.err.println("The inputted number does not have an integer format! Please try again! " +
                    ex.getMessage());
        } catch (Exception e) {
            System.err.println("Some errs occur while inputting number of choice! " + e.getMessage());
        }
    }

    public static void updateAccountStatus() {
        System.out.println("Please input username of account you wanna update status:");
        String username = input.nextLine();
        Account account = AccountBusiness.getAccountByUsername(username);
        if (account != null) {
            account.setAccStatus(account.validateAccStatus(input));
            boolean updateAccountStatusResult = AccountBusiness.updateAccountStatus(account);
            if (updateAccountStatusResult) {
                System.out.println("The status of account has been updated successfully!");
            }
        } else {
            System.err.println("The inputted username does not exist!");
        }
    }

    public static void searchAccount() {
        System.out.println("Please input keyword related to username or employee name you wanna search:");
        String keyword = input.nextLine();
        List<Account> accountListSearch = AccountBusiness.searchAccount(keyword);
        if (accountListSearch.size() != 0) {
            System.out.printf("%-15s%-25s%-25s%-15s%-15s%-15s\n", "Account ID", "User Name", "Password", "Permission",
                    "Employee ID", "Account Status");
            accountListSearch.stream().forEach(Account::displayData);
            System.out.println("Do you wanna change the status of account?");
            System.out.println("1. Yes              2. No");
            System.out.println("Please choose number representing your request:");
            boolean checkOutUpdateAccStatus = true;

            try {
                do {
                    int choice = Integer.parseInt(input.nextLine());
                    switch (choice) {
                        case 1 -> {
                            Account account = AccountBusiness.getAccountByUsername(accountListSearch.get(0).
                                    getUserName());
                            account.setAccStatus(account.validateAccStatus(input));
                            boolean updateAccountStatusResult = AccountBusiness.updateAccountStatus(account);
                            if (updateAccountStatusResult) {
                                System.out.println("The status of account has been updated successfully!");
                            } else {
                                System.err.println("Cannot update account status due to unexpected errs!");
                            }
                            checkOutUpdateAccStatus = false;
                        }
                        case 2 -> checkOutUpdateAccStatus = false;
                        default -> System.err.println("The inputted number is out of scope((1~2)");
                    }
                } while (checkOutUpdateAccStatus);
            } catch (NumberFormatException ex) {
                System.out.println("The inputted choice does not have an integer format! Please try again! " +
                        ex.getMessage());
            } catch (Exception e) {
                System.out.println("Some errs occur while inputting number of choice! " + e.getMessage());
            }

        } else {
            System.err.println("There is no employee or user whose name contains the inputted keyword!");
        }

    }
}
