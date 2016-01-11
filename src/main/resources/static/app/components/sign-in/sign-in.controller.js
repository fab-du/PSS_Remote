( function(){
    'use strict';
    angular
        .module('cryptoClient')
        .controller('signinController', [ '$scope', '$log', signinController ]);

        function signinController( $scope, $log ) {
            $scope.login = function( credentials ){
                $log.log( "log in have been clicked " + credentials );
            };
        }

})();
