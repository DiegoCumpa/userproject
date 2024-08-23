package com.user.model.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * Class: User *
 *
 * @author Diego Cumpa/>
 *
 * @version 1.0
 */

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Document(collection = "usuarios")
public class User {

    public static final String MSG_NOT_EMPTY = "no puede quedar sin valor";

    @Id
    private String id;
    @NotEmpty(message = "User property 'fullName' can not be empty")
    private String name;
    @NotEmpty(message = "User property 'email' can not be empty")
    private String email;
    @NotEmpty(message = "User property 'address' can not be empty")
    private String password;
    @NotEmpty(message = "User property 'phone' can not be empty")
    private List<Phone> phone;
    private Boolean status;

    private String created;
    private String modified;
    private String token;


}
