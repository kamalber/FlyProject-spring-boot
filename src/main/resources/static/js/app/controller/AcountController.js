monApp.controller('AcountController',
    ['AcountService','AuthSession','$scope','$location','$http',  function(AcountService,AuthSession,$scope,$location,$http) {
    	var self=this;
		self.user={// this is the parameters object that contain the search criteria
    			'username':'',
    			'password':'',
    	};
        self.login = login;
       
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
}]);