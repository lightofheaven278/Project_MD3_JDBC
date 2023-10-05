package ra.management;

import ra.business.BillBusiness;
import ra.business.EmployeeBusiness;
import ra.business.ProductBusiness;
import ra.business.ReceiptBusiness;
import ra.entity.Bill;
import ra.entity.BillDetail;
import ra.entity.Employee;
import ra.entity.Product;

import java.util.List;
import java.util.Scanner;

public class BillManagement {
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
    public static List<Bill> billList = BillBusiness.getAllBills();
    public static List<BillDetail> billDetailList = BillBusiness.getAllBillDetail();

    public static void billManagementMenu() {
        boolean checkOutBill = true;
        do {
            allBillAndReceiptList = ReceiptBusiness.getAllReceiptAndBill();
            receiptAnhBillDetailList = ReceiptBusiness.getAllReceiptAndBillDetail();
            System.out.println("-----------------------" + PURPLE_BOLD + "BILL MANAGEMENT" + ANSI_RESET +
                    "-----------------------");
            System.out.println("1. Display bill list");
            System.out.println("2. Create bill");
            System.out.println("3. Update bill info(include bill detail)");
            System.out.println("4. Display bill detail list");
            System.out.println("5. Approve bill");
            System.out.println("6. Search bill");
            System.out.println("7. Exit");
            System.out.println("Please input the number representing the function you wanna choose(1~7):");
            try {
                int choice = Integer.parseInt(input.nextLine());
                switch (choice) {
                    case 1:
                        billList = BillBusiness.getAllBills();
                        BillManagement.displayAllBills();
                        break;
                    case 2:
                        BillManagement.createBill();
                        break;
                    case 3:
                        BillManagement.updateBill();
                        break;
                    case 4:
                        billDetailList = BillBusiness.getAllBillDetail();
                        BillManagement.displayAllBillDetail();
                        break;
                    case 5:
                        BillManagement.approveBill();
                        break;
                    case 6:
                        BillManagement.searchBill();
                        break;
                    case 7:
                        checkOutBill = false;
                        break;
                    default:
                        System.err.println("The inputted number is out of scope(1~7)!");
                }
            } catch (NumberFormatException ex) {
                System.err.println("The inputted choice does not have an integer format! Please try again! " +
                        ex.getMessage());
            } catch (Exception e) {
                System.err.println("Some errs occur while inputting number of choice! " + e.getMessage());
            }
        } while (checkOutBill);
    }

