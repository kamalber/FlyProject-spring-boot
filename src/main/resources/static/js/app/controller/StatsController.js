monApp.controller('StatsController',
    ['StatsService','$scope',  function(StatsService,$scope) {
    	var self=this;
    	self.searchShow=false;
    	self.search=getAnalysedPostStatsWithStats;
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
	 self.searchShow=true;
	 StatsService.getAnalysedPostsWithStats(self.params)
	 .then(
	      function(data){
	    	  console.log(data);
	      setDataToBarChart(data); 
	      
			

	      
	      
	      
     	 },function(erroResponse){
		 console.error('the llist is empty');
         self.errorMessage = 'Error while cretriving data : ' + errResponse.data.errorMessage;
         self.successMessage='';
	 });
 }
 
  function setDataToBarChart(data){
	  Highcharts.chart('containeryy', {
  	    title: {
  	        text: 'Combination chart'
  	    },
  	    xAxis: {
  	        categories: Object.values(data.labelSeries)
  	    },
  	    labels: {
  	        items: [{
  	            html: 'Total fruit consumption',
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
  	        name: 'neutral',
  	        data: Object.values(data.negativeDataCount)
  	    }, {
  	        type: 'column',
  	        name: 'negative',
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
  	        name: 'Total consumption',
  	        data: [{
  	            name: 'positive',
  	            y: 13,
  	            color: Highcharts.getOptions().colors[0] // Jane's color
  	        }, {
  	            name: 'negative',
  	            y: 23,
  	            color: Highcharts.getOptions().colors[1] // John's color
  	        }, {
  	            name: 'neutral',
  	            y: 19,
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
 
    }]);





