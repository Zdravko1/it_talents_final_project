<%@page import="friendbook.model.comment.Comment"%>
<%@page import="java.util.List"%>
<%@ include file="index2part1.jsp"%>
      
      
 <% 
      	ArrayList<Post> posts = visit ? (ArrayList)request.getSession().getAttribute("visitedUserPosts") : (ArrayList)request.getSession().getAttribute("feed");
      	if(posts != null){
      	for(Post p : posts){
      %>
      <div class="w3-container w3-card w3-white w3-round w3-margin"><br>
        <span class="w3-right w3-opacity">1 min</span>
        <h4><%= p.getUser()	%></h4><br>
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
   		List<Comment> comments = p.getComments();
   		 if(comments != null){
    	for(Comment c : comments) {
      %>
      <div class="w3-container w3-card w3-white w3-round w3-margin"><br>
        <span class="w3-right w3-opacity">1 min</span>
        <h4><%= c.getUserId() %></h4><br>
        
        <p><%= c.getText()  %></p>
	  </div>
      <%}} %>
      
      
      <form action="comment" method="post">
              	 <input contenteditable="true" class="w3-border w3-padding" name="text">
              	  <input type="hidden" name="currentPost" value="<%= p.getId()%>">
              	 <br>
              	 <button type="submit" class="w3-button w3-theme"><i class="fa fa-pencil"></i>Comment</button> 
              </form>
         </div> 
      <%}} %>

<%@ include file="index2part2.jsp"%>