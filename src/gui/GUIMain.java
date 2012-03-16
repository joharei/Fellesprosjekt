package gui;

/*
 * GUIMain
 * 
 * Version 1.26
 * 
 * Author Magnus Solheim Thrap
 * 
 * GUI-class.
 * Purpose: main gui-class: show buttons for all tabs (chef/driver/order etc)
 * 
 */


import java.awt.Color;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * GUI-class: main gui-class. Displays the startup screen with buttons to 
 * access other views
 * 
 * @author Magnus Solheim Freak Thrap
 * @version 1.26
 */
@SuppressWarnings("serial")
public class GUIMain extends JFrame {
	protected static final String IMAGE_PATH = "gui/";
	protected static final Color COLOR_BACKGROUND = Color.WHITE;
	protected static final Color COLOR_IN_PROGRESS = new Color(247, 199, 173);
	protected static final Color COLOR_READY = new Color(198, 243, 214);
	protected static final Color COLOR_IN_DELIVERY = new Color(206, 219, 239);
	protected static final Font FONT_HEADER = new Font("", Font.PLAIN, 20);
	protected static final Font FONT_FIELD = new Font("", Font.PLAIN, 18);

	protected GridBagConstraints c;

	private JLabel helpLabel;
	private JButton orderButton;
	private JButton menuButton;
	private JButton chefButton;
	private JButton driverButton;
	private JButton takeOutButton;
	private JButton adminButton;

	/**
	 * Main method: starts the program
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GUIMain gmain = new GUIMain();
		gmain.setVisible(true);
		gmain.addHelpLabel();
	}

	/**
	 * Constructs the GUIMain object
	 */
	public GUIMain() {
		getContentPane().setBackground(COLOR_BACKGROUND);
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = (double) 1 / 12;

		helpLabel = new JLabel("");
		orderButton = new JButton("Ny ordre");
		menuButton = new JButton("Meny");
		adminButton = new JButton("Admin");
		chefButton = new JButton("Kokk");
		driverButton = new JButton("Sjåfør");
		takeOutButton = new JButton("Take-out");

		// adding action listeners to the buttons
		addActionListeners();

		// set style
		setButtonStyle();

		// making invisible grid with width 12
		for (int i = 0; i < 12; i++) {
			JLabel label = new JLabel("  ");
			c.gridx = i;
			c.gridy = 0;
			add(label, c);
		}

		// adding buttons to JFrame
		addJComponent(getButtonPanel(), 0, 0, 12, 1);
		// adding invisible label to create space between GUIMain and children
		addLabel("", 0, 1, 1, 20);
	}

