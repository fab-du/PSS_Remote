(function() {       
'use strict';

/**
 * @ngdoc service
 * @name cryptoClientApp.i18n
 * @description
 * # i18n
 * Factory in the cryptoClientApp.
 */
angular.module('cryptoClient')
  .factory('i18n', [ factory_i18n]);


  function factory_i18n( $http, $q ){

      function doReq( options ){
            var deferred = $q.defer();
            $http({
                method:'GET',
                url:'locale-' + options.key + '.json'
            }).success(function (data) {
                deferred.resolve(data);
            }).error(function () {
                deferred.reject(options.key);
            });
            
            return deferred.promise;
      }

      return  { i18n : doReq };
  }

})();
