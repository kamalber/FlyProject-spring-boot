monApp.factory('StatsService',
    ['$localStorage', '$http', '$q', 'urls',
        function ($localStorage, $http, $q, urls) {

            var factory = {
            		getAnalysedPosts: getAnalysedPosts,
              
            };

            return factory;

            function getAnalysedPosts(query,startDate,endDate){
            	 console.log('Fetching Items with query :'+query);
                 var deferred = $q.defer();
                 $http.get(urls.POST_SERVICE_API +"/statistics/"+query+"/"+startDate+"/"+endDate)
                     .then(
                         function (response) {
                             console.log('Fetched successfully posts' );
                             deferred.resolve(response.data);
                         },
                         function (errResponse) {
                             console.error('Error while loading posts');
                             deferred.reject(errResponse);
                         }
                     );
                 return deferred.promise;
            }
             
            
            } 
    ]);