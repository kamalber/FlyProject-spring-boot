monApp.factory('UserService',
    ['$localStorage', '$http', '$q', 'urls','CrudEngine',
        function ($localStorage, $http, $q, urls,CrudEngine) {

            var factory = {
                getAllUsers: getAllUsers,
                getUser: getUser,
                createUser: createUser,
                updateUser: updateUser,
                removeUser: removeUser,
                getUserSentimentStats:getUserSentimentStats
            };
            CrudEngine.setUrl(urls.USER_SERVICE_API);
            return factory;

            function getAllUsers() {
            	  CrudEngine.setUrl(urls.USER_SERVICE_API);
            	  return CrudEngine.getAll();
            }
            function getUser(id) {
            	  return CrudEngine.get(id);
            }

            function createUser(user) {
            	  return CrudEngine.create(user);
            }

            function updateUser(user, id) {
            	  return CrudEngine.update(user,id);
            }

            function removeUser(id) {
            	  return CrudEngine.remove(id);
            }
            function getUserSentimentStats(user) {
        		
				var deferred = $q.defer();
				$http.post(urls.USER_SERVICE_API+ "userSentiment", user)
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