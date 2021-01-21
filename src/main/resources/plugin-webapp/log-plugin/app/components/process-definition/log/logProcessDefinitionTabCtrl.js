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

        const setLogs = (businessKey, activityId, procInstBusinessKey) => {
            $scope.loadingState = configFactory.loadingState;
            $scope.logs = null;
            dataFactory.fetchLogs({
                "processDefinitionKey": businessKey,
                "activityId": activityId,
                "businessKey": procInstBusinessKey
            }).then(() => {
                $scope.logs = dataFactory.logs;
                $scope.loadingState = configFactory.loadedState;
            });
        };

        $scope.onSearchChange = (query, pages) => {
            $scope.processInstanceBusinessKey = query.businessKey;
            return setLogs(
                $scope.processDefinition.key,
                $scope.filter.scrollToBpmnElement,
                $scope.processInstanceBusinessKey
            );
        };
        $scope.$watch('filter', (oldValue, newValue) => {
            if (newValue) {
                setLogs(
                    $scope.processDefinition.key,
                    $scope.filter.scrollToBpmnElement,
                    $scope.processInstanceBusinessKey
                );
            }
        });
    }]);
});