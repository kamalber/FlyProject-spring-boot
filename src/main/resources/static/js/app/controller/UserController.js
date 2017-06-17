monApp.controller('UserController',
    ['UserService', '$scope',  function( UserService, $scope) {

        var self = this;
        self.user = {};
        self.users=[];

        self.submit = submit;
        self.getAllUsers = getAllUsers;
        self.createUser = createUser;
        self.updateUser = updateUser;
        self.removeUser = removeUser;
        self.editUser = editUser;
        self.getUserSentiment=getUserSentiment;
        self.reset = reset;

        self.successMessage = '';
        self.errorMessage = '';
        self.done = false;
        self.error=false;
		self.piChartBool=false;

        self.onlyIntegers = /^\d+$/;
        self.onlyNumbers = /^\d+([,.]\d+)?$/;
        getAllUsers();
        
        
        
   function getUserSentiment(user){
	    	UserService.getUserSentimentStats(user)
	    	.then(function(data){
	    		self.error=false;
	    		self.piChartBool=true;
	    		console.log(data);
	    		setStatsToPieChart(data);
	    	},
	    	function(errorMessage){
	    		console.log("empty");
	    		self.piChartBool=false
	    		self.error=true;
	    		self.infoMessage = 'this user have not post anything since is firts registration';
	    	}
	    	);
	    }
	    function setStatsToPieChart(data){
	    	self.positiveCount=data['positive'];
	    	self.negativeCount=data['negative'];
	    	self.neutralCount=data['neutral'];
	    	  // Build the chart
	    	Highcharts.setOptions({
	    	    colors:['#058DC7', '#50B432', '#ED561B']
	    	});
	        Highcharts.chart('pieChart', {
	            chart: {
	                plotBackgroundColor: null,
	                plotBorderWidth: null,
	                plotShadow: false,
	                type: 'pie'
	            },
	            title: {
	                text: 'user microBlogs sentiments analysis'
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
        
        function submit() {
            console.log('Submitting');
            if (self.user.id === undefined || self.user.id === null) {
                console.log('Saving New User', self.user);
                createUser(self.user);
            } else {
                updateUser(self.user, self.user.id);
                console.log('User updated with id ', self.user.id);
            }
        }

        
        function getAllUsers() {
            console.log('About to create user');
            UserService.getAllUsers()
                .then(
                    function (response) {
                        console.log('User fetched successfully');
                        self.users=response;
                    },
                    function (errResponse) {
                        console.error('Error while creating User');
                        
                    }
                );
        }
        
        function createUser(user) {
            console.log('About to create user');
            UserService.createUser(user)
                .then(
                    function (response) {
                        console.log('User created successfully');
                        self.successMessage = 'User created successfully';
                        self.errorMessage='';
                        self.done = true;
                        self.user={};
                        $scope.myForm.$setPristine();
                    },
                    function (errResponse) {
                        console.error('Error while creating User');
                        self.errorMessage = 'Error while creating User: ' + errResponse.data.errorMessage;
                        self.successMessage='';
                    }
                );
        }


        function updateUser(user, id){
            console.log('About to update user');
            UserService.updateUser(user, id)
                .then(
                    function (response){
                        console.log('User updated successfully');
                        self.successMessage='User updated successfully';
                        self.errorMessage='';
                        self.done = true;
                        $scope.myForm.$setPristine();
                    },
                    function(errResponse){
                        console.error('Error while updating User');
                        self.errorMessage='Error while updating User '+errResponse.data;
                        self.successMessage='';
                    }
                );
        }


        function removeUser(id){
            console.log('About to remove User with id '+id);
            UserService.removeUser(id)
                .then(
                    function(){
                        console.log('User '+id + ' removed successfully');
                    },
                    function(errResponse){
                        console.error('Error while removing user '+id +', Error :'+errResponse.data);
                    }
                );
        }


        

        function editUser(id) {
            self.successMessage='';
            self.errorMessage='';
            UserService.getUser(id).then(
                function (user) {
                    self.user = user;
                },
                function (errResponse) {
                    console.error('Error while removing user ' + id + ', Error :' + errResponse.data);
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