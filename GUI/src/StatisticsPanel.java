import java.awt.Color;
import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;


@SuppressWarnings("serial")
public class StatisticsPanel extends JPanel
{
	private static List<Tag> tags;
	private JTabbedPane tabs;
	private Backend backend;
	private static JFXPanel postFXPanel, scoreFXPanel;
	
	public StatisticsPanel()
	{
		tags = new LinkedList< Tag >();
		this.setBackground(Color.WHITE);
		backend = Backend.getInstance();
		
		postFXPanel = new JFXPanel();
		scoreFXPanel = new JFXPanel();
		
		this.add(createTabbedPane());
		
		Platform.runLater(new Runnable() {
		      @Override
		      public void run() {
		          initFX();
		      }
		 });
	}
	
	public void updateTags(List< String > tagNames)
	{
		if(tagNames.size() > 0)
			tags = backend.getTags(tagNames);
		else
			tags = new LinkedList< Tag >();
		updateTabs();
		
		Platform.runLater(new Runnable() {
		      @Override
		      public void run() {
		          initFX();
		      }
		 });
	}
	
	private JTabbedPane createTabbedPane()
	{
		tabs = new JTabbedPane();
		tabs.setPreferredSize(new Dimension(935,825));
		tabs.add(createPostTab());
		tabs.add(createScoreTab());
		tabs.add(createAnswerTab());
		tabs.add(createFavoriteTab());
		tabs.add(createCommentTab());
		return tabs;
	}
	
	private void updateTabs()
	{
		tabs.removeAll();
		tabs.add(createPostTab());
		tabs.add(createScoreTab());
		tabs.add(createAnswerTab());
		tabs.add(createFavoriteTab());
		tabs.add(createCommentTab());
	}
	
	private JPanel createPostTab()
	{
		JPanel postPanel = new JPanel();
		postPanel.setName("Posts");
		postPanel.setBackground(Color.WHITE);

    postPanel.add(postFXPanel);
		return postPanel;
	}
	
	private static Scene createPostScene()
	{
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		BarChart< String, Number > bc = new BarChart< String, Number >(xAxis, yAxis);
		bc.setTitle("Post Counts");
		bc.setLegendVisible(false);
		
		XYChart.Series series1 = new XYChart.Series();

		for (Tag t : tags)
			series1.getData().add(new XYChart.Data< String, Integer >(t.getTag(), t.getPostCount()));

    Scene scene  = new Scene(bc,920,750);
    bc.getData().addAll(series1);
    
    return scene;
	}
	
	private JPanel createScoreTab()
	{
		JPanel scorePanel = new JPanel();
		scorePanel.setName("Scores");
		scorePanel.setBackground(Color.WHITE);

		scorePanel.add(scoreFXPanel);
		return scorePanel;
	}
	
	private static Scene createScoreScene()
	{
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		BarChart< String, Number > bc = new BarChart< String, Number >(xAxis, yAxis);
		bc.setTitle("Score Statistics");
		bc.setLegendSide(Side.LEFT);
		
		XYChart.Series series1 = new XYChart.Series();
		series1.setName("Max Score");
		XYChart.Series series2 = new XYChart.Series();
		series2.setName("Min Score");
		XYChart.Series series3 = new XYChart.Series();
		series3.setName("Avg Score");

		for (Tag t : tags)
		{
			series1.getData().add(new XYChart.Data< String, Integer >(t.getTag(), t.getMaxScore()));
			series2.getData().add(new XYChart.Data< String, Integer >(t.getTag(), t.getMinScore()));
			series3.getData().add(new XYChart.Data< String, Integer >(t.getTag(), t.getAvgScore()));
		}

    Scene scene  = new Scene(bc,920,750);
    bc.getData().addAll(series1, series2, series3);
    
    return scene;
	}
	
	private JPanel createAnswerTab()
	{
		JPanel answerPanel = new JPanel();
		answerPanel.setName("Answers");
		answerPanel.setBackground(Color.WHITE);
		return answerPanel;
	}
	
	private JPanel createFavoriteTab()
	{
		JPanel favoritePanel = new JPanel();
		favoritePanel.setName("Favorites");
		favoritePanel.setBackground(Color.WHITE);
		return favoritePanel;
	}
	
	private JPanel createCommentTab()
	{
		JPanel commentPanel = new JPanel();
		commentPanel.setName("Comments");
		commentPanel.setBackground(Color.WHITE);
		return commentPanel;
	}
	
	private static void initFX() 
	{
    // This method is invoked on the JavaFX thread
		postFXPanel.setScene(createPostScene());
		scoreFXPanel.setScene(createScoreScene());
	}
}
