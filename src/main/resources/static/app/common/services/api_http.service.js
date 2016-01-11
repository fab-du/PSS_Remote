
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
  .factory('api_http', [ '$http', '$log', ApiHttp]);


function ApiHttp( $http){
 var _self = this;

 var _http = function( method , url, data ){
     if( method === 'GET'){
         return $http( {method : method , url: url } );
     }
     else{
         return  $http( {method : method , url: url, data:data } );
     }
 };

 _self.GET = function( url, callback ){
   callback = (typeof callback === 'function') ? callback : function() {};
       _http('GET', url, {} )
            .success(function( data, status){
                return callback( null, { data : data, status : status } );
            })
            .error( function( data, status ){
                return callback( status );
            });
  };

 _self.POST = function( url, data, callback ){
   callback = (typeof callback === 'function') ? callback : function() {};
       _http('POST', url, data )
            .success(function( data, status){
                callback( null, { data : data, status : status } );
            })
            .error( function( data, status ){
                callback( status );
            });

  };

 _self.PUT = function( url, data, callback ){
   callback = (typeof callback === 'function') ? callback : function() {};
       _http('PUT', url, data )
            .success(function( data, status){
                callback( null, { data : data.data, status : status } );
            })
            .error( function( data, status ){
                callback( status );
            });

  };

 _self.DELETE = function( url, callback ){
   callback = (typeof callback === 'function') ? callback : function() {};
       _http('DELETE', url )
            .success(function( data, status){
                callback( null, { data : data, status : status } );
            })
            .error( function( data, status ){
                callback( status );
            });

  };

return _self;

}

})();

