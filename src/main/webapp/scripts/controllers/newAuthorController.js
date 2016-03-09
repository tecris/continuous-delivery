
angular.module('bs2').controller('NewAuthorController', function ($scope, $location, locationParser, AuthorResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.author = $scope.author || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/Authors/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        AuthorResource.save($scope.author, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/Authors");
    };
});