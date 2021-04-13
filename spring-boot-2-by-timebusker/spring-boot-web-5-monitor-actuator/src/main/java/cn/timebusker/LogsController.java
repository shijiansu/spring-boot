package cn.timebusker;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LogsController {

  @GetMapping("/")
  public String createLogs() {
    log.info("----------------------------------INFO");
    log.debug("----------------------------------DEBUG");
    log.error("----------------------------------ERROR");
    LoggingUtil.creatLogging();
    return "Hello,this is spring-boot-starter-actuator";
  }
}
