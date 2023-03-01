package com.stripe.Users;

import jakarta.validation.constraints.NotEmpty;
import jdk.jfr.DataAmount;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "users", catalog = "public")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long userId;
    @NotEmpty
    @Length(min=5, max=32)
    String username;
    @Length(min=8, max=64)
    String password;
    @Length(min=5)
    String email;
    LocalDateTime lastLoggedIn;
    String salt;
}
