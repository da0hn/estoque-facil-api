package br.com.anunciabem.estoquefacil;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class IntegrationTest {

  @Container
  @ServiceConnection
  protected static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13.3");

  @SneakyThrows
  protected static String readPayloadRequest(final String path) {
    return IOUtils.toString(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream(path)), StandardCharsets.UTF_8);
  }

  @Test
  @DisplayName("Ensure container is running")
  void test() {
    Assertions.assertThat(postgres.isRunning()).isTrue();
  }

}
