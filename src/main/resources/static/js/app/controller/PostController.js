monApp.controller('PostController',
    ['PostService', '$scope',  function( PostService, $scope) {

        var self = this;
        self.post = {};
        self.posts=[];      
        self.getAllPosts = getAllPosts;
       
      

        
        function getAllPosts(){
            return PostService.getAllPosts();
        }

   
     
    }


    ]);