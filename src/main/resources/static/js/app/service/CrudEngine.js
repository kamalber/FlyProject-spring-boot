
// crud engine service that contain crud function to handle data
monApp.factory('CrudEngine',
    ['$localStorage', '$http', '$q',
        function ($localStorage, $http, $q) {
    	
            var factory = {
            	url:"this the url for rest api,",	
                getAll: getAll,
                get: get,
                create: create,
                update: update,
                remove: remove,
                setUrl:setUrl// this method put the url for every specific bean (user,category, ...)
            };

            return factory;

            function setUrl(url){
            	factory.url=url;
            }
            
            function getAll() {
                console.log('Fetching all items');
                var deferred = $q.defer();
                $http.get(factory.url)
                    .then(
                        function (response) {
                            console.log('Fetched successfully all Item');
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while loading Item');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function get(id) {
                console.log('Fetching Item with id :'+id);
                var deferred = $q.defer();
                $http.get(factory.url + id)
                    .then(
                        function (response) {
                            console.log('Fetched successfully categoryTyp with id :'+id);
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while loading Item with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function create(item) {
                console.log('Creating Item');
                var deferred = $q.defer();
                $http.post(factory.url, item)
                    .then(
                        function (response) {
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                           console.error('Error while creating Item : '+errResponse.data.errorMessage);
                           deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function update(item, id) {
                console.log('Updating Item with id '+id);
                var deferred = $q.defer();
                $http.put(factory.url + id, item)
                    .then(
                        function (response) { 
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while updating Item with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function remove(id) {
                console.log('Removing Item with id '+id);
                var deferred = $q.defer();
                $http.delete(factory.url + id)
                    .then(
                        function (response) {
                        	console.log('Item deleted from service :'+id);
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while removing Item with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

        }
    ]);