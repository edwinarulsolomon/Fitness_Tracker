import java.sql.*;
import java.util.*;

public class FrontPage {

    Scanner sc=new Scanner(System.in);
    Connection conn;
    public FrontPage(Connection conn) {
        this.conn=conn;
    }

    public void getInfo() {
        int input;

        while (true) {
            System.out.println(" WELCOME TO FITNESS TRACKER ");
            System.out.println("-----------------------------");

            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.println("------------------------------");
            System.out.println("Please Enter Number between 1 to 3");
            input = sc.nextInt();
            sc.nextLine();
            while (input < 1 || input > 3) {
                System.out.println("Invalid input. Please enter a number between 1 to 3");
                input = sc.nextInt();
                sc.nextLine();
            }

            switch (input) {
                case 1:
                    LoginImpl login = new LoginImpl(sc, conn);
                    login.logindetails();
                    break;

                case 2:
                    RegistrationImpl register = new RegistrationImpl(sc, conn);
                    register.registerationdatails();
                    break;

                case 3:
                    System.out.println("Exiting......");
                    return;
            }
        }
    }
}
