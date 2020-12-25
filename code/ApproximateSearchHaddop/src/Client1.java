

import javax.swing.JFrame;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.ToolRunner;

public class Client1 
{

	public static void main(String[] args) throws Exception
	{
			
		ToolRunner.run(new Configuration(), new Job1(), args);
		
		
	}
}
