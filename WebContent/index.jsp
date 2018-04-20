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
          	for(int i = 0; i < posts.size(); i++){
          %>
          <div class="w3-container w3-card w3-white w3-round w3-margin"><br>
            <span class="w3-right w3-opacity"><%=  Math.abs(Duration.between(LocalDateTime.now(), posts.get(i).getDate()).toHours())  %></span>
            <h4><%= posts.get(i).getUser()	%></h4><br>
            <!-- -=============POST IMAGE================- -->
            <img src="getPic?postId=<%= posts.get(i).getId() %>" alt="" class="w3-left w3-circle w3-margin-right" height="50%" width="50%"> 
            <hr class="w3-clear">
            <p><%= posts.get(i).getText() %></p>
              <div class="w3-row-padding" style="margin:0 -16px">
    	      </div>
    	   <form method="post" action="likePost">
    	      <input type="hidden" id="like" value="<%= posts.get(i).getId()%>">
    	      <input onclick="likePost(<%=i %>)" type="button"class="w3-button w3-theme-d1 w3-margin-bottom" class="fa fa-thumbs-up" value="Like">
    	      <p class="likeID"><%= posts.get(i).getLikes() %></p>
          </form>
          
         <% 
       		List<Comment> comments = posts.get(i).getComments();
       		 if(comments != null){
        	for(int j=0; j < comments.size(); j++) {
          %>
          <div class="w3-container w3-card w3-white w3-round w3-margin"><br>
            <span class="w3-right w3-opacity"><%=  Math.abs(Duration.between(LocalDateTime.now(), comments.get(j).getDate()).toHours())  %></span>
            <h3><%= UserDao.getInstance().getByID(comments.get(j).getUserId()) %></h3><br>
            
            <p><%= comments.get(j).getText()  %></p>
          
             <% 
       		List<Comment> childComments = comments.get(j).getComments();
       		 if(childComments != null){
        	for(int x = 0; x < childComments.size(); x++) {
          %>
          <div class="w3-container w3-card w3-white w3-round w3-margin">
            <span class="w3-right w3-opacity"><%= Math.abs(Duration.between(LocalDateTime.now(), childComments.get(x).getDate()).toHours()) %></span>
            <h4><%= UserDao.getInstance().getByID(childComments.get(x).getUserId()) %></h4>
            <p><%= childComments.get(x).getText()  %></p>
             <form method="post" action="likeComment">
    	      <input type="hidden" id="likeComment" value="<%= childComments.get(x).getId()%>">
    	      <input onclick="likeComments(<%=childComments.get(x).getLikes() %>)" type="button" class="w3-button w3-theme-d1 w3-margin-bottom" value="Like">
    	      <p class="likeCommentID"><%= childComments.get(x).getLikes() %></p>
          </form>
    	  </div>

          <%}} %>
            
            <form method="post" action="likeComment">
    	      <input type="hidden" id="likeComment" value="<%= comments.get(j).getId()%>">
    	      <input onclick="likeComments(<%=comments.get(j).getLikes() %>)" type="button" class="w3-button w3-theme-d1 w3-margin-bottom" value="Like">
    	       <p class="likeCommentID"><%= comments.get(j).getLikes() %></p>
          </form>
    	  </div>
    	  
          <%}} %>
          
          
          <form action="comment" method="post">
                  	 <input contenteditable="true" class="w3-border w3-padding" name="text" required>
                  	  <input type="hidden" name="currentPost" value="<%= posts.get(i).getId()%>">
                  	 <br>
                  	 <button type="submit" class="w3-button w3-theme"><i class="fa fa-pencil"></i>Comment</button> 
                  </form>
             </div> 
          <%}} %>

<%@ include file="index2part2.jsp"%>