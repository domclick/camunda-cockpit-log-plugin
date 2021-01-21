ngDefine('cockpit.plugin.log-plugin.shared-services', (module) => {
    module.factory('dataFactory', ['$http', 'Uri', ($http, Uri) => {

        let dataFactory = {};

        dataFactory.logs = [];

        dataFactory.fetchLogs = ({processDefinitionKey, activityId, businessKey}) => {
            return $http.get(Uri.appUri("plugin://log-plugin/:engine/log?"
                + getOptionalRequestParamStr('procDefKey', processDefinitionKey)
                + getOptionalRequestParamStr('activityId', activityId)
                + getOptionalRequestParamStr('businessKey', businessKey)))
                .then(({data}) => {
                    dataFactory.logs = data;
                }, (error) => {
                    console.debug("error in getting logs");
                });
        };

        return dataFactory;
    }])
});

function getOptionalRequestParamStr(paramName, paramValue) {
    return paramValue != null ? '&' + paramName + '=' + paramValue : "";
}