<%@page import="java.text.DateFormat"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.time.Duration"%>
<%@page import="friendbook.model.user.UserDao"%>
<%@page import="friendbook.model.comment.Comment"%>
<%@page import="java.util.List"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.Set"%>
<%@page import="friendbook.model.post.Post"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "friendbook.model.user.User" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<title>Friendbook</title>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-theme-blue-grey.css">
<link rel='stylesheet' href='https://fonts.googleapis.com/css?family=Open+Sans'>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<!-- for search function -->
<head>
	<meta charset="ISO-8859-1">
	<script src="//code.jquery.com/jquery-1.10.2.js"></script>
	<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
	<link rel="stylesheet" 
	  href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
	<!-- ================ -->
</head>
<style>
html,body,h1,h2,h3,h4,h5 {font-family: "Open Sans", sans-serif}
</style>
<body class="w3-theme-l5">

<% 
User u = (User)request.getSession().getAttribute("visitedUser");
boolean visit = u != null; 
boolean onFeed = request.getSession().getAttribute("feed") != null; %>

<!-- Navbar -->
<div class="w3-top">
 <div class="w3-bar w3-theme-d2 w3-left-align w3-large">
  <a class="w3-bar-item w3-button w3-hide-medium w3-hide-large w3-right w3-padding-large w3-hover-white w3-large w3-theme-d2" href="javascript:void(0);" onclick="openNav()"><i class="fa fa-bars"></i></a>
  	<a href="." class="w3-bar-item w3-button w3-padding-large w3-theme-d4"><i class="fa fa-home w3-margin-right"></i>Home</a>
  <a href="." class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white" title="News"><i class="fa fa-globe"></i></a>
  <a href="#" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white" title="My Account"><i class="fa fa-user"></i></a>
  <a href="logout" class="w3-bar-item w3-button w3-padding-large w3-right w3-theme-d4">Log Out</a>
  
  <form action="search" method="post">
  	<button type="submit" class="w3-bar-item w3-button w3-padding-large w3-right w3-theme-d4">Search</button>
    <input type="text" id="search" name="user" class="w3-bar-item w3-button w3-padding-large w3-right w3-theme-d4" required>
  </form>
    
 </div>
</div>

<!-- Navbar on small screens -->
<div id="navDemo" class="w3-bar-block w3-theme-d2 w3-hide w3-hide-large w3-hide-medium w3-large">
  <a href="#" class="w3-bar-item w3-button w3-padding-large">Link 1</a>
  <a href="#" class="w3-bar-item w3-button w3-padding-large">Link 2</a>
  <a href="#" class="w3-bar-item w3-button w3-padding-large">Link 3</a>
  <a href="#" class="w3-bar-item w3-button w3-padding-large">Profile</a>
</div>

