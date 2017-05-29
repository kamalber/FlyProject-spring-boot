monApp.factory('AcountService',
    ['$localStorage', '$http', '$q', 'urls',
        function ($localStorage, $http, $q, urls) {
    	
            var factory = {
                login: login,
    
            };    
            return factory;
            function login(base64Credential){
                     console.log('user login'+base64Credential);
                     
                     var deferred = $q.defer();
                     $http.get("/acount/login", {
             			headers : {
            				// setting the Authorization Header
            				'Authorization' : 'Basic ' +base64Credential
            			}})
                         .then(
                             function (response) {
                                 console.log('user loged successfully '+response);
                               
                                 deferred.resolve(response.data);
                             },
                             function (errResponse) {
                                 console.error('Error while user login'+errResponse);
                                 deferred.reject(errResponse);
                             }
                         );
                     return deferred.promise;
                 }
          

        }
    ]);