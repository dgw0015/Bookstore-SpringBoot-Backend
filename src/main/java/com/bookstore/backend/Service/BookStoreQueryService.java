package com.bookstore.backend.Service;

import com.bookstore.backend.Entity.*;
import com.bookstore.backend.Projections.*;
import com.bookstore.backend.Repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
@AllArgsConstructor
public class BookStoreQueryService {

   private BookRepository bookRepository;
   private CustomerRepository customerRepository;
   private EmployeeRepository employeeRepository;
   private OrderRepository orderRepository;
   private ShipperRepository shipperRepository;
   private SubjectRepository subjectRepository;
   private SupplierRepository supplierRepository;


   // 1
   public List<SubjectCategoryName> booksSubjectBySupplier(Integer supplier_id, String company_name)  {
      return bookRepository.getBooksBySupplierID(supplier_id, company_name);
   }

   // 2
   public List<TitleAndPrice> getTitleByPriceAndSupplierId(Integer supplier_id) {
      return bookRepository.maxPriceBySupplierId(supplier_id);
   }

   // 3
   public List<TitleOnly> bookNamesOrderedByName(String first_name, String last_name)  {
      return bookRepository.findUniqueBooksOrderedByCustomerName(first_name, last_name);
   }

   // 4
   public List<TitleOnly> getBookTitleByQuantity(Integer quantity) {

      return bookRepository.findBookByQuantity(quantity);
   }

   // 5
   public Double totalPriceByCustomerName(String first_name, String last_name) {
      return orderRepository.getOrderTotalByCustomerName(first_name, last_name);
   }

   // 6
   public List<FirstAndLastName> customerNameByAmountSpent(Integer amount) {
      return orderRepository.getCustomersWhoSpentMoreThan(amount);
   }

   // 7
   public List<TitleOnly> getBookNamesBySupplierName(String supplierName)  {
      return bookRepository.listTheNameOfBooksFromSupplier(supplierName);
   }

   // 8
   public List<FirstLastNameTotalPurchases> getCustomerTotalPurchasesDescending()   {
      return orderRepository.TotalPurchasesOfCustomersDescendingOrder();
   }

   // 9
   public List<TitleAndShipperName> findBookNamesAndShipperNameByDate(String dateIn)   {
      return bookRepository.getBookTitleAndShipperNameByShippedDate(dateIn);
   }

   // 10
   public List<TitleOnly> getBookTitlesThatBothHaveOrdered(String first_name, String last_name,
                                                           String first_name2, String last_name2)  {
      return bookRepository.findBookTitlesBothCustomersHaveOrderedByCustomerNames(first_name, last_name, first_name2, last_name2);
   }

   // 11
   public List<TitleOnly> getBooksEmployeeResponsibleFor(String first_name, String last_name)   {
      return bookRepository.findBooksEmployeeResponsibleForByEmployeeName(first_name, last_name);
   }

   // 12
   public List<TitleAndQuantity> getAllBookTitlesAndQuantitiesOrdered() {
      return orderRepository.getOrderedBookTitlesAndTotalQuantities();
   }

   // 13
   public List<FirstAndLastName> getCustomerByTotalAmountOrders(Integer totalBooksOrdered)   {
      return orderRepository.findCustomersNamesByNumberOfBooksOrdered(totalBooksOrdered);
   }

   //14
   public List<FirstLastNameAndTitle> getCustomerByOrderedBookCategory(String category_name, String category_name2)  {
      return orderRepository.findCustomersByOrderedBookCategory(category_name, category_name2);
   }

   // 15
   public List<FirstAndLastNameLookupByAuthor> getCustomersThatHaveOrderBookWrittenByAuthor(String author) {
      return orderRepository.findCustomerWhoOrderedBookWrittenByAuthor(author);
   }

   // 16
   public List<FirstLastNameAndTotalSale> employeeNamesAndTotalSales()  {
      return orderRepository.getEmployeeNamesAndTotalSales();
   }

   // 17
   public List<TitleAndTotalQuantity> getBookNamesAndQuantitiesNotShippedByDate(String shipped_date)  {
      return orderRepository.findTitleAndQuantityForOpenOrdersByShippedDate(shipped_date);
   }

   // 18
   public List<FirstLastNameAndTotalOrders> findCustomersAndQuantitiesByTotalOrders(Integer totalOrders) {
      return customerRepository.getCustomerAndOrderQuantitiesByTotalOrders(totalOrders);
   }

   // 19
   public List<FirstLastNameAndPhoneAndTotalOrders> findCustomerNamesAndPhoneThatOrderedMoreThan(Integer totalOrders)   {
      return customerRepository.getCustomerNameAndPhoneThatOrderedMoreThan(totalOrders);
   }





   /**
    *
    * These are the get all tables methods. You can get all of each table individually.
    *
    * @return (s) - all of the values in each repository table.
    */
   public List<Book> allBooks()  {
      return bookRepository.findAll();
   }

   // returns all the customer data
   public List<Customer> retrieveAllCustomers()   {
      return customerRepository.findAll();
   }

   // returns all employee data
   public List<Employee> allEmployees()   {
      return employeeRepository.findAll();
   }

   // returns all orders data
   public List<Order> allOrders()  {
      return orderRepository.findAll();
   }

   // returns all shipper data
   public List<Shipper> allShippers()  {
      return shipperRepository.findAll();
   }

   // returns all subject data
   public List<Subject> allSubjects()  {
      return subjectRepository.findAll();
   }

   // returns all supplier data
   public List<Supplier> allSuppliers()   {
      return supplierRepository.findAll();
   }


}
