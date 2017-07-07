monApp.factory('PostService', [
		'$localStorage',
		'$http',
		'$q',
		'urls',
		'CrudEngine',
		function($localStorage, $http, $q, urls, CrudEngine) {

			var factory = {
				getAll : getAll,
				get : get,
				create : create,
				findByUser : findByUser,
				myPostCommentsPlartiy:getPostCommentsPlarityByUser

			};
			// setting up the specific rest API url to the crud engine
			CrudEngine.setUrl(urls.POST_SERVICE_API);

			return factory;

			function getAll() {
				console.log('About to load all posts2');
				CrudEngine.setUrl(urls.POST_SERVICE_API);
				return CrudEngine.getAll();
			}

			function get(id) {
				CrudEngine.setUrl(urls.POST_SERVICE_API);
				return CrudEngine.get(id);
			}

			function create(post) {
				CrudEngine.setUrl(urls.POST_SERVICE_API);
				return CrudEngine.create(post);
			}

			function findByUser() {
				CrudEngine.setUrl(urls.POST_SERVICE_API);
				var deferred = $q.defer();
				$http.get(urls.POST_SERVICE_API + "findByUser").then(
						function(response) {
							console.log('Fetched successfully  stats ');
							deferred.resolve(response.data);
						}, function(errResponse) {
							console.error('Error while loading  stats');
							deferred.reject(errResponse);
						});
				return deferred.promise;
			}

			function getPostCommentsPlarityByUser() {
				CrudEngine.setUrl(urls.POST_SERVICE_API);
				var deferred = $q.defer();
				$http.get(urls.POST_SERVICE_API + "userStats").then(
						function(response) {
							console.log('Fetched successfully  stats ');
							deferred.resolve(response.data);
						}, function(errResponse) {
							console.error('Error while loading  stats');
							deferred.reject(errResponse);
						});
				return deferred.promise;
			}
		} ]);