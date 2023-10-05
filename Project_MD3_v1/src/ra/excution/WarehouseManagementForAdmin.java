package ra.excution;

import ra.management.*;

import java.util.Scanner;

public class WarehouseManagementForAdmin {
    /**
     * Text color
     */
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_GREEN = "\u001B[32m";

    /**
     * Bold format
     */
    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
    public static Scanner input = new Scanner(System.in);

    public static void warehouseManagementForAdmin() {
        boolean checkOutForAdmin = true;
        do {
            System.out.println("------------------------" + PURPLE_BOLD + "WAREHOUSE MANAGEMENT FOR ADMIN" +
                    ANSI_RESET + "-------------------------");
            System.out.println("1. Product Management");
            System.out.println("2. Staff Management");
            System.out.println("3. Account Management");
            System.out.println("4. Receipt Management");
            System.out.println("5. Bill Management");
            System.out.println("6. Report Management");
            System.out.println("7. Exit");
            System.out.println("Please input your choice(1~7):");
            try {
                int choice = Integer.parseInt(input.nextLine());
                switch (choice) {
                    case 1:
                        ProductManagement.productManagementMenu();
                        break;
                    case 2:
                        EmployeeManagement.employeeManagementMenu();
                        break;
                    case 3:
                        AccountManagement.accountManagementMenu();
                        break;
                    case 4:
                        ReceiptManagement.receiptManagementMenu();
                        break;
                    case 5:
                        BillManagement.billManagementMenu();
                        break;
                    case 6:
                        ReportManagement.reportManagementMenu();
                        break;
                    case 7:
                        checkOutForAdmin = false;
                        break;
                    default:
                        System.err.println("The inputted choice is out of scope(1~7)");
                }
            } catch (NumberFormatException ex) {
                System.err.println("The inputted choice does not have an integer format! Please try again! " +
                        ex.getMessage());
            } catch (Exception e) {
                System.err.println("Some errs occur while inputting choice! " + e.getMessage());
            }
        } while (checkOutForAdmin);

    }


}
