package reactive.by.liukang._2_docker_event_rest;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.AbstractProcess;
import de.flapdoodle.embed.process.runtime.Executable;
import de.flapdoodle.embed.process.runtime.Network;
import java.io.IOException;
import java.util.Optional;
import javax.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("dev") // for local development only
@Slf4j
@Configuration
public class MongoConfiguration {

  @Value("${spring.data.mongodb.host:localhost}")
  private String host;

  @Value("${spring.data.mongodb.port:27017}")
  private int port;

  @Bean
  public MongodExecutable mongodExecutable() throws IOException {
    log.info("host: {}", host);
    log.info("port: {}", port);

    MongodStarter starter = MongodStarter.getDefaultInstance();
    // by default no authentication
    IMongodConfig mongodConfig =
        new MongodConfigBuilder()
            .version(Version.Main.PRODUCTION)
            .net(new Net(host, port, Network.localhostIsIPv6()))
            .build();
    return starter.prepare(mongodConfig);
  }

  @Bean
  public MongodProcess mongodProcess(MongodExecutable mongodExecutable) throws IOException {
    return mongodExecutable.start();
  }

  @Autowired MongodExecutable mongodExecutable; // for shutdown
  @Autowired MongodProcess mongodProcess; // for shutdown

  @PreDestroy
  public void shutdownMongo() {
    // even do not have this, it will auto shutdown
    log.info("shutting down mongo");
    Optional.of(mongodProcess).ifPresent(AbstractProcess::stop);
    Optional.of(mongodExecutable).ifPresent(Executable::stop);
  }
}
