
( function(){
    'use strict';
    
    angular
        .module('cryptoClient')
        .controller('documentController', [ '$scope', '$resource',  documentController] );


        function documentController( $scope, $resource ) {

            var Document  = $resource(
                '/groups/:groupId/documents', 
                {}, 
                {'get' : { method : 'GET', isArray : true }}
            );


            Document.get()
            .$promise.then(function(documents){
                $scope.documents = documents;
            });

        }



})();
