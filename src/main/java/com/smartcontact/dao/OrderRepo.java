package com.smartcontact.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartcontact.entities.MyOrders;

public interface OrderRepo extends JpaRepository<MyOrders, Long> {

    public MyOrders findByOrderId(String orderId); 
}
