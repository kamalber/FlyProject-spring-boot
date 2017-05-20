monApp.factory('StatsService',
		['$localStorage','$http','$q','urls',function($localStorage, $http, $q, urls) {

			var factory = {
				getAnalysedPosts : getAnalysedPosts,
				getAnalysedPostsWithStats : getAnalysedPostsWithStats,
				getCoordonateFromIp : getLatitudeAndLongitudeFromIp,

			};

			return factory;

			function getAnalysedPosts(query, startDate, endDate) {
				console.log('Fetching Items with query :' + query);
				var deferred = $q.defer();
				$http.get(
						urls.POST_SERVICE_API + "statistics/" + query + "/"
								+ startDate + "/" + endDate).then(
						function(response) {
							console.log('Fetched successfully posts');
							deferred.resolve(response.data);
						}, function(errResponse) {
							console.error('Error while loading posts');
							deferred.reject(errResponse);
						});
				return deferred.promise;
			}

			function getAnalysedPostsWithStats(params) {
				console.log('Fetching Items with query :' + params.query);
				var deferred = $q.defer();
				$http.post(urls.POST_SERVICE_API + "statistics", params)
						.then(function(response) {
							console.log('Fetched successfully posts stats ');
							deferred.resolve(response.data);
						}, function(errResponse) {
							console.error('Error while loading posts stats');
							deferred.reject(errResponse);
						});
				return deferred.promise;
			}
			function getLatitudeAndLongitudeFromIp(ip){
				var deferred = $q.defer();
				$http.get("http://ipinfo.io/"+ip)
						.then(function(response) {
							console.log('lang/long  successfully from ip adress');
							console.log(response);
							deferred.resolve(response.data);
						}, function(errResponse) {
							console.error('Error while loading cordonate from ip adress');
							deferred.reject(errResponse);
						});
				return deferred.promise;
			}

		} ]);