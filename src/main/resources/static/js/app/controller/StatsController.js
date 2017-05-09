monApp.controller('StatsController',
    ['StatsService','$scope',  function(StatsService,$scope) {
    	var self=this;
    	self.searchShow=false;
    	self.barChartShow=false;
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
	      setDataToLineChart(data);
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
  
}]);





