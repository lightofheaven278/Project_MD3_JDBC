package ra.business;

import ra.entity.Account;
import ra.entity.Employee;
import ra.ultinity.ConnectionDB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountBusiness {

    public static List<Account> getAllAccounts() {
        Connection con = null;
        CallableStatement callSt = null;
        List<Account> accountList = null;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call get_all_accounts()}");
            ResultSet rs = callSt.executeQuery();
            accountList = new ArrayList<>();
            while (rs.next()) {
                Account account = new Account();
                account.setAccId(rs.getInt("acc_id"));
                account.setUserName(rs.getString("user_name"));
                account.setPassword(rs.getString("password"));
                account.setPermission(rs.getBoolean("permission"));
                account.setEmpId(rs.getString("emp_id"));
                account.setAccStatus(rs.getBoolean("acc_status"));
                accountList.add(account);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accountList;
    }

    public static Boolean addNewAccount(Account account) {
        Connection con = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call add_account(?,?,?,?,?)}");
            callSt.setInt(1, account.getAccId());
            callSt.setString(2, account.getUserName());
            callSt.setString(3, account.getPassword());
            callSt.setString(4, account.getEmpId());
            callSt.setBoolean(5, account.isAccStatus());
            callSt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(con, callSt);
        }
        return result;
    }

    public static Account getAccountByUsername(String user_name_in) {
        Connection con = null;
        CallableStatement callSt = null;
        Account account = null;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call get_account_by_user_name(?)}");
            callSt.setString(1, user_name_in);
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                account = new Account();
                account.setAccId(rs.getInt("acc_id"));
                account.setUserName(rs.getString("user_name"));
                account.setPassword(rs.getString("password"));
                account.setPermission(rs.getBoolean("permission"));
                account.setEmpId(rs.getString("emp_id"));
                account.setAccStatus(rs.getBoolean("acc_status"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(con, callSt);
        }
        return account;
    }

    public static Boolean updateAccountStatus(Account account) {
        Connection con = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call update_account_status(?,?)}");
            callSt.setString(1, account.getUserName());
            callSt.setBoolean(2, account.isAccStatus());
            callSt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(con, callSt);
        }
        return result;
    }

    public static List<Account> searchAccount(String keyword) {
        Connection con = null;
        CallableStatement callSt = null;
        List<Account> accountListListSearch = null;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call search_account(?)}");
            callSt.setString(1, keyword);
            ResultSet rs = callSt.executeQuery();
            accountListListSearch = new ArrayList<>();
            while (rs.next()) {
                Account account = new Account();
                account.setAccId(rs.getInt("acc_id"));
                account.setUserName(rs.getString("user_name"));
                account.setPassword(rs.getString("password"));
                account.setPermission(rs.getBoolean("permission"));
                account.setEmpId(rs.getString("emp_id"));
                account.setAccStatus(rs.getBoolean("acc_status"));
                accountListListSearch.add(account);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(con, callSt);
        }
        return accountListListSearch;
    }

    public static Boolean updateAccountStatusToBlock(Employee employee) {
        Connection con = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call update_account_status_to_block(?)}");
            callSt.setString(1, employee.getEmpId());
            callSt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(con, callSt);
        }
        return result;
    }
}
