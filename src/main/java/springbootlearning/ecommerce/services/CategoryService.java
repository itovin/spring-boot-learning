package springbootlearning.ecommerce.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springbootlearning.ecommerce.entities.Category;
import springbootlearning.ecommerce.exceptions.CategoryAlreadyExistException;
import springbootlearning.ecommerce.exceptions.CategoryNotFoundException;
import springbootlearning.ecommerce.repositories.CategoryRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public boolean isCategoryPresent(String name){
        return categoryRepository.findByName(name).isPresent();
    }
    public boolean isCategoryPresent(Long id){
        return categoryRepository.findById(id).isPresent();
    }


    public void addCategory(String name) throws CategoryAlreadyExistException {
        if(isCategoryPresent(name))
            throw new CategoryAlreadyExistException("Category already exist.");
        save(new Category(name));
    }

    public Category getCategoryById(Long id) throws CategoryNotFoundException {
        if(!isCategoryPresent(id))
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
