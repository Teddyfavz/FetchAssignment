package com.favcode.receiptprocessor.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

@Data
public class Receipt {

    @NotBlank(message = "Retailer cannot be blank")
    @Pattern(regexp = "^[\\w\\s\\-&]+$")
    private String retailer;

    @NotBlank(message = "Purchase date cannot be blank")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String purchaseDate;

    @NotBlank(message = "Purchase time cannot be blank")
    @DateTimeFormat(pattern = "HH-mm")
    private String purchaseTime;

    @Size(min = 1, message = "There must be at least one item on the receipt")
    private List<Item> items;

    @NotBlank(message = "Total cannot be blank")
    @Pattern(regexp = "^\\d+\\.\\d{2}$")
    private String total;

}
