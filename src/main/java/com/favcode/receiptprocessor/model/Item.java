package com.favcode.receiptprocessor.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class Item {
    @NotBlank(message = "Item description cannot be blank")
    @Pattern(regexp = "^[\\w\\s\\-&]+$")
    private String shortDescription;

    @NotBlank(message = "Price cannot be blank")
    @Pattern(regexp = "^\\d+\\.\\d{2}$")
    private String price;

}
