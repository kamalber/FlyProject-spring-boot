monApp.controller('AcountController',
    ['$rootScope','TwitterService','AcountService','AuthSession','Upload',
     '$timeout','$scope','$location','$http','$sessionStorage','$window', 
     function($rootScope,TwitterService,AcountService,AuthSession,Upload,
    		 $timeout,$scope,$location,$http,$sessionStorage,$window) {
    	var self=this;
		self.user={// this is the parameters object that contain the search criteria
    			'username':'',
    			'password':'',
    	};
		self.error=true;
        self.login = login;
        
        loadAllCategory();

        self.register = register;
        self.addKeyWords = addKeyWordToList;
        self.twitterKeWords=[];
        self.loadKeyWord=loadKeyWords;
        $rootScope.logOut=logOut;
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
        
        $scope.upload = function (dataUrl, name) {
            Upload.upload({
                url: 'acount/upload',
                data: {
                    file: Upload.dataUrltoBlob(dataUrl, name)
                },
            }).then(function (response) {
                $timeout(function () {
                    $scope.result = response.data;
                });
            }, function (response) {
                if (response.status > 0) $scope.errorMsg = response.status 
                    + ': ' + response.data;
            }, function (evt) {
                $scope.progress = parseInt(100.0 * evt.loaded / evt.total);
            });
        }
        
        
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
	         					$rootScope.connected=res.principal;
	         					console.log(AuthSession.connected);
	         					$sessionStorage.connected=res.principal;
	         					// setting the same header value for all request calling from
	         					// this application
	         					$http.defaults.headers.common['Authorization'] = 'Basic ' + base64Credential;
	         					if($sessionStorage.connected.role=="USER"){
	         						$location.path( "/posts");
	         					}else{
	         						$location.path( "/user");
	         					}
	         					
	         				} else {
	         					$scope.message = 'Authetication Failed !';
	         				}
	                     },
	                     function(errResponse){
	                    	 $scope.message = 'Authetication Failed !';
	                     }
	                 );
	        }
	      
	       function logOut(){
	    	   console.log("log out");
	    	  $sessionStorage.connected=null;
	    	  $location.path( "/login" );
	    	  $window.location.reload();
	      }
	      function registerRedirect(){
	    	  $location.path( "/register" );	
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
	    function  loadKeyWords(Category){
	    	  TwitterService.findByCategory(Category)
	    	  .then(function(res){
	    	  self.twitterKeWords=res;
	    	  console.log(res);
	    	  },
	    	  function(errorMessage){
	    		  $scope.message = 'Failed load twitter key words! '+errorMessage;
	    		  self.twitterKeWords=null;
	    	  }
	       );
	    	  
	      }
	   function loadAllCategory(){
		   TwitterService.loadAllCategory()
	    	  .then(function(res){
	    	  self.categorys=res;
	    	  },
	    	  function(errorMessage){
	    		  $scope.message = 'Failed load twitter key words! '+errorMessage;
	    	  }
	       );
	    }
}]);