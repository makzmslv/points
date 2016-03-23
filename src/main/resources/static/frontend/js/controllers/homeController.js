var homeController = angular.module('homeController', [ 'ngRoute' ]).controller('homeController',['$scope','GameService', 'filterFilter', '$cookies', function($scope, GameService, filterFilter, $cookies) {
        'use strict';



        $scope.createGame = function (){
            $scope.createGame = true;
            var gameDTO = {};
            gameDTO.gameType = 1;
            gameDTO.noOfPlayers = $scope.selection.length;
            gameDTO.status = 1;
            if(gameDTO.noOfPlayers > 1){

            } else {
                alert('Select players')
            }
            GameService.createGame(gameDTO).$promise.then(function(result) {
                $cookies.put('gameId', result.id);
                $scope.gameId = result.id;
                $cookies.put('roundNo',-1);
                alert('Game Started')
                $scope.getRounds();

            });

        }

        $scope.selection = [];
        $scope.rounds = [];
        $scope.addRow = false;
        $scope.showResults = false;

        $scope.players = [
                         { name: 'vinay',    selected: false, playerId: 2, col:1 },
                         { name: 'aditya',   selected: false, playerId: 3, col:1 },
                         { name: 'mudit',     selected: false, playerId: 4, col:1 },
                         { name: 'saggu', selected: false, playerId: 5, col:1 },
                         { name: 'amey', selected: false, playerId: 6, col:1 },
                         { name: 'golu', selected: false, playerId: 7, col:2 },
                         { name: 'mk3', selected: false, playerId: 1, col:2 },
                         { name: 'pratik', selected: false, playerId: 9, col:2 },
                         { name: 'shantam', selected: false, playerId: 8, col:2 },
                         { name: 'new', selected: false, playerId: 11, col:2 },
                       ];

        $scope.selectedPlayers = function selectedPlayers() {
            return filterFilter($scope.players, { selected: true });
          };

        $scope.$watch('players|filter:{selected:true}', function (nv) {
            $scope.selection = nv.map(function (player) {
              return player.name;
            });
          }, true);

        $scope.getRounds = function () {
            $scope.gameId = $cookies.get('gameId');
            var id = parseInt($scope.gameId)
            GameService.getRounds(id).$promise.then(function(result) {
                var r = $cookies.get('roundNo');
                r = parseInt(r);
                var no = 0;
                for(var i = 0; i <= r; i ++){
                    if($scope.rounds[i] !== undefined) {
                        no = no + $scope.rounds[i].length;
                        console.log(no + "no")
                    }
                }
                result.splice(0,no);
                console.log(result.length + "re")
                $scope.rounds[r] = result;

            });
            console.log($scope.gameId)
        }

        $scope.addRoundPoints = function () {
            $scope.roundpoints = [];
            $scope.addRow = true;
        }

        $scope.addPoints = function () {
            var inputDTO = [];
            $scope.selection.forEach(function (playerName, $index) {
                var dto = {};
                dto.gameId = $scope.gameId;
                dto.playerName = playerName;
                dto.points =$scope.roundpoints[$index];
                inputDTO.push(dto);

            });
            $scope.addRow = false;
            GameService.addRoundPoints(inputDTO).$promise.then(function(result) {
                var r = $cookies.get('roundNo');
                r = parseInt(r);
                $cookies.put('roundNo', r + 1);
                $scope.getRounds();
            });
        }

        $scope.doTotal = function () {
            GameService.totalPoints($scope.gameId).$promise.then(function(result) {
                $scope.results = result;
                $scope.showResults = true;
                $cookies.put('gameId', null);
                GameService.getResults($scope.gameId).$promise.then(function(result) {
                    $scope.results = result;

                });
            });
        }

        $scope.getRounds();
    }

]);