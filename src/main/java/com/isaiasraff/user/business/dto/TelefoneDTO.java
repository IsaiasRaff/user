package com.isaiasraff.user.business.dto;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TelefoneDTO {

    private Long id;
    private String ddd;
    private String numero;



}
