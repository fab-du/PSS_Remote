(function() {
  'use strict';
  angular
    .module('cryptoClient')
    .config(routeConfig);

  function routeConfig($stateProvider, $urlRouterProvider) {

    $stateProvider
      .state('home', {
        templateUrl:  'app/components/intro/intro.html', 
      })
      .state('home.intro', {
          url: '/',
          templateUrl: 'app/components/intro/intro.html',
      })


      $stateProvider
      .state('signin', {
          url: '/signin',
          templateUrl: 'app/components/sign-in/sign-in.html',
          controller: 'signinController'
      })

      $stateProvider
      .state('signup', {
          url: '/signup',
          templateUrl: 'app/components/sign-up/sign-up.html',
          controller: 'signupController'
      });


      /*
       *Documents groups
       */
      $stateProvider
      .state('documents', {
          url: '/documents',
          templateUrl: 'app/components/documents/documents.html',
          controller: 'documentController'
      });

      /*
       *Users Routes 
       */
      $stateProvider
        .state('users',{
          url: '/users',
          templateUrl: 'app/components/users/users.html',
          controller: 'userController'
        })
        .state('users.details', {
            url: '/user/:userId',
            templateUrl: 'app/components/users/user.detail.html',
            controller: 'userControllerDetail'
        });

      /*
       *Groups Routes 
       ****/
      $stateProvider
        .state('groups', {
          url: '/goups',
          templateUrl: 'app/components/groups/groups.html',
          controller: 'groupController'

        })
        .state('groups.details', {
          url: '/group/:groupId',
         templateUrl: 'app/components/groups/group.detail.html',
          controller: 'groupdetailController'
        })
        .state('groups.createGroup', {
            url : '/groups/:groupId',
            templateUrl : 'app/components/groups/group.create.html',
            controller  : 'newgroupController'
        })
        .state('groups.findGroup', {
            url : '/groups/:groupId',
            templateUrl : 'app/components/groups/group.search.html',
            controller  : 'findgroupController'
        });

      $urlRouterProvider.otherwise('/');

  }

})();

