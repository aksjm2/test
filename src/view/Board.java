package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import controller.MainController;

public class Board extends JPanel implements Runnable {

	protected static final int B_WIDTH = 350;
	protected static final int B_HEIGHT = 350;
	protected static final int DELAY = 8;
	protected int width, height;
	protected MainFrame frame;
	protected Image img;
	protected Thread animator;
	protected int x = 0, y = 0;
	protected int dx, dy;
	protected GamePanel panel;
	protected MainController mainControl;
	protected Dimension d;
	double ratio;

	public Board() {
	}

	public Board(MainFrame frame, MainController mainControl, GamePanel panel) {
		this.frame = frame;
		this.mainControl = mainControl;
		this.panel = panel;
		initBoard();
	}

	public void killPanel() {
		this.setVisible(false);
		this.getGraphics().clearRect(0, 0, frame.getWidth(), frame.getHeight());
	}

	public Thread getThread() {
		return this.animator;
	}

	private void loadImage() {
		d = Toolkit.getDefaultToolkit().getScreenSize();
		ratio = ((double) (d.getWidth() + d.getHeight()) * 0.0005);
		ImageIcon ii = new ImageIcon(mainControl.getGameController().getWord()
				.getImageURL());
		ii = new ImageIcon(ii.getImage().getScaledInstance(
				(int) (280 * ratio / 2), (int) (280 * ratio / 2),
				Image.SCALE_REPLICATE));
		img = ii.getImage();
	}

	private void initBoard() {
		this.setdemension();
		setDoubleBuffered(true);
		loadImage();
	}

	public void setdemension() {
		this.width = frame.getWidth();
		this.height = frame.getHeight();
		x = width / 2 - 150;
		y = height / 2 - 150;
		switch (mainControl.getGameController().getWord().getType()) {
		case 1:
			dx = width / 2 - width / 4 - 150;
			dy = height / 2 - height / 4 - 150;
			break;
		case 2:
			dx = width / 2 + width / 4 - 150;
			dy = height / 2 - height / 4 - 150;
			break;
		case 3:
			dx = width / 2 - width / 4 - 150;
			dy = height / 2 + height / 4 - 150;
			break;
		case 4:
			dx = width / 2 + width / 4 - 150;
			dy = height / 2 + height / 4 - 150;
			break;
		default:
			break;
		}
	}

	@Override
	public void addNotify() {
		super.addNotify();
		animator = new Thread(this);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawimg(g);
	}

	public void drawimg(Graphics g) {
		ImageIcon categoryicon = new ImageIcon("image/reward/category.jpg");
		g.drawImage(categoryicon.getImage(), 0, 0, frame.getWidth(),
				frame.getHeight(), null);
		g.drawImage(img, x, y, this);
		Toolkit.getDefaultToolkit().sync();
	}

	protected boolean cycle(int flag) {
		
		switch(flag){
		case 0:
			x--;
			y--;
			break;
		case 1:
			x--;
			y++;
			break;
		case 2:
			x++;
			y--;
			break;			
		case 3:
			x++;
			y++;
			break;
		default:
			break;
		}
		if (x == dx || y == dy)
			return false;
		else
			return true;
	}

	@Override
	public void run() {

		long beforeTime, timeDiff, sleep;
		int flag;
		beforeTime = System.currentTimeMillis();
		if (x > dx && y > dy) {
			flag = 0;
		} else if (x > dx && y < dy) {
			flag = 1;
		} else if (x < dx && y > dy) {
			flag = 2;
		} else {
			flag = 3;
		}
		
		while (cycle(flag)) {

			repaint();
			timeDiff = System.currentTimeMillis() - beforeTime;
			sleep = DELAY - timeDiff;

			if (sleep < 0) {
				sleep = 2;
			}

			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				System.out.println("Interrupted: " + e.getMessage());
			}

			beforeTime = System.currentTimeMillis();
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		killPanel();
		mainControl.getGameController().gameStart();
		panel.killPanel();
		panel.init(frame);
		frame.addKeyListener(frame);
	}
}