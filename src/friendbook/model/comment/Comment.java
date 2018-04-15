package friendbook.model.comment;

import friendbook.model.post.Post;
import friendbook.model.user.User;

public class Comment {

	private long id;
	private String text;
	private User user;
	private long postId;
	private Comment parentComment;
	
	public Comment(User user, long postId, Comment parentComment, String text) {
		this.user = user;
		this.postId = postId;
		this.parentComment = parentComment;
		setText(text);
	}
	
	private void setText(String text) {
		if (text == null || text.trim().isEmpty()) {
			throw new IllegalArgumentException("Invalid comment");
		}
		this.text = text;
	}
	
	public Long getPost() {
		return postId;
	}
	
	public Comment getParentComment() {
		return parentComment;
	}
	
	public User getUser() {
		return user;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getText() {
		return text;
	}
}
