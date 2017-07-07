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
                     loadAllCategory:loadAllCategory,
                     findByCategory:findByCategory
                     
               
            };
            CrudEngine.setUrl(urls.TWITTER_KEY_WORD_SERVICE_API);
            return factory;
            
            function getTwitterKeyWord(){         
            	 CrudEngine.setUrl(urls.TWITTER_KEY_WORD_SERVICE_API);
               return CrudEngine.getAll();
            }
     
     
            function getKeyWord(id) {
            	 CrudEngine.setUrl(urls.TWITTER_KEY_WORD_SERVICE_API);
            	return CrudEngine.get(id);
            }

            function createKeyWord(categoryType) {
            	 CrudEngine.setUrl(urls.TWITTER_KEY_WORD_SERVICE_API);
            	return CrudEngine.create(categoryType);
            }
            
            function updateKeyWord(categoryType, id) {
            	 CrudEngine.setUrl(urls.TWITTER_KEY_WORD_SERVICE_API);
               return CrudEngine.update(categoryType,id);
            }

            function removeKeyWord(id) {
            	 CrudEngine.setUrl(urls.TWITTER_KEY_WORD_SERVICE_API);
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
                           deferred.resolve(response.data);
                       },
                       function (errResponse) {
                           
                           deferred.reject(errResponse);
                       }
                   );
               return deferred.promise;
           
           }
           function findByCategory(category){
               var deferred = $q.defer();
               $http.post(urls.TWITTER_KEY_WORD_SERVICE_API+"findByCategory",category)
                   .then(
                       function (response) { 
                           deferred.resolve(response.data);
                       },
                       function (errResponse) {
                           
                           deferred.reject(errResponse);
                       }
                   );
               return deferred.promise;
           
           }
           function loadAllCategory(){
               var deferred = $q.defer();
               $http.get(urls.CATEGORY_SERVICE_API)
                   .then(
                       function (response) { 
                           deferred.resolve(response.data);
                       },
                       function (errResponse) {
                           
                           deferred.reject(errResponse);
                       }
                   );
               return deferred.promise;
           
           }
        }
    ]);

