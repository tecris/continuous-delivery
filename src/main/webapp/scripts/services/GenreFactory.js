angular.module('bs2').factory('GenreResource', function($resource){
    var resource = $resource('rest/genres/:GenreId',{GenreId:'@genreId'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});