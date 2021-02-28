ngDefine('cockpit.plugin.log-plugin.log', (module) => {
    module.controller('logProcessDefinitionTabCtrl', ['$scope', 'dataFactory', 'configFactory', ($scope, dataFactory, configFactory) => {

        $scope.searches = [];
        $scope.validSearches = [];

        $scope.searchConfig = configFactory.searchConfig;

        $scope.query = null;
        $scope.blocked = false;
        $scope.loadingState = configFactory.loadingState;
        $scope.textEmpty = 'NOT_FOUND';

        $scope.onSortChanged = (sorting) => {};
        $scope.onSortInitialized = (sorting) => {};

        const setLogs = (filterParams) => {
            $scope.loadingState = configFactory.loadingState;
            $scope.logs = null;
            dataFactory.fetchLogs(filterParams).then(() => {
                $scope.logs = dataFactory.logs;
                $scope.loadingState = configFactory.loadedState;
            });
        };

        $scope.onSearchChange = (query, pages) => {
            $scope.processInstanceBusinessKey = query.businessKey;
            return setLogs(
                {
                    "processDefinitionKey": $scope.processDefinition.key,
                    "activityId": $scope.filter.scrollToBpmnElement,
                    "businessKey": $scope.processInstanceBusinessKey
                }
            );
        };
        $scope.$watch('filter', (oldValue, newValue) => {
            if (newValue) {
                setLogs(
                    {
                        "processDefinitionKey": $scope.processDefinition.key,
                        "activityId": $scope.filter.scrollToBpmnElement,
                        "businessKey": $scope.processInstanceBusinessKey
                    }
                );
            }
        });
    }]);
});