
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	//String swfFilePath=session.getAttribute("swfpath").toString();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="css/bootstrap.min.css" rel="stylesheet">
		<script type="text/javascript" src="js/jquery.min.js"></script>
		<script type="text/javascript" src="js/flexpaper.js"></script>
		<script type="text/javascript" src="js/flexpaper_handlers.js"></script>
		<script src="js/handlebars-v2.0.0.js"></script>
		<script src="js/bootstrap.min.js"></script>
		<style type="text/css" media="screen">
html,body {
	height: 100%;
}

body {
	margin: 0;
	padding: 0;
	overflow: auto;
}

#flashContent {
	display: none;
}
</style>
		<%
			String path = session.getAttribute("swfPath").toString();
		%>
		<title>文档在线预览系统</title>
	</head>
	<body>
		<div class="row">
		<div class="col-md-2"></div>
		<div class="col-md-8">
			<div id="documentViewer" class="flexpaper_viewer"
				style="width: 770px; height: 500px"></div>

			<script type="text/javascript">
	var startDocument = "Paper";

	$('#documentViewer').FlexPaperViewer( {
		config : {
			//在这里设置路径，显示
			SWFFile : '<%=path%>',
			Scale : 0.6,
			ZoomTransition : 'easeOut',
			ZoomTime : 0.5,
			ZoomInterval : 0.2,
			FitPageOnLoad : true,
			FitWidthOnLoad : false,
			FullScreenAsMaxWindow : false,
			ProgressiveLoading : false,
			MinZoomSize : 0.2,
			MaxZoomSize : 5,
			SearchMatchAll : false,
			InitViewMode : 'Portrait',
			RenderingOrder : 'flash',
			StartAtPage : '',

			ViewModeToolsVisible : true,
			ZoomToolsVisible : true,
			NavToolsVisible : true,
			CursorToolsVisible : true,
			SearchToolsVisible : true,
			WMode : 'window',
			localeChain : 'en_US'
		}
	});
</script>
		</div>
		</div>
	</body>
</html>
