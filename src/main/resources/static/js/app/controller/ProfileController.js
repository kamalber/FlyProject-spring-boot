monApp.controller('ProfileController',
    ['$rootScope','TwitterService','PostService','CommentService','AcountService','AuthSession','Upload','$timeout','$scope','$location','$sessionStorage',
     function($rootScope,TwitterService,PostService,CommentService,AcountService,
    		 AuthSession,Upload,$timeout,$scope,$location,$sessionStorage) {
    	var self=this;
		self.user={// this is the parameters object that contain the search criteria
    			'username':'',
    			'password':'',
    	};
		
		self.error=true;
		self.dateRange={};
		self.searchParams={};
		initCommentsGraphe();
		self.postComments=[];
		self.connected=$sessionStorage.connected;
        self.addKeyWords = addKeyWordToList;
        self.twitterKeWords=[];
        self.userPosts=[];
        loadUserPosts();
        loadKeyWords();
        self.search=getMyPostCommentsPolarity;
        intitDate();
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
        
         function getCommentPolarity(){
        	 
         }
        function intitDate() {
            var start = moment().subtract(29, 'days');
            var end = moment();

            function cb(start, end) {
                $('#reportrange span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
                self.searchParams.startDate=start.format('DD/MM/YYYY');
                self.searchParams.endDate=end.format('DD/MM/YYYY');
              
            }

            $('#reportrange').daterangepicker({
                startDate: start,
                endDate: end,
                ranges: {
                   'Today': [moment(), moment()],
                   'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
                   'Last 7 Days': [moment().subtract(6, 'days'), moment()],
                   'Last 30 Days': [moment().subtract(29, 'days'), moment()],
                   'This Month': [moment().startOf('month'), moment().endOf('month')],
                   'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
                }
            }, cb);
            cb(start, end);
           
        }
        
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

	    function initCommentsGraphe(){
	    	CommentService.getCommentsTotalPolarity()
	    	  .then(function(data){
	    		  console.log(data);
	    		  setDataToBarChart(data);
	    	  },
	    	  function(errorMessage){
	    		  $scope.message = 'Failed load comments stats ! '+errorMessage;
	    		  self.postComments=null;
	    		 
	    	  }
	       );	
	    }
	    
function  getCommentsForPost(post){
	    	CommentService.getCommentsByPost(post)
	    	  .then(function(data){
	    		  self.postComments=data;
	    		  console.log(data);
	    	  },
	    	  function(errorMessage){
	    		  $scope.message = 'Failed load comments stats ! '+errorMessage;
	    		  self.postComments=null;
	    		 
	    	  }
	       );
	    	  
	      }
	    function  getMyPostCommentsPolarity(){	
	    	CommentService.getCommentsPolarityByDate(self.searchParams)
	    	  .then(function(data){
	    		  setDataToBarChart(data);
	    		  getCommentsForPost(self.searchParams.post);
	    	  },
	    	  function(errorMessage){
	    		  $scope.message = 'Failed load comments stats ! '+errorMessage;
	    		 
	    	  }
	       );
	    	  
	      }
	    
	    function setDataToBarChart(data){
	  	  Highcharts.chart('barChart', {
	    	    title: {
	    	        text: 'my posts comments sentiment polarity'
	    	    },
	    	    xAxis: {
	    	        categories: data.labelSeries
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
	    	        data: data.neutralDataCount,
	    	        //color: '#646161'
	    	    }, {
	    	        type: 'column',
	    	        name: 'Negative',
	    	        data: data.negativeDataCount,
	    	        //color: '#FF976D',
	    	    }, {
	    	        type: 'column',
	    	        name: 'Positive',
	    	        data: data.positiveDataCount,
	    	        //color: '#97F88F',
	    	    }, {
	      	        type: 'spline',
	      	        name: 'Average',
	      	        data: data.averageDataCount,
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
	    	            y: data.neutralCount,
	    	            color: Highcharts.getOptions().colors[0] // Jane's color
	    	        }, {
	    	            name: 'Negative',
	    	            y: data.negativeCount,
	    	            color: Highcharts.getOptions().colors[1] // John's color
	    	        }, {
	    	            name: 'Positive',
	    	            y: data.positiveCount,
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