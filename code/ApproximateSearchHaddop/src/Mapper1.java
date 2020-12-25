

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class Mapper1 extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable>
{

	public String hash = "";

	public void configure(JobConf job) {
		hash = job.get("hash");

	}

	@Override
	public void map(LongWritable arg0, Text arg1,
			OutputCollector<Text, IntWritable> arg2, Reporter arg3)
					throws IOException {

		if(arg1.toString().length()!=0)
		{
			String line = arg1.toString();
			if(line.contains(hash)){

				String words[] = line.split(" ");
				if(line.contains(hash)){

					if(line.contains("Bad")){
						arg2.collect(new Text("Bad"), new IntWritable(1));
					}else if(line.contains("Good")){
						arg2.collect(new Text("Good"), new IntWritable(1));
					}else if(line.contains("Worst")){
						arg2.collect(new Text("Worst"), new IntWritable(1));
					}else if(line.contains("awesome")){
						arg2.collect(new Text("awesome"), new IntWritable(1));
					}
					//					arg2.collect(new Text(words[1]), new IntWritable(1));
				}

			}
		}

	}


}
