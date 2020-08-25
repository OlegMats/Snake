import javax.swing.*;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {

	public MainWindow() {
		setTitle("Snake");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		add(new GameField());
		setSize(350, 370);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		MainWindow mw = new MainWindow();
	}

}
