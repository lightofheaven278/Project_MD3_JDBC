package ra.management;


import ra.business.EmployeeBusiness;
import ra.business.ProductBusiness;
import ra.business.ReceiptBusiness;
import ra.entity.Bill;
import ra.entity.BillDetail;
import ra.entity.Employee;
import ra.entity.Product;
import ra.excution.WarehouseManagementForUser;

import java.util.List;
import java.util.Scanner;

public class ReceiptManagement {
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

    public static List<Bill> allBillAndReceiptList = ReceiptBusiness.getAllReceiptAndBill();
    public static List<BillDetail> receiptAnhBillDetailList = ReceiptBusiness.getAllReceiptAndBillDetail();
    public static List<Bill> receiptList = ReceiptBusiness.getAllReceipt();
    public static List<BillDetail> receiptDetailList = ReceiptBusiness.getAllReceiptDetail();

    public static void receiptManagementMenu() {
        boolean checkOutReceipt = true;
        do {
            allBillAndReceiptList = ReceiptBusiness.getAllReceiptAndBill();
            receiptAnhBillDetailList = ReceiptBusiness.getAllReceiptAndBillDetail();
            System.out.println("-----------------------" + PURPLE_BOLD + "RECEIPT MANAGEMENT" +
                    ANSI_RESET + "-----------------------");
            System.out.println("1. Display receipt list");
            System.out.println("2. Create receipt");
            System.out.println("3. Update receipt info(include receipt detail)");
            System.out.println("4. Display receipt detail list");
            System.out.println("5. Approve receipt");
            System.out.println("6. Search receipt");
            System.out.println("7. Exit");
            System.out.println("Please input the number representing the function you wanna choose(1~7):");
            try {
                int choice = Integer.parseInt(input.nextLine());
                switch (choice) {
                    case 1:
                        receiptList = ReceiptBusiness.getAllReceipt();
                        ReceiptManagement.displayAllReceipts();
                        break;
                    case 2:
                        ReceiptManagement.createReceipt();
                        break;
                    case 3:
                        ReceiptManagement.updateReceipt();
                        break;
                    case 4:
                        receiptDetailList = ReceiptBusiness.getAllReceiptDetail();
                        ReceiptManagement.displayAllReceiptDetail();
                        break;
                    case 5:
                        ReceiptManagement.approveReceipt();
                        break;
                    case 6:
                        ReceiptManagement.searchReceipt();
                        break;
                    case 7:
                        checkOutReceipt = false;
                        break;
                    default:
                        System.err.println("The inputted number is out of scope!");
                }
            } catch (NumberFormatException ex) {
                System.err.println("The inputted choice does not have an integer format! Please try again! " +
                        ex.getMessage());
            } catch (Exception e) {
                System.err.println("Some errs occur while inputting number of choice! " + e.getMessage());
            }
        } while (checkOutReceipt);
    }

    public static void displayAllReceipts() {
        System.out.print("-----------------------------------------------------------------------------------------" +
                "--------------------------------------------------------------------------------------\n");
        System.out.printf("| " + ANSI_PURPLE + "%-15s" + ANSI_RESET + " | "
                        + ANSI_PURPLE + "%-20s" + ANSI_RESET + " | "
                        + ANSI_PURPLE + "%-15s" + ANSI_RESET + " | "
                        + ANSI_PURPLE + "%-20s" + ANSI_RESET + " | "
                        + ANSI_PURPLE + "%-20s" + ANSI_RESET + " | "
                        + ANSI_PURPLE + "%-20s" + ANSI_RESET + " | "
                        + ANSI_PURPLE + "%-25s" + ANSI_RESET + " | "
                        + ANSI_PURPLE + "%-15s" + ANSI_RESET + " |\n",
                "Receipt ID", "Receipt Code", "Type", "Created Employee", "Created Date", "Authorized Employee",
                "Authorized Date", "Receipt Status");
        receiptList.stream().forEach(Bill::displayData);
        System.out.print("-----------------------------------------------------------------------------------------" +
                "--------------------------------------------------------------------------------------\n");
    }

