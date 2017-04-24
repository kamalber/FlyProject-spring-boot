monApp.controller('CategoryTypeController',
    ['CategoryTypeService', '$scope',  function( CategoryTypeService, $scope) {

        var self = this;

        self.categoryType = {};
        self.categoryTypeList=getAll();
        
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
                        self.categoryTypeList.unshift(categoryTypeResult);
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
            console.log('About to update CategoryType');
            CategoryTypeService.update(categoryType, id)
                .then(
                    function (response){
                        console.log('categoryType updated successfully');
                        self.successMessage='categoryType updated successfully';
                        self.errorMessage='';
                        var index=self.categoryTypeList.findIndex((obj => obj.id == id));
                        self.categoryTypeList[index]=categoryType;
                        self.done = true;
                        $scope.myForm.$setPristine();
                    },
                    function(errResponse){
                        console.error('categoryType while updating CategoryType');
                        self.errorMessage='categoryType while updating CategoryType '+errResponse.data;
                        self.successMessage='';
                    }
                );
        }

        function getAll(){
        	self.categoryTypeList=new Array();
        	console.log('About to load all categorys');
             CategoryTypeService.getAll()
                  .then(
                     function(categoyrResults){
                    	self.categoryTypeList=self.categoyrResults!=='' ? categoyrResults : new Array();
                     },
                     function(errResponse){
                         console.error('Error while loading category, Error :'+errResponse.data);
                     }
                 );
        }
        
        function remove(id){
            console.log('About to remove CategoryType with id '+id);
            CategoryTypeService.remove(id)
                .then(
                    function(){
                    	var index=self.categoryTypeList.map( (el) => el.id ).indexOf(id);
                        self.categoryTypeList.splice(index,1);
                    },
                    function(errResponse){
                        console.error('Error while removing CategoryType '+id +', Error :'+errResponse.data);
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
                    console.error('Error while removing CategoryType ' + id + ', Error :' + errResponse.data);
                }
            );
        }
        
        function reset(){
            self.successMessage='';
            self.errorMessage='';
            self.categoryType={};
            $scope.myForm.$setPristine(); //reset Form
        }

   
       
    }


    ]);