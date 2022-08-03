package ust.demaf.demafshell.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    
    @Value("${analysis-manager.url}")
    private String analysisManagerURL;

	@Bean
	public WebClient analysisManagerApiClient() {
		return WebClient.create(analysisManagerURL);
	}

}
