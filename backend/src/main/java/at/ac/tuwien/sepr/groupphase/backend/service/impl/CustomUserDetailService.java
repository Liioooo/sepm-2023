package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserLoginDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserRegisterDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.UserLocationMapper;
import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.entity.UserRole;
import at.ac.tuwien.sepr.groupphase.backend.exception.ConflictException;
import at.ac.tuwien.sepr.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepr.groupphase.backend.repository.ApplicationUserRepository;
import at.ac.tuwien.sepr.groupphase.backend.security.JwtTokenizer;
import at.ac.tuwien.sepr.groupphase.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class CustomUserDetailService implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final ApplicationUserRepository applicationUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenizer jwtTokenizer;
    private final UserLocationMapper userLocationMapper;

    @Autowired
    public CustomUserDetailService(ApplicationUserRepository applicationUserRepository, PasswordEncoder passwordEncoder, JwtTokenizer jwtTokenizer, UserLocationMapper userLocationMapper) {
        this.applicationUserRepository = applicationUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenizer = jwtTokenizer;
        this.userLocationMapper = userLocationMapper;
    }

    @Override
    public ApplicationUser loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = applicationUserRepository.findUserByEmail(email);
        return user.orElseThrow(() -> new UsernameNotFoundException("No user with email %s found".formatted(email)));
    }

    @Override
    public String login(UserLoginDto userLoginDto) {
        try {
            UserDetails userDetails = loadUserByUsername(userLoginDto.getEmail());
            if (userDetails.isAccountNonExpired() && userDetails.isAccountNonLocked() && userDetails.isCredentialsNonExpired()) {
                if (passwordEncoder.matches(userLoginDto.getPassword(), userDetails.getPassword())) {
                    return generateTokenForUser(userDetails);
                }
                throw new BadCredentialsException("Username or password is incorrect");
            }
            throw new BadCredentialsException("Account is locked");
        } catch (UsernameNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e);
        }
    }

    @Override
    public String register(UserRegisterDto userRegisterDto) {
        var user = ApplicationUser.builder()
            .firstName(userRegisterDto.getFirstName())
            .lastName(userRegisterDto.getLastName())
            .email(userRegisterDto.getEmail())
            .password(passwordEncoder.encode(userRegisterDto.getPassword()))
            .location(userLocationMapper.userLocationDtoToUserLocation(userRegisterDto.getLocation()))
            .role(UserRole.ROLE_USER)
            .build();

        try {
            applicationUserRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Email is already in use", ex);
        }
        return generateTokenForUser(user);
    }

    private String generateTokenForUser(UserDetails userDetails) {
        List<String> roles = userDetails.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .toList();
        return jwtTokenizer.getAuthToken(userDetails.getUsername(), roles);
    }
}
