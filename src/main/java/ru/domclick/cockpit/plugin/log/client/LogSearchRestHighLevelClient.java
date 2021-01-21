package ru.domclick.cockpit.plugin.log.client;

import org.apache.http.HttpHost;
import org.apache.http.client.CredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import javax.net.ssl.SSLContext;

public class LogSearchRestHighLevelClient extends RestHighLevelClient {

    public LogSearchRestHighLevelClient(HttpHost httpHost, CredentialsProvider credentialsProvider, SSLContext sslContext) {
        super(RestClient
                .builder(httpHost)
                .setHttpClientConfigCallback(
                        httpClientBuilder ->
                                httpClientBuilder
                                        .setSSLHostnameVerifier((s, sslSession) -> true)
                                        .setDefaultCredentialsProvider(credentialsProvider)
                                        .setSSLContext(sslContext)
                )
        );
    }
}
