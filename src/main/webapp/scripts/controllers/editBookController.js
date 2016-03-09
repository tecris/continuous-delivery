

angular.module('bs2').controller('EditBookController', function($scope, $routeParams, $location, BookResource , AuthorResource, GenreResource, PublisherResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.book = new BookResource(self.original);
            AuthorResource.queryAll(function(items) {
                $scope.authorSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        authorId : item.authorId
                    };
                    var labelObject = {
                        value : item.authorId,
                        text : item.firstName + ' ' + item.lastName
                    };
                    if($scope.book.author){
                        $.each($scope.book.author, function(idx, element) {
                            if(item.authorId == element.authorId) {
                                $scope.authorSelection.push(labelObject);
                                $scope.book.author.push(wrappedObject);
                            }
                        });
                        self.original.author = $scope.book.author;
                    }
                    return labelObject;
                });
            });
            GenreResource.queryAll(function(items) {
                $scope.genreSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        genreId : item.genreId
                    };
                    var labelObject = {
                        value : item.genreId,
                        text : item.genre
                    };
                    if($scope.book.genre){
                        $.each($scope.book.genre, function(idx, element) {
                            if(item.genreId == element.genreId) {
                                $scope.genreSelection.push(labelObject);
                                $scope.book.genre.push(wrappedObject);
                            }
                        });
                        self.original.genre = $scope.book.genre;
                    }
                    return labelObject;
                });
            });

            PublisherResource.queryAll(function(items) {
                $scope.publisherSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        publisherId : item.publisherId
                    };
                    var labelObject = {
                        value : item.publisherId,
                        text : item.name
                    };
                    if($scope.book.publisher && item.publisherId == $scope.book.publisher.publisherId) {
                        $scope.publisherSelection = labelObject;
                        $scope.book.publisher = wrappedObject;
                        self.original.publisher = $scope.book.publisher;
                    }
                    return labelObject;
                });
            });
        };
        var errorCallback = function() {
            $location.path("/Books");
        };
        BookResource.get({BookId:$routeParams.BookId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.book);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.book.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/Books");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/Books");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.book.$remove(successCallback, errorCallback);
    };
    
    $scope.authorSelection = $scope.authorSelection || [];
    $scope.$watch("authorSelection", function(selection) {
        if (typeof selection != 'undefined' && $scope.book) {
            $scope.book.author = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.authorId = selectedItem.value;
                $scope.book.author.push(collectionItem);
            });
        }
    });
    $scope.genreSelection = $scope.genreSelection || [];
    $scope.$watch("genreSelection", function(selection) {
        if (typeof selection != 'undefined' && $scope.book) {
            $scope.book.genre = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.genreId = selectedItem.value;
                $scope.book.genre.push(collectionItem);
            });
        }
    });
    $scope.$watch("publisherSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.book.publisher = {};
            $scope.book.publisher.publisherId = selection.value;
        }
    });
    
    $scope.get();
});