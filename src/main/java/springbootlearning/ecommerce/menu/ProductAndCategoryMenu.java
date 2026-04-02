//package springbootlearning.ecommerce.menu;
//
//import jakarta.transaction.Transactional;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Component;
//import springbootlearning.ecommerce.entities.Category;
//import springbootlearning.ecommerce.entities.Product;
//import springbootlearning.ecommerce.exceptions.CategoryAlreadyExistException;
//import springbootlearning.ecommerce.exceptions.CategoryNotFoundException;
//import springbootlearning.ecommerce.exceptions.InvalidInputException;
//import springbootlearning.ecommerce.services.CategoryService;
//import springbootlearning.ecommerce.services.ProductService;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
//@AllArgsConstructor
//@Component
//public class ProductAndCategoryMenu {
//    private final Scanner scanner = new Scanner(System.in);
//    private final CategoryService categoryService;
//    private final ProductService productService;
//
//    public void viewProducts(){
//        System.out.println("1. View All" +
//                "\n2. View by category");
//        String choice =  scanner.nextLine();
//        if(choice.equals("1"))
//            viewAllProducts();
//        else if(choice.equals("2"))
//            viewAllByCategory();
//    }
//    public void viewAllProducts(){
//        productService.getAll().forEach(System.out::println);
//    }
//
//    public void viewAllByCategory(){
//        try {
//            Long id = selectCategory();
//            productService.getAllByCategoryId(id).forEach(System.out::println);
//        } catch (InvalidInputException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//    @Transactional
//    public void addProduct(){
//        Category category = null;
//        try {
//            category = getCategoryById(selectCategory());
//        } catch (CategoryNotFoundException | InvalidInputException e) {
//            System.out.println(e.getMessage());
//        }
//        List<Product> newProducts = new ArrayList<>();
//
//        while(true) {
//            System.out.println("1. Add product" +
//                    "\n2. Save");
//            String choice = scanner.nextLine();
//            if(choice.equals("1"))
//                newProducts.add(finalProduct(category));
//            else if(choice.equals("2")) {
//                saveAllProducts(newProducts);
//                System.out.println("All products saved.");
//                break;
//            }
//        }
//    }
//
//    public void addCategory(){
//        System.out.print("Enter new category name: ");
//        try {
//            categoryService.addCategory(scanner.nextLine());
//        } catch (CategoryAlreadyExistException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    public void saveAllProducts(List<Product> products){
//        products.forEach(productService::save);
//    }
//    public Product finalProduct(Category category){
//        Product product = createProduct();
//        product.setCategory(category);
//        return product;
//    }
//    public Product createProduct(){
//        System.out.print("Name: ");
//        String name = scanner.nextLine();
//        System.out.print("Price: ");
//        Double price = Double.valueOf(scanner.nextLine());
//        System.out.print("Description: ");
//        String description = scanner.nextLine();
//        System.out.print("Status: ");
//        String status = scanner.nextLine();
//        return new Product(name, price, description, status);
//    }
//
//    public Category getCategoryById(Byte id) throws CategoryNotFoundException {
//        return categoryService.getCategoryById(id);
//    }
//    public Long selectCategory() throws InvalidInputException {
//        List<Category> categories = categoryService.getAll();
//        categories.forEach(System.out::println);
//        try {
//            Long choice = Long.parseLong(scanner.nextLine());
//            return choice;
//        }catch(NumberFormatException e)
//        {
//            throw new InvalidInputException("Invalid input.");
//        }
//    }
//}
