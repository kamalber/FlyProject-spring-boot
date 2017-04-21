monApp.factory('CategoryTypeService',
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
            CrudEngine.setUrl(urls.TYPECATEGORY_SERVICE_API);
            
            return factory;

        

            function getAll(){
                return CrudEngine.getAll();
            }

            function get(id) {
            	return CrudEngine.get(id);
            }

            function create(categoryType) {
            	return CrudEngine.create(categoryType);
            }
            
            function update(categoryType, id) {
               return CrudEngine.update(categoryType,id);
            }

            function remove(id) {
            	CrudEngine.remove(id);
            }

        }
    ]);