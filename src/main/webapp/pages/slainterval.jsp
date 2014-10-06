<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>SLA using HighChart</title>

</head>
<body>
<h2>SLA graph for ${model.prefix}</h2>
<h4>${message}</h4>
<div id="chart2" style="height:300px; width:500px;"></div>

<script src="http://code.jquery.com/jquery.js"></script>
<!--<script src="${pageContext.request.contextPath}/js/jquery-1.8.2.min.js"></script>  -->
<script src="http://code.highcharts.com/highcharts.js"></script>
<script src="http://code.highcharts.com/modules/exporting.js"></script>
<script type="text/javascript">
    var chart;
    $.getJSON('/reporter/observe/sla/interval/${model.prefix}?filter=${model.methodName}&from=${model.from}&to=${model.to}', function(data) {
        var graphTitle='${model.methodName}';

        var line1=[];
        var mycategories=[];
        var series=[
            {
                name: 'Mean',
                data: []
            },
            {
                name: 'Max',
                data: []
            }, {
                name: '95 percentile',
                data: []
        }];
        data.forEach(function(interval){
            console.log("startTime: " + interval.startTime +", mean : " + interval.mean + ", max: " + interval.max + ", p95: " + interval.p95);
            mycategories.push(interval.startTime);
            series[0].data.push(interval.mean);
            series[1].data.push(interval.max);
            series[2].data.push(interval.p95);
        });

        $('#chart2').highcharts({
            chart: {
                type: 'spline',
                zoomType: 'x'
            },
            data: {
                json: line1
            },

            credits: {
                enabled: false
            },
            title: {
                text: graphTitle
            },
            xAxis: {
                categories: mycategories,
                type: 'datetime',
                dateTimeLabelFormats: { // don't display the dummy year
                    month: '%e. %b',
                    year: '%b'
                }
            },
            yAxis: {
                title: {
                    text: 'Responsetime (ms) '
                },
                min: 0
            },
            tooltip: {
                dateTimeLabelFormats: { // don't display the dummy year
                    second:" %d/%m %H:%M:%S",
                    minute:" %d/%m %H:%M",
                    hour:" %d/%m %H:%M",
                    day:"%A, %b %e, %Y",
                    month: '%e. %b',
                    year: '%b'
                },
                followPointer: false
            },
            series: series
        });

    });



</script>
</body>
</html>