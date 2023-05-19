package br.com.nexfar.reportgenerator.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FinalPrice {

    private BigDecimal price;

    private BigDecimal finalPrice;

}
