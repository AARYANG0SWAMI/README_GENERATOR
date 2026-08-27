import java.sql.Connection;

public class Main {

    public static void main(String[] args) {

        Connection connection = Database.getConnection();

        if (connection != null) {
            System.out.println("MySQL connection successful!");
        } else {
            System.out.println("MySQL connection failed!");
        }
    }
}
