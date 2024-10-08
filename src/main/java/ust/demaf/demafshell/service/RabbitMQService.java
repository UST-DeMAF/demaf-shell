package ust.demaf.demafshell.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RabbitMQService {

  private final RabbitAdmin rabbitAdmin;
  private final String username;
  private final String password;
  private final String url;

  @Autowired
  public RabbitMQService(
      RabbitAdmin rabbitAdmin,
      @Value("${spring.rabbitmq.username}") String username,
      @Value("${spring.rabbitmq.password}") String password,
      @Value("${spring.rabbitmq.url}") String url) {
    this.rabbitAdmin = rabbitAdmin;
    this.username = username;
    this.password = password;
    this.url = url;
  }

  /**
   * Calls the RabbitMQ management API to retrieve the list of available queues. It uses basic
   * authentication with default credentials to access the management API.
   *
   * @return a list of queue names.
   */
  public List<String> getQueueNames() {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(username, password));
    ResponseEntity<List> response =
        restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), List.class);
    List<Map<String, Object>> queues = response.getBody();
    List<String> queueNames = new ArrayList<>();
    for (Map<String, Object> queue : queues) {
      queueNames.add((String) queue.get("name"));
    }
    return queueNames;
  }
}
