package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ChangeMeetingGui extends MeetingGui{
	
	/**
	 * This class is for editing or deleting a meeting created by a user
	 */
	
	private JButton change, newCancel, delete;
	private GridBagConstraints gb;
	
	
	public ChangeMeetingGui(){
		
		super();
		
		this.setModal(true);
		this.setPreferredSize(new Dimension(600,700));
		pack();
		
		gb = new GridBagConstraints();
		
		remove(getNewCreate());
		remove(getNewCancel());
		
		getIconImages();
		getStartTimeField();
		getEndTimeField();
		getDescriptionField();
		getPlaceField1();
		getPlaceField2();
		getAddPersons();
		
		//add change and new cancel buttons
		gb.gridx = 1;
		gb.gridy = 6;
		change = new JButton("Save");
		gb.insets = new Insets(75, 0, 0, 50);
		change.addActionListener(new ChangeAction());
		add(change, gb);
		
		
		gb.gridx = 1;
		gb.gridy = 6;
		newCancel = new JButton("cancel");
		gb.insets = new Insets(75, 100, 0, 0);
		newCancel.addActionListener(new CancelAction());
		add(newCancel, gb);
		
		//adding a delete button with action listener 
		gb.gridx = 1;
		gb.gridy = 7;
		delete = new JButton("Delete Meeting");
		gb.insets = new Insets(10, 30, 0, 0);
		delete.addActionListener(new DeleteAction());
		add(delete, gb);
	}
	
	class ChangeAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			DefaultListModel model = (DefaultListModel) getAddPersons().getModel();
			if(model.isEmpty() || getCalIcon().getDate() == null || getDescriptionField().getText().isEmpty() || ((getPlaceField1().getText().isEmpty() || getPlaceField1().getText().equals("Type or click button"))
					&& getPlaceField2().getSelectedItem().equals("choose"))){
				JFrame frame = null;
				JOptionPane.showMessageDialog(frame, "<HTML>Some fields are empty, please take care of the empty fields :) " );
			}
			else{
				JFrame frame = null;
				JOptionPane.showMessageDialog(frame, "<HTML>Changes saved. <P> Message has been sent to: " + getAddPersons().getModel() );
				
				
				setVisible(false);
			}
		}
	}
	
	class CancelAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			setVisible(false);
		}
		
	}
	
	class DeleteAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JFrame frame = null;
			Object[] options = {"Yes", "No"};
			int selection = JOptionPane.showOptionDialog(frame, "Are you sure you want to delete this meeting?", "Final confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
			if(selection == JOptionPane.YES_OPTION){
				JOptionPane.showMessageDialog(frame, "<HTML>Meeting deleted. <P> Message has been sent to: " + getAddPersons().getModel() );
				
				setVisible(false);
			}
		}
		
	}
	

	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		
//		ChangeMeetingGui gui = new ChangeMeetingGui();
//		gui.pack();
//		gui.setVisible(true);
//
//	}

}
