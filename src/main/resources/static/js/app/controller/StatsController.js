monApp.controller('StatsController',
    ['StatsService','$scope',  function($scope) {

    	var self=this;
    	self.getAnalysedPost=getAnalysedPost;
    	self.startDate=null;
    	self.endDate=null;
    	self.querySearch=null;
    	
//  data ofr  	bar chart
    	  self.labels = ['2006', '2007', '2008', '2009', '2010', '2011', '2012'];
    	  self.series = ['Series A', 'Series B','Series d'];
    	  self.data = [
    	    [65, 59, 80, 81, 56, 55, 40],
    	    [28, 48, 40, 19, 86, 27, 90],
    	    [22, 38, 30, 39, 86, 27, 90]
    	  ];
    	  
//   data for pie chart	  
    	  self.labelsPie = ["Download Sales", "In-Store Sales", "Mail-Order Sales"];
    	  self.dataPie = [300, 500, 100];
    	  
    	//   data for bubble chart
    

    function getAnalysedPost(){
    	
    	StatsService.getAnalysedPosts()
    }	  
    	  
    }]);





