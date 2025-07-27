import java.util.*;
import java.sql.*;
public class LoginImpl implements LoginDAO{
    Connection conn;
    Scanner sc;
    PreparedStatement ps;
    LoginImpl(Scanner sc,Connection conn)
    {
        this.conn=conn;
        this.sc=sc;
    }
    public void logindetails() {
        System.out.println("Enter your username : ");
        String name=sc.nextLine();
        System.out.println("Enter your password");
        String pass=sc.nextLine();
        int userid=authendicateUser(name,pass);
        if(userid==-1)
        {
            System.out.println("User Not Found !!! ");
            System.out.println("Register First and then Login");
            System.out.println("Do you want to Register");
            System.out.println("1.YES \n2.NO");
            int num=sc.nextInt();
            sc.nextLine();
            if(num==1)
            {
                RegistrationImpl register = new RegistrationImpl(sc,conn);
                register.registerationdatails();
            }
            else System.out.println("Exiting.....!");
        }
        else {
            System.out.println("LOGIN SUCESSFUL.... WELCOME "+name.toUpperCase()+" !");
            WelcomePage welcome=new WelcomePage(conn,sc,userid);
            welcome.showOptions();
        }
    }

    @Override
    public int authendicateUser(String name, String pass) {
        String sql="SELECT user_id FROM login WHERE username = ? AND password = ?";
        try {
            ps=conn.prepareStatement(sql);
            ps.setString(1,name);
            ps.setString(2,pass);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
            {
                return rs.getInt("user_id");
            }
            return -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
