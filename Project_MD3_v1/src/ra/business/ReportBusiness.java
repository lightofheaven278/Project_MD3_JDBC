package ra.business;

import ra.ultinity.ConnectionDB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

public class ReportBusiness {
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

    public static void statisticsOfExpenses() {
        Connection con = null;
        CallableStatement callSt = null;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call statistics_expenses_by_date_time()}");
            ResultSet rs = callSt.executeQuery();
            System.out.print("----------------------------------------------------\n");
            System.out.printf("| " + ANSI_PURPLE + "%-25s" + ANSI_RESET + " | " +
                            ANSI_PURPLE + "%-20s" + ANSI_RESET + " |\n",
                    "Authorized Date", "Expenses");
            while (rs.next()) {
                System.out.print("----------------------------------------------------\n");
                String date = rs.getString("auth_date");
                float expenses = rs.getFloat("Expenses");
                System.out.printf("| %-25s | %-20.1f |\n", date, expenses);
            }
            System.out.print("----------------------------------------------------\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(con, callSt);
        }
    }

    public static void statisticsOfExpensesInTimeInterval(String date_in, String date_out) {
        Connection con = null;
        CallableStatement callSt = null;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call statistics_expenses_in_time_interval(?,?)}");
            callSt.setString(1, date_in);
            callSt.setString(2, date_out);
            ResultSet rs = callSt.executeQuery();
            System.out.print("----------------------------------------------------\n");
            System.out.printf("| " + ANSI_PURPLE + "%-25s" + ANSI_RESET + " | " +
                            ANSI_PURPLE + "%-20s" + ANSI_RESET + " |\n",
                    "Authorized Date", "Expenses");
            while (rs.next()) {
                System.out.print("----------------------------------------------------\n");
                String date = rs.getString("auth_date");
                float expenses = rs.getFloat("Expenses");
                System.out.printf("| %-25s | %-20.1f |\n", date, expenses);
            }
            System.out.print("----------------------------------------------------\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(con, callSt);
        }
    }

    public static void statisticsOfRevenue() {
        Connection con = null;
        CallableStatement callSt = null;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call statistics_revenue_by_date_time()}");
            ResultSet rs = callSt.executeQuery();
            System.out.print("----------------------------------------------------\n");
            System.out.printf("| " + ANSI_PURPLE + "%-25s" + ANSI_RESET + " | " +
                            ANSI_PURPLE + "%-20s" + ANSI_RESET + " |\n",
                    "Authorized Date", "Revenue");
            while (rs.next()) {
                System.out.print("----------------------------------------------------\n");
                String date = rs.getString("auth_date");
                float revenue = rs.getFloat("revenue");
                System.out.printf("| %-25s | %-20.1f |\n", date, revenue);
            }
            System.out.print("----------------------------------------------------\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(con, callSt);
        }
    }

    public static void statisticsOfRevenueInTimeInterval(String date_in, String date_out) {
        Connection con = null;
        CallableStatement callSt = null;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call statistics_revenue_in_time_interval(?,?)}");
            callSt.setString(1, date_in);
            callSt.setString(2, date_out);
            ResultSet rs = callSt.executeQuery();
            System.out.print("----------------------------------------------------\n");
            System.out.printf("| " + ANSI_PURPLE + "%-25s" + ANSI_RESET + " | " +
                            ANSI_PURPLE + "%-20s" + ANSI_RESET + " |\n",
                    "Authorized Date", "Revenue");
            while (rs.next()) {
                System.out.print("----------------------------------------------------\n");
                String date = rs.getString("auth_date");
                float expenses = rs.getFloat("revenue");
                System.out.printf("| %-25s | %-20.1f |\n", date, expenses);
            }
            System.out.print("----------------------------------------------------\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(con, callSt);
        }
    }

    public static void statisticsNumberOfEmpByStatus() {
        Connection con = null;
        CallableStatement callSt = null;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call statistics_number_of_emp_by_status()}");
            ResultSet rs = callSt.executeQuery();
            System.out.print("---------------------------------------------------------\n");
            System.out.printf("| " + ANSI_PURPLE + "%-25s" + ANSI_RESET + " | " +
                            ANSI_PURPLE + "%-25s" + ANSI_RESET + " |\n",
                    "Employee Status", "Number Of Employee");
            while (rs.next()) {
                System.out.print("---------------------------------------------------------\n");
                String statusStr = "";
                int status = rs.getInt("emp_status");
                if (status == 0) {
                    statusStr = "Active";
                } else if (status == 1) {
                    statusStr = "Paid leave";
                } else {
                    statusStr = "Resign";
                }
                int numberOfEmp = rs.getInt("NumberOfEmp");
                System.out.printf("| %-25s | %-25d |\n", statusStr, numberOfEmp);
            }
            System.out.print("---------------------------------------------------------\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(con, callSt);
        }
    }

    public static void statisticsMostImportProduct(String date_in, String date_out) {
        Connection con = null;
        CallableStatement callSt = null;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call statistics_most_import_product(?,?)}");
            callSt.setString(1, date_in);
            callSt.setString(2, date_out);
            ResultSet rs = callSt.executeQuery();
            System.out.print("---------------------------------------------------------\n");
            System.out.printf("| " + ANSI_PURPLE + "%-25s" + ANSI_RESET + " | " +
                            ANSI_PURPLE + "%-25s" + ANSI_RESET + " |\n",
                    "Product Name", "Import Quantity");
            while (rs.next()) {
                System.out.print("---------------------------------------------------------\n");
                String productName = rs.getString("product_name");
                int importQuantity = rs.getInt("total_quantity");
                System.out.printf("| %-25s | %-25d |\n", productName, importQuantity);
            }
            System.out.print("---------------------------------------------------------\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(con, callSt);
        }
    }

    public static void statisticsLeastImportProduct(String date_in, String date_out) {
        Connection con = null;
        CallableStatement callSt = null;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call statistics_least_import_product(?,?)}");
            callSt.setString(1, date_in);
            callSt.setString(2, date_out);
            ResultSet rs = callSt.executeQuery();
            System.out.print("---------------------------------------------------------\n");
            System.out.printf("| " + ANSI_PURPLE + "%-25s" + ANSI_RESET + " | " +
                            ANSI_PURPLE + "%-25s" + ANSI_RESET + " |\n",
                    "Product Name", "Import Quantity");
            while (rs.next()) {
                System.out.print("---------------------------------------------------------\n");
                String productName = rs.getString("product_name");
                int importQuantity = rs.getInt("total_quantity");
                System.out.printf("| %-25s | %-25d |\n", productName, importQuantity);
            }
            System.out.print("---------------------------------------------------------\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(con, callSt);
        }
    }

    public static void statisticsMostExportProduct(String date_in, String date_out) {
        Connection con = null;
        CallableStatement callSt = null;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call statistics_most_export_product(?,?)}");
            callSt.setString(1, date_in);
            callSt.setString(2, date_out);
            ResultSet rs = callSt.executeQuery();
            System.out.print("---------------------------------------------------------\n");
            System.out.printf("| " + ANSI_PURPLE + "%-25s" + ANSI_RESET + " | " +
                            ANSI_PURPLE + "%-25s" + ANSI_RESET + " |\n",
                    "Product Name", "Export Quantity");
            while (rs.next()) {
                System.out.print("---------------------------------------------------------\n");
                String productName = rs.getString("product_name");
                int exportQuantity = rs.getInt("total_quantity");
                System.out.printf("| %-25s | %-25d |\n", productName, exportQuantity);
            }
            System.out.print("---------------------------------------------------------\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(con, callSt);
        }
    }

    public static void statisticsLeastExportProduct(String date_in, String date_out) {
        Connection con = null;
        CallableStatement callSt = null;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call statistics_most_export_product(?,?)}");
            callSt.setString(1, date_in);
            callSt.setString(2, date_out);
            ResultSet rs = callSt.executeQuery();
            System.out.print("---------------------------------------------------------\n");
            System.out.printf("| " + ANSI_PURPLE + "%-25s" + ANSI_RESET + " | " +
                            ANSI_PURPLE + "%-25s" + ANSI_RESET + " |\n",
                    "Product Name", "Export Quantity");
            while (rs.next()) {
                System.out.print("---------------------------------------------------------\n");
                String productName = rs.getString("product_name");
                int exportQuantity = rs.getInt("total_quantity");
                System.out.printf("| %-25s | %-25d |\n", productName, exportQuantity);
            }
            System.out.print("---------------------------------------------------------\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(con, callSt);
        }
    }
}

