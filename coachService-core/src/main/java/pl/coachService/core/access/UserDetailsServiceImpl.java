package pl.coachService.core.access;

import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import pl.coachService.commons.NotExistException;
import pl.coachService.commons.access.UserAccountDTO;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsServiceImpl implements UserDetailsService {

    static final Logger LOGGER = Logger.getLogger(UserDetailsServiceImpl.class);

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {

        LOGGER.debug("Calling loadUserByUsername");

        UserAccountDTO userAccDto;

        try {
            userAccDto = UserManager.getUserDTO(username, false);
        } catch (NotExistException ex) {
            throw new UsernameNotFoundException("user not found", ex);
        }

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("admin")); // todo: add roles functionality

        LOGGER.debug("user found: " + userAccDto.getUsername());
        User user = new User(
                userAccDto.getUsername(),
                userAccDto.getPassword(),
                true, true, true, true,
                authorities
        );

        return user;
    }
}
