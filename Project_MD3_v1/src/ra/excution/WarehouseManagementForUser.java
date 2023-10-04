package ra.excution;

import ra.business.*;
import ra.entity.Bill;
import ra.entity.BillDetail;
import ra.entity.Employee;
import ra.entity.Product;
import ra.management.BillManagement;
import ra.management.ReceiptManagement;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class WarehouseManagementForUser {
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

    public static void warehouseManagementForUser() {
        String userName = LoginManagement.readUserNameFromFile();
        if (userName != null) {
            boolean checkOutForUser = true;
            do {
                System.out.println("------------------------" + PURPLE_BOLD + "WAREHOUSE MANAGEMENT FOR USER" +
                        ANSI_RESET + "-------------------------");
                System.out.println("1. Display receipt by status");
                System.out.println("2. Create receipt");
                System.out.println("3. Update receipt");
                System.out.println("4. Search receipt");
                System.out.println("5. Display bill by status");
                System.out.println("6. Create bill");
                System.out.println("7. Update bill");
                System.out.println("8. Search bill");
                System.out.println("9. Exit");
                System.out.println("Please input your choice(1~9):");
                try {
                    int choice = Integer.parseInt(input.nextLine());
                    switch (choice) {
                        case 1 -> WarehouseManagementForUser.displayAllReceiptByStatus();
                        case 2 -> ReceiptManagement.createReceipt();
                        case 3 -> WarehouseManagementForUser.updateReceiptByUser(userName);
                        case 4 -> WarehouseManagementForUser.searchReceipt();
                        case 5 -> WarehouseManagementForUser.displayAllBillsByStatus();
                        case 6 -> BillManagement.createBill();
                        case 7 -> WarehouseManagementForUser.updateBillByUser();
                        case 8 -> WarehouseManagementForUser.searchBill();
                        case 9 -> checkOutForUser = false;
                        default -> System.err.println("The inputted choice is out of scope(1~9)");
                    }
                } catch (NumberFormatException ex) {
                    System.out.println(ANSI_RED + "The inputted choice does not have an integer format!" +
                            " Please try again! " + ANSI_RESET + ex.getMessage());
                } catch (Exception e) {
                    System.out.println(ANSI_RED + "Some errs occur while inputting choice! " + ANSI_RESET +
                            e.getMessage());
                }
            } while (checkOutForUser);
        } else {
            System.out.println(ANSI_RED + "Cannot proceed due to missing username!" + ANSI_RED);
        }
    }

    public static void displayAllReceiptByStatus() {
        String userName = LoginManagement.readUserNameFromFile();
        List<Bill> receiptList = UserBusiness.getAllReceiptByStatus(userName);
        System.out.printf("%-15s%-20s%-15s%-20s%-20s%-20s%-25s%-15s\n", "Receipt ID", "Receipt Code", "Type",
                "Created Employee", "Created Date", "Authorized Employee", "Authorized Date", "Receipt Status");
        receiptList.stream().forEach(Bill::displayData);
    }

    public static void displayAllBillsByStatus() {
        String userName = LoginManagement.readUserNameFromFile();
        List<Bill> billList = UserBusiness.getAllBillByStatus(userName);
        System.out.printf("%-15s%-20s%-15s%-20s%-20s%-20s%-25s%-15s\n", "Receipt ID", "Receipt Code", "Type",
                "Created Employee", "Created Date", "Authorized Employee", "Authorized Date", "Receipt Status");
        billList.stream().forEach(Bill::displayData);
    }

    public static void updateReceiptByUser(String userName) {
        System.out.println("Please input the ID of receipt you wanna update info:");
        List<Bill> receiptListCreatedByUser = UserBusiness.getAllReceiptByStatus(userName);
        try {
            boolean checkReceiptId = false;
            int receiptId = Integer.parseInt(input.nextLine());
            for (Bill receipt : receiptListCreatedByUser) {
                if (receiptId == receipt.getBillId()) {
                    checkReceiptId = true;
                    break;
                }
            }
            if (checkReceiptId) {
                updateReceiptOfUser(receiptId);
            } else {
                System.out.println(ANSI_RED + "The inputted ID is not belong to created receipt list of " +
                        "user with user name: " + ANSI_RESET + ANSI_YELLOW + userName + ANSI_RESET + ANSI_RED +
                        " !" + ANSI_RESET);
            }
        } catch (NumberFormatException ex) {
            System.out.println(ANSI_RED + "The inputted number does not have an integer format! Please try again! " +
                    ANSI_RESET + ex.getMessage());
        } catch (Exception e) {
            System.out.println(ANSI_RED + "Some errs occur while inputting number! " + ANSI_RESET + e.getMessage());
        }
    }

    private static void updateReceiptOfUser(int receiptId) {
        String userName = LoginManagement.readUserNameFromFile();
        Bill receipt = ReceiptBusiness.getReceiptById(receiptId);
        if (receipt != null) {
            if (receipt.isBillStatus() != 2) {
                // update receipt info
                List<Employee> employeeList = EmployeeBusiness.getAllEmployeesInfo();
                System.out.println("--------------Employee List-------------");
                for (int i = 0; i < employeeList.size(); i++) {
                    System.out.println((i + 1) + ". " + employeeList.get(i).getEmpName());
                }
                receipt.setEmpIdCreated(UserBusiness.getEmpIdByUserName(userName));
                System.out.println("Please input the number representing employee authorizing receipt:");
                int authorizedChoice = Integer.parseInt(input.nextLine());
                receipt.setEmpIdAuth(employeeList.get(authorizedChoice - 1).getEmpId());
                receipt.setBillCode(receipt.validateBillCode(input));
                receipt.setCreated(receipt.validateCreatedDate(input));
                receipt.setAuthDate(receipt.validateAuthDate(input));
                System.out.println(ANSI_PURPLE + "Please note that for this case status can only be changed from " +
                        "0-Create -> 1-Cancel and vice versa!" + ANSI_RESET);
                receipt.setBillStatus(receipt.validateBillStatus(input, "update"));
                boolean result = ReceiptBusiness.updateReceipt(receipt);
                if (result) {
                    System.out.println("The receipt with ID: " + ANSI_GREEN + receiptId + ANSI_RESET
                            + " has been update successfully!");
                    System.out.println("Now, we continue to update receipt detail with ID: " + ANSI_GREEN + receiptId
                            + ANSI_RESET);
                    // update receipt detail by receipt ID
                    updateReceiptDetail(receiptId);
                } else {
                    System.out.println(ANSI_RED + "Cannot update receipt due to unexpected errs!" + ANSI_RESET);
                }
            } else {
                System.out.println(ANSI_RED + "Cannot update bill whose status is authorized!" + ANSI_RESET);
            }
        } else {
            System.out.println(ANSI_RED + "The inputted receipt ID does not exist!" + ANSI_RESET);
        }
    }

    private static void updateReceiptDetail(int receiptId) {
        updateDetail(receiptId, input);
    }

    public static void updateDetail(int receiptId, Scanner input) {
        List<BillDetail> receiptDetailListUpdate = ReceiptBusiness.getReceiptDetailByReceiptId(receiptId);
        if (receiptDetailListUpdate.size() != 0) {
            // display all receipt detail by receipt ID
            System.out.printf("| " + PURPLE_BOLD + "%-25s" + ANSI_RESET + " | "
                            + PURPLE_BOLD + "%-20s" + ANSI_RESET + " | "
                            + PURPLE_BOLD + "%-15s" + ANSI_RESET + " | "
                            + PURPLE_BOLD + "%-15s" + ANSI_RESET + " | "
                            + PURPLE_BOLD + "%-15s" + ANSI_RESET + " |\n",
                    "Bill Detail ID", "Bill ID", "Product ID", "Quantity", "Price");
            receiptDetailListUpdate.stream().forEach(BillDetail::displayData);
            // update each receipt detail
            int countReceiptDetail = 1;
            for (BillDetail receiptDetail : receiptDetailListUpdate) {
                System.out.println("-----------------Update Receipt Detail Number " + countReceiptDetail +
                        "------------------");
                // get product list to update product ID
                List<Product> productList = ProductBusiness.getOriginalProduct();
                System.out.println("------------Product List-----------");
                for (int i = 0; i < productList.size(); i++) {
                    System.out.println((i + 1) + ". " + productList.get(i).getProductName());
                }
                System.out.println("Please choose number representing the product for " +
                        "this receipt detail:");
                int choice = Integer.parseInt(input.nextLine());
                receiptDetail.setProductId(productList.get(choice - 1).getProductId());
                receiptDetail.setQuantity(receiptDetail.validateQuantity(input));
                receiptDetail.setPrice(receiptDetail.validatePrice(input));
                boolean receiptDetailUpdateResult = ReceiptBusiness.updateReceiptDetail(receiptDetail);
                if (receiptDetailUpdateResult) {
                    System.out.println("Updating receipt detail has ben completed!");
                } else {
                    System.err.println("Cannot update receipt detail due to unexpected errs");
                }
                countReceiptDetail++;
            }
        } else {
            System.err.println("There is no receipt detail of receipt ID " + receiptId +
                    " . So receipt detail update will be skipped for this case!");
        }
    }

    public static void searchReceipt() {
        System.out.println("Please input ID of receipt you wanna search:");
        try {
            int receiptId = Integer.parseInt(input.nextLine());
            List<Bill> searchReceiptList = UserBusiness.searchReceiptForUser(receiptId);
            if (searchReceiptList.size() != 0) {
                System.out.printf("%-15s%-20s%-15s%-20s%-20s%-20s%-25s%-15s\n", "Receipt ID", "Receipt Code", "Type",
                        "Created Employee", "Created Date", "Authorized Employee", "Authorized Date", "Receipt Status");
                searchReceiptList.stream().forEach(Bill::displayData);
            } else {
                System.err.println("There is no receipt whose ID matches with the inputted data!");
            }
        } catch (NumberFormatException ex) {
            System.out.println("The inputted number does not have an integer format! Please try again! " +
                    ex.getMessage());
        } catch (Exception e) {
            System.out.println("Some errs occur while inputting number! " + e.getMessage());
        }
    }

    public static void updateBillByUser() {
        String userName = LoginManagement.readUserNameFromFile();
        List<Bill> billListCreatedByUser = UserBusiness.getAllBillByStatus(userName);
        System.out.println("Please input the ID of bill you wanna update:");
        try {
            boolean checkReceiptId = false;
            int billId = Integer.parseInt(input.nextLine());
            for (Bill bill : billListCreatedByUser) {
                if (billId == bill.getBillId()) {
                    checkReceiptId = true;
                    break;
                }
            }
            if (checkReceiptId) {
                updateBillOfUser(billId);
            } else {
                System.err.println("The inputted ID is not belong to created receipt list of user with user name: "
                        + userName + " !");
            }
        } catch (NumberFormatException ex) {
            System.out.println("The inputted choice does not have an integer format! Please try again! " +
                    ex.getMessage());
        } catch (Exception e) {
            System.out.println("Some errs occur while inputting choice! " + e.getMessage());
        }
    }

    private static void updateBillOfUser(int billId) {
        String userName = LoginManagement.readUserNameFromFile();
        Bill bill = BillBusiness.getBillById(billId);
        if (bill != null) {
            if (bill.isBillStatus() != 2) {
                // update receipt info
                List<Employee> employeeList = EmployeeBusiness.getAllEmployeesInfo();
                // print employee name
                System.out.println("--------------Employee List-------------");
                for (int i = 0; i < employeeList.size(); i++) {
                    System.out.println((i + 1) + ". " + employeeList.get(i).getEmpName());
                }

                bill.setEmpIdCreated(UserBusiness.getEmpIdByUserName(userName));
                // choose person who create receipt
                System.out.println("Please input the number representing employee authorizing bill:");
                int authorizedChoice = Integer.parseInt(input.nextLine());
                bill.setEmpIdAuth(employeeList.get(authorizedChoice - 1).getEmpId());
                bill.setBillCode(bill.validateBillCode(input));
                bill.setCreated(bill.validateCreatedDate(input));
                bill.setAuthDate(bill.validateAuthDate(input));
                System.out.println("Please note that, for this function, status can only be changed from 0-Create "
                        + "-> 1-Cancel and vice versa!");
                bill.setBillStatus(bill.validateBillStatus(input, "update"));
                boolean result = BillBusiness.updateBill(bill);
                if (result) {
                    // update bill detail by receipt ID
                    updateBillDetail(billId);
                } else {
                    System.err.println("Cannot update bill due to unexpected errs!");
                }
            } else {
                System.err.println("Cannot update bill whose status is authorized!");
            }
        } else {
            System.err.println("The inputted bill ID does not exist!");
        }
    }

    private static void updateBillDetail(int billId) {
        System.out.println("The bill with ID: " + billId + " has been update successfully!");
        System.out.println("Now, we continue to update bill detail with ID: " + billId);
        List<BillDetail> billDetailListUpdate = BillBusiness.getBillDetailByBillId(billId);
        if (billDetailListUpdate.size() != 0) {
            // display all receipt detail by receipt ID
            System.out.printf("%-25s%-20s%-15s%-15s%-15s\n", "Bill Detail ID", "Bill ID", "Product ID",
                    "Quantity", "Price");
            billDetailListUpdate.stream().forEach(BillDetail::displayData);
            // update each receipt detail
            int countBillDetail = 1;
            for (BillDetail billDetail : billDetailListUpdate) {
                System.out.println("-----------------Update Receipt Detail Number " + countBillDetail +
                        "------------------");
                // get product list to update product ID
                List<Product> productList = ProductBusiness.getOriginalProduct();
                System.out.println("------------Product List-----------");
                for (int i = 0; i < productList.size(); i++) {
                    System.out.println((i + 1) + ". " + productList.get(i).getProductName());
                }
                System.out.println("Please choose number representing the product for " +
                        "this bill detail:");
                int choiceProduct = Integer.parseInt(input.nextLine());
                billDetail.setProductId(productList.get(choiceProduct - 1).getProductId());
                billDetail.setQuantity(billDetail.validateQuantity(input));
                billDetail.setPrice(billDetail.validatePrice(input));
                boolean receiptDetailUpdateResult = BillBusiness.updateBillDetail(billDetail);
                if (receiptDetailUpdateResult) {
                    System.out.println("Updating receipt detail has ben completed!");
                } else {
                    System.err.println("Cannot update receipt detail due to unexpected errs");
                }
                countBillDetail++;
            }
        } else {
            System.err.println("There is no bill detail of receipt ID " + billId +
                    " . So bill detail update will be skipped for this case!");
        }
    }

    public static void searchBill() {
        System.out.println("Please input ID of bill you wanna search:");
        try {
            int billId = Integer.parseInt(input.nextLine());
            List<Bill> searchReceiptList = UserBusiness.searchBillForUser(billId);
            if (searchReceiptList.size() != 0) {
                System.out.printf("%-15s%-20s%-15s%-20s%-20s%-20s%-25s%-15s\n", "Receipt ID", "Receipt Code", "Type",
                        "Created Employee", "Created Date", "Authorized Employee", "Authorized Date", "Receipt Status");
                searchReceiptList.stream().forEach(Bill::displayData);
            } else {
                System.err.println("There is no receipt whose ID matches with the inputted data!");
                System.out.println("On the other hand, you are using account for user so the result may be limited!");
            }
        } catch (NumberFormatException ex) {
            System.out.println("The inputted number does not have an integer format! Please try again! " +
                    ex.getMessage());
        } catch (Exception e) {
            System.out.println("Some errs occur while inputting number! " + e.getMessage());
        }
    }
}
