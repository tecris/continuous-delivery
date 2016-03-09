

angular.module('bs2').controller('EditGenreController', function($scope, $routeParams, $location, GenreResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.genre = new GenreResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/Genres");
        };
        GenreResource.get({GenreId:$routeParams.GenreId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.genre);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.genre.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/Genres");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/Genres");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.genre.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});