package com.bookstore.backend.Repository;

import com.bookstore.backend.Entity.Order;
import com.bookstore.backend.Projections.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

   @Query(value = "SELECT SUM(OD.Quantity * B.unit_price) FROM db_book B, db_order_detail OD, db_order O, db_customer C WHERE OD.BookID = B.BookID " +
       "AND O.OrderID = OD.OrderID AND C.CustomerID = O.CustomerID AND C.first_name = :first_name AND C.last_name = :last_name",
       nativeQuery = true)
   Double getOrderTotalByCustomerName(String first_name, String last_name);


   @Query(value = "SELECT FirstName, LastName " +
       "FROM (SELECT C.first_name AS FirstName, C.last_name AS LastName, SUM(OD.Quantity * B.unit_price) " +
       "AS TotalPurchases FROM db_book B, db_order_detail OD, db_order O, db_customer C WHERE OD.BookID = B.BookID AND O.OrderID = OD.OrderID " +
       "AND C.CustomerID = O.CustomerID GROUP BY C.CustomerID HAVING TotalPurchases < :amount) AS temporary",
       nativeQuery = true)
   List<FirstAndLastName> getCustomersWhoSpentMoreThan(Integer amount);


   @Query(value = "SELECT C.first_name, C.last_name, SUM(OD.Quantity * B.unit_price) AS TotalPurchases FROM db_book B, db_order_detail OD, db_order O, db_customer C " +
       "WHERE OD.BookID = B.BookID AND O.OrderID = OD.OrderID AND C.CustomerID = O.CustomerID GROUP BY C.CustomerID ORDER BY TotalPurchases DESC",
       nativeQuery = true)
   List<FirstLastNameTotalPurchases> TotalPurchasesOfCustomersDescendingOrder();


   @Query(value = "SELECT B.Title, OD.Quantity FROM db_book B, db_order_detail OD WHERE B.BookID = OD.BookID ORDER BY OD.Quantity ASC",
       nativeQuery = true)
   List<TitleAndQuantity> getOrderedBookTitlesAndTotalQuantities();


   @Query(value = "SELECT FirstName, LastName FROM (select C.first_name AS FirstName, C.last_name AS LastName, SUM(OD.Quantity) AS NumberBooksOrdered " +
       "FROM db_customer C, db_order O, db_order_detail OD WHERE OD.OrderID = O.orderID AND O.CustomerID = C.CustomerID GROUP BY C.CustomerID " +
       "HAVING NumberBooksOrdered >= :totalBooksOrdered) AS temporary", nativeQuery = true)
   List<FirstAndLastName> findCustomersNamesByNumberOfBooksOrdered(Integer totalBooksOrdered);


   @Query(value = "SELECT C.first_name, C.last_name, B.Title FROM db_customer C, db_subject Sb, db_book B, db_order O, db_order_detail OD " +
       "WHERE O.OrderID = OD.OrderID AND B.BookID = OD.BookID AND O.CustomerID = C.CustomerID AND B.SubjectID = Sb.SubjectID " +
       "AND (Sb.category_name = :category_name OR Sb.category_name = :category_name2)", nativeQuery = true)
   List<FirstLastNameAndTitle> findCustomersByOrderedBookCategory(String category_name, String category_name2);


   @Query(value = "SELECT C.first_name, C.last_name FROM db_order O, db_order_detail OD, db_customer C, db_book B " +
       "WHERE O.OrderID = OD.OrderID AND O.CustomerID = C.CustomerID AND OD.BookID = B.BookID AND B.Author = :author", nativeQuery = true)
   List<FirstAndLastNameLookupByAuthor> findCustomerWhoOrderedBookWrittenByAuthor(String author);


   @Query(value = "SELECT E.first_name, E.last_name, SUM(B.unit_price * OD.Quantity) AS TotalSales FROM db_book B, db_employee E, db_order O, db_order_detail OD " +
       "WHERE O.OrderID = OD.OrderID AND B.BookID = OD.BookID AND O.EmployeeID = E.EmployeeID " +
       "GROUP BY E.EmployeeID", nativeQuery = true)
   List<FirstLastNameAndTotalSale> getEmployeeNamesAndTotalSales();


   @Query(value = "SELECT B.Title, sum(OD.Quantity) AS TotalQuantity FROM db_book B, db_order O, db_order_detail OD WHERE B.BookID = OD.BookID" +
       " AND O.OrderID = OD.OrderID AND (O.shipped_date is NULL OR O.shipped_date > :shipped_date) " +
       "GROUP BY B.Title", nativeQuery = true)
   List<TitleAndTotalQuantity> findTitleAndQuantityForOpenOrdersByShippedDate(String shipped_date);



}
