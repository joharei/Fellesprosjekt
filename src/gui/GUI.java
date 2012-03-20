package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Calendar;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class GUI extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//protected static final String IMAGE_PATH = "gui/";
	protected static final Color COLOR_BACKGROUND = Color.WHITE;
	protected static final Color COLOR_APOINTMENT = new Color(247, 199, 173);
	protected static final Color COLOR_NO_APOINTMENT = new Color(198, 243, 214);
	//protected static final Color COLOR_IN_DELIVERY = new Color(206, 219, 239);
	protected static final Font FONT_HEADER = new Font("", Font.PLAIN, 20);
	protected static final Font FONT_FIELD = new Font("", Font.PLAIN, 18);
	
	GridBagConstraints c;
	JPanel panel = new JPanel();
	private String QUESTION_MESSAGE="Apointment or Meeting";
	
	
	private JButton logOutButton;
	private JButton createButton;
	private JLabel helpLabel;
	private JLabel overskrift;
	private String [] days = { "Time"," - ","Mon", "Tue", "Wed", "Thu", "Fri", "Sat","Sun"};
	private JLabel [] label2= new JLabel[9];

	private String [] options = {"Meeting","Appointment"};
	
	public static void main(String[] args) throws IOException {
		GUI gmain = new GUI();
		gmain.pack();
		gmain.setVisible(true);
	}
	
	
	
	public GUI() throws IOException{
		
		getContentPane().setBackground(COLOR_BACKGROUND);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c = new GridBagConstraints();
		
		setLayout(new GridBagLayout());
		
		c.gridx=0;
		c.gridy=0;
		c.anchor=GridBagConstraints.NORTHWEST;
		add(new GUILoggInInfo(),c);
		
		overskrift = new JLabel("CAL - X");
		c.gridx=7;
		c.gridy=0;
		add(overskrift,c);
		
		c.gridx=0;
		c.gridy=5;
		add(new SmallCalendar(),c);
		
		c.gridx=9;
		c.gridy=3;
		add(new CalendarPanecolumnHeaderPanel(),c);
		
		c.gridx=9;
		c.gridy=5;
		add(new ScrollPane(),c); //adder kalenderen i mainen
		
		helpLabel = new JLabel("");
		c.gridx=9;
		c.gridy=6;
		add(helpLabel, c);
		
		createButton = new JButton("Create");
		c.gridx=0;
		c.gridy=6;
		add(createButton,c);
		createButton.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	int response=JOptionPane.showOptionDialog(null, "Meeting or Apointment?", "Options", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, "none of your business");
		    	if(response==0){
		    		System.out.println("Meeting");
		    	}
		    	if(response==1){
		    		System.out.println("Apointment");
		    	}
		    }
		});
		
		logOutButton = new JButton("Log Out");
		c.gridx=30;
		c.gridy=0;
		add(logOutButton,c);
		
	}
}
