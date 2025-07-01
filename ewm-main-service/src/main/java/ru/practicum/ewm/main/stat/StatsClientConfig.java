package ru.practicum.ewm.main.stat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.practicum.ewm.stats.StatsClient;

@Configuration
public class StatsClientConfig {
    @Value("${stats-server.url}") String serverUrl;

    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder();
    }

    @Bean
    public StatsClient statsClient(RestTemplateBuilder builder) {
        return new StatsClient(serverUrl,builder);
    }
}
