monApp.factory('CommentService',
    ['$localStorage', '$http', '$q', 'urls','CrudEngine',
        function ($localStorage, $http, $q, urls,CrudEngine) {
    	
            var factory = {
                getAll: getAll,
                get: get,
                create: create
                
            };
         // setting up the specific rest API url to the crud engine
//            CrudEngine.setUrl(urls.COMMENT_SERVICE_API);
            
            return factory;

           

            function getAll(){
                return CrudEngine.getAll();
            }

            function get(id) {
            	return CrudEngine.get(id);
            }

            function create(comment) {
            	CrudEngine.setUrl(urls.COMMENT_SERVICE_API);
            	return CrudEngine.create(comment);
            }
            
         

        }
    ]);