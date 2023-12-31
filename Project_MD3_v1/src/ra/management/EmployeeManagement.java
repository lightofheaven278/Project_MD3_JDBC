package ra.management;

import ra.business.AccountBusiness;
import ra.business.EmployeeBusiness;
import ra.entity.Employee;

import java.util.List;
import java.util.Scanner;

public class EmployeeManagement {
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

    public static Scanner input = new Scanner(System.in);
    public static List<Employee> originalEmployeeList = EmployeeBusiness.getAllEmployeesInfo();

    public static void employeeManagementMenu() {
        boolean checkOutEmployee = true;
        do {
            originalEmployeeList = EmployeeBusiness.getAllEmployeesInfo();
            System.out.println("-----------------------" + PURPLE_BOLD + "EMPLOYEE MANAGEMENT" + ANSI_RESET +
                    "----------------------");
            System.out.println("1. Display employee info(displayed data less than 10)");
            System.out.println("2. Add new employee");
            System.out.println("3. Update employee info");
            System.out.println("4. Update employee status");
            System.out.println("5. Search employee info");
            System.out.println("6. Exit");
            System.out.println("Please input number representing function you wanna choose:");
            try {
                int choice = Integer.parseInt(input.nextLine());
                switch (choice) {
                    case 1:
                        EmployeeManagement.displayEmployee();
                        break;
                    case 2:
                        EmployeeManagement.addNewEmployee();
                        break;
                    case 3:
                        EmployeeManagement.updateEmployee();
                        break;
                    case 4:
                        EmployeeManagement.updateEmployeeStatus();
                        break;
                    case 5:
                        EmployeeManagement.searchEmployeeInfo();
                        break;
                    case 6:
                        checkOutEmployee = false;
                        break;
                    default:
                        System.err.println("The inputted choice is out of scope(1~6)");
                }
            } catch (NumberFormatException ex) {
                System.out.println("The inputted choice does not have an integer format! Please try again! " +
                        ex.getMessage());
            } catch (Exception e) {
                System.out.println("Some errs occur while inputting number of choice! " + e.getMessage());
            }
        } while (checkOutEmployee);
    }

    public static void displayEmployee() {
        boolean checkOutDisplay = true;
        do {
            try {
                System.out.println("Please input data per page you wanna see:");
                int dataPerPage = Integer.parseInt(input.nextLine());
                if (dataPerPage <= 10) {
                    System.out.println("Please input starting data:");
                    int startData = Integer.parseInt(input.nextLine());
                    List<Employee> employeeList = EmployeeBusiness.getEmployeesInfo(dataPerPage, startData);
                    System.out.print("--------------------------------------------------------------------------------" +
                            "-----------------------------------------------------------------------------------------" +
                            "--------\n");
                    System.out.printf("| " + PURPLE_BOLD + "%-15s" + ANSI_RESET +
                                    " | " + PURPLE_BOLD + "%-25s" + ANSI_RESET +
                                    " | " + PURPLE_BOLD + "%-25s" + ANSI_RESET +
                                    " | " + PURPLE_BOLD + "%-25s" + ANSI_RESET +
                                    " | " + PURPLE_BOLD + "%-20s" + ANSI_RESET +
                                    " | " + PURPLE_BOLD + "%-25s" + ANSI_RESET +
                                    " | " + PURPLE_BOLD + "%-20s" + ANSI_RESET + " |\n",
                            "Employee ID", "Employee Name", "Birthday", "Email", "Phone", "Address", "Employee Status");
                    employeeList.stream().forEach(Employee::displayData);
                    System.out.print("--------------------------------------------------------------------------------" +
                            "-----------------------------------------------------------------------------------------" +
                            "--------\n");
                    int remainingProducts = originalEmployeeList.size() - (dataPerPage + startData);
                    if (remainingProducts < 0) {
                        remainingProducts = 0;
                    }
                    System.out.print("The remaining employees is: ");
                    System.out.println(remainingProducts);
                    System.out.println("Please choose the number below if you wanna see the remaining of data ↓");
                    System.out.println("1. Yes                 2. No");
                    int choice = Integer.parseInt(input.nextLine());
                    if (choice != 1) {
                        checkOutDisplay = false;
                    }
                } else {
                    System.out.println(ANSI_RED + "Data per page is limited by 10!" + ANSI_RESET);
                }

            } catch (NumberFormatException ex) {
                System.err.println("The inputted number does not have an integer format! " + ex.getMessage());
            } catch (Exception e) {
                System.err.println("Some errs occur while inputting number of choice! " + e.getMessage());
            }
        } while (checkOutDisplay);
    }

