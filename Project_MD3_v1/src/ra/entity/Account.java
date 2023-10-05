package ra.entity;

import java.util.List;
import java.util.Scanner;

public class Account {
    private Integer accId;
    private String userName;
    private String password;
    private boolean permission;
    private String empId;
    private boolean accStatus;

    public Account() {
    }

    public Account(Integer accId, String userName, String password, boolean permission, String empId,
                   boolean accStatus) {
        this.accId = accId;
        this.userName = userName;
        this.password = password;
        this.permission = permission;
        this.empId = empId;
        this.accStatus = accStatus;
    }

    public Integer getAccId() {
        return accId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public boolean isPermission() {
        return permission;
    }

    public String getEmpId() {
        return empId;
    }

    public boolean isAccStatus() {
        return accStatus;
    }

    public void setAccId(Integer accId) {
        this.accId = accId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPermission(boolean permission) {
        this.permission = permission;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public void setAccStatus(boolean accStatus) {
        this.accStatus = accStatus;
    }

    public void inputData(Scanner input, List<Account> accountList) {
        this.accId = validateAccId(input, accountList);
        this.userName = validateUserName(input, accountList);
        this.password = validatePassword(input);
        this.permission = validatePermission(input);
        this.accStatus = validateAccStatus(input);
    }

    public Integer validateAccId(Scanner input, List<Account> accountList) {
        System.out.println("Please input account ID:");
        do {
            String accIdStr = input.nextLine();
            if (!accIdStr.trim().equals("")) {
                try {
                    int accId = Integer.parseInt(input.nextLine());
                    if (accountList.size() == 0) {
                        return accId;
                    } else {
                        boolean checkAccId = false;
                        for (Account acc : accountList) {
                            if (accId == acc.getAccId()) {
                                checkAccId = true;
                                break;
                            }
                        }
                        if (!checkAccId) {
                            return accId;
                        } else {
                            System.err.println("The inputted ID already existed!");
                        }
                    }
                } catch (NumberFormatException ex) {
                    System.err.println("The inputted ID does not have an integer format! " + ex.getMessage());
                } catch (Exception e) {
                    System.err.println("Some errs occur while inputting account ID! " + e.getMessage());
                }
            }
        } while (true);
    }

    public String validateUserName(Scanner input, List<Account> accountList) {
        System.out.println("Please input the user name:");
        do {
            String userName = input.nextLine();
            if (!userName.trim().equals("")) {
                if (userName.trim().length() <= 30) {
                    if (accountList.size() == 0) {
                        return userName;
                    } else {
                        boolean checkUserName = false;
                        for (Account acc : accountList) {
                            if (userName.trim().equals(acc.getUserName())) {
                                checkUserName = true;
                                break;
                            }
                        }
                        if (!checkUserName) {
                            return userName;
                        } else {
                            System.err.println("The inputted user name already existed!");
                        }
                    }
                } else {
                    System.err.println("The user name should contain less than 30 characters!");
                }
            } else {
                System.err.println("The user name should not be a blank!");
            }
        } while (true);
    }

    public String validatePassword(Scanner input) {
        System.out.println("Please input password:");
        do {
            String password = input.nextLine();
            if (!password.trim().equals("")) {
                if (password.trim().length() <= 30) {
                    return password;
                } else {
                    System.err.println("The password should contain less than 30 characters!");
                }
            } else {
                System.err.println("The password should not be blank!");
            }
        } while (true);
    }

    public Boolean validatePermission(Scanner input) {
        System.out.println("Please input permission of account:");
        do {
            String permission = input.nextLine();
            if (!permission.trim().equals("")) {
                if (permission.trim().equalsIgnoreCase("true") ||
                        permission.trim().equalsIgnoreCase("false")) {
                    return Boolean.parseBoolean(permission);
                } else {
                    System.err.println("The permission should be 'true' or 'false'!");
                }
            } else {
                System.err.println("The permission should not be a blank!");
            }
        } while (true);
    }

    public Boolean validateAccStatus(Scanner input) {
        System.out.println("Please input status of account:");
        do {
            String accStatus = input.nextLine();
            if (!accStatus.trim().equals("")) {
                if (accStatus.trim().equalsIgnoreCase("true") ||
                        accStatus.trim().equalsIgnoreCase("false")) {
                    return Boolean.parseBoolean(accStatus);
                } else {
                    System.err.println("The status of account should be 'true' or 'false'!");
                }
            } else {
                System.err.println("The status of account should not be a blank!");
            }
        } while (true);
    }


    public void displayData() {
        String permission = this.permission ? "User" : "Admin";
        String accStatus = this.accStatus ? "Active" : "Block";
        System.out.print("--------------------------------------------------------------------------------------" +
                "-------------------------------------------\n");
        System.out.printf("| %-15d | %-25s | %-25s | %-15s | %-15s | %-15s |\n", this.accId, this.userName,
                this.password, permission, this.empId, accStatus);
    }
}
