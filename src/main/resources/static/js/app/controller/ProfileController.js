monApp.controller('ProfileController',
    ['$rootScope','TwitterService','AcountService','AuthSession','Upload','$timeout','$scope','$location','$sessionStorage',
     function($rootScope,TwitterService,AcountService,AuthSession,Upload,$timeout,$scope,$location,$sessionStorage) {
    	var self=this;
		self.user={// this is the parameters object that contain the search criteria
    			'username':'',
    			'password':'',
    	};
		self.error=true;
		self.connected=$sessionStorage.connected;
       
        self.addKeyWords = addKeyWordToList;
        self.twitterKeWords=[];
        loadKeyWords();

/*       -- variable             */
        var keyWordList=[];
        
        
        
        
        $scope.uploadImage = function (dataUrl, name) {
        	console.log("upload");
            Upload.upload({
                url: 'acount/uploadImg',
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
	      
}]);