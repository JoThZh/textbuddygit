package textbuddies;
//Module: CS2103T
//Matric No.: A0108494Y
//Name: Johann Thang Zhirong
//Tutorial Grp: C01

//Precondition: Only integers and fixed command words are input. user does not input negative numbers

import java.util.*;
import java.io.*;

public class TextBuddy {
	private static final String SYSTEM_WRONG = "-1";
	private static final String SYSTEM_RIGHT = "1";
	private static final String SYSTEM_END = "0";
	private static String textFile = "";
	public static final String MSG_ADD = "Added to ";
	private static final String MSG_DELETE = "Deleted from ";
	private static final String MSG_NO_DELETE = "Nothing to delete";
	private static final String MSG_DELETEFAIL = "Delete operation is failed.";
	private static final String MSG_CLEAR = "All content deleted from ";
	private static final String MSG_EMPTY = " is empty";
	private static final String MSG_NOT_FOUND = "Word not found";
	private static final String MSG_WRONG = "wrong command";
	private static final int DELETE_LENGTH = 7;
	private static final int REMOVE_NUMBERING = 3;
	

	public static void main(String[] args) throws Exception, IOException {
		String cont = SYSTEM_RIGHT;
		System.out.println("Welcome to TextBuddy. " + args[0] + " is ready for use");
		textFile = args[0];
		Scanner sc = new Scanner(System.in);
		String command = "";
		do{
			System.out.print("command: ");
			command = sc.nextLine();
			cont = chooseCommand(textFile,command);
			
			if(cont.equals(SYSTEM_WRONG)){
				System.out.println(MSG_WRONG);
			}
		}while(!cont.equals(SYSTEM_END));
	}

	public static String chooseCommand(String newFile, String command) throws IOException {

		String deleteElig = "temporaryfiller";
		

		File file = new File(newFile);
		if (!file.exists()) {
			file.createNewFile();
		}
		BufferedReader br = new BufferedReader(new FileReader(file));

		if(command.equals("exit")){
			int type = 1;
			fileExit(file, br,type);
			return SYSTEM_END;
		}
		else if(command.equals("exitall")){
			int type = 0;
			fileExit(file,br,type);
			return SYSTEM_END;
		}
		//substring length depends on length of command
		else if(command.substring(0,4).equals("add ")){
			fileAdd(command, file, br);
			return SYSTEM_RIGHT;
		}
		else if(command.substring(0,4).equals("sort")){
			return fileSort(file, br);
		}
		else if(command.substring(0,5).equals("clear")){
			fileClear(deleteElig, file, br);
			return SYSTEM_RIGHT;
		}
		else if(command.substring(0,6).equals("delete")){
			fileDelete(command, file, br);
			return SYSTEM_RIGHT;
		}
		else if(command.substring(0,6).equals("search")){
			fileSearch(command,br);
			return SYSTEM_RIGHT;
		}
		else if(command.substring(0,7).equals("display")){
			fileDisplay(br);
			return SYSTEM_RIGHT;
		}

		br.close();
		return SYSTEM_WRONG;
	}

	public static String fileSort(File file, BufferedReader br) throws IOException{

		String lineRead = " ";
		
		ArrayList<String> list = new ArrayList<String>();

		lineRead = br.readLine();
		if(lineRead==null){
			System.out.println(textFile + MSG_EMPTY);
			return "";
		}
		System.out.println("sorted");
		if(lineRead!=null){
			lineRead = lineRead.substring(REMOVE_NUMBERING);
			list.add(lineRead);
		}

		return sortArray(file, br, list);
	}

	private static String sortArray(File file, BufferedReader br, ArrayList<String> list)
			throws IOException {
		String lineRead = " ";
		String temp2 = "";
		int listSize;
		
		lineRead = br.readLine();

		if(lineRead!=null){
			lineRead = lineRead.substring(REMOVE_NUMBERING);
		}

		while(lineRead!=null){
			listSize = list.size();
			lineRead = inputToArray(br, list, lineRead, listSize);

		}
		for(int i=0;i<list.size();i++){
			temp2 += String.valueOf(i+1) + ". " + list.get(i) + "\r\n";
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile(),false);//no append
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(temp2);
		bw.close();
		fw.close();
		return temp2;
	}

	private static String inputToArray(BufferedReader br,
			ArrayList<String> list, String lineRead, int listSize)
			throws IOException {
		int i = 0;
		int j = 0;
		for(i=0;i<listSize;i++){
			
			if(Character.getNumericValue(lineRead.charAt(0))<Character.getNumericValue(list.get(i).charAt(0))){
				list.add(i,lineRead);
				break;
			}
			else if(Character.getNumericValue(lineRead.charAt(0))==Character.getNumericValue(list.get(i).charAt(0))){
				for(j=0;j<Math.min(lineRead.length(),list.get(i).length());j++){
					if(lineRead.charAt(j)<list.get(i).charAt(j)){
						list.add(i,lineRead);
						j++;
						lineRead = br.readLine();
						if(lineRead!=null){
							lineRead = lineRead.substring(REMOVE_NUMBERING);
						}
						return lineRead;
					}
				}
				
				if(lineRead.charAt(j-1)==list.get(i).charAt(j-1) && lineRead.length()<list.get(i).length()){
					list.add(i,lineRead);
					lineRead = br.readLine();
					if(lineRead!=null){
						lineRead = lineRead.substring(REMOVE_NUMBERING);
					}
					return lineRead;
				}
			}
		}
		if(i==list.size()){
			list.add(lineRead);
		}
		lineRead = br.readLine();
		if(lineRead!=null){
			lineRead = lineRead.substring(REMOVE_NUMBERING);
		}
		return lineRead;
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
				System.out.println(String.valueOf(n) + ". " + lineRead.substring(REMOVE_NUMBERING));
				returnable = lineRead;
			}
		}while(lineRead!=null);

		if(returnable.equals("")){
			returnable = MSG_NOT_FOUND;
			System.out.println(MSG_NOT_FOUND);
		}
		br.close();
		return returnable;

	}

	private static void fileDisplay(BufferedReader br)
			throws IOException {
		String forCheckEmpty = "";
		String redisplay;
		//read each line of text and print them
		while((redisplay = br.readLine())!=null){
			System.out.println(redisplay);
			forCheckEmpty = redisplay;
		}
		//if no lines are read, it is empty
		if(forCheckEmpty.equals("")){
			System.out.println(textFile + MSG_EMPTY);
		}
		br.close();
	}

	private static void fileDelete(String command, File file, BufferedReader br)
			throws IOException {
		//extra feature to take "delete" as "delete 1"
		if(command.length()<DELETE_LENGTH){
			command = command + " 1";	
		}
		//text without the command is entered into method
		String deleted = deleteText(command.substring(7), file);
		if(deleted!=""){
			System.out.println(MSG_DELETE + textFile + ": \"" + deleted.substring(REMOVE_NUMBERING) + "\"");
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
			
			if (type==0) {
				System.out.println(file.getName() + " is deleted!");
				file.delete();
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
						tempText += String.valueOf(n-1) + ". " + transferLine.substring(REMOVE_NUMBERING) + "\r\n";
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

