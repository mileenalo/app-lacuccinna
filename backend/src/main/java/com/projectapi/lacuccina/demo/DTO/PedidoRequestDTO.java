package com.projectapi.lacuccina.demo.DTO;

public record PedidoRequestDTO(Long orderId, Long menuId, Integer qtd, String obsItem){
}
