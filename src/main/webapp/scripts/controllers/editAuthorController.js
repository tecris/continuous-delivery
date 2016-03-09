

angular.module('bs2').controller('EditAuthorController', function($scope, $routeParams, $location, AuthorResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.author = new AuthorResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/Authors");
        };
        AuthorResource.get({AuthorId:$routeParams.AuthorId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.author);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.author.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/Authors");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/Authors");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.author.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});