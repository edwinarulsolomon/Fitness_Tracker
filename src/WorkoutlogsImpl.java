import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class WorkoutlogsImpl {
    Scanner sc;
    Connection conn;
    PreparedStatement ps;
    WorkoutlogsImpl(Connection conn,Scanner sc)
    {
        this.conn=conn;
        this.sc=sc;
    }
    public void showWorkOutLogs(int userid, String date) {
        try {
            String getLevelQuery = "SELECT level FROM user_details WHERE user_id = ?";
            ps = conn.prepareStatement(getLevelQuery);
            ps.setInt(1, userid);
            ResultSet rs = ps.executeQuery();

            String level = null;
            if (rs.next()) {
                level = rs.getString("level");
            } else {
                System.out.println("User not found.");
                return;
            }

            String sql = "SELECT * FROM workout_logs WHERE user_id=? AND date=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userid);
            ps.setString(2, date);
            rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("\nWorkout Logs of you on" + date + ":");

                switch (level.toLowerCase()) {
                    case "beginner":
                        System.out.println("Pushups: " + rs.getInt("pushups"));
                        System.out.println("Pullups: " + rs.getInt("pullups"));
                        System.out.println("Jumping Jacks: " + rs.getInt("jumping_jacks"));
                        System.out.println("Squats: " + rs.getInt("squats"));
                        break;
                    case "intermediate":
                        System.out.println("Pushups: " + rs.getInt("pushups"));
                        System.out.println("Pullups: " + rs.getInt("pullups"));
                        System.out.println("Jumping Jacks: " + rs.getInt("jumping_jacks"));
                        System.out.println("Squats: " + rs.getInt("squats"));
                        System.out.println("Plank: " + rs.getInt("plank"));
                        System.out.println("Jumping Squats: " + rs.getInt("jumping_squats"));
                        System.out.println("Deadlifts: " + rs.getInt("deadlifts"));
                        break;
                    case "advanced":
                        System.out.println("Pushups: " + rs.getInt("pushups"));
                        System.out.println("Pullups: " + rs.getInt("pullups"));
                        System.out.println("Jumping Jacks: " + rs.getInt("jumping_jacks"));
                        System.out.println("Squats: " + rs.getInt("squats"));
                        System.out.println("Plank: " + rs.getInt("plank"));
                        System.out.println("Jumping Squats: " + rs.getInt("jumping_squats"));
                        System.out.println("Deadlifts: " + rs.getInt("deadlifts"));
                        System.out.println("Weighted Squats: " + rs.getInt("weighted_squats"));
                        System.out.println("Running: " + rs.getInt("running"));
                        break;
                    default:
                        System.out.println("Invalid level.");
                }

                System.out.println("Calories Burnt: " + rs.getInt("calories_burnt_during_workout"));
                System.out.println("Protein Intake: " + rs.getInt("protein_intake_during_workout"));
            } else {
                System.out.println("No workout logs found for this date.");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