<!-- Page Container -->
<div class="w3-container w3-content" style="max-width:1400px;margin-top:80px">    
  <!-- The Grid -->
  <div class="w3-row">
    <!-- Left Column -->
    <div class="w3-col m3">
      <!-- Profile -->
      <div class="w3-card w3-round w3-white">
        <div class="w3-container">
         <h4 class="w3-center"><%= (visit) ? "Profile" : "My Profile"%></h4>
         <h4 class="w3-center"><%= (visit) ? u : request.getSession().getAttribute("user") %></h4>
         <div style="display : <%= !visit ? "none" : "" %>">
         	
        		<% if(visit){%>
        			<button id="follow" name="followedId" onclick="follow()" value="<%=u.getId()%>" class="w3-button w3-theme"><%=u.isFollowed() ? "Followed" : "Follow" %></button>
        		<%}%>
         	
         </div>
         <hr>
        </div>
      </div>
      <br>

    <!-- End Left Column -->
    </div>
    
    <!-- Middle Column -->
    <div id="middleColumnId" class="w3-col m7">
    
      <div class="w3-row-padding">
        <div class="w3-col m12">
          <div class="w3-card w3-round w3-white" style="display : <%= (visit || onFeed) ? "none" : "" %>">
            <div class="w3-container w3-padding">
              <h6 class="w3-opacity">Post something</h6>
              <form action="post" method="post" enctype="multipart/form-data">
              	<input id="post" contenteditable="true" class="w3-border w3-padding" name="text" required>
				<input id="fileId" type="file" name="file" size="50" />
				<br>
              	 <br>
              	 <br>
              	 <input onclick="addPost()" class="w3-button w3-theme" type="button" value="Post">
              </form>
            </div>
          </div>
          <form method="post" action="order">
          <select name="order">
          	<option value="likes">Likes</option>
          	<option value="date">Date</option>
          </select>
          <button type="submit">Order</button>
          </form>
        </div>
      </div>
      
      <% 
      int commentID = 0;
      int likeCommentID = 0;
      int i = 0;
      ArrayList<Post> posts = null;
      
      	if(visit){
      		posts = (ArrayList)session.getAttribute("visitedUserPosts");
      	} else if(onFeed) {
      		posts = (ArrayList)session.getAttribute("feed");
      	} else{
      		posts = (ArrayList)request.getAttribute("posts");
      	}
        
      	if(posts != null){
      	for(; i < posts.size(); i++){
      %>
      <div id="postId<%=i %>" class="w3-container w3-card w3-white w3-round w3-margin"><br>
        <span class="w3-right w3-opacity"><%=  Math.abs(Duration.between(LocalDateTime.now(), posts.get(i).getDate()).toHours())  %></span>
        <h4><%= posts.get(i).getUser()	%></h4><br>
        <!-- -=============POST IMAGE================- -->
        <img src="getPic?postId=<%= posts.get(i).getId()%>" class="w3-left w3-margin-right" height="50%" width="50%" alt=""> 
        <hr class="w3-clear">
        <p><%= posts.get(i).getText() %></p>
          <div class="w3-row-padding" style="margin:0 -16px">
	      </div>
	   <form method="post" action="likePost">
	      <input type="hidden" id="like<%=i %>" value="<%= posts.get(i).getId()%>">
	      <input onclick="likePosts(<%=i %>)" type="button"class="w3-button w3-theme-d1 w3-margin-bottom" class="fa fa-thumbs-up" value="Like">
	      <p id="likeID<%=i%>"><%= posts.get(i).getLikes() %></p>
      </form>
      
     <% 
   		List<Comment> comments = posts.get(i).getComments();
   		 if(comments != null){
    	for(int j=0; j < comments.size(); j++) {
      %>
      <div class="w3-container w3-card w3-white w3-round w3-margin"><br>
        <span class="w3-right w3-opacity"><%= Math.abs(Duration.between(LocalDateTime.now(), comments.get(j).getDate()).toHours())  %></span>
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
	      <input type="hidden" id="likeComment<%=likeCommentID%>" value="<%= childComments.get(x).getId()%>">
	      <input onclick="likeComments(<%=likeCommentID%>)" type="button" class="w3-button w3-theme-d1 w3-margin-bottom" value="Like">
	      <p id="likeCommentID<%=likeCommentID++%>"><%= childComments.get(x).getLikes() %></p>
      </form>
	  </div>

      <%}} %>
        
        <form method="post" action="likeComment">
	      <input type="hidden" id="likeComment<%=likeCommentID%>" value="<%= comments.get(j).getId()%>">
	      <input onclick="likeComments(<%=likeCommentID%>)" type="button" class="w3-button w3-theme-d1 w3-margin-bottom" value="Like">
	       <p id="likeCommentID<%=likeCommentID++%>"><%= comments.get(j).getLikes() %></p>
      </form>
      
      <div id="commentID">
      		  <form action="comment" method="post">
              	 <input id="commentID<%=commentID %>" contenteditable="true" class="w3-border w3-padding" name="text" required>
              	  <input type="hidden" name="currentPost" value="<%= posts.get(i).getId()%>">
              	 <br>
              	 <input type="button" class="w3-button w3-theme" value="Comment" onclick="addComment(<%=i %>, <%=posts.get(i).getId()%>, <%=commentID++%>, <%= comments.get(j).getId()%>)"> 
              </form>
              </div>
	  </div>
	  
      <%}} %>
      
      		<div id="commentID">
      		  <form action="comment" method="post">
              	 <input id="commentID<%=commentID %>" contenteditable="true" class="w3-border w3-padding" name="text" required>
              	  <input type="hidden" name="currentPost" value="<%= posts.get(i).getId()%>">
              	 <br>
              	 <input type="button" class="w3-button w3-theme" value="Comment" onclick="addComment(<%=i %>, <%=posts.get(i).getId()%>, <%=commentID++%>)"> 
              </form>
              </div>
         </div> 
      <%}} %>
    <!-- End Middle Column -->
    </div>
    
  <!-- End Grid -->
  </div>
  
<!-- End Page Container -->
</div>
<br>

<!-- Footer -->
<footer class="w3-container w3-theme-d3 w3-padding-16">
  <h5>Footer</h5>
</footer>
 
<script>
//follow
function follow() {
	var element = document.getElementById("follow");
	var value = element.value;
	var request = new XMLHttpRequest();
	request.open("POST","follow", true);
	request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	request.send("followedId=" +value);
	request.onreadystatechange=function() {
	    if (this.readyState == 4 && this.status == 200) {
	    	var result = this.responseText;
	    	result = JSON.parse(result);
	      	element.innerHTML = result;
	    }
	  }
}
//addPost
var likeCommentId = <%= likeCommentID+1%>;
var comment_id = <%= commentID+1%>;
var like_id = <%= i+1%>;

