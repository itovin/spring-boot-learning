package springbootlearning.ecommerce.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springbootlearning.ecommerce.entities.Category;
import springbootlearning.ecommerce.exceptions.CategoryAlreadyExistException;
import springbootlearning.ecommerce.exceptions.CategoryNotFoundException;
import springbootlearning.ecommerce.repositories.CategoryRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public boolean isCategoryExists(String name){
        return categoryRepository.existsByName(name);
    }
    public boolean isCategoryExists(Byte id){
        return categoryRepository.existsById(Integer.valueOf(id));
    }


    public void addCategory(String name) throws CategoryAlreadyExistException {
        if(isCategoryExists(name))
            throw new CategoryAlreadyExistException("Category already exist.");
        save(new Category(name));
    }

    public Category getCategoryById(Byte id) throws CategoryNotFoundException {
        if(!isCategoryExists(id))
            throw new CategoryNotFoundException("Category not found");
        return categoryRepository.findById(id).get();
    }

    public List<Category> getAll(){
        return categoryRepository.findAll();
    }

    public void save(Category category){
        categoryRepository.save(category);
        System.out.println(category.getName() + " category succesfully added");
    }
}
