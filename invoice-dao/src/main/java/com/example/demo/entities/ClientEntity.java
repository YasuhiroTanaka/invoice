package com.example.demo.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 顧客情報クラス.
 */
@Entity
@Table(name = "client")
public class ClientEntity {
    @Id
    /** 顧客管理番号. */
    @Column(name = "client_no")
    @JsonProperty("client_no")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int clientNumber;

    /** 顧客担当者氏名（名字）. */
    @Column(name = "client_charge_last_name")
    @JsonProperty("client_charge_last_name")
    private String clientChargeLastName;

    /** 顧客担当者氏名（名前）. */
    @Column(name = "client_charge_first_name")
    @JsonProperty("client_charge_first_name")
    private String clientChargeFirstName;

    /** 法人名. */
    @Column(name = "client_name")
    @JsonProperty("client_name")
    private String clientName;

    /** 住所. */
    @Column(name = "client_address")
    @JsonProperty("client_address")
    private String clientAddress;

    /** 電話番号. */
    @Column(name = "client_tel")
    @JsonProperty("client_tel")
    private String clientTel;

    /** FAX. */
    @Column(name = "client_fax")
    @JsonProperty("client_fax")
    private String clientFax;

    /** 登録日時. */
    @Column(name = "create_datetime")
    @JsonProperty("create_datetime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date createDateTime;

    /** 更新日時. */
    @Column(name = "update_datetime")
    @JsonProperty("update_datetime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date updateDateTime;

    /** 削除フラグ. */
    @Column(name = "del_flg")
    @JsonProperty("del_flg")
    private String deleteFlag;
}
