package ra.business;

import ra.entity.Account;
import ra.entity.Product;
import ra.ultinity.ConnectionDB;

import javax.print.DocFlavor;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductBusiness {
    public static List<Product> getOriginalProduct() {
        Connection con = null;
        CallableStatement callSt = null;
        List<Product> productList = null;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call get_original_products()}");
            ResultSet rs = callSt.executeQuery();
            productList = new ArrayList<>();
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getString("product_id"));
                product.setProductName(rs.getString("product_name"));
                product.setManufacturer(rs.getString("manufacturer"));
                product.setCreated(rs.getString("created"));
                product.setBatch(rs.getInt("batch"));
                product.setQuantity(rs.getInt("quantity"));
                product.setProductStatus(rs.getBoolean("product_status"));
                productList.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(con, callSt);
        }
        return productList;
    }
    public static List<Product> getAllProduct(int dataPerPage, int startData) {
        Connection con = null;
        CallableStatement callSt = null;
        List<Product> productList = null;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call get_all_products(?,?)}");
            callSt.setInt(1, dataPerPage);
            callSt.setInt(2, startData);
            ResultSet rs = callSt.executeQuery();
            productList = new ArrayList<>();
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getString("product_id"));
                product.setProductName(rs.getString("product_name"));
                product.setManufacturer(rs.getString("manufacturer"));
                product.setCreated(rs.getString("created"));
                product.setBatch(rs.getInt("batch"));
                product.setQuantity(rs.getInt("quantity"));
                product.setProductStatus(rs.getBoolean("product_status"));
                productList.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(con, callSt);
        }
        return productList;
    }

    public static Boolean addProduct(Product product) {
        Connection con = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call add_product(?,?,?,?,?,?,?)}");
            callSt.setString(1, product.getProductId());
            callSt.setString(2, product.getProductName());
            callSt.setString(3, product.getManufacturer());
            callSt.setString(4, product.getCreated());
            callSt.setInt(5, product.getBatch());
            callSt.setInt(6, product.getQuantity());
            callSt.setBoolean(7, product.isProductStatus());
            callSt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(con, callSt);
        }
        return result;
    }

    public static Product getProductById(String productId) {
        Connection con = null;
        CallableStatement callSt = null;
        Product product = null;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call get_product_by_id(?)}");
            callSt.setString(1, productId);
            ResultSet rs = callSt.executeQuery();
            while (rs.next()){
                product = new Product();
                product.setProductId(rs.getString("product_id"));
                product.setProductName(rs.getString("product_name"));
                product.setManufacturer(rs.getString("manufacturer"));
                product.setCreated(rs.getString("created"));
                product.setBatch(rs.getInt("batch"));
                product.setQuantity(rs.getInt("quantity"));
                product.setProductStatus(rs.getBoolean("product_status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(con, callSt);
        }
        return product;
    }

    public static Boolean updateProduct(Product product) {
        Connection con = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call update_product(?,?,?,?,?,?,?)}");
            callSt.setString(1, product.getProductId());
            callSt.setString(2, product.getProductName());
            callSt.setString(3, product.getManufacturer());
            callSt.setString(4, product.getCreated());
            callSt.setInt(5, product.getBatch());
            callSt.setInt(6, product.getQuantity());
            callSt.setBoolean(7, product.isProductStatus());
            callSt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(con, callSt);
        }
        return result;
    }

    public static List<Product> searchProductByName(String productName) {
        Connection con = null;
        CallableStatement callSt = null;
        List<Product> productListSearch = null;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call search_product(?)}");
            callSt.setString(1, productName);
            ResultSet rs = callSt.executeQuery();
            productListSearch = new ArrayList<>();
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getString("product_id"));
                product.setProductName(rs.getString("product_name"));
                product.setManufacturer(rs.getString("manufacturer"));
                product.setCreated(rs.getString("created"));
                product.setBatch(rs.getInt("batch"));
                product.setQuantity(rs.getInt("quantity"));
                product.setProductStatus(rs.getBoolean("product_status"));
                productListSearch.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(con, callSt);
        }
        return productListSearch;
    }

    public static Boolean updateProductStatus(Product product) {
        Connection con = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            con = ConnectionDB.openConnection();
            callSt = con.prepareCall("{call update_product_status(?,?)}");
            callSt.setString(1, product.getProductId());
            callSt.setBoolean(2, product.isProductStatus());
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
