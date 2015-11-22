'use strict';

angular.module('planets',['ngRoute','ngResource'])
  .config(['$routeProvider', function($routeProvider) {
    $routeProvider
      .when('/',{templateUrl:'views/landing.html',controller:'LandingPageController'})
      .when('/Planets',{templateUrl:'views/Planet/search.html',controller:'SearchPlanetController'})
      .when('/Planets/new',{templateUrl:'views/Planet/detail.html',controller:'NewPlanetController'})
      .when('/Planets/edit/:PlanetId',{templateUrl:'views/Planet/detail.html',controller:'EditPlanetController'})
      .otherwise({
        redirectTo: '/'
      });
  }])
  .controller('LandingPageController', function LandingPageController() {
  })
  .controller('NavController', function NavController($scope, $location) {
    $scope.matchesRoute = function(route) {
        var path = $location.path();
        return (path === ("/" + route) || path.indexOf("/" + route + "/") == 0);
    };
  });
