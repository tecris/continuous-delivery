angular.module('bs2').factory('AuthorResource', function($resource){
    var resource = $resource('rest/authors/:AuthorId',{AuthorId:'@authorId'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});