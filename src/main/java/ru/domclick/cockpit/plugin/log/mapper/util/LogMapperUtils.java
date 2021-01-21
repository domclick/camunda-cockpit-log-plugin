package ru.domclick.cockpit.plugin.log.mapper.util;

import org.apache.commons.beanutils.BeanUtils;
import ru.domclick.cockpit.plugin.log.configuration.KibanaConfiguration;
import ru.domclick.cockpit.plugin.log.configuration.LogFilterParamConfiguration;
import org.elasticsearch.search.SearchHit;
import org.mapstruct.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;

public class LogMapperUtils {
    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Level {
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface BusinessKey {
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Message {
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface ActivityName {
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Timestamp {
    }

    @Level
    public String getLevel(SearchHit searchHit) {
        return (String)searchHit.getSourceAsMap().get(LogFilterParamConfiguration.LEVEL);
    }

    @BusinessKey
    public String getBusinessKey(SearchHit searchHit) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return BeanUtils.getProperty(searchHit.getSourceAsMap(), KibanaConfiguration.BUSINESS_KEY);
    }

    @Message
    public String getMessage(SearchHit searchHit) {
        return (String)searchHit.getSourceAsMap().get(LogFilterParamConfiguration.MESSAGE);
    }

    @ActivityName
    public String getActivityName(SearchHit searchHit) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return BeanUtils.getProperty(searchHit.getSourceAsMap(), KibanaConfiguration.ACTIVITY_NAME);
    }

    @Timestamp
    public String getTimestamp(SearchHit searchHit) {
        return (String)searchHit.getSourceAsMap().get(LogFilterParamConfiguration.TIMESTAMP);
    }
}
