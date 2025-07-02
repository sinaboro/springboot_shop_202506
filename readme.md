# 1. Shop Project

# 2. Tools
### 2.1 spring framework - 6.1.x
### 2.2 Springboot - 3.5
### 2.3 Language - java 17
### 2.4 DB - Mysql8.0
### 2.5 ORM - JPA
### 2.6 Build Tool - Gradle
### 2.7 IDE(통합개발환경) - IntelliJ

'''java
@Query("select new com.example.shop.dto.CartDetailDto(ci.id, i.itemNm," +
"i.price, ci.count, im.imgUrl) " +
"from CartItem ci, ItemImg  im " +
"join ci.item i " +
"where ci.cart.id = :cartId " +
"and im.item.id = ci.item.id " +
"and im.repimgYn == 'Y' " +
"order by ci.regTime desc"
)
List<CartDetailDto> findCartDetatilDtolist(Long cartId);
'''

<pre><code>```sql 
select ci.cart_item_id, i.item_nm, i.price, ci.count, im.img_url
from cart_item ci 
join item i
on ci.item_id = i.item_id
join item_img im
on i.item_id  = im.item_id
where im.repimg_Yn = 'Y'
order by ci.reg_time desc;
```</code></pre>