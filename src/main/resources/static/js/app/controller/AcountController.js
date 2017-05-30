monApp.controller('AcountController',
    ['$rootScope','AcountService','AuthSession','$scope','$location','$http',  function($rootScope,AcountService,AuthSession,$scope,$location,$http) {
    	var self=this;
		self.user={// this is the parameters object that contain the search criteria
    			'username':'',
    			'password':'',
    	};
		
        self.login = login;
        self.register = register;
        
        
        
        
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
	    	  AcountService.register(user)
	    	  .then(function(res){
	    	   $location.path( "/login" );	  
	    	  },
	    	  function(errorMessage){
	    		  $scope.message = 'Failed to create user!';
	    	  }
	       );
	      }
}]);