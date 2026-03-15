/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.SpringApplication
 *  org.springframework.boot.autoconfigure.SpringBootApplication
 */
package com.example.orders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;

@SpringBootApplication
public class OrderServiceApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(OrderServiceApplication.class);
        app.addListeners((ApplicationListener<ApplicationEnvironmentPreparedEvent>)OrderServiceApplication::logKafkaBootstrapServers);
        app.run((String[])args);
    }

    private static void logKafkaBootstrapServers(ApplicationEnvironmentPreparedEvent event) {
        String kafka = event.getEnvironment().getProperty("spring.kafka.bootstrap-servers");
        String kafkaEnv = System.getenv("SPRING_KAFKA_BOOTSTRAP_SERVERS");
        LOGGER.info("KAFKA BOOTSTRAP SERVERS (resolved) = {}", (Object)formatValue(kafka));
        LOGGER.info("SPRING_KAFKA_BOOTSTRAP_SERVERS (env) = {}", (Object)formatValue(kafkaEnv));
    }

    private static String formatValue(String value) {
        return value == null || value.isBlank() ? "<empty>" : value;
    }
}
