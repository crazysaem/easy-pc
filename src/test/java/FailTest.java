package test.java;
import static org.junit.Assert.*;

import org.junit.Test;


public class FailTest {

	@Test
	public void test() {
		fail("This will fail");
	}

}
