monApp.controller('CommentController',
    ['CommentService', '$scope','$sessionStorage',  function(CommentService, $scope,$sessionStorage) {
    	
        var self = this;
     	
        
        self.commentList=getAll();
       
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

       
        function submitComment() {
            console.log('Submitting');
          
                console.log('Saving New Comment', self.comment);
                create(self.self.comment);
           
        }
     
        function create(comment) {
            console.log('About to create comment');
            CommentService.create(comment)
                .then(
                    function (commentResult) {
                 
                        self.successMessage = 'comment created successfully';
                        self.errorMessage='';
                        self.done = true;                     
                        self.commentList.unshift(commentResult);
                        console.log(comment);
                        self.comment={};
                        $scope.myForm.$setPristine();
                    },
                    function (errResponse) {
                        console.error('Error while creating comment');
                        self.errorMessage = 'Error while creating comment: ' + comment;
                        self.successMessage='';
                    }
                );
        }


        function update(comment, id){
            console.log('About to update user');
            CommentService.update(user, id)
                .then(
                    function (response){
                        console.log('comment updated successfully');
                        self.successMessage='comment updated successfully';
                        self.errorMessage='';
                        self.done = true;
                        $scope.myForm.$setPristine();
                    },
                    function(errResponse){
                        console.error('comment while updating User');
                        self.errorMessage='comment while updating User '+errResponse.data;
                        self.successMessage='';
                    }
                );
        }

        function getAll(){
        	console.log('About to load all comments');
             CommentService.getAll()
                  .then(
                     function(commentResults){
                    	self.commentList=commentResults.slice().reverse();
                         console.log('loading all cateory'+commentResults);
                     },
                     function(errResponse){
                         console.error('Error while loading comment, Error :'+errResponse.data);
                     }
                 );
        }
        
        function remove(id){
            console.log('About to remove User with id '+id);
            CommentService.remove(id)
                .then(
                    function(){
                        console.log('User '+id + ' removed successfully');
                    },
                    function(errResponse){
                        console.error('Error while removing user '+id +', Error :'+errResponse.data);
                    }
                );
        }
     
        function reset(){
            self.successMessage='';
            self.errorMessage='';
            self.user={};
            $scope.myForm.$setPristine(); //reset Form
        }

   
       
    }


    ]);