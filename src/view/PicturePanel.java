package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.awt.*;

public class PicturePanel {
	private JPanel picturePanel;
	private JPanel informationPanel;
	private JPanel rewardImagePanel;
	private JPanel textPanel;
	private JPanel buttonPanel;

	private ImageIcon rewardImage;
	private ImageIcon uploadImage;
	private ImageIcon deleteImage;

	private JTextField urlTextField;

	private JLabel uploadLabel;
	private JLabel deleteLabel;
	private JLabel rewardImageLabel;

	Dimension d;
	double ratio;

	public PicturePanel() {

	}

	public PicturePanel(JFrame frame, String url) {
		d = Toolkit.getDefaultToolkit().getScreenSize();
		ratio = ((double) (d.getWidth() + d.getHeight()) * 0.0005);
		String temp = url;
		picturePanel = new JPanel();
		informationPanel = new JPanel();
		rewardImagePanel = new JPanel();
		textPanel = new JPanel();
		buttonPanel = new JPanel();

		rewardImage = new ImageIcon(url);
		uploadImage = new ImageIcon("image/button/upload_Picture.jpg");
		uploadImage = new ImageIcon(uploadImage.getImage().getScaledInstance(
				(int) (uploadImage.getIconWidth() * ratio / 2),
				(int) (uploadImage.getIconHeight() * ratio / 2),
				Image.SCALE_REPLICATE));
		deleteImage = new ImageIcon("image/button/delete_Picture.jpg");
		deleteImage = new ImageIcon(deleteImage.getImage().getScaledInstance(
				(int) (deleteImage.getIconWidth() * ratio / 2),
				(int) (deleteImage.getIconHeight() * ratio / 2),
				Image.SCALE_REPLICATE));

		urlTextField = new JTextField(30);

		uploadLabel = new JLabel();
		deleteLabel = new JLabel();
		rewardImageLabel = new JLabel();
		// /Set Layout///////////////////////////////////
		frame.setLayout(new BorderLayout());

		picturePanel.setLayout(null);
		rewardImagePanel.setLayout(null);
		informationPanel.setLayout(null);
		buttonPanel.setLayout(new GridLayout(1, 2));
		// //////////////////////////////////////////////

		// /Set
		// Bounds//////////////////////////////////////////////////////////////////////////////////////////////////////
		picturePanel.setSize(frame.getWidth() - 50, frame.getHeight() - 50);

		informationPanel.setBounds(10, 10, picturePanel.getWidth(),
				(int) (picturePanel.getHeight() * 0.8));
		buttonPanel
				.setBounds(10, informationPanel.getHeight() + 10,
						picturePanel.getWidth(),
						(int) (picturePanel.getHeight() * 0.2));

		rewardImagePanel.setBounds(30, 10,
				(int) (informationPanel.getWidth() * 0.97),
				(int) (informationPanel.getHeight() * 0.9));
		rewardImagePanel.setBorder(new EtchedBorder());
		textPanel.setBounds(frame.getWidth() / 5,
				rewardImagePanel.getHeight() + 10,
				(int) (informationPanel.getWidth() * 0.6),
				(int) (informationPanel.getHeight() * 0.1));
		if (rewardImage.getIconWidth() > rewardImagePanel.getWidth()
				|| rewardImage.getIconHeight() > rewardImagePanel.getHeight()) {
			rewardImage = new ImageIcon(rewardImage.getImage()
					.getScaledInstance(
							(int) (rewardImagePanel.getWidth() * 0.95),
							(int) (rewardImagePanel.getHeight() * 0.95),
							Image.SCALE_REPLICATE));
		}
		rewardImageLabel.setIcon(rewardImage);
		rewardImageLabel
				.setBounds((rewardImagePanel.getWidth() - rewardImage
						.getIconWidth()) / 2,
						((rewardImagePanel.getHeight() - rewardImage
								.getIconHeight())) / 2, rewardImage
								.getIconWidth(), rewardImage.getIconHeight());

		urlTextField.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 25));
		urlTextField.setForeground(Color.BLACK);
		urlTextField.setBackground(Color.WHITE);
		urlTextField.setText(url);

		uploadLabel.setSize(uploadImage.getIconWidth(),
				uploadImage.getIconWidth());
		uploadLabel.setIcon(uploadImage);
		deleteLabel.setIcon(deleteImage);
		uploadLabel.setBackground(Color.BLACK);
		deleteLabel.setBackground(Color.ORANGE);
		uploadLabel.setHorizontalAlignment(0);
		deleteLabel.setHorizontalAlignment(0);

		// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		picturePanel.setBackground(Color.WHITE);
		informationPanel.setBackground(Color.WHITE);
		rewardImagePanel.setBackground(Color.WHITE);
		textPanel.setBackground(Color.WHITE);
		buttonPanel.setBackground(Color.WHITE);
		// /Add///////////////////////////////////////////////////////////////////

		frame.add(picturePanel);

		picturePanel.add(informationPanel);
		picturePanel.add(buttonPanel);

		informationPanel.add(rewardImagePanel);
		informationPanel.add(textPanel);

		rewardImagePanel.add(rewardImageLabel);
		textPanel.add(urlTextField);

		buttonPanel.add(uploadLabel);
		buttonPanel.add(deleteLabel);
		// ///////////////////////////////////////////////////////////////////////

		urlTextField.setEnabled(false);
		picturePanel.setVisible(true);
	}

	public void killPanel() {
		picturePanel.setVisible(false);
	}

	public void refreshInformation(String url) {
		rewardImage = new ImageIcon(url);
		if (rewardImage.getIconWidth() > rewardImagePanel.getWidth()
				|| rewardImage.getIconHeight() > rewardImagePanel.getHeight()) {
			rewardImage = new ImageIcon(rewardImage.getImage()
					.getScaledInstance(
							(int) (rewardImagePanel.getWidth() * 0.95),
							(int) (rewardImagePanel.getHeight() * 0.95),
							Image.SCALE_REPLICATE));
		}
		rewardImageLabel
				.setBounds((rewardImagePanel.getWidth() - rewardImage
						.getIconWidth()) / 2,
						((rewardImagePanel.getHeight() - rewardImage
								.getIconHeight())) / 2, rewardImage
								.getIconWidth(), rewardImage.getIconHeight());
		urlTextField.setText(url);
		rewardImageLabel.setIcon(rewardImage);
	}

	public boolean checkLoadStatus() {
		if (rewardImage.getImageLoadStatus() == java.awt.MediaTracker.ERRORED)
			return true;
		else
			return false;
	}
}
