package view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.*;

import controller.MainController;
import model.Alphabet;
import model.Database;
import model.Word;

import java.awt.*;

public class GamePanel extends JPanel {
	private Word word;
	private Database db;

	double ratio, ratio2;
	int count;
	int wordLength;
	int alphabetPosX[] = new int[10];
	int alphabetPosY[] = new int[10];
	int alphabetNow;
	int keyboardWidth;
	int keyboardHeight;

	private MainController mainControl;
	private AlphabetBoard alphabetboard;
	private Alphabet alphabet;
	private JPanel rewardPanel;
	private Board board;

	private ImageIcon wordImage;
	private ImageIcon[] alphabetImage;
	private ImageIcon vKeyboardImage;

	private JLabel wordLabel;
	private JLabel alphabetLabel[];
	private JLabel keyboardLabel;

	public GamePanel() {

	}

	public GamePanel(MainFrame frame, MainController mainControl) {
		ratio = ((double) (frame.getWidth() + frame.getHeight()) * 0.0005);
		this.mainControl = mainControl;
		this.setDoubleBuffered(true);
		init(frame);
	}

	public void killPanel() {
		this.setVisible(false);
		this.getGraphics().clearRect(0, 0, this.getWidth(), this.getHeight());
	}

	public AlphabetBoard getAlphabetBoard() {
		return this.alphabetboard;
	}

