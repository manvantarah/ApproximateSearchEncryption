package SearchFramework;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Database.JDBC;

public class ApproximateSearch extends Thread {

	static String hashValueOfDataSet = "";

	static void init()
	{


		JLabel jLabel1 = new JLabel("An Approximate Search",JLabel.CENTER);
		JLabel jLabel2 = new JLabel("Framework for Big Data",JLabel.CENTER);
		jLabel1.setFont(new Font("Brush Script M", Font.BOLD, 20));
		jLabel2.setFont(new Font("Brush Script M", Font.BOLD, 16));

		jLabel1.setForeground(new Color(120,20,60));
		jLabel2.setForeground(new Color(120,20,60));


		JLabel jLabel3 = new JLabel("Approximate Search",JLabel.CENTER);
		jLabel3.setFont(new Font("Brush Script M", Font.BOLD, 18));
		jLabel3.setForeground(new Color(120,20,60));
		final JTextArea jTextArea1 = new JTextArea();
		JScrollPane jScrollPane1 = new JScrollPane();
		//jButton1 = new JButton();
		JButton jButton2 = new JButton();
		JButton exit = new JButton("Exit");
		JButton jButton3 = new JButton();
		JFrame contentPane = new JFrame();
		contentPane.setBackground(Color.cyan);

		jScrollPane1.setViewportView(jTextArea1);

		jButton2.setText("View data");

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

		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}});

// approximate search button in user interface

		try
		{
			ServerSocket server = new ServerSocket(5000);	
			System.out.println("Approximate search server waiting at "+server.getLocalPort());
			while (true)
			{
				System.out.println("waiting");//			bs.addActionListener(new ActionListener() {

				Socket client_socket = server.accept();
				System.out.println("acepted request");
				DataInputStream in = new DataInputStream(client_socket.getInputStream());
				DataOutputStream out= new DataOutputStream(client_socket.getOutputStream());
				String type=in.readUTF();

				if(type.equals("Sensors"))
				{

					String tweet=in.readUTF()+"\n";
					jTextArea1.setText("Received tweets from sensors\n"+tweet+ "-----------------------------------\n"+jTextArea1.getText());
					try {
						Files.write(new File("DataSet/dataset.txt").toPath(), tweet.getBytes(), StandardOpenOption.APPEND);
					}catch (IOException e) {
						//exception handling left as an exercise for the reader
					}
				}
				else
				{
					String tweet=in.readUTF();
					StringBuffer finalResult = new StringBuffer("");
					String hasValue = verifyChecksum("DataSet/dataset.txt");
					System.out.println(hasValue);
					System.out.println(hashValueOfDataSet);
					if((!hashValueOfDataSet.equals(hasValue))){
						hashValueOfDataSet = hasValue;
						JDBC.clreaPreviousData();
						try {
							System.out.println("inserting dataset");
							Process p =  Runtime.getRuntime().exec("hdfs dfs -put -f "+new File("DataSet/dataset.txt"));         
							String line;
							BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
							while ((line = input.readLine()) != null) {
								System.out.println(line);
							}
							input.close();
							System.out.println("removing previous result");
							p =  Runtime.getRuntime().exec("hdfs dfs -rmr ResultApoxi");    
							input = new BufferedReader(new InputStreamReader(p.getInputStream()));

							while ((line = input.readLine()) != null) {
								System.out.println(line);
							}
							input.close();

							System.out.println("calling hadoop");
							p =  Runtime.getRuntime().exec("hadoop jar Hadoop_jars/approximateHadoop.jar Client1 "+tweet);    
							input = new BufferedReader(new InputStreamReader(p.getInputStream()));
							while ((line = input.readLine()) != null) {
								System.out.println(line);
							}//		public void actionPerformed(ActionEvent e){
//							
//							
//								bs.setEnabled(false);
//								Thread t =new Thread(new bomb());
//								t.start();
//							
//							
//						}});
					//
					//es.addActionListener(new ActionListener() {
//						public void actionPerformed(ActionEvent e){
					//	
//							
//								es.setEnabled(false);
//								Thread t = new Thread(new earthquake());
//								t.start();
//							
//						}});
							input.close();

							System.out.println("reading result");
							p =  Runtime.getRuntime().exec("hdfs dfs -cat ResultApoxi/part-00000"); 
							input = new BufferedReader(new InputStreamReader(p.getInputStream()));

							while ((line = input.readLine()) != null) {
								finalResult.append(line+"\n");
							}
							input.close();
							System.out.println(finalResult +"data");
							JDBC.updatePreviousRes(tweet,finalResult.toString());
						} catch (IOException ex) {
							ex.printStackTrace();
						}
					}else{

						if(JDBC.checkPreviousResult(tweet)){
							finalResult = JDBC.getPreviousResult(tweet);
						}
						else
						{
							try {
								System.out.println("inserting dataset");
								Process p =  Runtime.getRuntime().exec("hdfs dfs -put -f "+new File("DataSet/dataset.txt"));         
								String line;
								BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
								while ((line = input.readLine()) != null) {
									System.out.println(line);
								}
								input.close();
								System.out.println("removing previous result");
								p =  Runtime.getRuntime().exec("hdfs dfs -rmr ResultApoxi");    
								input = new BufferedReader(new InputStreamReader(p.getInputStream()));

								while ((line = input.readLine()) != null) {
									System.out.println(line);
								}
								input.close();

								System.out.println("calling hadoop");
								p =  Runtime.getRuntime().exec("hadoop jar Hadoop/approximateHadoop.jar Client1 "+tweet);    
								input = new BufferedReader(new InputStreamReader(p.getInputStream()));
								while ((line = input.readLine()) != null) {
									System.out.println(line);
								}
								input.close();

								System.out.println("reading result");
								p =  Runtime.getRuntime().exec("hdfs dfs -cat ResultApoxi/part-00000"); 
								input = new BufferedReader(new InputStreamReader(p.getInputStream()));

								while ((line = input.readLine()) != null) {
									finalResult.append(line+"\n");
								}
								input.close();
								System.out.println(finalResult +"data");
								JDBC.updatePreviousRes(tweet,finalResult.toString());
							} catch (IOException ex) {
								ex.printStackTrace();
							}
						}

					}

					out.writeUTF(finalResult.toString());

				}


			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private static void addComponent(Container container, Component c, int x, int y,int width, int height) 
	{
		c.setBounds(x, y, width, height);
		container.add(c);
	}

	@Override
	public void run() {
		init();
	}

	public static void main(String[] args)
	{
		ApproximateSearch t = new  ApproximateSearch();
		t.start();

	}

	public static String verifyChecksum(String file) throws NoSuchAlgorithmException, IOException
	{
		MessageDigest sha1 = MessageDigest.getInstance("SHA1");
		FileInputStream fis = new FileInputStream(file);

		byte[] data = new byte[1024];
		int read = 0;
		while ((read = fis.read(data)) != -1) {
			sha1.update(data, 0, read);
		};
		byte[] hashBytes = sha1.digest();

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < hashBytes.length; i++) {
			sb.append(Integer.toString((hashBytes[i] & 0xff) + 0x100, 16).substring(1));
		}

		String fileHash = sb.toString();

		return fileHash;
	}



}
