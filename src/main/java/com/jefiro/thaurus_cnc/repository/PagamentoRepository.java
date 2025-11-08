package com.jefiro.thaurus_cnc.repository;

import com.jefiro.thaurus_cnc.model.Infinitepay.Pagamentos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamentos, Long> {
}
