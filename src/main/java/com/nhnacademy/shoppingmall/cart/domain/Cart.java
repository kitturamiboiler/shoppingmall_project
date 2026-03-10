package com.nhnacademy.shoppingmall.cart.domain;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private final Map<Integer, Integer> items = new HashMap<>();
    public void addItem(int productId, int quantity, int currentStock) {
        int existingQuantity = items.getOrDefault(productId, 0);
        int totalQuantity = existingQuantity + quantity;
        if (totalQuantity > currentStock) {
            throw new RuntimeException("재고 부족(현재: ) " + currentStock + ")");
        }
        items.put(productId, totalQuantity);
    }
    public Map<Integer, Integer> getItems(){
        return Map.copyOf(items);
    }
    public int getTotalItemCount() {
        return items.values().stream().mapToInt(Integer::intValue).sum();
    }
    public void updateQuantity(int productId, int quantity, int currentStock) {
        if (quantity <= 0) {
            items.remove(productId);
            return;
        }
        if (quantity > currentStock) {
            throw new RuntimeException("재고 부족으로 수량 변경 불가능");
        }
        items.put(productId, quantity);
    }
    public void removeItem(int productId) {
        items.remove(productId);
    }
}
