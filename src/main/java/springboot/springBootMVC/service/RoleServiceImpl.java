package springboot.springBootMVC.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.springBootMVC.dao.RoleRepository;
import springboot.springBootMVC.model.Role;
import java.util.List;
import java.util.Optional;


@Service
@Transactional  //Транзакция это последовательность операций, которые выполняются как одно целое. То есть либо все выполнятся сразу, либо, если, случится ошибка, ни одна не выполнится.
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        System.out.println("RoleServiceImpl - constructor RoleServiceImpl(RoleRepository roleRepository)");
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAllRoles() {
        System.out.println("RoleServiceImpl - getAllRoles");
        return roleRepository.findAll();
    }

    @Override
    public void add(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void edit(Role role) {
        roleRepository.save(role);
    }

    @Override
    public Role getById(long id) {
        Role role = null;
        Optional<Role> opt = roleRepository.findById(id);
        if (opt.isPresent()) {
            role = opt.get();
        }
        return role;
    }

    @Override
    public Role getByName(String name) throws NotFoundException {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            throw new NotFoundException(name);
        }
        return role;
    }
}
