package com.projectapi.lacuccina.demo.DTO;

import com.projectapi.lacuccina.demo.model.Pedido;

public record PedidoDTO(Long id, String mesa, String dtPedido, String hrPedido, String status, Integer qtdItens, Float valor) {
    public PedidoDTO(Pedido pedido){
        this(pedido.getId(), pedido.getMesa(), pedido.getDtpedido(), pedido.getHrpedido(), pedido.getStatus(), pedido.getQtditens(), pedido.getValor());
    }
}

