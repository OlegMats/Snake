import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	public MainWindow() {
		setTitle("Snake");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(514, 540);
		buttons();
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}

	public void buttons() {
		JPanel buttonsPanel = new JPanel(new GridLayout(4, 1, 0, 20));
		creatingButtons(buttonsPanel);
		JPanel east = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.ipady = 40;
		gbc.ipadx = 50;
		east.add(buttonsPanel, gbc);
		this.add(east, BorderLayout.CENTER);
	}

	public void creatingButtons(JPanel pnl) {
		JButton start = new JButton("start");
		start.setFont(new Font("Arial", Font.BOLD, 20));
		start.setActionCommand("start");

		JButton options = new JButton("options");
		options.setFont(new Font("Arial", Font.BOLD, 20));
		options.setActionCommand("options");

		JButton records = new JButton("records");
		records.setFont(new Font("Arial", Font.BOLD, 20));
		records.setActionCommand("records");

		JButton exit = new JButton("exit");
		exit.setFont(new Font("Arial", Font.BOLD, 20));
		exit.setActionCommand("exit");

		pnl.add(start);
		pnl.add(options);
		pnl.add(records);
		pnl.add(exit);

		start.addActionListener(new ButtonActionListener());
		options.addActionListener(new ButtonActionListener());
		records.addActionListener(new ButtonActionListener());
		exit.addActionListener(new ButtonActionListener());
	}

	public class ButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand() == "start") {
				getContentPane().removeAll();
				getContentPane().add(new GameField());
				validate();
				repaint();
			} else if (e.getActionCommand() == "exit") {
				System.exit(1);
			}

		}

	}

}