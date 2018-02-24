package com.example.demo.form;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


/**
 * 請求書リクエストフォーム.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class InvoiceRequestForm {
    /** ユーザID. */
    @JsonProperty("user_id")
    private String userId;
    /** 顧客管理番号. */
    @JsonProperty("clilent_no")
    private int clientNumber = -1;
    /** 件名. */
    @JsonProperty("invoice_title")
    private String invoiceTitle;
    /** 請求期間開始日. */
    @JsonProperty("invoice_start_date")
    private String invoiceStartDate;
    /** 請求期間終了日. */
    @JsonProperty("invoice_end_date")
    private String invoiceEndDate;
    /** 備考. */
    @JsonProperty("invoice_note")
    private String invoiceNote;
}
