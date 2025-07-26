import java.util.Scanner;
import java.sql.*;
public class RegistrationImpl implements RegistrationDAO{
    Scanner sc;
    Connection conn;
    PreparedStatement ps;
    RegistrationImpl(Scanner sc,Connection conn)
    {
        this.conn=conn;
        this.sc=sc;
    }
    public void registerationdatails()
    {
        System.out.println("Enter your Name");
        String name=sc.nextLine();
        System.out.println("Enter your Age");
        int age=sc.nextInt();
        sc.nextLine();
        System.out.println("Enter your Gender");
        String gender=sc.nextLine();
        System.out.println("Enter your Height");
        float height=sc.nextFloat();
        sc.nextLine();
        System.out.println("Enter your Weight");
        float weight=sc.nextFloat();
        sc.nextLine();
        System.out.println("Enter your Level (Beginner ,Intermediate ,Advanced");
        String level=sc.nextLine();
        System.out.println("Set your Password ");
        String pass=sc.nextLine();
        int id=setUserLogin(name,pass);
        setUserDetails(id,age,gender,height,weight,level);
    }

    @Override
    public void setUserDetails(int userid, int age, String gender, float height, float weight, String level) {
        String sql = "INSERT INTO user_details (user_id, age, gender, height, weight, level) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            ps=conn.prepareStatement(sql);
            ps.setInt(1,userid);
            ps.setInt(2,age);
            ps.setString(3,gender);
            ps.setFloat(4,height);
            ps.setFloat(5,weight);
            ps.setString(6,level);
            ps.executeUpdate();
            System.out.println("Registration Sucessfull");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int setUserLogin(String name, String pass) {
        String sql = "INSERT INTO login (username, password) VALUES (?, ?)";
        try {
            ps=conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1,name);
            ps.setString(2,pass);
            ps.executeUpdate();
            ResultSet rs=ps.getGeneratedKeys();
            int userid=0;
            if(rs.next()) userid=rs.getInt(1);
            return userid;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
