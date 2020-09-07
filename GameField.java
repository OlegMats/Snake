import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;

import javax.swing.*;
import javax.swing.Timer;

public class GameField extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final int SIZE = 475;
	private final int DOT_SIZE = 25;
	private final int ALL_DOTS = 475;
	private Image dot;
	private Image apple;
	private int appleX;
	private int appleY;
	private int[] x = new int[ALL_DOTS];
	private int[] y = new int[ALL_DOTS];
	private int dots;
	private int score;
	private Timer timer;
	private boolean left = false;
	private boolean right = true;
	private boolean up = false;
	private boolean down = false;
	private boolean inGame = true;

	public GameField() {
		setBackground(Color.darkGray);
		loadImages();
		initGame();
		addKeyListener(new FieldKeyListener());
	}

	public void initGame() {
		dots = 3;
		for (int i = 0; i < dots; i++) {
			x[i] = 75 - i * DOT_SIZE;
			y[i] = 75;
		}
		timer = new Timer(250, this);
		timer.start();
		createApple();
	}

	public void createApple() {
		appleX = new Random().nextInt(19) * DOT_SIZE;
		appleY = new Random().nextInt(19) * DOT_SIZE;
	}

	public void loadImages() {
		ImageIcon iia = new ImageIcon("apple.png");
		apple = iia.getImage();
		ImageIcon iid = new ImageIcon("dot.png");
		dot = iid.getImage();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (inGame) {
			g.drawImage(apple, appleX, appleY, this);
			for (int i = 0; i < dots; i++) {
				g.drawImage(dot, x[i], y[i], this);
			}
			g.setColor(Color.yellow);
			g.setFont(new Font("Arial", Font.BOLD, 14));
			g.drawString("Score: " + score, 420, 11);
			if (!timer.isRunning()) {
				String pause = "Pause";
				Font f = new Font("Arial", Font.BOLD, 20);
				g.setColor(Color.white);
				g.setFont(f);
				g.drawString(pause, 219, SIZE / 2);
			}
		} else if (!inGame) {
			String str = "Game Over";
			String rest = "\"R\" to RESTART";
			String exit = "\"E\" to EXIT";
			Font s = new Font("Arial", Font.BOLD, 21);
			Font r = new Font("Arial", Font.BOLD, 16);
			g.setColor(Color.white);
			g.setFont(s);
			g.drawString(str, 200, SIZE / 4);
			g.setFont(r);
			g.drawString(rest, 200, SIZE / 2);
			g.drawString(exit, 200, SIZE / 2 + SIZE / 4);
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
			score++;
			createApple();
			timer.setDelay(timer.getDelay() - 5);
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
		if (inGame) {
			checkApple();
			checkCollisions();
			move();
		} else {
			new Records(score);
			timer.stop();
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
				} else if (key == KeyEvent.VK_SPACE) {
					timer.stop();
					repaint();
				}
			} else {
				if (key == KeyEvent.VK_SPACE && !timer.isRunning()) {
					timer.start();
				} else if (key == KeyEvent.VK_E && !inGame) {
					System.exit(1);
				} else if (key == KeyEvent.VK_R && !inGame) {
					score = 0;
					inGame = true;
					left = false;
					right = true;
					up = false;
					down = false;
					timer.stop();
					initGame();
				}
			}
		}
	}

}