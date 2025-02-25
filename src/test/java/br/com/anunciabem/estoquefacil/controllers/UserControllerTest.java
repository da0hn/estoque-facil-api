package br.com.anunciabem.estoquefacil.controllers;

import br.com.anunciabem.estoquefacil.CleanupAfterTest;
import br.com.anunciabem.estoquefacil.IntegrationTest;
import br.com.anunciabem.estoquefacil.dto.ApiDataResponse;
import br.com.anunciabem.estoquefacil.dto.user.LoginResponse;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;

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
  @CleanupAfterTest
  @Sql(scripts = "/scripts/users.sql")
  @DisplayName("Should create a User")
  void test1() {
    // https://mkyong.com/spring-boot/spring-boot-testcontainers-example/
    // https://medium.com/@hsielei/end-to-end-testing-spring-boot-rest-apis-with-rest-assured-e21765f74263
    // https://medium.com/@mbanaee61/api-testing-in-spring-boot-2a6d69e5c3ce
    final var loginResponse = RestAssured.given()
      .contentType(ContentType.JSON)
      .body(readPayloadRequest("payload/users/authenticate_admin_payload.json"))
      .when()
      .post("/auth/token")
      .thenReturn()
      .as(new TypeRef<ApiDataResponse<LoginResponse>>() { });

    RestAssured.given()
      .contentType(ContentType.JSON)
      .header("Authorization", "Bearer " + loginResponse.getData().token())
      .body(readPayloadRequest("payload/users/create_user_payload.json"))
      .when()
      .post("/users")
      .then()
      .statusCode(HttpStatus.OK.value())
      .body("data", Matchers.notNullValue())
      .body("data.id", Matchers.notNullValue(Long.class))
      .body("timestamp", Matchers.notNullValue(Instant.class))
      .body("message", Matchers.is("User created successfully."));
  }

  @Test
  @CleanupAfterTest
  @Sql("/scripts/username_already_exists.sql")
  @DisplayName("Should not create a User")
  void test2() {
    final var loginResponse = RestAssured.given()
      .contentType(ContentType.JSON)
      .body(readPayloadRequest("payload/users/authenticate_admin_payload.json"))
      .when()
      .post("/auth/token")
      .thenReturn()
      .as(new TypeRef<ApiDataResponse<LoginResponse>>() { });

    RestAssured.given()
      .contentType(ContentType.JSON)
      .header("Authorization", "Bearer " + loginResponse.getData().token())
      .body(readPayloadRequest("payload/users/create_user_payload.json"))
      .when()
      .post("/users")
      .then()
      .statusCode(HttpStatus.BAD_REQUEST.value())
      .body("data.field", Matchers.hasItem("username"))
      .body("data.value", Matchers.hasItem("john.doe"))
      .body("data.message", Matchers.hasItem("Username already exists"))
      .body("timestamp", Matchers.notNullValue(Instant.class))
      .body("message", Matchers.is("Validation failed"))
      .log();
  }

}
