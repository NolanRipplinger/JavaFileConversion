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

public class CsvToXml 
{
	
	final static String DELIMITER = ",";

	public static void main(String[] args) 
	{
		//Convert CSV to XML
		File inputCsv = new File("csvFile.csv");
		File outputXml = new File("ToXml.xml");
		FileWriter output;
		BufferedWriter bufferedOutput;
		Scanner fileReader;
		List<List<String>> records = new ArrayList<>();
		
		//Wrap input IO File in try/catch to avoid incorrect file name error
		//This is parsing a CSV input file
		try {
			fileReader = new Scanner(inputCsv);
			
			//While file is not finished
			while(fileReader.hasNextLine()) {
				records.add(getCSVRecordFromLine(fileReader.nextLine()));
				
			}
			//Close input file reader, XML parsed
			fileReader.close();
			
		} catch (FileNotFoundException e) {
			//Naming error occurred, make sure to exit
			System.out.println("Input file not found, exiting");
			System.exit(1);
		}
		
		
		//This is parsing our newly created records and outputting to XML
		//Wrap output IO File in try/catch to avoid IOException error
		try 
		{
			//Create new file, wipe if already existing
			outputXml.createNewFile();
			
			
			output = new FileWriter(outputXml.getAbsoluteFile());
			bufferedOutput = new BufferedWriter(output);
			
			//Manually place first entry root
			bufferedOutput.write("<root>\n");
			
			//Iterate through all
			for(int i = 0; i < records.size() - 1; i++) 
			{
				//Manually 
				bufferedOutput.write("  <row>\n");
				
				for(int j = 0; j < records.get(i).size(); j++) 
				{
					
					//Grab the column header start for this element
					bufferedOutput.write("\t<" + records.get(0).get(j) + ">");
					
					//Add the corresponding element
					bufferedOutput.write(records.get(i + 1).get(j));
					
					//Grab the column header ending for this element
					bufferedOutput.write("</" + records.get(0).get(j) + ">\n");
					
				}
				
				//Format for end of row entry
				bufferedOutput.write("  </row>\n");
					
			}
			
			//Manually place EOF root exit
			bufferedOutput.write("</root>");
			
			//Close off the file inputs
			bufferedOutput.close();
			output.close();
			
		} catch (IOException e) {
			//Naming error occurred, make sure to exit
			System.out.println("Output file could not be created, exiting");
			System.exit(2);
		}
		
		
		//output fun
		//System.out.println(records);
		//System.out.println(records.size());
		
	}
	/*	Takes a string input (from a CSV) and returns a list of strings
		which contain the fields of the CSV
	 */
	private static List<String> getCSVRecordFromLine(String line)
	{
		//Create new List<String> to return
		List<String> values = new ArrayList<String>();
		
		//Creating new scanner requires not found exception catching
		try (Scanner rowScanner = new Scanner(line))
		{
			
			//Only need this if delimiter is not comma
			rowScanner.useDelimiter(Pattern.compile(DELIMITER));
			
			//Take input character by character
			while(rowScanner.hasNext()) 
			{
				values.add(rowScanner.next());
			}
		}
		
		//Return list of newly separated fields
		return values;
	}

}
