package com.example.demo.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DebugLog;
import com.example.demo.dao.ClientRepository;
import com.example.demo.dao.InvoiceRepository;
import com.example.demo.dao.OrderRepository;
import com.example.demo.entities.InvoiceEntity;
import com.example.demo.entities.OrderEntity;
import com.example.demo.exception.ApiException;
import com.example.demo.exception.ApiException.ErrorCode;
import com.example.demo.form.InvoiceRequestForm;
import com.example.demo.utils.FormatUtils;

/**
 * Invoiceサービスクラス.
 */
@Service
public class InvoiceService {
    /** CLASS. */
    private static final String CLASS = InvoiceService.class.getSimpleName();
    /** 消費税. */
    private static final double TAX = 0.08;

    /** Invoice Repository. */
    @Autowired
    private InvoiceRepository mInvoiceRepository;
    /** Client Repository. */
    @Autowired
    private ClientRepository mClientRepository;
    /** Order Repository. */
    @Autowired
    private OrderRepository mOrderRepository;

    /**
     * 請求書登録.
     *
     * @param form
     *            登録フォーム.
     * @return 請求書管理番号.
     */
    public int reigsterInvoice(final InvoiceRequestForm form) {
        DebugLog.enter(CLASS + ".reigsterInvoice");
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
            throw new ApiException(ErrorCode.PARAMETER_FORMAT, e.getMessage());
        }
        // 取得した注文リストチェック.
        if (orderList == null || orderList.isEmpty()) {
            throw new ApiException(ErrorCode.DATA_NOTHING, "Order is nothing.");
        }
        // 注文リストから金額を算出.
        int amt = 0;
        for (OrderEntity order : orderList) {
            amt += order.mItemPrice * order.mItemCount;
        }
        int tax = (int) (amt * TAX);
        Date now = new Date();

        // 登録実行.
        InvoiceEntity invoice = new InvoiceEntity();
        invoice.mClientNumber = clientNumber;
        invoice.mInvoiceStatus = InvoiceEntity.STATUS_NEW;
        invoice.mCreateDate = now;
        invoice.mTitle = form.getInvoiceTitle();
        invoice.mAmt = amt;
        invoice.mTaxAmt = tax;
        invoice.mStartDate = startDate;
        invoice.mEndDate = endDate;
        invoice.mNote = form.getInvoiceNote();
        invoice.mCreateUser = form.getUserId();
        invoice.mCreateDateTime = now;
        invoice.mUpdateUser = form.getUserId();
        invoice.mUpdateDateTime = now;
        invoice.mDeleteFlag = InvoiceEntity.DELETE_FLAG_ENABLE;
        InvoiceEntity savedInvoice = mInvoiceRepository.save(invoice);
        if (savedInvoice == null) {
            throw new ApiException(ErrorCode.ERROR_OTHER, "Invoice registration failed.");
        }

        DebugLog.exit(CLASS + ".reigsterInvoice");
        return savedInvoice.mInvoiceNumber;
    }

    /**
     * すべての請求書情報取得.
     *
     * @return すべての請求書情報.
     */
    public List<InvoiceEntity> getAllInvoice() {
        DebugLog.enter(CLASS + ".getAllInvoice");
        List<InvoiceEntity> invoiceList = mInvoiceRepository.findAll();
        if (invoiceList == null || invoiceList.isEmpty()) {
            throw new ApiException(ErrorCode.DATA_NOTHING, "Invoice is nothing.");
        }
        for (InvoiceEntity invoice : invoiceList) {
            List<OrderEntity> orderList = getInvoiceOrderList(invoice);
            if (orderList == null || orderList.isEmpty()) {
                throw new ApiException(ErrorCode.DATA_NOTHING,
                        "Invoice [" + invoice.mInvoiceNumber + "]'s order is nothing.");
            }
            invoice.mOrder = orderList;
        }
        DebugLog.exit(CLASS + ".getAllInvoice");
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
        DebugLog.enter(CLASS + ".getInvoice:" + invoiceNumber);
        InvoiceEntity invoice = mInvoiceRepository.findOne(invoiceNumber);
        if (invoice == null) {
            throw new ApiException(ErrorCode.DATA_NOTHING, "Invoice [" + invoiceNumber + "] is nothing.");
        }
        List<OrderEntity> orderList = getInvoiceOrderList(invoice);
        if (orderList == null || orderList.isEmpty()) {
            throw new ApiException(ErrorCode.DATA_NOTHING, "Invoice [" + invoiceNumber + "]'s order is nothing.");
        }
        invoice.mOrder = orderList;
        DebugLog.exit(CLASS + ".getInvoice");
        return invoice;
    }

    /**
     * 条件に当てはまる注文リストを取得する.
     *
     * @param clientNumber
     *            顧客管理番号.
     * @param startDate
     *            検索開始日時.
     * @param endDate
     *            検索終了日時.
     * @return 注文リスト.
     */
    private List<OrderEntity> getMatchedOrderList(final int clientNumber, final Date startDate, final Date endDate) {
        return mOrderRepository.findOrders(clientNumber, startDate, endDate);
    }

    /**
     * 請求書情報に紐づく注文リストを取得する.
     *
     * @param invoice
     *            請求書情報.
     * @return 注文リスト.
     */
    private List<OrderEntity> getInvoiceOrderList(final InvoiceEntity invoice) {
        return getMatchedOrderList(invoice.mClientNumber, invoice.mStartDate, invoice.mEndDate);
    }

    /**
     * パラメータチェック.
     *
     * @param form
     *            登録フォーム.
     */
    private void checkParameter(final InvoiceRequestForm form) {
        if (form == null) {
            throw new ApiException(ErrorCode.ERROR_OTHER, "form nothing.");
        }
        ApiException error = new ApiException();
        // ユーザID.
        String userId = form.getUserId();
        if (userId == null) {
            error.addError(ErrorCode.REQIRE_PARAMETER, "user id is require.");
        }
        // 顧客管理番号.
        int clientNumber = form.getClientNumber();
        if (clientNumber == -1) {
            error.addError(ErrorCode.REQIRE_PARAMETER, "client number is require.");
        } else if (mClientRepository.findOne(clientNumber) == null) {
            error.addError(ErrorCode.DATA_NOTHING, "[" + clientNumber + "] is unkown client.");
        }
        // 請求開始日.
        String endDate = form.getInvoiceEndDate();
        if (endDate == null) {
            error.addError(ErrorCode.REQIRE_PARAMETER, "invoice end date is require.");
        } else if (!FormatUtils.isFineDateString(endDate)) {
            error.addError(ErrorCode.PARAMETER_FORMAT, "invoice end date format error.");
        }
        // 請求終了日.
        String startDate = form.getInvoiceStartDate();
        if (startDate == null) {
            error.addError(ErrorCode.REQIRE_PARAMETER, "invoice start date is require.");
        } else if (!FormatUtils.isFineDateString(startDate)) {
            error.addError(ErrorCode.PARAMETER_FORMAT, "invoice start date format error.");
        }
        // タイトル.
        if (!FormatUtils.isFineString(form.getInvoiceTitle())) {
            error.addError(ErrorCode.REQIRE_PARAMETER, "invoice title is require.");
        }
        if (!error.isEmpty()) {
            throw error;
        }
    }

}
