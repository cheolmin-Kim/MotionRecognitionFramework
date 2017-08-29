<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
<title>Home</title>
<link href="<%=application.getContextPath()%>/resources/bootstrap-3.3.7/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<script src="<%=application.getContextPath()%>/resources/jquery/jquery-3.2.1.min.js" type="text/javascript"></script>
<script src="<%=application.getContextPath()%>/resources/bootstrap-3.3.7/js/bootstrap.min.js" type="text/javascript"></script>
</head>

<script type="text/javascript">
	var ws = null;

	$(function() {
		var ws = new WebSocket("ws://" + window.location.host
				+ "/MirrorMotion/websocket/action");

		ws.onmessage = handleOnMessage;
	});


	function handleOnMessage(event) {
		var strMessage = event.data;
		control(strMessage);
		
	}

	function control(message) {
		var message;
		if(message=="left"){
			$('#Carousel').carousel('next');
			
		}
		if(message=="right"){
			$('#Carousel').carousel('prev');
		}
	}
</script>

<body>
	

	<div class="container" style="width: 100%; height: 100%; padding: 0;">
		<div style="width: 100%; height: 100%; position: absolute; z-index: 1">
			<img style="width: 100%; height: 100%;" alt="Image" src="<%=application.getContextPath()%>/resources/image/cafeBackground.jpg">
		</div>

		<div class="row " style="position: absolute; top: 50%; width: 100%; z-index: 2; margin: 0;">
			<%-- <div id="divDisplay" style="background-color: white; color: black" align="center" >direction:${session}</div> --%>
			<div class="col-md-12">
				<div id="Carousel" class="carousel slide">

					<ol class="carousel-indicators">
						<li data-target="#Carousel" data-slide-to="0" class="active"></li>
						<li data-target="#Carousel" data-slide-to="1"></li>
						<li data-target="#Carousel" data-slide-to="2"></li>
					</ol>

					<!-- Carousel items -->
					<div class="carousel-inner">
						<div class="item active">
							<div class="row" align="center">
								<div class="col-md-3" style="float: none;">
									<a href="#" class="thumbnail">
										<img src="<%=application.getContextPath()%>/resources/image/menu/1.png" alt="Image" style="width: 100%; max-width: 100%;">
									</a>
								</div>
							</div>
							<!--.row-->
						</div>
						<!--.item-->
						<div class="item">
							<div class="row" align="center">
								<div class="col-md-3" style="float: none;">
									<a href="#" class="thumbnail">
										<img src="<%=application.getContextPath()%>/resources/image/menu/2.png" alt="Image" style="width: 100%; max-width: 100%;">
									</a>
								</div>
							</div>
							<!--.row-->
						</div>
						<!--.item-->

						<div class="item">
							<div class="row" align="center">
								<div class="col-md-3" style="float: none;">
									<a href="#" class="thumbnail">
										<img src="<%=application.getContextPath()%>/resources/image/menu/3.png" alt="Image" style="width: 100%; max-width: 100%;">
									</a>
								</div>
							</div>
							<!--.row-->
						</div>
						<!--.item-->

					</div>
					<!--.carousel-inner-->
					<a data-slide="prev" href="#Carousel" class="left carousel-control">‹</a>
					<a data-slide="next" href="#Carousel" class="right carousel-control">›</a>
				</div>
				<!--.Carousel-->

			</div>
		</div>
	</div>
	<!--.container-->
</body>

<style>
body {
	/* padding-top: 20px; */
	
}

.carousel {
	margin-bottom: 0;
	/* padding: 0 40px 30px 40px; */
}
/* The controlsy */
.carousel-control {
	left: -12px;
	height: 40px;
	width: 40px;
	background: none repeat scroll 0 0 #222222;
	border: 4px solid #FFFFFF;
	border-radius: 23px 23px 23px 23px;
	margin-top: 90px;
}

.carousel-control.right {
	right: -12px;
}
/* The indicators */
.carousel-indicators {
	right: 50%;
	top: auto;
	/* 	bottom: -10px; */
	margin-right: -19px;
}
/* The colour of the indicators */
.carousel-indicators li {
	background: #cecece;
}

.carousel-indicators .active {
	background: #428bca;
}

.thumbnail {
	background-color: rgba(255, 255, 255, 0);
	border: none;
	position: 40%;
}

.carousel-inner {
	top: 50%;
	width: 100%;
}

.col-md-3 {
	float: none;
}

.row {
	align: center;
}
</style>
</body>
</html>
