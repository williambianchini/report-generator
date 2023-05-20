package br.com.nexfar.reportgenerator.repository;

import br.com.nexfar.reportgenerator.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order, Long> {
}
