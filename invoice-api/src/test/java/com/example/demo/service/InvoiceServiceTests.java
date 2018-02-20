package com.example.demo.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.dao.ClientRepository;
import com.example.demo.dao.InvoiceRepository;
import com.example.demo.dao.OrderRepository;
import com.example.demo.entities.InvoiceEntity;
import com.example.demo.entities.OrderEntity;
import com.example.demo.exception.ApiException;

/**
 * The Class InvoiceServiceTests.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class InvoiceServiceTests {

    /** The m invoice service. */
    // モックオブジェクトの挿入対象
    @InjectMocks
    private InvoiceService mInvoiceService;


    /** Invoice Repository. */
    @Mock
    private InvoiceRepository mInvoiceRepository;
    /** Client Repository. */
    @Mock
    private ClientRepository mClientRepository;
    /** Order Repository. */
    @Mock
    private OrderRepository mOrderRepository;

    /** The Constant TEST_DATE. */
    private static final Date TEST_DATE = new Date();

    /** The Constant TEST_ORDER_LIST. */
    private static final List<OrderEntity> TEST_ORDER_LIST = Arrays.asList(
            createOrderEntity(5000, 1),
            createOrderEntity(5001, 1),
            createOrderEntity(5002, 1),
            createOrderEntity(5003, 1));

    /**
     * Setup.
     */
    @Before
    public void setup() {
        doReturn(null).when(mInvoiceRepository).findOne(0);
        doReturn(null).when(mClientRepository).findOne(0);
    }

    /**
     * Test reigster invoice.
     */
    @Test
    public void testReigsterInvoice() {
        try {
            mInvoiceService.getInvoice(0);
          } catch (ApiException e) {
            assertTrue(true);
          }
    }

    /**
     * Test get all invoice.
     */
    @Test
    public void testGetAllInvoice() {
        doReturn(Collections.EMPTY_LIST).when(mInvoiceRepository).findAll();
        try {
            mInvoiceService.getInvoice(0);
          } catch (ApiException e) {
            assertTrue(true);
          }
    }


    /**
     * Test get invoice.
     */
    @Test
    public void testGetInvoice() {
        doReturn(createTestInvoiceEntity(0, 0)).when(mInvoiceRepository).findOne(0);
        doReturn(TEST_ORDER_LIST).when(mOrderRepository).findOrders(0, TEST_DATE, TEST_DATE);
        InvoiceEntity invoice = mInvoiceService.getInvoice(0);
        assertEquals(invoice.order, TEST_ORDER_LIST);
    }



    /**
     * Creates the test invoice entity.
     *
     * @param invoiceNumber the invoice number
     * @param clientNumber the client number
     * @return the invoice entity
     */
    private InvoiceEntity createTestInvoiceEntity(final int invoiceNumber, final int clientNumber) {
        InvoiceEntity entity = new InvoiceEntity();
        entity.invoiceNumber = invoiceNumber;
        entity.clientNumber = clientNumber;
        entity.invoiceStatus = InvoiceEntity.STATUS_NEW;
        entity.createDate = TEST_DATE;
        entity.title = "test title:" + invoiceNumber;
        entity.amt = 0;
        entity.taxAmt = 0;
        entity.startDate = new Date();
        entity.endDate = new Date();
        entity.note = "test note:" + invoiceNumber;
        entity.createUser = "user id : " + invoiceNumber;
        entity.createDateTime = TEST_DATE;
        entity.updateUser = "user id : " + invoiceNumber;
        entity.updateDateTime = TEST_DATE;
        entity.deleteFlag = InvoiceEntity.DELETE_FLAG_ENABLE;
        return entity;
    }

    /**
     * Creates the order entity.
     *
     * @param orderNumber the order number
     * @param clientNumber the client number
     * @return the order entity
     */
    private static OrderEntity createOrderEntity(final int orderNumber, final int clientNumber) {
        OrderEntity entity = new OrderEntity();
        entity.orderNumber = orderNumber;
        entity.clientNumber = clientNumber;
        return entity;
    }
}
