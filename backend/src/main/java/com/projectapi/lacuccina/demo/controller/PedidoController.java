package com.projectapi.lacuccina.demo.controller;

import com.projectapi.lacuccina.demo.DTO.ItensPedidoDTO;
import com.projectapi.lacuccina.demo.DTO.PedidoDTO;
import com.projectapi.lacuccina.demo.DTO.PedidoRequestDTO;
import com.projectapi.lacuccina.demo.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public List<PedidoDTO> getAll() {
        return pedidoService.getAllOrders();
    }

    @GetMapping("/{id}")
    public PedidoDTO getEspecficOrderById(@PathVariable Long id) {
        return pedidoService.getOrder(id);
    }

    @GetMapping("/items/{id}")
    public List<ItensPedidoDTO> getItemOrderById(@PathVariable Long id) {
        return pedidoService.getOrderItem(id);
    }

    @PostMapping
    public Long addToOrder(@RequestBody PedidoRequestDTO pedido) {
        return pedidoService.addToOrder(pedido.orderId(), pedido.menuId(), pedido.qtd(), pedido.obsItem());
    }

    @PostMapping("/finish")
    public Long closeOrder(@RequestBody PedidoRequestDTO pedido) {
        return pedidoService.closeOrder(pedido.orderId());
    }

    @DeleteMapping
    public void removeFromOrder(@RequestBody PedidoRequestDTO pedido) {
        pedidoService.removeFromOrder(pedido.orderId(), pedido.menuId());
    }
}
