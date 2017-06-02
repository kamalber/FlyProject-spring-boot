monApp.controller('TwitterController',
    ['$rootScope','TwitterService','AuthSession','$scope','$location','$http',  function($rootScope,TwitterService,AuthSession,$scope,$location,$http) {
    	var self=this;
		self.keyWord={};
        self.twitterKeyWordList=[];
        loadKeyWords();
        
       
        self.addKeyWords = addKeyWordToList;
        self.submit =submit;
        
		self.error=true;
        self.successMessage = '';
        self.errorMessage = '';
        self.done = false;
        
        self.onlyIntegers = /^\d+$/;
        self.onlyNumbers = /^\d+([,.]\d+)?$/;
/*       -- variable             */
        var keyWordList=[];
        
        function submit() {
            console.log('Submitting');
            if (self.keyWord.id === undefined || self.keyWord.id === null) {
                console.log('Saving New category', self.keyWord );
                create(self.keyWord );
            } else {
                update(self.keyWord , self.keyWord.id);
                console.log('category updated with id ', self.keyWord.id);
            }
        }
       
        
        function create(keyWord ) {
            console.log('About to create category');
            TwitterService.create(keyWord )
                .then(
                    function (keyWordResult) {
                        console.log('category created successfully');
                        self.successMessage = 'category created successfully';
                        self.errorMessage='';
                        self.done = true;
                        self.twitterKeyWordList.unshift(keyWordResult);
                        self.category={};
                        $scope.myForm.$setPristine();
                    },
                    function (errResponse) {
                        console.error('Error while creating category');
                        self.errorMessage = 'Error while creating category: ' + errResponse.data.errorMessage;
                        self.successMessage='';
                    }
                );
        }
        
        function update(keyWord , id){
            console.log('About to update category');
            TwitterService.update(keyWord , id)
                .then(
                    function (response){
                        console.log('category updated successfully');
                        self.successMessage='category updated successfully';
                        self.errorMessage='';
                        var index=self.twitterKeyWordList.findIndex((obj => obj.id == id));
                        self.twitterKeyWordList[index]=keyWord ;
                        self.done = true;
                        $scope.myForm.$setPristine();
                    },
                    function(errResponse){
                        console.error('category while updating category');
                        self.errorMessage='category while updating category '+errResponse.data;
                        self.successMessage='';
                    }
                );
        }
        
        function edit(id) {
            self.successMessage='';
            self.errorMessage='';
            TwitterService.get(id).then(
                function (keyWord ) {
                    self.keyWord  = keyWord ;
                },
                function (errResponse) {
                    console.error('Error while removing category ' + id + ', Error :' + errResponse.data);
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
	    	  self.twitterKeyWordList=res;
	    	  },
	    	  function(errorMessage){
	    		  $scope.message = 'Failed load twitter key words! '+errorMessage;
	    		 
	    	  }
	       );
	    	  
	      }
	      
}]);