package friendbook.model.post;

import friendbook.model.user.User;

public class Post {

	private long id;
	private String text;
	private User user;
	private String imagePath;
	
	//for testing purposes
	public Post(User user, String text) {
		this.user = user;
		setText(text);
	}
	
	public Post(int id, User user, String text) {
		this(user, text);
		this.id = id;
	}
	//
	
	
	public Post(User user, String text, String imagePath) {
		this.user = user;
		setText(text);
		this.imagePath = imagePath;
	}
	
	public void setText(String text) {
		if (text == null || text.trim().isEmpty()) {
			throw new IllegalArgumentException("Invalid comment");
		}
		this.text = text;
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
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getText() {
		return text;
	}
}
