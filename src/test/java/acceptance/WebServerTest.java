package acceptance;

import com.google.common.net.MediaType;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 해당 테스트는 WebServer를 실행해야 동작합니다.
 */
public class WebServerTest {

    @Test
    public void sendIndexHtml() {
        ExtractableResponse<Response> 메인_페이지_조회 = RestAssured.given().log().all()
            .when().get("/index.html")
            .then().log().all().extract();

        assertThat(메인_페이지_조회.statusCode()).isEqualTo(HttpStatus.SC_OK);
    }


    @Test
    public void sendFormHtml() {
        ExtractableResponse<Response> 회원가입_페이지_조회 = RestAssured.given().log().all()
            .when().get("/user/form.html")
            .then().log().all().extract();

        assertThat(회원가입_페이지_조회.statusCode()).isEqualTo(HttpStatus.SC_OK);
    }

    @Test
    public void sendRegister() {
        ExtractableResponse<Response> 회원가입_요청 = RestAssured.given().log().all()
            .body("userId=javajigi&password=password&name=JaeSung&email=javajigi%40slipp.net")
            .when().post("/user/create")
            .then().log().all().extract();

        assertThat(회원가입_요청.statusCode()).isEqualTo(HttpStatus.SC_MOVED_TEMPORARILY);
    }

}
