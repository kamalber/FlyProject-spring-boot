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
                        console.log(categoryResult);
                        self.successMessage = 'category created successfully';
                        self.errorMessage='';
                        self.done = true;
                        self.categoryList.push(categoryResult);
                        console.log(categoryResult);
                        self.categoryList.unshift(categoryResult);

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
            console.log('About to update category');
            CategoryService.update(category, id)
                .then(
                    function (response){
                        console.log('category updated successfully');
                        self.successMessage='category updated successfully';
                        self.errorMessage='';
                        var index=self.categoryList.findIndex((obj => obj.id == id));
                        self.categoryList[index]=category;
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

        function getAll(){
        	console.log('About to load all categorys');
             CategoryService.getAll()
                  .then(
                     function(cateroyResults){
                    	 self.categoryList=self.cateroyResults!=='' ? cateroyResults : new Array();
                     },
                     function(errResponse){
                         console.error('Error while loading category, Error :'+errResponse.data);
                     }
                 );
        }
        
        function remove(id){
            console.log('About to remove category with id '+id);
            CategoryService.remove(id)
                .then(
                    function(){
                        var index=self.categoryList.map( (el) => el.id ).indexOf(id);
                        self.categoryList.splice(index,1);
                    },
                    function(errResponse){
                        console.error('Error while removing category '+id +', Error :'+errResponse.data);
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
                    console.error('Error while removing category ' + id + ', Error :' + errResponse.data);
                }
            );
        }
        
        function reset(){
            self.successMessage='';
            self.errorMessage='';
            self.category={};
            $scope.myForm.$setPristine(); //reset Form
        }

      
       
    }


    ]);