function addPost(){
	var flag = true;
	var p = document.getElementById('middleColumnId');
    var newElement = document.createElement("div");
    newElement.setAttribute('id', 'postId'+like_id+'');
    newElement.setAttribute('class', 'w3-container w3-card w3-white w3-round w3-margin');
    
    var file = document.getElementById('fileId').files[0];
    var data = 'text=' + document.getElementById('post').value;
    
    function getBase64(file) {
    	   var reader = new FileReader();
    	   if(file != null){
    	   	reader.readAsDataURL(file);
    	   }
    	   reader.onload = function () {
    		   data += '&file=' + reader.result;
    		   send(data);
    	   };
    	   reader.onerror = function (error) {
    	     console.log('Error: ', error);
    	   };
    	}

    getBase64(file);
    if(file == null){
    	send(data);
	}
    
     function send(data){
	    var request = new XMLHttpRequest();
		request.open('POST', "post", true);
		request.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
		request.send(data);
		request.onreadystatechange = function(){
			if(this.readyState == 4 && this.status == 200){
				var result = this.responseText;
				result = JSON.parse(result);
				var user = result.user.firstName + ' ' + result.user.lastName;
			    var imagePath = result.imagePath;
			    var text = result.text;
			    var postId = result.id;
			    var likes = result.likes;
			    var likeId = like_id++;
			    var commentId = comment_id++;
			    
			    var imageElement = "<img src=\"getPic?postId="+postId+"\" class=\"w3-left w3-margin-right\" height=\"50%\" width=\"50%\" alt=\"\">";			    
			    newElement.innerHTML = "<br><span class=\"w3-right w3-opacity\">0</span><h4>"+user+"</h4><br>"+imageElement+"<hr class=\"w3-clear\"><p>"+text+"</p><div class=\"w3-row-padding\" style=\"margin:0 -16px\"></div><form method=\"post\" action=\"likePost\"><input type=\"hidden\" id=\"like"+likeId+"\" value=\""+postId+"\"><input onclick=\"likePosts("+likeId+")\" type=\"button\"class=\"w3-button w3-theme-d1 w3-margin-bottom\" class=\"fa fa-thumbs-up\" value=\"Like\"><p id=\"likeID"+likeId+"\">"+likes+"</p></form></div><form action=\"comment\" method=\"post\"><input id=\"commentID"+commentId+"\" contenteditable=\"true\" class=\"w3-border w3-padding\" name=\"text\" required><input type=\"hidden\" name=\"currentPost\" value=\""+postId+"\"><br><input type=\"button\" class=\"w3-button w3-theme\" value=\"Comment\" onclick=\"addComment("+likeId+", "+postId+", "+commentId+")\"></form>";
			    p.insertBefore(newElement, p.childNodes[2]);
			}
		}
    }
}
//addComment
function addComment(a, b, c, d){
	var p = document.getElementById('postId'+a);
	var newElement = document.createElement("div");
	newElement.setAttribute('id', 'comment');
	newElement.setAttribute('class', 'w3-container w3-card w3-white w3-round w3-margin');
	
	if (typeof d === 'undefined') {
		var d = null;
	}
	
	var request = new XMLHttpRequest();
	request.open('POST', 'comment', true);
	request.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
	request.send("text=" + document.getElementById("commentID"+c).value + "&currentPost=" + b + "&currentComment=" + d);
	request.onreadystatechange = function(){
		if(this.readyState == 4 && this.status == 200){
			var result = this.responseText;
			result = JSON.parse(result);
			
			var user = result.user.firstName + ' ' + result.user.lastName;
			var text = result.text;
			var id = result.id;
			var likes = result.likes;
			var commentId = likeCommentId++;
			
			newElement.innerHTML = " <br><span class=\"w3-right w3-opacity\">0</span><h4>"+user+"</h4><p>"+text+"</p><form method=\"post\" action=\"likeComment\"><input type=\"hidden\" id=\"likeComment"+commentId+"\" value=\""+id+"\"><input onclick=\"likeComments("+commentId+")\" type=\"button\" class=\"w3-button w3-theme-d1 w3-margin-bottom\" value=\"Like\"><p id=\"likeCommentID"+commentId+"\">"+likes+"</p></form>";
			p.insertBefore(newElement, p.lastChild.previousSibling);
		}
	}
}

//likeComment
function likeComments(a){
	var like = document.getElementById("likeCommentID"+a);
	var request = new XMLHttpRequest();
	request.open('POST', "likeComment", true);
	request.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
	request.send("like=" + document.getElementById("likeComment"+a).value);
	request.onreadystatechange = function(){
		if(this.readyState == 4 && this.status == 200){
			var result = this.responseText;
			result = JSON.parse(result);
			like.innerHTML = result;
		}
	}
}
//likePost
function likePosts(a){
	var like = document.getElementById("likeID"+a);
	var request = new XMLHttpRequest();
	request.open('POST', "likePost", true);
	request.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
	request.send("like=" + document.getElementById("like"+a).value);
	request.onreadystatechange = function(){
		if(this.readyState == 4 && this.status == 200){
			var result = this.responseText;
			result = JSON.parse(result);
			like.innerHTML = result;
		}
	}
}

//search
$(document).ready(function() {
    $(function() {
        $("#search").autocomplete({     
            source : function(request, response) {
              $.ajax({
                   url : "searchServlet",
                   type : "GET",
                   data : {
                          term : request.term
                   },
                   dataType : "json",
                   success : function(data) {
                         response(data);
                   }
            });
         }
     });
  });
});
</script>

</body>
</html>