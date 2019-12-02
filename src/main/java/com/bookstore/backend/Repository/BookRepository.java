package com.bookstore.backend.Repository;

import com.bookstore.backend.Entity.Book;
import com.bookstore.backend.Projections.SubjectCategoryName;
import com.bookstore.backend.Projections.TitleAndPrice;
import com.bookstore.backend.Projections.TitleAndShipperName;
import com.bookstore.backend.Projections.TitleOnly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

   /**
    * This serves as the SELECT B.Title FROM bookstore.db_book B WHERE B.Quantity > 10.
    * Had to create additional interface as a projection to access only the book title column.
    *
    * @param quantity - number of book current in inventory.
    * @return (s) - List of titles for books with more than quantity.
    */
   @Query(value = "SELECT B.Title FROM bookstore.db_book B WHERE B.Quantity > :quantity",
       nativeQuery = true)
   List<TitleOnly> findBookByQuantity(Integer quantity);


   @Query(value = "SELECT B.Title, B.unit_price FROM bookstore.db_book B WHERE B.SupplierID = :supplier_id " +
       "AND B.unit_price = (SELECT MAX(B2.unit_price) FROM bookstore.db_book B2 WHERE B2.SupplierID = :supplier_id AND B2.unit_price)",
       nativeQuery = true)
   List<TitleAndPrice> maxPriceBySupplierId(Integer supplier_id);



   @Query(value = "SELECT S.category_name FROM bookstore.db_book B, db_subject S, db_supplier P " +
       "WHERE B.SupplierID = :supplier_id AND B.SubjectID = S.SubjectID AND P.company_name = :company_name",
       nativeQuery = true)
   List<SubjectCategoryName> getBooksBySupplierID(Integer supplier_id, String company_name);



   @Query(value = "SELECT Distinct B.Title FROM db_book B, db_order O, db_order_detail OD, db_customer C " +
       "WHERE B.BookID = OD.BookID AND OD.OrderID = O.OrderID AND O.CustomerID = C.CustomerID " +
       "AND C.last_name = :last_name AND C.first_name = :first_name", nativeQuery = true)
   List<TitleOnly> findUniqueBooksOrderedByCustomerName(String first_name, String last_name);


   @Query(value = "SELECT B.Title FROM db_book B, db_supplier S WHERE B.SupplierID = S.SupplierID AND S.company_name = :supplier_name",
       nativeQuery = true)
   List<TitleOnly> listTheNameOfBooksFromSupplier(String supplier_name);


   @Query(value = "SELECT B.Title, S.shipper_name FROM db_book B, db_shipper S, db_order O, db_order_detail OD WHERE B.BookID = OD.BookID AND OD.OrderID = O.OrderID " +
       "AND O.ShipperID = S.ShipperID AND O.shipped_date = :shipped_date", nativeQuery = true)
   List<TitleAndShipperName> getBookTitleAndShipperNameByShippedDate(String shipped_date);


   @Query(value = "SELECT Distinct B.Title " +
       "FROM db_book B, db_order O, db_order_detail OD, db_customer C " +
       "WHERE B.BookID = OD.BookID AND OD.OrderID = O.OrderID AND O.CustomerID = C.CustomerID AND C.first_name = :first_name " +
       "AND C.last_name = :last_name AND B.BookID IN (SELECT B1.bookID FROM db_book B1, db_order O1, db_order_detail OD1, db_customer C1 " +
       "WHERE B1.BookID = OD1.BookID AND OD1.OrderID = O1.OrderID AND O1.CustomerID = C1.CustomerID AND C1.first_name = :first_name2 " +
       "AND C1.last_name = :last_name2)", nativeQuery = true)
   List<TitleOnly> findBookTitlesBothCustomersHaveOrderedByCustomerNames(String first_name, String last_name,
                                                                         String first_name2, String last_name2);


   @Query(value = "SELECT B.Title FROM db_book B, db_employee E, db_order O, db_order_detail OD WHERE OD.BookID = B.BookID AND OD.OrderID = O.OrderID " +
       "AND O.EmployeeID = E.EmployeeID AND E.first_name = :first_name AND last_name = :last_name", nativeQuery = true)
   List<TitleOnly> findBooksEmployeeResponsibleForByEmployeeName(String first_name, String last_name);



}