    public static void displayAllBills() {
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
                "Bill ID", "Bill Code", "Type", "Created Employee", "Created Date", "Authorized Employee",
                "Authorized Date", "Bill Status");
        billList.stream().forEach(Bill::displayData);
        System.out.print("-----------------------------------------------------------------------------------------" +
                "--------------------------------------------------------------------------------------\n");
    }

    public static void createBill() {
        Bill bill = new Bill();
        List<Employee> employeeList = EmployeeBusiness.getAllEmployeesInfo();
        System.out.println("--------------" + ANSI_GREEN + "Employee List" + ANSI_RESET + "-------------");
        for (int i = 0; i < employeeList.size(); i++) {
            System.out.println((i + 1) + ". " + employeeList.get(i).getEmpName());
        }
        System.out.println("Please input the number representing employee creating bill:");
        try {
            int createdChoice = Integer.parseInt(input.nextLine());
            bill.setEmpIdCreated(employeeList.get(createdChoice - 1).getEmpId());
            System.out.println("Please input the number representing employee authorizing bill:");
            int authorizedChoice = Integer.parseInt(input.nextLine());
            bill.setEmpIdAuth(employeeList.get(authorizedChoice - 1).getEmpId());
            bill.inputData(input, allBillAndReceiptList, "create");
            boolean result = BillBusiness.addNewBill(bill);
            if (result) {
                System.out.println("The bill has been created successfully!");
            } else {
                System.err.println("Cannot create bill due to unexpected errs!");
            }
            // add new receipt detail
            addNewBillDetail(bill);
        } catch (NumberFormatException ex) {
            System.err.println("The inputted number does not have an integer format! Please try again! " +
                    ex.getMessage());
        } catch (Exception e) {
            System.err.println("Some errs occur while inputting number of choice! " + e.getMessage());
        }

    }

    private static void addNewBillDetail(Bill bill) {
        System.out.println("Now, we continue to create bill detail with receipt ID: " + bill.getBillId());
        System.out.println("Please input number of bill detail(s) you wanna create:");
        int numberOfReceiptDetail = Integer.parseInt(input.nextLine());
        for (int i = 0; i < numberOfReceiptDetail; i++) {
            receiptAnhBillDetailList = ReceiptBusiness.getAllReceiptAndBillDetail();
            System.out.println("------------------" + ANSI_GREEN + "Create Bill Detail Number " + ANSI_RESET +
                    ANSI_PURPLE + (i + 1) + ANSI_RESET + "--------------------");
            BillDetail billDetail = new BillDetail();
            billDetail.setBillId(bill.getBillId());
            // get product list to update product ID
            List<Product> productList = ProductBusiness.getOriginalProduct();
            System.out.println("------------" + ANSI_GREEN + "Product List" + ANSI_RESET + "-----------");
            for (int j = 0; j < productList.size(); j++) {
                System.out.println((j + 1) + ". " + productList.get(j).getProductName());
            }
            System.out.println("Please choose number representing the product for this bill detail:");
            int choiceProduct = Integer.parseInt(input.nextLine());
            billDetail.setProductId(productList.get(choiceProduct - 1).getProductId());
            billDetail.inputData(input, receiptAnhBillDetailList);
            boolean createReceiptDetailResult = BillBusiness.addNewBillDetail(billDetail);
            if (createReceiptDetailResult) {
                System.out.println("The receipt detail has been created successfully!");
            } else {
                System.err.println("Cannot create receipt detail due to unexpected errs!");
            }
        }
    }

    public static void updateBill() {
        System.out.println("Please input ID of bill you wanna update:");
        try {
            int billId = Integer.parseInt(input.nextLine());
            Bill bill = BillBusiness.getBillById(billId);
            if (bill != null) {
                if (bill.isBillStatus() != 2) {
                    // update receipt info
                    List<Employee> employeeList = EmployeeBusiness.getAllEmployeesInfo();
                    // print employee name
                    System.out.println("--------------" + ANSI_GREEN + "Employee List" + ANSI_RESET + "-------------");
                    for (int i = 0; i < employeeList.size(); i++) {
                        System.out.println((i + 1) + ". " + employeeList.get(i).getEmpName());
                    }
                    // choose person who create receipt
                    System.out.println("Please input the number representing employee creating bill:");
                    int createdChoice = Integer.parseInt(input.nextLine());
                    bill.setEmpIdCreated(employeeList.get(createdChoice - 1).getEmpId());
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
                        // update receipt detail by receipt ID
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
        } catch (NumberFormatException ex) {
            System.err.println("The inputted number does not have an integer format! Please try again! " +
                    ex.getMessage());
        } catch (Exception e) {
            System.err.println("Some errs occur while inputting number! " + e.getMessage());
        }
    }

    private static void updateBillDetail(int billId) {
        System.out.println("The bill with ID: " + billId + " has been update successfully!");
        System.out.println("Now, we continue to update bill detail with ID: " + billId);
        List<BillDetail> billDetailListUpdate = BillBusiness.getBillDetailByBillId(billId);
        if (billDetailListUpdate.size() != 0) {
            // display all receipt detail by receipt ID
            System.out.print("-------------------------------------------------------------------------------------" +
                    "---------------------\n");
            System.out.printf("| " + PURPLE_BOLD + "%-25s" + ANSI_RESET + " | "
                            + PURPLE_BOLD + "%-20s" + ANSI_RESET + " | "
                            + PURPLE_BOLD + "%-15s" + ANSI_RESET + " | "
                            + PURPLE_BOLD + "%-15s" + ANSI_RESET + " | "
                            + PURPLE_BOLD + "%-15s" + ANSI_RESET + " |\n",
                    "Bill Detail ID", "Bill ID", "Product ID", "Quantity", "Price");
            billDetailListUpdate.stream().forEach(BillDetail::displayData);
            System.out.print("-------------------------------------------------------------------------------------" +
                    "---------------------\n");
            // update each receipt detail
            int countBillDetail = 1;
            for (BillDetail billDetail : billDetailListUpdate) {
                System.out.println("-----------------" + ANSI_GREEN + "Update Bill Detail Number " + ANSI_RESET
                        + ANSI_PURPLE + countBillDetail + ANSI_RESET + "------------------");
                // get product list to update product ID
                List<Product> productList = ProductBusiness.getOriginalProduct();
                System.out.println("------------" + ANSI_GREEN + "Product List" + ANSI_RESET + "-----------");
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

    public static void displayAllBillDetail() {
        System.out.print("-------------------------------------------------------------------------------------" +
                "---------------------\n");
        System.out.printf("| " + PURPLE_BOLD + "%-25s" + ANSI_RESET + " | "
                        + PURPLE_BOLD + "%-20s" + ANSI_RESET + " | "
                        + PURPLE_BOLD + "%-15s" + ANSI_RESET + " | "
                        + PURPLE_BOLD + "%-15s" + ANSI_RESET + " | "
                        + PURPLE_BOLD + "%-15s" + ANSI_RESET + " |\n",
                "Bill Detail ID", "Bill ID", "Product ID", "Quantity", "Price");
        billDetailList.stream().forEach(BillDetail::displayData);
        System.out.print("-------------------------------------------------------------------------------------" +
                "---------------------\n");
    }

    public static void approveBill() {
        System.out.println("Please input the ID of bill you wanna approve:");
        try {
            int billId = Integer.parseInt(input.nextLine());
            Bill bill = BillBusiness.getBillById(billId);
            Boolean approveResult = BillBusiness.approveBill(bill);
            if (approveResult) {
                System.out.println("The bill has been approved successfully!");
            } else {
                System.err.println("Cannot approve bill due to unexpected errs!");
            }
        } catch (NumberFormatException ex) {
            System.err.println("The inputted ID does not have an integer format! Please try again! " +
                    ex.getMessage());
        } catch (Exception e) {
            System.err.println("Some errs occur while inputting number! " + e.getMessage());
        }
    }

    public static void searchBill() {
        System.out.println("Please input bill ID you wanna search:");
        try {
            int billId = Integer.parseInt(input.nextLine());
            System.out.println("Please input bill code you wanna search:");
            String billCode = input.nextLine();
            List<Bill> searchReceiptList = BillBusiness.searchBill(billId, billCode);
            if (searchReceiptList.size() != 0) {
                System.out.print("------------------------------------------------------------------------------------" +
                        "------------------------------------------------------------------------------------" +
                        "-------\n");
                System.out.printf("| " + ANSI_PURPLE + "%-15s" + ANSI_RESET + " | "
                                + ANSI_PURPLE + "%-20s" + ANSI_RESET + " | "
                                + ANSI_PURPLE + "%-15s" + ANSI_RESET + " | "
                                + ANSI_PURPLE + "%-20s" + ANSI_RESET + " | "
                                + ANSI_PURPLE + "%-20s" + ANSI_RESET + " | "
                                + ANSI_PURPLE + "%-20s" + ANSI_RESET + " | "
                                + ANSI_PURPLE + "%-25s" + ANSI_RESET + " | "
                                + ANSI_PURPLE + "%-15s" + ANSI_RESET + " |\n",
                        "Bill ID", "Bill Code", "Type", "Created Employee", "Created Date", "Authorized Employee",
                        "Authorized Date", "Bill Status");
                searchReceiptList.stream().forEach(Bill::displayData);
                System.out.print("------------------------------------------------------------------------------------" +
                        "------------------------------------------------------------------------------------" +
                        "-------\n");
                updateAfterSearch();
                approveAfterSearch();
            } else {
                System.err.println("There is no bill whose ID or Code matches with the inputted data!");
            }
        } catch (NumberFormatException ex) {
            System.err.println("The inputted number does not have an integer format! Please try again! " +
                    ex.getMessage());
        } catch (Exception e) {
            System.err.println("Some errs occur while inputting number! " + e.getMessage());
        }
    }

    private static void approveAfterSearch() {
        System.out.println("Do you wanna approve bill?");
        System.out.println("1. Yes        2. No");
        System.out.println("Please choose number representing your choice(1 or 2):");
        int choiceApprove = Integer.parseInt(input.nextLine());
        switch (choiceApprove) {
            case 1:
                BillManagement.approveBill();
                break;
            case 2:
                System.out.println("Thank you for your choice!");
                break;
            default:
                System.err.println("The inputted number is out of scope(1 or 2)!");
        }
    }

    private static void updateAfterSearch() {
        System.out.println("Do you wanna update bill?");
        System.out.println("1. Yes        2. No");
        System.out.println("Please choose number representing your choice:");
        int choiceUpdate = Integer.parseInt(input.nextLine());
        switch (choiceUpdate) {
            case 1:
                BillManagement.updateBill();
                break;
            case 2:
                System.out.println("Thank you for your choice!");
                break;
            default:
                System.err.println("The inputted number is out of scope(1~2)!");
        }
    }
}
