angular.module('planets').factory('PlanetResource', function($resource){
    var resource = $resource('rest/planets/:PlanetId',{PlanetId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});