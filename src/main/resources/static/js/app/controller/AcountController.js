monApp.controller('AcountController',
    ['AcountService','AuthSession','$scope','$http',  function(AcountService,AuthSession,$scope,$http) {
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
	                    	 console.log(res);
	                    	 if (res.authenticated) {
	         					$scope.message = '';
	         					// setting the same header value for all request calling from
	         					// this application
	         					$http.defaults.headers.common['Authorization'] = 'Basic ' + base64Credential;
	         	
	         				} else {
	         					$scope.message = 'Authetication Failed !';
	         				}
	                     },
	                     function(errResponse){
	                    	 $scope.message = 'Authetication Failed !';
	                     }
	                 );
	        }
	        
//		// method for login
//		$scope.login = function() {
//			// creating base64 encoded String from user name and password
//			var base64Credential = btoa($scope.username + ':' + $scope.password);
//
//			// calling GET request for getting the user details
//			$http.get('/user', {
//				headers : {
//					// setting the Authorization Header
//					'Authorization' : 'Basic ' + base64Credential
//				}
//			}).success(function(res) {
//				$scope.password = null;
//				if (res.authenticated) {
//					$scope.message = '';
//					// setting the same header value for all request calling from
//					// this application
//					$http.defaults.headers.common['Authorization'] = 'Basic ' + base64Credential;
//	
//				} else {
//					$scope.message = 'Authetication Failed !';
//				}
//			}).error(function(error) {
//				$scope.message = 'Authetication Failed !';
//			});
//		};
}]);