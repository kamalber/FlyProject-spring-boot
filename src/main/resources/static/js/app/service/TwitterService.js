monApp.factory('TwitterService',
    ['$localStorage','CrudEngine', '$http', '$q', 'urls',
        function ($localStorage,CrudEngine, $http, $q, urls) {
    	
            var factory = {
            		 getTwitterKeyWord: getTwitterKeyWord,
            		 planScheduledTask: planScheduledTask,
            		 getStats:getStats,
            		 getTotalStats:getTotalStats,
            		 get: getKeyWord,
                     create: createKeyWord,
                     update: updateKeyWord,
                     remove: removeKeyWord,
                     
               
            };
            CrudEngine.setUrl(urls.TWITTER_KEY_WORD_SERVICE_API);
            return factory;
            
            function getTwitterKeyWord(){         
               return CrudEngine.getAll();
            }
     
     
            function getKeyWord(id) {
            	return CrudEngine.get(id);
            }

            function createKeyWord(categoryType) {
            	return CrudEngine.create(categoryType);
            }
            
            function updateKeyWord(categoryType, id) {
               return CrudEngine.update(categoryType,id);
            }

            function removeKeyWord(id) {
            	return CrudEngine.remove(id);
            }
            function getTotalStats(keyWord) {
		
				var deferred = $q.defer();
				$http.post(urls.TWITTER_KEY_WORD_SERVICE_API+ "totalStats", keyWord)
						.then(function(response) {
							console.log('Fetched successfully twitter stats ');
							deferred.resolve(response.data);
						}, function(errResponse) {
							console.error('Error while loading twitter stats');
							deferred.reject(errResponse);
						});
				return deferred.promise;
			}
            
        	function getStats(params) {
				console.log('Fetching Items with query :' + params.query);
				var deferred = $q.defer();
				$http.post(urls.TWITTER_KEY_WORD_SERVICE_API+ "statistics", params)
						.then(function(response) {
							console.log('Fetched successfully posts stats ');
							deferred.resolve(response.data);
						}, function(errResponse) {
							console.error('Error while loading posts stats');
							deferred.reject(errResponse);
						});
				return deferred.promise;
			}
            
           function planScheduledTask(twitterkeyWord){
               var deferred = $q.defer();
               $http.post(urls.TWITTER_KEY_WORD_SERVICE_API+"planTask",twitterkeyWord)
                   .then(
                       function (response) { 
                           deferred.resolve(response);
                       },
                       function (errResponse) {
                           
                           deferred.reject(errResponse);
                       }
                   );
               return deferred.promise;
           
           }
        }
    ]);

