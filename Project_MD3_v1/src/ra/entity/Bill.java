package ra.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class Bill {
    private int billId;
    private String billCode;
    private boolean billType;
    private String empIdCreated;
    private String created;
    private String empIdAuth;
    private String authDate;
    private int billStatus;

    public Bill() {
    }

    public Bill(int billId, String billCode, boolean billType, String empIdCreated, String created, String empIdAuth,
                String authDate, int billStatus) {
        this.billId = billId;
        this.billCode = billCode;
        this.billType = billType;
        this.empIdCreated = empIdCreated;
        this.created = created;
        this.empIdAuth = empIdAuth;
        this.authDate = authDate;
        this.billStatus = billStatus;
    }

    public int getBillId() {
        return billId;
    }

    public String getBillCode() {
        return billCode;
    }

    public boolean isBillType() {
        return billType;
    }

    public String getEmpIdCreated() {
        return empIdCreated;
    }

    public String getCreated() {
        return created;
    }

    public String getEmpIdAuth() {
        return empIdAuth;
    }

    public String getAuthDate() {
        return authDate;
    }

    public Integer isBillStatus() {
        return billStatus;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public void setBillType(boolean billType) {
        this.billType = billType;
    }

    public void setEmpIdCreated(String empIdCreated) {
        this.empIdCreated = empIdCreated;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public void setEmpIdAuth(String empIdAuth) {
        this.empIdAuth = empIdAuth;
    }

    public void setAuthDate(String authDate) {
        this.authDate = authDate;
    }

    public void setBillStatus(int billStatus) {
        this.billStatus = billStatus;
    }

    public void inputData(Scanner input, List<Bill> billList, String condition) {
        if (condition.equalsIgnoreCase("create")) {
            this.billId = validateBillId(input, billList);
            this.billCode = validateBillCode(input);
            this.created = validateCreatedDate(input);
            this.authDate = validateAuthDate(input);
            this.billStatus = validateBillStatus(input, "create");
        } else {
            this.billId = validateBillId(input, billList);
            this.billCode = validateBillCode(input);
            this.created = validateCreatedDate(input);
            this.authDate = validateAuthDate(input);
            this.billStatus = validateBillStatus(input, "update");
        }

    }

    public Integer validateBillId(Scanner input, List<Bill> billList) {
        System.out.println("Please input bill/receipt ID:");
        do {
            String billIdStr = input.nextLine();
            if (!billIdStr.trim().equals("")) {
                try {
                    int billId = Integer.parseInt(billIdStr);
                    if (billList.size() == 0) {
                        return billId;
                    } else {
                        boolean checkBillId = false;
                        for (Bill bill : billList) {
                            if (billId == bill.getBillId()) {
                                checkBillId = true;
                                break;
                            }
                        }
                        if (!checkBillId) {
                            return billId;
                        } else {
                            System.err.println("The inputted bill/receipt ID already existed!");
                        }
                    }
                } catch (NumberFormatException ex) {
                    System.err.println("The inputted bill/receipt ID does not have an integer format! " +
                            ex.getMessage());
                } catch (Exception e) {
                    System.err.println("Some errs occur while inputting bill/receipt ID! " + e.getMessage());
                }
            } else {
                System.err.println("The bill/receipt ID should not be a blank!");
            }
        } while (true);
    }

    public String validateBillCode(Scanner input) {
        System.out.println("Please input bill/receipt code:");
        do {
            String billCodeStr = input.nextLine();
            if (!billCodeStr.trim().equals("")) {
                return billCodeStr;
            } else {
                System.err.println("The password should not be blank!");
            }
        } while (true);
    }

    public Boolean validateBillType(Scanner input) {
        System.out.println("Please input bill type(true->Receipt or false->Bill):");
        do {
            String billType = input.nextLine();
            if (!billType.trim().equals("")) {
                if (billType.trim().equalsIgnoreCase("true") ||
                        billType.trim().equalsIgnoreCase("false")) {
                    return Boolean.parseBoolean(billType);
                } else {
                    System.err.println("The bill type should be 'true' or 'false'!");
                }
            } else {
                System.err.println("The bill type should not be a blank!");
            }
        } while (true);
    }

    public String validateCreatedDate(Scanner input) {
        System.out.println("Please input created date:");
        do {
            String created = input.nextLine();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            try {
                sdf.parse(created);
                return created;
            } catch (ParseException ex) {
                System.err.println("The inputted created date is invalid format: yyyy-MM-dd! " + ex.getMessage());
            } catch (Exception e) {
                System.err.println("Some errs occur while inputting created date! " + e.getMessage());
            }
        } while (true);
    }

    public String validateAuthDate(Scanner input) {
        System.out.println("Please input authorized date:");
        do {
            String authDate = input.nextLine();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            try {
                sdf.parse(authDate);
                return authDate;
            } catch (ParseException ex) {
                System.err.println("The inputted authorized date is invalid format:yyyy-MM-dd! " + ex.getMessage());
            } catch (Exception e) {
                System.err.println("Some errs occur while inputting authorized date! " + e.getMessage());
            }
        } while (true);
    }

    public Integer validateBillStatus(Scanner input, String condition) {
        if (condition.equalsIgnoreCase("create")) {
            System.out.println("----------------Bill/Receipt Status Menu-----------------");
            System.out.println("0. Created");
            System.out.println("1. Cancelled");
            System.out.println("2. Authorized");
            System.out.println("Please choose bill/receipt status:");
            do {
                try {
                    int billStatus = Integer.parseInt(input.nextLine());
                    return billStatus;
                } catch (NumberFormatException ex) {
                    System.err.println("The inputted bill status is not an integer number " + ex.getMessage());
                } catch (Exception e) {
                    System.err.println("Some errs occur while inputting status of bill! " + e.getMessage());
                }
            } while (true);
        } else {
            System.out.println("----------------Bill/Receipt Status Menu-----------------");
            System.out.println("0. Created");
            System.out.println("1. Cancelled");
            System.out.println("Please choose bill/receipt status:");
            do {
                try {
                    int billStatus = Integer.parseInt(input.nextLine());
                    if (billStatus != 2) {
                        return billStatus;
                    } else {
                        System.err.println("Please input 1->Cancelled or 0->Created");
                    }
                } catch (NumberFormatException ex) {
                    System.err.println("The inputted bill/receipt status is not an integer number " + ex.getMessage());
                } catch (Exception e) {
                    System.err.println("Some errs occur while inputting status of bill/receipt! " + e.getMessage());
                }
            } while (true);
        }
    }

    public void displayData() {
        String billType = this.billType ? "Receipt" : "Bill";
        String billStatus = "";
        if (this.billStatus == 0) {
            billStatus = "Created";
        } else if (this.billStatus == 1) {
            billStatus = "Cancelled";
        } else {
            billStatus = "Authorized";
        }
        System.out.printf("%-15d%-20s%-15s%-20s%-20s%-20s%-25s%-15s\n", this.billId, this.billCode, billType,
                this.empIdCreated, this.created, this.empIdAuth, this.authDate, billStatus);
    }
}
