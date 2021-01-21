##Cockpit Log Plugin
This plugin allows to view logs from kibana per process instances in case if logs have tracing info like process instance business key, activity id, activity name in their header. You could achieve this behavior using project [Sleuth](https://github.com/spring-cloud/spring-cloud-sleuth) of version 3 and higher.

The plugin adds Logs tab to Process Definition and Process Instance view pages.
![Logs tab](./resources/log-tab.png)

##Build Requirements
 - **Java** v8 and higher
 - **Maven** v3 and higher
 - **Camunda** 7.11.0
 - **Spring** 3 and higher
##Usage
Just add next properties to your `application.properties`(`application.yml`) file:
 - kibana.scheme - provides http or https
 - kibana.host - host of your kibana server
 - kibana.port - port of your kibana server
 - kibana.username - username for cockpit plugin to login to kibana server
 - kibana.password - password for cockpit plugin to login to kibana server
 - kibana.query.businessKey - name of process instance business key in logs header(ex.: mdc.HEADER_BUSINESS_KEY)
 - kibana.query.scenarioId - name of process definition key in logs header(ex.: mdc.HEADER_SCENARIO_ID)
 - kibana.query.activityId - name of process activity id in logs header(ex.:mdc.HEADER_ACTIVITY_ID)
 - kibana.query.activityName - name of process activity id in logs header(ex.: mdc.HEADER_ACTIVITY_NAME)
 - kibana.query.additional-params - additional params in logs header to specify your app logs (ex.: "{kafka_topic: '${KAFKA_TOPIC}', level: 'INFO'}")

## Contributor Notice

We are always open for contributions. Feel free to submit an issue or a PR. However, when submitting a PR we will ask 
you to sign our [CLA (Contributor License Agreement)][cla-text] to confirm that you have the rights to submit your 
contributions and to give us the rights to actually use them.

When submitting a PR our special bot will ask you to review and to sign our [CLA][cla-text]. This will happen only once 
for all our GitHub repositories.

## License
Copyright Ⓒ 2021
["Sberbank Real Estate Center" Limited Liability Company](https://domclick.ru/).

[MIT License](./LICENSE.md)