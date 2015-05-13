/**
 * Created by Pat on 11.05.2015.
 */
var training = angular.module('training', ['ui.bootstrap']);

training.controller('TrainingController', function ($scope, $http) {

    $scope.getCountries = function () {
        $http.get('/api/countries').success(function (data) {
            $scope.countries = data;
        }).error(function (data, status) {
            window.alert('Status ' + status + ': ' + data.message);
        });
    };
    $scope.getCountries();

    $scope.reloadCountries = function () {
        var parameter =  document.getElementById('input').value;

        $http.get('/api/countries?population='+parameter).success(function (data) {
            $scope.countries = data;
        }).error(function (data, status) {
            window.alert('Status ' + status + ': ' + data.message);
        });
    };

});

