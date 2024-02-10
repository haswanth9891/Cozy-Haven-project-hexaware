package com.hexaware.ccozyhaven.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.hexaware.ccozyhaven.entities.Administrator;
import com.hexaware.ccozyhaven.entities.HotelOwner;
import com.hexaware.ccozyhaven.entities.User;
import com.hexaware.ccozyhaven.entities.UserInfo;
import com.hexaware.ccozyhaven.repository.AdministratorRepository;
import com.hexaware.ccozyhaven.repository.HotelOwnerRepository;
import com.hexaware.ccozyhaven.repository.UserInfoRepository;
import com.hexaware.ccozyhaven.repository.UserRepository;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private HotelOwnerRepository hotelOwnerRepo;

    @Autowired
    private AdministratorRepository adminRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Administrator> adminInfo = null;
        Optional<User> userInfo = null;
        Optional<HotelOwner> hotelOwnerInfo = null;

        if (username.endsWith("@hexaware.com")) {
            adminInfo = adminRepo.findByEmail(username);
            return adminInfo.map(UserInfoUserDetails::new)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
        } else if (username.endsWith("@hotelowner.com")) {
            hotelOwnerInfo = hotelOwnerRepo.findByEmail(username);
            return hotelOwnerInfo.map(UserInfoUserDetails::new)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
        }

        userInfo = userRepo.findByEmail(username);
        return userInfo.map(UserInfoUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }
}
