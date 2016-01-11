(function(){
    'use strict';

    angular
        .module('cryptoClient')
        .controller('userController', ['$scope', '$log', 'api_http', userController] ) 
        .controller('userControllerDetail', ['$scope', '$log', '$stateParams', 'api_http', userControllerDetail ]);

        function userController( $scope, $log, api_http ){
            $scope.users = { };
            api_http.GET('http://localhost:8080/users', function( err, data ){
                        $scope.users = data.data; 
                        $log.log( data.data );
            });

            $scope.remove = function( user, index ){
                $scope.users.splice( index, 1 );
                    api_http.DELETE( "/users/" + index, function( err ){
                        if( !err ){
                            $log.log( "perfect user deleted" );
                        }
                    })
            };
        }



        function userControllerDetail( $scope , $log, $stateParams, api_http ){
            api_http.GET('/users/' + $stateParams.userId, function( err, data ){
                    $scope.detailedUser = data.data;
            })
        }

})();
