
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;

public class ChartPanel extends JPanel {
	private double[] values;

	private static String[] names;

	private String title;

	public ChartPanel(double[] v, String[] n, String t) {
		names = n;
		values = v;
		title = t;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (values == null || values.length == 0)
			return;
		double minValue = 0;
		double maxValue = 0;
		for (int i = 0; i < values.length; i++) {
			if (minValue > values[i])
				minValue = values[i];
			if (maxValue < values[i])
				maxValue = values[i];
		}

		Dimension d = getSize();
		int clientWidth = d.width;
		int clientHeight = d.height;
		int barWidth = clientWidth / values.length;

		Font titleFont = new Font("SansSerif", Font.BOLD, 20);
		FontMetrics titleFontMetrics = g.getFontMetrics(titleFont);
		Font labelFont = new Font("SansSerif", Font.PLAIN, 10);
		FontMetrics labelFontMetrics = g.getFontMetrics(labelFont);

		int titleWidth = titleFontMetrics.stringWidth(title);
		int y = titleFontMetrics.getAscent();
		int x = (clientWidth - titleWidth) / 2;
		g.setFont(titleFont);
		g.drawString(title, x, y);

		int top = titleFontMetrics.getHeight();
		int bottom = labelFontMetrics.getHeight();
		if (maxValue == minValue)
			return;
		double scale = (clientHeight - top - bottom) / (maxValue - minValue);
		y = clientHeight - labelFontMetrics.getDescent();
		g.setFont(labelFont);
		String colors[] = new String[2];
		colors[0]="blue";colors[1]="green";
//		colors[2]="gray";colors[3]="red";
//		colors[4]="cyan";
		//colors[5]="orange";
		for (int i = 0; i < values.length; i++) {
			int valueX = i * barWidth + 1;
			int valueY = top;
			int height = (int) (values[i] * scale);
			if (values[i] >= 0)
				valueY += (int) ((maxValue - values[i]) * scale);
			else {
				valueY += (int) (maxValue * scale);
				height = -height;
			}

			g.setColor(getColor(colors[i]));
			g.fillRect(valueX, valueY, barWidth - 2, height);
			g.setColor(Color.black);
			g.drawRect(valueX, valueY, barWidth - 2, height);
			int labelWidth = labelFontMetrics.stringWidth(names[i]);
			x = i * barWidth + (barWidth - labelWidth) / 2;
			g.drawString(names[i], x, y);
		}
	}
	public static Color getColor(String name)
	{
		if(name.equals("blue"))
		{
			return Color.BLUE;
		}
		
	
		else 	
		{
			return Color.orange;
		}	
		}
	

	public static void main(String[] args) {
		
		 DefaultPieDataset pieDataset = new DefaultPieDataset();
		  pieDataset.setValue("Good ", new Integer("18"));
		  
		  pieDataset.setValue("Awesome", new Double("16"));
		  pieDataset.setValue("Bad", new Double("512"));
		JFreeChart chart = ChartFactory.createPieChart3D
				("Key comparision between Comparison between the Encryption type in existing and proposed system", pieDataset, true,true,true);
		PiePlot3D p=(PiePlot3D)chart.getPlot();
		p.setForegroundAlpha(0.5f);
		ChartFrame frame1=new ChartFrame("3D Pie Chart",chart);
		frame1.setVisible(true);
		frame1.setSize(650,450);
		
	}
 
}
