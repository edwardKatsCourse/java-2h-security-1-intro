package com.telran.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

@Document("user_session")
public class UserSession {

    @Id
    private String id;

    @DBRef
    private User user;

    @Indexed(unique = true)
    private String token;

    private Boolean isValid;
}
