'use strict';

angular.module('bs2',['ngRoute','ngResource'])
  .config(['$routeProvider', function($routeProvider) {
    $routeProvider
      .when('/',{templateUrl:'views/landing.html',controller:'LandingPageController'})
      .when('/Authors',{templateUrl:'views/Author/search.html',controller:'SearchAuthorController'})
      .when('/Authors/new',{templateUrl:'views/Author/detail.html',controller:'NewAuthorController'})
      .when('/Authors/edit/:AuthorId',{templateUrl:'views/Author/detail.html',controller:'EditAuthorController'})
      .when('/Books',{templateUrl:'views/Book/search.html',controller:'SearchBookController'})
      .when('/Books/new',{templateUrl:'views/Book/detail.html',controller:'NewBookController'})
      .when('/Books/edit/:BookId',{templateUrl:'views/Book/detail.html',controller:'EditBookController'})
      .when('/Genres',{templateUrl:'views/Genre/search.html',controller:'SearchGenreController'})
      .when('/Genres/new',{templateUrl:'views/Genre/detail.html',controller:'NewGenreController'})
      .when('/Genres/edit/:GenreId',{templateUrl:'views/Genre/detail.html',controller:'EditGenreController'})
      .when('/Publishers',{templateUrl:'views/Publisher/search.html',controller:'SearchPublisherController'})
      .when('/Publishers/new',{templateUrl:'views/Publisher/detail.html',controller:'NewPublisherController'})
      .when('/Publishers/edit/:PublisherId',{templateUrl:'views/Publisher/detail.html',controller:'EditPublisherController'})
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
