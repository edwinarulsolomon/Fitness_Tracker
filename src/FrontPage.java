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
        do {
            System.out.println(" WELCOME TO FITNESS TRACKER ");
            System.out.println("-----------------------------");

            System.out.println("1.Login");
            System.out.println("2.Register");
            System.out.println("3.Exit");
            System.out.println("please enter number between 1 to 3");
            input = sc.nextInt();
            sc.nextLine();
        } while (input <= 0 && input > 3);
        switch (input) {
            case 1:
                Login login = new Login(sc,conn);
                login.logindetails();
                break;

            case 2:
                Registration register = new Registration(sc,conn);
                register.registerationdatails();
                break;

            case 3:
                System.out.println("Exiting......");
                break;
        }
    }
}
