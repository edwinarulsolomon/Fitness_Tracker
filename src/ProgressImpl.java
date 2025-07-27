import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ProgressImpl implements ProgressDAO {
    private Scanner sc;
    private Connection conn;
    PreparedStatement ps;
    private int calories;
    private int protein;
    String remark;

    ProgressImpl(Connection conn, Scanner sc) {
        this.conn = conn;
        this.sc = sc;
    }

    @Override
    public void showProgress(int userid, String date) {
        String sql = "SELECT * FROM progress WHERE user_id = ? AND date = ? ORDER BY progress_id DESC LIMIT 1";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userid);
            ps.setString(2, date);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                calories = rs.getInt("calories_burnt");
                protein = rs.getInt("protein_intake");
                remark = rs.getString("remark");

                System.out.println("TODAY'S CALORIES BURNT: " + calories);
                System.out.println("TODAY'S PROTEIN INTAKE: " + protein);
                System.out.println("OVERALL REMARK: " + remark);
            } else {
                System.out.println("No progress data found for this user on this date.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void setProgress(int userid, String date) {
        String sql;
        try {
            sql = "SELECT level FROM user_details WHERE user_id=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userid);
            ResultSet rs = ps.executeQuery();

            String level = null;
            if (rs.next()) level = rs.getString("level");
            int pushups = 0, pullups = 0, jumping_jacks = 0, squats = 0;
            int plank = 0, jumping_squats = 0, deadlifts = 0;
            int weighted_squats = 0, running = 0;
            int inputCalories = 0, inputProtein = 0;

            if (level != null) {
                if (level.equalsIgnoreCase("Beginner") || level.equalsIgnoreCase("Intermediate") || level.equalsIgnoreCase("Advanced")) {
                    System.out.print("Enter Pushups: "); pushups = sc.nextInt(); sc.nextLine();
                    System.out.print("Enter Pullups: "); pullups = sc.nextInt(); sc.nextLine();
                    System.out.print("Enter Jumping Jacks: "); jumping_jacks = sc.nextInt(); sc.nextLine();
                    System.out.print("Enter Squats: "); squats = sc.nextInt(); sc.nextLine();
                }

                if (level.equalsIgnoreCase("Intermediate") || level.equalsIgnoreCase("Advanced")) {
                    System.out.print("Enter Plank (sec): "); plank = sc.nextInt(); sc.nextLine();
                    System.out.print("Enter Jumping Squats: "); jumping_squats = sc.nextInt(); sc.nextLine();
                    System.out.print("Enter Deadlifts: "); deadlifts = sc.nextInt(); sc.nextLine();
                }

                if (level.equalsIgnoreCase("Advanced")) {
                    System.out.print("Enter Weighted Squats: "); weighted_squats = sc.nextInt(); sc.nextLine();
                    System.out.print("Enter Running (m): "); running = sc.nextInt(); sc.nextLine();
                }
            }

            System.out.print("Enter Calories Burnt: "); inputCalories = sc.nextInt(); sc.nextLine();
            System.out.print("Enter Protein Intake: "); inputProtein = sc.nextInt(); sc.nextLine();
            sql = "SELECT * FROM workout_logs WHERE user_id = ? AND date = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userid);
            ps.setString(2, date);
            rs = ps.executeQuery();

            if (rs.next()) {
                pushups += rs.getInt("pushups");
                pullups += rs.getInt("pullups");
                jumping_jacks += rs.getInt("jumping_jacks");
                squats += rs.getInt("squats");
                plank += rs.getInt("plank");
                jumping_squats += rs.getInt("jumping_squats");
                deadlifts += rs.getInt("deadlifts");
                weighted_squats += rs.getInt("weighted_squats");
                running += rs.getInt("running");
                inputCalories += rs.getInt("calories_burnt_during_workout");
                inputProtein += rs.getInt("protein_intake_during_workout");
            }
            sql = """
                REPLACE INTO workout_logs (
                    user_id, date, pushups, pullups, jumping_jacks, squats, plank,
                    jumping_squats, deadlifts, weighted_squats, running,
                    calories_burnt_during_workout, protein_intake_during_workout
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
              """;
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userid);
            ps.setString(2, date);
            ps.setInt(3, pushups);
            ps.setInt(4, pullups);
            ps.setInt(5, jumping_jacks);
            ps.setInt(6, squats);
            ps.setInt(7, plank);
            ps.setInt(8, jumping_squats);
            ps.setInt(9, deadlifts);
            ps.setInt(10, weighted_squats);
            ps.setInt(11, running);
            ps.setInt(12, inputCalories);
            ps.setInt(13, inputProtein);
            ps.executeUpdate();

            int goalCalories = 0, goalProtein = 0;
            sql = "SELECT calories_burn_perday, protein_intake_perday FROM goals WHERE user_id=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userid);
            rs = ps.executeQuery();
            if (rs.next()) {
                goalCalories = rs.getInt("calories_burn_perday");
                goalProtein = rs.getInt("protein_intake_perday");
            }

            if (inputCalories >= goalCalories && inputProtein >= goalProtein)
                remark = "Great Progress";
            else if (inputCalories >= goalCalories * 0.7 || inputProtein >= goalProtein * 0.7)
                remark = "Better Progress";
            else
                remark = "Poor Progress";

            sql = """
                INSERT INTO progress (user_id, date, calories_burnt, protein_intake, remark)
                VALUES (?, ?, ?, ?, ?)
                ON DUPLICATE KEY UPDATE
                    calories_burnt = VALUES(calories_burnt),
                    protein_intake = VALUES(protein_intake),
                    remark = VALUES(remark)
              """;
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userid);
            ps.setString(2, date);
            ps.setInt(3, inputCalories);
            ps.setInt(4, inputProtein);
            ps.setString(5, remark);
            int rowsAffected = ps.executeUpdate();

            System.out.println("Progress " + (rowsAffected > 0 ? "saved" : "not saved") + " successfully.");
            System.out.println("CALORIES BURNT : " + inputCalories + ",PROTEIN INTAKE: " + inputProtein + ",REMARK: " + remark);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
