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
		
		for (int j = 0; j < 7; j++) {
			if(startDatoUke==maxDagerIMnd){
				if(j==0){
					createMon(j);
					break;
				}
				if(j==1){
					createTue(j);
					break;
				}
				if(j==2){
					createWed(j);
					break;
				}
				if(j==3){
					createThu(j);
					break;
				}
				if(j==4){
					createFri(j);
					break;
				}
				if(j==5){
					createSat(j);
					break;
				}
				if(j==6){
					createSun(j);
					break;
				}
				startDatoUke++;
			}
		}
		
		
		if(startDatoUke==maxDagerIMnd){
			i=1;
			startMndUke++;
			if(startDatoUke!=maxDagerIMnd){
				sunday.setText("sun"+i+"/"+startMndUke+" ");
				i++;
			}
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
	public void createMon(int i){
		monday.setText("Mon"+startDatoUke+"/"+startMndUke+" ");
		startMndUke++;
		tuesday.setText("Thu"+"1"+"/"+startMndUke+" ");
		wednesday.setText("Wed"+"2"+"/"+startMndUke+" ");
		thursday.setText("Thu"+"3"+"/"+startMndUke+" ");
		friday.setText("Fri"+"4"+"/"+startMndUke+" ");
		saturday.setText("Sat"+"5"+"/"+startMndUke+" ");
		sunday.setText("Sun"+"6"+"/"+startMndUke+" ");
	}
	public void createTue(int i){
		monday.setText("Mon"+startDatoUke+"/"+startMndUke+" ");
		startDatoUke++;
		tuesday.setText("Thu"+startDatoUke+"/"+startMndUke+" ");
		startMndUke++;
		wednesday.setText("Wed"+"1"+"/"+startMndUke+" ");
		thursday.setText("Thu"+"2"+"/"+startMndUke+" ");
		friday.setText("Fri"+"3"+"/"+startMndUke+" ");
		saturday.setText("Sat"+"4"+"/"+startMndUke+" ");
		sunday.setText("Sun"+"5"+"/"+startMndUke+" ");
	}
	public void createWed(int i){
		monday.setText("Mon"+startDatoUke+"/"+startMndUke+" ");
		startDatoUke++;
		tuesday.setText("Thu"+startDatoUke+"/"+startMndUke+" ");
		startDatoUke++;
		wednesday.setText("Wed"+startDatoUke+"/"+startMndUke+" ");
		startMndUke++;
		thursday.setText("Thu"+"1"+"/"+startMndUke+" ");
		friday.setText("Fri"+"2"+"/"+startMndUke+" ");
		saturday.setText("Sat"+"3"+"/"+startMndUke+" ");
		sunday.setText("Sun"+"4"+"/"+startMndUke+" ");
	}
	public void createThu(int i){
		monday.setText("Mon"+startDatoUke+"/"+startMndUke+" ");
		startDatoUke++;
		tuesday.setText("Thu"+startDatoUke+"/"+startMndUke+" ");
		startDatoUke++;
		wednesday.setText("Wed"+startDatoUke+"/"+startMndUke+" ");
		startDatoUke++;
		thursday.setText("Thu"+startDatoUke+"/"+startMndUke+" ");
		startMndUke++;
		friday.setText("Fri"+"1"+"/"+startMndUke+" ");
		saturday.setText("Sat"+"2"+"/"+startMndUke+" ");
		sunday.setText("Sun"+"3"+"/"+startMndUke+" ");
	}
	public void createFri(int i){
		monday.setText("Mon"+startDatoUke+"/"+startMndUke+" ");
		startDatoUke++;
		tuesday.setText("Thu"+startDatoUke+"/"+startMndUke+" ");
		startDatoUke++;
		wednesday.setText("Wed"+startDatoUke+"/"+startMndUke+" ");
		startDatoUke++;
		thursday.setText("Thu"+startDatoUke+"/"+startMndUke+" ");
		startDatoUke++;
		friday.setText("Fri"+startDatoUke+"/"+startMndUke+" ");
		startMndUke++;
		saturday.setText("Sat"+"1"+"/"+startMndUke+" ");
		sunday.setText("Sun"+"2"+"/"+startMndUke+" ");
	}
	public void createSat(int i){
		monday.setText("Mon"+startDatoUke+"/"+startMndUke+" ");
		startDatoUke++;
		tuesday.setText("Thu"+startDatoUke+"/"+startMndUke+" ");
		startDatoUke++;
		wednesday.setText("Wed"+startDatoUke+"/"+startMndUke+" ");
		startDatoUke++;
		thursday.setText("Thu"+startDatoUke+"/"+startMndUke+" ");
		startDatoUke++;
		friday.setText("Fri"+startDatoUke+"/"+startMndUke+" ");
		startDatoUke++;
		saturday.setText("Sat"+startDatoUke+"/"+startMndUke+" ");
		startMndUke++;
		sunday.setText("Sun"+"1"+"/"+startMndUke+" ");
	}
	public void createSun(int i){
		monday.setText("Mon"+startDatoUke+"/"+startMndUke+" ");
		startDatoUke++;
		tuesday.setText("Thu"+startDatoUke+"/"+startMndUke+" ");
		startDatoUke++;
		wednesday.setText("Wed"+startDatoUke+"/"+startMndUke+" ");
		startDatoUke++;
		thursday.setText("Thu"+startDatoUke+"/"+startMndUke+" ");
		startDatoUke++;
		friday.setText("Fri"+startDatoUke+"/"+startMndUke+" ");
		startDatoUke++;
		saturday.setText("Sat"+startDatoUke+"/"+startMndUke+" ");
		startDatoUke++;
		sunday.setText("Sun"+startDatoUke+"/"+startMndUke+" ");
	}
}
