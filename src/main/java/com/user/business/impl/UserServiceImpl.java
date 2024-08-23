package com.user.business.impl;

import com.user.model.dto.ErrorDTO;
import com.user.model.entity.Phone;
import com.user.model.entity.User;
import com.user.model.request.UserRequest;
import com.user.util.Constants;
import com.user.business.UserService;
import com.user.model.response.UserResponse;
import com.user.repository.UserRepository;
import com.user.util.Validator;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Class: UserService *
 *
 * @author Diego Cumpa/>
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public Mono<UserResponse> post(UserRequest userRequest) {

        JwtBuilder myBuilder = Jwts.builder();
        String key = "8963SZ7KKZlDb8OzfHJDKXvHtyNjklMZeZcZYxXb0mxHGPvu3sKKkBBuP7WsATf1srPgt1Oprg77XS6PYyMo6sqgcaSoxUthnRf";
        String signatureAlgo = "HS512";

        Key mySigningKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(key));
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.forName(signatureAlgo);
        myBuilder.signWith(mySigningKey, signatureAlgorithm);

        String jwtToken = myBuilder.compact();

        String dateNow = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        if(!Validator.validate(userRequest.getEmail())){
            return Mono.error(new RuntimeException(Constants.VALIDATE_EMAIL));
        }else if(!Validator.validPassword(userRequest.getPassword())){
            return Mono.error(new RuntimeException(Constants.VALIDATE_PASSWORD));
        }
        else if (userRequest.getName() == null || userRequest.getName().equals(Constants.SPACE)) {
            return Mono.error(new RuntimeException(Constants.NAME_NECESARY));
        } else if (userRequest.getEmail() == null || userRequest.getEmail().equals(Constants.SPACE)) {
            return Mono.error(new RuntimeException(Constants.EMAIL_NECESARY));
        } else if (userRequest.getPassword() == null || userRequest.getPassword().equals(Constants.SPACE)) {
            return Mono.error(new RuntimeException(Constants.PASSWORD_NECESARY));
        } else if (userRequest.getPhone() == null || userRequest.getPhone().isEmpty()) {
            return Mono.error(new RuntimeException(Constants.PHONE_NECESARY));
        } else {

            Phone dataPhone = Phone
                    .builder()
                    .number(userRequest.getPhone().get(0).getNumber())
                    .citycode(userRequest.getPhone().get(0).getCitycode())
                    .contrycode(userRequest.getPhone().get(0).getContrycode())
                    .build();

            List<Phone> phoneList = new ArrayList<>();
            phoneList.add(dataPhone);

            return userRepository.findByEmail(userRequest.getEmail())
                    .flatMap(__ ->Mono.error(new RuntimeException(Constants.EMAIL_EXIST)))
                    .switchIfEmpty(Mono.defer(() ->
             userRepository.save(User.builder()
                    .name(userRequest.getName())
                    .email(userRequest.getEmail())
                    .phone(phoneList)
                    .password(userRequest.getPassword())
                    .created(dateNow)
                    .modified(null)
                    .token(jwtToken)
                    .status(true)
                    .build()).map(r -> {
                UserResponse response = new UserResponse();
                response.setId(r.getId());
                response.setCreated(r.getCreated());
                response.setModified(response.getModified());
                response.setToken(r.getToken());
                response.setIsactive(r.getStatus());
                response.setResponseCode(String.valueOf(HttpResponseStatus.CREATED.code()));
                response.setResponseMessage(Constants.USER_CREATED);
                return response;
            }))).cast(UserResponse.class);
        }
    }

    @Override
    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Mono<ErrorDTO> put(UserRequest userRequest) {

        String dateNow = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        if(!Validator.validate(userRequest.getEmail())){
            return Mono.error(new RuntimeException(Constants.VALIDATE_EMAIL));
        }else if(!Validator.validPassword(userRequest.getPassword())){
            return Mono.error(new RuntimeException(Constants.VALIDATE_PASSWORD));
        }
        else if (userRequest.getName() == null || userRequest.getName().equals(Constants.SPACE)) {
            return Mono.error(new RuntimeException(Constants.NAME_NECESARY));
        } else if (userRequest.getEmail() == null || userRequest.getEmail().equals(Constants.SPACE)) {
            return Mono.error(new RuntimeException(Constants.EMAIL_NECESARY));
        } else if (userRequest.getPassword() == null || userRequest.getPassword().equals(Constants.SPACE)) {
            return Mono.error(new RuntimeException(Constants.PASSWORD_NECESARY));
        } else if (userRequest.getPhone() == null || userRequest.getPhone().isEmpty()) {
            return Mono.error(new RuntimeException(Constants.PHONE_NECESARY));
        } else {
            return userRepository.findById(userRequest.getId())
                    .switchIfEmpty(
                            Mono.error(new RuntimeException(Constants.USER_NOT_FOUND))
                    ).map(rs -> {

                        Phone dataPhone = Phone
                                .builder()
                                .number(userRequest.getPhone().get(0).getNumber())
                                .citycode(userRequest.getPhone().get(0).getCitycode())
                                .contrycode(userRequest.getPhone().get(0).getContrycode())
                                .build();

                        List<Phone> phoneList = new ArrayList<>();
                        phoneList.add(dataPhone);


                        rs.setName(userRequest.getName());
                        rs.setEmail(userRequest.getEmail());
                        rs.setPassword(userRequest.getPassword());
                        rs.setModified(dateNow);
                        rs.setPhone(phoneList);

                        return rs;
                    }).flatMap(s -> userRepository.save(s).map(x -> {
                        ErrorDTO response = new ErrorDTO();
                        response.setCode(String.valueOf(HttpResponseStatus.OK.code()));
                        response.setMessage(Constants.USER_UPDATED);
                        return response;
                    }));
        }
    }

    @Override
    public Mono<Void> delete(String id) {
        return userRepository.findById(id)
                .switchIfEmpty(
                        Mono.error(new RuntimeException(Constants.USER_NOT_FOUND))
                ).flatMap(r -> userRepository.deleteById(id))
                .then();
    }
}
