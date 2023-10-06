package ra.management;

import ra.business.ProductBusiness;
import ra.entity.Product;

import java.util.List;
import java.util.Scanner;

public class ProductManagement {
    /**
     * Text color
     */
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";

    /**
     * Bold format
     */
    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE

    public static Scanner input = new Scanner(System.in);
    public static List<Product> originalProductList = ProductBusiness.getOriginalProduct();

    public static void productManagementMenu() {
        boolean checkOutProduct = true;
        do {
            originalProductList = ProductBusiness.getOriginalProduct();
            System.out.println("----------------------" + PURPLE_BOLD + "PRODUCT MANAGEMENT SYSTEM" + ANSI_RESET +
                    "----------------------");
            System.out.println("1. Display product info(number of items less than or equal 10)");
            System.out.println("2. Add new product to list");
            System.out.println("3. Update product info");
            System.out.println("4. Search product by name");
            System.out.println("5. Update product status");
            System.out.println("6. Exit");
            System.out.println("Please input your choice(1~6):");
            try {
                int choice = Integer.parseInt(input.nextLine());
                switch (choice) {
                    case 1:
                        ProductManagement.displayProduct();
                        break;
                    case 2:
                        ProductManagement.addNewProduct();
                        break;
                    case 3:
                        ProductManagement.updateProduct();
                        break;
                    case 4:
                        ProductManagement.searchProduct();
                        break;
                    case 5:
                        ProductManagement.updateProductStatus();
                        break;
                    case 6:
                        checkOutProduct = false;
                        break;
                    default:
                        System.err.println("The inputted choice is out of scope(1~6)");
                }
            } catch (NumberFormatException ex) {
                System.err.println("The inputted choice does not have an integer format! Please try again! " +
                        ex.getMessage());
            } catch (Exception e) {
                System.err.println("Some errs occur while inputting choice! " + e.getMessage());
            }
        } while (checkOutProduct);

    }

    public static void displayProduct() {
        boolean checkOutDisplay = true;
        do {
            try {
                System.out.println("Please input data per page you wanna see:");
                int dataPerPage = Integer.parseInt(input.nextLine());
                if (dataPerPage <= 10) {
                    System.out.println("Please input starting data:");
                    int startData = Integer.parseInt(input.nextLine());
                    List<Product> productList = ProductBusiness.getAllProduct(dataPerPage, startData);
                    System.out.print("--------------------------------------------------------------------------------" +
                            "---------------------------------------------------------------------------\n");
                    System.out.printf("| " + ANSI_PURPLE + "%-15s" + ANSI_RESET +
                                    " | " + ANSI_PURPLE + "%-25s" + ANSI_RESET +
                                    " | " + ANSI_PURPLE + "%-25s" + ANSI_RESET +
                                    " | " + ANSI_PURPLE + "%-20s" + ANSI_RESET +
                                    " | " + ANSI_PURPLE + "%-15s" + ANSI_RESET +
                                    " | " + ANSI_PURPLE + "%-15s" + ANSI_RESET +
                                    " | " + ANSI_PURPLE + "%-20s" + ANSI_RESET + " |\n",
                            "Product ID", "Product Name", "Manufacturer", "Created Date",
                            "Batch", "Quantity", "Product Status");
                    productList.stream().forEach(Product::displayData);
                    System.out.print("--------------------------------------------------------------------------------" +
                            "-----------------------------------------------------------------------------\n");
                    int remainingProducts = originalProductList.size() - (dataPerPage + startData);
                    if (remainingProducts < 0) {
                        remainingProducts = 0;
                    }
                    System.out.print("The remaining products is: ");
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
                System.out.println(ANSI_RED + "The inputted number does not have an integer format! " + ANSI_RESET
                        + ex.getMessage());
            } catch (Exception e) {
                System.out.println(ANSI_RED + "Some errs occur while inputting number of choice! " + ANSI_RESET
                        + e.getMessage());
            }
        } while (checkOutDisplay);
    }

    public static void addNewProduct() {
        Product product = new Product();
        product.inputData(input, originalProductList);
        boolean result = ProductBusiness.addProduct(product);
        if (result) {
            System.out.println("The product has been added successfully!");
        } else {
            System.err.println("Cannot add new product to list due to some unexpected errs!");
        }
    }

    public static void updateProduct() {
        System.out.println("Please input the ID of Product you wanna update info:");
        String productId = input.nextLine();
        Product product = ProductBusiness.getProductById(productId);
        if (product != null) {
            product.setProductName(product.validateProductNameForUpdate(input, originalProductList, productId));
            product.setManufacturer(product.validateManufacturer(input));
            product.setCreated(product.validateDateCreated(input));
            product.setBatch(product.validateBatch(input));
            product.setQuantity(product.validateQuantity(input));
            product.setProductStatus(product.validateProductStatus(input));
            boolean result = ProductBusiness.updateProduct(product);
            if (result) {
                System.out.println("The product has been updated successfully!");
            } else {
                System.err.println("Cannot update product to list due to some unexpected errs!");
            }
        } else {
            System.err.println("The inputted ID does not exist!");
        }
    }

    public static void searchProduct() {
        System.out.println("Please input the name of product you wanna search:");
        String productName = input.nextLine();
        List<Product> productListSearch = ProductBusiness.searchProductByName(productName);
        if (productListSearch.size() != 0) {
            System.out.print("--------------------------------------------------------------------------------" +
                    "---------------------------------------------------------------------------\n");
            System.out.printf("| " + ANSI_PURPLE + "%-15s" + ANSI_RESET +
                            " | " + ANSI_PURPLE + "%-25s" + ANSI_RESET +
                            " | " + ANSI_PURPLE + "%-25s" + ANSI_RESET +
                            " | " + ANSI_PURPLE + "%-20s" + ANSI_RESET +
                            " | " + ANSI_PURPLE + "%-15s" + ANSI_RESET +
                            " | " + ANSI_PURPLE + "%-15s" + ANSI_RESET +
                            " | " + ANSI_PURPLE + "%-20s" + ANSI_RESET + " |\n",
                    "Product ID", "Product Name", "Manufacturer", "Created Date",
                    "Batch", "Quantity", "Product Status");
            productListSearch.stream().forEach(Product::displayData);
            System.out.print("--------------------------------------------------------------------------------" +
                    "---------------------------------------------------------------------------\n");
        } else {
            System.err.println("The inputted name does not exist!");
        }
    }

    public static void updateProductStatus() {
        System.out.println("Please input the ID of Product you wanna update info:");
        String productId = input.nextLine();
        Product product = ProductBusiness.getProductById(productId);
        if (product != null) {
            System.out.println("------------------" + ANSI_GREEN + "Product Status Menu" + ANSI_RESET +
                    "--------------------");
            System.out.println("1. True  ----> Active");
            System.out.println("2. False ----> Inactive");
            System.out.println("Please choose number representing status of product(1 or 2):");
            try {
                int choice = Integer.parseInt(input.nextLine());
                switch (choice) {
                    case 1 -> product.setProductStatus(true);
                    case 2 -> product.setProductStatus(false);
                    default -> System.err.println("The inputted number is out of scope!");
                }
                boolean result = ProductBusiness.updateProductStatus(product);
                if (result) {
                    System.out.println("The product status has been updated successfully!");
                } else {
                    System.err.println("Cannot update product status to list due to some unexpected errs!");
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
}
