/**
 * Created by luozhongjin on 01/01/2018.
 */

var app = angular.module('myApp', []);
app.controller('myCtrl', function($scope) {
    $scope.purchase = function() {
        console.log("hehhe")
        $('#iphoneX').modal('hide');
    }
});
