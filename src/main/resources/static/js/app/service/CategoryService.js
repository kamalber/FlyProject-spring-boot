monApp.factory('CategoryService',
    ['$localStorage', '$http', '$q', 'urls','CrudEngine',
        function ($localStorage, $http, $q, urls,CrudEngine) {
    	
            var factory = {
                getAll: getAll,
                get: get,
                create: create,
                update: update,
                remove: remove
            };
         // setting up the specific rest API url to the crud engine
            CrudEngine.setUrl(urls.CATEGORY_SERVICE_API);
            
            return factory;

           

            function getAll(){
                return CrudEngine.getAll();
            }

            function get(id) {
            	return CrudEngine.get(id);
            }

            function create(category) {
            	return CrudEngine.create(category);
            }
            
            function update(categoryType, id) {
               return CrudEngine.update(category,id);
            }

            function remove(id) {
            	return CrudEngine.remove(id);
            }

        }
    ]);