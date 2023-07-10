package com.projectapi.lacuccina.demo.DTO;

import com.projectapi.lacuccina.demo.model.ItensPedido;
public record ItensPedidoDTO(ItensPedido pedido, MenuDTO produto) {
    public ItensPedidoDTO(ItensPedido pedido, MenuDTO produto){
        this.pedido = pedido;
        this.produto = produto;
        //this(pedido.getId(), pedido.getIdpedido(), pedido.getIdmenu(), pedido.getQtditem(), pedido.getValor());
    }

}