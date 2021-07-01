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

public class CsvToCsv 
{
	
	final static String DELIMITER = ",";

	public static void main(String[] args) 
	{
		//Convert CSV to JSON
		File inputCsv = new File("csvFile.csv");
		File outputCsv = new File("ToCsv.csv");
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
				records.add(getRecordFromLine(fileReader.nextLine()));
				
			}
			//Close input file reader, CSV parsed
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
		
	}
	
	private static List<String> getRecordFromLine(String line)
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
