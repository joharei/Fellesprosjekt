package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("Serial")
public class CalendarPanecolumnHeaderPanel extends JPanel implements PropertyChangeListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel time = new JLabel("Time       ");
	private JLabel monday = new JLabel("Mon");
	private JLabel tuesday = new JLabel("Tue");
	private JLabel wednesday = new JLabel("Wed");
	private JLabel thursday = new JLabel("Thu");
	private JLabel friday = new JLabel("Fri");
	private JLabel saturday = new JLabel("Sat");
	private JLabel sunday = new JLabel("Sun");
	private int maned=0;
	private int weekNumber;
	private int dagIMnd=0;
	private int maxDagerIMnd=0;
	private int trekkFraDag=0;
	private int startDatoUke;
	private int startMndUke;
	GridBagConstraints c;
	
	public CalendarPanecolumnHeaderPanel(SmallCalendar cal){
		cal.addListener(this);
		
		
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		c = new GridBagConstraints();
		
		setLayout(new GridBagLayout());
		// TODO Auto-generated method stub
		
		if(evt.getPropertyName()==SmallCalendar.NAME_PROPERTY_WEEK_NUMBER){
			//System.out.println("Ukenr: "+(Integer)evt.getNewValue());
			weekNumber=(Integer)evt.getNewValue();
		}
		
		//System.out.println(evt.getPropertyName());
		if(evt.getPropertyName()==SmallCalendar.NAME_PROPERTY_MONTH){
			//System.out.println("Måned: "+evt.getNewValue());
			maned=(Integer) evt.getNewValue();
			
		}
		if(evt.getPropertyName()==SmallCalendar.NAME_PROPERTY_DAYS_IN_WEEK){
			//System.out.println("Dag i uken: "+evt.getNewValue());
			trekkFraDag=(Integer)evt.getNewValue();
		}
		if(evt.getPropertyName()==SmallCalendar.NAME_PROPERTY_DATE_IN_MONTH){
		//	System.out.println("Dato i måneden: "+ evt.getNewValue());
			dagIMnd=(Integer)evt.getNewValue();
		}
		if(evt.getPropertyName()==SmallCalendar.NAME_PROPERTY_MAX_DAYS_IN_WEEK){
			maxDagerIMnd=(Integer)evt.getNewValue();
			//System.out.println("Max Dager i denne mnd: "+maxDagerIMnd);
		}
		if(evt.getPropertyName()==SmallCalendar.NAME_PROPERTY_START_DATE_OF_WEEK){
			startDatoUke=(Integer)evt.getNewValue();
		}
		if(evt.getPropertyName()==SmallCalendar.NAME_PROPERTY_START_MONTH_OF_WEEK){
			startMndUke=(Integer)evt.getNewValue();
		}
		startMndUke++;
		
		int i = startDatoUke;
		System.out.println("StartDato er: "+i);
		System.out.println("StartMnd er: "+startMndUke);
		if(startDatoUke==maxDagerIMnd){
			i=1;
			startMndUke++;
		}
		if(startDatoUke!=maxDagerIMnd){
			monday.setText("Mon"+i+"/"+" ");
			i++;
		}
		if(startDatoUke==maxDagerIMnd){
			i=1;
			startMndUke++;
		}
		if(startDatoUke!=maxDagerIMnd){
			tuesday.setText("Tue"+i+"/"+startMndUke+" ");
			i++;
		}
		if(startDatoUke==maxDagerIMnd){
			i=1;
			startMndUke++;
		}
		if(startDatoUke!=maxDagerIMnd){
			wednesday.setText("Wed"+i+"/"+startMndUke+" ");
			i++;
		}
		if(startDatoUke==maxDagerIMnd){
			i=1;
			startMndUke++;
		}
		if(startDatoUke!=maxDagerIMnd){
			wednesday.setText("Thu"+i+"/"+startMndUke+" ");
			i++;
		}
		if(startDatoUke==maxDagerIMnd){
			i=1;
			startMndUke++;
		}
		if(startDatoUke!=maxDagerIMnd){
			friday.setText("Fri"+i+"/"+startMndUke+" ");
			i++;
		}
		if(startDatoUke==maxDagerIMnd){
			i=1;
			startMndUke++;
		}
		if(startDatoUke!=maxDagerIMnd){
			saturday.setText("Sat"+i+"/"+startMndUke+" ");
			i++;
		}
		if(startDatoUke==maxDagerIMnd){
			i=1;
			startMndUke++;
		}
		if(startDatoUke!=maxDagerIMnd){
			sunday.setText("sun"+i+"/"+startMndUke+" ");
			i++;
		}
		
		c.anchor=GridBagConstraints.NORTHWEST;
		add(time);
		add(monday);
		add(tuesday);
		add(wednesday);
		add(thursday);
		add(friday);
		add(saturday);
		add(sunday);
		setPreferredSize(new Dimension(400,30));
	}
}
