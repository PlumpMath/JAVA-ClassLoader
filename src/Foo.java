import demo.urlClassLoader.MyInterface;

public class Foo implements MyInterface {

	@Override
	public void invoke() {
		System.out.println("Invoking...");
	}

}