	public void displayRewardImage(MainFrame frame) {
		this.killPanel();
		for (int i = 0; i < wordLength; i++) {
			alphabetImage[i] = null;
			alphabetLabel[i] = null;
		}
		Board board = new Board(frame, mainControl, this);
		frame.setLayout(new BorderLayout());
		mainControl.getPictureController().managePicture();
		ImageIcon reward = new ImageIcon(mainControl.getPictureController()
				.getRewardPicture().getImageURL());
		JLabel rewardimage = new JLabel();

		rewardPanel = new JPanel();

		rewardPanel.setLayout(null);

		rewardPanel.setBackground(Color.WHITE);

		if (reward.getIconWidth() > frame.getWidth()
				|| reward.getIconHeight() > frame.getHeight()) {
			reward = new ImageIcon(reward.getImage().getScaledInstance(
					frame.getWidth(), frame.getHeight(), Image.SCALE_REPLICATE));
		}
		rewardimage.setBounds((frame.getWidth() - reward.getIconWidth()) / 2,
				((frame.getHeight() - reward.getIconHeight())) / 2,
				reward.getIconWidth(), reward.getIconHeight());
		rewardimage.setIcon(reward);
		rewardPanel.add(rewardimage);
		frame.add(rewardPanel);

		// 출력 다 끝난후, 종료
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					rewardPanel.setVisible(true);
					Thread.sleep(3000);
					rewardPanel.setVisible(false);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				frame.add(board);
				board.getThread().start();
			}
		});
	}

	public void init(JFrame frame) {
		wordLength = mainControl.getGameController().getWord().getLength();
		frame.setLayout(null);
		this.setLayout(null);
		this.setBackground(Color.WHITE);
		this.setOpaque(true);
		this.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		refresh(frame);
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public void refresh(JFrame frame) {
		this.removeAll();

		alphabetNow = 0;
		wordImage = new ImageIcon(mainControl.getGameController().getWord()
				.getImageURL());
		wordLabel = new JLabel();
		wordImage = new ImageIcon(wordImage.getImage()
				.getScaledInstance((int) (280 * ratio), (int) (280 * ratio),
						Image.SCALE_REPLICATE));
		wordLabel.setIcon(wordImage);
		wordLabel.setBounds(
				(int) ((frame.getWidth() - wordImage.getIconWidth()) / 2),
				(int) (frame.getHeight() * 0.05), wordImage.getIconWidth(),
				wordImage.getIconHeight());
		this.add(wordLabel);
		alphabetImage = new ImageIcon[10];
		alphabetLabel = new JLabel[10];
		for (int i = 0; i < wordLength; i++) {
			alphabetPosY[i] = (int) (this.getHeight() * 0.45);
			alphabetImage[i] = new ImageIcon();
			alphabetLabel[i] = new JLabel();
		}
		for (int i = 0; i < wordLength; i++) {
			if (i > mainControl.getGameController().getWordIndex() - 1) {
				alphabetImage[i] = new ImageIcon(mainControl.getGameController()
						.getWord().getAlphabetArr().elementAt(i).getImageURL()
						.replace("1.jpg", "2.jpg"));
			} else {
				alphabetImage[i] = new ImageIcon(mainControl.getGameController()
						.getWord().getAlphabetArr().elementAt(i).getImageURL());
			}
			alphabetImage[i] = new ImageIcon(alphabetImage[i].getImage()
					.getScaledInstance((int) (70 * ratio), (int) (70 * ratio),
							Image.SCALE_REPLICATE));
			alphabetPosX[i] = (this.getWidth() - (this.getWidth() / 19)
					* wordLength)
					/ 2 + alphabetImage[i].getIconWidth() * i - 10;
			alphabetLabel[i].setIcon(alphabetImage[i]);
			alphabetLabel[i].setBounds(alphabetPosX[i], alphabetPosY[i],
					alphabetImage[i].getIconWidth(),
					alphabetImage[i].getIconHeight());
			this.add(alphabetLabel[i]);
		}
		keyboardLabel = new JLabel();
		vKeyboardImage = new ImageIcon(mainControl.getGameController()
				.getvKeyboard().getImageURL());
		ratio2 = ((double) (frame.getHeight() * 0.4) / (double) vKeyboardImage
				.getIconHeight());
		keyboardWidth = ((int) ((double) vKeyboardImage.getIconWidth() * ratio2));
		keyboardHeight = ((int) ((double) vKeyboardImage
				.getIconHeight() * ratio2));
		vKeyboardImage = new ImageIcon(vKeyboardImage
				.getImage().getScaledInstance(keyboardWidth, keyboardHeight,
						Image.SCALE_REPLICATE));
		keyboardLabel.setBounds(
				(frame.getWidth() - vKeyboardImage.getIconWidth()) / 2,
				(int) (frame.getHeight() * 0.56),
				vKeyboardImage.getIconWidth(),
				vKeyboardImage.getIconHeight());
		keyboardLabel.setIcon(vKeyboardImage);
		this.add(keyboardLabel);
		this.setVisible(true);
	}

	public void refreshPress(MainFrame frame) {
		for (int i = 0; i < wordLength; i++) {
			if (i > mainControl.getGameController().getWordIndex() - 1) {
				alphabetImage[i] = new ImageIcon(mainControl.getGameController()
						.getWord().getAlphabetArr().elementAt(i).getImageURL()
						.replace("1.jpg", "2.jpg"));
			} else {
				alphabetImage[i] = new ImageIcon(mainControl.getGameController()
						.getWord().getAlphabetArr().elementAt(i).getImageURL());
			}
			alphabetImage[i] = new ImageIcon(alphabetImage[i].getImage()
					.getScaledInstance((int) (70 * ratio), (int) (70 * ratio),
							Image.SCALE_REPLICATE));
			alphabetPosX[i] = (this.getWidth() - (this.getWidth() / 19)
					* wordLength)
					/ 2 + alphabetImage[i].getIconWidth() * i - 10;
			alphabetLabel[i].setIcon(alphabetImage[i]);
			alphabetLabel[i].setBounds(alphabetPosX[i], alphabetPosY[i],
					alphabetImage[i].getIconWidth(),
					alphabetImage[i].getIconHeight());
		}

		vKeyboardImage = new ImageIcon(mainControl.getGameController()
				.getvKeyboard().getImageURL());
		vKeyboardImage = new ImageIcon(vKeyboardImage
				.getImage().getScaledInstance(keyboardWidth, keyboardHeight,
						Image.SCALE_REPLICATE));
		keyboardLabel.setBounds(
				(frame.getWidth() - vKeyboardImage.getIconWidth()) / 2,
				(int) (frame.getHeight() * 0.56),
				vKeyboardImage.getIconWidth(),
				vKeyboardImage.getIconHeight());
		keyboardLabel.setIcon(vKeyboardImage);
		alphabetNow++;
	}

	public void movingAlphabet(MainFrame frame) {
		alphabetboard = new AlphabetBoard(frame, mainControl, this);
		alphabetboard.init(
				(int) wordLabel.getLocation().getX(),
				(int) wordLabel.getWidth(),
				(int) alphabetLabel[mainControl.getGameController().getWordIndex() - 1]
						.getLocation().getX(), (int) alphabetLabel[mainControl
						.getGameController().getWordIndex() - 1].getLocation()
						.getY(), mainControl.getGameController().getWordIndex() - 1);
		alphabetboard.setVisible(true);
		alphabetboard.setOpaque(false);
		frame.add(alphabetboard);
		alphabetboard.getThread().start();
	}

}