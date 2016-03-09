
angular.module('bs2').controller('NewGenreController', function ($scope, $location, locationParser, GenreResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.genre = $scope.genre || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/Genres/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        GenreResource.save($scope.genre, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/Genres");
    };
});