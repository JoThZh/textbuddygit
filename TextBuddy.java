package textbuddies;
//Module: CS2103T
//Matric No.: A0108494Y
//Name: Johann Thang Zhirong
//Tutorial Grp: C01

//Precondition: Only integers and fixed command words are input. user does not input negative numbers

import java.util.*;
import java.io.*;

public class TextBuddy {
	private static final int MSG_WRONG = -1;
	private static final int MSG_RIGHT = 1;
	private static final int MSG_END = 0;
	private static String textFile = "";
	public static final String MSG_ADD = "Added to ";
	private static final String MSG_DELETE = "Deleted from ";
	private static final String MSG_NO_DELETE = "Nothing to delete";
	private static final String MSG_DELETEFAIL = "Delete operation is failed.";
	private static final String MSG_CLEAR = "All content deleted from ";
	private static final String MSG_EMPTY = " is empty";
	private static final String MSG_NOT_FOUND = "Word not found";

	public static void main(String[] args) throws Exception, IOException {
		int cont = MSG_RIGHT;
		System.out.println("Welcome to TextBuddy. " + args[0] + " is ready for use");
		textFile = args[0];
		
		do{
			cont = commandChoose(textFile);
			//System.out.println();
			if(cont == MSG_WRONG){
				System.out.println("wrong command");
			}
		}while(cont!=MSG_END);
	}

	public static int commandChoose(String newFile) throws IOException {
		
		System.out.print("command: ");
		Scanner sc = new Scanner(System.in);
		String command = ""+ sc.nextLine();
		String deleteElig = "temporaryfiller";
		String redisplay = "";
		String checkEmpty = "";

		File file = new File(newFile);
		// if file doesnt exist, then create it
		if (!file.exists()) {
			file.createNewFile();
		}
		BufferedReader br = new BufferedReader(new FileReader(file));

		if(command.equals("exit")){
			int type = 1;
			fileExit(file, br,type);
			return MSG_END;
		}
		else if(command.equals("exitall")){
			int type = 0;
			fileExit(file,br,type);
			return MSG_END;
		}
		//substring length depends on length of command
		else if(command.substring(0,4).equals("add ")){
			fileAdd(command, file, br);
			return MSG_RIGHT;
		}
		else if(command.substring(0,5).equals("clear")){
			fileClear(deleteElig, file, br);
			return MSG_RIGHT;
		}
		else if(command.substring(0,6).equals("delete")){
			fileDelete(command, file, br);
			return MSG_RIGHT;
		}
		else if(command.substring(0,6).equals("search")){
			fileSearch(command,br);
			return MSG_RIGHT;
		}
		else if(command.substring(0,7).equals("display")){
			fileDisplay(checkEmpty, br);
			return MSG_RIGHT;
		}
		
		br.close();
		return MSG_WRONG;
	}
	
	public static void fileSort(String command, File file, BufferedReader br) throws IOException{
		String lineRead = "";
		String nextlineRead = "";
		String temp = "";
		FileWriter fw = new FileWriter(file.getAbsoluteFile(), false);//no append
		BufferedWriter bw = new BufferedWriter(fw);
		do{
			lineRead = br.readLine().substring(3,4);
			br.mark(10);
			nextlineRead = br.readLine().substring(3,4);
			
			if(Integer.valueOf(lineRead)>Integer.valueOf(nextlineRead)){
				br.reset();
				bw.write(nextlineRead);
				bw.newLine();
				bw.write(lineRead);
				br.readLine();
			}
			else{
				br.reset();
				br.readLine();
			}
			
			
		}while(lineRead!="");
	}
	
