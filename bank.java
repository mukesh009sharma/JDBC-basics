package bank;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class bank {
    public static void main(String[] args) throws IOException {
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        bankManagement bankmanagement = new bankManagement();
        String name = "";
        int Pass_code;
        int ch;

        while (true) {
            System.out.println("\n->|| Welcome To ApnaBank ||<-\n");
            System.out.println("1) Create Account");
            System.out.println("2) Login Account");
            System.out.println("5) Exit");

            try {
                System.out.print("\nEnter Input: ");
                ch = Integer.parseInt(sc.readLine());

                switch (ch) {
                    case 1:
                        try {
                            System.out.print("Enter unique Username: ");
                            name = sc.readLine();
                            System.out.print("Enter the New Password: ");
                            Pass_code = Integer.parseInt(sc.readLine());
                            if (bankmanagement.createAccount(name, Pass_code)) {
                                System.out.println("MSG: Account Created Successfully.\n");
                            } else {
                                System.out.println("ERR: Account Creation Failed.\n");
                            }
                        } catch (Exception e) {
                            System.out.println("ERR: Enter Valid Data. Insertion Failed!\n");
                        }
                        break;

                    case 2:
                        try {
                            System.out.print("Enter Username: ");
                            name = sc.readLine();
                            System.out.print("Enter Password: ");
                            Pass_code = Integer.parseInt(sc.readLine());
                            if (bankmanagement.loginAccount(name, Pass_code)) {
                                System.out.println("MSG: Logged Out Successfully!\n");
                            } else {
                                System.out.println("ERR: Login Failed!\n");
                            }
                        } catch (Exception e) {
                            System.out.println("ERR: Enter Valid Data. Login Failed!\n");
                        }
                        break;

                    case 5:
                        System.out.println("Exited Successfully!\n\nThank You :)");
                        return;

                    default:
                        System.out.println("Invalid Entry!\n");
                }
            } catch (Exception e) {
                System.out.println("Enter Valid Entry!");
            }
        }
    }
}
