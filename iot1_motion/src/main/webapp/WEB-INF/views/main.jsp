<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
<title>JSP Page</title>
<%-- <link href="<%=application.getContextPath()%>/resources/bootstrap-3.3.7/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<script src="<%=application.getContextPath()%>/resources/jquery/jquery-3.2.1.min.js" type="text/javascript"></script>
<script src="<%=application.getContextPath()%>/resources/bootstrap-3.3.7/js/bootstrap.min.js" type="text/javascript"></script> --%>



<link href="<%=application.getContextPath()%>/resources/css/highcharts/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="<%=application.getContextPath()%>/resources/css/highcharts/source-sans-pro.css" rel="stylesheet" type="text/css" />
<link href="<%=application.getContextPath()%>/resources/css/highcharts/font-awesome.css" rel="stylesheet" type="text/css" />
<link href="<%=application.getContextPath()%>/resources/css/highcharts/template.css" rel="stylesheet" type="text/css" />

<style type="text/css">

/*Master banner container */
.master-video-wrapper {
	overflow: hidden;
	position: relative;
	background-image:
		url('<%=application.getContextPath()%>/resources/img/highcharts/main.png');
	background-size: cover;
	background-position: center;
}

.master-video {
	height: auto;
	width: 100%;
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
}

.master-overlay {
	background-color: #1B1B2B;
	opacity: 0.6;
	position: absolute;
	top: 0;
	left: 0;
	bottom: 0;
	right: 0;
	width: 100%;
	height: 100%;
}

.master-body {
	position: relative;
	width: 100%;
	text-align: center;
	height: 120px;
	line-height: 1;
	top: 0;
	bottom: 0;
	margin: auto;
	color: #fff;
}


.master-title {
	font-size: 50px;
	font-weight: 500;
	margin-top: 10px;
}

.master-sub-title {
	margin: 20px 0 50px 0;
	font-size: 20px;
	color: rgba(255, 255, 255, 0.8);
	line-height: 1.5;
}

.master-logo {
	margin-top: -9%;
}

.master-logo img {
	width: 250px;
	height: auto;
}

.master-body p.button-row a.secondary-link {
	color: #fff;
	font-size: 17px;
}


.master-body p.button-row a.secondary-link:focus, .master-body p.button-row a.secondary-link:hover
	{
	color: #59F14F;
	opacity: 1;
}

.master-body p.button-row .button {
	background-color: #7C82FF;
	font-size: 17px;
}

.master-body p.button-row .button:focus, .master-body p.button-row .button:hover
	{
	background-color: #59F14F;
	opacity: 1;
}

.master-arrow {
	position: absolute;
	bottom: 20px;
	left: 0;
	right: 0;
	margin: auto;
	opacity: 0.4;
	color: #fff;
	text-align: center;
	font-size: 25px;
}

</style>

<script src="<%=application.getContextPath()%>/resources/js/highcharts/jquery-3.1.1.js" type="text/javascript"></script>
<script src="<%=application.getContextPath()%>/resources/js/highcharts/bootstrap.js" type="text/javascript"></script>
<script src="<%=application.getContextPath()%>/resources/js/highcharts/modernizr.js" type="text/javascript"></script>
<script src="<%=application.getContextPath()%>/resources/js/highcharts/script.js" type="text/javascript"></script>
<script src="<%=application.getContextPath()%>/resources/js/highcharts/jquery.apear.min.js" type="text/javascript"></script>
<script src="<%=application.getContextPath()%>/resources/js/highcharts/tweets.min.js" type="text/javascript"></script>
<script src="<%=application.getContextPath()%>/resources/js/highcharts/demos.js" type="text/javascript"></script>

<script type="text/javascript">
	function cycle(div) {
		childnr = 0;
		setInterval(function() {
			childnr = fader(div, childnr);
		}, 16000);
	}

	function fader(element, nr) {
		jQuery(element.children()[nr]).fadeOut('fast', function() {
			nr++;
			if (nr === element.children().length) {
				nr = 0;
			}
			jQuery(element.children()[(nr)]).fadeIn('fast');
		});
		return nr;
	}
	jQuery(function() {
		cycle(jQuery('#twitter-quotes'));
	});
</script>


<script src="<%=application.getContextPath()%>/resources/js/highcharts/covervid.js" type="text/javascript"></script>
<script src="<%=application.getContextPath()%>/resources/js/highcharts/fullscreen.js" type="text/javascript"></script>



</head>
<body>
	<jsp:include page="home.jsp"></jsp:include>
	<div class="custom">
		<div class="master-video-wrapper">
			<video width="320" height="240" class="master-video" autoplay="autoplay" loop="loop" poster="/">
				<source src="<%=application.getContextPath()%>/resources/img/video.mp4" type="video/mp4" />
				<object width="320" height="240" data="<%=application.getContextPath()%>/resources/media/moxieplayer.swf"
					type="application/x-shockwave-flash"
				>
					<param name="src" value="<%=application.getContextPath()%>/resources/media/moxieplayer.swf" />
					<param name="flashvars" value="url=<%=application.getContextPath()%>/resources/img/video.mp4&amp;poster=/" />
					<param name="allowfullscreen" value="true" />
					<param name="allowscriptaccess" value="true" />
				</object>
				<!--<source src="/videos/video.webm" type="video/webm">-->
			</video>
			<div class="master-overlay"></div>
			<div class="master-body col-md-12 col-sm-11 col-xs-12" style="position: fixed;">
				<div class="master-title">Motion Recognition Control</div>
				<div class="master-sub-title">Makes your motion interactive with IoT devices</div>
				<p class="button-row">
					<a class="button" href="<%=application.getContextPath()%>/demo">View demo</a>
					<a class="secondary-link" href="https://github.com/Marsseo/IoTFinalProject">View on Github</a>
				</p>
			</div>
		</div>
	</div>
	
</body>
</html>