	public static String fileSearch(String command, BufferedReader br)
			throws IOException {
		
		String lineRead="";
		String tokenWithSpace = " " + command.substring(7).toLowerCase() + " ";
		String lineReadWithSpace = "";
		int n = 0;
		String returnable = "";
		do{
			lineRead = br.readLine();
			if(lineRead==""){
				return returnable;
			}
			else{
				lineReadWithSpace = lineRead + " ";
				lineReadWithSpace = lineReadWithSpace.toLowerCase();//add whitespace to prevent exclusion of search word if at the back
			}
			if(lineReadWithSpace.contains(tokenWithSpace)){
				n++;
				System.out.println(String.valueOf(n) + ". " + lineRead.substring(3));
				returnable = lineRead;
			}
		}while(lineRead!=null);
		
		if(returnable.equals("")){
			returnable = MSG_NOT_FOUND;
			System.out.println(MSG_NOT_FOUND);
		}
		
		return returnable;
		
		
	}

	private static void fileDisplay(String checkEmpty, BufferedReader br)
			throws IOException {
		String redisplay;
		//read each line of text and print them
		while((redisplay = br.readLine())!=null){
			System.out.println(redisplay);
			checkEmpty = redisplay;
		}
		//if no lines are read, it is empty
		if(checkEmpty.equals("")){
			System.out.println(textFile + MSG_EMPTY);
		}
		br.close();
	}

	private static void fileDelete(String command, File file, BufferedReader br)
			throws IOException {
		//extra feature to take "delete" as "delete 1"
		if(command.length()<7){
			command = command + " 1";	
		}
		//text without the command is entered into method
		String deleted = deleteText(command.substring(7), file);
		if(deleted!=""){
			System.out.println(MSG_DELETE + textFile + ": \"" + deleted.substring(3) + "\"");
		}
		else
			System.out.println(MSG_NO_DELETE);
		br.close();
	}

	private static void fileClear(String deleteElig, File file,
			BufferedReader br) throws IOException {
		System.out.println(MSG_CLEAR + textFile);
		while(deleteElig!=""){
			//first line is continuously deleted until nothing is returned
			deleteElig = deleteText(String.valueOf(1), file);
		}
		br.close();
	}

	private static void fileAdd(String command, File file, BufferedReader br)
			throws IOException {
		System.out.println(MSG_ADD + textFile + ": \"" + command.substring(4) + "\"");
		addText(command.substring(4), file);
		br.close();
	}

	private static void fileExit(File file, BufferedReader br, int type) {
		try {
			br.close();
			//delete file after use, for convenience
			if (type==0 && file.delete()) {
				System.out.println(file.getName() + " is deleted!");
			} 
			else if(type!=1){
				System.out.println(MSG_DELETEFAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void addText(String shortCommand, File file){
		try {
			int n = 0;
			String line;
			//for appending
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			FileReader fr = new FileReader(file.getAbsoluteFile());
			BufferedReader br = new BufferedReader(fr);
			//empty slot is found to write new line
			while((line=br.readLine())!=null){
				n++;
			}

			bw.write(String.valueOf(n+1)+". "+shortCommand);
			bw.newLine();

			bw.close();
			br.close();
			fw.close();
			fr.close();
			n=0;

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String deleteText(String numDelete, File file){
		try {
			//variables used to determine which if/else conditions are met
			int n = 1;
			int m = 0;
			String line = null;
			BufferedReader br = new BufferedReader(new FileReader(file));

			String transferLine = "", tempText = "";
			while((transferLine = br.readLine()) != null){

				if(n!=Integer.valueOf(numDelete)){
					if(m==1){
						//recombine sentence numbering and sentences by shifting sentences upwards 1 step to fill deleted sentence
						tempText += String.valueOf(n-1) + ". " + transferLine.substring(3) + "\r\n";
					}
					else//transfer simply
						tempText += transferLine + "\r\n";
				}
				else {//line to be deleted is transferred to line variable. m=1 to activate recombination
					line = transferLine;
					m=1;
				}
				n++;
			}
			//if counter does not increase, "" is returned
			if(n==1){
				line = "";       	
			}
			br.close();

			FileWriter writer = new FileWriter(file);
			writer.write(tempText);writer.close();
			return line;

		}

		catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}



}