    public static void createReceipt() {
        Bill receipt = new Bill();
        List<Employee> employeeList = EmployeeBusiness.getAllEmployeesInfo();
        System.out.println("--------------" + ANSI_GREEN + "Employee List" + ANSI_RESET + "-------------");
        for (int i = 0; i < employeeList.size(); i++) {
            System.out.println((i + 1) + ". " + employeeList.get(i).getEmpName());
        }
        System.out.println("Please input the number representing employee creating receipt:");
        try {
            int createdChoice = Integer.parseInt(input.nextLine());
            receipt.setEmpIdCreated(employeeList.get(createdChoice - 1).getEmpId());
            System.out.println("Please input the number representing employee authorizing receipt:");
            int authorizedChoice = Integer.parseInt(input.nextLine());
            receipt.setEmpIdAuth(employeeList.get(authorizedChoice - 1).getEmpId());
            receipt.inputData(input, allBillAndReceiptList, "create");
            boolean result = ReceiptBusiness.addNewReceipt(receipt);
            if (result) {
                System.out.println("The receipt has been created successfully!");
            } else {
                System.err.println("Cannot create receipt due to unexpected errs!");
            }
            // add new receipt detail
            addNewReceiptDetail(receipt);
        } catch (NumberFormatException ex) {
            System.err.println("The inputted choice does not have an integer format! Please try again! " +
                    ex.getMessage());
        } catch (Exception e) {
            System.err.println("Some errs occur while inputting number of choice! " + e.getMessage());
        }

    }

    private static void addNewReceiptDetail(Bill receipt) {
        System.out.println("Now, we continue to create receipt detail with receipt ID: " + receipt.getBillId());
        System.out.println("Please input number of receipt detail(s) you wanna create:");
        int numberOfReceiptDetail = Integer.parseInt(input.nextLine());
        for (int i = 0; i < numberOfReceiptDetail; i++) {
            receiptAnhBillDetailList = ReceiptBusiness.getAllReceiptAndBillDetail();
            System.out.println("------------------" + ANSI_GREEN + "Create Bill Detail Number " + ANSI_RESET +
                    ANSI_PURPLE + (i + 1) + ANSI_RESET + "--------------------");
            BillDetail receiptDetail = new BillDetail();
            receiptDetail.setBillId(receipt.getBillId());
            // get product list to update product ID
            List<Product> productList = ProductBusiness.getOriginalProduct();
            System.out.println("------------" + ANSI_GREEN + "Product List" + ANSI_RESET + "-----------");
            for (int j = 0; j < productList.size(); j++) {
                System.out.println((j + 1) + ". " + productList.get(j).getProductName());
            }
            System.out.println("Please choose number representing the product for this receipt detail:");
            int choice = Integer.parseInt(input.nextLine());
            receiptDetail.setProductId(productList.get(choice - 1).getProductId());
            receiptDetail.inputData(input, receiptAnhBillDetailList);
            boolean createReceiptDetailResult = ReceiptBusiness.addNewReceiptDetail(receiptDetail);
            if (createReceiptDetailResult) {
                System.out.println("The receipt detail has been created successfully!");
            } else {
                System.err.println("Cannot create receipt detail due to unexpected errs!");
            }
        }
    }

    public static void updateReceipt() {
        System.out.println("Please input ID of receipt you wanna update:");
        try {
            int receiptId = Integer.parseInt(input.nextLine());
            Bill receipt = ReceiptBusiness.getReceiptById(receiptId);
            if (receipt != null) {
                if (receipt.isBillStatus() != 2) {
                    // update receipt info
                    List<Employee> employeeList = EmployeeBusiness.getAllEmployeesInfo();
                    System.out.println("--------------" + ANSI_GREEN + "Employee List" + ANSI_RESET + "-------------");
                    for (int i = 0; i < employeeList.size(); i++) {
                        System.out.println((i + 1) + ". " + employeeList.get(i).getEmpName());
                    }
                    System.out.println("Please input the number representing employee creating receipt:");
                    int createdChoice = Integer.parseInt(input.nextLine());
                    receipt.setEmpIdCreated(employeeList.get(createdChoice - 1).getEmpId());
                    System.out.println("Please input the number representing employee authorizing receipt:");
                    int authorizedChoice = Integer.parseInt(input.nextLine());
                    receipt.setEmpIdAuth(employeeList.get(authorizedChoice - 1).getEmpId());
                    receipt.setBillCode(receipt.validateBillCode(input));
                    receipt.setCreated(receipt.validateCreatedDate(input));
                    receipt.setAuthDate(receipt.validateAuthDate(input));
                    System.out.println("Please note that for this case status can only be changed from 0-Create " +
                            "-> 1-Cancel and vice versa!");
                    receipt.setBillStatus(receipt.validateBillStatus(input, "update"));
                    boolean result = ReceiptBusiness.updateReceipt(receipt);
                    if (result) {
                        System.out.println("The receipt with ID: " + receiptId + " has been update successfully!");
                        System.out.println("Now, we continue to update receipt detail with ID: " + receiptId);
                        // update receipt detail by receipt ID
                        updateReceiptDetail(receiptId);
                    } else {
                        System.err.println("Cannot update receipt due to unexpected errs!");
                    }
                } else {
                    System.err.println("Cannot update bill whose status is authorized!");
                }
            } else {
                System.err.println("The inputted receipt ID does not exist!");
            }

        } catch (NumberFormatException ex) {
            System.err.println("The inputted number does not have an integer format! Please try again! " +
                    ex.getMessage());
        } catch (Exception e) {
            System.err.println("Some errs occur while inputting number! " + e.getMessage());
        }
    }

