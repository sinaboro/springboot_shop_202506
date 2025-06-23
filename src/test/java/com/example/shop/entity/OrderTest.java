package com.example.shop.entity;

import com.example.shop.constant.ItemSellStatus;
import com.example.shop.dto.MemberFormDto;
import com.example.shop.repository.ItemRepository;
import com.example.shop.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Transactional
class OrderTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @PersistenceContext
    EntityManager em;
    static int i=1;

    public Item createItem(){
        Item item = new Item();

        item.setItemNm("테스트 상품"+i);
        item.setPrice(10000*i);
        item.setItemDetail("상세설명"+i);
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100*i);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        i++;
        return item;
    }

    @Test
    public void cacadeTest(){
        Order order = new Order();

        for(int i=0; i<3; i++){
            Item item = createItem();
            itemRepository.save(item);

            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setOrderPrice(1000);
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem);
        }

        log.info("--------------------------------");
        order.getOrderItems().forEach(orderItem -> {
            log.info(orderItem.toString());
        });

        orderRepository.save(order);

        em.flush();
        em.clear();

        Order savedOrder =  orderRepository.findById(order.getId())
                .orElseThrow(()-> new EntityNotFoundException("ID 없음"));
        assertEquals(3, savedOrder.getOrderItems().size());
    }
}