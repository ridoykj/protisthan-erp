package com.itbd.protisthan.db;

import com.itbd.protisthan.db.dao.ItemDao;
import com.itbd.protisthan.db.dao.OrderDao;
import com.itbd.protisthan.db.dao.OrderDetailDao;
import com.itbd.protisthan.db.dao.iddao.OrderDetailId;
import com.itbd.protisthan.db.repos.ItemRepository;
import com.itbd.protisthan.db.repos.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;
import java.util.Set;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
@Slf4j
public class OrderTableTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

//    @BeforeEach
//    public void setUp() {
//        // Initialize test data before each test method
//    }
//
//    @AfterEach
//    public void tearDown() {
//        // Release test data after each test method
//    }

    //    @Transactional
    @Test
    public void makeOrder() {
        ItemDao item = new ItemDao();
        item.setId(5L);
        item.setRecordVersion(4);

        OrderDao order = new OrderDao();

        ItemDao item2 = itemRepository.getReferenceById(5L);
//        ItemDao item2  = item;

        OrderDetailId odid1 = new OrderDetailId();
        odid1.setIdOrderKey(null);
        odid1.setIdItemKey(item2.getId());

        OrderDetailDao od1 = new OrderDetailDao();
        od1.setId(odid1);
        od1.setItem(item2);
        od1.setOrder(order);
        od1.setQuantity((short) 5);
        od1.setDiscount(0d);
        od1.setUnitPrice(786d);

        order.setOrderDetails(Set.of(od1));
        orderRepository.save(order);
    }

    @Test
    public void getItem() {
        Optional<ItemDao> item = itemRepository.findById(1L);
        item.ifPresent(itemDao -> log.info(itemDao.toString()));
    }
}
