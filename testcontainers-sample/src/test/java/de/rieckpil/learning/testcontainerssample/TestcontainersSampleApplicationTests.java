package de.rieckpil.learning.testcontainerssample;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestcontainersSampleApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = TestcontainersSampleApplicationTests.Initializer.class)
public class TestcontainersSampleApplicationTests {

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer().withPassword("inmemory")
            .withUsername("inmemory");

    @LocalServerPort
    private int localPort;

    public TestRestTemplate testRestTemplate = new TestRestTemplate();

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

            System.out.println("##########" + postgreSQLContainer.getJdbcUrl());

            TestPropertyValues values = TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername()
            );

            values.applyTo(configurableApplicationContext);
        }
    }

    @Test
    public void contextLoads() {
    }

}
