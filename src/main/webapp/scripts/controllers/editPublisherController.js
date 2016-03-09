

angular.module('bs2').controller('EditPublisherController', function($scope, $routeParams, $location, PublisherResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.publisher = new PublisherResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/Publishers");
        };
        PublisherResource.get({PublisherId:$routeParams.PublisherId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.publisher);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.publisher.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/Publishers");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/Publishers");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.publisher.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});