    private static void updateReceiptDetail(int receiptId) {
        WarehouseManagementForUser.updateDetail(receiptId, input);
    }

    public static void displayAllReceiptDetail() {
        System.out.print("-------------------------------------------------------------------------------------" +
                "---------------------\n");
        System.out.printf("| " + PURPLE_BOLD + "%-25s" + ANSI_RESET + " | "
                        + PURPLE_BOLD + "%-20s" + ANSI_RESET + " | "
                        + PURPLE_BOLD + "%-15s" + ANSI_RESET + " | "
                        + PURPLE_BOLD + "%-15s" + ANSI_RESET + " | "
                        + PURPLE_BOLD + "%-15s" + ANSI_RESET + " |\n",
                "Receipt Detail ID", "Receipt ID", "Product ID", "Quantity", "Price");
        receiptDetailList.stream().forEach(BillDetail::displayData);
        System.out.print("-------------------------------------------------------------------------------------" +
                "----------------------\n");
    }

    public static void approveReceipt() {
        System.out.println("Please input the ID of receipt you wanna approve:");
        try {
            int receiptId = Integer.parseInt(input.nextLine());
            Bill receipt = ReceiptBusiness.getReceiptById(receiptId);
            Boolean approveResult = ReceiptBusiness.approveReceipt(receipt);
            if (approveResult) {
                System.out.println("The receipt has been approved successfully!");
            } else {
                System.err.println("Cannot update receipt due to unexpected errs!");
            }
        } catch (NumberFormatException ex) {
            System.err.println("The inputted number does not have an integer format! Please try again! " +
                    ex.getMessage());
        } catch (Exception e) {
            System.err.println("Some errs occur while inputting number! " + e.getMessage());
        }
    }

    public static void searchReceipt() {
        System.out.println("Please input receipt ID you wanna search:");
        try {
            int receiptId = Integer.parseInt(input.nextLine());
            System.out.println("Please input receipt code you wanna search:");
            String receiptCode = input.nextLine();
            List<Bill> searchReceiptList = ReceiptBusiness.searchReceipt(receiptId, receiptCode);
            if (searchReceiptList.size() != 0) {
                System.out.print("---------------------------------------------------------------------------------" +
                        "------------------------------------------------------------------------------------------" +
                        "----\n");
                System.out.printf("| " + ANSI_PURPLE + "%-15s" + ANSI_RESET + " | "
                                + ANSI_PURPLE + "%-20s" + ANSI_RESET + " | "
                                + ANSI_PURPLE + "%-15s" + ANSI_RESET + " | "
                                + ANSI_PURPLE + "%-20s" + ANSI_RESET + " | "
                                + ANSI_PURPLE + "%-20s" + ANSI_RESET + " | "
                                + ANSI_PURPLE + "%-20s" + ANSI_RESET + " | "
                                + ANSI_PURPLE + "%-25s" + ANSI_RESET + " | "
                                + ANSI_PURPLE + "%-15s" + ANSI_RESET + " |\n",
                        "Receipt ID", "Receipt Code", "Type", "Created Employee", "Created Date",
                        "Authorized Employee", "Authorized Date", "Receipt Status");
                searchReceiptList.stream().forEach(Bill::displayData);
                updateReceiptAfterSearch();
                approveReceiptAfterSearch();
            } else {
                System.err.println("There is no receipt whose ID or Code matches with the inputted data!");
            }
        } catch (NumberFormatException ex) {
            System.err.println("The inputted number does not have an integer format! Please try again! " +
                    ex.getMessage());
        } catch (Exception e) {
            System.err.println("Some errs occur while inputting number! " + e.getMessage());
        }
    }

    private static void approveReceiptAfterSearch() {
        System.out.println("Do you wanna approve receipt?");
        System.out.println("1. Yes        2. No");
        System.out.println("Please choose number representing your choice(1 or 2):");
        int choiceApprove = Integer.parseInt(input.nextLine());
        switch (choiceApprove) {
            case 1:
                ReceiptManagement.approveReceipt();
                break;
            case 2:
                System.out.println("Thank you for your choice!");
                break;
            default:
                System.err.println("The inputted number is out of scope(1 or 2)!");
        }
    }

    private static void updateReceiptAfterSearch() {
        System.out.println("Do you wanna update receipt?");
        System.out.println("1. Yes        2. No");
        System.out.println("Please choose number representing your choice:");
        int choiceUpdate = Integer.parseInt(input.nextLine());
        switch (choiceUpdate) {
            case 1:
                ReceiptManagement.updateReceipt();
                break;
            case 2:
                System.out.println("Thank you for your choice!");
                break;
            default:
                System.err.println("The inputted number is out of scope(1~2)!");
        }
    }

}
