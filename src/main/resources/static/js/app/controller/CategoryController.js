monApp.controller('CategoryController',
    ['CategoryService', '$scope',  function(CategoryService, $scope) {

        var self = this;

        self.category = {};
        self.categoryList=getAll();
        
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
            if (self.category.id === undefined || self.category.id === null) {
                console.log('Saving New category', self.category);
                create(self.category);
            } else {
                update(self.category, self.category.id);
                console.log('category updated with id ', self.category.id);
            }
        }

        function create(category) {
            console.log('About to create category');
            CategoryService.create(category)
                .then(
                    function (categoryResult) {
                        console.log('category created successfully');
                        self.successMessage = 'category created successfully';
                        self.errorMessage='';
                        self.done = true;
                        self.categoryList.push(categoryResult);
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


        function update(category, id){
            console.log('About to update user');
            CategoryService.update(user, id)
                .then(
                    function (response){
                        console.log('category updated successfully');
                        self.successMessage='category updated successfully';
                        self.errorMessage='';
                        self.done = true;
                        $scope.myForm.$setPristine();
                    },
                    function(errResponse){
                        console.error('category while updating User');
                        self.errorMessage='category while updating User '+errResponse.data;
                        self.successMessage='';
                    }
                );
        }

        function getAll(){
        	console.log('About to load all categorys');
             CategoryService.getAll()
                  .then(
                     function(cateroyResults){
                    	self.categoryList=cateroyResults;
                         console.log('loading all cateory'+cateroyResults);
                     },
                     function(errResponse){
                         console.error('Error while loading category, Error :'+errResponse.data);
                     }
                 );
        }
        
        function remove(id){
            console.log('About to remove User with id '+id);
            CategoryService.remove(id)
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
            CategoryService.get(id).then(
                function (category) {
                    self.category = category;
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