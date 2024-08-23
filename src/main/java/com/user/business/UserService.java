package com.user.business;

import com.user.model.dto.ErrorDTO;
import com.user.model.entity.User;
import com.user.model.request.UserRequest;
import com.user.model.response.UserResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Class: UserService *
 *
 * @author Diego Cumpa/>
 *
 * @version 1.0
 */
public interface UserService {
    Mono<UserResponse> post(UserRequest userRequest);

    Flux<User> findAll();

    Mono<ErrorDTO> put(UserRequest userRequest);

    Mono<Void> delete(String id);
}
