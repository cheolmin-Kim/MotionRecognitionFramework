<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
<title>Home</title>
<link href="<%=application.getContextPath()%>/resources/bootstrap-3.3.7/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<script src="<%=application.getContextPath()%>/resources/jquery/jquery-3.2.1.min.js" type="text/javascript"></script>
<script src="<%=application.getContextPath()%>/resources/bootstrap-3.3.7/js/bootstrap.min.js" type="text/javascript"></script>


<!-- start: CSS -->
<%-- <link href="<%=application.getContextPath()%>/resources/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="<%=application.getContextPath()%>/resources/css/bootstrap-responsive.min.css" rel="stylesheet" type="text/css">
<link id="base-style" href="<%=application.getContextPath()%>/resources/css/style.css" rel="stylesheet" type="text/css">
<link id="base-style-responsive" href="<%=application.getContextPath()%>/resources/css/style-responsive.css" rel="stylesheet" type="text/css">
<link
	href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800&subset=latin,cyrillic-ext,latin-ext'
	rel='stylesheet' type='text/css'
> --%>
<!-- start: CSS -->

<!-- start: Favicon -->
<link rel="shortcut icon" href="<%=application.getContextPath()%>/resources/img/favicon.ico">
<!-- end: Favicon -->

<!-- start: highchart CSS -->
<link href="<%=application.getContextPath()%>/resources/css/highcharts/template.css" rel="stylesheet" type="text/css" />
<link href="<%=application.getContextPath()%>/resources/css/highcharts/source-sans-pro.css" rel="stylesheet" type="text/css" />
<link href="<%=application.getContextPath()%>/resources/css/highcharts/font-awesome.css" rel="stylesheet" type="text/css" />
<link href="<%=application.getContextPath()%>/resources/css/highcharts/bootstrap.min.css" rel="stylesheet" type="text/css" />
<!-- end: highchart CSS -->

<link href="<%=application.getContextPath()%>/resources/css/font-awesome.css" rel="stylesheet" type="text/css" />
<link href="<%=application.getContextPath()%>/resources/css/font-awesome.min.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="<%=application.getContextPath()%>/resources/js/myjs.js"></script>

<script type="text/javascript">
	function leave() {
		if (confirm("정말 탈퇴하시겠습니까?") == true) { // 확인
			$.ajax({
				url : "http://localhost:8080/iot1_motion/leave",
				method : "post",
				data : {
					"mid" : "${member.mid}"
				},
				success : function(data) {
					location.href = "http://localhost:8080/iot1_motion/"
				}
			});
		} else {
			return;
		}
	}
</script>
</head>

