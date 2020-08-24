import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

@SuppressWarnings("unused")
public class GameField extends JPanel implements ActionListener {

	private final int SIZE = 320;
	private final int DOT_SIZE = 16;
	private final int ALL_DOTS = 400;
	private Image dot;
	private Image apple;
	private Image menu;
	private int appleX;
	private int appleY;
	private int[] x = new int[ALL_DOTS];
	private int[] y = new int[ALL_DOTS];
	private int dots;
	private Timer timer;
	private boolean left = false;
	private boolean right = true;
	private boolean up = false;
	private boolean down = false;
	private boolean inGame = true;
	private boolean firstStart = true;

	public GameField() {
		setBackground(Color.darkGray);
		loadImages();
		initGame();
		addKeyListener(new FieldKeyListener());
		setFocusable(true);
	}

	public void initGame() {
		dots = 3;
		for (int i = 0; i < dots; i++) {
			x[i] = 48 - i * DOT_SIZE;
			y[i] = 48;
		}
		timer = new Timer(250, this);
		timer.start();
		createApple();
	}

	public void createApple() {
		appleX = new Random().nextInt(20) * DOT_SIZE;
		appleY = new Random().nextInt(20) * DOT_SIZE;
	}

	public void loadImages() {
		ImageIcon iia = new ImageIcon("apple.png");
		apple = iia.getImage();
		ImageIcon iid = new ImageIcon("dot.png");
		dot = iid.getImage();
		ImageIcon iim = new ImageIcon("menu.png");
		menu = iim.getImage();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (firstStart) {
			g.drawImage(menu, 0, 0, this);
		} else {
			if (inGame) {
				g.drawImage(apple, appleX, appleY, this);
				for (int i = 0; i < dots; i++) {
					g.drawImage(dot, x[i], y[i], this);
				}
				if (!timer.isRunning()) {
					String pause = "Pause";
					Font f = new Font("Arial", Font.BOLD, 14);
					g.setColor(Color.white);
					g.setFont(f);
					g.drawString(pause, 125, SIZE / 2);
				}
			} else if (!inGame) {

				String str = "Game Over";
				Font f = new Font("Arial", Font.BOLD, 14);
				g.setColor(Color.white);
				g.setFont(f);
				g.drawString(str, 125, SIZE / 2);
			}
		}
	}

	public void move() {
		for (int i = dots; i > 0; i--) {
			x[i] = x[i - 1];
			y[i] = y[i - 1];
		}
		if (left) {
			x[0] -= DOT_SIZE;
		} else if (right) {
			x[0] += DOT_SIZE;
		} else if (up) {
			y[0] -= DOT_SIZE;
		} else if (down) {
			y[0] += DOT_SIZE;
		}
	}

	public void checkApple() {
		if (x[0] == appleX && y[0] == appleY) {
			dots++;
			createApple();
			timer.setDelay(timer.getDelay() - 10);
		}
	}

	public void checkCollisions() {
		for (int i = dots; i > 0; i--) {
			if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
				inGame = false;
			}
		}
		if (x[0] > SIZE) {
			inGame = false;
		} else if (x[0] < 0) {
			inGame = false;
		} else if (y[0] > SIZE) {
			inGame = false;
		} else if (y[0] < 0) {
			inGame = false;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (inGame && firstStart == false) {
			checkApple();
			checkCollisions();
			move();
		}
		repaint();
	}

	class FieldKeyListener extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			super.keyPressed(e);
			int key = e.getKeyCode();
			if (timer.isRunning()) {
				if (key == KeyEvent.VK_LEFT && !right) {
					left = true;
					up = false;
					down = false;
				} else if (key == KeyEvent.VK_RIGHT && !left) {
					right = true;
					up = false;
					down = false;
				} else if (key == KeyEvent.VK_UP && !down) {
					up = true;
					right = false;
					left = false;
				} else if (key == KeyEvent.VK_DOWN && !up) {
					down = true;
					right = false;
					left = false;
				} else if (key == KeyEvent.VK_SPACE && firstStart == false) {
					timer.stop();
					repaint();
				} else if (key == KeyEvent.VK_ENTER) {
					firstStart = false;
				}
			} else if (key == KeyEvent.VK_SPACE && !timer.isRunning()) {
				timer.start();
			}

		}

	}

}
