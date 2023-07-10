package com.projectapi.lacuccina.demo.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.projectapi.lacuccina.demo.DTO.ItensPedidoDTO;
import com.projectapi.lacuccina.demo.DTO.MenuDTO;
import com.projectapi.lacuccina.demo.DTO.PedidoDTO;
import com.projectapi.lacuccina.demo.model.ItensPedido;
import com.projectapi.lacuccina.demo.model.Menu;
import com.projectapi.lacuccina.demo.model.Pedido;
import com.projectapi.lacuccina.demo.repository.MenuRepository;
import com.projectapi.lacuccina.demo.repository.PedidoRepository;
import com.projectapi.lacuccina.demo.repository.ItemPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidioRepository;

    @Autowired
    private ItemPedidoRepository itemPedidioRepository;

    @Autowired
    private MenuRepository menuRepository;

    public List<PedidoDTO> getAllOrders() {
        List<PedidoDTO> ordersList = pedidioRepository.findAll().stream().map(PedidoDTO::new).toList();
        return ordersList;
    }

    public PedidoDTO getOrder(Long id) {
        Optional<Pedido> optionalOrder = pedidioRepository.findById(id);
        return new PedidoDTO(optionalOrder.get());
    }

    public List<ItensPedidoDTO> getOrderItem(Long id) {
        List<ItensPedido> itemList = itemPedidioRepository.findByIdpedido(id);
        List<Long> produtoIds = itemList.stream()
                .map(ItensPedido::getIdmenu)
                .collect(Collectors.toList());
        List<MenuDTO> produtos = menuRepository.findByIdIn(produtoIds);

        List<ItensPedidoDTO> itemDTOList = new ArrayList<>();
        for (ItensPedido item : itemList) {
            Optional<MenuDTO> optionalProduto = produtos.stream()
                    .filter(produto -> produto.id().equals(item.getIdmenu()))
                    .findFirst();

            optionalProduto.ifPresent(produto -> {
                ItensPedidoDTO itemDTO = new ItensPedidoDTO(item, produto);
                itemDTOList.add(itemDTO);
            });
        }

        return itemDTOList;
    }

    public Long closeOrder(Long orderId){
        Optional<Pedido> optionalOrder = pedidioRepository.findById(orderId != null ? orderId : -1);
        LocalDateTime dataHoraAtual = LocalDateTime.now();

        int ano = dataHoraAtual.getYear();
        int mes = dataHoraAtual.getMonthValue();
        int dia = dataHoraAtual.getDayOfMonth();

        int hora = dataHoraAtual.getHour();
        int minuto = dataHoraAtual.getMinute();

        Pedido order = optionalOrder.get();
        order.setStatus("Em preparação");
        order.setDtpedido(String.valueOf(ano)+String.valueOf(mes)+String.valueOf(dia));
        order.setHrpedido(hora+":"+minuto);

        pedidioRepository.save(order);

        return orderId;
    }

    public Long addToOrder(Long orderId, Long itemId, Integer qtd, String obsItem) {
        Optional<Pedido> optionalOrder = pedidioRepository.findById(orderId != null ? orderId : -1);
        Optional<Menu> optionalItem = menuRepository.findById(itemId);
        Optional<ItensPedido> optionalItensPedido = itemPedidioRepository.findByIdpedidoAndIdmenu(orderId, itemId);
        Menu item = optionalItem.get();

        if (optionalOrder.isPresent()) {
            Pedido order = optionalOrder.get();
            order.setQtditens(order.getQtditens() + qtd);
            order.setValor(order.getValor() + (item.getPrice()*qtd));
            pedidioRepository.save(order);

            if (optionalItensPedido.isPresent()) {
                ItensPedido itensPedido = optionalItensPedido.get();
                itensPedido.setQtditem(itensPedido.getQtditem() + qtd);
                if(obsItem.equals("")){
                    itensPedido.setObsitem(itensPedido.getObsitem());
                }else{
                    itensPedido.setObsitem(obsItem);
                }
                itensPedido.setValor(itensPedido.getValor() + (item.getPrice()*qtd));
                itemPedidioRepository.save(itensPedido);
            } else {
                ItensPedido itensPedido = new ItensPedido();
                itensPedido.setIdpedido(orderId);
                itensPedido.setIdmenu(itemId);
                itensPedido.setQtditem(qtd);
                itensPedido.setObsitem(obsItem);
                itensPedido.setValor(item.getPrice()*qtd);
                itemPedidioRepository.save(itensPedido);
            }
            return orderId;
        } else {
            Pedido order = new Pedido();
            order.setValor(item.getPrice()*qtd);
            order.setQtditens(qtd);
            order.setMesa("25");//mesa fixa por enquanto
            order.setStatus("Em andamento");
            pedidioRepository.save(order);

            ItensPedido itensPedido = new ItensPedido();
            itensPedido.setIdpedido(order.getId());
            itensPedido.setIdmenu(itemId);
            itensPedido.setQtditem(qtd);
            itensPedido.setObsitem(obsItem);
            itensPedido.setValor(item.getPrice()*qtd);
            itemPedidioRepository.save(itensPedido);

            return order.getId();
        }
    }

    public void removeFromOrder(Long orderId, Long itemId) {
        Optional<Pedido> optionalOrder = pedidioRepository.findById(orderId);
        Optional<ItensPedido> optionalItensPedido = itemPedidioRepository.findByIdpedidoAndIdmenu(orderId, itemId);
        Pedido order = optionalOrder.get();
        ItensPedido itensPedido = optionalItensPedido.get();

        if (itensPedido.getQtditem() == 1) {
            itemPedidioRepository.delete(itensPedido);
        }

        if (order.getQtditens() == 1) {
            pedidioRepository.delete(order);
            return;
        }

        Optional<Menu> optionalItem = menuRepository.findById(itemId);
        Menu item = optionalItem.get();
        order.setQtditens(order.getQtditens() - 1);
        order.setValor(order.getValor() - item.getPrice());
        itensPedido.setQtditem(itensPedido.getQtditem() - 1);
        itensPedido.setValor(itensPedido.getValor() - item.getPrice());
        pedidioRepository.save(order);
        itemPedidioRepository.save(itensPedido);
    }
}
