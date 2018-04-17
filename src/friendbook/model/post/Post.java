package friendbook.model.post;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import friendbook.model.comment.Comment;
import friendbook.model.user.User;

public class Post implements Serializable{

	private long id;
	private String text;
	private User user;
	private String imagePath;
	private int likes;
	private List<Comment> comments = new ArrayList<>();
	
	//for testing purposes
	public Post(User user, String text) {
		this.user = user;
		setText(text);
	}
	
	public Post(long id, User user, String text) {
		this(user, text);
		this.id = id;
	}
	//
	
	public Post(User user, String text, String imagePath) {
		this.user = user;
		setText(text);
		this.imagePath = imagePath;
	}

	public Post(long id, String imagePath, String text, Date date, User user) {
		this.id = id;
		this.imagePath = imagePath;
		this.text = text;
		this.user = user;
	}

	public void setText(String text) {
		if (text == null || text.trim().isEmpty()) {
			throw new IllegalArgumentException("Invalid comment");
		}
		this.text = text;
	}
	
	public void addComment(Comment comment) {
		this.comments.add(comment);
	}
	
	public User getUser() {
		return user;
	}
	
	public long getId() {
		return id;
	}
	
	public String getImagePath() {
		return imagePath;
	}
	
	public int getLikes() {
		return likes;
	}
	
	public List<Comment> getComments() {
		return Collections.unmodifiableList(this.comments);
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
	
	@Override
	public String toString() {
		return id + " " + text;
	}
}
