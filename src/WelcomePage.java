import jdk.jshell.spi.SPIResolutionException;

import java.sql.Connection;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WelcomePage {
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    Scanner sc;
    Connection conn;
    private int userid;
    ProgressImpl progress;
    WelcomePage(Connection conn,Scanner sc,int userid)
    {
        this.conn=conn;
        this.sc=sc;
        this.userid=userid;
        progress=new ProgressImpl(conn,sc);
    }
    public void showOptions() {
        while(true) {
            System.out.println("------------------------------------------");
            System.out.println("1.Set Goals\n2.Today's Progress\n3.Show Workout History\n4.Show Progress\n5.FeedBack\n6.Logout");
            int choice = sc.nextInt();
            sc.nextLine();
            while (choice < 1 || choice > 6) {
                System.out.println("Invalid Input. Enter number between 1 to 6");
                choice = sc.nextInt();
                sc.nextLine();
            }

            switch (choice) {
                case 1:
                    GoalImpl g = new GoalImpl(conn, sc);
                    g.setGoals(userid);
                    break;
                case 2:
                    progress.setProgress(userid, now.format(format));
                    break;
                case 3:
                    WorkoutlogsImpl workout = new WorkoutlogsImpl(conn, sc);
                    workout.showWorkOutLogs(userid, now.format(format));
                    break;
                case 4:
                    progress.showProgress(userid, now.format(format));
                    break;
                case 5:
                    FeedBack feedBack = new FeedBack(conn, sc);
                    feedBack.addFeedBack(userid);
                    break;
                case 6:
                {
                    System.out.println("You have been logged out successfully . . . !!!");
                    return;
                }
            }
        }
    }
}
