var ifraredrayChart;
$(function() {
	
	ifraredrayChart = new Highcharts.Chart({
		
		chart: {
			renderTo:"ifraredrayChartContainer",
			defaultSeriesType:"spline",
			events: {
				load: requestIfraredrayData
			}
		},
		colors: ['red'],
		title: {
			text: "IfraredraySensor(적외선 거리센서)"
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

function requestIfraredrayData(){
	var ws = new WebSocket("ws://"+location.host+"/iot1_motion/websocket/Ifraredray");
	ws.onmessage = function(event){
		var data = JSON.parse(event.data);
		var series = ifraredrayChart.series[0];
		var shift = series.data.length>20;
		series.addPoint([data.time, data.distance], true, shift);
		
	};
}