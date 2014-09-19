package textbuddies;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;


//Test used to test search function
public class Test2 {

	

	@Test 
	public void testSearch() throws IOException{
		
		TextBuddy.chooseCommand("newFile.txt","add item");
		TextBuddy.chooseCommand("newFile.txt","add object");
		FileReader fr = new FileReader("newFile.txt");
		BufferedReader br = new BufferedReader(fr);
		assertEquals("1. item",TextBuddy.fileSearch("search item",br));//after 'item' is already inserted
		
		TextBuddy.chooseCommand("newFile.txt","clear");
		TextBuddy.chooseCommand("newFile.txt","exitall");
		br.close();
		fr.close();
	}

	
}

