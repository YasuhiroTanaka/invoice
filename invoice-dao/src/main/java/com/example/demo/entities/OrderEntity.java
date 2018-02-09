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
    public int mOrderNumber;
    /** 顧客管理番号. */
    @Column(name = "client_no")
    @JsonProperty("client_no")
    public int mClientNumber;
    /** 商品管理番号. */
    @Column(name = "item_no")
    @JsonProperty("item_no")
    public int mItemNumber;
    /** 商品名. */
    @Column(name = "item_name")
    @JsonProperty("item_name")
    public String mItemName;
    /** 商材種別. */
    @Column(name = "item_type")
    @JsonProperty("item_type")
    public String mItemType;
    /** 商品販売価格（税抜き）. */
    @Column(name = "item_price")
    @JsonProperty("item_price")
    public int mItemPrice;
    /** 購入数量. */
    @Column(name = "item_count")
    @JsonProperty("item_count")
    public int mItemCount;
    /** 登録日時. */
    @Column(name = "create_datetime")
    @JsonProperty("create_datetime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    public Date mCreateDateTime;
    /** 更新日時. */
    @Column(name = "update_datetime")
    @JsonProperty("update_datetime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    public Date mUpdateDateTime;
    /** 削除フラグ. */
    @Column(name = "del_flg")
    @JsonProperty("del_flg")
    public String mDeleteFlag;
}
