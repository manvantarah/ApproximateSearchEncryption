package DataSetGeneration;

import SearchFramework.Constants;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Random;

public class AutoGenerationOfDataSet {
	
	static String names[]={"Modi","Rahul","siddaramaiah","yeddyurappa","kumaraswamy"};
	
	
	
	public static void main(String[] args) {
		
		
		int nooftwt=1000;
		Random rand = new Random();
		for(int I=0;I<=nooftwt;I++)
		{
			String name=names[rand.nextInt(names.length)];
			
             String twitt=  name +" is "+Constants.types[rand.nextInt(Constants.types.length)] +" politician" +" #"+" Time= "+ rand.nextInt(24)+":"+rand.nextInt(60)+":"+rand.nextInt(60)+" Date= "+getdate()+"\n";

					
			try {
			    Files.write(Paths.get("DataSet/dataset.txt"), twitt.getBytes(), StandardOpenOption.APPEND);
			}catch (IOException e) {
			    //exception handling left as an exercise for the reader
			}
		}
		System.out.println("Successfully Generated "+nooftwt+ " DataSet");
		
	}
	
	public static String getdate()
	{
		GregorianCalendar gc = new GregorianCalendar();

        int year = randBetween(1990, 2017);

        gc.set(gc.YEAR, year);

        int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));

        gc.set(gc.DAY_OF_YEAR, dayOfYear);

        String date=  gc.get(gc.YEAR) + "/" + (gc.get(gc.MONTH) + 1) + "/" + gc.get(gc.DAY_OF_MONTH);
        return date;
        
        
	}
	 public static int randBetween(int start, int end) {
	        return start + (int)Math.round(Math.random() * (end - start));
	    }

}
