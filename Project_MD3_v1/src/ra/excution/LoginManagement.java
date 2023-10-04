package ra.excution;

import ra.business.AccountBusiness;

import ra.entity.Account;
import java.io.*;
import java.util.Scanner;

public class LoginManagement {
    /**
     * Text color
     */
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";

    /**
     * Bold format
     */
    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE

    public static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
/*
        System.out.println("---------------------ACCOUNT LOGIN---------------------");
        System.out.println("1. Login as Administrator");
        System.out.println("2. Login as User");
        System.out.println("3. Exit");
        System.out.println("Please enter your choice(1~3):");
        try {
            int choice = Integer.parseInt(input.nextLine());
            switch (choice) {
                case 1:
                    WarehouseManagement.warehouseManagementForAdmin();
                    break;
                case 2:
                    WarehouseManagement.warehouseManagementForUser();
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.err.println("The inputted choicee is out of scope(1~3)!");
            }
        } catch (NumberFormatException ex) {
            System.out.println("The inputted choice does not have an integer format! Please try again! " +
                    ex.getMessage());
        } catch (Exception e) {
            System.out.println("Some errs occur while inputting choice! " + e.getMessage());
        }
*/
        System.out.println("------------------------ACCOUNT LOGIN----------------------");
        do {
            System.out.println("Please input user name:");
            String userName = input.nextLine();
            System.out.println("Please input password:");
            String password = input.nextLine();
            // check in database if inputted userName exists
            Account account = AccountBusiness.getAccountByUsername(userName);
            LoginManagement.writeUserNameToFile(userName);
            if (account != null) {
                if (!account.isPermission() && account.isAccStatus() && account.getPassword().trim().equals(password)) {
                    WarehouseManagementForAdmin.warehouseManagementForAdmin();
                    return;
                }
                if (account.isPermission() && account.isAccStatus() && account.getPassword().trim().equals(password)) {
                    WarehouseManagementForUser.warehouseManagementForUser();
                    return;
                }
            } else {
                System.out.println(ANSI_RED + "The inputted username does not exist!" + ANSI_RESET);
            }
        } while (true);
    }

    public static void writeUserNameToFile(String userName) {
        File newUserName = new File("userName.txt");
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(newUserName);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(userName);
            oos.flush();
        } catch (FileNotFoundException ex1) {
            System.err.println("Cannot find the file!" + ex1.getMessage());
        } catch (IOException ex2) {
            System.err.println("Err appears while writing stream!" + ex2.getMessage());
        } catch (Exception ex) {
            System.err.println("There are some errs occur while writing stream!" + ex.getMessage());
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException ex1) {
                System.err.println("Err appears while closing stream!");
            } catch (Exception ex) {
                System.err.println("There are some errs occur while closing stream");
            }
        }
    }

    public static String readUserNameFromFile() {
        File newBookRead = new File("userName.txt");
        if (newBookRead.exists()) {
            FileInputStream fis = null;
            ObjectInputStream ois = null;
            try {
                fis = new FileInputStream(newBookRead);
                ois = new ObjectInputStream(fis);
                String userName = (String) ois.readObject();
                return userName;
            } catch (FileNotFoundException ex1) {
                System.err.println("Cannot find the file!");
            } catch (IOException ex2) {
                System.err.println("Err appears while reading stream!");
            } catch (Exception ex) {
                System.err.println("There are some errs occur while reading stream!");
            } finally {
                try {
                    if (fis != null) {
                        fis.close();
                    }
                    if (ois != null) {
                        ois.close();
                    }
                } catch (IOException ex1) {
                    System.err.println("Err appears while closing stream!");
                } catch (Exception ex) {
                    System.err.println("There are some errs occur while closing stream!");
                }
            }
        }
        return null;
    }

}
