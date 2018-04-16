<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="ISO-8859-1">
<title>Autocomplete in java web application using Jquery and JSON</title>
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<link rel="stylesheet" 
  href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
<!-- User defied css -->
<link rel="stylesheet" href="style.css">

</head>
<body>
<div class="header">
        <h3>Autocomplete in java web application using Jquery and JSON</h3>
</div>
<br />
<br />
<div class="search-container">
        <div class="ui-widget">
                <input type="text" id="search" name="search" class="search" />
        </div>
</div>

<script>

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