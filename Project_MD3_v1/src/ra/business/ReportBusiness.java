package ra.business;

import ra.ultinity.ConnectionDB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

public class ReportBusiness {
    public static void statisticsOfExpenses() {
        Connection con = null;
        CallableStatement callSt = null;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call statistics_expenses_by_date_time()}");
            ResultSet rs = callSt.executeQuery();
            System.out.printf("%-25s%-20s\n", "Authorized Date", "Expenses");
            while (rs.next()) {
                String date = rs.getString("auth_date");
                float expenses = rs.getFloat("Expenses");
                System.out.printf("%-25s%-20.1f\n", date, expenses);
            }
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
            System.out.printf("%-25s%-20s\n", "Authorized Date", "Expenses");
            while (rs.next()) {
                String date = rs.getString("auth_date");
                float expenses = rs.getFloat("Expenses");
                System.out.printf("%-25s%-20.1f\n", date, expenses);
            }
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
            System.out.printf("%-25s%-20s\n", "Authorized Date", "Revenue");
            while (rs.next()) {
                String date = rs.getString("auth_date");
                float revenue = rs.getFloat("revenue");
                System.out.printf("%-25s%-20.1f\n", date, revenue);
            }
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
            System.out.printf("%-25s%-20s\n", "Authorized Date", "Revenue");
            while (rs.next()) {
                String date = rs.getString("auth_date");
                float expenses = rs.getFloat("revenue");
                System.out.printf("%-25s%-20.1f\n", date, expenses);
            }
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
            System.out.printf("%-25s%-25s\n", "Employee Status", "Number Of Employee");
            while (rs.next()) {
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
                System.out.printf("%-25s%-25d\n", statusStr, numberOfEmp);
            }
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
            System.out.printf("%-25s%-25s\n", "Product Name", "Import Quantity");
            while (rs.next()) {
                String productName = rs.getString("product_name");
                int importQuantity = rs.getInt("total_quantity");
                System.out.printf("%-25S%-25d\n", productName, importQuantity);
            }
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
            System.out.printf("%-25s%-25s\n", "Product Name", "Import Quantity");
            while (rs.next()) {
                String productName = rs.getString("product_name");
                int importQuantity = rs.getInt("total_quantity");
                System.out.printf("%-25S%-25d\n", productName, importQuantity);
            }
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
            System.out.printf("%-25s%-25s\n", "Product Name", "Export Quantity");
            while (rs.next()) {
                String productName = rs.getString("product_name");
                int exportQuantity = rs.getInt("total_quantity");
                System.out.printf("%-25S%-25d\n", productName, exportQuantity);
            }
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
            System.out.printf("%-25s%-25s\n", "Product Name", "Export Quantity");
            while (rs.next()) {
                String productName = rs.getString("product_name");
                int exportQuantity = rs.getInt("total_quantity");
                System.out.printf("%-25S%-25d\n", productName, exportQuantity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(con, callSt);
        }
    }
}

