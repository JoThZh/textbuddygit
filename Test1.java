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
		assertEquals("item",TextBuddy.fileSearch("search item",br));//after 'item' is already inserted
		//fail("Not yet implemented");
		br.close();
	}

	@Test
	public void testSearch2() throws IOException{

		BufferedReader br = new BufferedReader(new FileReader(file));
		assertEquals("Word not found",TextBuddy.fileSearch("search object",br));//'object' not inserted
		//fail("Not yet implemented");
		br.close();
	}

	@Test(expected = NullPointerException.class)
	public void testExceptionIsThrown() throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(file));
		TextBuddy.fileSearch("search item",br);
		br.close();
	}

	@Test
	public void testSort() throws IOException{

		BufferedReader br = new BufferedReader(new FileReader(file));
		assertEquals("1. item\r\n2. object\r\n",TextBuddy.fileSort(file,br));//'object' to be switched with 'item', add object then item
		//fail("Not yet implemented");
		br.close();
	}
}
