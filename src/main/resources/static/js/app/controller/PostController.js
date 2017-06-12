monApp.controller('PostController',
    ['PostService','CommentService','$scope','$sessionStorage',  function(PostService, CommentService,$scope,$sessionStorage) {
    	
        var self = this;
     	
        $scope.post=  $sessionStorage.currentPost;      
        self.postList=getAll();
        self.submitPost = submitPost;
        self.submitComment = submitComment;
        self.getAll = getAll;
        self.create = create;
        self.update = update;
        self.remove = remove;
        self.edit = edit;
        self.reset = reset;

        self.successMessage = '';
        self.errorMessage = '';
        self.done = false;

        self.onlyIntegers = /^\d+$/;
        self.onlyNumbers = /^\d+([,.]\d+)?$/;
        
 

        function submitPost() {
            console.log('Submitting');
            if (self.post.id === undefined || self.post.id === null) {
                console.log('Saving New post', self.post);
                create(self.post);
            } else {
                update(self.post, self.post.id);
                console.log('post updated with id ', self.post.id);
            }
        }
        function submitComment() {
            console.log('Submitting');
          
                console.log('Saving New Comment', self.comment);
                createComment(self.comment);
           
        }
        
        function createComment(comment) {
            console.log('About to create comment');
            comment.post=$scope.post;
            console.log(comment.post);
            CommentService.create(comment)
                .then(
                    function (commentResult) {
                        
                        self.done = true;                     
                        $scope.post.comments.unshift(commentResult);
                        console.log(commentResult);
                        self.comment={};
                        $scope.myForm.$setPristine();
                    },
                    function (errResponse) {
                        console.error('Error while creating comment');
                      
                    }
                );
        }
     
        function create(post) {
            console.log('About to create post');
            PostService.create(post)
                .then(
                    function (postResult) {
                 
                        self.successMessage = 'post created successfully';
                        self.errorMessage='';
                        self.done = true;                     
                        self.postList.unshift(postResult);
                        console.log(post);
                        self.post={};
                        $scope.myForm.$setPristine();
                    },
                    function (errResponse) {
                        console.error('Error while creating post');
                        self.errorMessage = 'Error while creating post: ' + post;
                        self.successMessage='';
                    }
                );
        }


        function update(post, id){
            console.log('About to update user');
            PostService.update(user, id)
                .then(
                    function (response){
                        console.log('post updated successfully');
                        self.successMessage='post updated successfully';
                        self.errorMessage='';
                        self.done = true;
                        $scope.myForm.$setPristine();
                    },
                    function(errResponse){
                        console.error('post while updating User');
                        self.errorMessage='post while updating User '+errResponse.data;
                        self.successMessage='';
                    }
                );
        }

        function getAll(){
        	console.log('About to load all posts');
             PostService.getAll()
                  .then(
                     function(postResults){
                    	self.postList=postResults.slice().reverse();
                         console.log('loading all cateory'+postResults);
                     },
                     function(errResponse){
                         console.error('Error while loading post, Error :'+errResponse.data);
                     }
                 );
        }
        
        function remove(id){
            console.log('About to remove User with id '+id);
            PostService.remove(id)
                .then(
                    function(){
                        console.log('User '+id + ' removed successfully');
                    },
                    function(errResponse){
                        console.error('Error while removing user '+id +', Error :'+errResponse.data);
                    }
                );
        }

        
        function getCurrent(){
        	 console.log('Koooooooooooo ----'+ $sessionStorage.currentPost.id);
             
        	return $sessionStorage.currentPost;
        }
        function edit(Post){
//        delete $sessionStorage.currentPost
            self.successMessage='';
            self.errorMessage='';
            console.log('hiiiiiiiiiiiiiiii');
            $sessionStorage.currentPost =Post;
            
        }
        
        function reset(){
            self.successMessage='';
            self.errorMessage='';
            self.user={};
            $scope.myForm.$setPristine(); //reset Form
        }

   
       
    }


    ]);