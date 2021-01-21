package ru.domclick.cockpit.plugin.log.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.X509TrustManager;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KibanaConfiguration {
    public static String SCHEME = "";
    public static String HOST = "";
    public static Integer PORT = 9200;
    public static String USERNAME = "";
    public static String PASSWORD = "";
    public static X509TrustManager TRUST_MANAGER = null;
    public static String BUSINESS_KEY = "";
    public static String SCENARIO_ID = "";
    public static String ACTIVITY_ID = "";
    public static String ACTIVITY_NAME = "";
    public static Map<String, String> ADDITIONAL_QUERY_PARAMS = new HashMap<>();

    @Value("${kibana.scheme}")
    public void setScheme(String scheme) {
        SCHEME = scheme;
    }

    @Value("${kibana.host}")
    public void setHost(String host) {
        HOST = host;
    }

    @Value("${kibana.port}")
    public void setHost(Integer port) {
        PORT = port;
    }

    @Value("${kibana.username}")
    public void setUsername(String username) {
        USERNAME = username;
    }

    @Value("${kibana.password}")
    public void setPassword(String password) {
        PASSWORD = password;
    }

    @Value("${kibana.trust-manager:ru.domclick.cockpit.plugin.log.ssl.TrustAllManager}")
    public void setTrustManager(String trustManager) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        TRUST_MANAGER = (X509TrustManager) Class.forName(trustManager).getConstructor().newInstance();
    }

    @Value("${kibana.query.businessKey}")
    public void setBusinessKey(String businessKey) {
        BUSINESS_KEY = businessKey;
    }

    @Value("${kibana.query.scenarioId}")
    public void setScenarioId(String scenarioId) {
        SCENARIO_ID = scenarioId;
    }

    @Value("${kibana.query.activityId}")
    public void setActivityId(String activityId) {
        ACTIVITY_ID = activityId;
    }

    @Value("${kibana.query.activityName}")
    public void setActivityName(String activityName) {
        ACTIVITY_NAME = activityName;
    }

    @Value("#{${kibana.query.additional-params}}")
    public void setQueryParams(Map<String, String> queryParams) {
        ADDITIONAL_QUERY_PARAMS.putAll(queryParams);
    }
}
