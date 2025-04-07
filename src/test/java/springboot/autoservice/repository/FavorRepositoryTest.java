package springboot.autoservice.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import springboot.autoservice.model.Favor;
import java.math.BigDecimal;


    @DataJpaTest
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
    class FavorRepositoryTest {

        @Autowired
        private FavorRepository favorRepository;

        @Test
        @Sql("/scripts/init_favor.sql")
        void shouldChangeFavorStatusById() {
            favorRepository.changeFavorStatusById(1L, Favor.Status.PAID);
            Favor expected = new Favor();
            expected.setId(1L);
            expected.setFavorName("favor name");
            expected.setPrice(BigDecimal.valueOf(100).setScale(2));
            expected.setStatus(Favor.Status.PAID);

            Favor actual = favorRepository.findById(1L).get();
            Assertions.assertEquals(expected, actual);
        }
    }

