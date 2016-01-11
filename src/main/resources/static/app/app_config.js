(function () {
    'use strict'

     angular.module('cryptoClient').config(['$routeProvider',  '$translateProvider', '$httpProvider' , '$resourceProvider', 'localStorageServiceProvider' ,configfunction] )
     .run(['$rootScope', '$location', 'localStorageService', '$http', '$state', function($rootScope, $location, localStorageService, $http, $state){

        $rootScope.globals = localStorageService.get('globals') || {};

        if( $rootScope.globals.currentUser ){
            $http.defaults.headers.common['Authorization']= 'Basic';
        }


        $rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState,  fromParams){

            var restrictedPage = $.inArray( $state.get(), ['signin', 'signup']);
            var loggedIn = $rootScope.globals.currentUser;
            if ( restrictedPage && !loggedIn ){
                return;
            }

            /*
             * Prevent User for clicking on ruturn button 
             * and go out of the app
             * */
            if ( toState === '' || toState === null ){
                return;
            }
                
        });


     }]);


     function configfunction( $routeProvider ,  $translateProvider, $httpProvider, $resourceProvider, localStorageServiceProvider){

        $translateProvider.preferredLanguage('de');
        $translateProvider.useStaticFilesLoader({prefix : 'i18n/', suffix: '.json'});
        $resourceProvider.defaults.stripTrailingSlashes = false;
         localStorageServiceProvider.setPrefix('ls');

        $httpProvider.interceptors.push(['$q', '$location', '$localStorage', function($q, $location, $localStorage) {
                    return {
                        'request': function (config) {
                            config.headers = config.headers || {};
                            if ($localStorage.token) {
                                config.headers.Authorization = 'Bearer ' + $localStorage.token;
                            }
                            return config;
                        },
                        'responseError': function(response) {
                            if(response.status === 401 || response.status === 403) {
                                $location.path('/signin');
                            }
                            return $q.reject(response);
                        }
                    };
                }]);

     }
    
})();
