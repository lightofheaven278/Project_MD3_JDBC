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

public class ReceiptBusiness {
    public static List<Bill> getAllReceiptAndBill() {
        Connection con = null;
        CallableStatement callSt = null;
        List<Bill> receiptAndBillList = null;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call get_all_receipt_and_bill()}");
            ResultSet rs = callSt.executeQuery();
            receiptAndBillList = new ArrayList<>();
            while (rs.next()) {
                Bill receiptAndBill = new Bill();
                receiptAndBill.setBillId(rs.getInt("bill_id"));
                receiptAndBill.setBillCode(rs.getString("bill_code"));
                receiptAndBill.setBillType(rs.getBoolean("bill_type"));
                receiptAndBill.setEmpIdCreated(rs.getString("emp_id_created"));
                receiptAndBill.setCreated(rs.getString("created"));
                receiptAndBill.setEmpIdAuth(rs.getString("emp_id_auth"));
                receiptAndBill.setAuthDate(rs.getString("auth_date"));
                receiptAndBill.setBillStatus(rs.getInt("bill_status"));
                receiptAndBillList.add(receiptAndBill);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return receiptAndBillList;
    }

    public static List<BillDetail> getAllReceiptAndBillDetail() {
        Connection con = null;
        CallableStatement callSt = null;
        List<BillDetail> receiptAndBillDetailList = null;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call get_all_receipt_and_bill_detail()}");
            ResultSet rs = callSt.executeQuery();
            receiptAndBillDetailList = new ArrayList<>();
            while (rs.next()) {
                BillDetail receiptAndBillDetail = new BillDetail();
                receiptAndBillDetail.setBillDetailId(rs.getInt("bill_detail_id"));
                receiptAndBillDetail.setBillId(rs.getInt("bill_id"));
                receiptAndBillDetail.setProductId(rs.getString("product_id"));
                receiptAndBillDetail.setQuantity(rs.getInt("quantity"));
                receiptAndBillDetail.setPrice(rs.getFloat("price"));
                receiptAndBillDetailList.add(receiptAndBillDetail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return receiptAndBillDetailList;
    }

    public static List<Bill> getAllReceipt() {
        Connection con = null;
        CallableStatement callSt = null;
        List<Bill> receiptList = null;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call get_all_receipt()}");
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

    public static List<BillDetail> getAllReceiptDetail() {
        Connection con = null;
        CallableStatement callSt = null;
        List<BillDetail> receiptDetailList = null;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call get_all_receipt_detail()}");
            ResultSet rs = callSt.executeQuery();
            receiptDetailList = new ArrayList<>();
            while (rs.next()) {
                BillDetail receiptDetail = new BillDetail();
                receiptDetail.setBillDetailId(rs.getInt("bill_detail_id"));
                receiptDetail.setBillId(rs.getInt("bill_id"));
                receiptDetail.setProductId(rs.getString("product_id"));
                receiptDetail.setQuantity(rs.getInt("quantity"));
                receiptDetail.setPrice(rs.getFloat("price"));
                receiptDetailList.add(receiptDetail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return receiptDetailList;
    }

    public static Boolean addNewReceipt(Bill receipt) {
        Connection con = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call create_receipt(?,?,?,?,?,?,?)}");
            callSt.setInt(1, receipt.getBillId());
            callSt.setString(2, receipt.getBillCode());
            callSt.setString(3, receipt.getEmpIdCreated());
            callSt.setString(4, receipt.getCreated());
            callSt.setString(5, receipt.getEmpIdAuth());
            callSt.setString(6, receipt.getAuthDate());
            callSt.setInt(7, receipt.isBillStatus());
            callSt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(con, callSt);
        }
        return result;
    }

    public static Boolean addNewReceiptDetail(BillDetail receiptDetail) {
        Connection con = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call create_receipt_detail(?,?,?,?,?)}");
            callSt.setInt(1, receiptDetail.getBillDetailId());
            callSt.setInt(2, receiptDetail.getBillId());
            callSt.setString(3, receiptDetail.getProductId());
            callSt.setInt(4, receiptDetail.getQuantity());
            callSt.setFloat(5, receiptDetail.getPrice());
            callSt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(con, callSt);
        }
        return result;
    }

    public static Bill getReceiptById(int receiptId) {
        Connection con = null;
        CallableStatement callSt = null;
        Bill receipt = null;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call get_receipt_by_id(?)}");
            callSt.setInt(1, receiptId);
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                receipt = new Bill();
                receipt.setBillId(rs.getInt("bill_id"));
                receipt.setBillCode(rs.getString("bill_code"));
                receipt.setBillType(rs.getBoolean("bill_type"));
                receipt.setEmpIdCreated(rs.getString("emp_id_created"));
                receipt.setCreated(rs.getString("created"));
                receipt.setEmpIdAuth(rs.getString("emp_id_auth"));
                receipt.setAuthDate(rs.getString("auth_date"));
                receipt.setBillStatus(rs.getInt("bill_status"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(con, callSt);
        }
        return receipt;
    }

    public static Boolean updateReceipt(Bill receipt) {
        Connection con = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call update_receipt(?,?,?,?,?,?,?)}");
            callSt.setInt(1, receipt.getBillId());
            callSt.setString(2, receipt.getBillCode());
            callSt.setString(3, receipt.getEmpIdCreated());
            callSt.setString(4, receipt.getCreated());
            callSt.setString(5, receipt.getEmpIdAuth());
            callSt.setString(6, receipt.getAuthDate());
            callSt.setInt(7, receipt.isBillStatus());
            callSt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(con, callSt);
        }
        return result;
    }

    public static List<BillDetail> getReceiptDetailByReceiptId(int receiptId) {
        Connection con = null;
        CallableStatement callSt = null;
        List<BillDetail> receiptDetailList = null;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call get_receipt_detail_by_id(?)}");
            callSt.setInt(1, receiptId);
            ResultSet rs = callSt.executeQuery();
            receiptDetailList = new ArrayList<>();
            while (rs.next()) {
                BillDetail receipt = new BillDetail();
                receipt.setBillDetailId(rs.getInt("bill_detail_id"));
                receipt.setBillId(rs.getInt("bill_id"));
                receipt.setProductId(rs.getString("product_id"));
                receipt.setQuantity(rs.getInt("quantity"));
                receipt.setPrice(rs.getFloat("price"));
                receiptDetailList.add(receipt);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(con, callSt);
        }
        return receiptDetailList;
    }

    public static Boolean updateReceiptDetail(BillDetail receiptDetail) {
        Connection con = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call update_receipt_detail(?,?,?,?,?)}");
            callSt.setInt(1, receiptDetail.getBillDetailId());
            callSt.setInt(2, receiptDetail.getBillId());
            callSt.setString(3, receiptDetail.getProductId());
            callSt.setInt(4, receiptDetail.getQuantity());
            callSt.setFloat(5, receiptDetail.getPrice());
            callSt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(con, callSt);
        }
        return result;
    }

    public static Boolean approveReceipt(Bill receipt) {
        Connection con = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call approve_receipt(?)}");
            callSt.setInt(1, receipt.getBillId());
            callSt.executeUpdate();
            result = true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            ConnectionDB.closeConnection(con, callSt);
        }
        return result;
    }

    public static List<Bill> searchReceipt(int receiptId, String receiptCode) {
        Connection con = null;
        CallableStatement callSt = null;
        List<Bill> receiptListListSearch = null;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call search_receipt(?,?)}");
            callSt.setInt(1, receiptId);
            callSt.setString(2, receiptCode);
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
}
