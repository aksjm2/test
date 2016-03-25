package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FilenameFilter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.MainController;

public class MainFrame extends JFrame implements KeyListener {
	
	private MainController mainControl;
	private SoundPlayer sp;
	private JPanel mainPanel;
	private DictionaryPanel dictionaryPanel;
	private GamePanel gamePanel;
	private PicturePanel picturePanel;
	private JFileChooser fd;

	private JLabel gameStartLabel;
	private JLabel dictionaryLabel;
	private JLabel pictureLabel;
	private JLabel exitLabel;
	private ImageIcon gameStartImage;
	private ImageIcon dictionaryImage;
	private ImageIcon pictureImage;
	private ImageIcon exitImage;

	private Dimension d;

	double ratio;

	public MainFrame() {
		mainControl = new MainController();
		init();
	}

	public void init() {
		d = Toolkit.getDefaultToolkit().getScreenSize();
		ratio = ((double) (d.getWidth() + d.getHeight()) * 0.0005);
		sp = new SoundPlayer();
		this.setExtendedState(Frame.MAXIMIZED_BOTH); // 풀 스크린 출력
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // 닫힘 버튼 설정
		this.setSize(d);
		this.setResizable(false);
		this.setTitle("유아용 수집왕!!");
		this.setBackground(Color.WHITE);
		settingFrame(this);
		this.addKeyListener(this);
		this.setVisible(true);// 프레임 가시성 활성화

	}

	private void settingFrame(JFrame frame) {
		gameStartImage = new ImageIcon("image/button/1.jpg");
		gameStartImage = new ImageIcon(gameStartImage.getImage()
				.getScaledInstance(
						(int) ((double) (gameStartImage.getIconWidth())
								* ratio / 2),
						(int) ((double) (gameStartImage.getIconHeight())
								* ratio / 2), Image.SCALE_REPLICATE));
		dictionaryImage = new ImageIcon("image/button/2.jpg");
		dictionaryImage = new ImageIcon(
				dictionaryImage.getImage()
						.getScaledInstance(
								(int) ((double) dictionaryImage.getIconWidth()
										* ratio / 2),
								(int) ((double) dictionaryImage
										.getIconHeight() * ratio / 2),
								Image.SCALE_REPLICATE));
		pictureImage = new ImageIcon("image/button/3.jpg");
		pictureImage = new ImageIcon(
				pictureImage.getImage()
						.getScaledInstance(
								(int) ((double) pictureImage.getIconWidth()
										* ratio / 2),
								(int) ((double) pictureImage.getIconHeight()
										* ratio / 2), Image.SCALE_REPLICATE));
		exitImage = new ImageIcon("image/button/4.jpg");
		exitImage = new ImageIcon(exitImage.getImage().getScaledInstance(
				(int) ((double) exitImage.getIconWidth() * ratio / 2),
				(int) ((double) exitImage.getIconHeight() * ratio / 2),
				Image.SCALE_REPLICATE));

		JLabel gameStartLabel = new JLabel(gameStartImage);
		JLabel dictionaryLabel = new JLabel(dictionaryImage);
		JLabel pictureLabel = new JLabel(pictureImage);
		JLabel exitLabel = new JLabel(exitImage);
		mainPanel = new JPanel();
		frame.add(mainPanel);
		mainPanel.setLayout(new GridLayout(2, 2));
		mainPanel.setBackground(Color.WHITE);
		mainPanel.add(gameStartLabel);
		mainPanel.add(dictionaryLabel);
		mainPanel.add(pictureLabel);
		mainPanel.add(exitLabel);
		mainPanel.setVisible(true);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (mainControl.getState() == 0) {
			mainEvent(e);
		} else if (mainControl.getState() == 1) {
			gameEvent(e);
		} else if (mainControl.getState() == 3) {
			pictureEvent(e);
		}
	}

	private void pictureEvent(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyChar() == 27) {
			picturePanel.killPanel();
			mainControl.quit();
			mainPanel.setVisible(true);
		} else if (e.getKeyChar() == 'u') {
			mainControl.getPictureController().uploadPicture();
			fd = new JFileChooser();
			fd.setMultiSelectionEnabled(false);
			fd.setFileFilter(new FileNameExtensionFilter("JPG & PNG file",
					"jpg", "png"));
			fd.setAcceptAllFileFilterUsed(false);
			
			int val = fd.showOpenDialog(null);
			if (val == JFileChooser.APPROVE_OPTION) {
				String fdirectory = fd.getSelectedFile().getPath();
				if(fdirectory.endsWith(".jpg")||fdirectory.endsWith(".png")){
					mainControl.getPictureController().selectFileDirectory(fdirectory);
					picturePanel.refreshInformation(fdirectory);
				}
			}
		} else if (e.getKeyChar() == 'd') {
			if(picturePanel.checkLoadStatus()){
				JOptionPane.showMessageDialog(null, "has not Image!\n","이미지가 없습니다.",JOptionPane.ERROR_MESSAGE);	
			}
			mainControl.getPictureController().deletePicture();
			picturePanel.refreshInformation("image/reward/default.jpg");
		}
	}

	private synchronized void gameEvent(KeyEvent e) {
		if (e.getKeyChar() == 27) {
			gamePanel.killPanel();
			mainControl.quit();
			mainPanel.setVisible(true);
		} else {
			char c = e.getKeyChar();
			int wordIndex = mainControl.getGameController().getWordIndex();
			if (mainControl.getGameController().getWord().getName()
					.charAt(wordIndex) == c
					|| mainControl.getGameController().getWord().getName()
							.charAt(wordIndex) == (c + 32)) {
				mainControl.getGameController().pressAlphabet(c);
				if (mainControl
						.getGameController()
						.getWord()
						.getAlphabetArr()
						.elementAt(
								mainControl.getGameController().getWordIndex() - 1)
						.getCorrect()) {
					// gamePanel.알파벳맞춘함수
					this.removeKeyListener(this);
					// gamePanel.refresh_Press(this);
					gamePanel.movingAlphabet(this);
					sp.play(mainControl
							.getGameController()
							.getWord()
							.getAlphabetArr()
							.elementAt(
									mainControl.getGameController().getWordIndex() - 1)
							.getSoundURL());
					mainControl.getGameController()
							.getWord()
							.getAlphabetArr()
							.elementAt(
									mainControl.getGameController().getWordIndex() - 1)
							.setCorrect(false);
					if (mainControl.getGameController().getWord().getCorrect()) {
						// gamePanel.재시작함수
						try {
							this.removeKeyListener(this);
							gamePanel.getAlphabetBoard().getThread().join();
							sp.play(mainControl.getGameController().getWord()
									.getSoundURL());
							gamePanel.displayRewardImage(this);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		}
	}

	private void mainEvent(KeyEvent e) {
		System.out.println(e.getKeyChar());
		if (e.getKeyChar() == '1') {
			mainControl.getGameController().gameStart();
			mainPanel.setVisible(false);
			gamePanel = new GamePanel(this, mainControl);
		} else if (e.getKeyChar() == '2') {
			mainControl.getDictionaryController().dictionaryOpen();
			mainPanel.setVisible(false);
			dictionaryPanel = new DictionaryPanel(this);
			dictionaryPanel.viewDictionary(mainControl, mainPanel, this);
		} else if (e.getKeyChar() == '3') {
			mainControl.getPictureController().managePicture();
			mainPanel.setVisible(false);
			picturePanel = new PicturePanel(this, mainControl.getPictureController()
					.getRewardPicture().getImageURL());
		} else if (e.getKeyChar() == '4') {
			mainControl.exit();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}