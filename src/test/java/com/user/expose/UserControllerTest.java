package com.user.expose;

import com.user.business.UserService;
import com.user.model.dto.ErrorDTO;
import com.user.model.entity.Phone;
import com.user.model.entity.User;
import com.user.model.request.UserRequest;
import com.user.model.response.UserResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureWebTestClient(timeout = "20000")
class UserControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private UserService userService;

    private static UserRequest userRequestOk = new UserRequest();

    private static UserRequest userRequestPutOk = new UserRequest();
    private static User user = new User();
    private static List<User> userList = new ArrayList<>();

    public static UserResponse userResponseoK = new UserResponse();

    public static ErrorDTO errorDTO = new ErrorDTO();


    @BeforeAll
    public static void setup() {

        userResponseoK = UserResponse.builder()
                .id("661c647be887e07dbb463e9e")
                .responseMessage("Usuario creado satisfactoriamente")
                .responseCode("201")
                .build();

        Phone dataPhone = Phone
                .builder()
                .number("1234567")
                .citycode("2")
                .contrycode("56")
                .build();

        List<Phone> phoneList = new ArrayList<>();
        phoneList.add(dataPhone);

        user = User.builder()
                .id("66198f4546eb6c66f0ed3019")
                .name("José Diego Cumpa Levano")
                .email("diego.cl16@hotmail.com")
                .password("Hunter3$")
                .phone(phoneList)
                .status(true)
                .build();

        userList.add(user);

        userRequestOk = UserRequest.builder()
                .name("José Diego Cumpa Levano")
                .email("diego.cl16@hotmail.com")
                .password("Hunter3$")
                .phone(phoneList)
                .status(true)
                .build();

        userRequestPutOk = UserRequest.builder()
                .id("66198f4546eb6c66f0ed3019")
                .name("José Diego Cumpa Levano")
                .email("diego.cl16@hotmail.com")
                .password("Hunter3$")
                .phone(phoneList)
                .status(true)
                .build();

        errorDTO = ErrorDTO.builder()
                .code("200")
                .message("Usuario actualizado satisfactoriamente")
                .build();

    }

    @Test
    void getUsersOkTest() {
        Mockito.when(userService.findAll())
                .thenReturn(Flux.fromIterable(userList));

        ResponseSpec responseSpec = webClient
                .get()
                .uri("/v1/users")
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        responseSpec.expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    void postUserOkTest(){
        Mockito.when(userService.post(any()))
                .thenReturn(Mono.just(userResponseoK));

        ResponseSpec responseSpec = webClient.post()
                .uri("/v1/users")
                .bodyValue(userRequestOk)
                .exchange();

        responseSpec.expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON);

        responseSpec.expectStatus().isCreated();

        responseSpec.expectBody().jsonPath("$.id").isEqualTo(userResponseoK.getId())
                .jsonPath("$.responseMessage").isEqualTo(userResponseoK.getResponseMessage())
                .jsonPath("$.responseCode").isEqualTo(userResponseoK.getResponseCode());

    }

    @Test
    void putUserOkTest(){
        Mockito.when(userService.put(any()))
                .thenReturn(Mono.just(errorDTO));

        ResponseSpec responseSpec = webClient.put()
                .uri("/v1/users/661c647be887e07dbb463e9e")
                .bodyValue(userRequestPutOk)
                .exchange();

        responseSpec.expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON);

        responseSpec.expectStatus().isOk();

        responseSpec.expectBody()
                .jsonPath("$.code").isEqualTo(errorDTO.getCode())
                .jsonPath("$.message").isEqualTo(errorDTO.getMessage());

    }

    @Test
    void deleteUserOkTest(){
        Mockito.when(userService.delete(any()))
                .thenReturn(Mono.empty());

        ResponseSpec responseSpec = webClient.delete()
                .uri("/v1/users/661c647be887e07dbb463e9e")
                .exchange();

        responseSpec.expectStatus().isNoContent();

    }



}
