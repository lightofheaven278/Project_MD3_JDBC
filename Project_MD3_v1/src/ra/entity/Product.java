package ra.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class Product {
    private String productId;
    private String productName;
    private String Manufacturer;
    private String created;
    private int batch;
    private int quantity;
    private boolean productStatus;

    public Product() {
    }

    public Product(String productId, String productName, String manufacturer, String created, int batch,
                   int quantity, boolean productStatus) {
        this.productId = productId;
        this.productName = productName;
        Manufacturer = manufacturer;
        this.created = created;
        this.batch = batch;
        this.quantity = quantity;
        this.productStatus = productStatus;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getManufacturer() {
        return Manufacturer;
    }

    public String getCreated() {
        return created;
    }

    public int getBatch() {
        return batch;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isProductStatus() {
        return productStatus;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setManufacturer(String manufacturer) {
        Manufacturer = manufacturer;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public void setBatch(int batch) {
        this.batch = batch;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setProductStatus(boolean productStatus) {
        this.productStatus = productStatus;
    }

    public void inputData(Scanner input, List<Product> productList) {
        this.productId = validateProductId(input, productList);
        this.productName = validateProductName(input);
        this.Manufacturer = validateManufacturer(input);
        this.created = validateDateCreated(input);
        this.batch = validateBatch(input);
        this.quantity = validateQuantity(input);
        this.productStatus = validateProductStatus(input);
    }

    public String validateProductId(Scanner input, List<Product> productList) {
        System.out.println("Please input the product ID:");
        do {
            boolean checkProductId = false;
            String productId = input.nextLine();
            if (!productId.trim().equals("")) {
                if (productId.length() == 5) {
                    if (productList.size() == 0) {
                        return productId;
                    } else {
                        for (Product product : productList) {
                            if (productId.equals(product.getProductId())) {
                                checkProductId = true;
                                break;
                            }
                        }
                        if (!checkProductId) {
                            return productId;
                        } else {
                            System.err.println("The inputted ID already existed! Please try another.");
                        }
                    }
                } else {
                    System.err.println("The inputted ID should contains 5 characters!");
                }
            } else {
                System.err.println("The ID of product should not be a blank!");
            }
        } while (true);
    }

    public String validateProductName(Scanner input) {
        System.out.println("Please input name of product:");
        do {
            String productName = input.nextLine();
            if (productName.trim().length() <= 150 && !productName.trim().equals("")) {
                return productName;
            } else {
                System.err.println("The product name should not be a blank and contains less than or " +
                        "equal 150 character");
            }
        } while (true);
    }

    public String validateManufacturer(Scanner input) {
        System.out.println("Please input name of manufacturer:");
        do {
            String manufacturer = input.nextLine();
            if (!manufacturer.trim().equals("") && manufacturer.trim().length() <= 200) {
                return manufacturer;
            } else {
                System.err.println("The manufacturer name should not be a blank and" +
                        " contains less than or equal 200 characters!");
            }
        } while (true);
    }

    public String validateDateCreated(Scanner input) {
        System.out.println("Please input the created date of product:");
        do {
            String createdDate = input.nextLine();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            try {
                sdf.parse(createdDate);
                return createdDate;
            } catch (ParseException ex) {
                System.err.println("The inputted date is not following standard format: YYYY-MM-DD! " +
                        ex.getMessage());
            } catch (Exception e) {
                System.err.println("Some errs occur while inputting created date! " + e.getMessage());
            }
        } while (true);
    }

    public Integer validateBatch(Scanner input) {
        System.out.println("Please input batch of product:");
        do {
            String batchStr = input.nextLine();
            if (!batchStr.trim().equals("")) {
                try {
                    int batch = Integer.parseInt(batchStr);
                    return batch;
                } catch (NumberFormatException ex) {
                    System.out.println("The inputted batch does not have an integer format! Please try again! " +
                            ex.getMessage());
                } catch (Exception e) {
                    System.out.println("Some errs occur while inputting batch! " + e.getMessage());
                }
            }
        } while (true);
    }

    public Integer validateQuantity(Scanner input) {
        System.out.println("Please input the quantity of product:");
        do {
            String quantityStr = input.nextLine();
            if (!quantityStr.trim().equals("")) {
                try {
                    int quantity = Integer.parseInt(quantityStr);
                    return quantity;
                } catch (NumberFormatException ex) {
                    System.err.println("The inputed quantity does not have integer format! " + ex.getMessage());
                } catch (Exception e) {
                    System.err.println("Some errs occur while inputting quantity! " + e.getMessage());
                }
            } else {
                System.err.println("The quantity should not be a blank!");
            }
        } while (true);
    }

    public Boolean validateProductStatus(Scanner input) {
        System.out.println("Please input status of product:");
        do {
            String statusStr = input.nextLine();
            if (!statusStr.trim().equals("")) {
                if (statusStr.trim().equalsIgnoreCase("true") ||
                        statusStr.trim().equalsIgnoreCase("false")) {
                    return Boolean.parseBoolean(statusStr);
                } else {
                    System.err.println("The status of product should be 'true' or 'false'!");
                }
            } else {
                System.err.println("The status of product should no be a blank!");
            }
        } while (true);
    }

    public void displayData() {
        String status = this.productStatus ? "Active" : "Inactive";
        System.out.print("--------------------------------------------------------------------------------" +
                "-----------------------------------------------------------------------------\n");
        System.out.printf("| %-15s | %-25s | %-25s | %-20s | %-15d | %-15d | %-20s |\n", this.productId,
                this.productName, this.Manufacturer, this.created, this.batch, this.quantity, status);
    }
}
