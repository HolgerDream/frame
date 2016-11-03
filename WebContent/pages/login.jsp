<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>My Frame</title>
<base href="<%=basePath%>">
<link rel="stylesheet" href="<%=basePath%>csslib/bootstrap/bootstrap.min.css" type="text/css">
<link rel="stylesheet" href="<%=basePath%>css/login/login.css" type="text/css">
<!--[if lt IE 9]>
    <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
	<script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
<script src="<%=basePath%>jslib/jquery/jquery-1.8.3.min.js"></script>
<script src="<%=basePath%>jslib/bootstrap/bootstrap.min.js"></script>
</head>
<body>
	<h1>login </h1>
</body>
</html>