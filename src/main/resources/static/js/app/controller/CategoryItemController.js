monApp.controller('CategoryItemController',
    ['CategoryItemService', '$scope',  function(CategoryItemService, $scope) {

        var self = this;

        self.categoryItem = {};
        self.categoryItemList=getAll();
        
        self.submit = submit;
        self.getAll = getAll;
        self.create = create;
        self.update = update;
        self.remove = remove;
        self.addCategorys = addCategorys;
        self.edit = edit;
        self.reset = reset;

        self.successMessage = '';
        self.errorMessage = '';
        self.done = false;

        self.onlyIntegers = /^\d+$/;
        self.onlyNumbers = /^\d+([,.]\d+)?$/;

        function submit() {
            console.log('Submitting');
            if (self.categoryItem.id === undefined || self.categoryItem.id === null) {
                console.log('Saving New category', self.categoryItem);
                create(self.categoryItem);
            } else {
                update(self.categoryItem, self.categoryItem.id);
                console.log('category updated with id ', self.categoryItem.id);
            }
        }

        function create(categoryItem) {
            console.log('About to create category');
            CategoryItemService.create(categoryItem)
                .then(
                    function (categoryResult) {
                        console.log('category created successfully');
                        self.successMessage = 'category created successfully';
                        self.errorMessage='';
                        self.done = true;
                        self.categoryItemList.unshift(categoryResult);
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


        function update(categoryItem, id){
            console.log('About to update category');
            CategoryItemService.update(categoryItem, id)
                .then(
                    function (response){
                        console.log('category updated successfully');
                        self.successMessage='category updated successfully';
                        self.errorMessage='';
                        var index=self.categoryItemList.findIndex((obj => obj.id == id));
                        self.categoryItemList[index]=categoryItem;
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
             CategoryItemService.getAll()
                  .then(
                     function(cateroyResults){
                    	 self.categoryItemList=self.cateroyResults!=='' ? cateroyResults : new Array();
                     },
                     function(errResponse){
                         console.error('Error while loading category, Error :'+errResponse.data);
                     }
                 );
        }
        
        function remove(id){
            console.log('About to remove category with id '+id);
            CategoryItemService.remove(id)
                .then(
                    function(){
                        var index=self.categoryItemList.map( (el) => el.id ).indexOf(id);
                        self.categoryItemList.splice(index,1);
                    },
                    function(errResponse){
                        console.error('Error while removing category '+id +', Error :'+errResponse.data);
                    }
                );
        }

        
        
        function edit(id) {
            self.successMessage='';
            self.errorMessage='';
            CategoryItemService.get(id).then(
                function (categoryItem) {
                    self.categoryItem = categoryItem;
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

        function addCategorys(id){
        	
        }
       
    }


    ]);