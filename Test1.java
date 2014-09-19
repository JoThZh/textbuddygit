package textbuddies;
import static org.junit.Assert.*;
import java.io.File;

//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;


//Test used to test search function
public class Test1 {



	@Test
	public void testSearch1()throws IOException{
		File file = new File("newFile.txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		TextBuddy.chooseCommand("newFile","add third");
		TextBuddy.chooseCommand("newFile","add second");
		TextBuddy.chooseCommand("newFile","add first");

		assertEquals("1. first\r\n2. second\r\n3. third\r\n",TextBuddy.chooseCommand("newFile","sort"));
		file.deleteOnExit();

	}


	@Test
	public void testSearch2()throws IOException{
		File file = new File("newFile2.txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		TextBuddy.chooseCommand("newFile2","add apple");
		TextBuddy.chooseCommand("newFile2","add aaple");
		TextBuddy.chooseCommand("newFile2","add appld");

		assertEquals("1. appld\r\n2. aaple\r\n3. apple\r\n",TextBuddy.chooseCommand("newFile2","sort"));
		file.deleteOnExit();
	}

	@Test
	public void testSearch3()throws IOException{
		File file = new File("newFile3.txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		TextBuddy.chooseCommand("newFile3","add appled");
		TextBuddy.chooseCommand("newFile3","add apple");
		TextBuddy.chooseCommand("newFile3","add appl");

		assertEquals("1. appl\r\n2. apple\r\n3. appled\r\n",TextBuddy.chooseCommand("newFile3","sort"));
		file.deleteOnExit();
	}




}
