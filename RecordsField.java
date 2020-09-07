import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

public class RecordsField extends JPanel {

	private static final long serialVersionUID = 1L;

	public RecordsField() {
		setBackground(Color.darkGray);
		JList records = new JList(new Records().readingRecord().toArray());
		records.setFont(records.getFont().deriveFont(22.0f));
		add(records);
		JButton back = new JButton("back");
		add(back);

		
	}

}
