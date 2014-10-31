<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="ajouteTrajet" method="post">
Station Depart<input type="text" name="stationD"><br>Station Arrive<input type="text" name="stationA"><br>
vous partez dans combien de minutes:<input type="text" name="heured"><br>
tems estimé du trajet:<input type="text" name="heurea"><br><br>
 Indiquer un commentaire à la communauté:<br><textarea rows="4" cols="50" name="comment" value="Leave your comment here..">
</textarea> <br>
<input type="submit" value="Soumettre" name="submit">
</form>
</body>
</html>