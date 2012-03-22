package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ListOfPersons2 extends JDialog{
	
	private GridBagConstraints gb;
	private GridBagLayout gbLayout;
	private JLabel searchImg, guideText;
	private JButton searchButton, add;
	private JScrollPane scrollPane;
	private JTextField searchBox;
	private JTable tableOfPersons;

	private Object[] colNames =  {"Name", "e-mail"};
	
	private Object[][] testData = {{"person1", "person1@bla.com"},{"person2", "person2@bla.com"},
			{"person3", "person3@bla.com"},{"person4", "person4@bla.com"}};
	
	private ImageIcon search = new ImageIcon(getClass().getResource("art/addPersons/search.png"));
	
	/**
	 * A list with persons to add to a meeting
	 * search for persons in database is enabled 
	 */
	private MeetingGui motherPanel;
	
	public ListOfPersons2(MeetingGui motherPanel){
		
		this.setModal(true);
		this.motherPanel = motherPanel;
		
		gb = new GridBagConstraints();
		gbLayout = new GridBagLayout();
		setLayout(gbLayout);
		
		//search image
		gb.gridx = 3;
		gb.gridy = 0;
		searchImg = new JLabel();
		searchImg.setIcon(search);
		add(searchImg, gb);
		
		//search field
		gb.gridx = 4;
		gb.gridy = 0;
		searchBox = new JTextField();
		searchBox.setColumns(10);
		gb.insets = new Insets(0, 15, 0, 0);
		add(searchBox, gb);
		
		//search button
		gb.gridx = 5;
		gb.gridy = 0;
		searchButton = new JButton("Search");
		searchButton.setPreferredSize(new Dimension(75, 19));
		gb.insets = new Insets(0, 0, 0, 20);
		add(searchButton, gb);

		
		
		/**
		 * main Jtable with name, email
		 */
		
		gb.gridx = 0;
		gb.gridy = 1;
		tableOfPersons = new JTable(testData, colNames);
		tableOfPersons.setFillsViewportHeight(true);
		tableOfPersons.setModel(new DefaultTableModel(testData, colNames){
			public boolean isCellEditable(int rowIndex, int columnIndex){
				return false;
			}
			
		});
		scrollPane = new JScrollPane(tableOfPersons);
		add(scrollPane, gb);
		
		//how to add persons
		gb.gridx = 3;
		gb.gridy = 1;
		gb.gridwidth = 3;
		guideText = new JLabel("<HTML>To select several persons hold 'Ctrl' and click on several persons. <P><P>To select all use 'Ctrl' + A");
		guideText.setPreferredSize(new Dimension(200, 60));
		add(guideText, gb);
		
		
		// add persons to meeting gui
		gb.gridwidth = 1;
		gb.gridx = 4;
		gb.gridy = 3;
		add = new JButton("Add");
		add.addActionListener(new AddButtonAction());
		add(add, gb);
		
		
		pack();
		setVisible(true);
	}
	
	/**
	 * when add button is clicked, selected person(s) should be placed in the meeting gui. 
	 *
	 */
	
	class AddButtonAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
				int rowCount = tableOfPersons.getModel().getRowCount();
				int k = 0;
				String[] selectedNames = new String[rowCount];
				String[] selectedEmails = new String[rowCount];
				for(int i = 0; i< rowCount; i++){
					if(tableOfPersons.isRowSelected(i)){
						String name = (String) tableOfPersons.getValueAt(i, 0);
						String email = (String) tableOfPersons.getValueAt(i, 1);
						selectedNames[k] = name;
						selectedEmails[k] = email;
						k++;
					}
				}
				motherPanel.fillInvitationList(selectedNames, selectedEmails);
			}
		
	}
	
	
	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		JFrame frame = new JFrame("Select Persons");
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		
//		//set dimension size
//		frame.setContentPane(new ListOfPersons2());
//		frame.setPreferredSize(new Dimension(750, 750));
//		
//		frame.pack();
//		frame.setVisible(true);
//	}

}
