(function() {
  'use strict';

  angular
    .module('cryptoClient')
    .directive('userDirective', userDirective);

  /** @ngInject */
  function userDirective() {
    var directive = {
      restrict: 'E',
      templateUrl: 'app/components/users/users.html',
      scope: {
          creationDate: '='
      },
      controller: usersController,
      controllerAs: 'CtrlUsers',
      bindToController: true
    };

    return directive;

    /** @ngInject */
    function usersController(  ) {
    }
  }

})();
