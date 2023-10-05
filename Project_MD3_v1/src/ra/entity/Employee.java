package ra.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class Employee {
    /**
     * Text color
     */
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";

    /**
     * Bold format
     */
    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE

    private String empId;
    private String empName;
    private String birthday;
    private String email;
    private String phoneNum;
    private String address;
    private int empStatus;

    public Employee() {
    }

    public Employee(String empId, String empName, String birthday, String email, String phoneNum,
                    String address, int empStatus) {
        this.empId = empId;
        this.empName = empName;
        this.birthday = birthday;
        this.email = email;
        this.phoneNum = phoneNum;
        this.address = address;
        this.empStatus = empStatus;
    }

    public String getEmpId() {
        return empId;
    }

    public String getEmpName() {
        return empName;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getAddress() {
        return address;
    }

    public int getEmpStatus() {
        return empStatus;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmpStatus(int empStatus) {
        this.empStatus = empStatus;
    }

    public void inputData(Scanner input, List<Employee> employeeList) {
        this.empId = validateEmpId(input, employeeList);
        this.empName = validateEmpName(input, employeeList);
        this.birthday = validateBirthday(input);
        this.email = validateEmail(input);
        this.phoneNum = validatePhone(input);
        this.address = validateAddress(input);
        this.empStatus = validateEmpStatus(input);

    }

    public String validateEmpId(Scanner input, List<Employee> employeeList) {
        System.out.println("Please input employee ID:");
        do {
            String empId = input.nextLine();
            if (!empId.trim().equals("")) {
                if (empId.trim().length() == 5) {
                    if (employeeList.size() == 0) {
                        return empId;
                    } else {
                        boolean checkEmpId = false;
                        for (Employee emp : employeeList) {
                            if (empId.trim().equals(emp.getEmpId())) {
                                checkEmpId = true;
                                break;
                            }
                        }
                        if (!checkEmpId) {
                            return empId;
                        } else {
                            System.err.println("The inputted ID already existed! Please try another.");
                        }
                    }
                } else {
                    System.err.println("The employee ID should contain 5 character!");
                }
            } else {
                System.err.println("The employee ID should not be a blank!");
            }
        } while (true);
    }

    public String validateEmpName(Scanner input, List<Employee> employeeList) {
        System.out.println("Please input the name of employee:");
        do {
            String empName = input.nextLine();
            if (!empName.trim().equals("")) {
                if (empName.trim().length() <= 100) {
                    if (employeeList.size() == 0) {
                        return empName;
                    } else {
                        boolean checkEmpName = false;
                        for (Employee emp : employeeList) {
                            if (empName.trim().equals(emp.getEmpName().trim())) {
                                checkEmpName = true;
                                break;
                            }
                        }
                        if (!checkEmpName) {
                            return empName;
                        } else {
                            System.err.println("The inputted employee name already existed! Please try another.");
                        }
                    }
                } else {
                    System.err.println("The employee name should contain less than 100 characters!");
                }
            } else {
                System.err.println("The employee name should not be a blank!");
            }
        } while (true);
    }

    public String validateBirthday(Scanner input) {
        System.out.println("Please input employee's date of birth:");
        do {
            String birthday = input.nextLine();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            try {
                sdf.parse(birthday);
                return birthday;
            } catch (ParseException ex) {
                System.err.println("The inputted date of birth is invalid format: yyyy-MM-dd! " + ex.getMessage());
            } catch (Exception e) {
                System.err.println("Some errs occur while inputting date of birth! " + e.getMessage());
            }
        } while (true);
    }

    public String validateEmail(Scanner input) {
        System.out.println("Please input employee's email:");
        do {
            String email = input.nextLine();
            if (!email.trim().equals("")) {
                if (email.trim().length() <= 100) {
                    return email;
                } else {
                    System.err.println("The email should contain less than 100 characters");
                }
            } else {
                System.err.println("The email should not be a blank!");
            }
        } while (true);
    }

    public String validatePhone(Scanner input) {
        System.out.println("Please input employee's phone number:");
        do {
            String phoneNum = input.nextLine();
            if (!phoneNum.trim().equals("")) {
                if (phoneNum.trim().length() <= 100) {
                    return phoneNum;
                } else {
                    System.err.println("The phone number should contain less than 100 characters");
                }
            } else {
                System.err.println("The phone number should not be a blank!");
            }
        } while (true);
    }

    public String validateAddress(Scanner input) {
        System.out.println("Please input employee's address:");
        do {
            String address = input.nextLine();
            if (!address.trim().equals("")) {
                return address;
            } else {
                System.err.println("The address should not be a blank!");
            }
        } while (true);
    }

    public Integer validateEmpStatus(Scanner input) {
        System.out.println("----------------" + ANSI_GREEN + "Status Menu" + ANSI_RESET + "-----------------");
        System.out.println("0. Active");
        System.out.println("1. Paid Leave");
        System.out.println("2. Resigned");
        System.out.println("Please choose employee status:");
        do {
            try {
                int status = Integer.parseInt(input.nextLine());
                return status;
            } catch (NumberFormatException ex) {
                System.err.println("The inputted status is not an integer number " + ex.getMessage());
            } catch (Exception e) {
                System.err.println("Some errs occur while inputting status of employee! " + e.getMessage());
            }
        } while (true);
    }

    public void displayData() {
        String status = "";
        if (this.empStatus == 0) {
            status = "Active";
        } else if (this.empStatus == 1) {
            status = "Paid leave";
        } else {
            status = "Resigned";
        }
        System.out.print("--------------------------------------------------------------------------------" +
                "-----------------------------------------------------------------------------------------" +
                "--------\n");
        System.out.printf("| %-15s | %-25s | %-25s | %-25s | %-20s | %-25s | %-20s |\n", this.empId, this.empName, this.birthday,
                this.email, this.phoneNum, this.address, status);
    }
}
