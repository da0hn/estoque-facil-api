package br.com.anunciabem.estoquefacil;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class IntegrationTest {

  @Container
  @ServiceConnection
  protected static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13.3");

  private static final ObjectMapper objectMapper = new ObjectMapper();

  @SneakyThrows
  protected static String readPayloadRequest(final String path) {
    return IOUtils.toString(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream(path)), StandardCharsets.UTF_8);
  }



}
