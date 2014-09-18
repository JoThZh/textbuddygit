package textbuddies;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;


//Test used to test search function
public class Test1 {

	File file = new File("newFile.txt");
	@Test 
	public void testSearch() throws IOException{
		
		BufferedReader br = new BufferedReader(new FileReader(file));
		assertEquals("expect item to be found","item",TextBuddy.fileSearch("search item",file,br));//after item is already inserted
		//fail("Not yet implemented");
		br.close();
	}
	
	@Test(expected = NullPointerException.class)
	  public void testExceptionIsThrown() throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(file));
		TextBuddy.fileSearch("search item",file,br);
		br.close();
	  }
}
