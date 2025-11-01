package com.jefiro.thaurus_cnc.dto.melhorenvio; // Ou onde ficam seus modelos/entidades

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "melhorenvio_token")
public class MelhorEnvioToken {

    @Id
    private Long id;

    @Column(name = "access_token", length = 4096)
    private String accessToken;

    @Column(name = "refresh_token", length = 4096)
    private String refreshToken;

    @Column(name = "expires_at")
    private Instant expiresAt;

}