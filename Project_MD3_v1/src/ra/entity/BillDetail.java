package ra.entity;

import java.util.List;
import java.util.Scanner;

public class BillDetail {
    private int billDetailId;
    private int billId;
    private String productId;
    private int quantity;
    private float price;

    public BillDetail() {
    }

    public BillDetail(int billDetailId, int billId, String productId, int quantity, float price) {
        this.billDetailId = billDetailId;
        this.billId = billId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public int getBillDetailId() {
        return billDetailId;
    }

    public int getBillId() {
        return billId;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setBillDetailId(int billDetailId) {
        this.billDetailId = billDetailId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void inputData(Scanner input, List<BillDetail> billDetailList) {
        this.billDetailId = validateBillDetailId(input, billDetailList);
        this.quantity = validateQuantity(input);
        this.price = validatePrice(input);
    }

    public Integer validateBillDetailId(Scanner input, List<BillDetail> billDetailList) {
        System.out.println("Please input bill/receipt detail ID:");
        do {
            String billDetailIdStr = input.nextLine();
            if (!billDetailIdStr.trim().equals("")) {
                try {
                    int billDetailId = Integer.parseInt(billDetailIdStr);
                    if (billDetailList.size() == 0) {
                        return billDetailId;
                    } else {
                        boolean checkBillDetailId = false;
                        for (BillDetail billDetail : billDetailList) {
                            if (billDetailId == billDetail.getBillDetailId()) {
                                checkBillDetailId = true;
                                break;
                            }
                        }
                        if (!checkBillDetailId) {
                            return billDetailId;
                        } else {
                            System.err.println("The inputted bill/receipt detail ID already existed!");
                        }
                    }
                } catch (NumberFormatException ex) {
                    System.err.println("The inputted bill/receipt detail ID does not have an integer format! " +
                            ex.getMessage());
                } catch (Exception e) {
                    System.err.println("Some errs occur while inputting bill/receipt detail ID! " + e.getMessage());
                }
            } else {
                System.err.println("The bill/receipt detail ID should not be a blank!");
            }
        } while (true);
    }

    public Integer validateQuantity(Scanner input) {
        System.out.println("Please input quantity of product for this bill/receipt:");
        do {
            String quantityStr = input.nextLine();
            if (!quantityStr.trim().equals("")) {
                try {
                    int quantity = Integer.parseInt(quantityStr);
                    if (quantity > 0) {
                        return quantity;
                    } else {
                        System.err.println("The quantity should be greater than 0");
                    }
                } catch (NumberFormatException ex) {
                    System.err.println("The quantity does not have an integer format! " + ex.getMessage());
                } catch (Exception e) {
                    System.err.println("Some errs occur while inputting quantity! " + e.getMessage());
                }
            } else {
                System.err.println("The quantity should not be a blank!");
            }
        } while (true);
    }

    public Float validatePrice(Scanner input) {
        System.out.println("Please input price of product for this bill/receipt:");
        do {
            String priceStr = input.nextLine();
            if (!priceStr.trim().equals("")) {
                try {
                    float price = Float.parseFloat(priceStr);
                    if (price > 0) {
                        return price;
                    } else {
                        System.err.println("The price should be greater than 0");
                    }
                } catch (NumberFormatException ex) {
                    System.err.println("The price does not have an integer format! " + ex.getMessage());
                } catch (Exception e) {
                    System.err.println("Some errs occur while inputting price! " + e.getMessage());
                }
            } else {
                System.err.println("The price should not be a blank!");
            }
        } while (true);
    }

    public void displayData() {
        System.out.printf("| %-25d | %-20d | %-15s | %-15d | %-15.1f |\n", this.billDetailId, this.billId, this.productId,
                this.quantity, this.price);
    }
}
