monApp.factory('TwitterService',
    ['$localStorage', '$http', '$q', 'urls',
        function ($localStorage, $http, $q, urls) {
    	
            var factory = {
            		getTwitterKeyWord: getTwitterKeyWord,
               
            };
            
            return factory;
            function getTwitterKeyWord(){         
                var deferred = $q.defer();
                $http.get("/twitter/keyWords")
                    .then(
                        function (response) {
                            console.log('list loaded success');
                          
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while loeading key words');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }
     
     
     

        }
    ]);