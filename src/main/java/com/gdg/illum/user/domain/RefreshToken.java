package com.gdg.illum.user.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class RefreshToken {

    @Id
    @GeneratedValue
    private Long id;

    @Setter
    private String tokenValue;
}
