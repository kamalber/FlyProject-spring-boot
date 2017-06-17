monApp.controller('ProfileController',
    ['$rootScope','TwitterService','PostService','AcountService','AuthSession','Upload','$timeout','$scope','$location','$sessionStorage',
     function($rootScope,TwitterService,PostService,AcountService,AuthSession,Upload,$timeout,$scope,$location,$sessionStorage) {
    	var self=this;
		self.user={// this is the parameters object that contain the search criteria
    			'username':'',
    			'password':'',
    	};
		self.error=true;
		self.dateRange={};
		self.connected=$sessionStorage.connected;
        self.addKeyWords = addKeyWordToList;
        self.twitterKeWords=[];
        self.userPosts=[];
        loadUserPosts();
        loadKeyWords();
        getMyPostCommentsPlarity();
/*       -- variable             */
        var keyWordList=[];
        
        
        
        
        $scope.uploadImage = function (dataUrl, name) {
        	console.log( $('#dateRange'));
        	console.log("upload");
            Upload.upload({
                url: 'acount/uploadImg',
                data: {
                    file: Upload.dataUrltoBlob(dataUrl, name)
                },
            }).then(function (response) {
                $timeout(function () {
                    $scope.result = response.data;
                    $sessionStorage.connected=response.data;
                    self.connected= response.data;
                });
            }, function (response) {
                if (response.status > 0) $scope.errorMsg = response.status 
                    + ': ' + response.data;
            }, function (evt) {
                $scope.progress = parseInt(100.0 * evt.loaded / evt.total);
            });
        }
/*      form wizard manipulation  */
        self.pageChange = function(state) {
            console.dir(state);
            // state is an object describing page context
            //{state: { previousPage: 2, currentPage: 3 }};
        };
        
	      function register(){
	    	  console.log("about to register");
	    	  self.user.twitterKeyWords=keyWordList;
	    	  AcountService.register(self.user)
	    	  .then(function(res){
	    	   $location.path( "/login" );	  
	    	  },
	    	  function(errorMessage){
	    		  self.message = 'Failed to create user! '+errorMessage;
	    		  self.error=false;
	    	  }
	       );
	      }
	      
	      function addKeyWordToList(word){
	    	 if(!keyWordList.includes(word)){
	    		 keyWordList.push(word);
	    	 }else{
	    		 var index=keyWordList.map( (el) => el.id ).indexOf(word.id);
                 keyWordList.splice(index,1);
                 
	    	 }
	    	 console.log(keyWordList);
	      }
	    function  loadKeyWords(){
	    	console.log($sessionStorage.connected);
	    	  TwitterService.getTwitterKeyWord()
	    	  .then(function(res){
	    	  self.twitterKeWords=res;
	    	  },
	    	  function(errorMessage){
	    		  $scope.message = 'Failed load twitter key words! '+errorMessage;
	    		 
	    	  }
	       );
	    	  
	      }

	    function  loadUserPosts(){
	    	
	    	  PostService.findByUser()
	    	  .then(function(res){
	    	  self.userPosts=res;
	    	  },
	    	  function(errorMessage){
	    		  $scope.message = 'Failed load twitter key words! '+errorMessage;
	    		 
	    	  }
	       );
	    	  
	      }

	    function  getMyPostCommentsPlarity(){
	    	setDataToBarChart("k");
//	    	  PostService.myPostCommentsPlartiy()
//	    	  .then(function(res){
//	    		  setDataToBarChart(data);
//	    	  },
//	    	  function(errorMessage){
//	    		  $scope.message = 'Failed load twitter key words! '+errorMessage;
//	    		 
//	    	  }
//	       );
//	    	  
	      }
	    
	    function setDataToBarChart(data){
	    	
	    	
	  	  Highcharts.chart('barChart', {
	    	    title: {
	    	        text: 'my posts comments sentiment polarity'
	    	    },
	    	    xAxis: {
	    	        categories: ["01/05/2017","02/05/2017","03/05/2017","04/05/2017","05/05/2017"]
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
	    	    series: [
	    	       {
	    	        type: 'column',
	    	        name: 'Neutral',
	    	        data: [4,2,5,2],
	    	        //color: '#646161'
	    	    }, {
	    	        type: 'column',
	    	        name: 'Negative',
	    	        data: [2,4,1,5],
	    	        //color: '#FF976D',
	    	    }, {
	    	        type: 'column',
	    	        name: 'Positive',
	    	        data: [8,6,7,10],
	    	        //color: '#97F88F',
	    	    }, {
	      	        type: 'spline',
	      	        name: 'Average',
	      	        data: [4.66,4,4.33,5.66],
	      	        marker: {
	      	            lineWidth: 2,
	      	            lineColor: Highcharts.getOptions().colors[3],
	      	            fillColor: 'white'
	      	        }},
	    	       {
	    	        type: 'pie',
	    	        name: 'Total ',
	    	        data: [{
	    	            name: 'Neutral',
	    	            y: 13,
	    	            color: Highcharts.getOptions().colors[0] // Jane's color
	    	        }, {
	    	            name: 'Negative',
	    	            y: 12,
	    	            color: Highcharts.getOptions().colors[1] // John's color
	    	        }, {
	    	            name: 'Positive',
	    	            y: 31,
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