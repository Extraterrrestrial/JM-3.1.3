package springboot.springBootMVC.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.springBootMVC.dao.UserRepository;
import springboot.springBootMVC.model.User;
import java.util.List;
import java.util.Optional;

@Service
@Transactional //Транзакция это последовательность операций, которые выполняются как одно целое. То есть либо все выполнятся сразу, либо, если, случится ошибка, ни одна не выполнится.
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    private BCryptPasswordEncoder bCrypt() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void save(User user) {
        user.setPassword(bCrypt().encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public void edit(User user) {
        user.setPassword(bCrypt().encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User getById(long id) {
       return userRepository.getById(id);
    }

    @Override
    public User getByName(String name) throws NotFoundException {
        User user = userRepository.findByUsername(name);
        if (user == null) {
            throw new NotFoundException(name);
        }
        return user;
    }
    @Override
    public User getByEmail(String email) throws NotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new NotFoundException(email);
        }
        return user;
    }
}
