package ust.demaf.demafshell;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemafShellApplication {

  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(DemafShellApplication.class);
    app.setBannerMode(Mode.OFF);
    app.run(args);
  }
}
