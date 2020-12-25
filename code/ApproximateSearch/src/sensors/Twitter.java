package sensors;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import util.utils;
import SearchFramework.Constant1;
import SearchFramework.Constants;


public class Twitter implements ActionListener,Runnable
{

	JFrame jf,jf2;
	JPanel jp1,jp2,jp3,jp4,jp5;
	JTextArea jt,jt1;
	JLabel jl1,jl3,temp;
	JSeparator jsep;
	JButton jb,jb1,jb2;
	static JLabel jl2;
	JScrollPane js2;
	int i,j;
	static int status = 60;
	String name,place,t,g;
	String str,un,result="Eventtype	villageName 	   Area 	     Date&Time\n";
	
	void init()
	{

		jf=new JFrame("Twitter");
		jf.setLayout(null);

		jp1=new JPanel();
		jp1.setLayout(null);
		jp1.setBounds(10,10, 520,580);
		jp1.setBackground(Color.black);

		jp2=new JPanel();
		jp2.setLayout(null);
		jp2.setBounds(10,10,475,320);
		//jp2.setBackground(new Color(238,232,170));
		jp2.setBackground(Color.white);

		jp3=new JPanel();
		jp3.setLayout(null);
		jp3.setBounds(10,345,110,55);
		jp3.setBackground(Color.red);

		jp4=new JPanel();
		jp4.setLayout(null);
		jp4.setBackground(Color.white);
		jp4.setBounds(130,345,340,150);


		jt=new JTextArea("Sensing...");
		jt.setBounds(5,5,100,45);
		
		jp3.add(jt);

		jt1=new JTextArea("                        ******Information******\n");
		jt1.append("---------------------------------------------------------------------------------\n");
		jt1.setFont(new Font("Serif", Font.BOLD, 13));
		js2=new JScrollPane(jt1);
		js2.setBounds(5,5,330,140);
		jp4.add(js2);

		jb=new JButton("ALERT !");
		jb.setBounds(10,405,100,40);
		jb.setFont(new Font(null,Font.BOLD,15));
		Border thatBorder1 = new LineBorder(Color.RED);
		jb.setBorder(thatBorder1);
		jb.addActionListener(this);

		jb1=new JButton("Refresh");
		jb1.setBounds(10,455,100,40);
		jb1.setFont(new Font(null,Font.BOLD,15));
		jb1.setBorder(thatBorder1);
		jb1.addActionListener(this);

		jb2=new JButton("Exit");
		jb2.setBounds(10,500,100,40);
		jb2.setFont(new Font(null,Font.BOLD,15));
		jb2.setBorder(thatBorder1);
		jb2.addActionListener(this);

		temp=new JLabel(new ImageIcon("images/t1.jpg"),JLabel.LEFT);
		temp.setBounds(5,5,465,310);
		jp2.add(temp);

		jl2=new JLabel(new ImageIcon(""));
		jl3=new JLabel(new ImageIcon("images/t1.jpg"));
		jl2.setBounds(0,0,465,310);
		jl2.setVisible(false);
		jl3.setVisible(false);
		jp4.add(jl3);

		jp1.add(jp2);
		jp1.add(jp3);
		jp1.add(jb);
		jp1.add(jb1);
		jp1.add(jb2);
		jf.add(jp1);
		jf.setSize(520,600);



		jf.setVisible(true); 

		jp1.add(jp4);	



	}











	public void actionPerformed(ActionEvent ae)
	{

		if(ae.getSource()==jb)
		{


			temp.setVisible(false);
			temp=new JLabel(new ImageIcon("images/t1.jpg"),JLabel.LEFT);
			temp.setBounds(5,5,465,310);
			jp2.add(temp);

			jt.setText("Twitter");
			jp4.setBackground(Color.red);
			jl3.setVisible(true);
			jl3.setBounds(0,0,200,175);
			jp4.add(jl3);
			jb.setBackground(Color.red);
		
			Random rnd=new Random();
			i=rnd.nextInt(Constants.types.length);
			j=rnd.nextInt(Constant1.names.length);
			place=Constants.types[i];
			name=Constant1.names[i];
	
			Date d=new Date();
			System.out.println(d);
			String s=d.toString();
			jt1.append("Twitted Date & Time is  "+s +"\n");
			jt1.append("politician name is "+name +"\n");
			jt1.append("Twitter Status is "+place +"\n");    
	

			String twitt= name+ "  is "+place +" "+"politician"+" #" +" Time= " +utils.gettime()+" Date= "+utils.getdate();
			
			System.out.println("twitt"+twitt);	
		
			try {
				Socket client=new Socket("localhost",5000);
				DataOutputStream out= new DataOutputStream(client.getOutputStream());
				out.writeUTF("Sensors");
				out.writeUTF(twitt);
				JOptionPane.showMessageDialog(null,"send success");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}



		if(ae.getSource()==jb1)
		{
			jb.setBackground(Color.white);
			jt.setText("Sensing...");
			jt1.setText("         ******Information******\n");
			jt1.append("----------------------------------------\n");
			temp.setVisible(false);
			temp=new JLabel(new ImageIcon("images/t1.jpg"),JLabel.LEFT);
			temp.setBounds(5,5,465,310);
			jp2.add(temp);
		}
		if(ae.getSource()==jb2)
		{
			System.exit(0);
		}
	}


	public void run() {

		init();

	}
	public static void main(String args[])
	{

		Thread t = new Thread(new Twitter());
		t.start();

	}
}
