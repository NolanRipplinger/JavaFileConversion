package fileConversion;

import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XmlToCsv 
{
	
	final static String DELIMITER = "</row>";

	public static void main(String[] args) 
	{
		//Convert XML to CSV
		File inputXml = new File("xmlFile.xml");
		File outputCsv = new File("ToCsv.csv");
		FileWriter output;
		BufferedWriter bufferedOutput;
		Scanner fileReader;
		List<List<String>> records = new ArrayList<>();
		
		//Wrap input IO File in try/catch to avoid incorrect file name error
		//This is parsing a CSV input file
		try {
			fileReader = new Scanner(inputXml);
			
			//While file is not finished
			while(fileReader.hasNextLine()) {
				String inputLine = fileReader.nextLine();
				
				//Only focus on lines that contain elements
				if(inputLine.contains("")) {
					inputLine = inputLine.trim();
					records.add(getXMLRecordFromLine(inputLine));
				}
				
			}
			//Close input file reader, XML parsed
			fileReader.close();
			
		} catch (FileNotFoundException e) {
			//Naming error occurred, make sure to exit
			System.out.println("Input file not found, exiting");
			System.exit(1);
		}
		
		
		//This is parsing our newly created records and outputting to CSV
		//Wrap output IO File in try/catch to avoid IOException error
		try 
		{
			//Create new file, wipe if already existing
			outputCsv.createNewFile();
			
			
			output = new FileWriter(outputCsv.getAbsoluteFile());
			bufferedOutput = new BufferedWriter(output);
			
			//Iterate through all
			for(int i = 0; i < records.size(); i++) 
			{
				for(int j = 0; j < records.get(i).size(); j++) 
				{
					
					//Pull record sequentially from List similar to 2d array
					bufferedOutput.write(records.get(i).get(j));
					
					//Don't put comma at the end of record row
					if (j != records.get(i).size() - 1)	
					{
						bufferedOutput.write(",");
					}
					
					
				}
				//Don't put newline at the end of file
				if (i != records.size() - 1) 
				{
					bufferedOutput.write("\n");
				}
				
			}
			
			bufferedOutput.close();
			output.close();
		} catch (IOException e) {
			//Naming error occurred, make sure to exit
			System.out.println("Output file could not be created, exiting");
			System.exit(2);
		}
		
		
		//output fun
		//System.out.println(records);
		System.out.println("Num of records: " + records.size());
		System.out.println("Num of columns: " + records.get(0).size());
		System.out.println("num of columns in row 1: " + records.get(1).size());
		
	}
	
	/*	Takes a string input (from a JSON) and returns a list of strings
		which contain the fields of the JSON
	 */
	private static List<String> getXMLRecordFromLine(String line)
	{
		int numOfColumns = 0;
		boolean rowsComplete = false;
		
		//Create new List<String> to return
		List<String> values = new ArrayList<String>();
		
		//Creating new scanner to scan line (requires not found exception catching)
		try (Scanner rowScanner = new Scanner(line))
		{
			
			//Only need this if delimiter is not comma
			rowScanner.useDelimiter(Pattern.compile(DELIMITER));
			
			//Take input character by character
			while(rowScanner.hasNext()) 
			{
				//Save current line as string to manipulate
				String currentLine = rowScanner.next();
				System.out.println(currentLine);
				values.add(currentLine);
				
			}
		}
		
		//Return list of newly separated fields
		return values;
	}

}
