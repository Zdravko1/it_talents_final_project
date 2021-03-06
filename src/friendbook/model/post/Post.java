package friendbook.model.post;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
	private LocalDateTime date;
	
	//for testing purposes
	public Post(User user, String text) {
		this.user = user;
		setText(text);
	}
	
	public Post(long id, User user, String text, LocalDateTime date) {
		this(user, text);
		this.id = id;
		this.date = date;
	}
	//
	
	public Post(User user, String text, String imagePath) {
		this.user = user;
		setText(text);
		this.imagePath = imagePath;
	}

	public Post(long id, String imagePath, String text, LocalDateTime date, User user) {
		this.id = id;
		this.imagePath = imagePath;
		this.text = text;
		this.user = user;
		this.date = date;
	}
	
	public Post(int id, User u, String text) {
		this.id = id;
		this.user = u;
		this.text = text;
	}

	public Post(long id, String imagePath, String text, User user) {
		this.id = id;
		this.imagePath = imagePath;
		this.text = text;
		this.user = user;
	}

	public LocalDateTime getDate() {
		return date;
	}
	
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	public void setDate(LocalDateTime date) {
		this.date = date;
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
