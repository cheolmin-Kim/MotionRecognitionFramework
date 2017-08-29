var ultrasonicChart;
$(function() {
	
	ultrasonicChart = new Highcharts.Chart({
		
		chart: {
			renderTo:"ultrasonicChartContainer",
			defaultSeriesType:"spline",
			events: {
				load: requestUltrasonicData
			}
			
		},
		colors: ['yellow'],
		title: {
			text: "UltrasonicSensor(초음파 거리센서)"
		},
		xAxis:{
			type: "datetime",
			tickPixelInterval: 100,
			maxZoom: 20*1000
		},
		yAxis:{
			minPadding: 0.2,
			maxPadding: 0.2,
			title: {
				text: "거리",
				margin: 30
			}
		},
		series: [{
			name: "거리",
			date:[]
			}]
	});
});

function requestUltrasonicData(){
	var ws = new WebSocket("ws://"+location.host+"/iot1_motion/websocket/Ultrasonic");
	ws.onmessage = function(event){
		var data = JSON.parse(event.data);
		var series = ultrasonicChart.series[0];
		var shift = series.data.length>20;
		series.addPoint([data.time, data.distance], true, shift);
		
	};
}