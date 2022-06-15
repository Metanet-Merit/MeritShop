package com.merit.meritShop.user.service;

import com.merit.meritShop.user.domain.CustomUserDetail;
import com.merit.meritShop.user.domain.User;
import com.merit.meritShop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(String email, String loginType)
            throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        Optional<User> user = userRepository.findByEmailAndLoginType(email, loginType);

        if(user == null){
            throw new UsernameNotFoundException(email);
        }
        //userDetail을 default로 사용할때
//        return new User(person.get(0).getId(), person.get(0).getPassword(), "ROLE_USER");

        return new CustomUserDetail(user.get());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
