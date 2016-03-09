angular.module('bs2').factory('PublisherResource', function($resource){
    var resource = $resource('rest/publishers/:PublisherId',{PublisherId:'@publisherId'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});