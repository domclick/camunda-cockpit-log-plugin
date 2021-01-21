ngDefine('cockpit.plugin.log-plugin.log', (module) => {
    module.controller('logProcessInstanceTabCtrl', ['$scope', 'dataFactory', ($scope, dataFactory) => {

        const setLogs = (businessKey, activityId) => {
            $scope.logs = null;
            dataFactory.fetchLogs({
                "businessKey": businessKey,
                "activityId": activityId
            }).then(() => {
                $scope.logs = dataFactory.logs;
            });
        };

        setLogs($scope.processInstance.businessKey, $scope.filter.activityIds[0]);

        $scope.$watch('filter', (oldValue, newValue) => {
            if (newValue) {
                setLogs($scope.processInstance.businessKey, $scope.filter.activityIds[0]);
            }
        });
    }]);
});