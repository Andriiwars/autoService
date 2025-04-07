package springboot.autoservice.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import springboot.autoservice.model.Favor;
import springboot.autoservice.model.Order;
import springboot.autoservice.model.Worker;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class WorkerRepositoryTest {

    @Autowired
    private WorkerRepository workerRepository;

    @Test
    @Sql("/scripts/init_workers_favors_orders_.sql")
    void shouldReturnAllCompletedOrders() {
        List<Order> actual = workerRepository.getAllCompletedOrdersByWorkerId(2L);

        Worker worker = new Worker();
        worker.setId(2L);
        worker.setFirstName("1");

        Favor favor1 = new Favor();
        favor1.setId(3L);
        favor1.setFavorName("1");
        favor1.setWorker(worker);
        favor1.setPrice(BigDecimal.valueOf(100).setScale(2));
        favor1.setStatus(Favor.Status.NOT_PAID);
        List<Favor> favors = new ArrayList<>();
        favors.add(favor1);

        Favor favor2 = new Favor();
        favor2.setId(4L);
        favor2.setFavorName("2");
        favor2.setWorker(worker);
        favor2.setPrice(BigDecimal.valueOf(100).setScale(2));
        favor2.setStatus(Favor.Status.PAID);
        favors.add(favor2);

        Order order = new Order();
        order.setId(2L);
        order.setProblemDescription("1");
        order.setStatus(Order.Status.COMPLETED);
        order.setFavors(favors);

        List<Order> expected = new ArrayList<>();
        expected.add(order);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Sql("/scripts/init_workers_favors_orders.sql")
    void shouldReturnAllWorkersByOrderId() {
        List<Worker> actual = workerRepository.getAllByOrderId(1L);

        Worker worker = new Worker();
        worker.setId(1L);
        worker.setFirstName("1");
        List<Worker> expected = new ArrayList<>();
        expected.add(worker);

        Assertions.assertEquals(expected, actual);
    }
}
