package br.com.nexfar.reportgenerator.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Items {

    private Product product;
    private Integer quantity;
    private FinalPrice finalPrice;

}