    public static void addNewEmployee() {
        Employee employee = new Employee();
        employee.inputData(input, originalEmployeeList);
        boolean result = EmployeeBusiness.addNewEmployee(employee);
        if (result) {
            System.out.println("The employee info has been added successfully!");
        } else {
            System.err.println("Cannot add new employee info to list due to some unexpected errs!");
        }
    }

    public static void updateEmployee() {
        System.out.println("Please input the ID of employee you wanna update info:");
        String employeeId = input.nextLine();
        Employee employee = EmployeeBusiness.getEmployeeById(employeeId);
        if (employee != null) {
            employee.setEmpName(employee.validateEmpNameForUpdate(input, originalEmployeeList, employeeId));
            employee.setBirthday(employee.validateBirthday(input));
            employee.setEmail(employee.validateEmail(input));
            employee.setPhoneNum(employee.validatePhone(input));
            employee.setAddress(employee.validateAddress(input));
            employee.setEmpStatus(employee.validateEmpStatus(input));
            boolean result = EmployeeBusiness.updateEmployee(employee);
            if (result) {
                System.out.println("The employee info has been updated successfully!");
            } else {
                System.err.println("Cannot update employee info to list due to some unexpected errs!");
            }
        } else {
            System.err.println("The inputted ID does not exist!");
        }
    }

    public static void updateEmployeeStatus() {
        System.out.println("Please input the ID of employee you wanna update info:");
        String employeeId = input.nextLine();
        Employee employee = EmployeeBusiness.getEmployeeById(employeeId);
        if (employee != null) {
            System.out.println("------------------" + ANSI_GREEN + "Product Status Menu" + ANSI_RESET +
                    "--------------------");
            System.out.println("0. Active");
            System.out.println("1. Paid leave");
            System.out.println("2. Resign");
            System.out.println("Please choose number representing status of product(0~2):");
            try {
                int choice = Integer.parseInt(input.nextLine());
                switch (choice) {
                    case 0 -> employee.setEmpStatus(0);
                    case 1 -> employee.setEmpStatus(1);
                    case 2 -> {
                        employee.setEmpStatus(2);
                        AccountBusiness.updateAccountStatusToBlock(employee);
                    }
                    default -> System.err.println("The inputted number is out of scope(0~2)");
                }
                boolean result = EmployeeBusiness.updateEmployeeStatus(employee);
                if (result) {
                    System.out.println("The employee status has been updated successfully!");
                } else {
                    System.err.println("Cannot update employee status to list due to some unexpected errs!");
                }
            } catch (NumberFormatException ex) {
                System.err.println("The inputted number does not have integer format! " + ex.getMessage());
            } catch (Exception e) {
                System.err.println("Some errs occur while inputting number! " + e.getMessage());
            }
        } else {
            System.err.println("The inputted ID does not exist!");
        }
    }

    public static void searchEmployeeInfo() {
        System.out.println("Please input the name of employee you wanna search:");
        String employeeName = input.nextLine();
        List<Employee> employeeListSearch = EmployeeBusiness.searchEmployee(employeeName);
        if (employeeListSearch.size() != 0) {
            System.out.print("--------------------------------------------------------------------------------" +
                    "-----------------------------------------------------------------------------------------" +
                    "--------\n");
            System.out.printf("| " + PURPLE_BOLD + "%-15s" + ANSI_RESET +
                            " | " + PURPLE_BOLD + "%-25s" + ANSI_RESET +
                            " | " + PURPLE_BOLD + "%-25s" + ANSI_RESET +
                            " | " + PURPLE_BOLD + "%-25s" + ANSI_RESET +
                            " | " + PURPLE_BOLD + "%-20s" + ANSI_RESET +
                            " | " + PURPLE_BOLD + "%-25s" + ANSI_RESET +
                            " | " + PURPLE_BOLD + "%-20s" + ANSI_RESET + " |\n",
                    "Employee ID", "Employee Name", "Birthday", "Email", "Phone", "Address", "Employee Status");
            employeeListSearch.stream().forEach(Employee::displayData);
            System.out.print("--------------------------------------------------------------------------------" +
                    "-----------------------------------------------------------------------------------------" +
                    "--------\n");
        } else {
            System.err.println("The inputted name does not exist!");
        }
    }
}
