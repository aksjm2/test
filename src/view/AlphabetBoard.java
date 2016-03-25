package view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import controller.MainController;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class AlphabetBoard extends Board {
	private static final int DELAY = 2;
	private Graphics bg;
	private Image offScreen;
	private int oldX, oldY;
	private int index;
	private int wordWidth;

	public AlphabetBoard(MainFrame frame, MainController mainControl,
			GamePanel panel) {
		this.frame = frame;
		this.mainControl = mainControl;
		this.panel = panel;
	}

	public void init(int width, int wordWidth, int dx, int dy, int index) {
		this.width = width;
		this.wordWidth = wordWidth;
		this.dx = dx;
		this.dy = dy;
		this.index = index;
		setDoubleBuffered(true);
		loadImage(index);
		this.setVisible(true);
	}

	private void loadImage(int i) {
		int result = (int) (Math.random() * 2);

		d = Toolkit.getDefaultToolkit().getScreenSize();
		ratio = ((double) (d.getWidth() + d.getHeight()) * 0.0005);

		ImageIcon ii = new ImageIcon(mainControl.getGameController().getWord()
				.getAlphabetArr().elementAt(i).getImageURL());
		ii = new ImageIcon(ii.getImage().getScaledInstance((int) (70 * ratio),
				(int) (70 * ratio), Image.SCALE_REPLICATE));
		img = ii.getImage();

		switch (result) {
		case 0:
			this.x = (int) ((double) Math.random() * ((double) this.width - (70 * ratio)));
			break;
		case 1:
			this.x = (int) ((double) Math.random() * ((double) this.width - (70 * ratio)));
			this.x = this.x + this.width + this.wordWidth;
			break;
		default:
			break;
		}
		this.y = (int) ((double) Math.random() * ((double) this.dy - (70 * ratio)));
	}

	public void paintComponent(Graphics g) {
		if (bg == null) {
			bg = panel.getGraphics();
		}
		drawStar(bg);
		g = frame.getGraphics();
		g.drawImage(offScreen, 0, 0, this);
	}

	public void drawStar(Graphics g) {
		g.clearRect(oldX, oldY, (int) (70 * ratio), (int) (70 * ratio));
		g.drawImage(img, x, y, this);
		Toolkit.getDefaultToolkit().sync();
	}

	protected boolean cycle() {
		oldX = x;
		oldY = y;
		if (dy / 2 > y / 2) {
			y += 2;
			return true;
		} else if (dy / 2 < y / 2) {
			y -= 2;
			return true;
		} else {
			if (dx / 2 > x / 2) {
				x += 2;
				return true;
			} else if (dx / 2 < x / 2) {
				x -= 2;
				return true;
			} else {
				return false;
			}
		}
	}

	@Override
	public void run() {
		long beforeTime, timeDiff, sleep;
		beforeTime = System.currentTimeMillis();

		while (cycle()) {
			paintComponent(bg);

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
		killPanel();
		panel.refreshPress(frame);
		if (index != mainControl.getGameController().getWord().getLength() - 1)
			frame.addKeyListener(frame);
	}

}
