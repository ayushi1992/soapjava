# soapjava
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.xmlbeans.XmlError;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.google.common.base.Charsets;
import com.google.common.io.Files;


public class GenerateTestCase implements ActionListener{
	private JFrame app;
	private JPanel p1,p2,p3,p4;
	private JLabel l1,l2,l3,l4,l5,label_op;
	private JButton b1,b2,b3,temp,temp1,valbutton;
	private JTextArea txt;
	private JTextField tf1,tf2,tf_op;
	private File wsdlPath=null,tddPath=null;
	private String operation="";
	private String inputM100="";
	private m100Generator m100obj= new m100Generator();
	private SOAPUI_Test obj = new SOAPUI_Test();
	
	public void gui2() {
		app = new JFrame("Test Case Generator Basic code");
		p1 = new JPanel();
		p2=new JPanel();
		p3 = new JPanel();
		p4 = new JPanel();

		p2.setPreferredSize(new Dimension(200,600));
		p4.setPreferredSize(new Dimension(800,200));

		p4.setBackground(Color.LIGHT_GRAY);
		p3.setBackground(Color.PINK);
		p2.setBackground(Color.ORANGE);

		//p1 content : center
		txt = new JTextArea(34,50);
		//txt.setLineWrap(true);
		JScrollPane sc = new JScrollPane(txt);
		sc.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		sc.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		p1.add(sc);

		//p2 content : east
		b1 = new JButton("select WSDL");
		b2 = new JButton("Select tdd");
		b3 = new JButton("generate TestCases");
		valbutton = new JButton("Validate Button");
		
		
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		valbutton.addActionListener(this);
		
		
		b1.setPreferredSize(new Dimension(180,25));
		b2.setPreferredSize(new Dimension(180,25));
		b3.setPreferredSize(new Dimension(180,25));
		valbutton.setPreferredSize(new Dimension(180,25));
		
		p2.add(b1);
		p2.add(b2);
		p2.add(b3);
		p2.add(valbutton);

		//p4 content : south 
		Font font = new Font("Courier", Font.BOLD,12);
		l1 = new JLabel("Enter column number of tdd which is to be read to generate m100");
		l1.setPreferredSize(new Dimension(400,20));
		l3= new JLabel("WSDL SELECTED IS : "+wsdlPath);
		l3.setPreferredSize(new Dimension(700,20));
		l3.setFont(font);
		l4= new JLabel("TDD SELECTED IS : "+tddPath);
		l4.setPreferredSize(new Dimension(700,20));
		l4.setFont(font);
		l5= new JLabel("");
		l5.setPreferredSize(new Dimension(700,20));
		l5.setFont(font);


		tf1= new JTextField();
		tf_op = new JTextField();
		tf1.setPreferredSize(new Dimension(300,20));
		tf_op.setPreferredSize(new Dimension(300,20));
		p4.add(l1); p4.add(tf1);
		p4.add(l3);
		p4.add(l4);
		p4.add(l5);

		app.getContentPane().add(BorderLayout.EAST,p2);
		app.getContentPane().add(BorderLayout.WEST,p3);
		app.getContentPane().add(BorderLayout.CENTER,p1);
		app.getContentPane().add(BorderLayout.SOUTH,p4);

		app.setSize(800,800);
		app.setVisible(true);
		app.setResizable(false);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == b1){
			l5.setText("");
			JFileChooser jf = new JFileChooser();
			int returnVal=jf.showOpenDialog(app);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				wsdlPath = jf.getSelectedFile();
				l3.setText("WSDL SELECTED IS : "+wsdlPath);
			}
		}
		else if(e.getSource() == b2) 
		{
			l5.setText("");
			JFileChooser jf = new JFileChooser();
			int returnVal=jf.showOpenDialog(app);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				tddPath = jf.getSelectedFile();
				l4.setText("TDD SELECTED IS : "+tddPath);
			}
		}
		else if(e.getSource() == b3)  {
			String msg=new String("");
			String regex = "^[0-9]+$";
			if(tddPath==null || wsdlPath==null) {
				msg=msg+"Please upload wsdl and tdd first. ";
			}
			if(tf1.getText().matches(regex)) {
			}
			else 
				msg=msg+"Enter valid column no and row no.";
			if(msg.equals("")) {
				msg="generating TestCase.....";
				
			}
			l5.setText(msg);
			
		}
		else if (e.getSource() == valbutton) {
			//File tf=new File("tempfile.xml");
			try {
				//FileWriter fileWriter = new FileWriter(tf);
				//fileWriter.write(txt.getText());
				//fileWriter.flush();
				//fileWriter.close();
				obj.getReq().setRequestContent(txt.getText());	
				List<XmlError> l1 =obj.validateRequest();
				if(l1.size()==0) {
					JOptionPane.showMessageDialog(app,"valid.");
				}
				else {
					JOptionPane.showMessageDialog(app,"invalid."+l1);
				}
				
			}
			catch(Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		GenerateTestCase obj = new GenerateTestCase();
		obj.gui2();
	}
}
