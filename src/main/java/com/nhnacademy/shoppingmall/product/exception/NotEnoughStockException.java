package com.nhnacademy.shoppingmall.product.exception;

public class NotEnoughStockException extends RuntimeException {
    public NotEnoughStockException(int productId) {
        super(productId + "의 수량 부족");
    }
}
