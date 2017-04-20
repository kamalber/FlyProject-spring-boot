monApp.controller('CategorysController',
    ['CategoryTypeService', '$scope',  function( CategoryTypeService, $scope) {

        var self = this;

        self.categoryType = {};
        self.list=getAll();
        
        self.submit = submit;
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

        function submit() {
            console.log('Submitting');
            if (self.categoryType.id === undefined || self.categoryType.id === null) {
                console.log('Saving New categoryType', self.categoryType);
                create(self.categoryType);
            } else {
                update(self.categoryType, self.categoryType.id);
                console.log('categoryType updated with id ', self.categoryType.id);
            }
        }

        function create(categoryType) {
            console.log('About to create categoryType');
            CategoryTypeService.create(categoryType)
                .then(
                    function (categoryTypeResult) {
                        console.log('categoryType created successfully');
                        self.successMessage = 'categoryType created successfully';
                        self.errorMessage='';
                        self.done = true;
                        self.categoryType={};
                        $scope.myForm.$setPristine();
                    },
                    function (errResponse) {
                        console.error('Error while creating categoryType');
                        self.errorMessage = 'Error while creating categoryType: ' + errResponse.data.errorMessage;
                        self.successMessage='';
                    }
                );
        }


        function update(categoryType, id){
            console.log('About to update user');
            CategoryTypeService.update(user, id)
                .then(
                    function (response){
                        console.log('categoryType updated successfully');
                        self.successMessage='categoryType updated successfully';
                        self.errorMessage='';
                        self.done = true;
                        $scope.myForm.$setPristine();
                    },
                    function(errResponse){
                        console.error('categoryType while updating User');
                        self.errorMessage='categoryType while updating User '+errResponse.data;
                        self.successMessage='';
                    }
                );
        }

        function getAll(){
        	
            return CategoryTypeService.getAllTypeCatgory();
        }
        
        function remove(id){
            console.log('About to remove User with id '+id);
            CategoryTypeService.remove(id)
                .then(
                    function(){
                        console.log('User '+id + ' removed successfully');
                    },
                    function(errResponse){
                        console.error('Error while removing user '+id +', Error :'+errResponse.data);
                    }
                );
        }

        
        
        function edit(id) {
            self.successMessage='';
            self.errorMessage='';
            CategoryTypeService.get(id).then(
                function (categoryType) {
                    self.categoryType = categoryType;
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