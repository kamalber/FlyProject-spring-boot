monApp.controller('StatsController',
    ['StatsService','$scope',  function(StatsService,$scope) {

    	var self=this;
    	self.search=getAnalysedPostStatsWithStats;
		self.params={// this is the parameters object that contain the search criteria
    			'query':'',
    			'startYear': 0,
    			'endYear':0,
    			 'month':0,
    			'sentimentMethode':0
    	};
    	
// data for bar chart
    	  self.labels = ['2006', '2007', '2008', '2009', '2010', '2011', '2012','2008', '2009', '2010', '2011', '2012'];
    	  self.series = ['Series A', 'Series B','Series d'];
    	  self.data = [
    	    [65, 59, 80, 81, 56, 55, 40,81, 56, 55, 40,81, 56, 55, 40],
    	    [28, 48, 40, 19, 86, 27, 90,81, 56, 55, 40,81, 56, 55, 40],
    	    [22, 38, 30, 39, 86, 27, 90,81, 56, 55, 40,81, 56, 55, 40]
    	  ];
    	  
// data for pie chart
    	  self.labelsPie = ["Download Sales", "In-Store Sales", "Mail-Order Sales"];
    	  self.dataPie = [300, 500, 100];
    	  
    	// data for bubble chart
    
 function getAnalysedPostStatsWithStats(){
	 StatsService.getAnalysedPostsWithStats(self.params)
	 .then(
	      function(data){
	      setDataToBarChart(data); 
     	 },function(erroResponse){
		 console.error('the llist is empty');
         self.errorMessage = 'Error while cretriving data : ' + errResponse.data.errorMessage;
         self.successMessage='';
	 });
 }
 
  function setDataToBarChart(data){
	  console.log(self.data);
	  console.log(data.statistics);
	  var keys = Object.keys(data.statistics);
	// self.labels=
	  
	  for(var i=0;i<data.statistics.length;i++) {
		  self.labels.push("'"+i+1+"");
		  console.log("lenght");
		  
	}
 }
 
    }]);





