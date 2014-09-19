package textbuddies;
import static org.junit.Assert.*;

//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;


//Test used to test search function
public class Test1 {



	@Test
	public void testSort1()throws IOException{

		TextBuddy.chooseCommand("newFile.txt","add third");
		TextBuddy.chooseCommand("newFile.txt","add second");
		TextBuddy.chooseCommand("newFile.txt","add first");

		assertEquals("1. first\r\n2. second\r\n3. third\r\n",TextBuddy.chooseCommand("newFile.txt","sort"));
		TextBuddy.chooseCommand("newFile.txt","clear");
		TextBuddy.chooseCommand("newFile.txt","exitall");
	}


	@Test
	public void testSort2()throws IOException{

		TextBuddy.chooseCommand("newFile2.txt","add apple");
		TextBuddy.chooseCommand("newFile2.txt","add aaple");
		TextBuddy.chooseCommand("newFile2.txt","add appld");

		assertEquals("1. appld\r\n2. aaple\r\n3. apple\r\n",TextBuddy.chooseCommand("newFile2.txt","sort"));
		TextBuddy.chooseCommand("newFile2.txt","clear");
		TextBuddy.chooseCommand("newFile2.txt","exitall");
	}

	@Test
	public void testSort3()throws IOException{


		TextBuddy.chooseCommand("newFile3.txt","add appled");
		TextBuddy.chooseCommand("newFile3.txt","add apple");
		TextBuddy.chooseCommand("newFile3.txt","add appl");

		assertEquals("1. appl\r\n2. apple\r\n3. appled\r\n",TextBuddy.chooseCommand("newFile3.txt","sort"));
		TextBuddy.chooseCommand("newFile2.txt","clear");
		TextBuddy.chooseCommand("newFile3.txt","exitall");
	}




}
