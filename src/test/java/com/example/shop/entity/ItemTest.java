package com.example.shop.entity;

import com.example.shop.constant.ItemSellStatus;
import com.example.shop.repository.ItemImgRepository;
import com.example.shop.repository.ItemRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryFactory;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
class ItemTest {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemImgRepository itemImgRepository;

    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;

    @BeforeEach
    void setUp() {
        queryFactory = new JPAQueryFactory(em);
    }

    // itemNm 조회
    @Test
    public void testFindByItemNm(){
        List<Item> items =
                itemRepository.findByItemNm("자바");

        items.forEach( item-> log.info(item.toString()));
        //items.forEach(System.out::println);
        log.info("---------------------QueryDSL---------------------------");

        QItem qItem = QItem.item;

        List<Item> item2 = queryFactory
                .select(qItem)
                .from(qItem)
                .where(qItem.itemNm.eq("자바"))
                .fetch();
        item2.forEach( item-> log.info(item.toString()));
    }

    //And
    @Test
    public void testFindByItemNmAndPrice(){
        QItem qItem = QItem.item;

        List<Item> item = queryFactory
                .selectFrom(qItem)
                .where(
                        qItem.itemNm.eq("자바"),
                        qItem.price.gt(10000)
                )
                .fetch();

        log.info(item.toString());
    }

    //OR 조건 검색
    @Test
    public void testFindByitemNmOrItemDetail(){

        QItem qItem = QItem.item;

        List<Item> items = queryFactory
                .select(qItem)
                .from(qItem)
                .where(
                        qItem.itemNm.contains("부트")
                        .or(qItem.itemDetail.contains("자바"))
                )
                .fetch();

        items.forEach( item-> log.info(item.toString()));
    }

    //Enum 조건검색
    @Test
    public void testFindBySellStatus(){
        QItem qItem = QItem.item;

        List<Item> items = queryFactory
                .selectFrom(qItem)
                .where(qItem.itemSellStatus.eq(ItemSellStatus.SOLD_OUT))
                .fetch();

        items.forEach( item-> log.info(item.toString()));
    }

    //동적 조건 검색(BooleanBuilder사용)
    @Test
    public void testDynamicSearch(){
        QItem qItem = QItem.item;
        BooleanBuilder builder = new BooleanBuilder();

        String searchNm = "스프링";
        Integer minPrice = 10000;

        if(searchNm != null){
            builder.and(qItem.itemNm.contains(searchNm));
        }

        if(minPrice != null){
            builder.and(qItem.price.gt(minPrice));
        }

        log.info("builder :  {} ", builder.toString());
        List<Item> items = queryFactory
                .selectFrom(qItem)
                .where(builder)
                .fetch();
        items.forEach( item-> log.info(item.toString()));
    }

    // 정렬
    @Test
    public void testPaging(){
        QItem qItem = QItem.item;

        List<Item> items = queryFactory
                .selectFrom(qItem)
                .where(qItem.price.gt(1000))
                .orderBy(qItem.price.asc())
                .fetch();

        log.info(items.toString());
    }

    // 정렬 + 페이징 처리
    @Test
    public void testPagingAndSort(){
        QItem qItem = QItem.item;

        List<Item> items = queryFactory
                .selectFrom(qItem)
//                .where(qItem.i.gt(1000))
                .orderBy(qItem.id.asc())
                .offset(2) //시작 위치 0번 인텍스부터
                .limit(3)  //최대 2개 가져오기
                .fetch();

        log.info(items.toString());
    }

    //그룹화 , 집계함수(count, max, avg등)
    @Test
    public void testAggreegateFunction(){
        QItem qItem = QItem.item;

        List<Tuple> fetch = queryFactory
                .select(
                        qItem.itemSellStatus,
                        qItem.price.avg()
                )
                .from(qItem)
                .groupBy(qItem.itemSellStatus)
                .fetch();

        fetch.stream().forEach( item-> log.info(item.toString()));
    }

    //ItemImg 조회
    @Test
    public void testItemImg(){
        QItemImg qItemImg = QItemImg.itemImg;

        List<ItemImg> result = queryFactory
                .selectFrom(qItemImg)
                .where(qItemImg.repimgYn.eq("Y"))
                .fetch();
        result.forEach( item-> log.info(item.toString()));
    }

    //ItemImg , Itme Join
    /*
        select  i.*
        from item_img i
        join item t on i.item_id  = t.item_id
        where t.item_nm like "%자바%";
     */
    @Test
    public void testJoin(){
        QItem qItem = QItem.item;
        QItemImg qItemImg = QItemImg.itemImg;

        List<ItemImg> result = queryFactory
                .selectFrom(qItemImg)
                .join(qItemImg.item, qItem)
                .where(qItem.itemNm.contains("자바"))
                .fetch();

        log.info(result.toString());
    }

}