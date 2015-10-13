package demo.dbClassLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

// simple Main program to show how we can save a java .class file in mysql.
public class InsertClassToDb {
	public static void main(String[] args) throws FileNotFoundException {
		String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "1234";
        String filePath = "Foo.class";
        
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            
            // By default, MySQL sets a limit on the amount of data can be sent in a 
            // query (including both the file data and other query’s data). This limit is 1MB 
            // and can be configured via a property called max_allowed_packet. If we are 
            // trying to store a file whose size exceeds this limit, MySQL will throw this error:
            // ----- com.mysql.jdbc.PacketTooBigException: Packet for query is too large (4384068 > 1048576).
            // You can change this value on the server by setting the max_allowed_packet' variable -----.
            
            // Because 1 MB limit is quite small for binary file, so we usually have to set a higher 
            // value when working with blob type. There are two common ways for setting this limit:
            
            // 1.
            // Via MySQL’s configuration file my.ini: Open my.ini file and append the following line at the end:
            // max_allowed_packet=104857600
            
            // 2.
            // Via SQL statement: We can also configure the max_allowed_packet variable from the client by sending the 
            // following SQL statement before inserting the file:
            String querySetLimit = "SET GLOBAL max_allowed_packet=104857600;";  // 10 MB
            Statement stSetLimit = conn.createStatement();
            stSetLimit.execute(querySetLimit);
            
            String sql = "INSERT INTO classes (name, data) values (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            
            // get the file as raw bytes to be saved as a BLOB in mysql.
            InputStream inputStream = new FileInputStream(new File(filePath));
            
            // set statement.
            statement.setString(1, "Foo");
            statement.setBlob(2, inputStream);
            
            // execute statement.
            int row = statement.executeUpdate();
            if (row > 0) {
                System.out.println("A jave .class file was inserted.");
            }
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } 
	}
}
