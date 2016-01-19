# soapjava
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

class HomePage implements ActionListener {
	private JFrame app;
	private JPanel p1;
	private JButton b1,b2,b3;
	
	public void gui2() {
		app = new JFrame("Test Case Generator Basic code");
		p1 = new JPanel();
		
		b1 = new JButton("generate M100");
		b2 = new JButton("generate testcase");
		b3 = new JButton("BoTh");
		
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		
		b1.setPreferredSize(new Dimension(180,25));
		b2.setPreferredSize(new Dimension(180,25));
		b3.setPreferredSize(new Dimension(180,25));
		
		p1.add(b1);
		p1.add(b2);
		p1.add(b3);
		
		app.getContentPane().add(p1);
		
		app.setSize(400,400);
		app.setVisible(true);
		app.setResizable(false);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == b1){
			GuiHome obj = new GuiHome();
			obj.gui2();
		}
		
	}
}
public class Home {
	public static void main(String[] args) {
		HomePage hobj=new HomePage();
		hobj.gui2();
	}

}
