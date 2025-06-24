package com.example.shop.dto;

import com.example.shop.constant.ItemSellStatus;
import com.example.shop.entity.Item;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Getter@Setter
public class ItemFormDto {

    private Long id;  //상품 코드

    //설명: null, "", " " 등 공백 문자열까지 포함해서 모두 거부
    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String itemNm; //상품 명

    //설명: null이 아닌지만 검사함, 허용되는 값 예시: " " (공백 문자열), "" (빈 문자열)
    @NotNull(message = "가격은 필수 입력 값입니다.")
    private int price; //가격

    @NotNull(message = "재고는 필수 입력 값입니다.")
    private int stockNumber; //재고수량

    @NotBlank(message = "상세설명은 필수 입력 값입니다.")
    private String itemDetail; //상품 상세 설명

    private ItemSellStatus itemSellStatus; //상품 판매 상태

    private List<ItemImgDto> itemImgDtoList = new ArrayList<>();

    private List<Long> itemImgId = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    //ItemFormDto(자기자신) => Item 변환
    public Item createItem(){
        return modelMapper.map(this, Item.class);
    }

    //Item -> ItemFormDto 변환
    public static ItemFormDto of(Item item){
        return modelMapper.map(item, ItemFormDto.class);
    }
}
