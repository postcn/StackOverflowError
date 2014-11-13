import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
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
	private static JFXPanel postFXPanel, 
		scoreMaxFXPanel, scoreMinFXPanel, scoreAvgFXPanel, 
		answerMaxFXPanel, answerMinFXPanel, answerAvgFXPanel,
		favoriteMaxFXPanel, favoriteMinFXPanel, favoriteAvgFXPanel,
		commentCountFXPanel, commentMaxScoreFXPanel, commentMinScoreFXPanel, commentAvgScoreFXPanel;
	
	public StatisticsPanel()
	{
		tags = new LinkedList< Tag >();
		this.setBackground(Color.WHITE);
		backend = Backend.getInstance();
		
		postFXPanel = new JFXPanel();

		scoreMaxFXPanel = new JFXPanel();
		scoreMaxFXPanel.setPreferredSize(new Dimension(550,375));
		scoreMinFXPanel = new JFXPanel();
		scoreMinFXPanel.setPreferredSize(new Dimension(550,375));
		scoreAvgFXPanel = new JFXPanel();
		scoreAvgFXPanel.setPreferredSize(new Dimension(550,375));

		answerMaxFXPanel = new JFXPanel();
		answerMaxFXPanel.setPreferredSize(new Dimension(550,375));
		answerMinFXPanel = new JFXPanel();
		answerMinFXPanel.setPreferredSize(new Dimension(550,375));
		answerAvgFXPanel = new JFXPanel();
		answerAvgFXPanel.setPreferredSize(new Dimension(550,375));

		favoriteMaxFXPanel = new JFXPanel();
		favoriteMaxFXPanel.setPreferredSize(new Dimension(550,375));
		favoriteMinFXPanel = new JFXPanel();
		favoriteMinFXPanel.setPreferredSize(new Dimension(550,375));
		favoriteAvgFXPanel = new JFXPanel();
		favoriteAvgFXPanel.setPreferredSize(new Dimension(550,375));
		
		commentCountFXPanel = new JFXPanel();
		commentCountFXPanel.setPreferredSize(new Dimension(550,375));
		commentMaxScoreFXPanel = new JFXPanel();
		commentMaxScoreFXPanel.setPreferredSize(new Dimension(550,375));
		commentMinScoreFXPanel = new JFXPanel();
		commentMinScoreFXPanel.setPreferredSize(new Dimension(550,375));
		commentAvgScoreFXPanel = new JFXPanel();
		commentAvgScoreFXPanel.setPreferredSize(new Dimension(550,375));
		
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
		tabs.setPreferredSize(new Dimension(1135,825));
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

		scorePanel.add(scoreMaxFXPanel);
		scorePanel.add(scoreMinFXPanel);
		scorePanel.add(scoreAvgFXPanel);
		return scorePanel;
	}
	
	private static Scene createScoreMaxScene()
	{
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		BarChart< String, Number > bc = new BarChart< String, Number >(xAxis, yAxis);
		bc.setTitle("Max Score");
		bc.setLegendVisible(false);
		
		XYChart.Series series1 = new XYChart.Series();
		for (Tag t : tags)
			series1.getData().add(new XYChart.Data< String, Integer >(t.getTag(), t.getMaxScore()));

    Scene scene  = new Scene(bc,550,200);
    bc.getData().addAll(series1);
    
    return scene;
	}
	
	private static Scene createScoreMinScene()
	{
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		BarChart< String, Number > bc = new BarChart< String, Number >(xAxis, yAxis);
		bc.setTitle("Min Score");
		bc.setLegendVisible(false);
		
		XYChart.Series series1 = new XYChart.Series();
		for (Tag t : tags)
			series1.getData().add(new XYChart.Data< String, Integer >(t.getTag(), t.getMinScore()));

    Scene scene  = new Scene(bc,550,200);
    bc.getData().addAll(series1);
    
    return scene;
	}
	
	private static Scene createScoreAvgScene()
	{
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		BarChart< String, Number > bc = new BarChart< String, Number >(xAxis, yAxis);
		bc.setTitle("Average Score");
		bc.setLegendVisible(false);
		
		XYChart.Series series1 = new XYChart.Series();
		for (Tag t : tags)
			series1.getData().add(new XYChart.Data< String, Integer >(t.getTag(), t.getAvgScore()));

    Scene scene  = new Scene(bc,550,200);
    bc.getData().addAll(series1);
    
    return scene;
	}
	
	private JPanel createAnswerTab()
	{
		JPanel answerPanel = new JPanel();
		answerPanel.setName("Answers");
		answerPanel.setBackground(Color.WHITE);
		
		answerPanel.add(answerMaxFXPanel);
		answerPanel.add(answerMinFXPanel);
		answerPanel.add(answerAvgFXPanel);
		return answerPanel;
	}
	
	private static Scene createAnswerMaxScene()
	{
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		BarChart< String, Number > bc = new BarChart< String, Number >(xAxis, yAxis);
		bc.setTitle("Max Answer Count");
		bc.setLegendVisible(false);
		
		XYChart.Series series1 = new XYChart.Series();
		for (Tag t : tags)
			series1.getData().add(new XYChart.Data< String, Integer >(t.getTag(), t.getMaxAnswerCount()));

    Scene scene  = new Scene(bc,550,200);
    bc.getData().addAll(series1);
    
    return scene;
	}
	
	private static Scene createAnswerMinScene()
	{
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		BarChart< String, Number > bc = new BarChart< String, Number >(xAxis, yAxis);
		bc.setTitle("Min Answer Count");
		bc.setLegendVisible(false);
		
		XYChart.Series series1 = new XYChart.Series();
		for (Tag t : tags)
			series1.getData().add(new XYChart.Data< String, Integer >(t.getTag(), t.getMinAnswerCount()));

    Scene scene  = new Scene(bc,550,200);
    bc.getData().addAll(series1);
    
    return scene;
	}
	
	private static Scene createAnswerAvgScene()
	{
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		BarChart< String, Number > bc = new BarChart< String, Number >(xAxis, yAxis);
		bc.setTitle("Average Answer Count");
		bc.setLegendVisible(false);
		
		XYChart.Series series1 = new XYChart.Series();
		for (Tag t : tags)
			series1.getData().add(new XYChart.Data< String, Integer >(t.getTag(), t.getAvgAnswerCount()));

    Scene scene  = new Scene(bc,550,200);
    bc.getData().addAll(series1);
    
    return scene;
	}
	
	
	private JPanel createFavoriteTab()
	{
		JPanel favoritePanel = new JPanel();
		favoritePanel.setName("Favorites");
		favoritePanel.setBackground(Color.WHITE);
		
		favoritePanel.add(favoriteMaxFXPanel);
		favoritePanel.add(favoriteMinFXPanel);
		favoritePanel.add(favoriteAvgFXPanel);
		return favoritePanel;
	}
	
	private static Scene createFavoriteMaxScene()
	{
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		BarChart< String, Number > bc = new BarChart< String, Number >(xAxis, yAxis);
		bc.setTitle("Max Favorite Count");
		bc.setLegendVisible(false);
		
		XYChart.Series series1 = new XYChart.Series();
		for (Tag t : tags)
			series1.getData().add(new XYChart.Data< String, Integer >(t.getTag(), t.getMaxFavoriteCount()));

    Scene scene  = new Scene(bc,550,200);
    bc.getData().addAll(series1);
    
    return scene;
	}
	
	private static Scene createFavoriteMinScene()
	{
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		BarChart< String, Number > bc = new BarChart< String, Number >(xAxis, yAxis);
		bc.setTitle("Min Favorite Count");
		bc.setLegendVisible(false);
		
		XYChart.Series series1 = new XYChart.Series();
		for (Tag t : tags)
			series1.getData().add(new XYChart.Data< String, Integer >(t.getTag(), t.getMinFavoriteCount()));

    Scene scene  = new Scene(bc,550,200);
    bc.getData().addAll(series1);
    
    return scene;
	}
	
	private static Scene createFavoriteAvgScene()
	{
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		BarChart< String, Number > bc = new BarChart< String, Number >(xAxis, yAxis);
		bc.setTitle("Average Favorite Count");
		bc.setLegendVisible(false);
		
		XYChart.Series series1 = new XYChart.Series();
		for (Tag t : tags)
			series1.getData().add(new XYChart.Data< String, Integer >(t.getTag(), t.getAvgFavoriteCount()));

    Scene scene  = new Scene(bc,550,200);
    bc.getData().addAll(series1);
    
    return scene;
	}
	
	private JPanel createCommentTab()
	{
		JPanel commentPanel = new JPanel();
		commentPanel.setName("Comments");
		commentPanel.setBackground(Color.WHITE);
		
		commentPanel.add(commentCountFXPanel);
		commentPanel.add(commentMaxScoreFXPanel);
		commentPanel.add(commentMinScoreFXPanel);
		commentPanel.add(commentAvgScoreFXPanel);
		return commentPanel;
	}
	
	private static Scene createCommentCountScene()
	{
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		BarChart< String, Number > bc = new BarChart< String, Number >(xAxis, yAxis);
		bc.setTitle("Total Comment Count");
		bc.setLegendVisible(false);
		
		XYChart.Series series1 = new XYChart.Series();		
		for (Tag t : tags)
			series1.getData().add(new XYChart.Data< String, Integer >(t.getTag(), t.getCommentCount()));

    Scene scene  = new Scene(bc,550,200);
    bc.getData().addAll(series1);
    
    return scene;
	}
	
	private static Scene createCommentMaxScoreScene()
	{
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		BarChart< String, Number > bc = new BarChart< String, Number >(xAxis, yAxis);
		bc.setTitle("Max Comment Score");
		bc.setLegendVisible(false);
		
		XYChart.Series series1 = new XYChart.Series();

		for (Tag t : tags)
			series1.getData().add(new XYChart.Data< String, Integer >(t.getTag(), t.getMaxCommentScore()));

    Scene scene  = new Scene(bc,550,200);
    bc.getData().addAll(series1);
    
    return scene;
	}
	
	private static Scene createCommentMinScoreScene()
	{
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		BarChart< String, Number > bc = new BarChart< String, Number >(xAxis, yAxis);
		bc.setTitle("Min Comment Score");
		bc.setLegendVisible(false);
		
		XYChart.Series series1 = new XYChart.Series();

		for (Tag t : tags)
			series1.getData().add(new XYChart.Data< String, Integer >(t.getTag(), t.getMinCommentScore()));

    Scene scene  = new Scene(bc,550,200);
    bc.getData().addAll(series1);
    
    return scene;
	}
	
	private static Scene createCommentAvgScoreScene()
	{
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		BarChart< String, Number > bc = new BarChart< String, Number >(xAxis, yAxis);
		bc.setTitle("Average Comment Score");
		bc.setLegendVisible(false);
		
		XYChart.Series series1 = new XYChart.Series();

		for (Tag t : tags)
			series1.getData().add(new XYChart.Data< String, Integer >(t.getTag(), t.getAvgCommentScore()));

    Scene scene  = new Scene(bc,550,200);
    bc.getData().addAll(series1);
    
    return scene;
	}
	
	private static void initFX() 
	{
    // This method is invoked on the JavaFX thread
		postFXPanel.setScene(createPostScene());
		
		scoreMaxFXPanel.setScene(createScoreMaxScene());
		scoreMinFXPanel.setScene(createScoreMinScene());
		scoreAvgFXPanel.setScene(createScoreAvgScene());
		
		answerMaxFXPanel.setScene(createAnswerMaxScene());
		answerMinFXPanel.setScene(createAnswerMinScene());
		answerAvgFXPanel.setScene(createAnswerAvgScene());
		
		favoriteMaxFXPanel.setScene(createFavoriteMaxScene());
		favoriteMinFXPanel.setScene(createFavoriteMinScene());
		favoriteAvgFXPanel.setScene(createFavoriteAvgScene());
		
		commentCountFXPanel.setScene(createCommentCountScene());
		commentMaxScoreFXPanel.setScene(createCommentMaxScoreScene());
		commentMinScoreFXPanel.setScene(createCommentMinScoreScene());
		commentAvgScoreFXPanel.setScene(createCommentAvgScoreScene());
	}
}
