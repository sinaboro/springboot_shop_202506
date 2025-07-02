package com.example.shop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class CartDetailDto {

    //item, itemImg,  cartItem ??
    private Long catItemId; //장바구니 상품 아이디
    private String itmeNm; //상품명
    private int price;     //가격
    private int count;     //수량
    private String imgUrl; //상품 이미지 경로

    public CartDetailDto(Long cartItmeId, String itmeNm, int price, int count, String imgUrl) {
        this.catItemId = cartItmeId;
        this.itmeNm = itmeNm;
        this.price = price;
        this.count = count;
        this.imgUrl = imgUrl;
    }
}
