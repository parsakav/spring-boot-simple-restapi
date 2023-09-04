package com.pgu.springboot.service;

import com.pgu.springboot.dto.RoleDto;
import com.pgu.springboot.entity.Role;
import com.pgu.springboot.exceptions.InvalidRoleException;
import com.pgu.springboot.model.response.ErrorMessages;
import com.pgu.springboot.repository.RoleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.RoleNotFoundException;

@Service
@Transactional

public class RoleService implements InitializingBean {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    public RoleDto findRoleIdByRoleName(String roleName) {
      Role role=  roleRepository.findByName(roleName);
if(role == null){
    throw new InvalidRoleException(ErrorMessages.ROLE_NOT_FOUND.getErrorMessage());
}
      RoleDto returnValue= new RoleDto();
        BeanUtils.copyProperties(role,returnValue);
        return returnValue;
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        Role role1 = new Role();
        role1.setId(1L);
        role1.setName("ROLE_ADMIN");
        roleRepository.saveAndFlush(role1);
        role1.setId(2L);

        role1.setName("ROLE_RENTER");
        roleRepository.saveAndFlush(role1);
        role1.setId(3L);

        role1.setName("ROLE_LESSOR");
        roleRepository.saveAndFlush(role1);

    }

}
