monApp.factory('CompareService',
    ['$localStorage', '$http', '$q', 'urls','CrudEngine',
        function ($localStorage, $http, $q, urls,CrudEngine) {
    	
            var factory = {
           getCompareTols: getCompareTols,    
            };
            
         // setting up the specific rest API url to the crud engine
            CrudEngine.setUrl(urls.POST_SERVICE_API);
            
            return factory;

           

      
         
function getCompareTols(){
	var deferred = $q.defer();
	$http.get(urls.ACOUNT_SERVICE_API+ "compareTols")
			.then(function(response) {
				console.log('Fetched successfully  stats ');
				deferred.resolve(response.data);
			}, function(errResponse) {
				console.error('Error while loading  stats');
				deferred.reject(errResponse);
			});
	return deferred.promise;
}
        }
    ]);