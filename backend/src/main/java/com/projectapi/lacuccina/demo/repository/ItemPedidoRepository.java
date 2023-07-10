package com.projectapi.lacuccina.demo.repository;

import com.projectapi.lacuccina.demo.model.ItensPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemPedidoRepository extends JpaRepository<ItensPedido, Long> {
    Optional<ItensPedido> findByIdpedidoAndIdmenu(Long idPedido, Long idMenu);
    List<ItensPedido> findByIdpedido(Long idPedido);
}
