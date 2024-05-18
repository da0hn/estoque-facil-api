package br.com.anunciabem.estoquefacil.controllers;

import br.com.anunciabem.estoquefacil.IntegrationTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;

@Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest extends IntegrationTest {

  @LocalServerPort
  private Integer serverPort;

  @BeforeEach
  void setUp() {
    RestAssured.port = this.serverPort;
  }

  @Test
  @Sql("/scripts/users.sql")
  @DisplayName("Should create a User")
  void test1() {
    // FIXME: adicionar chamada HTTP para realizar login e obter o token de acesso
    // https://mkyong.com/spring-boot/spring-boot-testcontainers-example/
    // https://medium.com/@hsielei/end-to-end-testing-spring-boot-rest-apis-with-rest-assured-e21765f74263
    // https://medium.com/@mbanaee61/api-testing-in-spring-boot-2a6d69e5c3ce
    final var payload = readPayloadRequest("payload/users/create_user_payload.json");
    RestAssured.given()
      .contentType(ContentType.JSON)
      .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdXRob3JpdGllcyI6WyJST0xFX0FETUlOIl0sInN1YiI6ImFkbWluaXN0cmF0b3IiLCJpYXQiOjE3MTYwNTc3MTYsImlzcyI6ImVzdG9xdWUtZmFjaWwiLCJleHAiOjE4MTYzMTY5MTZ9.RyvRn0JYO4w8MajsdzuYo53qXNFr0PjcwweNlTbJ3Nw")
      .body(payload)
      .when()
      .post("/users")
      .then()
      .statusCode(200)
      .body("data", Matchers.notNullValue())
      .body("data.id", Matchers.notNullValue(Long.class))
      .body("timestamp", Matchers.notNullValue(Instant.class))
      .body("message", Matchers.is("User created successfully."))
    ;
  }

}
