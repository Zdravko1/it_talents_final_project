package friendbook.model.comment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Comment {

	private long id;
	private String text;
	private long userId;
	private long postId;
	private Long parentComment;
	private LocalDateTime date;
	private List<Comment> comments = new ArrayList<>();
	private int likes;

	
	public Comment(long userId, long postId, Long parentComment, String text) {
		this.userId = userId;
		this.postId = postId;
		this.parentComment = parentComment;
		setText(text);
	}
	
	public Comment(long id, long userId, long postId, Long parentComment, String text) {
		this(userId, postId, parentComment, text);
		this.id = id;
	}
	
	void setDate(LocalDateTime date) {
		this.date = date;
	}
	
	public LocalDateTime getDate() {
		return date;
	}
	
	
	private void setText(String text) {
		if (text == null || text.trim().isEmpty()) {
			throw new IllegalArgumentException("Comment cannot be empty!");
		}
		this.text = text;
	}
	
	public Long getPost() {
		return postId;
	}
	
	public void addComment(Comment comment) {
		this.comments.add(comment);
	}
	
	public Long getParentComment() {
		return parentComment;
	}
	
	public List<Comment> getComments() {
		return Collections.unmodifiableList(this.comments);
	}
	
	public long getUserId() {
		return userId;
	}
	
	public long getId() {
		return id;
	}
	
	public int getLikes() {
		return likes;
	}
	
	public void setLikes(int likes) {
		if(likes > 0) {
			this.likes = likes;
		}
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getText() {
		return text;
	}
}
