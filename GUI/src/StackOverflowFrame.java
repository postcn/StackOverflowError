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
	private JPanel tagSearchPanel, tagListPanel, tagDisplayPanel;
	private StatisticsPanel statisticsPanel;
	private Backend backEnd;
	private JTextField tagFilter;
	private List<String> suggestions, selected;
	private final Color ORANGE = new Color(234,140,0);
	private final Color BUTTONCOLOR = new Color(224,234,241);
	private final Color TAGCOLOR = new Color(48,98,139);
	
	public StackOverflowFrame()
	{
		backEnd = Backend.getInstance();
		ImageIcon img = new ImageIcon("images/StackOverflowIcon.png");
		this.setIconImage(img.getImage());
		this.setSize(new Dimension(1200,1000));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Stack Overflow");
		this.setResizable(false);
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
		tagDisplayPanel = new JPanel();
		tagDisplayPanel.setBackground(Color.WHITE);
		tagDisplayPanel.setPreferredSize(new Dimension(200,100));
		tagDisplayPanel.setLayout(new FlowLayout());
		innerPanel.add(tagDisplayPanel, BorderLayout.NORTH);
		
		statisticsPanel = new StatisticsPanel();
		innerPanel.add(statisticsPanel, BorderLayout.CENTER);
		
		layout.add(innerPanel, BorderLayout.CENTER);
		layout.add(titleLabel, BorderLayout.NORTH);
		layout.add(createTagSearchPanel(), BorderLayout.WEST);
		
		return layout;
	}
	
	private JPanel createTagSearchPanel()
	{
		tagSearchPanel = new JPanel();
		tagSearchPanel.setBackground(ORANGE);
		tagSearchPanel.setLayout(new BorderLayout());
		tagSearchPanel.setPreferredSize(new Dimension(265,900));
		
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
		 
		tagListPanel = new JPanel();
		tagListPanel.setBackground(ORANGE);
		tagListPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		tagListPanel.setPreferredSize(new Dimension(230,10000));
		tagSearchPanel.add(topPanel, BorderLayout.NORTH);
		tagSearchPanel.add(tagListPanel, BorderLayout.CENTER);
		
		return tagSearchPanel;
	}
	
	private void searchTags()
	{
		tagSearchPanel.remove(1);
		tagListPanel.removeAll();
		suggestions = backEnd.getSuggestions(tagFilter.getText());
		for (String s : suggestions)
		{
			JButton tag = new JButton(s);
			tag.setBackground(BUTTONCOLOR);
			tag.setForeground(TAGCOLOR);
			tag.addActionListener(getTagAddListener(s));
			tagListPanel.add(tag);
		}
		JScrollPane scrollPane = new JScrollPane(tagListPanel);
		scrollPane.setPreferredSize(new Dimension(265,3000));
		tagSearchPanel.add(scrollPane, BorderLayout.CENTER);
		
		revalidate();
		repaint();
	}
	
	private void refreshTags()
	{
		tagSearchPanel.remove(1);
		tagListPanel.removeAll();
		for (String s : suggestions)
		{
			if(!selected.contains(s))
			{
				JButton tag = new JButton(s);
				tag.setBackground(BUTTONCOLOR);
				tag.setForeground(TAGCOLOR);
				tag.addActionListener(getTagAddListener(s));
				tagListPanel.add(tag);
			}
		}
		JScrollPane scrollPane = new JScrollPane(tagListPanel);
		scrollPane.setPreferredSize(new Dimension(265,3000));
		tagSearchPanel.add(scrollPane, BorderLayout.CENTER);

		tagDisplayPanel.removeAll();
		for(String s : selected)
		{
			JButton tag = new JButton(s);
			tag.setBackground(BUTTONCOLOR);
			tag.setForeground(TAGCOLOR);
			tag.addActionListener(getTagRemoveListener(s));
			tagDisplayPanel.add(tag);
		}
		statisticsPanel.updateTags(selected);
		
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