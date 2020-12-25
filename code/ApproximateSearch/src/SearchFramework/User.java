package SearchFramework;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;

public class User extends Thread{

	//@Override
	public void run() {
		init();
	}

	static void init()
	{


		JLabel jLabel1 = new JLabel("An Approximate Search",JLabel.CENTER);
		JLabel jLabel2 = new JLabel("Framework for Big Data",JLabel.CENTER);
		jLabel1.setFont(new Font("Brush Script M", Font.BOLD, 20));
		jLabel2.setFont(new Font("Brush Script M", Font.BOLD, 16));

		jLabel1.setForeground(new Color(120,20,60));
		jLabel2.setForeground(new Color(120,20,60));


		JLabel jLabel3 = new JLabel("User Approximate Search",JLabel.CENTER);
		jLabel3.setFont(new Font("Brush Script M", Font.BOLD, 18));
		jLabel3.setForeground(new Color(120,20,60));
		final JTextArea jTextArea1 = new JTextArea();
		JScrollPane jScrollPane1 = new JScrollPane();
	
		JButton jButton2 = new JButton();
		JButton exit = new JButton("Exit");
		JButton jButton3 = new JButton();
		JFrame contentPane = new JFrame();
		contentPane.setBackground(Color.cyan);

		jScrollPane1.setViewportView(jTextArea1);

		jButton2.setText("Get data");

		jButton3.setText("CLEAR");

		JLabel back = new JLabel(new ImageIcon("images/f.jpg"));

		JLabel status = new JLabel("Status");
		status.setFont(new Font("Brush Script M", Font.BOLD, 18));
		status.setForeground(new Color(41,36,33));

		contentPane.setLayout(null);
		addComponent(contentPane, jLabel1, 0, 0, 450, 30);
		addComponent(contentPane, jLabel2, 0, 30, 450, 30);
		addComponent(contentPane, jLabel3, 0, 60, 450, 30);
		addComponent(contentPane, status, 20, 90, 100, 30);
		addComponent(contentPane, jScrollPane1, 35, 120, 360, 320);
		addComponent(contentPane, jButton2, 30, 454, 100, 40);
		addComponent(contentPane, jButton3, 170, 454, 100, 40);
		addComponent(contentPane, exit,300, 454, 100, 40);
		addComponent(contentPane, back, 0, 0, 450, 545);
		contentPane.setLocation(new Point(0, 0));
		contentPane.setSize(new Dimension(450, 545));
		contentPane.setVisible(true);

		jButton3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				jTextArea1.setText(null);
			}});

		jButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){

				String selectedType = JOptionPane.showInputDialog("Enter hash keyword");


				try {
					Socket client=new Socket("localhost",5000);
					DataOutputStream out= new DataOutputStream(client.getOutputStream());
					DataInputStream in = new DataInputStream(client.getInputStream());
					out.writeUTF("User");
					out.writeUTF(selectedType);
					String finalResult = in.readUTF();
					System.out.println(finalResult);
					jTextArea1.append(finalResult);

					String splits[] = finalResult.split("\n");

					JFrame f = new JFrame();
					f.setSize(400, 300);
// graph part 
					int i=0;
					DefaultPieDataset pieDataset = new DefaultPieDataset();
					for(String line : splits){
						String lineSplit[] = line.trim().split("\\s");
						if(lineSplit.length==2){
							pieDataset.setValue(lineSplit[0].trim(),Integer.valueOf(lineSplit[1].trim()));
						}
					}
					JFreeChart chart = ChartFactory.createPieChart3D
							("Twetter data for "+selectedType, pieDataset, true,true,true);
					PiePlot3D p=(PiePlot3D)chart.getPlot();
					p.setForegroundAlpha(0.5f);
					ChartFrame frame1=new ChartFrame("Approximate Search",chart);
					frame1.setVisible(true);
					frame1.setSize(650,450);

				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}



			}});


		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}});


	}

	private static void addComponent(Container container, Component c, int x, int y,int width, int height) 
	{
		c.setBounds(x, y, width, height);
		container.add(c);
	}
	
	public static void main(String[] args) {

	}



}
