

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

public class Reducer1 extends MapReduceBase implements Reducer<Text, IntWritable, Text, Text>
{

	@Override
	public void reduce(Text arg0, Iterator<IntWritable> v,
			OutputCollector<Text, Text> arg2, Reporter arg3)
					throws IOException 
					{
		int sum = 0;
		while(v.hasNext()){
			sum+=v.next().get();
		}
		
		arg2.collect(arg0, new Text(""+sum));

					}

}
