package br.com.nexfar.reportgenerator.intro;

import br.com.nexfar.reportgenerator.dto.ReportFilter;
import br.com.nexfar.reportgenerator.dto.ReportGenerateRequest;
import br.com.nexfar.reportgenerator.entity.Order;
import br.com.nexfar.reportgenerator.repository.OrderRepository;
import br.com.nexfar.reportgenerator.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@SpringBootTest
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    OrderService service;

    @Test
    void readSomeRows(){
        Optional<Order> order = orderRepository.findById(95168L);
        System.out.println(order.get().getClient().getName());
    }

    @Test
    void buscandoAlgumasInfo(){
        orderRepository.findById(95161L)
                .ifPresent(order -> order.getItems()
                        .forEach(items -> System.out.println(items.getProduct().getName())));
    }

    @Test
    void validandoQuery(){
        var filterCNPJ = ReportFilter.builder().key("cnpj").operation("EQ")
                .value1("06242137000139").build();
        var filterCreatedAt = ReportFilter.builder().key("createdAt").operation("INTERVAL")
                .value1("2017-01-08 18:26")
                .value2("2019-01-08 18:26")
                .build();
        var filterStatus = ReportFilter.builder().key("status").operation("EQ")
                .value1("BILLED")
                .build();
        var array = new ArrayList<ReportFilter>();
        array.add(filterCNPJ);
//        array.add(filterCreatedAt);
//        array.add(filterStatus);

        var req = ReportGenerateRequest.builder()
                .filters(array).build();
        System.out.println(req.toString());
        service.dynamicQuery(req.getFilters()).forEach(System.out::println);
    }

}
