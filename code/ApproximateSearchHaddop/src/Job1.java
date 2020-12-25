

import java.io.File;

import javax.swing.JOptionPane;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;

import org.apache.hadoop.util.Tool;

public class Job1 extends Configured implements Tool
{

	@Override
	public int run(String[] arg0) throws Exception
	{
		JobConf conf = new JobConf(getConf(),Job1.class);
		conf.setJobName("ApproximateKeyWord");
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(IntWritable.class);
		conf.setMapperClass(Mapper1.class);
		conf.setReducerClass(Reducer1.class);
		conf.set("hash", arg0[0]);
		FileInputFormat.addInputPath(conf, new Path("dataset.txt"));
		FileOutputFormat.setOutputPath(conf, new Path("ResultApoxi"));
		JobClient.runJob(conf);
		return 0;
	}

}
