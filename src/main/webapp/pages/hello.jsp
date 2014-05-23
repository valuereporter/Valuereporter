<html>
<head>
    <title>Some graphing examples</title>
        <script language="javascript" type="text/javascript" src="../js/jqplot/jquery.min.js"></script>
        <script language="javascript" type="text/javascript" src="../js/jqplot/jquery.jqplot.js"></script>
        <script type="text/javascript" src="../js/jqplot/jqplot.dateAxisRenderer.js"></script>
        <link rel="stylesheet" type="text/css" href="../css/jqplot/jquery.jqplot.css" />
        <script type="text/javascript">
            $(document).ready(function(){
            var line1=[['2008-06-30 8:00AM',4], ['2008-7-30 8:00AM',6.5], ['2008-8-30 8:00AM',5.7], ['2008-9-30 8:00AM',9], [Date.now(),8.2]];
            var plot2 = $.jqplot('chart2', [line1], {
                title:'Customized Date Axis',
                gridPadding:{right:35},
                axes:{
                    xaxis:{
                        renderer:$.jqplot.DateAxisRenderer,
                        tickOptions:{formatString:'%b %#d, %y'},
                        min:'May 30, 2008',
                        tickInterval:'1 month'
                    }
                },
                series:[{lineWidth:4, markerOptions:{style:'square'}}]
            });
        });
    </script>
</head>
<body>
    <h2>Being Java Guys | Hello World</h2>
    <h4>${message}</h4>
    <div id="chart2" style="height:300px; width:500px;"></div>
</body>
</html>