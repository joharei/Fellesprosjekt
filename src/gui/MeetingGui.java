package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

public class MeetingGui extends AppointmentGui{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel invite;
	private JButton add, remove, newCreate, newCancel;
	private JList addPersons;
	private JScrollPane addPersonsScroll;
	
	private GridBagConstraints gb;
//	protected GridBagLayout gbLayout;
	
	public MeetingGui(){
		
		super();


		this.setModal(true);
		this.setPreferredSize(new Dimension(600,700));
		pack();
		
		gb = getGridBagConstraints();
		
		remove(create);
		remove(cancel);

//		gb = new GridBagConstraints();
//		gbLayout = new GridBagLayout();
//		setLayout(gbLayout);
		//invite
		gb.gridx = 0;
		gb.gridy = 5;
		invite = new JLabel("Invite: ");
		gb.insets = new Insets(10, 30, 50, 0);
		add(invite, gb);
		
		
		//add persons field
		gb.gridx = 1;
		gb.gridy = 5;
		addPersons = new JList();
		addPersons.setModel(new DefaultListModel());
		gb.insets = new Insets(48, 50, 0, 0);
		
		addPersonsScroll = new JScrollPane(addPersons);
		addPersonsScroll.setPreferredSize(new Dimension(170, 100));
		add(addPersonsScroll, gb);
		
		
		//add button
		gb.gridx = 2;
		gb.gridy = 5;
		add = new JButton("add");
		add.setPreferredSize(new Dimension(80, 30));
		gb.insets = new Insets(10, 0, 32, 0);
		add.addActionListener(new AddButtonAction());
		add(add, gb);
		
		//remove button
		gb.gridx = 2;
		gb.gridy = 5;
		remove = new JButton("remove");
		remove.setPreferredSize(new Dimension(80, 30));
		gb.insets = new Insets(45, 0, 0, 0);
		remove.addActionListener(new RemoveButtonAction());
		add(remove, gb);
		
		
		//create and cancel button
		gb.gridx = 1;
		gb.gridy = 6;
		newCreate = new JButton("Create");
		gb.insets = new Insets(75, 0, 0, 50);
		add(newCreate, gb);
		
		gb.gridx = 1;
		gb.gridy = 6;
		newCancel =  new JButton("cancel");
		gb.insets = new Insets(75, 100, 0, 0);
		add(newCancel, gb);
		
		addActionListeners();
	}
	
	class RemoveButtonAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Object[] userStrings = addPersons.getSelectedValues();
			DefaultListModel model = (DefaultListModel) addPersons.getModel();
			for(int i = 0; i<userStrings.length; i++){
				 model.removeElement(userStrings[i]);
			}
		}
		
	}
	
	class AddButtonAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JButton b = (JButton) e.getSource();
			MeetingGui m = (MeetingGui) b.getParent().getParent().getParent().getParent();
			ListOfPersons2 selectPersons = new ListOfPersons2(m);
		}
		
	}
	
	private void addActionListeners() {
		newCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("goes back");
				setVisible(false);
			}
		});
		newCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("goes back");
				DefaultListModel model = (DefaultListModel) addPersons.getModel();
				if(model.isEmpty()){
					JFrame frame = null;
					JOptionPane.showMessageDialog(frame, "<HTML>Some fields are empty, please take care of the empty fields :) " );
				}
				else{
					JFrame frame = null;
					JOptionPane.showMessageDialog(frame, "<HTML>A new meeting has been created. <P> Message has been sent to: " + addPersons.getModel() );
					
					setVisible(false);
					
				}
			}
		});
	}

	public void fillInvitationList(String[] selectedNames,
			String[] selectedEmails) {
		// TODO Auto-generated method stub
		int count = selectedNames.length;
		for(int i = 0; i<count; i++){
			if(selectedNames[i] != null){
				String userString = selectedNames[i] + " ; " + selectedEmails[i];
				DefaultListModel model = (DefaultListModel) addPersons.getModel();
				if(!model.contains(userString)){
					model.addElement(userString);
				}
			}
			continue;
		}
	}
	/**
	 * @param args
	 */
	
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		MeetingGui gui = new MeetingGui();
//		gui.pack();
//		gui.setVisible(true);
//}
}

