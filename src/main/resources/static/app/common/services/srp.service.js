
(function () {
    'use strict'

    angular.module('cryptoClient')
    .service('srp_signin', [ srp_signin ]);

    function srp_signin(){

        var _self = this; 
        _self.challengeUrl = '/challenge';
        _self.securityCheckUrl = '/authenticate';
        _self.email = null;
        _self.password = null;

        _self.setCredentials = function( credentials ){
            _self.email = credentials.email;
            _self.password = credentials.password;
        }


        var onChallengeResponse  = function( response ){

            var email =    _self.getEmail();
            var password = _self.getPassword();
            var srpClient = new SRP6JavascriptClientSessionSHA256();
            
            var start = Date.now();

            var credentials = srpClient.step2(response.salt, response.b);

            var end = Date.now();

            var values = {
                username: _self.getEmail(),
                password: credentials.M1+":"+credentials.A
            };

        }

        var onSecurityCheck = function( response ){

        }


        _self.initzialize = function( options ){
            if (options) {
                me.options = options;
            }

        }

    }


    
})();