<body>
	<!-- start: Header -->
	<div id="menu" class="nav-down">
		<nav class="navbar navbar-default" role="navigation">
			<div class="container-fluid">
				<!-- start: Header Menu -->
				<div class="collapse navbar-collapse">
					<div id="menu-container">
						<div style="text-align: center; margin-right: 100px; float: left">
							<a style="line-height: 2" href="http://localhost:8080/iot1_motion/"> IoT0619 </a>
						</div>

						<div id="menu-second" class="hidden-xs">
							<ul class="nav navbar-nav" style="line-height: 50%">
								<!-- start: User Dropdown -->
								<li class="item-112 deeper dropdown">
									<a style="width: 100px; height: 50px; vertical-align: middle;" class="dropdown-toggle" data-toggle="dropdown" href="#">
										<img src="<%=application.getContextPath()%>/resources/img/student-32.png"></img>
									</a>
									<ul class="dropdown-menu" role="menu">
										<li class="item-206">
											<a>Account Settings</a>
										</li>
										<li class="item-206">
											<c:if test="${member.mid==null}">
												<a>환영합니다.</a>
											</c:if>
											<c:if test="${log=='login'}">
												<a>${member.mname}</a>
											</c:if>
										</li>
										<li>
											<c:if test="${log=='login'}">
												<a class="btn-leave" onclick="javascript:leave();">
													<i class="halflings-icon off"></i>
													회원탈퇴
												</a>
											</c:if>
										</li>
									</ul>
								</li>
								<!-- end: User Dropdown -->
								<div style="float: left">
									<c:if test="${log!='login'}">
										<a style="line-height: 40px">로그인해주세요</a>
									</c:if>
									<c:if test="${log=='login'}">
										<img src="http://graph.facebook.com/${member.getMid()}/picture" />
									</c:if>
								</div>

							</ul>
							<c:if test="${member.mid==null}">
								<a class="btn btn-primary" href="<%=application.getContextPath()%>/fb/login"
									style="background: transparent; border: none; box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0), 0 1px 2px rgba(0, 0, 0, 0)"
								>
									<!-- <i class="fa fa-user fa-fw"></i> -->
									회원가입
								</a>
							</c:if>
							<c:if test="${log!='login'}">
								<a class="btn btn-primary" href="<%=application.getContextPath()%>/login"
									style="line-height: 30px; background: transparent; border: none; box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0), 0 1px 2px rgba(0, 0, 0, 0)"
								>
									<!-- <i class="fa fa-user fa-fw"></i> -->
									로그인
								</a>
							</c:if>
							<c:if test="${log=='login'}">
								<a class="btn btn-primary" href="<%=application.getContextPath()%>/logout"
									style="line-height: 30px; background: transparent; border: none; box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0), 0 1px 2px rgba(0, 0, 0, 0)"
								>
									<i class="fa fa-lock"> </i>
									로그아웃
								</a>
							</c:if>
							<div class="clearfix"></div>
						</div>
						<!-- end: Header Menu -->

						<div id="menu-main">
							<ul class="nav navbar-nav">
								<li class="item-103 deeper dropdown">
									<a href="<%=application.getContextPath()%>/demo">
										<span class="hidden-tablet">Demo</span>
									</a>
								</li>
								<li class="item-108 deeper dropdown">
									<a href="static/index.html">
										<span class="hidden-tablet">Document</span>
									</a>
								</li>
								<li class="item-254 deeper dropdown">
									<a href="<%=application.getContextPath()%>/gyroTest">
										Simulation
										<span class="toggle-arrow"></span>
									</a>
									<!-- 
									<ul class="dropdown-menu" role="menu">
										<li>
											<a href="">
												<i class="icon-tasks"></i>
												<span class="hidden-tablet">Gyro, UltraSonic, Infrared light Test</span>
											</a>
										</li>
										<li class="item-256">
											<a href="<%=application.getContextPath()%>/gyroExplore">
												<i class="icon-tasks"></i>
												<span class="hidden-tablet">Gyro Explore</span>
											</a>
										</li>
									</ul>
								</li>
								-->
								<li class="item-282 deeper dropdown">
									<a href="<%=application.getContextPath()%>/board/boardList">
										<span class="hidden-tablet">Question Board</span>
									</a>
								</li>
								<c:if test="${member.mlevel >= 4}">
									<li class="item-282 deeper dropdown">
										<a href="<%=application.getContextPath()%>/admin/memberList">
											<span class="hidden-tablet">Member Board</span>
										</a>
									</li>
								</c:if>
							</ul>
							<div class="clearfix"></div>
						</div>
					</div>
				</div>
			</div>
		</nav>
	</div>

	<!-- start: JavaScript-->

	<script src="<%=application.getContextPath()%>/resources/js/jquery-1.9.1.min.js"></script>
	<script src="<%=application.getContextPath()%>/resources/js/jquery-migrate-1.0.0.min.js"></script>
	<script src="<%=application.getContextPath()%>/resources/js/jquery-ui-1.10.0.custom.min.js"></script>
	<script src="<%=application.getContextPath()%>/resources/js/jquery.ui.touch-punch.js"></script>
	<script src="<%=application.getContextPath()%>/resources/js/modernizr.js"></script>
	<script src="<%=application.getContextPath()%>/resources/js/bootstrap.min.js"></script>
	<script src="<%=application.getContextPath()%>/resources/js/jquery.cookie.js"></script>
	<script src='<%=application.getContextPath()%>/resources/js/fullcalendar.min.js'></script>
	<script src='<%=application.getContextPath()%>/resources/js/jquery.dataTables.min.js'></script>

	<script src="<%=application.getContextPath()%>/resources/js/excanvas.js"></script>
	<script src="<%=application.getContextPath()%>/resources/js/jquery.flot.js"></script>
	<script src="<%=application.getContextPath()%>/resources/js/jquery.flot.pie.js"></script>
	<script src="<%=application.getContextPath()%>/resources/js/jquery.flot.stack.js"></script>
	<script src="<%=application.getContextPath()%>/resources/js/jquery.flot.resize.min.js"></script>

	<script src="<%=application.getContextPath()%>/resources/js/jquery.chosen.min.js"></script>
	<script src="<%=application.getContextPath()%>/resources/js/jquery.uniform.min.js"></script>
	<script src="<%=application.getContextPath()%>/resources/js/jquery.cleditor.min.js"></script>
	<script src="<%=application.getContextPath()%>/resources/js/jquery.noty.js"></script>
	<script src="<%=application.getContextPath()%>/resources/js/jquery.elfinder.min.js"></script>
	<script src="<%=application.getContextPath()%>/resources/js/jquery.raty.min.js"></script>
	<script src="<%=application.getContextPath()%>/resources/js/jquery.iphone.toggle.js"></script>
	<script src="<%=application.getContextPath()%>/resources/js/jquery.uploadify-3.1.min.js"></script>
	<script src="<%=application.getContextPath()%>/resources/js/jquery.gritter.min.js"></script>
	<script src="<%=application.getContextPath()%>/resources/js/jquery.imagesloaded.js"></script>
	<script src="<%=application.getContextPath()%>/resources/js/jquery.masonry.min.js"></script>
	<script src="<%=application.getContextPath()%>/resources/js/jquery.knob.modified.js"></script>
	<script src="<%=application.getContextPath()%>/resources/js/jquery.sparkline.min.js"></script>

	<script src="<%=application.getContextPath()%>/resources/js/counter.js"></script>
	<script src="<%=application.getContextPath()%>/resources/js/retina.js"></script>
	<script src="<%=application.getContextPath()%>/resources/js/custom.js"></script>
	<!-- end: JavaScript-->
</body>
</html>
