package com.user.expose;

import com.user.model.dto.ErrorDTO;
import com.user.model.entity.User;
import com.user.model.request.UserRequest;
import com.user.model.response.UserResponse;
import com.user.business.UserService;
import com.user.util.Constants;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class: UserController *
 *
 * @author Diego Cumpa/>
 * @version 1.0
 */

@RestController
@RequestMapping("/v1")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    @RateLimiter(name= "postUser", fallbackMethod = "fallBackIndexPost")
    public Mono<UserResponse> indexPost(@RequestBody UserRequest userRequest) {
        return userService.post(userRequest);
    }

    public Mono<UserResponse> fallBackIndexPost(Exception e){
        return Mono.just(UserResponse.builder().id(Constants.COD_200)
                .responseMessage(Constants.EXCEED_TRY)
                .responseCode(String.valueOf(HttpStatus.CONFLICT))
                .build());
    }

    @GetMapping("/users")
    @RateLimiter(name= "getUser", fallbackMethod= "fallBackIndexGet")
    public Flux<User> indexGet(){
        return userService.findAll();
    }

    public Flux<UserResponse> fallBackIndexGet(Exception e) {
        List<UserResponse> userResponseList = new ArrayList<>();
        userResponseList.add(UserResponse.builder().id(Constants.COD_200)
                .responseMessage(Constants.EXCEED_TRY)
                .responseCode(String.valueOf(HttpStatus.CONFLICT))
                .build());
        return Flux.fromIterable(userResponseList);
    }

    @PutMapping("/users/{userId}")
    @RateLimiter(name= "putUser", fallbackMethod = "fallBackIndexPut")
    public Mono<ErrorDTO> indexPut(@RequestBody UserRequest userRequest,
                                   @PathVariable("userId") String userId
                                       ){
        return userService.put(UserRequest.builder()
                .id(userId)
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .phone(Collections.singletonList(userRequest.getPhone().get(0)))
                .status(userRequest.getStatus())
                .build());
    }

    public Mono<UserResponse> fallBackIndexPut(Exception e){
        return Mono.just(UserResponse.builder().id(Constants.COD_200)
                .responseMessage(Constants.EXCEED_TRY)
                .responseCode(String.valueOf(HttpStatus.CONFLICT))
                .build());
    }

    @DeleteMapping("/users/{userId}")
    @RateLimiter(name= "deleteUser", fallbackMethod = "fallBackIndexDelete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> indexDelete(@PathVariable("userId") String userId){
        return userService.delete(userId);
    }

    public Mono<Void> fallBackIndexDelete(Exception e){
        return Mono.empty();
    }

}
