monApp.factory('CommentService',
    ['$localStorage', '$http', '$q', 'urls','CrudEngine',
        function ($localStorage, $http, $q, urls,CrudEngine) {
    	
            var factory = {
                getAll: getAll,
                getCommentsPolarity:getCommentsPolarity,
                getCommentsPolarityByDate:getCommentsPolarityByDate,
                getCommentsByPost:getCommentsByPost,
                getCommentsTotalPolarity:getCommentsTotalPolarity,
                get: get,
                create: create
                
            };
         // setting up the specific rest API url to the crud engine
//            CrudEngine.setUrl(urls.COMMENT_SERVICE_API);
            
            return factory;

           

            function getAll(){
            	CrudEngine.setUrl(urls.COMMENT_SERVICE_API);
                return CrudEngine.getAll();
            }

            function get(id) {
            	CrudEngine.setUrl(urls.COMMENT_SERVICE_API);
            	return CrudEngine.get(id);
            }

            function create(comment) {
            	CrudEngine.setUrl(urls.COMMENT_SERVICE_API);
            	return CrudEngine.create(comment);
            }
            
            function getCommentsByPost(post){
            	var deferred = $q.defer();
            	$http.post(urls.COMMENT_SERVICE_API+ "commentsByPost", post)
            			.then(function(response) {
            				
            				deferred.resolve(response.data);
            			}, function(errResponse) {
            			
            				deferred.reject(errResponse);
            			});
            	return deferred.promise;
            }
            
            function getCommentsPolarity(post){
            	var deferred = $q.defer();
            	$http.post(urls.COMMENT_SERVICE_API+ "commentPolarity", post)
            			.then(function(response) {
            				console.log('Fetched successfully  stats ');
            				deferred.resolve(response.data);
            			}, function(errResponse) {
            				console.error('Error while loading  stats');
            				deferred.reject(errResponse);
            			});
            	return deferred.promise;
            }
                    
            function getCommentsPolarityByDate(params){
            	var deferred = $q.defer();
            	$http.post(urls.COMMENT_SERVICE_API+ "commentPolarityByDate", params)
            			.then(function(response) {
            				console.log('Fetched successfully  stats ');
            				deferred.resolve(response.data);
            			}, function(errResponse) {
            				console.error('Error while loading  stats');
            				deferred.reject(errResponse);
            			});
            	return deferred.promise;
            }
            
            function getCommentsTotalPolarity(){
            	var deferred = $q.defer();
            	$http.post(urls.COMMENT_SERVICE_API+ "commentTotalPolarity")
            			.then(function(response) {
            				console.log('Fetched successfully user post comments ');
            				deferred.resolve(response.data);
            			}, function(errResponse) {
            				console.error('Error while  user post comments ');
            				deferred.reject(errResponse);
            			});
            	return deferred.promise;
            }
        }
    ]);