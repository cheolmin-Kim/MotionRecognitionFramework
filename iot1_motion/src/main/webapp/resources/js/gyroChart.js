var gyroChart;
$(function() {
	
	gyroChart = new Highcharts.Chart({
		
		chart: {
			renderTo:"gyroChartContainer",
			defaultSeriesType:"spline",
			events: {
				load: requestGyroData
			}
		},
		colors: ['green', 'orange', 'blue'],
		title: {
			text: "GyroSensor(자이로 센서)"
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
				text: "각도",
				margin: 30
			}
		},
		series : [ {
			name : "yaw",
			date : []
		}, {
			name : "pitch",
			date : []
		}, {
			name : "roll",
			date : []
		} ]
	});
});

function requestGyroData(){
	var ws = new WebSocket("ws://"+location.host+"/iot1_motion/websocket/GyroSensor");
	ws.onmessage = function(event){
		var data = JSON.parse(event.data);
		var series1 = gyroChart.series[0];
		var series2 = gyroChart.series[1];
		var series3 = gyroChart.series[2];
		var shift1 = series1.data.length>20;
		var shift2 = series2.data.length>20;
		var shift3 = series3.data.length>20;
		series1.addPoint([data.time, data.yawAngle], true, shift1);
		series2.addPoint([data.time, data.pitchAngle], true, shift2);
		series3.addPoint([data.time, data.rollAngle], true, shift3);
		
	};
}