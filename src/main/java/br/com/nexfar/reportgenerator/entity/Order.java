package br.com.nexfar.reportgenerator.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "order")
public class Order {

    @Id
    private Long id;

    @CreatedDate
    private LocalDateTime createdAt;

    private String status;

    private BigDecimal netTotal;

    private BigDecimal totalWithTaxes;
    private Client client;

    private List<Items> items;

}
