package com.favcode.receiptprocessor.service;

import com.favcode.receiptprocessor.model.Item;
import com.favcode.receiptprocessor.model.Receipt;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ReceiptService {

    private final Map<String, Integer> receiptPoints = new ConcurrentHashMap<>();

    public String processReceipt(Receipt receipt){
        String receiptId = UUID.randomUUID().toString();
        int points = calculatedPoints(receipt);
        receiptPoints.put(receiptId, points);
        return receiptId;
    }

    public Integer getPoints(String id){
        return receiptPoints.get(id);
    }

    private int calculatedPoints(Receipt receipt) {
        int points = 0;

        points += calculateRetailerNamePoints(receipt.getRetailer());
        points += calculateTotalPoints(receipt.getTotal());
        points += calculateItemPoints(receipt.getItems());
        points += calculateDatePoints(receipt.getPurchaseDate());
        points += calculateTimePoints(receipt.getPurchaseTime());

        return points;
    }

    private int calculateRetailerNamePoints(String retailer) {
        return (int) retailer.chars().filter(Character::isLetterOrDigit).count();
    }

    private int calculateTotalPoints(String total) {
        int points = 0;
        double totalValue = Double.parseDouble(total);
        if (totalValue == Math.floor(totalValue)) {
            points += 50; // Add 50 points if it's a whole number.
        }
        if (totalValue % 0.25 == 0){
            points += 25; // Add 25 points if it's a multiple of 0.25.
        }
        return points;
    }

    private int calculateItemPoints(List<Item> items) {
        int points = (items.size()/2) * 5; // For every 2 items found

        for (Item item : items){
            String description = item.getShortDescription().trim();
            if (description.length() % 3 == 0){
                points += Math.ceil(Double.parseDouble(item.getPrice()) * 0.2);
            }
        }
        return points;
    }

    private int calculateDatePoints(String purchaseDate) {
        int day = Integer.parseInt(purchaseDate.split("-")[2]);
        return (day % 2 != 0) ? 6 : 0; //For odd days
    }

    private int calculateTimePoints(String purchaseTime) {
        int hour = Integer.parseInt(purchaseTime.split(":")[0]);
        return (hour >= 14 && hour < 16) ? 10 : 0; // For time between 2-4pm
    }

}
