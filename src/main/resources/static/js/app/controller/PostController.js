monApp.controller('PostController',
    ['PostService','CommentService','$scope','$rootScope','$sessionStorage',  function(PostService, CommentService,$scope,$rootScope,$sessionStorage) {
    	
        var self = this;
     	
        $scope.post=  $sessionStorage.currentPost;      
        self.postList=getAll();
        getCommentsPolarity(getCurrent());
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
        self.chartShowBool=false;
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
                    	getCommentsPolarity(getCurrent());
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
        	self.chartShowBool=false;
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
        	return $sessionStorage.currentPost;
        }
        function edit(Post){
//        delete $sessionStorage.currentPost
            self.successMessage='';
            self.errorMessage='';
           
            $sessionStorage.currentPost =Post;
            getCommentsPolarity(Post);
        }
        
        function reset(){
            self.successMessage='';
            self.errorMessage='';
            self.user={};
            $scope.myForm.$setPristine(); //reset Form
        }

        function getCommentsPolarity(post){
           
            CommentService.getCommentsPolarity(post)
                .then(
                    function(data){
                    	self.chartShowBool=true;
                    	setStatsToBarChart(data);
                    	setStatsToPieChart(data);
                    },
                    function(errResponse){
                        console.error('Error');
                    	self.chartShowBool=false;
                    }
                );
        }
        function setStatsToBarChart(data){
        
        	Highcharts.chart('barChartContainer', {

        		title: {
	                text: ''
	            },

        	    subtitle: {
        	        text: 'Plain'
        	    },

        	    xAxis: {
        	        categories: ['neutral','negative','positive']
        	    },

        	    series: [{
        	        type: 'column',
        	        colorByPoint: true,
        	        data: [data['neutral'], data['negative'], data['positive']],
        	        showInLegend: false
        	    }]

        	});
        }
        function setStatsToPieChart(data){
	    	  // Build the chart
//	    	Highcharts.setOptions({
//	    	    colors:['#058DC7', '#50B432', '#ED561B']
//	    	});
	        Highcharts.chart('pieChartContainer', {
	            chart: {
	                plotBackgroundColor: null,
	                plotBorderWidth: null,
	                plotShadow: false,
	                type: 'pie'
	            },
	            title: {
	                text: 'comments setiment polarity'
	            },
	            tooltip: {
	                pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
	            },
	            plotOptions: {
	                pie: {
	                    allowPointSelect: true,
	                    cursor: 'pointer',
	                    dataLabels: {
	                        enabled: false
	                    },
	                    showInLegend: true
	                }
	            },
	            series: [{
	                name: 'Brands',
	                colorByPoint: true,
	                data: [{
	                    name: 'neutral',
	                    y: data['neutral'],
	                    color: '#058DC7', // Je force la couleur en no
	                
	                    },
	                     { name: 'Positive',
	                    y:  data['positive'],
	                    color: '#50B432', 
	                   },
	                    { name: 'negative',
		                    y: data['negative'],
		                    color: '#ED561B', 
	                    }]
	            }]
	        });
	    }
        function setDataToBarChart(data){
      	  
       }   
        
    }]);