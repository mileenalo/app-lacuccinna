package com.projectapi.lacuccina.demo.repository;

import com.projectapi.lacuccina.demo.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
