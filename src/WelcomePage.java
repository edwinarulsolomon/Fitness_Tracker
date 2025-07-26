import java.sql.Connection;
import java.util.*;

public class WelcomePage {
    Scanner sc;
    Connection conn;
    private int userid;
    WelcomePage(Connection conn,Scanner sc,int userid)
    {
        this.conn=conn;
        this.sc=sc;
        this.userid=userid;
    }
    public void showOptions() {
        System.out.println("1.Set Goals\n2.Today's Progress\n3.Show Workout History\n4.Show Progress\n5.FeedBack");
        int choice=sc.nextInt();
        while(choice<1 || choice>5)
        {
            System.out.println("Invalid Input Enter number between 1 to 5");
            choice=sc.nextInt();
        }
        switch (choice)
        {
            case 1:
                GoalImpl g=new GoalImpl(conn,sc);
                g.setGoals(userid);
        }
    }
}
