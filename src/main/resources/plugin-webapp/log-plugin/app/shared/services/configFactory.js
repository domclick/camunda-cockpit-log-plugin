ngDefine('cockpit.plugin.log-plugin.shared-services', (module) => {
    module.factory('configFactory', [() => {

        let configFactory = {};

        let searchConfig = {};

        searchConfig.tooltips = {
            'inputPlaceholder': 'Add criteria',
            'invalid': 'This search query is not valid',
            'deleteSearch': 'Remove search',
            'type': 'Type',
            'name': 'Property',
            'operator': 'Operator',
            'value': 'Value'
        };

        searchConfig.types = [
            {
                'id': {
                    'key': 'businessKey',
                    'value': 'Business Key'
                },
                'operators': [
                    {'key': 'eq', 'value': '='}
                ],
                enforceString: true
            }
        ];
        searchConfig.operators =  {
            'undefined': [
                {'key': 'eq', 'value': '='},
                {'key': 'neq','value': '!='}
            ],
            'string': [
                {'key': 'eq',  'value': '='},
                {'key': 'like','value': 'like'}
            ]
        };

        configFactory.searchConfig = searchConfig;

        configFactory.loadingState = 'LOADING';
        configFactory.loadedState = 'LOADED';

        return configFactory;
    }])
});