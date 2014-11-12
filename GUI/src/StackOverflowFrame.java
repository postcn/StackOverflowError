import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


@SuppressWarnings("serial")
public class StackOverflowFrame extends JFrame
{
	private JPanel TagSearchPanel, TagListPanel, TagDisplayPanel;
	private Backend backEnd;
	private JTextField tagFilter;
	private List<String> suggestions, selected;
	private final Color ORANGE = new Color(234,140,0);
	private final Color BUTTONCOLOR = new Color(224,234,241);
	private final Color TAGCOLOR = new Color(48,98,139);
	
	public StackOverflowFrame(Backend b)
	{
		backEnd = b;
		ImageIcon img = new ImageIcon("images/StackOverflowIcon.png");
		this.setIconImage(img.getImage());
		this.setSize(new Dimension(1200,1000));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Stack Overflow");
		selected = new LinkedList<String>();
		setLocationRelativeTo(null);
		
		this.add(createLayout());
	}
	
	private JPanel createLayout(){
		JPanel layout = new JPanel();
		layout.setBackground(Color.WHITE);
		layout.setLayout(new BorderLayout());

		JLabel titleLabel = new JLabel("Stack Overflow Statistics");
		titleLabel.setFont(new Font("Courier", Font.BOLD,36));
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setPreferredSize(new Dimension(200,75));
		titleLabel.setBackground(Color.GRAY);
		titleLabel.setOpaque(true);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel innerPanel = new JPanel();
		innerPanel.setLayout(new BorderLayout());
		innerPanel.setBackground(Color.WHITE);
		TagDisplayPanel = new JPanel();
		TagDisplayPanel.setBackground(Color.WHITE);
		TagDisplayPanel.setPreferredSize(new Dimension(200,100));
		TagDisplayPanel.setLayout(new FlowLayout());
		innerPanel.add(TagDisplayPanel, BorderLayout.NORTH);
		
		layout.add(innerPanel, BorderLayout.CENTER);
		layout.add(titleLabel, BorderLayout.NORTH);
		layout.add(createTagSearchPanel(), BorderLayout.WEST);
		
		return layout;
	}
	
	private JPanel createTagSearchPanel()
	{
		TagSearchPanel = new JPanel();
		TagSearchPanel.setBackground(ORANGE);
		TagSearchPanel.setLayout(new BorderLayout());
		TagSearchPanel.setPreferredSize(new Dimension(265,900));
		
		JPanel topPanel = new JPanel();
		topPanel.setBackground(ORANGE);
		GridLayout grid = new GridLayout(0,1);
		grid.setVgap(10);
		topPanel.setLayout(grid);
		topPanel.setPreferredSize(new Dimension(265,100));
		
		JLabel addLabel = new JLabel("Search for a Tag");
		addLabel.setFont(new Font("Courier", Font.BOLD,18));
		addLabel.setHorizontalAlignment(SwingConstants.CENTER);
		topPanel.add(addLabel);

		tagFilter = new JTextField();
		topPanel.add(tagFilter);

		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(getSearchListener());
		topPanel.add(searchButton);
//		JPanel searchPanel = new JPanel();
//		searchPanel.setBackground(MYORANGE);
//		searchPanel.setPreferredSize(new Dimension(200, 50));
//		searchPanel.setLayout(new BorderLayout());
//		searchButton.setPreferredSize(new Dimension(100, 25));
//		searchPanel.add(searchButton, BorderLayout.CENTER);
//		topPanel.add(searchPanel);
		 
		TagListPanel = new JPanel();
		TagListPanel.setBackground(ORANGE);
		TagListPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		TagListPanel.setPreferredSize(new Dimension(230,3000));
		TagSearchPanel.add(topPanel, BorderLayout.NORTH);
		TagSearchPanel.add(TagListPanel, BorderLayout.CENTER);
		
		return TagSearchPanel;
	}
	
	private void searchTags()
	{
		TagSearchPanel.remove(1);
		TagListPanel.removeAll();
		suggestions = backEnd.getSuggestions(tagFilter.getText());
		for (String s : suggestions)
		{
			JButton tag = new JButton(s);
			tag.setBackground(BUTTONCOLOR);
			tag.setForeground(TAGCOLOR);
			tag.addActionListener(getTagAddListener(s));
			TagListPanel.add(tag);
		}
		JScrollPane scrollPane = new JScrollPane(TagListPanel);
		scrollPane.setPreferredSize(new Dimension(265,3000));
		TagSearchPanel.add(scrollPane, BorderLayout.CENTER);
		
		revalidate();
		repaint();
	}
	
	private void refreshTags()
	{
		TagSearchPanel.remove(1);
		TagListPanel.removeAll();
		for (String s : suggestions)
		{
			if(!selected.contains(s))
			{
				JButton tag = new JButton(s);
				tag.setBackground(BUTTONCOLOR);
				tag.setForeground(TAGCOLOR);
				tag.addActionListener(getTagAddListener(s));
				TagListPanel.add(tag);
			}
		}
		JScrollPane scrollPane = new JScrollPane(TagListPanel);
		scrollPane.setPreferredSize(new Dimension(265,3000));
		TagSearchPanel.add(scrollPane, BorderLayout.CENTER);

		TagDisplayPanel.removeAll();
		for(String s : selected)
		{
			JButton tag = new JButton(s);
			tag.setBackground(BUTTONCOLOR);
			tag.setForeground(TAGCOLOR);
			tag.addActionListener(getTagRemoveListener(s));
			TagDisplayPanel.add(tag);
		}
		
		revalidate();
		repaint();
	}
	
	//Button Listeners
	private ActionListener getSearchListener()
	{
		return new ActionListener()
		{
			@Override
			public void actionPerformed (ActionEvent e)
			{
				searchTags();
			}
		};
	}
	
	private ActionListener getTagAddListener(final String s)
	{
		return new ActionListener()
			{
				@Override
				public void actionPerformed (ActionEvent e)
				{
					selected.add(s);
					refreshTags();
				}
			};
	}
	
	private ActionListener getTagRemoveListener(final String s)
	{
		return new ActionListener()
			{
				@Override
				public void actionPerformed (ActionEvent e)
				{
					selected.remove(s);
					refreshTags();
				}
			};
	}
}