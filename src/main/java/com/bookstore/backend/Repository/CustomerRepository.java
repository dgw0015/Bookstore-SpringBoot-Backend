package com.bookstore.backend.Repository;

import com.bookstore.backend.Entity.Customer;
import com.bookstore.backend.Projections.FirstLastNameAndPhoneAndTotalOrders;
import com.bookstore.backend.Projections.FirstLastNameAndTotalOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

   @Query(value = "SELECT C.first_name, C.last_name, SUM(OD.Quantity) AS TotalOrders FROM db_customer C, db_order O, db_order_detail OD " +
       "WHERE OD.OrderID = O.orderID AND O.CustomerID = C.CustomerID " +
       "GROUP BY C.CustomerID HAVING TotalOrders > :totalOrders " +
       "ORDER BY TotalOrders DESC", nativeQuery = true)
   List<FirstLastNameAndTotalOrders> getCustomerAndOrderQuantitiesByTotalOrders(Integer totalOrders);


   @Query(value = "SELECT C.first_name, C.last_name, C.Phone, SUM(OD.Quantity) AS TotalOrders FROM db_customer C, db_order O, db_order_detail OD " +
       "WHERE OD.OrderID = O.orderID AND O.CustomerID = C.CustomerID " +
       "GROUP BY C.CustomerID HAVING TotalOrders > :totalOrders", nativeQuery = true)
   List<FirstLastNameAndPhoneAndTotalOrders> getCustomerNameAndPhoneThatOrderedMoreThan(Integer totalOrders);


}
