import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class GUITest
{

	public static void main (String[] args)
	{
		JFrame loadingFrame = new JFrame();
		loadingFrame.setSize(350, 200);
		loadingFrame.setLocationRelativeTo(null);
		loadingFrame.setTitle("Stack Overflow");
		ImageIcon img = new ImageIcon("images/StackOverflowIcon.png");
		loadingFrame.setIconImage(img.getImage());
		
		JPanel loadingPanel = new JPanel();
		loadingPanel.setBackground(Color.WHITE);
		loadingPanel.setLayout(new BorderLayout());
		JLabel loadingIcon = new JLabel(new ImageIcon("images/StackOverflowFullIcon.png"));
		loadingPanel.add(loadingIcon, BorderLayout.CENTER);
		
		JLabel loadingText = new JLabel("Loading...");
		loadingText.setFont(new Font("Courier", Font.BOLD,20));
		loadingText.setHorizontalAlignment(SwingConstants.CENTER);
		loadingText.setPreferredSize(new Dimension(300,75));
		loadingPanel.add(loadingText, BorderLayout.SOUTH);
		
		loadingFrame.add(loadingPanel);
		loadingFrame.setVisible(true);
		Backend b = Backend.getInstance();
		loadingFrame.setVisible(false);
		StackOverflowFrame frame = new StackOverflowFrame(b);
		frame.setVisible(true);
	}

}
