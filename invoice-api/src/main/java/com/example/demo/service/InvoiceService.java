package com.example.demo.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.ClientRepository;
import com.example.demo.dao.InvoiceRepository;
import com.example.demo.dao.OrderRepository;
import com.example.demo.entities.InvoiceEntity;
import com.example.demo.entities.OrderEntity;
import com.example.demo.exception.ApiException;
import com.example.demo.form.InvoiceRequestForm;
import com.example.demo.response.ApiError;
import com.example.demo.utils.FormatUtils;

/**
 * Invoiceサービスクラス.
 */
@Service
public class InvoiceService {
    /** 消費税. */
    private static final double TAX = 0.08;

    /** Invoice Repository. */
    @Autowired
    private InvoiceRepository invoiceRepository;
    /** Client Repository. */
    @Autowired
    private ClientRepository clientRepository;
    /** Order Repository. */
    @Autowired
    private OrderRepository orderRepository;

    /**
     * 請求書登録.
     *
     * @param form
     *            登録フォーム.
     * @return 請求書管理番号.
     */
    public int reigsterInvoice(final InvoiceRequestForm form) {
        // パラメータチェック.
        checkParameter(form);

        int clientNumber = form.getClientNumber();
        // 指定された時間に当てはまる注文リストを取得する.
        List<OrderEntity> orderList;
        Date startDate;
        Date endDate;
        try {
            startDate = FormatUtils.parseDateString(form.getInvoiceStartDate());
            endDate = FormatUtils.parseDateString(form.getInvoiceEndDate());
            orderList = getMatchedOrderList(clientNumber, startDate, endDate);
        } catch (ParseException e) {
            // checkParameterで一回チェックしているため発生することはないが念のため.
            throw new ApiException(ApiError.PARAMETER_FORMAT, e.getMessage());
        }
        // 取得した注文リストチェック.
        if (orderList == null || orderList.isEmpty()) {
            throw new ApiException(ApiError.DATA_NOTHING, "Order is nothing.");
        }
        // 注文リストから金額を算出.
        int amt = 0;
        for (OrderEntity order : orderList) {
            amt += order.itemPrice * order.itemCount;
        }
        int tax = (int) (amt * TAX);
        Date now = new Date();

        // 登録実行.
        InvoiceEntity invoice = new InvoiceEntity();
        invoice.clientNumber = clientNumber;
        invoice.invoiceStatus = InvoiceEntity.STATUS_NEW;
        invoice.createDate = now;
        invoice.title = form.getInvoiceTitle();
        invoice.amt = amt;
        invoice.taxAmt = tax;
        invoice.startDate = startDate;
        invoice.endDate = endDate;
        invoice.note = form.getInvoiceNote();
        invoice.createUser = form.getUserId();
        invoice.createDateTime = now;
        invoice.updateUser = form.getUserId();
        invoice.updateDateTime = now;
        invoice.deleteFlag = InvoiceEntity.DELETE_FLAG_ENABLE;
        InvoiceEntity savedInvoice = invoiceRepository.save(invoice);
        if (savedInvoice == null) {
            throw new ApiException(ApiError.ERROR_OTHER, "Invoice registration failed.");
        }

        return savedInvoice.invoiceNumber;
    }

    /**
     * すべての請求書情報取得.
     *
     * @return すべての請求書情報.
     */
    public List<InvoiceEntity> getAllInvoice() {
        List<InvoiceEntity> invoiceList = invoiceRepository.findAll();
        if (invoiceList == null || invoiceList.isEmpty()) {
            throw new ApiException(ApiError.DATA_NOTHING, "Invoice is nothing.");
        }
        for (InvoiceEntity invoice : invoiceList) {
            List<OrderEntity> orderList = getInvoiceOrderList(invoice);
            if (orderList == null || orderList.isEmpty()) {
                throw new ApiException(ApiError.DATA_NOTHING,
                        "Invoice [" + invoice.invoiceNumber + "]'s order is nothing.");
            }
            invoice.order = orderList;
        }
        return invoiceList;
    }

    /**
     * 請求書情報取得.
     *
     * @param invoiceNumber
     *            請求書管理番号.
     * @return 指定された請求書情報.
     */
    public InvoiceEntity getInvoice(final int invoiceNumber) {
        InvoiceEntity invoice = invoiceRepository.findOne(invoiceNumber);
        if (invoice == null) {
            throw new ApiException(ApiError.DATA_NOTHING, "Invoice [" + invoiceNumber + "] is nothing.");
        }
        List<OrderEntity> orderList = getInvoiceOrderList(invoice);
        if (orderList == null || orderList.isEmpty()) {
            throw new ApiException(ApiError.DATA_NOTHING, "Invoice [" + invoiceNumber + "]'s order is nothing.");
        }
        invoice.order = orderList;
        return invoice;
    }

    /**
     * 条件に当てはまる注文リストを取得する.
     *
     * @param clientNumber 顧客管理番号.
     * @param startDate 検索開始日時.
     * @param endDate 検索終了日時.
     * @return 注文リスト.
     */
    private List<OrderEntity> getMatchedOrderList(final int clientNumber, final Date startDate, final Date endDate) {
        return orderRepository.findOrders(clientNumber, startDate, endDate);
    }

    /**
     * 請求書情報に紐づく注文リストを取得する.
     *
     * @param invoice 請求書情報.
     * @return 注文リスト.
     */
    private List<OrderEntity> getInvoiceOrderList(final InvoiceEntity invoice) {
        return getMatchedOrderList(invoice.clientNumber, invoice.startDate, invoice.endDate);
    }

    /**
     * パラメータチェック.
     *
     * @param form 登録フォーム.
     */
    private void checkParameter(final InvoiceRequestForm form) {
        if (form == null) {
            throw new ApiException(ApiError.ERROR_OTHER, "form nothing.");
        }
        List<ApiError> errorList = new ArrayList<>();
        // ユーザID.
        String userId = form.getUserId();
        if (userId == null) {
            errorList.add(new ApiError(ApiError.REQIRE_PARAMETER, "user id is require."));
        }
        // 顧客管理番号.
        int clientNumber = form.getClientNumber();
        if (clientNumber == -1) {
            errorList.add(new ApiError(ApiError.REQIRE_PARAMETER, "client number is require."));
        } else if (clientRepository.findOne(clientNumber) == null) {
            errorList.add(new ApiError(ApiError.DATA_NOTHING, "[" + clientNumber + "] is unkown client."));
        }
        // 請求開始日.
        String endDate = form.getInvoiceEndDate();
        if (endDate == null) {
            errorList.add(new ApiError(ApiError.REQIRE_PARAMETER, "invoice end date is require."));
        } else if (!FormatUtils.isFineDateString(endDate)) {
            errorList.add(new ApiError(ApiError.PARAMETER_FORMAT, "invoice end date format error."));
        }
        // 請求終了日.
        String startDate = form.getInvoiceStartDate();
        if (startDate == null) {
            errorList.add(new ApiError(ApiError.REQIRE_PARAMETER, "invoice start date is require."));
        } else if (!FormatUtils.isFineDateString(startDate)) {
            errorList.add(new ApiError(ApiError.PARAMETER_FORMAT, "invoice start date format error."));
        }
        // タイトル.
        if (!FormatUtils.isFineString(form.getInvoiceTitle())) {
            errorList.add(new ApiError(ApiError.REQIRE_PARAMETER, "invoice title is require."));
        }
        if (!errorList.isEmpty()) {
            throw new ApiException(errorList);
        }
    }

}
