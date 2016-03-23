//
// product services
//
var restServices = angular.module('restServices', [ 'ngResource' ]);
restServices.factory('GameService', function ($resource) {
        "use strict";

        return {
            "createGame": function (gameDTO) {
                var ret, restResource;

                restResource = $resource('/game', {}, {});

                ret = restResource.save(gameDTO);

                return ret;
            },
            "addRoundPoints": function (roundPoints) {
                var ret, restResource;

                restResource = $resource('/round', {}, {});

                ret = restResource.save(roundPoints);

                return ret;
            },
            "totalPoints": function (gameId) {
                var ret, restResource;

                restResource = $resource('/total', {}, {});

                ret = restResource.save(gameId);

                return ret;
            },
            "getRounds": function (id) {
                var ret, restResource;

                restResource = $resource('/game/:id', {}, {
                    "query": {
                        "method": "GET",
                        "isArray": true
                    }
                });

                ret = restResource.query({ "id": id });

                return ret;
            },
            "getResults": function (id) {
                var ret, restResource;

                restResource = $resource('/result/:id', {}, {
                    "query": {
                        "method": "GET",
                        "isArray": true
                    }
                });

                ret = restResource.query({ "id": id });

                return ret;
            }

        };
    });