	/**
	 * Add action listeners to buttons
	 */
	private void addActionListeners() {
		orderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUIMain gorder = new GUIOrder();
				gorder.setVisible(true);
				setVisible(false);

				gorder.setOrderButtonTransparent();
			}
		});
		menuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUIMain gmenu = new GUIMenu();
				gmenu.setVisible(true);
				gmenu.menuButton
						.setIcon(new ImageIcon(IMAGE_PATH + "food.gif"));
				gmenu.menuButton.setBackground(COLOR_BACKGROUND);
				gmenu.menuButton.setForeground(Color.BLACK);
				gmenu.menuButton.setBorder(null);
				setVisible(false);
			}
		});
		chefButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUIMain gchef = new GUIChef();
				gchef.setVisible(true);
				gchef.chefButton
						.setIcon(new ImageIcon(IMAGE_PATH + "chef.gif"));
				gchef.chefButton.setBackground(COLOR_BACKGROUND);
				gchef.chefButton.setForeground(Color.BLACK);
				gchef.chefButton.setBorder(null);
				setVisible(false);
			}
		});
		driverButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUIMain gdriver = new GUIDriver();
				gdriver.setVisible(true);
				setVisible(false);

				gdriver.setDriverButtonTransparent();
			}
		});
		takeOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUIMain gtakeout = new GUITakeOut();
				gtakeout.setVisible(true);
				gtakeout.takeOutButton.setIcon(new ImageIcon(IMAGE_PATH
						+ "house.gif"));
				gtakeout.takeOutButton.setBackground(COLOR_BACKGROUND);
				gtakeout.takeOutButton.setForeground(Color.BLACK);
				gtakeout.takeOutButton.setBorder(null);
				setVisible(false);
			}
		});
		adminButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//update delivery fee limit
				String input = JOptionPane.showInputDialog(getContentPane(),
						"Over hvilken verdi(i kr) skal levering være gratis?");
				if (!isParseableToInt(input) || Integer.parseInt(input) < 0) {
					JOptionPane.showMessageDialog(getContentPane(), 
							"Ugyldig input!", "", JOptionPane.ERROR_MESSAGE);
				} else {
					int newLimit = Integer.parseInt(input);
					String sql = "UPDATE settings SET delivery_fee='" 
							+ newLimit + "'";
					Database.executeUpdate(sql);
					JOptionPane.showMessageDialog(getContentPane(),
							"Ny grense er satt til: " + newLimit + " kr");
				}
			}
		});
	}

	/**
	 * Set style on buttons
	 */
	private void setButtonStyle() {
		Color bg = new Color(178, 178, 178);

		orderButton.setBackground(bg);
		menuButton.setBackground(bg);
		adminButton.setBackground(bg);
		chefButton.setBackground(bg);
		driverButton.setBackground(bg);
		takeOutButton.setBackground(bg);

		Color fg = COLOR_BACKGROUND;

		orderButton.setForeground(fg);
		menuButton.setForeground(fg);
		adminButton.setForeground(Color.GRAY);
		chefButton.setForeground(fg);
		driverButton.setForeground(fg);
		takeOutButton.setForeground(fg);
	}

	/**
	 * Get buttons in a new panel
	 * 
	 * @return {@code JPanel} object with buttons
	 */
	private JPanel getButtonPanel() {
		GridBagConstraints gbc = new GridBagConstraints();
		JPanel buttonPanel = new JPanel();

		buttonPanel.setLayout(new GridBagLayout());

		gbc.weightx = (double) 1 / 12;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 2;
		gbc.ipady = 5;
		gbc.gridx = 0;
		buttonPanel.add(orderButton, gbc);
		gbc.gridx = 2;
		buttonPanel.add(menuButton, gbc);
		gbc.gridx = 4;
		buttonPanel.add(adminButton, gbc);
		gbc.gridx = 6;
		buttonPanel.add(chefButton, gbc);
		gbc.gridx = 8;
		buttonPanel.add(driverButton, gbc);
		gbc.gridx = 10;
		buttonPanel.add(takeOutButton, gbc);
		buttonPanel.setBackground(COLOR_BACKGROUND);

		return buttonPanel;
	}

	/**
	 * Push all components to the top using an invisible label
	 */
	protected void addHelpLabel() {
		c.ipady = 0;
		c.weighty = 1.0;
		c.gridx = 1;
		c.gridwidth = 2;
		c.gridy += 1;
		add(helpLabel, c);
		c.weighty = 0;
	}

	/**
	 * Add a JComponent to a specific location in JFrame
	 * 
	 * @param jComponent
	 *            A component in the frame
	 * @param x
	 *            X-position
	 * @param y
	 *            Y-position
	 * @param width
	 *            Gridwidth
	 * @param heightpx
	 *            Height in pixels
	 */
	protected void addJComponent(JComponent jComponent, int x, int y,
			int width, int heightpx) {
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = width;
		c.ipady = heightpx;
		add(jComponent, c);
	}

	/**
	 * Add a JLabel to a specific location in JFrame
	 * 
	 * @param text
	 *            Label text
	 * @param x
	 *            X-position
	 * @param y
	 *            Y-position
	 * @param width
	 *            Gridwidth
	 * @param heightpx
	 *            Height in pixels
	 */
	protected void addLabel(String text, int x, int y, int width, int heightpx) {
		JLabel label = new JLabel(text);
		addJComponent(label, x, y, width, heightpx);
	}

	/**
	 * Determine if a String can be parsed to Integer
	 * 
	 * @param string
	 *            <code>String</code> that needs parsing if possible
	 * 
	 * @return {@code true} if string is can be parsed to int {@code false}
	 *         otherwise.
	 */
	public static boolean isParseableToInt(String string) {
		try {
			Integer.parseInt(string);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	/**
	 * Determine current row
	 * 
	 * @return {@code int} row
	 */
	protected int getCurrentY() {
		return c.gridy;
	}

	/**
	 * Remove border and set white background on order-button, to make it look
	 * selected
	 */
	protected void setOrderButtonTransparent() {
		orderButton.setIcon(new ImageIcon(IMAGE_PATH + "phone.gif"));
		orderButton.setBackground(COLOR_BACKGROUND);
		orderButton.setForeground(Color.BLACK);
		orderButton.setBorder(null);
	}

	/**
	 * Remove border and set white background on order-button, to make it look
	 * selected
	 */
	protected void setDriverButtonTransparent() {
		driverButton.setIcon(new ImageIcon(IMAGE_PATH + "car.gif"));
		driverButton.setBackground(COLOR_BACKGROUND);
		driverButton.setForeground(Color.BLACK);
		driverButton.setBorder(null);
	}

	/**
	 * Create add button
	 * 
	 * @return {@code JButton} add button
	 */
	protected JButton getNewAddButton() {
		JButton addButton = new JButton(new ImageIcon(IMAGE_PATH + "add.gif"));
		addButton.setBackground(null);
		addButton.setBorder(null);
		return addButton;
	}

	/**
	 * Create next button
	 * 
	 * @return {@code JButton} next button
	 */
	protected JButton getNewNextButton() {
		JButton nextButton = new JButton(new ImageIcon(IMAGE_PATH + "next.gif"));
		nextButton.setBackground(null);
		nextButton.setBorder(null);
		return nextButton;
	}

	/**
	 * Create finish button
	 * 
	 * @return {@code JButton} finish button
	 */
	protected JButton getNewFinishButton() {
		JButton finishButton = new JButton(new ImageIcon(IMAGE_PATH
				+ "finish.gif"));
		finishButton.setBackground(null);
		finishButton.setBorder(null);
		return finishButton;
	}
}
