package com.user.repository;

import com.user.model.entity.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;;

/**
 * Class: UserRepository *
 *
 * @author Diego Cumpa/>
 *
 * @version 1.0
 */
@Repository
public interface UserRepository extends ReactiveMongoRepository<User,String> {

    Mono<User> findByEmail(String email);

}
