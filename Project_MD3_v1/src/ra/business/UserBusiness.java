package ra.business;

import ra.entity.Bill;
import ra.ultinity.ConnectionDB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserBusiness {
    public static List<Bill> getAllReceiptByStatus(String userName) {
        Connection con = null;
        CallableStatement callSt = null;
        List<Bill> receiptList = null;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call get_all_receipt_by_status(?)}");
            callSt.setString(1, userName);
            ResultSet rs = callSt.executeQuery();
            receiptList = new ArrayList<>();
            while (rs.next()) {
                Bill receipt = new Bill();
                receipt.setBillId(rs.getInt("bill_id"));
                receipt.setBillCode(rs.getString("bill_code"));
                receipt.setBillType(rs.getBoolean("bill_type"));
                receipt.setEmpIdCreated(rs.getString("emp_id_created"));
                receipt.setCreated(rs.getString("created"));
                receipt.setEmpIdAuth(rs.getString("emp_id_auth"));
                receipt.setAuthDate(rs.getString("auth_date"));
                receipt.setBillStatus(rs.getInt("bill_status"));
                receiptList.add(receipt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return receiptList;
    }

    public static List<Bill> getAllBillByStatus(String userName) {
        Connection con = null;
        CallableStatement callSt = null;
        List<Bill> billList = null;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call get_all_bills_by_status(?)}");
            callSt.setString(1, userName);
            ResultSet rs = callSt.executeQuery();
            billList = new ArrayList<>();
            while (rs.next()) {
                Bill bill = new Bill();
                bill.setBillId(rs.getInt("bill_id"));
                bill.setBillCode(rs.getString("bill_code"));
                bill.setBillType(rs.getBoolean("bill_type"));
                bill.setEmpIdCreated(rs.getString("emp_id_created"));
                bill.setCreated(rs.getString("created"));
                bill.setEmpIdAuth(rs.getString("emp_id_auth"));
                bill.setAuthDate(rs.getString("auth_date"));
                bill.setBillStatus(rs.getInt("bill_status"));
                billList.add(bill);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return billList;
    }

    public static String getEmpIdByUserName(String userName) {
        Connection con = null;
        CallableStatement callSt = null;
        String empId = "";
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call get_emp_id_by_user_name(?)}");
            callSt.setString(1, userName);
            ResultSet rs = callSt.executeQuery();
            while (rs.next()){
                empId = rs.getString("emp_Id");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(con, callSt);
        }
        return empId;
    }

    public static List<Bill> searchReceiptForUser(int receiptId) {
        Connection con = null;
        CallableStatement callSt = null;
        List<Bill> receiptListListSearch = null;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call search_receipt_by_user(?)}");
            callSt.setInt(1, receiptId);
            ResultSet rs = callSt.executeQuery();
            receiptListListSearch = new ArrayList<>();
            while (rs.next()) {
                Bill receipt = new Bill();
                receipt.setBillId(rs.getInt("bill_id"));
                receipt.setBillCode(rs.getString("bill_code"));
                receipt.setBillType(rs.getBoolean("bill_type"));
                receipt.setEmpIdCreated(rs.getString("emp_id_created"));
                receipt.setCreated(rs.getString("created"));
                receipt.setEmpIdAuth(rs.getString("emp_id_auth"));
                receipt.setAuthDate(rs.getString("auth_date"));
                receipt.setBillStatus(rs.getInt("bill_status"));
                receiptListListSearch.add(receipt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(con, callSt);
        }
        return receiptListListSearch;
    }

    public static List<Bill> searchBillForUser(int billId) {
        Connection con = null;
        CallableStatement callSt = null;
        List<Bill> billListListSearch = null;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call search_bill_by_user(?,?)}");
            callSt.setInt(1, billId);
            ResultSet rs = callSt.executeQuery();
            billListListSearch = new ArrayList<>();
            while (rs.next()) {
                Bill bill = new Bill();
                bill.setBillId(rs.getInt("bill_id"));
                bill.setBillCode(rs.getString("bill_code"));
                bill.setBillType(rs.getBoolean("bill_type"));
                bill.setEmpIdCreated(rs.getString("emp_id_created"));
                bill.setCreated(rs.getString("created"));
                bill.setEmpIdAuth(rs.getString("emp_id_auth"));
                bill.setAuthDate(rs.getString("auth_date"));
                bill.setBillStatus(rs.getInt("bill_status"));
                billListListSearch.add(bill);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(con, callSt);
        }
        return billListListSearch;
    }
}
