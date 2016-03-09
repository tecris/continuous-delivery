angular.module('bs2').factory('BookResource', function($resource){
    var resource = $resource('rest/books/:BookId',{BookId:'@bookId'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});