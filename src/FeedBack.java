import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class FeedBack {
    Connection conn;
    Scanner sc;
    PreparedStatement ps;
    FeedBack(Connection conn,Scanner sc)
    {
        this.conn=conn;
        this.sc=sc;
    }
    public void addFeedBack(int userid) {
        String sql = "INSERT INTO feedback (user_id, feedbacks) VALUES (?, ?)";
        System.out.println("Enter your Feedback here : ");
        String feedback=sc.nextLine();
        try
        {
            ps=conn.prepareStatement(sql);
            ps.setInt(1,userid);
            ps.setString(2,feedback);
            int rows=ps.executeUpdate();
            if(rows>0) System.out.println("Thank you !! Your FeedBack has been sent Sucessfully !!!");
            else System.out.println("Oops Something went wrong try again !");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
