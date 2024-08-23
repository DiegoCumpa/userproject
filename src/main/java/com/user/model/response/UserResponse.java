package com.user.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
/**
 * Class: UserBookResponse *
 *
 * @author Diego Cumpa/>
 *
 * @version 1.0
 */

@Builder
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
public class UserResponse {

    private String id;
    private String created;
    private String modified;
    private String token;
    private Boolean isactive;

    private String responseCode;
    private String responseMessage;


}
