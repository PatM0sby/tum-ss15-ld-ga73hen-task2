var training = angular.module('training', ['ui.bootstrap']);

training.controller('TrainingController', function ($scope, $http) {

    $scope.getCities = function () {
        $http.get('/api/cities').success(function (data) {
            $scope.cities = data;
        }).error(function (data, status) {
            window.alert('Status ' + status + ': ' + data.message);
        });
    };
    $scope.getCities();

});
