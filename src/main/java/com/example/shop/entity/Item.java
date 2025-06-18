package com.example.shop.entity;

import com.example.shop.constant.ItemSellStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "item")
@Getter
@ToString
@Setter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  //상품 코드

    @Column(nullable = false, length = 50)
    private String itemNm; //상품 명

    @Column(nullable = false, name="price")
    private int price; //가격

    @Column(nullable = false)
    private int stockNumber; //재고수량

    @Lob
    @Column(nullable = false)
    private String itemDetail; //상품 상세 설명

    @Enumerated(EnumType.STRING) //필수
    private ItemSellStatus itemSellStatus; //상품 판매 상태
    
    private LocalDateTime regTime; //등록 시간
    
    private LocalDateTime updateTiem; //수정 시간
    
    
    
    
}
