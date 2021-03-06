<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.Duration"%>
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
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-theme-blue-grey.css">
<link rel='stylesheet' href='https://fonts.googleapis.com/css?family=Open+Sans'>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<style>
html,body,h1,h2,h3,h4,h5 {font-family: "Open Sans", sans-serif}
</style>
<body class="w3-theme-l5">

<!-- Navbar -->
<div class="w3-top">
 <div class="w3-bar w3-theme-d2 w3-left-align w3-large">
  <a class="w3-bar-item w3-button w3-hide-medium w3-hide-large w3-right w3-padding-large w3-hover-white w3-large w3-theme-d2" href="javascript:void(0);" onclick="openNav()"><i class="fa fa-bars"></i></a>
  	<a href="" class="w3-bar-item w3-button w3-padding-large w3-theme-d4"><i class="fa fa-home w3-margin-right"></i>Home</a>
  <a href="#" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white" title="News"><i class="fa fa-globe"></i></a>
  <a href="#" class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white" title="My Account"><i class="fa fa-user"></i></a>
  <a href="logout" class="w3-bar-item w3-button w3-padding-large w3-right w3-theme-d4">Log Out</a>
  
  <form action="search" method="post">
  	<a type="submit" class="w3-bar-item w3-button w3-padding-large w3-right w3-theme-d4">Search</a>
    <input type="text" name="first_and_last_name" class="w3-bar-item w3-button w3-padding-large w3-right w3-theme-d4">
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
         <h4 class="w3-center">Profile</h4>
         <h4 class="w3-center"><%= request.getAttribute("user") %></h4>
         <hr>
        </div>
      </div>
      <br>

    <!-- End Left Column -->
    </div>
    
    <!-- Middle Column -->
    <div class="w3-col m7">
    
      <div class="w3-row-padding">
        <div class="w3-col m12">
          <div class="w3-card w3-round w3-white">
            <div class="w3-container w3-padding">
            </div>
          </div>
        </div>
      </div>
      
      <%
      LinkedList<Post> posts = new LinkedList();
      if(request.getSession().getAttribute("visitedUserPosts") == null){
    	  posts = (LinkedList)request.getSession().getAttribute("visitedUserPosts");
      } else {
    	  posts = (LinkedList)request.getAttribute("posts");	   	  
      }
      	if(posts != null){
      	for(Post p : posts){
      %>
      <div class="w3-container w3-card w3-white w3-round w3-margin"><br>
        <span class="w3-right w3-opacity"><%= Math.abs(Duration.between(LocalDateTime.now(), p.getDate()).toHours()) %> h</span>
        <h4><%= request.getAttribute("user")%></h4><br>
        <!-- -=============POST IMAGE================- -->
        <img src="/w3images/avatar2.png" alt="Image" class="w3-left w3-circle w3-margin-right" style="width:60px"> 
        <hr class="w3-clear">
        <p><%= p.getText() %></p>
          <div class="w3-row-padding" style="margin:0 -16px">
	      </div>
	   <form method="post" action="like">
	      <input type="hidden" name="like" value="<%= p.getId()%>">
	      <button type="submit" class="w3-button w3-theme-d1 w3-margin-bottom" ><i class="fa fa-thumbs-up"></i>Like</button><%= p.getLikes() %>
	      <button type="button" class="w3-button w3-theme-d2 w3-margin-bottom"><i class="fa fa-comment"></i>Comment</button>
      </form>
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
// Accordion
function myFunction(id) {
    var x = document.getElementById(id);
    if (x.className.indexOf("w3-show") == -1) {
        x.className += " w3-show";
        x.previousElementSibling.className += " w3-theme-d1";
    } else { 
        x.className = x.className.replace("w3-show", "");
        x.previousElementSibling.className = 
        x.previousElementSibling.className.replace(" w3-theme-d1", "");
    }
}

// Used to toggle the menu on smaller screens when clicking on the menu button
function openNav() {
    var x = document.getElementById("navDemo");
    if (x.className.indexOf("w3-show") == -1) {
        x.className += " w3-show";
    } else { 
        x.className = x.className.replace(" w3-show", "");
    }

}
</script>

</body>
</html>