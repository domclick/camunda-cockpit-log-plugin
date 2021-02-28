package ru.domclick.cockpit.plugin.log.query;

import lombok.Builder;
import lombok.val;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import ru.domclick.cockpit.plugin.log.client.LogSearchRestHighLevelClient;
import ru.domclick.cockpit.plugin.log.configuration.KibanaConfiguration;
import ru.domclick.cockpit.plugin.log.configuration.LogFilterParamConfiguration;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.util.StringUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Builder(builderClassName = "LogSearchQueryInternalBuilder", builderMethodName = "internalBuilder")
public class LogSearchQuery implements AutoCloseable {

    private String businessKey;
    private List<String> procDefKeys;
    private List<String> activityIds;
    private RestHighLevelClient client;

    private void init() throws NoSuchAlgorithmException, KeyManagementException {
        val httpHost = new HttpHost(
                KibanaConfiguration.HOST,
                KibanaConfiguration.PORT,
                KibanaConfiguration.SCHEME
        );

        val credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider
                .setCredentials(
                        AuthScope.ANY,
                        new UsernamePasswordCredentials(
                                KibanaConfiguration.USERNAME,
                                KibanaConfiguration.PASSWORD
                        )
                );

        val tm = KibanaConfiguration.TRUST_MANAGER;
        val sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, new TrustManager[] { tm }, null);

        client = new LogSearchRestHighLevelClient(httpHost, credentialsProvider, sslContext);
    }

    public SearchHits search() {
        val boolQueryBuilder = QueryBuilders.boolQuery();

        KibanaConfiguration.ADDITIONAL_QUERY_PARAMS.forEach((key, value) ->
                boolQueryBuilder.filter()
                        .add(QueryBuilders.matchPhraseQuery(key, value))
        );
        if (!StringUtils.isEmpty(businessKey)) {
            boolQueryBuilder.filter()
                    .add(QueryBuilders.matchPhraseQuery(KibanaConfiguration.BUSINESS_KEY, businessKey));
        }
        if (procDefKeys != null) {
            val procDefKeySubQuery = QueryBuilders.boolQuery();

            procDefKeys.forEach(procDefKey ->
                    procDefKeySubQuery.should()
                            .add(QueryBuilders.matchPhraseQuery(KibanaConfiguration.SCENARIO_ID, procDefKey)));

            boolQueryBuilder.filter().add(procDefKeySubQuery);
        }
        if (activityIds != null) {
            val activitySubQuery = QueryBuilders.boolQuery();

            activityIds.forEach(activityId ->
                    activitySubQuery.should()
                            .add(QueryBuilders.matchPhraseQuery(KibanaConfiguration.ACTIVITY_ID, activityId)));

            boolQueryBuilder.filter().add(activitySubQuery);
        }

        val searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.size(50);
        searchSourceBuilder.sort(new FieldSortBuilder(LogFilterParamConfiguration.TIMESTAMP).order(SortOrder.ASC));

        val searchRequest = new SearchRequest();
        searchRequest.source(searchSourceBuilder);
        try {
            return client.search(searchRequest, RequestOptions.DEFAULT).getHits();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws IOException {
        client.close();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends LogSearchQueryInternalBuilder {
        private Builder() {
            super();
        }

        @Override
        public LogSearchQuery build() {
            val logSearchQuery = super.build();

            try {
                logSearchQuery.init();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            return logSearchQuery;
        }
    }
}
