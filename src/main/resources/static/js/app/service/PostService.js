monApp.factory('PostService',
    ['$localStorage', '$http', '$q', 'urls','CrudEngine',
        function ($localStorage, $http, $q, urls,CrudEngine) {
    	
            var factory = {
                getAll: getAll,
                get: get,
                create: create
                
            };
         // setting up the specific rest API url to the crud engine
            CrudEngine.setUrl(urls.POST_SERVICE_API);
            
            return factory;

           

            function getAll(){
                return CrudEngine.getAll();
            }

            function get(id) {
            	return CrudEngine.get(id);
            }

            function create(post) {
            	return CrudEngine.create(post);
            }
            
         

        }
    ]);