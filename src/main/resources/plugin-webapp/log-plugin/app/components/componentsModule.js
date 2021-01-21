ngDefine('cockpit.plugin.log-plugin.components', [
    'module:cockpit.plugin.log-plugin.log:./process-definition/log/logProcessDefinitionModule',
    'module:cockpit.plugin.log-plugin.log:./process-instance/log/logProcessInstanceModule'
], (module) => {

    let Configuration = (ViewsProvider) => {

        /* components in tab of process definition view */

        ViewsProvider.registerDefaultView('cockpit.processDefinition.runtime.tab', {
            id: 'process-definition-runtime-tab-log',
            priority: 20,
            label: 'Logs',
            url: 'plugin://log-plugin/static/app/components/process-definition/processDefinitionTabView.html'
        });

        ViewsProvider.registerDefaultView('cockpit.processInstance.runtime.tab', {
            id: 'process-instance-runtime-tab-log',
            priority: 20,
            label: 'Logs',
            url: 'plugin://log-plugin/static/app/components/process-instance/processInstanceTabView.html'
        });

    };

    Configuration.$inject = ['ViewsProvider'];

    module.config(Configuration);

    return module;
});