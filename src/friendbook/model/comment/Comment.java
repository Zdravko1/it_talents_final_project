package friendbook.model.comment;

import friendbook.model.post.Post;
import friendbook.model.user.User;

public class Comment {

	private long id;
	private String text;
	private User user;
	private Post post;
	private Comment parentComment;
	
	public Comment(User user, Post post, Comment parentComment, String text) {
		this.user = user;
		this.post = post;
		this.parentComment = parentComment;
		setText(text);
	}
	
	private void setText(String text) {
		if (text == null || text.trim().isEmpty()) {
			throw new IllegalArgumentException("Invalid comment");
		}
		this.text = text;
	}
	
	public Post getPost() {
		return post;
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
