package ra.business;

import ra.entity.Employee;
import ra.entity.Product;
import ra.ultinity.ConnectionDB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EmployeeBusiness {

    public static List<Employee> getAllEmployeesInfo() {
        Connection con = null;
        CallableStatement callSt = null;
        List<Employee> employeeList = null;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call get_all_employees()}");
            ResultSet rs = callSt.executeQuery();
            employeeList = new ArrayList<>();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEmpId(rs.getString("emp_id"));
                employee.setEmpName(rs.getString("emp_name"));
                employee.setBirthday(rs.getString("birth_of_date"));
                employee.setEmail(rs.getString("email"));
                employee.setPhoneNum(rs.getString("phone"));
                employee.setAddress(rs.getString("address"));
                employee.setEmpStatus(rs.getInt("emp_status"));
                employeeList.add(employee);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(con, callSt);
        }
        return employeeList;
    }

    public static List<Employee> getEmployeesInfo(int dataPerPage, int startData) {
        Connection con = null;
        CallableStatement callSt = null;
        List<Employee> employeeList = null;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call get_limit_employees(?,?)}");
            callSt.setInt(1, dataPerPage);
            callSt.setInt(2, startData);
            ResultSet rs = callSt.executeQuery();
            employeeList = new ArrayList<>();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEmpId(rs.getString("emp_id"));
                employee.setEmpName(rs.getString("emp_name"));
                employee.setBirthday(rs.getString("birth_of_date"));
                employee.setEmail(rs.getString("email"));
                employee.setPhoneNum(rs.getString("phone"));
                employee.setAddress(rs.getString("address"));
                employee.setEmpStatus(rs.getInt("emp_status"));
                employeeList.add(employee);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(con, callSt);
        }
        return employeeList;
    }

    public static Boolean addNewEmployee(Employee employee) {
        Connection con = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call add_employee(?,?,?,?,?,?,?)}");
            callSt.setString(1, employee.getEmpId());
            callSt.setString(2, employee.getEmpName());
            callSt.setString(3, employee.getBirthday());
            callSt.setString(4, employee.getEmail());
            callSt.setString(5, employee.getPhoneNum());
            callSt.setString(6, employee.getAddress());
            callSt.setInt(7, employee.getEmpStatus());
            callSt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(con, callSt);
        }
        return result;
    }

    public static Employee getEmployeeById(String employeeId) {
        Connection con = null;
        CallableStatement callSt = null;
        Employee employee = null;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call get_employee_by_id(?)}");
            callSt.setString(1, employeeId);
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                employee = new Employee();
                employee.setEmpId(rs.getString("emp_id"));
                employee.setEmpName(rs.getString("emp_name"));
                employee.setBirthday(rs.getString("birth_of_date"));
                employee.setEmail(rs.getString("email"));
                employee.setPhoneNum(rs.getString("phone"));
                employee.setAddress(rs.getString("address"));
                employee.setEmpStatus(rs.getInt("emp_status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(con, callSt);
        }
        return employee;
    }

    public static Boolean updateEmployee(Employee employee) {
        Connection con = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call update_employee(?,?,?,?,?,?,?)}");
            callSt.setString(1, employee.getEmpId());
            callSt.setString(2, employee.getEmpName());
            callSt.setString(3, employee.getBirthday());
            callSt.setString(4, employee.getEmail());
            callSt.setString(5, employee.getPhoneNum());
            callSt.setString(6, employee.getAddress());
            callSt.setInt(7, employee.getEmpStatus());
            callSt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(con, callSt);
        }
        return result;
    }

    public static List<Employee> searchEmployee(String employeeName) {
        Connection con = null;
        CallableStatement callSt = null;
        List<Employee> employeeListSearch = null;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call search_employee(?)}");
            callSt.setString(1, employeeName);
            ResultSet rs = callSt.executeQuery();
            employeeListSearch = new ArrayList<>();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEmpId(rs.getString("emp_id"));
                employee.setEmpName(rs.getString("emp_name"));
                employee.setBirthday(rs.getString("birth_of_date"));
                employee.setEmail(rs.getString("email"));
                employee.setPhoneNum(rs.getString("phone"));
                employee.setAddress(rs.getString("address"));
                employee.setEmpStatus(rs.getInt("emp_status"));
                employeeListSearch.add(employee);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(con, callSt);
        }
        return employeeListSearch;
    }

    public static Boolean updateEmployeeStatus(Employee employee) {
        Connection con = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call update_employee_status(?,?)}");
            callSt.setString(1, employee.getEmpId());
            callSt.setInt(2, employee.getEmpStatus());
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
