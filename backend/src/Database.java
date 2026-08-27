import java.sql.Connection;
import java.sql.DriverManager;

public class Database {

    private static final String URL =
            "jdbc:mysql://localhost:3306/codeforces";

    private static final String USER =
            "javauser";

    private static final String PASSWORD =
            "Java@12345";

    public static Connection getConnection() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );

        } catch (Exception e) {

            e.printStackTrace();

            return null;
        }
    }
}
