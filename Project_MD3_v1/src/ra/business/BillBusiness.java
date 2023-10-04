package ra.business;

import ra.entity.Bill;
import ra.entity.BillDetail;
import ra.ultinity.ConnectionDB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BillBusiness {
    public static Scanner input = new Scanner(System.in);

    public static List<Bill> getAllBills() {
        Connection con = null;
        CallableStatement callSt = null;
        List<Bill> billList = null;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call get_all_bills()}");
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
        } finally {
            ConnectionDB.closeConnection(con, callSt);
        }
        return billList;
    }

    public static List<BillDetail> getAllBillDetail() {
        Connection con = null;
        CallableStatement callSt = null;
        List<BillDetail> billDetailList = null;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call get_all_bill_detail()}");
            ResultSet rs = callSt.executeQuery();
            billDetailList = new ArrayList<>();
            while (rs.next()) {
                BillDetail billDetail = new BillDetail();
                billDetail.setBillDetailId(rs.getInt("bill_detail_id"));
                billDetail.setBillId(rs.getInt("bill_id"));
                billDetail.setProductId(rs.getString("product_id"));
                billDetail.setQuantity(rs.getInt("quantity"));
                billDetail.setPrice(rs.getFloat("price"));
                billDetailList.add(billDetail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(con, callSt);
        }
        return billDetailList;
    }

    public static Boolean addNewBill(Bill bill) {
        Connection con = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call create_bill(?,?,?,?,?,?,?)}");
            callSt.setInt(1, bill.getBillId());
            callSt.setString(2, bill.getBillCode());
            callSt.setString(3, bill.getEmpIdCreated());
            callSt.setString(4, bill.getCreated());
            callSt.setString(5, bill.getEmpIdAuth());
            callSt.setString(6, bill.getAuthDate());
            callSt.setInt(7, bill.isBillStatus());
            callSt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(con, callSt);
        }
        return result;
    }

    public static Boolean addNewBillDetail(BillDetail billDetail) {
        Connection con = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call create_bill_detail(?,?,?,?,?)}");
            callSt.setInt(1, billDetail.getBillDetailId());
            callSt.setInt(2, billDetail.getBillId());
            callSt.setString(3, billDetail.getProductId());
            callSt.setInt(4, billDetail.getQuantity());
            callSt.setFloat(5, billDetail.getPrice());
            callSt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(con, callSt);
        }
        return result;
    }

    public static Bill getBillById(int billId) {
        Connection con = null;
        CallableStatement callSt = null;
        Bill bill = null;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call get_bill_by_id(?)}");
            callSt.setInt(1, billId);
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                bill = new Bill();
                bill.setBillId(rs.getInt("bill_id"));
                bill.setBillCode(rs.getString("bill_code"));
                bill.setBillType(rs.getBoolean("bill_type"));
                bill.setEmpIdCreated(rs.getString("emp_id_created"));
                bill.setCreated(rs.getString("created"));
                bill.setEmpIdAuth(rs.getString("emp_id_auth"));
                bill.setAuthDate(rs.getString("auth_date"));
                bill.setBillStatus(rs.getInt("bill_status"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(con, callSt);
        }
        return bill;
    }

    public static Boolean updateBill(Bill bill) {
        Connection con = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call update_bill(?,?,?,?,?,?,?)}");
            callSt.setInt(1, bill.getBillId());
            callSt.setString(2, bill.getBillCode());
            callSt.setString(3, bill.getEmpIdCreated());
            callSt.setString(4, bill.getCreated());
            callSt.setString(5, bill.getEmpIdAuth());
            callSt.setString(6, bill.getAuthDate());
            callSt.setInt(7, bill.isBillStatus());
            callSt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(con, callSt);
        }
        return result;
    }

    public static List<BillDetail> getBillDetailByBillId(int billId) {
        Connection con = null;
        CallableStatement callSt = null;
        List<BillDetail> billDetailList = null;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call get_bill_detail_by_id(?)}");
            callSt.setInt(1, billId);
            ResultSet rs = callSt.executeQuery();
            billDetailList = new ArrayList<>();
            while (rs.next()) {
                BillDetail billDetail = new BillDetail();
                billDetail.setBillDetailId(rs.getInt("bill_detail_id"));
                billDetail.setBillId(rs.getInt("bill_id"));
                billDetail.setProductId(rs.getString("product_id"));
                billDetail.setQuantity(rs.getInt("quantity"));
                billDetail.setPrice(rs.getFloat("price"));
                billDetailList.add(billDetail);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(con, callSt);
        }
        return billDetailList;
    }

    public static Boolean updateBillDetail(BillDetail billDetail) {
        Connection con = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call update_receipt_detail(?,?,?,?,?)}");
            callSt.setInt(1, billDetail.getBillDetailId());
            callSt.setInt(2, billDetail.getBillId());
            callSt.setString(3, billDetail.getProductId());
            callSt.setInt(4, billDetail.getQuantity());
            callSt.setFloat(5, billDetail.getPrice());
            callSt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(con, callSt);
        }
        return result;
    }

    public static Boolean approveBill(Bill bill) {
        Connection con = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call approve_bill(?)}");
            callSt.setInt(1, bill.getBillId());
            callSt.executeUpdate();
            result = true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            ConnectionDB.closeConnection(con, callSt);
        }
        return result;
    }

    public static List<Bill> searchBill(int billId, String billCode) {
        Connection con = null;
        CallableStatement callSt = null;
        List<Bill> billListListSearch = null;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call search_bill(?,?)}");
            callSt.setInt(1, billId);
            callSt.setString(2, billCode);
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
