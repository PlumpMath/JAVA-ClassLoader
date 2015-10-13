package demo.dbClassLoader;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// A custom class loader which loads classes from a database.
public class MysqlClassLoader extends ClassLoader {
	private ClassLoader parent;
	private String connectionString;
	
	public MysqlClassLoader(String connectionString) {
		this(ClassLoader.getSystemClassLoader(), connectionString);
	}
	
	// Creates a new class loader using the specified parent class loader for delegation.
	public MysqlClassLoader(ClassLoader parent, String connectionString) {
		super(parent);
		this.parent = parent;
		this.connectionString = connectionString;
	}
	
	// This method should be overridden by class loader implementations that follow the delegation model for loading classes, 
	// and will be invoked by the loadClass method after checking the parent class loader for the requested class.
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		Class cls = null;
		try {
			cls = this.parent.loadClass(name);
		} catch (ClassNotFoundException e) {
			byte[] bytes;
			try {
				
				// we want to get to this part.
				bytes = this.loadClassFromDatabase(name);
			} catch (SQLException sqle) {
				throw new ClassNotFoundException("Unable to load class", sqle);
			}
			
			// Converts an array of bytes into an instance of class Class. 
			// Before the Class can be used it must be resolved.
			return this.defineClass(name, bytes, 0, bytes.length);
		}
		return cls;
	}
	
	// fetching the class BLOB from mysql as array of bytes.
	private byte[] loadClassFromDatabase(String name) throws SQLException{
		PreparedStatement pstmt = null;
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(this.connectionString);
			String sql = "select data from clazz where name= ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				Blob blob = rs.getBlob(1);
				byte[] data = blob.getBytes(1, (int)blob.length());
				return data;
			}
		} catch (SQLException sqlex) {
			System.out.println("Unexpected exception: " + sqlex.toString());
		} catch (Exception e) {
			System.out.println("Unexpected exception: " + e.toString());
		} finally {
			if (pstmt != null) pstmt.close();
			if (connection != null) connection.close();
		}
		
		return null;
	}
}
