<%@page import="java.util.Map.Entry"%>
<%@page import="friendbook.model.comment.Comment"%>
<%@page import="java.util.HashMap"%>
<%@page import="friendbook.model.post.Post"%>
<%@page import="java.util.LinkedList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<% 
      	LinkedList<Post> posts = (LinkedList)request.getAttribute("posts");
      	if(posts != null){
      	for(Post p : posts){
      %>
      <div class="w3-container w3-card w3-white w3-round w3-margin"><br>
        <span class="w3-right w3-opacity">1 min</span>
        <h4><%= request.getSession().getAttribute("user")%></h4><br>
        <!-- -=============POST IMAGE================- -->
        <img src="/w3images/avatar2.png" alt="Image" class="w3-left w3-circle w3-margin-right" style="width:60px"> 
        <hr class="w3-clear">
        <p><%= p.getText() %></p>
          <div class="w3-row-padding" style="margin:0 -16px">
	      </div>
	   <form method="post" action="like">
	      <input type="hidden" name="like" value="<%= p.getId()%>">
	      <button type="submit" class="w3-button w3-theme-d1 w3-margin-bottom" ><i class="fa fa-thumbs-up"></i>Like</button><%= p.getLikes() %>
      </form>
    <% 
    //TODO comments by post  show -  Make it work
      	HashMap<Long, LinkedList<Comment>> comments = (HashMap<Long, LinkedList<Comment>>)request.getAttribute("comments");
      	if(comments != null && comments.containsKey(p.getId())){
      		for(Comment comment : comments.get(p.getId())) {
      %>
      <div class="w3-container w3-card w3-white w3-round w3-margin"><br>
        <span class="w3-right w3-opacity">1 min</span>
        <h4><%= request.getSession().getAttribute("user")%></h4><br>
        
        <p><%= comment.getText()  %></p>
          <div class="w3-row-padding" style="margin:0 -16px">
	  </div>
      <%}} %>
      
      <form action="comment" method="post">
              	 <input contenteditable="true" class="w3-border w3-padding" name="text">
              	  <input type="hidden" name="currentPost" value="<%= p.getId()%>">
              	 <br>
              	 <button type="submit" class="w3-button w3-theme"><i class="fa fa-pencil"></i>Comment</button> 
              </form>
         </div>
         </div>
         
      <%}} %>