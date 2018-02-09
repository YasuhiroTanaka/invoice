package com.example.demo.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 請求書情報クラス.
 */
@Entity
@Table(name = "invoice")
public class InvoiceEntity {
    /** 状態(新規作成). */
    public static final String STATUS_NEW = "10";
    /** 状態(送付済み). */
    public static final String STATUS_SENT = "20";
    /** 状態(入金確認済み). */
    public static final String STATUS_PAYMENT = "30";
    /** 状態(破棄). */
    public static final String STATUS_DELETE = "90";

    /** 削除フラグ(有効). */
    public static final String DELETE_FLAG_ENABLE = "0";
    /** 削除フラグ(無効). */
    public static final String DELETE_FLAG_DISABLE = "1";

    @Id
    @Column(name = "invoice_no")
    @JsonProperty("invoice_no")
    @GeneratedValue(strategy = GenerationType.AUTO)
    /** 管理番号. */
    public int mInvoiceNumber;
    /** 顧客管理番号. */
    @Column(name = "client_no")
    @JsonProperty("client_no")
    public int mClientNumber;
    /** 請求状態. */
    @Column(name = "invoice_status")
    @JsonProperty("invoice_status")
    public String mInvoiceStatus;
    /** 請求書作成日. */
    @Column(name = "invoice_create_date")
    @JsonProperty("invoice_create_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date mCreateDate;
    /** 請求書件名. */
    @Column(name = "invoice_title")
    @JsonProperty("invoice_title")
    public String mTitle;
    /** 請求金額. */
    @Column(name = "invoice_amt")
    @JsonProperty("invoice_amt")
    public int mAmt;
    /** 消費税. */
    @Column(name = "tax_amt")
    @JsonProperty("tax_amt")
    public int mTaxAmt;
    /** 請求期間開始日. */
    @Column(name = "invoice_start_date")
    @JsonProperty("invoice_start_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date mStartDate;
    /** 請求期間終了日. */
    @Column(name = "invoice_end_date")
    @JsonProperty("invoice_end_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date mEndDate;
    /** 備考. */
    @Column(name = "invoice_note")
    @JsonProperty("invoice_note")
    public String mNote;
    /** 登録ユーザーID. */
    @Column(name = "create_user")
    @JsonProperty("create_user")
    public String mCreateUser;
    /** 登録日時. */
    @Column(name = "create_datetime")
    @JsonProperty("create_datetime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    public Date mCreateDateTime;
    /** 更新ユーザーID. */
    @Column(name = "update_user")
    @JsonProperty("update_user")
    public String mUpdateUser;
    /** 更新日時. */
    @Column(name = "update_datetime")
    @JsonProperty("update_datetime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    public Date mUpdateDateTime;
    /** 削除フラグ. */
    @Column(name = "del_flg")
    @JsonProperty("del_flg")
    public String mDeleteFlag;

    /** 注文内容. */
    @JsonProperty("order")
    @OneToMany
    public List<OrderEntity> mOrder;
}