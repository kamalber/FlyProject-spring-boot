monApp.factory('CategoryTypeService',
    ['$localStorage', '$http', '$q', 'urls',
        function ($localStorage, $http, $q, urls) {
    	
            var factory = {
                loadAllTypeCatgory: loadAll,
                getAllTypeCatgory: getAll,
                get: get,
                create: create,
                update: update,
                remove: remove
            };

            return factory;

            function loadAll() {
                console.log('Fetching all categoryTypes');
                var deferred = $q.defer();
                $http.get(urls.TYPECATEGORY_SERVICE_API)
                    .then(
                        function (response) {
                            console.log('Fetched successfully all categoryType');
                            $localStorage.categoryTypes = response.data;
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading categoryType');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function getAll(){
            	loadAll();
                return $localStorage.categoryTypes;
            }

            function get(id) {
                console.log('Fetching CategoryType with id :'+id);
                var deferred = $q.defer();
                $http.get(urls.TYPECATEGORY_SERVICE_API + id)
                    .then(
                        function (response) {
                            console.log('Fetched successfully categoryTyp with id :'+id);
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while loading CategoryType with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function create(categoryType) {
                console.log('Creating CategoryType');
               
                var deferred = $q.defer();
                console.log(urls.TYPECATEGORY_SERVICE_API);
                console.log(categoryType);
                $http.post(urls.TYPECATEGORY_SERVICE_API, categoryType)
                    .then(
                        function (response) {
                        	$localStorage.categoryTypes.push(response.data);
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                           console.error('Error while creating CategoryType : '+errResponse.data.errorMessage);
                           deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function update(categoryType, id) {
                console.log('Updating CategoryType with id '+id);
                var deferred = $q.defer();
                $http.put(urls.TYPECATEGORY_SERVICE_API + id, categoryType)
                    .then(
                        function (response) {
                           
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while updating CategoryType with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function remove(id) {
                console.log('Removing CategoryType with id '+id);
                var deferred = $q.defer();
                $http.delete(urls.TYPECATEGORY_SERVICE_API + id)
                    .then(
                        function (response) {
                        	$localStorage.categoryTypes.remove(response.data)
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while removing CategoryType with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

        }
    ]);