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
	
	private JLabel time = new JLabel("Time       ");
	private JLabel monday = new JLabel("Mon");
	private JLabel tuesday = new JLabel("Tue");
	private JLabel wednesday = new JLabel("Wed");
	private JLabel thursday = new JLabel("Thu");
	private JLabel friday = new JLabel("Fri");
	private JLabel saturday = new JLabel("Sat");
	private JLabel sunday = new JLabel("Sun");
	private int maned=0;
	private int [] datoerIMnd=null;
	private int weekNumber;
	private int dag=0;
	private int dagIUken=0;
	private int dagIMnd=0;
	private int trekkFraDag=0;
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
		if(evt.getPropertyName()==SmallCalendar.NAME_PROPERTY_DATE_IN_MONTH){
			for (int i = 0; i < 7; i++) {
				datoerIMnd[i]=(Integer)evt.getNewValue();
				System.out.print(datoerIMnd[i]);
			}
		}
		if(weekNumber==52||weekNumber==5||weekNumber==9||weekNumber==13||weekNumber==18||weekNumber==22||weekNumber==26||weekNumber==31||weekNumber==35||weekNumber==40||weekNumber==44||weekNumber==48){
			
			int temp=7-trekkFraDag;
			
			System.out.println("testtall: "+dagIMnd+trekkFraDag);
			int teller = 0;
			for (int i = trekkFraDag; i < 7; i++) {
				//System.out.println("antalldager: "+i);
				teller++;
			}
			int i = (dagIMnd+teller);
			System.out.println("en: "+i);
			
			sunday.setText("Sun"+i+"/"+maned+" ");
			i--;
			if(i==0){
				if(maned==1||maned==3||maned==5||maned==7||maned==8||maned==10||maned==12){
					System.out.println("to: "+i);
					saturday.setText("Sat"+"31/"+maned+" ");
					friday.setText("Fri"+"30/"+maned+" ");
					thursday.setText("Thu"+"29/"+maned+" ");
					wednesday.setText("Wed"+"28/"+maned+" ");
					tuesday.setText("Tue"+"27/"+maned+" ");
					monday.setText("Mon"+"26/"+maned+" ");
				}
				if(maned==4||maned==6||maned==9||maned==11){
					saturday.setText("Sat"+"30/"+maned+" ");
					friday.setText("Fri"+"29/"+maned+" ");
					thursday.setText("Thu"+"28/"+maned+" ");
					wednesday.setText("Wed"+"27/"+maned+" ");
					tuesday.setText("Tue"+"26/"+maned+" ");
					monday.setText("Mon"+"25/"+maned+" ");
				}
				if(maned==2){
					saturday.setText("Sat"+"29/"+maned+" ");
					friday.setText("Fri"+"28/"+maned+" ");
					thursday.setText("Thu"+"27/"+maned+" ");
					wednesday.setText("Wed"+"26/"+maned+" ");
					tuesday.setText("Tue"+"25/"+maned+" ");
					monday.setText("Mon"+"24/"+maned+" ");
				}
			}
			saturday.setText("Sat"+i+"/"+maned+" ");
			i--;
			if(i==0){
				if(maned==1||maned==3||maned==5||maned==7||maned==8||maned==10||maned==12){
					
					friday.setText("Fri"+"31/"+maned+" ");
					thursday.setText("Thu"+"30/"+maned+" ");
					wednesday.setText("Wed"+"29/"+maned+" ");
					tuesday.setText("Tue"+"28/"+maned+" ");
					monday.setText("Mon"+"27/"+maned+" ");
				}
				if(maned==4||maned==6||maned==9||maned==11){
					friday.setText("Fri"+"30/"+maned+" ");
					thursday.setText("Thu"+"29/"+maned+" ");
					wednesday.setText("Wed"+"28/"+maned+" ");
					tuesday.setText("Tue"+"27/"+maned+" ");
					monday.setText("Mon"+"26/"+maned+" ");
				}
				if(maned==2){
					friday.setText("Fri"+"28/"+maned+" ");
					thursday.setText("Thu"+"27/"+maned+" ");
					wednesday.setText("Wed"+"26/"+maned+" ");
					tuesday.setText("Tue"+"25/"+maned+" ");
					monday.setText("Mon"+"24/"+maned+" ");
				}
			}
			friday.setText("Fri"+i+"/"+maned+" ");
			i--;
			if(i==0){
				if(maned==1||maned==3||maned==5||maned==7||maned==8||maned==10||maned==12){
					
					thursday.setText("Thu"+"31/"+maned+" ");
					wednesday.setText("Wed"+"30/"+maned+" ");
					tuesday.setText("Tue"+"29/"+maned+" ");
					monday.setText("Mon"+"28/"+maned+" ");
				}
				if(maned==4||maned==6||maned==9||maned==11){
					thursday.setText("Thu"+"30/"+maned+" ");
					wednesday.setText("Wed"+"29/"+maned+" ");
					tuesday.setText("Tue"+"28/"+maned+" ");
					monday.setText("Mon"+"27/"+maned+" ");
				}
				if(maned==2){
					thursday.setText("Thu"+"28/"+maned+" ");
					wednesday.setText("Wed"+"27/"+maned+" ");
					tuesday.setText("Tue"+"26/"+maned+" ");
					monday.setText("Mon"+"25/"+maned+" ");
				}
				
			}
			thursday.setText("Thu"+i+"/"+maned+" ");
			i--;
			if(i==0){
				if(maned==1||maned==3||maned==5||maned==7||maned==8||maned==10||maned==12){
					
					wednesday.setText("Wed"+"31/"+maned+" ");
					tuesday.setText("Tue"+"30/"+maned+" ");
					monday.setText("Mon"+"29/"+maned+" ");
				}
				if(maned==4||maned==6||maned==9||maned==11){
					wednesday.setText("Wed"+"30/"+maned+" ");
					tuesday.setText("Tue"+"29/"+maned+" ");
					monday.setText("Mon"+"28/"+maned+" ");
				}
				if(maned==2){
					wednesday.setText("Wed"+"28/"+maned+" ");
					tuesday.setText("Tue"+"27/"+maned+" ");
					monday.setText("Mon"+"26/"+maned+" ");
				}
			}
			wednesday.setText("Wed"+i+"/"+maned+" ");
			i--;
			if(i==0){
				if(maned==1||maned==3||maned==5||maned==7||maned==8||maned==10||maned==12){
					
					tuesday.setText("Tue"+"31/"+maned+" ");
					monday.setText("Mon"+"30/"+maned+" ");
				}
				if(maned==4||maned==6||maned==9||maned==11){
					tuesday.setText("Tue"+"30/"+maned+" ");
					monday.setText("Mon"+"29/"+maned+" ");
				}
				if(maned==2){
					tuesday.setText("Tue"+"28/"+maned+" ");
					monday.setText("Mon"+"27/"+maned+" ");
				}
			}
			tuesday.setText("Thu"+i+"/"+maned+" ");
			i--;
			if(i==0){
				if(maned==1||maned==3||maned==5||maned==7||maned==8||maned==10||maned==12){
					
					monday.setText("Mon"+"31/"+maned+" ");
				}
				if(maned==4||maned==6||maned==9||maned==11){
					monday.setText("Mon"+"30/"+maned+" ");
				}
				if(maned==2){
					monday.setText("Mon"+"28/"+maned+" ");
				}
			}
			monday.setText("Mon"+i+"/"+maned+" ");
			
			if(i==0){
				System.out.println("Noe er galt!!!!!!!!!");
			}
			System.out.println("--------------");
			
		}
		
		if(dagIMnd-trekkFraDag>0){
			dag=dagIMnd-trekkFraDag+1;
			monday.setText("Mon"+dag+"/"+maned+" ");
		}
			
		if(dagIMnd-trekkFraDag>0){
			dag=dagIMnd-trekkFraDag+1;
			tuesday.setText("Tue"+(dag+1)+"/"+maned+" ");
		}
		if(dagIMnd-trekkFraDag>0){
			dag=dagIMnd-trekkFraDag+1;
			wednesday.setText("Wed"+(dag+2)+"/"+maned+" ");
		}
		if(dagIMnd-trekkFraDag>0){
			dag=dagIMnd-trekkFraDag+1;
			thursday.setText("Thu"+(dag+3)+"/"+maned+" ");
		}
		if(dagIMnd-trekkFraDag>0){
			dag=dagIMnd-trekkFraDag+1;
			friday.setText("Fri"+(dag+4)+"/"+maned+" ");
		}
		if(dagIMnd-trekkFraDag>0){
			dag=dagIMnd-trekkFraDag+1;
			saturday.setText("Sat"+(dag+5)+"/"+maned+" ");
		}
		if(dagIMnd-trekkFraDag>0){
			dag=dagIMnd-trekkFraDag+1;
			sunday.setText("Sun"+(dag+6)+"/"+maned+" ");
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
