
angular.module('bs2').controller('NewPublisherController', function ($scope, $location, locationParser, PublisherResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.publisher = $scope.publisher || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/Publishers/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        PublisherResource.save($scope.publisher, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/Publishers");
    };
});