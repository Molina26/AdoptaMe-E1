package utez.edu.mx.adoptame.e1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import utez.edu.mx.adoptame.e1.entity.Blog;
import utez.edu.mx.adoptame.e1.entity.MovementManagement;
import utez.edu.mx.adoptame.e1.entity.UserAdoptame;
import utez.edu.mx.adoptame.e1.model.request.blog.BlogInsertDto;
import utez.edu.mx.adoptame.e1.repository.BlogRepository;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.adoptame.e1.repository.UserAdoptameRepository;
import utez.edu.mx.adoptame.e1.util.InfoMovement;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validator;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService{

    @Autowired
    private Validator validator;

    private final BlogRepository blogRepository;
    private final MovementManagementServiceImpl movementManagementService;
    private final InfoMovement infoMovement;
    private final UserAdoptameRepository userAdoptameRepository;

    private final Logger logger = LoggerFactory.getLogger(BlogServiceImpl.class);


    public BlogServiceImpl (BlogRepository blogRepository, MovementManagementServiceImpl movementManagementService, InfoMovement infoMovement,
                            UserAdoptameRepository userAdoptameRepository){
        this.blogRepository = blogRepository;
        this.movementManagementService = movementManagementService;
        this.infoMovement = infoMovement;
        this.userAdoptameRepository = userAdoptameRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Blog> findAllBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Blog> findBlogById(Long id) {
        Optional<Blog> blog = blogRepository.findById(id);
        if(blog.isPresent()){
            return blog;
        }
        return Optional.empty();
    }


    @Override
    public boolean saveBlog(BlogInsertDto blogDto, String imageName, String username) {
        boolean validInsert = false;

        UserAdoptame user = userAdoptameRepository.findUserByUsername(username);

        if(user != null){
            Blog blog = new Blog();

            BeanUtils.copyProperties(blogDto, blog);

            blog.setImage(imageName);
            blog.setUser(user);


            try{
                Blog blogInsertedBd = blogRepository.save(blog);

                if(blogInsertedBd.getId() != 0){

                    validInsert = true;

                    MovementManagement movement = new MovementManagement();

                    movement.setModuleName(infoMovement.getModuleName());
                    movement.setUsername(infoMovement.getUsername());
                    movement.setAction(infoMovement.getActionMovement());
                    movement.setMovementDate(new Date());
                    movement.setNewData(blogInsertedBd.toString());

                    movementManagementService.createOrUpdate(movement);
                }

            }catch (Exception e){
                    logger.error("error to insert blog");
            }
        }
        return validInsert;
    }

    @Override
    public boolean updateBlog(Blog blog) {
        return false;
    }


    public Map<String, List<String>> getValidationInsertBlog(BlogInsertDto blogDto){
        Set<ConstraintViolation<BlogInsertDto>> violations = validator.validate(blogDto);
        Map<String, List<String>> errors = new HashMap<>();

        if(!violations.isEmpty()){
            for (ConstraintViolation<BlogInsertDto> error: violations) {
                List<String> messages = new ArrayList<>();
                Path path = error.getPropertyPath();
                String key = path.toString();
                String message = error.getMessage();

                if(errors.get(key) != null){
                    errors.get(key).add(message);
                }else{
                    messages.add(message);
                    errors.put(key,messages);
                }
            }
        }
        return errors;
    }
}
