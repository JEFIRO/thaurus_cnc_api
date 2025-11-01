package com.jefiro.thaurus_cnc.repository;

import com.jefiro.thaurus_cnc.dto.melhorenvio.MelhorEnvioToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<MelhorEnvioToken, Long> {
}
