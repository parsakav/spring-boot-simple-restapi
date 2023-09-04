package com.pgu.springboot.service;

import com.pgu.springboot.dto.RoleDto;
import com.pgu.springboot.dto.UserDto;
import com.pgu.springboot.entity.Ads;
import com.pgu.springboot.entity.Rented;
import com.pgu.springboot.entity.Role;
import com.pgu.springboot.entity.User;
import com.pgu.springboot.exceptions.UserServiceException;
import com.pgu.springboot.model.response.ErrorMessages;
import com.pgu.springboot.repository.UserRepository;
import com.pgu.springboot.utils.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.RoleNotFoundException;
import java.util.*;

@Service("userServiceImpl")
@Transactional
public class UserService implements UserDetailsService {

    private final PasswordEncoder encoder;
    private final Utils utils;
    private final UserRepository userRepository;

private final RoleService roleService;


    public UserService(PasswordEncoder encoder, Utils utils, UserRepository userRepository, RoleService roleService) {
        this.encoder = encoder;
        this.utils = utils;
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    public UserDto getUserByUsername(String username) {

        // TODO Auto-generated method stub
        User userEntity = userRepository.findByUsername(username);
        if (userEntity != null) {
            UserDto returnValue = new UserDto();
            BeanUtils.copyProperties(userEntity, returnValue);
            setRole(userEntity,returnValue);

            return returnValue;
        }throw new UsernameNotFoundException(username);
    }

    public UserDto createUser(UserDto user) throws UserServiceException {
        if (isUserExist(user.getUsername()))
            throw new UserServiceException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());
        User userEntity = new User();
        Set<Role> roleSet = new HashSet<>();
        RoleDto roleDto =roleService.findRoleIdByRoleName(user.getRole());
        Role role= new Role();
        BeanUtils.copyProperties(roleDto,role);
        roleSet.add(role);
        String userId = utils.generateUserId(30);
        BeanUtils.copyProperties(user, userEntity);
        userEntity.setEncryptedPassword(encoder.encode(user.getPassword()));
        userEntity.setUserId(userId);
        userEntity.setRoles(roleSet);
        User storedUserDetails = userRepository.save(userEntity);
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(storedUserDetails, returnValue);
setRole(storedUserDetails,returnValue);
return returnValue;
    }

     Optional<User> findUserById(String userId){
        User userEntity = userRepository.findByUserId(userId);
return Optional.ofNullable(userEntity);
    }
    Optional<User> findUserByName(String username){
        User userEntity = userRepository.findByUsername(username);
return Optional.ofNullable(userEntity);
    }
    public UserDto getUserById(String userId) {
        Optional<User> userEntity = findUserById(userId);

        if (userEntity.isPresent()) {
            UserDto returnValue = new UserDto();
            BeanUtils.copyProperties(userEntity.get(), returnValue);
            setRole(userEntity.get(),returnValue);
          returnValue.setAds(AdsService.convertAdsEntityToAdsDto(userEntity.get().getAds()));
            return returnValue;
        } throw new UsernameNotFoundException(userId);
    }



    private boolean isUserExist(String username) {
        return userRepository.findByUsername(username) != null;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        List<GrantedAuthority> roles = new ArrayList<>();
        for(Role r:user.getRoles()) {
            roles.add(new SimpleGrantedAuthority(r.getName()));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getEncryptedPassword(), roles);

    }
    public static void setRole(User u, UserDto dto){
        dto.setRole(((Role)u.getRoles().toArray()[0]).getName());

    }



}
