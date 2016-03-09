
angular.module('bs2').controller('NewBookController', function ($scope, $location, locationParser, BookResource , AuthorResource, GenreResource, PublisherResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.book = $scope.book || {};
    
    $scope.authorList = AuthorResource.queryAll(function(items){
        $scope.authorSelectionList = $.map(items, function(item) {
            return ( {
                value : item.authorId,
                text : item.firstName + ' ' + item.lastName
            });
        });
    });
    $scope.$watch("authorSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.book.author = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.authorId = selectedItem.value;
                $scope.book.author.push(collectionItem);
            });
        }
    });
    
    $scope.genreList = GenreResource.queryAll(function(items){
        $scope.genreSelectionList = $.map(items, function(item) {
            return ( {
                value : item.genreId,
                text : item.genre
            });
        });
    });
    $scope.$watch("genreSelection", function(selection) {
        if ( typeof selection != 'undefined') {
            $scope.book.genre = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.genreId = selectedItem.value;
                $scope.book.genre.push(collectionItem);
            });
        }
    });
    
    $scope.publisherList = PublisherResource.queryAll(function(items){
        $scope.publisherSelectionList = $.map(items, function(item) {
            return ( {
                value : item.publisherId,
                text : item.name
            });
        });
    });
    $scope.$watch("publisherSelection", function(selection) {
        if ( typeof selection != 'undefined') {
            $scope.book.publisher = {};
            $scope.book.publisher.publisherId = selection.value;
        }
    });
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/Books/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        BookResource.save($scope.book, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/Books");
    };
});