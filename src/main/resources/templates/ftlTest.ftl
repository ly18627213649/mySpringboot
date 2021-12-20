<#assign base=request.contextPath/>
<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<title>freemark模板测试</title>
	    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8;IE=edge,chrome=1" />

	    <script type="text/javascript">
	    	var base = "${base}";
	    </script>
	</head>

	<body>
	    <h3>freemark 模板测试</h3>
	    <div id="condition">
             base: ${base}
			 code: ${code}
			 msg: ${msg}
	    </div>
	</body>
</html>

