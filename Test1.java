package textbuddies;
import static org.junit.Assert.*;
import java.io.File;
import org.junit.Test;

//Test used to test search function
public class Test1 {

	@Test 
	public void testSearch() {
				
		assertEquals("expect item to be found","item",TextBuddy.search("item"));//after item is already inserted
		//fail("Not yet implemented");
	}

}
