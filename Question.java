
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
	
public class Question {
	String question, choice1, choice2, choice3, choice4;
	
	public Question (String question, String choice1, String choice2, String choice3, String choice4) {
		
	}

	String filename = "QuestionBank.txt";
	ArrayList<String> AllElements= new ArrayList<String> (125);
    BufferedReader br = new BufferedReader(new FileReader(filename)); {
    try {
        StringBuilder sb = new StringBuilder();
        String line = "q"; //just for initialization

        while (line != null) {
        	for (int i = 0;i <= 24; i++) {
        		for (int j = 0;j <=4; j++) {
        				line = br.readLine();
                        sb.append(line);
                        sb.append("\n");
                        AllElements.set(((i*5) + j), line);
	        			}
        			}
        } 
    }
    finally 
    {
        br.close(); }
  
    }
	
/* I'm not sure if this will work, but try it.
 * Quincy, please check if this code is valid. If it works better than
 * what I've already used, please replace it.
 * Also, move this to BackgroundGame.
 * I had intentionally tried to make this work in the Question Class, but to no avail.
 
	public static void main(String... aArgs) throws IOException{
    Question text = new Question();
    
    //treat as a small file
    List<String> lines = text.readSmallTextFile(FILE_NAME);
    log(lines);
    }
    
  final static String FILE_NAME = "QuestionBank.txt";
  final static Charset ENCODING = StandardCharsets.UTF_8;
  
  //For smaller files
  
  List<String> readSmallTextFile(String aFileName) throws IOException {
    Path path = Paths.get(aFileName);
    return Files.readAllLines(path, ENCODING);
  }
 */
    
}


