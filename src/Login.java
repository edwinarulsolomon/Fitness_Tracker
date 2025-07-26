import java.util.*;
import java.sql.*;
public class Login {
    Connection conn;
    Scanner sc;
    Login(Scanner sc,Connection conn)
    {
        this.conn=conn;
        this.sc=sc;
    }
    public void logindetails() {
        System.out.println("Enter your username : ");
        sc.nextLine();
        String user=sc.nextLine();
        System.out.println("Enter your password");
        String pass=sc.nextLine();
    }
}
