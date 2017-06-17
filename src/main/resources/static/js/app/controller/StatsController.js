monApp.controller('StatsController',
    ['StatsService','$scope',  function(StatsService,$scope) {
    	var self=this;
    	// directvies to hide/show
    	self.searchShow=true;
    	self.hightchart=false;
    	self.selectSentiMethode=selectSentiMethode;
    	self.statShow=false
    	// the principale function 
    	self.search=getAnalysedPostStatsWithStats;
    	self.initMap=mappy;
		self.params={// this is the parameters object that contain the search criteria
    			'query':'',
    			'startYear': 0,
    			'endYear':0,
    			 'month':0,
    			'sentimentMethode':1
    	};

		function selectSentiMethode(sentiId){
			self.params.sentimentMethode=sentiId;
		}
 function getAnalysedPostStatsWithStats(){
	 StatsService.getAnalysedPostsWithStats(self.params)
	 .then(
	      function(data){
	      setDataToBarChart(data); 
	     // setDataToLineChart(data);
	      self.searchShow=false;
	      self.statShow=true;
	      self.hightchart=true;
	      mappy(data);
	      self.barChartShow=true;
	      
     	 },function(erroResponse){
		 console.error('the llist is empty');
         self.errorMessage = 'Error while cretriving data : ' + errResponse.data.errorMessage;
         self.successMessage='';
	 });
 }
 
// function setDataToLineChart(data){
//	 Highcharts.chart('lineChartContainer', {
//
//		    title: {
//		        text: 'sentiments analyses of the query (java)'
//		    },
//
//		    subtitle: {
//		        text: 'Source: platforme'
//		    },
//
//		    yAxis: {
//		        title: {
//		            text: 'Number of posts'
//		        }
//		    },
//		    legend: {
//		        layout: 'vertical',
//		        align: 'right',
//		        verticalAlign: 'middle'
//		    },
//
//		    xAxis: {
//	  	        categories: Object.values(data.labelSeries)
//	  	    },
//		    series: [{
//		        name: 'positive',
//		        data: Object.values(data.positiveDataCount)
//		    }, {
//		        name: 'neutral',
//		        data: Object.values(data.neutralDataCount)
//		    }, {
//		        name: 'netagive',
//		        data: Object.values(data.negativeDataCount)
//		    }
//		
//		    ]
//
//		});
// }
 
 function setDataToBarChart(data){
  	
  	  Highcharts.chart('barChartContainer', {
    	    title: {
    	       text:  'sentiments analyses of the query ( '+self.params.query+' )'
    	    },
    	    xAxis: {
    	        categories: Object.values(data.labelSeries)
    	    },
    	    labels: {
    	        items: [{
    	            html: 'Total',
    	            style: {
    	                left: '50px',
    	                top: '18px',
    	                color: (Highcharts.theme && Highcharts.theme.textColor) || 'black'
    	            }
    	        }]
    	    },
    	    series: [{
    	        type: 'column',
    	        name: 'Neutral',
    	        data: Object.values(data.neutralDataCount)
    	    }, {
    	        type: 'column',
    	        name: 'Negative',
    	        data: Object.values(data.negativeDataCount)
    	    }, {
    	        type: 'column',
    	        name: 'Positive',
    	        data: Object.values(data.positiveDataCount)
    	    }, {
    	        type: 'spline',
    	        name: 'Average',
    	        data: Object.values(data.averageDataCount),
    	        marker: {
    	            lineWidth: 2,
    	            lineColor: Highcharts.getOptions().colors[3],
    	            fillColor: 'white'
    	        }
    	    }, {
    	        type: 'pie',
    	        name: 'Total ',
    	        data: [{
    	            name: 'Neutral',
    	            y: Object.keys(data.neutralItems).length,
    	            color: Highcharts.getOptions().colors[0] // Jane's color
    	        }, {
    	            name: 'Negative',
    	            y: Object.keys(data.negativeItems).length,
    	            color: Highcharts.getOptions().colors[1] // John's color
    	        }, {
    	            name: 'Positive',
    	            y: Object.keys(data.positiveItems).length,
    	            color: Highcharts.getOptions().colors[2] // Joe's color
    	        }],
    	        center: [100, 80],
    	        size: 100,
    	        showInLegend: false,
    	        dataLabels: {
    	            enabled: false
    	        }
    	    }]
    	});	
   }
  
  
  
  function mappy(data){

	//Data
	  var cities = [
	      {
	          city : 'Toronto',
	          desc : 'This is the best city in the world!',
	          lat : 43.7000,
	          long : -79.4000
	      },
	      {
	          city : 'New York',
	          desc : 'This city is aiiiiite!',
	          lat : 40.6700,
	          long : -73.9400
	      },
	      {
	          city : 'Chicago',
	          desc : 'This is the second best city in the world!',
	          lat : 41.8819,
	          long : -87.6278
	      },
	      {
	          city : 'Los Angeles',
	          desc : 'This city is live!',
	          lat : 34.0500,
	          long : -118.2500
	      },
	      {
	          city : 'Las Vegas',
	          desc : 'Sin City...\'nuff said!',
	          lat : 36.0800,
	          long : -115.1522
	      }
	  ];

	  var mapOptions = {
		        zoom: 4,
		        center: new google.maps.LatLng(40.0000, -98.0000),
		        mapTypeId: google.maps.MapTypeId.TERRAIN
		    }

		    $scope.map = new google.maps.Map(document.getElementById('map'), mapOptions);

		    $scope.markers = [];
		    
		    var infoWindow = new google.maps.InfoWindow();
		
		    var oms = new OverlappingMarkerSpiderfier($scope.map, { 
		    	  markersWontMove: true, 
		    	  markersWontHide: true,
		    	  basicFormatEvents: true
		    	});
		    
		    var createMarker = function (info){
		        var marker = new google.maps.Marker({
		            map: $scope.map,
		            icon: 'http://maps.google.com/mapfiles/ms/icons/green-dot.png',
		            position: new google.maps.LatLng(info.location.latitude, info.location.longitude),
		            title: info.title
		        });
		      marker.content = '<div class="infoWindowContent">' + info.content + '</div>';
		        
		        google.maps.event.addListener(marker, 'click', function(){
		            infoWindow.setContent('<h2>' + marker.title + '</h2>' + marker.content);
		            infoWindow.open($scope.map, marker);
		        });
		        
		        $scope.markers.push(marker);
		        
		    }  
		    
		    var createSpiderMarker = function (info,iconUrl){
		    		    var marker = new google.maps.Marker({ 
		    		    	 icon: iconUrl,
		    		    	 position: new google.maps.LatLng(info.location.latitude, info.location.longitude)
		    		    	});  // markerData works here as a LatLngLiteral
		    		    google.maps.event.addListener(marker, 'spider_click', function(e) {// 'spider_click', not plain 'click'
		    		    	infoWindow.setContent(info.content);
		    		    	infoWindow.open($scope.map, marker);
		    		    });
		    		    oms.addMarker(marker);  // adds the marker to the spiderfier _and_ the map
		    		    
		    }
		    
		    // creating negative posts marker
		    for (i = 0; i < data.negativeItems.length; i++){
		    	var iconUrl='http://maps.google.com/mapfiles/ms/icons/red-dot.png';
		    	createSpiderMarker(data.negativeItems[i],iconUrl);	
		    }
		    // creating positive posts marker
		    for (i = 0; i < data.positiveItems.length; i++){
		    	var iconUrl='http://maps.google.com/mapfiles/ms/icons/green-dot.png';
		    	createSpiderMarker(data.positiveItems[i],iconUrl);	
		    }
		    // creating neutral posts marker
		    for (i = 0; i < data.neutralItems.length; i++){
		    	var iconUrl='http://maps.google.com/mapfiles/ms/icons/blue-dot.png';
		    	createSpiderMarker(data.neutralItems[i],iconUrl);	
		    }

		    $scope.openInfoWindow = function(e, selectedMarker){
		        e.preventDefault();
		        google.maps.event.trigger(selectedMarker, 'click');
		    }
//		    StatsService.getCoordonateFromIp("202.88.237.138")
//		      .then(
//		          function(response){
//		        	  var lats = response.loc.split(',')[0]; 
//		              var lngs = response.loc.split(',')[1];
//		          },
//		          function(errResponse){
//		              console.error('Error while removing user Error :'+errResponse.data);
//		          }
//		      );
           }
  
  
  
  function countryMap(){
	  



	// New map-pie series type that also allows lat/lon as center option.
	// Also adds a sizeFormatter option to the series, to allow dynamic sizing
	// of the pies.
	Highcharts.seriesType('mappie', 'pie', {
	    center: null, // Can't be array by default anymore
	    clip: true, // For map navigation
	    states: {
	        hover: {
	            halo: {
	                size: 5
	            }
	        }
	    },
	    dataLabels: {
	        enabled: false
	    }
	}, {
	    getCenter: function () {
	        var options = this.options,
	            chart = this.chart,
	            slicingRoom = 2 * (options.slicedOffset || 0);
	        if (!options.center) {
	            options.center = [null, null]; // Do the default here instead
	        }
	        // Handle lat/lon support
	        if (options.center.lat !== undefined) {
	            var point = chart.fromLatLonToPoint(options.center);
	            options.center = [
	                chart.xAxis[0].toPixels(point.x, true),
	                chart.yAxis[0].toPixels(point.y, true)
	            ];
	        }
	        // Handle dynamic size
	        if (options.sizeFormatter) {
	            options.size = options.sizeFormatter.call(this);
	        }
	        // Call parent function
	        var result = Highcharts.seriesTypes.pie.prototype.getCenter.call(this);
	        // Must correct for slicing room to get exact pixel pos
	        result[0] -= slicingRoom;
	        result[1] -= slicingRoom;
	        return result;
	    },
	    translate: function (p) {
	        this.options.center = this.userOptions.center;
	        this.center = this.getCenter();
	        return Highcharts.seriesTypes.pie.prototype.translate.call(this, p);
	    }
	});


	var data = [
	    ['lr',88, 77, 66, 6, 5, -1],
	    ['bn',88, 77, 66, 6, 5, -1],
	    ['iq', 88, 77, 66, 6, 5, -1],
	    ['ge', 88, 77, 66, 6, 5, -1],
	    ['ma', 66, 10, 87, 25, 28, 3],
	    ['ch', 55, 99, 4, 8, 77, 0],
	    ['td', 88, 77, 66, 6, 5, 1],
	    ['kv', 88, 77, 66, 6, 5, 0], 
	    ],
	    maxVotes = 0,
	    demColor = 'rgba(74,131,240,0.80)',
	    repColor = 'rgba(220,71,71,0.80)',
	    libColor = 'rgba(240,190,50,0.80)',
	    grnColor = 'rgba(90,200,90,0.80)';


	// Compute max votes to find relative sizes of bubbles
	Highcharts.each(data, function (row) {
	    maxVotes = Math.max(maxVotes, row[5]);
	});

	// Build the chart
	var chart = Highcharts.mapChart('container', {
	    title: {
	        text: 'USA 2016 Presidential Election Results'
	    },

	    chart: {
	        animation: true // Disable animation, especially for zooming
	    },

	    colorAxis: {
	        dataClasses: [{
	            from: -1,
	            to: 0,
	            color: 'rgba(244,91,91,0.5)',
	            name: 'Republican'
	        }, {
	            from: 0,
	            to: 1,
	            color: 'rgba(124,181,236,0.5)',
	            name: 'Democrat'
	        }, {
	            from: 2,
	            to: 3,
	            name: 'Libertarian',
	            color: libColor
	        }, {
	            from: 3,
	            to: 4,
	            name: 'Green',
	            color: grnColor
	        }]
	    },

	    mapNavigation: {
	        enabled: true
	    },
	    // Limit zoom range
	    yAxis: {
	       // minRange: 2300,
	       // max :1000
	    },
	    tooltip: {
	        useHTML: true
	    },

	    // Default options for the pies
	    plotOptions: {
	        mappie: {
	            borderColor: 'rgba(255,255,255,0.4)',
	            borderWidth: 1,
	            tooltip: {
	                headerFormat: ''
	            }
	        }
	    },

	    series: [{
	        mapData: Highcharts.maps['custom/world'],
	        data: data,
	        name: 'States',
	        borderColor: '#FFF',
	        showInLegend: false,
	        joinBy: ['hc-key', 'id'],
	        keys: ['id', 'demVotes', 'repVotes', 'libVotes', 'grnVotes',
	            'sumVotes', 'value', 'pieOffset'],
	        tooltip: {
	            headerFormat: '',
	            pointFormatter: function () {
	                var hoverVotes = this.hoverVotes; // Used by pie only
	                return '<b>' + this.id + ' votes</b><br/>' +
	                    Highcharts.map([
	                        ['Democrats', this.demVotes, demColor],
	                        ['Republicans', this.repVotes, repColor],
	                        ['Libertarians', this.libVotes, libColor],
	                        ['Green', this.grnVotes, grnColor]
	                    ].sort(function (a, b) {
	                        return b[1] - a[1]; // Sort tooltip by most votes
	                    }), function (line) {
	                        return '<span style="color:' + line[2] +
	                            // Colorized bullet
	                            '">\u25CF</span> ' +
	                            // Party and votes
	                            (line[0] === hoverVotes ? '<b>' : '') +
	                            line[0] + ': ' +
	                            Highcharts.numberFormat(line[1], 0) +
	                            (line[0] === hoverVotes ? '</b>' : '') +
	                            '<br/>';
	                    }).join('') +
	                    '<hr/>Total: ' + Highcharts.numberFormat(this.sumVotes, 0);
	            }
	        }
	    }, {
	        name: 'Separators',
	        type: 'mapline',
	        data: Highcharts.geojson(Highcharts.maps['custom/world'], 'mapline'),
	        color: '#707070',
	        showInLegend: false,
	        enableMouseTracking: false
	    }, {
	        name: 'Connectors',
	        type: 'mapline',
	        color: 'rgba(130, 130, 130, 0.5)',
	        zIndex: 5,
	        showInLegend: false,
	        enableMouseTracking: false
	    }]
	});

	// pichart

	// When clicking legend items, also toggle connectors and pies
	// Add the pies after chart load, optionally with offset and connector

	Highcharts.each(chart.series[0].points, function (state) {
	    if (!state.id) {
	        return; // Skip points with no data, if any
	    }
	    var latitude=0;
	    var longitude=0;
	    for (var i in properties) {
	  if (properties[i]['hc-key'] == state.properties["hc-a2"]) {
	    latitude=properties[i]['latitude'];
	    longitude=properties[i]['longitude'];// {a: 5, b: 6}
	  }
	}
	/*  var filterObj = properties.filter(function(e) {
	  return e['hc-key'] ==state.properties["hc-a2"] ;
	 }); */
	    var pieOffset = state.pieOffset || {},
	        centerLat = parseFloat(latitude);
	        centerLon = parseFloat(longitude);

	    // Add the pie for this state
	    chart.addSeries({
	        type: 'mappie',
	        name: state.id,
	        zIndex: 6, // Keep pies above connector lines
	        sizeFormatter: function () {
	            var yAxis = this.chart.yAxis[0],
	                zoomFactor = (yAxis.dataMax - yAxis.dataMin) /
	                    (yAxis.max - yAxis.min);
	            return Math.max(
	              /*  this.chart.chartWidth / 20 * zoomFactor, // Min size
	                this.chart.chartWidth / 11 * zoomFactor * state.sumVotes / maxVotes*/
	                25,10
	            );
	        },
	        tooltip: {
	            // Use the state tooltip for the pies as well
	            pointFormatter: function () {
	                return state.series.tooltipOptions.pointFormatter.call({
	                    id: state.id,
	                    hoverVotes: this.name,
	                    demVotes: state.demVotes,
	                    repVotes: state.repVotes,
	                    libVotes: state.libVotes,
	                    grnVotes: state.grnVotes,
	                    sumVotes: state.sumVotes
	                });
	            }
	        },
	        data: [{
	            name: 'Democrats',
	            y: state.demVotes,
	            color: demColor
	        }, {
	            name: 'Republicans',
	            y: state.repVotes,
	            color: repColor
	        }, {
	            name: 'Libertarians',
	            y: state.libVotes,
	            color: libColor
	        }, {
	            name: 'Green',
	            y: state.grnVotes,
	            color: grnColor
	        }],
	        center: {
	            lat: centerLat + (pieOffset.lat || 0),
	            lon: centerLon + (pieOffset.lon || 0)
	        }
	    }, false);

	    // Draw connector to state center if the pie has been offset
	    if (pieOffset.drawConnector !== false) {
	        var centerPoint = chart.fromLatLonToPoint({
	                lat: centerLat,
	                lon: centerLon
	            }),
	            offsetPoint = chart.fromLatLonToPoint({
	                lat: centerLat + (pieOffset.lat || 0),
	                lon: centerLon + (pieOffset.lon || 0)
	            });
	        chart.series[2].addPoint({
	            name: state.id,
	            path: 'M' + offsetPoint.x + ' ' + offsetPoint.y +
	                'L' + centerPoint.x + ' ' + centerPoint.y
	        }, false);
	    }
	});
	// Only redraw once all pies and connectors have been added
	chart.redraw();
  }
  
  function initMap(){
	  StatsService.getCoordonateFromIp("202.88.237.138")
      .then(
          function(response){
        	  var lats = response.loc.split(',')[0]; 
              var lngs = response.loc.split(',')[1];
          },
          function(errResponse){
              console.error('Error while removing user Error :'+errResponse.data);
          }
      );
	  
	  
	  var map = new google.maps.Map(document.getElementById('map'), {
          zoom: 8,
          center: {lat: 35.717, lng: 139.731}
        });
  }
}]);