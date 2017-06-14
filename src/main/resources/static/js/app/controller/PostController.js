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
        	 console.log('Koooooooooooo ----'+ $sessionStorage.currentPost.id);
             
        	return $sessionStorage.currentPost;
        }
        function edit(Post){
//        delete $sessionStorage.currentPost
            self.successMessage='';
            self.errorMessage='';
            console.log('hiiiiiiiiiiiiiiii');
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
        	Highcharts.setOptions({
	    	    colors:['#058DC7', '#50B432', '#ED561B']
	    	});
        	Highcharts.chart('barChartContainer', {

        	    title: {
        	        text: 'Chart.update'
        	    },

        	    subtitle: {
        	        text: 'Plain'
        	    },

        	    xAxis: {
        	        categories: ['neutral', 'positive', 'negative']
        	    },

        	    series: [{
        	        type: 'column',
        	        colorByPoint: true,
        	        data: [data['neutral'], data['positive'], data['negative']],
        	        showInLegend: false
        	    }]

        	});
        }
        function setStatsToPieChart(data){
	    	  // Build the chart
	    	Highcharts.setOptions({
	    	    colors:['#058DC7', '#50B432', '#ED561B']
	    	});
	        Highcharts.chart('pieChartContainer', {
	            chart: {
	                plotBackgroundColor: null,
	                plotBorderWidth: null,
	                plotShadow: false,
	                type: 'pie'
	            },
	            title: {
	                text: 'sentiments analysis '
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
	                    y: data['neutral']
	                    },
	                     { name: 'Positive',
	                    y:  data['positive']
	                   },
	                    { name: 'negative',
		                    y: data['negative']
	                    }]
	            }]
	        });
	    }
        function setDataToBarChart(data){
      	  
       }   
        
    }]);