package com.raofy.springsecurity.config;

import com.raofy.springsecurity.repository.UserDetailsRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;

/**
 * @author raofy
 * @date 2021-04-13 17:57
 * @desc 注入Bean
 */
@Configuration
public class SpringIoc {

    @Bean
    public UserDetailsRepository getUserDetailsRepository() {
        UserDetailsRepository userDetailsRepository = new UserDetailsRepository();
        UserDetails userDetails = User.withUsername("Raofy").password("{noop}123456").authorities(AuthorityUtils.NO_AUTHORITIES).build();
        userDetailsRepository.createUser(userDetails);
        return userDetailsRepository;
    }

    /**
     * 实现UserDetailsManager接口，通过委托的方式交付给UserDetailRepository类实现权限校验逻辑
     *
     * @param userDetailsRepository
     * @return
     */
    @Bean
    public UserDetailsManager userDetailsManager(UserDetailsRepository userDetailsRepository) {
        return new UserDetailsManager() {
            @Override
            public void createUser(UserDetails user) {
                userDetailsRepository.createUser(user);
            }

            @Override
            public void updateUser(UserDetails user) {
                userDetailsRepository.updateUser(user);
            }

            @Override
            public void deleteUser(String username) {
                userDetailsRepository.deleteUser(username);
            }

            @Override
            public void changePassword(String oldPassword, String newPassword) {
                userDetailsRepository.changePassword(oldPassword, newPassword);
            }

            @Override
            public boolean userExists(String username) {
                return userDetailsRepository.userExists(username);
            }

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userDetailsRepository.loadUserByUsername(username);
            }
        };
    }
}
