
angular.module('planets').controller('NewPlanetController', function ($scope, $location, locationParser, flash, PlanetResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.planet = $scope.planet || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            flash.setMessage({'type':'success','text':'The planet was created successfully.'});
            $location.path('/Planets');
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        PlanetResource.save($scope.planet, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/Planets");
    };
});