package com.user.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import javax.validation.constraints.NotEmpty;

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
public class Phone {

    @NotEmpty(message = "User property 'number' can not be empty")
    private String number;
    @NotEmpty(message = "User property 'citycode' can not be empty")
    private String citycode;
    @NotEmpty(message = "User property 'countrycode' can not be empty")
    private String contrycode;

}
