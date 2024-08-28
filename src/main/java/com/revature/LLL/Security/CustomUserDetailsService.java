package com.revature.LLL.Security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.revature.LLL.User.User;
import com.revature.LLL.User.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ArrayList<String> roles = new ArrayList<>();

    /**
     * <h5>LoadUserByUsername takes an email and tests to see if it is in the
     * InMemory database</h5>
     *
     * @param username String - takes users email
     * @return UserDetails which is the in memory User
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Email not found"));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(roles));
    }

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
        roles.add("FARMER");
        roles.add("VET");
    }

    /**
     * <h5>Creates a collection of GrantedAuthority by iterating through
     * roles</h5>
     * <p>
     * </p>
     *
     * @param ArrayList - roles is the MemberTypes that are possible in Users
     * @return Collection of GrantedAuthority
     */
    private Collection<GrantedAuthority> mapRolesToAuthorities(List<String> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
    }
}
