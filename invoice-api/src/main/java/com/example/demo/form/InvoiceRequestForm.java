package com.example.demo.form;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 請求書リクエストフォーム.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceRequestForm {
    /** ユーザID. */
    @JsonProperty("user_id")
    private String mUserId;
    /** 顧客管理番号. */
    @JsonProperty("clilent_no")
    private int mClientNumber = -1;
    /** 件名. */
    @JsonProperty("invoice_title")
    private String mInvoiceTitle;
    /** 請求期間開始日. */
    @JsonProperty("invoice_start_date")
    private String mInvoiceStartDate;
    /** 請求期間終了日. */
    @JsonProperty("invoice_end_date")
    private String mInvoiceEndDate;
    /** 備考. */
    @JsonProperty("invoice_note")
    private String mInvoiceNote;

    /**
     * ユーザID取得.
     *
     * @return ユーザID.
     */
    public String getUserId() {
        return mUserId;
    }

    /**
     * 顧客管理番号取得.
     *
     * @return 顧客管理番号.
     */
    public int getClientNumber() {
        return mClientNumber;
    }

    /**
     * 件名取得.
     *
     * @return 件名.
     */
    public String getInvoiceTitle() {
        return mInvoiceTitle;
    }

    /**
     * 請求期間開始日取得.
     *
     * @return 請求期間開始日.
     */
    public String getInvoiceStartDate() {
        return mInvoiceStartDate;
    }

    /**
     * 請求期間終了日取得.
     *
     * @return 請求期間終了日.
     */
    public String getInvoiceEndDate() {
        return mInvoiceEndDate;
    }

    /**
     * 備考取得.
     *
     * @return 備考.
     */
    public String getInvoiceNote() {
        return mInvoiceNote;
    }
}
