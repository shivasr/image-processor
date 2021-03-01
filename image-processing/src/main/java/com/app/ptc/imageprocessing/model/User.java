package com.app.ptc.imageprocessing.model;

import lombok.*;

@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class User {

    private String tenantId;

    private String clientId;

    private String email;
}
