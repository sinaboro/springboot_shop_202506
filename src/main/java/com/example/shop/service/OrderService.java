package com.example.shop.service;

import com.example.shop.dto.OrderDto;
import com.example.shop.entity.Item;
import com.example.shop.entity.Member;
import com.example.shop.entity.Order;
import com.example.shop.entity.OrderItem;
import com.example.shop.repository.ItemRepository;
import com.example.shop.repository.MemberRepository;
import com.example.shop.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    //   orderDto(맥주, 2병), email(1번 테이블)
    public Long order(OrderDto orderDto, String email) {

        //맥주 있는지 확인( 상품 정보 확인)
        Item item = itemRepository.findById(orderDto.getItemId())
                .orElseThrow(() -> new EntityNotFoundException());

        // email(1번 테이블)
        Member member = memberRepository.findByEmail(email);

        List<OrderItem> orderItemList = new ArrayList<>();

        //주문서 작성(맥주, 2병,...)
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());

        orderItemList.add(orderItem);

        //직원에게 주문서 전달...
        Order order = Order.createOrder(member, orderItemList);

        //주방 메시지 전달....DB저장(Order, OrderItem 테이블 저장)
        orderRepository.save(order);

        return order.getId();
    }
}
