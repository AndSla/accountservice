package com.learning.accountservice.service;

import com.learning.accountservice.model.NeatUser;
import com.learning.accountservice.repository.NeatUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NeatUserService implements UserDetailsService {

    @Autowired
    NeatUserRepository neatUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<NeatUser> neatUser = neatUserRepository.findByUsername(username);
        if (neatUser.isPresent()) {
            return neatUser.get();
        } else {
            throw new UsernameNotFoundException("Wal się na pysk");
        }
    }

}
