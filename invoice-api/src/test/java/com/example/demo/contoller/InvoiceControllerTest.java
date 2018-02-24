package com.example.demo.contoller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.controller.InvoiceController;
import com.example.demo.entities.InvoiceEntity;
import com.example.demo.service.InvoiceService;

/**
 * The Class InvoiceControllerTest.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class InvoiceControllerTest {

    /** The invoice controller. */
    @InjectMocks
    private InvoiceController invoiceController;

    /** The invoice service. */
    @Mock
    private InvoiceService invoiceService;

    /** The Constant TEST_DATE. */
    private static final Date TEST_DATE = new Date();


    /**
     * Test post invoice.
     */
    @Test
    public void testPostInvoice() {
        doReturn(99).when(invoiceService).reigsterInvoice(null);
        assertTrue(invoiceController.postInvoice(null) == 99);
    }

    /**
     * Test get all invoice.
     */
    @Test
    public void testGetAllInvoice() {
        List<InvoiceEntity> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(createTestInvoiceEntity(i, i));
        }
        doReturn(list).when(invoiceService).getAllInvoice();
        assertTrue(invoiceController.getAllInvoice().size() == 10);
    }

    /**
     * Test get invoice.
     */
    @Test
    public void testGetInvoice() {
        doReturn(createTestInvoiceEntity(99, 99)).when(invoiceService).getInvoice(99);
        assertTrue(invoiceController.getInvoice("99").invoiceNumber == 99);
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
}
