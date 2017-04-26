monApp.factory('UserService',
    ['$localStorage', '$http', '$q', 'urls',
        function ($localStorage, $http, $q, urls) {

            var factory = {
               getPosts: getPosts,
              
            };

            return factory;

            function getPosts(query){
            	 console.log('Fetching Item with id :'+id);
                 var deferred = $q.defer();
                 $http.get(urls.POST_SERVICE_API + id)
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
             
            
            } 
    ]);