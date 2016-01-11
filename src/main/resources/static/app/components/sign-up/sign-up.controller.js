( function(){
    'use strict';
    
    angular
        .module('cryptoClient')
        .controller('signupController', [ '$scope', '$rootScope', '$location', '$window', 'api_http' , '$log', signupController ] );

        function signupController( $scope, $rootScope, $location, $window, api_http, $log ) {
            $scope.credentials = null;

            $scope.register = function( credentials ){
                api_http.POST( '/users', credentials, function( status ){
                    $log.log( status );
                });
            };

        }

})();
