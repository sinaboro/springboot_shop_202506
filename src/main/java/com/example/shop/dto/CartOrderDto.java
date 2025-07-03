package com.example.shop.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter@Setter
public class CartOrderDto {

    private Long cartItemId;

    //{"cartOrderDtoList":[{"cartItemId":"253"},{"cartItemId":"252"},{"cartItemId":"1"}]}
    private List<CartOrderDto> cartOrderDtoList;
}
