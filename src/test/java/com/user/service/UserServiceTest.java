package com.user.service;

import com.user.business.UserService;
import com.user.model.dto.ErrorDTO;
import com.user.model.entity.Phone;
import com.user.model.entity.User;
import com.user.model.request.UserRequest;
import com.user.model.response.UserResponse;
import com.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private static UserRequest userRequestOk = new UserRequest();

    private static UserRequest userRequestPutOk = new UserRequest();
    private static User user = new User();

    private static Phone phone =new Phone();
    private static List<User> userList = new ArrayList<>();

    private static final List<Phone>  phoneList =new ArrayList<>();

    public static UserResponse userResponseoK = new UserResponse();

    private static User userReqPost = new User();

    private static UserRequest userReqPosSomeNull = new UserRequest();

    private static ErrorDTO errorDTO = new ErrorDTO();

    private static ErrorDTO errorDTOUpdate = new ErrorDTO();

    @BeforeAll
    static void setup() throws IOException {

        errorDTOUpdate = ErrorDTO.builder()
                .code("200")
                .message("Usuario actualizado satisfactoriamente")
                .build();

        phone = Phone
                .builder()
                .number("1234567")
                .citycode("2")
                .contrycode("56")
                .build();


        phoneList.add(phone);

        userResponseoK = UserResponse.builder()
                .id("66198f4546eb6c66f0ed3019")
                .responseMessage("Usuario creado satisfactoriamente")
                .created("22-08-2024")
                .modified("")
                .token("eyJhbGciOiJIUzUxMiJ9..RO0lssuwIE_wXV0tBWNeSLusJK1CppZLgv_LawHFavbFvE7XqoXAFHLLWCZUMPHWqiQwqggwIiEhwCDkRbxeSQ")
                .isactive(true)
                .responseCode("201")
                .build();



        user = User.builder()
                .id("66198f4546eb6c66f0ed3019")
                .name("José Diego Cumpa Levano")
                .email("diego.cl16@hotmail.com")
                .password("Hunter3$")
                .phone(phoneList)
                .created("22-08-2024")
                .modified(null)
                .token("eyJhbGciOiJIUzUxMiJ9..RO0lssuwIE_wXV0tBWNeSLusJK1CppZLgv_LawHFavbFvE7XqoXAFHLLWCZUMPHWqiQwqggwIiEhwCDkRbxeSQ")
                .status(true)
                .build();

        userReqPost = User.builder()
                .name("José Diego Cumpa Levano")
                .email("diego.cl16@hotmail.com")
                .password("Hunter3$")
                .phone(phoneList)
                .modified(null)
                .token("eyJhbGciOiJIUzUxMiJ9..RO0lssuwIE_wXV0tBWNeSLusJK1CppZLgv_LawHFavbFvE7XqoXAFHLLWCZUMPHWqiQwqggwIiEhwCDkRbxeSQ")
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


    }

    @Test
    void getAllUsersOkTest(){

        when(userRepository.findAll())
                .thenReturn(Flux.fromIterable(userList));

        Flux<User> result = userService.findAll();

        StepVerifier.create(result)
                .assertNext(next ->{
                    assertEquals(next.getId(),userList.get(0).getId());
                })
                .verifyComplete();

    }



    @Test
    void postUserWhenNameIsNullTest(){

        errorDTO = ErrorDTO.builder()
                .code("P-500")
                .message("El campo fullname es necesario")
                .build();

        Phone dataPhone = Phone
                .builder()
                .number("1234567")
                .citycode("2")
                .contrycode("56")
                .build();

        List<Phone> phoneList = new ArrayList<>();
        phoneList.add(dataPhone);

        userReqPosSomeNull = UserRequest.builder()
                .name(null)
                .email("diego.cl16@hotmail.com")
                .password("Hunter3$")
                .phone(phoneList)
                .status(true)
                .build();

        when(userRepository.save(user))
                .thenReturn(Mono.empty());

        Mono<ErrorDTO> result = userService.post(userReqPosSomeNull)
                .thenReturn(errorDTO);

        StepVerifier.create(result)
                .assertNext(next -> {
                    assertEquals(next.getCode(), errorDTO.getCode());
                    assertEquals(next.getMessage(), errorDTO.getMessage());
                }).expectError();

    }

    @Test
    void postUserWhenEmailIsNullTest(){

        errorDTO = ErrorDTO.builder()
                .code("P-500")
                .message("El campo email es necesario")
                .build();

        Phone dataPhone = Phone
                .builder()
                .number("1234567")
                .citycode("2")
                .contrycode("56")
                .build();

        List<Phone> phoneList = new ArrayList<>();
        phoneList.add(dataPhone);

        userReqPosSomeNull = UserRequest.builder()
                .name("José Diego Cumpa Levano")
                .email("")
                .password("Hunter3$")
                .phone(phoneList)
                .status(true)
                .build();

        when(userRepository.save(user))
                .thenReturn(Mono.empty());

        Mono<ErrorDTO> result = userService.post(userReqPosSomeNull)
                .thenReturn(errorDTO);

        StepVerifier.create(result)
                .assertNext(next -> {
                    assertEquals(next.getCode(), errorDTO.getCode());
                    assertEquals(next.getMessage(), errorDTO.getMessage());
                }).expectError();

    }

    @Test
    void postUserWhenPhoneIsNullTest(){

        errorDTO = ErrorDTO.builder()
                .code("P-500")
                .message("El campo phone es necesario")
                .build();

        userReqPosSomeNull = UserRequest.builder()
                .name("José Diego Cumpa Levano")
                .email("diego.cl16@hotmail.com")
                .password("Hunter3$")
                .phone(null)
                .status(true)
                .build();

        when(userRepository.save(user))
                .thenReturn(Mono.empty());

        Mono<ErrorDTO> result = userService.post(userReqPosSomeNull)
                .thenReturn(errorDTO);

        StepVerifier.create(result)
                .assertNext(next -> {
                    assertEquals(next.getCode(), errorDTO.getCode());
                    assertEquals(next.getMessage(), errorDTO.getMessage());
                }).expectError();

    }


    @Test
    void putUserOkTest(){

        when(userRepository.findById("66198f4546eb6c66f0ed3019"))
                .thenReturn(Mono.just(user));

        when(userRepository.save(any()))
                .thenReturn(Mono.just(user));

        Mono<ErrorDTO> result = userService.put(userRequestPutOk);

        StepVerifier.create(result)
                .assertNext(next -> {
                    assertEquals(next.getCode(), errorDTOUpdate.getCode());
                }).verifyComplete();

    }

    @Test
    void deleteUserOkTest(){

        when(userRepository.findById("66198f4546eb6c66f0ed3019"))
                .thenReturn(Mono.just(user));

        when(userRepository.deleteById("66198f4546eb6c66f0ed3019"))
                .thenReturn(Mono.empty());

        Mono<Void> result = userService.delete("66198f4546eb6c66f0ed3019");

        StepVerifier.create(result)
                .expectComplete()
                .verify();

    }



}
