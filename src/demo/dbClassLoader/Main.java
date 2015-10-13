package demo.dbClassLoader;

public class Main {
	public static void main(String[] args) {
		try {
			MysqlClassLoader cl = new MysqlClassLoader("jdbc:mysql://localhost/test?user=root&password=1234");
			Class clazz = cl.findClass("Foo");
			
			// we use 'Object' here just for convenience.
			// we typically will want to have an interface instead of the 'Object'
			// and the implementation which is the class we load dynamically from the database.
			Object obj = (Object) clazz.newInstance();
			System.out.println(obj.toString());
		} catch (Exception e) {
			System.out.println("Unexpected exception: " + e);
		}
	}
}
