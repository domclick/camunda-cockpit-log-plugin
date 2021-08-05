ngDefine('cockpit.plugin.log-plugin.log', (module) => {
    module.controller('logProcessInstanceTabCtrl', ['$scope', 'dataFactory', ($scope, dataFactory) => {

        const setLogs = (filterParams) => {
            $scope.logs = null;
            dataFactory.fetchLogs(filterParams).then(() => {
                $scope.logs = dataFactory.logs;
            });
        };

        const processDefinitionKeyByDefinitionId = (definitionId) => {
            return definitionId.substring(0, definitionId.indexOf(':'));
        };

        setLogs(
            {
                "processDefinitionKey": processDefinitionKeyByDefinitionId($scope.processInstance.definitionId),
                "businessKey": $scope.processInstance.businessKey,
                "activityId": $scope.filter.activityIds[0]
            }
        );

        $scope.$watch('filter', (oldValue, newValue) => {
            if (newValue) {
                setLogs(
                    {
                        "processDefinitionKey": processDefinitionKeyByDefinitionId($scope.processInstance.definitionId),
                        "businessKey": $scope.processInstance.businessKey,
                        "activityId": $scope.filter.activityIds[0]
                    }
                );
            }
        });
    }]);
});