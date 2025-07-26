import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class GoalImpl implements GoalDAO{
    Connection conn;
    Scanner sc;
    PreparedStatement ps;
    GoalImpl(Connection conn, Scanner sc)
    {
        this.conn=conn;
        this.sc=sc;
    }
    public void setGoals(int userid) {
        System.out.println("Enter your Target weight:");
        float weight=sc.nextFloat();
        sc.nextLine();
        System.out.println("Enter your Calories Burn for a day:");
        int caloriesburn=sc.nextInt();
        sc.nextLine();
        System.out.println("Enter your Protein Intake for a day:");
        int proteinintake=sc.nextInt();
        setGoals(userid,weight,proteinintake,caloriesburn);
        WelcomePage welcome=new WelcomePage(conn,sc,userid);
        welcome.showOptions();
    }

    @Override
    public void setGoals(int userid, float weight, int protein, int calories) {
        try
        {
            String sql="REPLACE INTO goals (user_id,calories_burn_perday,protein_intake_perday,target_weight) VALUES (?,?,?,?)";
            ps=conn.prepareStatement(sql);
            ps.setInt(1,userid);
            ps.setInt(2,calories);
            ps.setInt(3,protein);
            ps.setFloat(4,weight);
            int row=ps.executeUpdate();
            if(row>0)
            {
                System.out.println("Your goals have been successfully saved !!!");
            }
            else System.out.println("Your goal have not been saved");
        }
        catch(SQLException e)
        {
            e.getMessage();
        }
    }
}
