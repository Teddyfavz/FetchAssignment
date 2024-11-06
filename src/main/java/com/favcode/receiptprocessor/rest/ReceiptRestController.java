package com.favcode.receiptprocessor.rest;

import com.favcode.receiptprocessor.model.Receipt;
import com.favcode.receiptprocessor.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/receipts")
public class ReceiptRestController {

    @Autowired
    private ReceiptService receiptService;

    //POST end point
    @PostMapping("/process")
    public ResponseEntity<Map<String, String>> processReceipt(@Validated @RequestBody Receipt receipt){
        String receiptId = receiptService.processReceipt(receipt);
        return ResponseEntity.ok(Map.of("id", receiptId));
    }

    // GET end point
    @GetMapping("/{id}/points")
    public ResponseEntity<Map<String, Integer>> receiotPoints(@PathVariable String id){
        Integer points = receiptService.getPoints(id);
        if (points == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(Map.of("points", points));
    }
}
