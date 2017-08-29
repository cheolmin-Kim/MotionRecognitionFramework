<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri= "http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
		<title>JSP Page</title>
		<!-- start: CSS -->
			<link id="bootstrap-style" href="<%=application.getContextPath()%>/resources/css/bootstrap.min.css" rel="stylesheet">
			<link href="<%=application.getContextPath()%>/resources/css/bootstrap-responsive.min.css" rel="stylesheet">
			<link id="base-style" href="<%=application.getContextPath()%>/resources/css/style.css" rel="stylesheet">
			<link id="base-style-responsive" href="<%=application.getContextPath()%>/resources/css/style-responsive.css" rel="stylesheet">
			<link
				href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800&subset=latin,cyrillic-ext,latin-ext'
				rel='stylesheet' type='text/css'
			>
		<!-- end: CSS -->
		
		<link href="<%= application.getContextPath() %>/resources/bootstrap-3.3.7/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
		<script src="<%= application.getContextPath() %>/resources/jquery/jquery-3.2.1.min.js" type="text/javascript"></script>
		<script src="<%= application.getContextPath() %>/resources/bootstrap-3.3.7/js/bootstrap.min.js" type="text/javascript"></script>
		<meta name="viewport" content="width=device-width, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0">
		<style>
			body {
				background:#000;
				color:#fff;
				padding:0;
				margin:0;
				font-weight: bold;
				overflow:hidden;
			}
		</style>
	</head>
<body>

		<jsp:include page="home.jsp" flush="false"></jsp:include>

		<div id="info"></div>
		
		<script src="<%= application.getContextPath() %>/resources/js/threejs/three.js"></script>
		<script src="<%= application.getContextPath() %>/resources/js/threejs/controls/FlyControls2.js"></script>
		<script src="<%= application.getContextPath() %>/resources/js/threejs/Detector.js"></script>
		<script src="<%= application.getContextPath() %>/resources/js/threejs/libs/stats.min.js"></script>

		<script>
			
			if ( ! Detector.webgl ) Detector.addGetWebGLMessage();
			var container, stats;
			var camera, scene, renderer;
			var geometry, objects;
			var controls, clock = new THREE.Clock();
			
			//var yawAngle=0, pitchAngle=0, rollAngle=0, preyawAngle=0, prepitchAngle=0, prerollAngle=0;
			
			init();
			animate();
			
			function init() {
				container = document.createElement( 'div' );
				document.body.appendChild( container );
				camera = new THREE.PerspectiveCamera( 45, window.innerWidth / window.innerHeight, 1, 15000 );
				camera.position.z = 1000;
				controls = new THREE.FlyControls( camera );
				controls.movementSpeed = 1500;
				controls.rollSpeed = Math.PI / 10;
				scene = new THREE.Scene();
				scene.fog = new THREE.Fog( 0x000000, 1, 15000 );
				scene.autoUpdate = false;
				var light = new THREE.PointLight( 0xff2200 );
				light.position.set( 0, 0, 0 );
				scene.add( light );
				var light = new THREE.DirectionalLight( 0xffffff );
				light.position.set( 0, 0, 1 ).normalize();
				scene.add( light );
				var geometry = [
					[ new THREE.IcosahedronGeometry( 100, 5 ), 50 ],
					[ new THREE.IcosahedronGeometry( 100, 4 ), 300 ],
					[ new THREE.IcosahedronGeometry( 100, 3 ), 1000 ],
					[ new THREE.IcosahedronGeometry( 100, 2 ), 2000 ],
					[ new THREE.IcosahedronGeometry( 100, 1 ), 8000 ],
					[ new THREE.IcosahedronGeometry( 100, 0 ), 10000 ]
				];
				var material = new THREE.MeshLambertMaterial( { color: 0xffffff, wireframe: true } );
				var i, j, mesh, lod;
				for ( j = 0; j < 2000; j ++ ) {
					lod = new THREE.LOD();
					for ( i = 0; i < geometry.length; i ++ ) {
						mesh = new THREE.Mesh( geometry[ i ][ 0 ], material );
						mesh.scale.set( 1.5, 1.5, 1.5 );
						mesh.updateMatrix();
						mesh.matrixAutoUpdate = false;
						lod.addLevel( mesh, geometry[ i ][ 1 ] );
					}
					lod.position.x = 10000 * ( 0.5 - Math.random() );
					lod.position.y =  7500 * ( 0.5 - Math.random() );
					lod.position.z = 10000 * ( 0.5 - Math.random() );
					lod.updateMatrix();
					lod.matrixAutoUpdate = true;
					scene.add( lod );
				}
				renderer = new THREE.WebGLRenderer();
				renderer.setPixelRatio( window.devicePixelRatio );
				renderer.setSize( window.innerWidth, window.innerHeight );
				renderer.sortObjects = false;
				container.appendChild( renderer.domElement );
				//
				window.addEventListener( 'resize', onWindowResize, false );
			}
			function onWindowResize() {
				camera.aspect = window.innerWidth / window.innerHeight;
				camera.updateProjectionMatrix();
				renderer.setSize( window.innerWidth, window.innerHeight );
			}
			function animate() {
				requestAnimationFrame( animate );
				render();
			}
			function render() {
				controls.update( clock.getDelta() );
				scene.updateMatrixWorld();
				scene.traverse( function ( object ) {
					if ( object instanceof THREE.LOD ) {
						object.update( camera );
					}
				} );
				renderer.render( scene, camera );
			}			
			
		/* 	function requestGyroSensorData(){
				var ws = new WebSocket("ws://"+location.host+"/MpuWebProject/websocket/GyroSensor3D2");
				ws.onmessage = function(event){
					var data = JSON.parse(event.data);
					preyawAngle = data.yawAngle-180;
					prepitchAngle = data.pitchAngle-180;
					prerollAngle = data.rollAngle-180;
					console.log("ddd2   "+preyawAngle+"  "+prepitchAngle+"  "+prerollAngle);
				};
				yawAngle = preyawAngle;
				pitchAngle = prepitchAngle;
				rollAngle = prerollAngle;				
			} */
		</script>
</body>
</html>