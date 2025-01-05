package sail.study.redis.service;

import sail.study.redis.dto.OrderInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BeforeOrderServiceJava {

    // 상품 DB
//    private final Map<String, Integer> productDatabase = new HashMap<>();
    private final Map<String, Integer> productDatabase = new ConcurrentHashMap<>();
    // 가장 최근 주문 정보를 저장하는 DB
    private final Map<String, OrderInfo> latestOrderDatabase = new HashMap<>();

    public BeforeOrderServiceJava() {
        // 초기 상품 데이터
        productDatabase.put("apple", 100);
        productDatabase.put("banana", 50);
        productDatabase.put("orange", 75);
    }

    public void order(String productName, int amount) {
        productDatabase.compute(productName, (key, currentStock) -> {
            if (currentStock == null) {
                currentStock = 0; // 기본값
            }

            try {
                Thread.sleep(1); // 동시성 이슈 유발을 위한 인위적 지연
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }

            String name;
            if (currentStock >= amount) {
                name = Thread.currentThread().getName().split("-")[3];
                System.out.println("Thread " + name + " 주문 정보: " + productName + ": 1건 ([" + amount + "])");
                latestOrderDatabase.put(productName, new OrderInfo(productName, amount, System.currentTimeMillis()));

                return currentStock - amount; // 업데이트된 재고 반환
            } else {
                return currentStock; // 재고가 부족하면 변경하지 않음
            }
        });
    }

    // 주문 처리 메서드
//    public void order(String productName, int amount) {
//        Integer currentStock = productDatabase.getOrDefault(productName, 0);
//
//        try {
//            Thread.sleep(1); // 동시성 이슈 유발을 위한 인위적 지연
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//            throw new RuntimeException(e);
//        }
//
//        if (currentStock >= amount) {
//            System.out.println("Current Thread : " + Thread.currentThread().getName() +
//                    " - CurrentStock : " + currentStock + " - Order : " + amount);
//            productDatabase.put(productName, currentStock - amount);
//            latestOrderDatabase.put(productName, new OrderInfo(productName, amount, System.currentTimeMillis()));
//            System.out.println("latestOrderDatabase = " + latestOrderDatabase);
//        }
//    }

    // 재고 조회
    public int getStock(String productName) {
        return productDatabase.getOrDefault(productName, 0);
    }
}