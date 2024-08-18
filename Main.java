import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String url="jdbc:mysql://localhost:3306/metro";
        String username ="root";
        String password = "root";

        try(Connection connection = DriverManager.getConnection(url,username,password)){
            System.out.println("Connected To Dtaabase");
        }catch(SQLException e){
            System.out.println("Failed");
        }
    }
}
