package com.shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class OrderItem extends BaseEntity{
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; //주문가격

    private int count; //수량

    public static OrderItem createOrderItem(Item item, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setCount(count);
        orderItem.setOrderPrice(item.getPrice());       //상품 가격을 주문 가격으로 매칭

        item.removeStock(count);        //주문수량 만큼 재고 수량 감소
        return orderItem;
    }
    public int getTotalPrice(){     //총 가격 계산 매소드
        return orderPrice*count;
    }

    public void cancel(){       //주문 취소 시 주문 수량 만큼의 상품 재고를 더해줌
        this.getItem().addStock(count);
    }
}
