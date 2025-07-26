import java.sql.Connection;
import java.sql.SQLException;

public class Fitness_Tracker {
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()) {
            FrontPage f = new FrontPage(conn);
            f.getInfo();
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
    }
}
