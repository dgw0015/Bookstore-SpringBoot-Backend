package com.bookstore.backend.Controller;

import com.bookstore.backend.Entity.*;
import com.bookstore.backend.Projections.*;
import com.bookstore.backend.Exception.ResourceNotFoundException;
import com.bookstore.backend.Repository.*;
import com.bookstore.backend.Service.BookStoreQueryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
@AllArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class BookstoreController {

   private BookStoreQueryService queryService;
   private BookRepository bookRepository;
   private CustomerRepository customerRepository;
   private EmployeeRepository employeeRepository;
   private OrderRepository orderRepository;
   private OrderDetailsRepository orderDetailsRepository;
   private ShipperRepository shipperRepository;
   private SubjectRepository subjectRepository;
   private SupplierRepository supplierRepository;


   /**
    *    GET the category names of books supplied by ID and that company name.
    *    Number 1.
    *
    * @param supplierID - identifier used as constraint.
    * @param companyName - identifier used as constraint.
    * @return - The books subject name from that supplier.
    * @throws ResourceNotFoundException - current DNE in database.
    */
   @GetMapping("/books/categoryName/bySupplierID/withCompanyName/{supplierID}/{companyName}")
   public ResponseEntity<List<SubjectCategoryName>> getSubjectOfBooksBySupplier(
       @PathVariable("supplierID") Integer supplierID, @PathVariable("companyName") String companyName)
       throws ResourceNotFoundException {
      List<SubjectCategoryName> supplierCategoryName = queryService.booksSubjectBySupplier(supplierID, companyName);
      if (supplierCategoryName.isEmpty()) {
         throw new ResourceNotFoundException("SupplierID " + supplierID + " was not found in our system. Please try another request.");
      }
      return new ResponseEntity<>(supplierCategoryName, HttpStatus.ACCEPTED);
   }

   /**
    *  Gets the most expensive book from the specified supplier.
    *  2.
    *
    * @param supplierID - used in the query search.
    * @return - Most expensive book from the specified supplier.
    * @throws ResourceNotFoundException - DNE in database at this moment.
    */
   @GetMapping("/books/bySupplier/mostExpensiveFrom/{supplierID}")
   public ResponseEntity<List<TitleAndPrice>> getMostExpensiveBookBySupplierID(@PathVariable("supplierID") Integer supplierID) throws ResourceNotFoundException  {
      List<TitleAndPrice> bookTitlePrice = queryService.getTitleByPriceAndSupplierId(supplierID);
      if(bookTitlePrice.isEmpty())  {
         throw new ResourceNotFoundException("SupplierID " + supplierID + " was not found in our system. Please try another request");
      }
      return new ResponseEntity<>(bookTitlePrice, HttpStatus.ACCEPTED);
   }

   /**
    * Get the unique names of books order by customer first & last name.
    * 3.
    *
    * @param first_name - search parameter.
    * @param last_name - search parameter.
    * @return - Unique names of books order by the specified customer.
    * @throws ResourceNotFoundException - currently DNE in database.
    */
   @GetMapping("/books/unique/byFirstLast/{first_name}/{last_name}")
   public ResponseEntity<List<TitleOnly>> getUniqueBooksOrderedByCustomer(
       @PathVariable("first_name") String first_name, @PathVariable("last_name") String last_name) throws ResourceNotFoundException {
      List<TitleOnly> uniqueBooks = queryService.bookNamesOrderedByName(first_name, last_name);
      if (uniqueBooks.isEmpty()) {
         throw new ResourceNotFoundException("The name " + first_name + " " + last_name + " was not found in our system. " +
             "Please try a different request.");
      }
      return new ResponseEntity<>(uniqueBooks, HttpStatus.ACCEPTED);
   }

   /**
    * Get Books with more than 10 units in stock.
    * 4.
    *
    * @param number - represents the minimum number of units in stock.
    * @return - returns a projection of only titles per query request constraints.
    * @throws ResourceNotFoundException and a message for more reasoning.
    */
   @GetMapping("/books/byTitles/inStock/moreThan/{number}")
   public ResponseEntity<List<TitleOnly>> getAllBooksInSystem(@PathVariable("number") Integer number) throws ResourceNotFoundException {
      List<TitleOnly> books = queryService.getBookTitleByQuantity(number);
      if (!books.isEmpty()) {
         return new ResponseEntity<>(books, HttpStatus.ACCEPTED);
      }
      else  {
         throw new ResourceNotFoundException("We are out of books at the moment. Try again later.");
      }
   }

   /**
    * Get the total price that a customer has paid for books.
    * 5.
    *
    * @param firstName - search parameter.
    * @param lastName - search parameter.
    * @return - total price that specified customer has paid for books.
    * @throws ResourceNotFoundException - customer DNE in database at the moment.
    */
   @GetMapping("/orders/byFirstLast/totalPrice/{firstName}/{lastName}")
   public ResponseEntity<Double> bookOrderTotalByCustomerName(
       @PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) throws ResourceNotFoundException  {
      Double totalOrderPrice = queryService.totalPriceByCustomerName(firstName, lastName);
      if (totalOrderPrice == null)   {
         throw new ResourceNotFoundException("The name " + firstName + " " + lastName + " was not found. Please try a different request.");
      }
      return new ResponseEntity<>(totalOrderPrice, HttpStatus.ACCEPTED);
   }

   /**
    * Get customer who spent more than 80 or whatever queried amount.
    * 6.
    *
    * @param amount - customers have paid for books.
    * @return - customers names that spent more than the specified amount.
    * @throws ResourceNotFoundException - customer have not spend that amount yet.
    */
   @GetMapping("/orders/byAmount/customersSpent/greaterThan/{amount}")
   public ResponseEntity<List<FirstAndLastName>> lookupCustomerByAmountSpent(@PathVariable("amount") Integer amount) throws ResourceNotFoundException    {
      List<FirstAndLastName> customerNamesList = queryService.customerNameByAmountSpent(amount);
      if (customerNamesList.isEmpty()) {
         throw new ResourceNotFoundException("No customer has currently spent than much yet! Please select a lower amount.");
      }
      return new ResponseEntity<>(customerNamesList, HttpStatus.ACCEPTED);
   }

   /**
    * GET the names of books from a specific supplier by supplier name.
    * 7.
    *
    * @param supplierName - search parameter.
    * @return - list of books from the specific supplier.
    * @throws ResourceNotFoundException - error when DNE in database.
    */
   @GetMapping("/books/bySupplierName/{supplierName}")
   public ResponseEntity<List<TitleOnly>> bookNamesBySupplierName(@PathVariable("supplierName") String supplierName) throws ResourceNotFoundException {
      List<TitleOnly> booksFromSupplierName = queryService.getBookNamesBySupplierName(supplierName);
      if (booksFromSupplierName.isEmpty()) {
         throw new ResourceNotFoundException("We do not have any books with a supplier name of " + supplierName + ". Please try another name.");
      }
      return new ResponseEntity<>(booksFromSupplierName, HttpStatus.ACCEPTED);
   }

   /**
    * GET Customer total in purchases in descending order.
    * 8.
    *
    * @return - customer total purchases in DESC order.
    * @throws ResourceNotFoundException - No customers in the database at this moment.
    */
   @GetMapping("/orders/byTotalPurchases/orderDescending")
   public ResponseEntity<List<FirstLastNameTotalPurchases>> customerTotalPurchasesOrderedByPriceDescending() throws ResourceNotFoundException   {
      List<FirstLastNameTotalPurchases> customersTotalPriceList = queryService.getCustomerTotalPurchasesDescending();
      if (customersTotalPriceList.isEmpty()) {
         throw new ResourceNotFoundException("This feature is not responding. Please try again later.");
      }
      return new ResponseEntity<>(customersTotalPriceList, HttpStatus.ACCEPTED);
   }

   /**
    * GET Book names that were shipped by dateIn and their suppliers name
    * 9.
    *
    * @return - return the books that for 8/4/2016
    * @throws ResourceNotFoundException - DNE in database.
    */
   @GetMapping("/books/byShippedDate/8/4/2016")
   public ResponseEntity<List<TitleAndShipperName>> BookTitlesAndShipperNameByDateShipped() throws ResourceNotFoundException    {
      String shipped_date = "8/4/2016";
      List<TitleAndShipperName>  booksShippedByDateList = queryService.findBookNamesAndShipperNameByDate(shipped_date);
      if (booksShippedByDateList.isEmpty())  {
         throw new ResourceNotFoundException("Their were no books shipped on " + shipped_date + ", please try a different date. " +
             "Date format is m/d/yyyy or mm/dd/yyyy.");
      }
      return new ResponseEntity<>(booksShippedByDateList, HttpStatus.ACCEPTED);
   }

   /**
    * Get books that customer X and Y have ordered by names. x = firstname1 lastname1, y = firstname4 lastname4.
    * 10.
    * @param first_name - search param.
    * @param last_name - search param.
    * @param first_name2 - search param.
    * @param last_name2 - search param.
    * @return - list of books that both customers ordered.
    * @throws ResourceNotFoundException - These two customer haven't ordered any of the same books.
    */
   @GetMapping("/books/unique/thatBoth/customerX/customerY/ordered/{first_name}/{last_name}/{first_name2}/{last_name2}")
   public ResponseEntity<List<TitleOnly>> titlesOfBooksOrderedByBothCustomers(
       @PathVariable("first_name") String first_name, @PathVariable("last_name") String last_name,
       @PathVariable("first_name2") String first_name2, @PathVariable("last_name2") String last_name2) throws ResourceNotFoundException   {
      List<TitleOnly> bookTitlesList = queryService.getBookTitlesThatBothHaveOrdered(first_name, last_name, first_name2, last_name2);
      if (bookTitlesList.isEmpty()) {
         throw new ResourceNotFoundException("These two customer have not ordered any of the same books.");
      }
      return new ResponseEntity<>(bookTitlesList, HttpStatus.ACCEPTED);
   }

   /**
    * GET book titles that employee was responsible for by employee name(firstname6 lastname6)
    * 11.
    *
    * @param first_name - search param.
    * @param last_name - search - param.
    * @return - books that the employee is responsible for.
    * @throws ResourceNotFoundException - DNE is the database.
    */
   @GetMapping("/books/titles/byEmployeeName/responsibleFor/{first_name}/{last_name}")
   public ResponseEntity<List<TitleOnly>> booksThatEmployeeResponsibleByEmployeeName(
       @PathVariable("first_name") String first_name, @PathVariable("last_name") String last_name) throws ResourceNotFoundException    {
      List<TitleOnly> bookTitles = queryService.getBooksEmployeeResponsibleFor(first_name, last_name);
      if (bookTitles.isEmpty())  {
         throw new ResourceNotFoundException("Employee name " + first_name + " " + last_name + " is not currently responsible for any books.");
      }
      return new ResponseEntity<>(bookTitles, HttpStatus.ACCEPTED);
   }

   /**
    * GET names of all ordered books and their quantities. List in ascending order
    * 12.
    *
    * @return - all books/quantities in ascending orders
    * @throws ResourceNotFoundException - DNE in database.
    */
   @GetMapping("/orders/allTitles/quantityOrdered/ascending")
   public ResponseEntity<List<TitleAndQuantity>> bookTitlesAndQuantitiesOrdered() throws ResourceNotFoundException   {
      List<TitleAndQuantity> titleAndQuantities = queryService.getAllBookTitlesAndQuantitiesOrdered();
      if (titleAndQuantities.isEmpty())   {
         throw new ResourceNotFoundException("There are currently no orders in our system.");
      }
      return new ResponseEntity<>(titleAndQuantities, HttpStatus.ACCEPTED);
   }

   /**
    * Get customer who have ordered at least x books. x = 2
    * 13.
    *
    * @param totalBooksOrdered - search param.
    * @return - described above.
    * @throws ResourceNotFoundException - DNE in database.
    */
   @GetMapping("/orders/booksCustomersHaveOrdered/atLeast/{totalBooksOrdered}")
   public ResponseEntity<List<FirstAndLastName>> customerNamesByTotalBooksOrdered(
       @PathVariable("totalBooksOrdered") Integer totalBooksOrdered) throws ResourceNotFoundException    {
      List<FirstAndLastName> firstAndLastNames = queryService.getCustomerByTotalAmountOrders(totalBooksOrdered);
      if (firstAndLastNames.isEmpty()) {
         throw new ResourceNotFoundException("Currently no customers in our system has ordered at least " + totalBooksOrdered + " books.");
      }
      return new ResponseEntity<>(firstAndLastNames, HttpStatus.ACCEPTED);
   }

   /**
    * GET customers that have ordered at least one book from category n or n2.
    * 14. - Show the name of the customers who have ordered at least a book in *category3* or *category4* and the book names.
    *
    * @param category_name - search param.
    * @param category_name2 - search param.
    * @return - description above.
    * @throws ResourceNotFoundException - DNE in database.
    */
   @GetMapping("/orders/byCategoryName/customersOrdered/fromCategory/{category_name}/{category_name2}")
   public ResponseEntity<List<FirstLastNameAndTitle>> customersByOrderedBookCategory(
       @PathVariable("category_name") String category_name, @PathVariable("category_name2") String category_name2) throws ResourceNotFoundException  {
      List<FirstLastNameAndTitle> firstLastNameAndTitles = queryService.getCustomerByOrderedBookCategory(category_name,category_name2);
      if (firstLastNameAndTitles.isEmpty())  {
         throw new ResourceNotFoundException("No customer have order a book from " + category_name + " or " + category_name2 +
             ". Please try different category names.");
      }
      return new ResponseEntity<>(firstLastNameAndTitles, HttpStatus.ACCEPTED);
   }

   /**
    * Get the name of the customer who has ordered at least one book written by AuthorN.
    * 15. Show the name of the customer who has ordered at least one book written by *author1*.
    *
    * @param author - search param.
    * @return - description above.
    * @throws ResourceNotFoundException - DNE in database.
    */
   @GetMapping("/orders/byCustomerName/orderedBook/writtenBy/{author}")
   public ResponseEntity<List<FirstAndLastNameLookupByAuthor>> customerOrderedBookByAuthor(@PathVariable("author") String author) throws ResourceNotFoundException  {
      List<FirstAndLastNameLookupByAuthor> firstAndLastNameList = queryService.getCustomersThatHaveOrderBookWrittenByAuthor(author);
      if (firstAndLastNameList.isEmpty()) {
         throw new ResourceNotFoundException("Currently, no customers in our system have ordered a book from " + author + ". " +
             "Please try again with a different author");
      }
      return new ResponseEntity<>(firstAndLastNameList, HttpStatus.ACCEPTED);
   }

   /**
    * GET the name and total sale (price of orders) of each employee
    * 16.
    *
    * @return - description above.
    * @throws ResourceNotFoundException - DNE in database.
    */
   @GetMapping("/orders/employeesNames/totalSales")
   public ResponseEntity<List<FirstLastNameAndTotalSale>> employeeTotalSales()  throws ResourceNotFoundException {
      List<FirstLastNameAndTotalSale> firstLastNameAndTotalSales = queryService.employeeNamesAndTotalSales();
      if (firstLastNameAndTotalSales.isEmpty()) {
         throw new ResourceNotFoundException("Currently there are no employee sales in the system. Please try again another time.");
      }
      return new ResponseEntity<>(firstLastNameAndTotalSales, HttpStatus.ACCEPTED);
   }

   /**
    * GET the book names and their respective quantities for open orders (the orders which have not been shipped) at midnight 08/04/2016.
    * 17.
    *
    * @return - description above.
    * @throws ResourceNotFoundException - DNE in database.
    */
   @GetMapping("/orders/byDate/bookTitlesAndQuantities/notYetShipped")
   public ResponseEntity<List<TitleAndTotalQuantity>> bookTitlesQuantitiesNotYetShippedByDate() throws ResourceNotFoundException   {
      String shipped_date = "8/4/2016";
      List<TitleAndTotalQuantity> titleAndTotalQuantities = queryService.getBookNamesAndQuantitiesNotShippedByDate(shipped_date);
      if (titleAndTotalQuantities.isEmpty()) {
         throw new ResourceNotFoundException("There are no open orders on " + shipped_date + ". Please enter a different date. " +
             "Make sure you date format is m/d/yyyy or mm/dd/yyyy.");
      }
      return new ResponseEntity<>(titleAndTotalQuantities, HttpStatus.ACCEPTED);
   }

   /**
    * GET the names of customers who have ordered more than N book and the corresponding quantities.  List the result in the descending quantity.
    * 18. - Show the names of customers who have ordered more than 1 book and the corresponding quantities.  List the result in the descending
    * quantity.
    *
    * @param totalBooksOrdered - search param.
    * @return - description above.
    * @throws ResourceNotFoundException - DNE in database.
    */
   @GetMapping("/customers/byOrders/customerNames/{totalBooksOrdered}/results/listByQuantity/descending")
   public ResponseEntity<List<FirstLastNameAndTotalOrders>> customersThatOrderedMoreThan(
       @PathVariable("totalBooksOrdered") Integer totalBooksOrdered) throws ResourceNotFoundException    {
      List<FirstLastNameAndTotalOrders> firstLastNameAndTotalOrders = queryService.findCustomersAndQuantitiesByTotalOrders(totalBooksOrdered);
      if (firstLastNameAndTotalOrders.isEmpty())   {
         throw new ResourceNotFoundException("Currently, no customer has ordered more than " + totalBooksOrdered + ". Please enter a different number.");
      }
      return new ResponseEntity<>(firstLastNameAndTotalOrders, HttpStatus.ACCEPTED);
   }

   /**
    * GET the names of customers who have ordered more than N books and their respective telephone numbers.
    * 19. - Show the names of customers who have ordered more than 3 books and their respective telephone numbers.
    *
    * @param totalOrders - search param.
    * @return - description above.
    * @throws ResourceNotFoundException - DNE in database.
    */
   @GetMapping("/customers/orderedMoreThan/{totalOrders}/show/telephone/true")
   public ResponseEntity<List<FirstLastNameAndPhoneAndTotalOrders>> customerNamesPhoneThatOrderedMoreThan(
       @PathVariable("totalOrders") Integer totalOrders) throws ResourceNotFoundException    {
      List<FirstLastNameAndPhoneAndTotalOrders> firstLastNameAndPhoneAndTotalOrders = queryService.findCustomerNamesAndPhoneThatOrderedMoreThan(totalOrders);
      if (firstLastNameAndPhoneAndTotalOrders.isEmpty()) {
         throw new ResourceNotFoundException("Currently, no customers have order more than " + totalOrders + ". Please try a different number.");
      }
      return new ResponseEntity<>(firstLastNameAndPhoneAndTotalOrders, HttpStatus.ACCEPTED);
   }


   //////////////////////////////////////////////////////////
   // The Get ALL methods for each
   // Entity in our database are below.
   //////////////////////////////////////////////////////////

   // Get all customer.
   @GetMapping("/allCustomers")
   public ResponseEntity<List<Customer>> allCustomers() throws ResourceNotFoundException {
      List<Customer> customers = queryService.retrieveAllCustomers();
      if (customers.isEmpty()) {
         throw new ResourceNotFoundException("There is not customer with that ID in our records.");
      }
      return new ResponseEntity<>(customers, HttpStatus.ACCEPTED);
   }

   // Get all Books
   @GetMapping("/allBooks")
   public ResponseEntity<List<Book>> getAllBooks() throws ResourceNotFoundException {
      List<Book> allBooks = queryService.allBooks();
      if (allBooks.isEmpty()) {
         throw new ResourceNotFoundException("No books are currently in our database. Try again later.");
      }
      return new ResponseEntity<>(allBooks, HttpStatus.ACCEPTED);
   }

   //Get all employees.
   @GetMapping("/allEmployees")
   public ResponseEntity<List<Employee>> getAllEmployees()  throws ResourceNotFoundException   {
      List<Employee> allEmployees = queryService.allEmployees();
      if (allEmployees.isEmpty())   {
         throw new ResourceNotFoundException("No Employees currently in the database.");
      }
      return new ResponseEntity<>(allEmployees, HttpStatus.ACCEPTED);
   }

   // Get all orders
   @GetMapping("/allOrders")
   public ResponseEntity<List<Order>> getAllOrders() throws ResourceNotFoundException  {
      List<Order> allOrders = queryService.allOrders();
      if (allOrders.isEmpty())   {
         throw new ResourceNotFoundException("No Orders are currently in the database.");
      }
      return new ResponseEntity<>(allOrders, HttpStatus.ACCEPTED);
   }

   // Gets all order details information.
   @GetMapping("/allOrderDetails")
   public ResponseEntity<List<OrderDetails>> getAllOrdersDetails() throws ResourceNotFoundException  {
      List<OrderDetails> allOrdersDetails = orderDetailsRepository.findAll();
      if (allOrdersDetails.isEmpty())   {
         throw new ResourceNotFoundException("No Orders details are currently in the database.");
      }
      return new ResponseEntity<>(allOrdersDetails, HttpStatus.ACCEPTED);
   }

   // Get all Shippers
   @GetMapping("/allShippers")
   public ResponseEntity<List<Shipper>> getAllShippers() throws ResourceNotFoundException {
      List<Shipper> allShippers = queryService.allShippers();
      if (allShippers.isEmpty()) {
         throw new ResourceNotFoundException("No Shippers are currently in the database.");
      }
      return new ResponseEntity<>(allShippers, HttpStatus.ACCEPTED);
   }

   // Get all Subjects.
   @GetMapping("/allSubjects")
   public ResponseEntity<List<Subject>> getAllSubjects() throws ResourceNotFoundException {
      List<Subject> allSubjects = queryService.allSubjects();
      if(allSubjects.isEmpty())  {
         throw new ResourceNotFoundException("No Subjects are currently in the database.");
      }
      return new ResponseEntity<>(allSubjects, HttpStatus.ACCEPTED);
   }

   // Get all Suppliers.
   @GetMapping("/allSuppliers")
   public ResponseEntity<List<Supplier>> getAllSuppliers() throws ResourceNotFoundException  {
      List<Supplier> allSuppliers = queryService.allSuppliers();
      if(allSuppliers.isEmpty()) {
         throw new ResourceNotFoundException("No Suppliers are currently in the database.");
      }
      return new ResponseEntity<>(allSuppliers, HttpStatus.ACCEPTED);
   }


   /////////////////////////////////////////////////
   // All the POST mappings are below.
   //////////////////////////////////////////////////

   // creates a new book in the system.
   @PostMapping("/add/book")
   public ResponseEntity<Book> addBook(@RequestBody Book book)   {

      Book savedBook = bookRepository.save(book);

      URI location = ServletUriComponentsBuilder.fromCurrentRequest()
          .path("/{id}")
          .buildAndExpand(savedBook.getBookID()).toUri();

      return ResponseEntity.created(location).build();
   }

   // creates a new customer in the system
   @PostMapping("/add/customer")
   public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer)    {

      Customer savedCustomer = customerRepository.save(customer);

      URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
          .buildAndExpand(savedCustomer.getCustomerID()).toUri();

      return ResponseEntity.created(location).build();
   }

   // creates a new employee.
   @PostMapping("/add/employee")
   public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee)    {

      Employee savedEmployee = employeeRepository.save(employee);

      URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
          .buildAndExpand(savedEmployee.getEmployeeID()).toUri();

      return ResponseEntity.created(location).build();
   }

   // creates a new order
   @PostMapping("/add/order")
   public ResponseEntity<Order> addOrder(@RequestBody Order order)    {

      Order savedOrder = orderRepository.save(order);

      URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
          .buildAndExpand(savedOrder.getOrderID()).toUri();

      return ResponseEntity.created(location).build();
   }


   // creates new shipper
   @PostMapping("/add/shipper")
   public ResponseEntity<Shipper> addShipper(@RequestBody Shipper shipper)  {

      Shipper savedShipper = shipperRepository.save(shipper);

      URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
          .buildAndExpand(savedShipper.getShipperID()).toUri();

      return ResponseEntity.created(location).build();
   }

   // creates a new subject
   @PostMapping("/add/subject")
   public ResponseEntity<Subject> addSubject(@RequestBody Subject subject)  {

      Subject savedSubject = subjectRepository.save(subject);

      URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
          .buildAndExpand(savedSubject.getSubjectID()).toUri();

      return ResponseEntity.created(location).build();
   }

   // creates a new supplier
   @PostMapping("/add/supplier")
   public ResponseEntity<Supplier> addSupplier(@RequestBody Supplier supplier)    {

      Supplier savedSupplier = supplierRepository.save(supplier);

      URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
          .buildAndExpand(savedSupplier.getSupplierID()).toUri();

      return ResponseEntity.created(location).build();
   }


   /////////////////////////////////////////////////
   // All the DELETE mapping is below.
   //////////////////////////////////////////////////

   /**
    *  DELETE book by book id.
    *
    * @param bookID - book that should be deleted.
    * @return - true will print success to the google developer console. Otherwise null or false means failure.
    */
   @DeleteMapping("/delete/book/{bookID}")
   public boolean deleteBook(@PathVariable("bookID") Integer bookID) {
      Optional<Book> book = bookRepository.findById(bookID);
      if(book.isPresent())  {
         bookRepository.deleteById(bookID);
         return true;
      }
      return false;
   }

   /**
    *  DELETE customer by customer id
    *
    * @param customerID - customer to be deleted.
    * @return - true will print success to the google developer console. Otherwise null or false means failure.
    */
   @DeleteMapping("/delete/customer/{customerID}")
   public boolean deleteCustomer(@PathVariable("customerID") Integer customerID)   {
      Optional<Customer> customer = customerRepository.findById(customerID);
      if(customer.isPresent())  {
         customerRepository.deleteById(customerID);
         return true;
      }
      return false;
   }

   /**
    *  DELETE employee by id.
    *
    * @param employeeID - employee that should be deleted.
    * @return - true will print success to the google developer console. Otherwise null or false means failure.
    */
   @DeleteMapping("/delete/employee/{employeeID}")
   public boolean deleteEmployee(@PathVariable("employeeID") Integer employeeID) {
      Optional<Employee> employee = employeeRepository.findById(employeeID);
      if (employee.isPresent())  {
         employeeRepository.deleteById(employeeID);
         return true;
      }
      return false;
   }

   /**
    *  DELETE order by order id.
    *
    * @param orderID - order that should be deleted.
    * @return - true will print success to the google developer console. Otherwise null or false means failure.
    */
   @DeleteMapping("/delete/order/{orderID}")
   public boolean deleteOrder(@PathVariable("orderID") Integer orderID) {
      Optional<Order> order = orderRepository.findById(orderID);
      if (order.isPresent())  {
         orderRepository.deleteById(orderID);
         return true;
      }
      return false;
   }

   /**
    *  DELETE shipper by id.
    *
    * @param shipperID - shipper that should be deleted.
    * @return - true will print success to the google developer console. Otherwise null or false means failure.
    */
   @DeleteMapping("/delete/shipper/{shipperID}")
   public boolean deleteShipper(@PathVariable("shipperID") Integer shipperID)   {
      Optional<Shipper> shipper = shipperRepository.findById(shipperID);
      if (shipper.isPresent())   {
         shipperRepository.deleteById(shipperID);
         return true;
      }
      else  {
         return false;
      }
   }

   /**
    *  DELETE subject by id.
    *
    * @param subjectID - subject that should be deleted.
    * @return - true will print success to the google developer console. Otherwise null or false means failure.
    */
   @DeleteMapping("/delete/subject/{subjectID}")
   public boolean deleteSubject(@PathVariable("subjectID") Integer subjectID) {
      Optional<Subject> subject = subjectRepository.findById(subjectID);
      if (subject.isPresent())   {
         subjectRepository.deleteById(subjectID);
         return true;
      }
      else  {
         return false;
      }
   }

   /**
    *  DELETE supplier by id.
    *
    * @param supplierID - supplier that should be deleted.
    * @return - true will print success to the google developer console. Otherwise null or false means failure.
    */
   @DeleteMapping("/delete/supplier/{supplierID}")
   public boolean deleteSupplier(@PathVariable("supplierID") Integer supplierID) {
      Optional<Supplier> supplier = supplierRepository.findById(supplierID);
      if (supplier.isPresent())  {
         supplierRepository.deleteById(supplierID);
         return true;
      }
      else  {
         return false;
      }
   }


}
