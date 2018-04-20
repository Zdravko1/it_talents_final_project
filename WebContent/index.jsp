<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.Duration"%>
<%@page import="friendbook.model.user.UserDao"%>
<%@page import="friendbook.controller.UserManager"%>
<%@page import="friendbook.model.comment.Comment"%>
<%@page import="java.util.List"%>
<%@ include file="index2part1.jsp"%>
      
      
 <% 
      	ArrayList<Post> posts = visit ? (ArrayList)request.getSession().getAttribute("visitedUserPosts") : (ArrayList)request.getSession().getAttribute("feed");
      	if(posts != null){
      	for(Post p : posts){
      %>
      <div class="w3-container w3-card w3-white w3-round w3-margin"><br>
        <span class="w3-right w3-opacity"><%= Math.abs(Duration.between(LocalDateTime.now(), p.getDate()).toHours()) %> h</span>
        <h4><%= p.getUser()	%></h4><br>
        <!-- -=============POST IMAGE================- -->
        <img src="getPic?postId=<%= p.getId()%>" class="w3-left w3-margin-right" height="50%" width="50%" alt=""> 
        <hr class="w3-clear">
        <p><%= p.getText() %></p>
          <div class="w3-row-padding" style="margin:0 -16px">
	      </div>
	   <form method="post" action="likePost">
	      <input type="hidden" name="like" value="<%= p.getId()%>">
	      <button type="submit" class="w3-button w3-theme-d1 w3-margin-bottom" ><i class="fa fa-thumbs-up"></i>Like</button><%= p.getLikes() %>
      </form>
      
     <% 
   		List<Comment> comments = p.getComments();
   		 if(comments != null){
    	for(Comment c : comments) {
      %>
      <div class="w3-container w3-card w3-white w3-round w3-margin"><br>
        <span class="w3-right w3-opacity"><%= Math.abs(Duration.between(LocalDateTime.now(), c.getDate()).toHours()) %> h</span>
        <h4><%= UserDao.getInstance().getByID(c.getUserId()) %></h4><br>
        
        <p><%= c.getText()  %></p>

         <% 
   		List<Comment> childComments = c.getComments();
   		 if(childComments != null){
    	for(Comment childC : childComments) {
      %>
      <div class="w3-container w3-card w3-white w3-round w3-margin">
        <span class="w3-right w3-opacity"><%= Math.abs(Duration.between(LocalDateTime.now(), childC.getDate()).toHours()) %> h</span>
        <h4><%= UserDao.getInstance().getByID(childC.getUserId()) %></h4>
        <p><%= childC.getText()  %></p>
	  </div>

      <%}} %>
        
        <form method="post" action="likeComment">
	      <input type="hidden" name="like" value="<%= c.getId()%>">
	      <button type="submit" class="w3-button w3-theme-d1 w3-margin-bottom" ><i class="fa fa-thumbs-up"></i>Like</button><%= c.getLikes() %>
      </form>
	  </div>
	  
      <%}} %>
      
      
      <form action="comment" method="post">
              	 <input contenteditable="true" class="w3-border w3-padding" name="text" required>
              	  <input type="hidden" name="currentPost" value="<%= p.getId()%>">
              	 <br>
              	 <button type="submit" class="w3-button w3-theme"><i class="fa fa-pencil"></i>Comment</button> 
              </form>
         </div> 
      <%}} %>

<%@ include file="index2part2.jsp"%>