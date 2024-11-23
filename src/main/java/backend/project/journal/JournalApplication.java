package backend.project.journal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.SpringVersion;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.beans.BeanProperty;

@SpringBootApplication
@EnableTransactionManagement
@EnableAutoConfiguration(exclude={MongoAutoConfiguration.class})

public class JournalApplication {

    public static void main(String[] args) {
        System.out.println(SpringVersion.getVersion());
        SpringApplication.run(JournalApplication.class, args);
    }

    @Bean
    public PlatformTransactionManager enableTransaction(MongoDatabaseFactory mongoDatabaseFactory) {
        return new MongoTransactionManager(mongoDatabaseFactory);
    }
}
