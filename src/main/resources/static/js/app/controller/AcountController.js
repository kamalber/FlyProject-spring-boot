monApp.controller('AcountController',
    ['$rootScope','TwitterService','AcountService','AuthSession','$scope','$location','$http',  function($rootScope,TwitterService,AcountService,AuthSession,$scope,$location,$http) {
    	var self=this;
		self.user={// this is the parameters object that contain the search criteria
    			'username':'',
    			'password':'',
    	};
		self.error=true;
        self.login = login;
        self.register = register;
        self.addKeyWords = addKeyWordToList;
        self.twitterKeWords=[];
        loadKeyWords();

/*       -- variable             */
        var keyWordList=[];
        
/*      form wizard manipulation  */
        self.pageChange = function(state) {
            console.dir(state);
            // state is an object describing page context
            //{state: { previousPage: 2, currentPage: 3 }};
        };
        self.save = function() {
            console.log('Got Save');
            
        };
        self.cancel = function() {
            console.log('Got Cancel');
        };
        
	      function login(){
	        	console.log('About to log in');
	        	// creating base64 encoded String from user name and password
				var base64Credential = btoa($scope.username + ':' + $scope.password);
	        	AcountService.login(base64Credential)
	                  .then(
	                     function(res){
	                    	 if (res.authenticated) {
	         					$scope.message = '';
	         					AuthSession.connected=res;
	         					$rootScope.user=res.principal;
	         					console.log(AuthSession.connected);
	         					// setting the same header value for all request calling from
	         					// this application
	         					$http.defaults.headers.common['Authorization'] = 'Basic ' + base64Credential;
	         					$location.path( "/posts" );
	         				} else {
	         					$scope.message = 'Authetication Failed !';
	         				}
	                     },
	                     function(errResponse){
	                    	 $scope.message = 'Authetication Failed !';
	                     }
	                 );
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
	    	  TwitterService.getTwitterKeyWord()
	    	  .then(function(res){
	    	  self.twitterKeWords=res;
	    	  },
	    	  function(errorMessage){
	    		  $scope.message = 'Failed load twitter key words! '+errorMessage;
	    		 
	    	  }
	       );
	    	  
	      }
	      
}]);