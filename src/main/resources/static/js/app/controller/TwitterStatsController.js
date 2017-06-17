monApp.controller('TwitterStatsController',
    ['$rootScope','TwitterService','AuthSession','$scope','$location','$http',  function($rootScope,TwitterService,AuthSession,$scope,$location,$http) {
    	var self=this;
		self.keyWordList=[];
		self.params={// this is the parameters object that contain the search criteria
    			'query':'',
    			'startYear':0,
    			'endYear':0,
    			 'month':0,
    			'sentimentMethode':0
    	};
		self.selectKeyWord=selectKeyWord;
		self.search=getAnalysedTwitteStatsWithStats;
		self.selectSentiMethode=selectSentiMethode;
		self.error=true;
        self.successMessage = '';
        self.errorMessage = '';
        self.done = false;
        loadKeyWords();
        $scope.itemArray = [
                            {id: 1, name: 'first'},
                            {id: 2, name: 'second'},
                            {id: 3, name: 'third'},
                            {id: 4, name: 'fourth'},
                            {id: 5, name: 'fifth'},
                        ];

                        $scope.selected = {};
        self.onlyIntegers = /^\d+$/;
        self.onlyNumbers = /^\d+([,.]\d+)?$/;
        $scope.items = [{
        	  id: 1,
        	  label: 'aLabel',
        	  subItem: { name: 'aSubItem' }
        	}, {
        	  id: 2,
        	  label: 'bLabel',
        	  subItem: { name: 'bSubItem' }
        	}];
     
        function selectSentiMethode(sentiId){
			self.params.sentimentMethode=sentiId;
		}
        
        function selectKeyWord(x){
        	self.params.keyWord=x;
        }
        	function getAnalysedTwitteStatsWithStats(){
        		 self.hightchart=true;
        		 
        		 TwitterService.getStats(self.params)
        		 .then(
        		      function(data){
        		      setDataToBarChart(data); 
        		      //setDataToLineChart(data);
        		      self.positiveTweets=data.positiveItems;
        		      self.negativeTweets=data.negativeItems;
        		      self.neutralTweets=data.neutralItems;
        		      self.barChartShow=true;
        		      
        	     	 },function(erroResponse){
        			 console.error('the llist is empty');
        	         self.errorMessage = 'Error while cretriving data : ' + errResponse.data.errorMessage;
        	         self.successMessage='';
        		 });
        	 
        }
        function  loadKeyWords(){
	    	  TwitterService.getTwitterKeyWord()
	    	  .then(function(res){
	    	      self.keyWordList=res;
	    	  },
	    	  function(errorMessage){
	    		  $scope.message = 'Failed load twitter key words! '+errorMessage;
	    		 
	    	  }
	       );
	    	  
	      }
        
        function setDataToLineChart(data){
       	 Highcharts.chart('lineChartContainer', {

       		    title: {
       		        text:  'sentiments analyses of the query ( '+self.params.keyWord.word+' )'
       		    },

       		    subtitle: {
       		        text: 'Source: platforme'
       		    },

       		    yAxis: {
       		        title: {
       		            text: 'Number of twittes'
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
         	       text:  'sentiments analyses of the query ( '+self.params.keyWord.word+' )'
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
   
}]);