package ra.management;

import ra.business.ReportBusiness;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class ReportManagement {
    public static Scanner input = new Scanner(System.in);

    public static void reportManagementMenu() {
        boolean checkOutReport = true;
        do {
            System.out.println("-----------------------REPORT MANAGEMENT-----------------------");
            System.out.println("1. Statistics of expenses by day, month, year");
            System.out.println("2. Statistics of expenses within time interval");
            System.out.println("3. Statistics of revenue by day, month, year");
            System.out.println("4. Statistics of revenue within time interval");
            System.out.println("5. Statistics of number of employees by status");
            System.out.println("6. Statistics of the most imported product within time interval");
            System.out.println("7. Statistics of the least imported product within time interval");
            System.out.println("8. Statistics of the most exported product within time interval");
            System.out.println("9. Statistics of the least exported product within time interval");
            System.out.println("10. Exit");
            System.out.println("Please input the number representing the function you wanna choose(1~10):");
            try {
                int choice = Integer.parseInt(input.nextLine());
                switch (choice) {
                    case 1:
                        ReportBusiness.statisticsOfExpenses();
                        break;
                    case 2:
                        ReportManagement.statisticsOfExpensesInTimeInterval();
                        break;
                    case 3:
                        ReportBusiness.statisticsOfRevenue();
                        break;
                    case 4:
                        ReportManagement.statisticsOfRevenueInTimeInterval();
                        break;
                    case 5:
                        ReportBusiness.statisticsNumberOfEmpByStatus();
                        break;
                    case 6:
                        ReportManagement.statisticsMostImportProduct();
                        break;
                    case 7:
                        ReportManagement.statisticsLeastImportProduct();
                        break;
                    case 8:
                        ReportManagement.statisticsMostExportProduct();
                        break;
                    case 9:
                        ReportManagement.statisticsLeastExportProduct();
                        break;
                    case 10:
                        checkOutReport = false;
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
        } while (checkOutReport);
    }

    public static void statisticsOfExpensesInTimeInterval() {
        System.out.println("Please input start-date:");
        String date_in = validateDate(input);
        System.out.println("Please input end-date:");
        String date_out = validateDate(input);
        ReportBusiness.statisticsOfExpensesInTimeInterval(date_in, date_out);
    }

    public static void statisticsOfRevenueInTimeInterval() {
        System.out.println("Please input start-date:");
        String date_in = validateDate(input);
        System.out.println("Please input end-date:");
        String date_out = validateDate(input);
        ReportBusiness.statisticsOfRevenueInTimeInterval(date_in, date_out);
    }

    public static String validateDate(Scanner input) {
        do {
            String date = input.nextLine();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            try {
                sdf.parse(date);
                return date;
            } catch (ParseException ex) {
                System.err.println("The inputted date is invalid format: yyyy-MM-dd! " + ex.getMessage());
            } catch (Exception e) {
                System.err.println("Some errs occur while inputting date! " + e.getMessage());
            }
        } while (true);
    }

    public static void statisticsMostImportProduct() {
        System.out.println("Please input start-date:");
        String date_in = validateDate(input);
        System.out.println("Please input end-date:");
        String date_out = validateDate(input);
        ReportBusiness.statisticsMostImportProduct(date_in, date_out);
    }

    public static void statisticsLeastImportProduct() {
        System.out.println("Please input start-date:");
        String date_in = validateDate(input);
        System.out.println("Please input end-date:");
        String date_out = validateDate(input);
        ReportBusiness.statisticsLeastImportProduct(date_in, date_out);
    }

    public static void statisticsMostExportProduct() {
        System.out.println("Please input start-date:");
        String date_in = validateDate(input);
        System.out.println("Please input end-date:");
        String date_out = validateDate(input);
        ReportBusiness.statisticsMostExportProduct(date_in, date_out);
    }

    public static void statisticsLeastExportProduct() {
        System.out.println("Please input start-date:");
        String date_in = validateDate(input);
        System.out.println("Please input end-date:");
        String date_out = validateDate(input);
        ReportBusiness.statisticsLeastExportProduct(date_in, date_out);
    }

}
