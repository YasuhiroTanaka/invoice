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
 * 注文実績情報クラス.
 */
@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    /** 注文管理番号. */
    @Column(name = "order_no")
    @JsonProperty("order_no")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int orderNumber;
    /** 顧客管理番号. */
    @Column(name = "client_no")
    @JsonProperty("client_no")
    public int clientNumber;
    /** 商品管理番号. */
    @Column(name = "item_no")
    @JsonProperty("item_no")
    public int itemNumber;
    /** 商品名. */
    @Column(name = "item_name")
    @JsonProperty("item_name")
    public String itemName;
    /** 商材種別. */
    @Column(name = "item_type")
    @JsonProperty("item_type")
    public String itemType;
    /** 商品販売価格（税抜き）. */
    @Column(name = "item_price")
    @JsonProperty("item_price")
    public int itemPrice;
    /** 購入数量. */
    @Column(name = "item_count")
    @JsonProperty("item_count")
    public int itemCount;
    /** 登録日時. */
    @Column(name = "create_datetime")
    @JsonProperty("create_datetime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    public Date createDateTime;
    /** 更新日時. */
    @Column(name = "update_datetime")
    @JsonProperty("update_datetime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    public Date updateDateTime;
    /** 削除フラグ. */
    @Column(name = "del_flg")
    @JsonProperty("del_flg")
    public String deleteFlag;
}
