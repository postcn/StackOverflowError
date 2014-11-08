public class Tag {
	private String tag;
	private int postCount;
	private int maxScore;
	private int minScore;
	private int avgScore;
	private int maxAnswerCount;
	private int minAnswerCount;
	private int avgAnswerCount;
	private int maxFavoriteCount;
	private int minFavoriteCount;
	private int avgFavoriteCount;
	private int commentCount;
	private int maxCommentScore;
	private int minCommentScore;
	private int avgCommentScore;

	public Tag(String tag, int postCount, int maxScore, int minScore,
			int avgScore, int maxAnswerCount, int minAnswerCount,
			int avgAnswerCount, int maxFavoriteCount, int minFavoriteCount,
			int avgFavoriteCount, int commentCount, int maxCommentScore,
			int minCommentScore, int avgCommentScore) {
		this.tag = tag;
		this.postCount = postCount;
		this.maxScore = maxScore;
		this.minScore = minScore;
		this.avgScore = avgScore;
		this.maxAnswerCount = maxAnswerCount;
		this.minAnswerCount = minAnswerCount;
		this.maxFavoriteCount = maxFavoriteCount;
		this.minFavoriteCount = minFavoriteCount;
		this.avgFavoriteCount = avgFavoriteCount;
		this.commentCount = commentCount;
		this.maxCommentScore = maxCommentScore;
		this.minCommentScore = minCommentScore;
		this.avgCommentScore = avgCommentScore;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getPostCount() {
		return postCount;
	}

	public void setPostCount(int postCount) {
		this.postCount = postCount;
	}

	public int getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(int maxScore) {
		this.maxScore = maxScore;
	}

	public int getMinScore() {
		return minScore;
	}

	public void setMinScore(int minScore) {
		this.minScore = minScore;
	}

	public int getAvgScore() {
		return avgScore;
	}

	public void setAvgScore(int avgScore) {
		this.avgScore = avgScore;
	}

	public int getMaxAnswerCount() {
		return maxAnswerCount;
	}

	public void setMaxAnswerCount(int maxAnswerCount) {
		this.maxAnswerCount = maxAnswerCount;
	}

	public int getMinAnswerCount() {
		return minAnswerCount;
	}

	public void setMinAnswerCount(int minAnswerCount) {
		this.minAnswerCount = minAnswerCount;
	}

	public int getAvgAnswerCount() {
		return avgAnswerCount;
	}

	public void setAvgAnswerCount(int avgAnswerCount) {
		this.avgAnswerCount = avgAnswerCount;
	}

	public int getMaxFavoriteCount() {
		return maxFavoriteCount;
	}

	public void setMaxFavoriteCount(int maxFavoriteCount) {
		this.maxFavoriteCount = maxFavoriteCount;
	}

	public int getMinFavoriteCount() {
		return minFavoriteCount;
	}

	public void setMinFavoriteCount(int minFavoriteCount) {
		this.minFavoriteCount = minFavoriteCount;
	}

	public int getAvgFavoriteCount() {
		return avgFavoriteCount;
	}

	public void setAvgFavoriteCount(int avgFavoriteCount) {
		this.avgFavoriteCount = avgFavoriteCount;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public int getMaxCommentScore() {
		return maxCommentScore;
	}

	public void setMaxCommentScore(int maxCommentScore) {
		this.maxCommentScore = maxCommentScore;
	}

	public int getMinCommentScore() {
		return minCommentScore;
	}

	public void setMinCommentScore(int minCommentScore) {
		this.minCommentScore = minCommentScore;
	}

	public int getAvgCommentScore() {
		return avgCommentScore;
	}

	public void setAvgCommentScore(int avgCommentScore) {
		this.avgCommentScore = avgCommentScore;
	}
	
	@Override
	public String toString() {
		return tag + "\t"+ postCount;
	}
}
