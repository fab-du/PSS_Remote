(function(){

    'use strict';
    /**
    * @ngdoc function
    * @name cryptoClientApp.controller:I18nCtrl
    * @description
    * # I18nCtrl
    * Controller of the cryptoClientApp
    */
    angular.module('cryptoClient')
    .controller('I18nCtrl', [ '$translate', '$scope' , i18controller])
    .controller('langChangedController', ['$scope', '$translate', function( $scope, $translate){
        $scope.currentLanguage = $translate.use();
        $scope.$on('langchange', function(event, newkey ){
            $scope.currentLanguage = newkey;
        });
    }]);

    function i18controller( $translate, $scope ){
        $scope.translate = function (key) {
            var currentkey = $translate.use();
            if ( currentkey !== key ){
                $translate.use( key );
                $scope.$broadcast( 'langchange', key );
            }
        };
    }

})();

