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

public class JsonToCsv 
{
	
	final static String DELIMITER = ",";

	public static void main(String[] args) 
	{
		//Convert XML to CSV
		File inputJson = new File("jsonFile.json");
		File outputCsv = new File("ToCsv.csv");
		FileWriter output;
		BufferedWriter bufferedOutput;
		Scanner fileReader;
		List<List<String>> records = new ArrayList<>();
		
		//Wrap input IO File in try/catch to avoid incorrect file name error
		//This is parsing a CSV input file
		try 
		{
			fileReader = new Scanner(inputJson);
			
			int numOfColumns = 0;
			int numOfRows = 0;
			int numOfRecords = 0;
			boolean columnsAdded = false;
			boolean columnsParsed = false;
			List<String> values = new ArrayList<String>();
			List<String> headers = new ArrayList<String>();
			
			//While file is not finished
			while(fileReader.hasNextLine()) 
			{
				
				//Convert nextline to string for formatting
				String inputLine = fileReader.nextLine();
				
				//Format input
				inputLine = inputLine.trim();
				
				//No need to populate columns names after first row
				if (!columnsParsed && inputLine.contains("}"))
				{
					numOfRecords++;
					columnsParsed = true;
				}
				
				if (!columnsParsed) {
					//numOfColumns++;
				} else {
					if(!columnsAdded) {
						records.add(headers);
						columnsAdded = true;
					}
					if (numOfRows % numOfColumns == 0 && values.size() != 0) 
					{
						List<String> valuesCopy = new ArrayList<String>(values);
						records.add(valuesCopy);
						values.clear();
					}
					
				}
				
				//Make sure root, and row properties are not present. Only contents
				if (inputLine.contains(":")) 
				{
					
					//This code strips the string into element contents
					String contents = inputLine.substring(inputLine.indexOf(":") + 1);
					//contents = contents.substring(0, contents.indexOf("\""));
					values.add(contents);
					
					if(!columnsParsed) {
						String header = inputLine.substring(inputLine.indexOf("\"") + 1);
						header = header.substring(0, header.indexOf("\""));
						headers.add(header);
					}				
					numOfRows++;
					if (!columnsParsed) {
						numOfColumns++;
					} else {
						if (numOfRows % numOfColumns == 0) 
						{
							numOfRecords++;
						}
					}
				}
			}

			//Close input file reader, XML parsed
			fileReader.close();
			
		} catch (FileNotFoundException e) 
		{
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
		
	}
}