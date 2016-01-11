( function(){
    'use strict';
    
    angular
        .module('cryptoClient')
        .controller('groupController', [ '$scope', '$log', '$resource', groupController] )
        .controller('newgroupController',  [ '$scope', '$log','$stateParams', newgroupController ] )
        .controller('findgroupController', [ '$scope', '$log','$stateParams', findgroupController ] )
        .controller('groupdetailController', [ '$scope', '$log','$stateParams', groupdetailController ] );

        function groupController( $scope, $log, $resource ) {

            var Group  = $resource(
                '/groups', 
                {}, 
                {'get' : { method : 'GET', isArray : true }}
            );

            Group.get()
                .$promise.then(function(groups){ $scope.groups = groups; });
            
            $scope.remove = function( group,  index ){
                $scope.groups.splice( index , 1 );
            };


        }


         function newgroupController( $scope, $log ){
            var Group  = $resource(
                '/groups', 
                {}, 
                {'post' : { method : 'GET', isArray : true }}
            );

         }

         function findgroupController( $scope, $log ){
             $log.log("here sind wir in find group controller");
         }

        function groupdetailController( $scope, $log, $stateParams ){
            $scope.detailedGroup = $scope.groups[ $stateParams.groupId ];
            $log.log ( $scope.detailedGroup );
        }


})();
