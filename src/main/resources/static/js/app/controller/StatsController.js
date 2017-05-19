monApp.controller('StatsController',
    ['StatsService','$scope',  function(StatsService,$scope) {
    	var self=this;
    	// directvies to hide/show
    	self.searchShow=false;
    	self.hightchart=false;
    	
    	// the principale function 
    	self.search=getAnalysedPostStatsWithStats;
    	self.initMap=mappy;
		self.params={// this is the parameters object that contain the search criteria
    			'query':'',
    			'startYear': 0,
    			'endYear':0,
    			 'month':0,
    			'sentimentMethode':0
    	};

    	/*           bar and pie chart               */  
		
    	// data for bubble chart
 function getAnalysedPostStatsWithStats(){
	 self.hightchart=true;
	 
	 StatsService.getAnalysedPostsWithStats(self.params)
	 .then(
	      function(data){
	    	  console.log(data);
	      setDataToBarChart(data); 
	      setDataToLineChart(data);
	      mappy(data);
	      self.barChartShow=true;
	      
     	 },function(erroResponse){
		 console.error('the llist is empty');
         self.errorMessage = 'Error while cretriving data : ' + errResponse.data.errorMessage;
         self.successMessage='';
	 });
 }
 
 function setDataToLineChart(data){
	 Highcharts.chart('lineChartContainer', {

		    title: {
		        text: 'sentiments analyses of the query (java)'
		    },

		    subtitle: {
		        text: 'Source: platforme'
		    },

		    yAxis: {
		        title: {
		            text: 'Number of posts'
		        }
		    },
		    legend: {
		        layout: 'vertical',
		        align: 'right',
		        verticalAlign: 'middle'
		    },

		    xAxis: {
	  	        categories: Object.values(data.labelSeries)
	  	    },
		    series: [{
		        name: 'positive',
		        data: Object.values(data.positiveDataCount)
		    }, {
		        name: 'neutral',
		        data: Object.values(data.neutralDataCount)
		    }, {
		        name: 'netagive',
		        data: Object.values(data.negativeDataCount)
		    }
		
		    ]

		});
 }
 
  function setDataToBarChart(data){
	  Highcharts.chart('barChartContainer', {
  	    title: {
  	        text: 'sentiments analysis for the query (java)'
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
  	        name: 'positive',
  	        data: Object.values(data.positiveDataCount)
  	    }, {
  	        type: 'column',
  	        name: 'negative',
  	        data: Object.values(data.negativeDataCount)
  	    }, {
  	        type: 'column',
  	        name: 'neutral',
  	        data: Object.values(data.neutralDataCount)
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
  	            name: 'positive',
  	            y: Object.keys(data.positivePosts).length,
  	            color: Highcharts.getOptions().colors[0] // Jane's color
  	        }, {
  	            name: 'negative',
  	            y: Object.keys(data.negativePosts).length,
  	            color: Highcharts.getOptions().colors[1] // John's color
  	        }, {
  	            name: 'neutral',
  	            y: Object.keys(data.neutralPost).length,
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
		    
		    var createMarker = function (info){
		        var marker = new google.maps.Marker({
		            map: $scope.map,
		            icon: 'http://maps.google.com/mapfiles/ms/icons/green-dot.png',
		        //    http://maps.google.com/mapfiles/ms/icons/red-dot.png__ http://maps.google.com/mapfiles/ms/icons/blue-dot.png
		            position: new google.maps.LatLng(info.latitude, info.longitude),
		           // title: info.city
		        });
		     //   marker.content = '<div class="infoWindowContent">' + info.desc + '</div>';
		        
		        google.maps.event.addListener(marker, 'click', function(){
		            infoWindow.setContent('<h2>' + marker.title + '</h2>' + marker.content);
		            infoWindow.open($scope.map, marker);
		        });
		        
		        $scope.markers.push(marker);
		        
		    }  
		    
		    for (i = 0; i < data.negativePosts.length; i++){
		    	console.log(data.negativePosts[i].location);
		        createMarker(data.negativePosts[i].location);
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