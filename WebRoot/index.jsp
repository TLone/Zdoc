<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="struts"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'index.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link href="css/bootstrap.min.css" rel="stylesheet">

<script src="js/jquery.min.js"></script>
<script src="js/handlebars-v2.0.0.js"></script>
<script src="js/bootstrap.min.js"></script>
<style>
body {
	padding-top: 80px
}
</style>
</head>

<body>

	<!-- ---------------------------------------------------------- -->
	<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
	<div class="container-fluid">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-8">
				<div class="navbar-header">
					<a class="navbar-brand" href="#"> <img alt="Brand"
						src="./pic/lolo.png" width="20px" height="20px"> </a>
				</div>

				<!-- Collect the nav links, forms, and other content for toggling -->
				<div class="collapse navbar-collapse"
					id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav">
						<li class="active"><a href="#">Link</a></li>
						<li><a href="#">Link</a></li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<li><a href="#">登陆</a></li>
						<form class="navbar-form navbar-left" role="search">
							<div class="form-group">
								<input type="text" class="form-control" placeholder="Search">
							</div>
							<button type="submit" class="btn btn-default">Submit</button>
						</form>
					</ul>
				</div>
			</div>
		</div>

		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container-fluid --> </nav>
	<!--
	<struts:actionerror />
	<struts:form action="UploadAction" enctype="multipart/form-data"
		method="post" validate="true">
		<struts:label value="upload file" class="form-control"></struts:label>
		<struts:textfield name="username"></struts:textfield>
		<struts:file name="file" label="file1"></struts:file>
		<struts:submit value="上传" method="upload"></struts:submit>
	</struts:form>
	- -->

	<div class="row">
		<div class="col-md-2"></div>
		<div class="col-md-5">
			<div class="panel panel-default">
				<div class="panel-heading">Docs</div>
				<div class="panel-body"></div>
			</div>
		</div>
		<div class="col-md-3">
			<div class="panel panel-default">
				<div class="panel-heading">Hot Docs</div>
				<div class="panel-body">
					<ul class="list-group">
						<li class="list-group-item"><span class="badge">14</span>
							Cras justo odio</li>
						<li class="list-group-item"><span class="badge">14</span>
							Cras justo odio</li>
						<li class="list-group-item"><span class="badge">14</span>
							Cras justo odio</li>
						<li class="list-group-item"><span class="badge">14</span>
							Cras justo odio</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="col-md-2"></div>
	</div>
	<div class="row">
		<div class="col-md-2"></div>
		<div class="col-md-5"></div>
		<div class="col-md-3">
			<div class="panel panel-default">
				<div class="panel-heading">Upload Doc</div>
				<div class="panel-body">
					<struts:actionerror />
					<form role="form" action="UploadAction"
						enctype="multipart/form-data" method="post">
						<div class="form-group">
							<label>文件描述</label> <input type="text" class="form-control"
								id="description" placeholder="description" name="description">
						</div>
						<div class="form-group">
							<label>File input</label> <input type="file" id="file"
								name="file">
						</div>
						<button type="submit" class="btn btn-default">Submit</button>
					</form>
				</div>
			</div>
		</div>
		<div class="col-md-2"></div>
	</div>
</body>
</html>
