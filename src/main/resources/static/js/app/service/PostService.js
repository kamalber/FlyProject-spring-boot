monApp.factory('PostService',
    ['$localStorage', '$http', '$q', 'urls',
        function ($localStorage, $http, $q, urls) {

            var factory = {
                loadAllPosts: loadAll,
                getAllPosts: getAll
               };

            return factory;

            function loadAll() {
                console.log('Fetching all posts');
                var deferred = $q.defer();
                $http.get(urls.POST_SERVICE_API)
                    .then(
                        function (response) {
                            console.log('Fetched successfully all posts');
                            $localStorage.posts = response.data;
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading posts');
                            deferred.reject(errResponse);
                        }
                    );
                return response.data;
            }

            function getAll(){
            	
            	return $localStorage.posts;
            }

       

        }
    ]);