package com.example.demo.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entities.OrderEntity;


/**
 * Jpa Order Repository.
 */
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
    /**
     * 条件に当てはまる注文リストを取得する.
     * @param clientNumber 顧客管理番号.
     * @param startDate 検索開始日時.
     * @param endDate 検索終了日時.
     * @return 注文リスト.
     */
    @Query("select o from OrderEntity o where client_no = :client_no and create_datetime >= :start_date and create_datetime <= :end_date")
    public List<OrderEntity> findOrders(
            @Param("client_no") int clientNumber, @Param("start_date") Date startDate, @Param("end_date") Date endDate);
}
