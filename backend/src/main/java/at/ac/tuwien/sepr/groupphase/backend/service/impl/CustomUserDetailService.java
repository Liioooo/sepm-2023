package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EmailResetDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.ResetPasswordDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UpdateUserDetailDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserLoginDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserRegisterDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.UserLocationMapper;
import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.entity.UserLocation;
import at.ac.tuwien.sepr.groupphase.backend.enums.UserRole;
import at.ac.tuwien.sepr.groupphase.backend.exception.ConflictException;
import at.ac.tuwien.sepr.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepr.groupphase.backend.repository.ApplicationUserRepository;
import at.ac.tuwien.sepr.groupphase.backend.security.JwtTokenizer;
import at.ac.tuwien.sepr.groupphase.backend.security.JwtVerifier;
import at.ac.tuwien.sepr.groupphase.backend.service.EmailService;
import at.ac.tuwien.sepr.groupphase.backend.service.UserService;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailService implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final ApplicationUserRepository applicationUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenizer jwtTokenizer;
    private final JwtVerifier jwtVerifier;
    private final UserLocationMapper userLocationMapper;
    private final EmailService emailService;

    @Autowired
    public CustomUserDetailService(ApplicationUserRepository applicationUserRepository,
                                   PasswordEncoder passwordEncoder,
                                   JwtTokenizer jwtTokenizer,
                                   UserLocationMapper userLocationMapper,
                                   EmailService emailService,
                                   JwtVerifier jwtVerifier) {
        this.applicationUserRepository = applicationUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenizer = jwtTokenizer;
        this.jwtVerifier = jwtVerifier;
        this.userLocationMapper = userLocationMapper;
        this.emailService = emailService;
    }

    @Override
    public ApplicationUser loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = applicationUserRepository.findUserByEmail(email);
        return user.orElseThrow(() -> new UsernameNotFoundException("No user with email %s found".formatted(email)));
    }

    @Override
    public String login(UserLoginDto userLoginDto) {
        try {
            ApplicationUser applicationUser = loadUserByUsername(userLoginDto.getEmail());
            if (applicationUser.isAccountNonExpired() && applicationUser.isAccountNonLocked() && applicationUser.isCredentialsNonExpired()) {
                boolean authenticationSuccess = false;
                if (passwordEncoder.matches(userLoginDto.getPassword(), applicationUser.getPassword())) {
                    applicationUser.setFailedAuths(0);
                    authenticationSuccess = true;
                } else {
                    applicationUser.setFailedAuths(applicationUser.getFailedAuths() + 1);
                }
                applicationUserRepository.save(applicationUser);
                if (authenticationSuccess) {
                    return generateTokenForUser(applicationUser);
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

    @Override
    public void unlockUser(long userId) {
        ApplicationUser user = applicationUserRepository.findById(userId).orElseThrow(() -> new NotFoundException("No user with id %s found".formatted(userId)));
        user.setLocked(false);
        user.setFailedAuths(0);
        applicationUserRepository.save(user);
    }

    @Override
    public boolean getIsAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }

    @Override
    public Optional<ApplicationUser> getCurrentlyAuthenticatedUser() {
        if (!getIsAuthenticated()) {
            return Optional.empty();
        }
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return applicationUserRepository.findUserByEmail(username);
    }

    @Override
    public ApplicationUser updateAuthenticatedUser(UpdateUserDetailDto updateUserDetailDto) {
        var applicationUser = getCurrentlyAuthenticatedUser().orElseThrow(() -> new NotFoundException("No user currently logged in"));

        applicationUser.setEmail(updateUserDetailDto.getEmail());
        applicationUser.setFirstName(updateUserDetailDto.getFirstName());
        applicationUser.setLastName(updateUserDetailDto.getLastName());

        if (updateUserDetailDto.getPassword() != null) {
            applicationUser.setPassword(passwordEncoder.encode(updateUserDetailDto.getPassword()));
        }

        UserLocation location = userLocationMapper.userLocationDtoToUserLocation(updateUserDetailDto.getLocation());
        if (location == null) {
            applicationUser.setLocation(null);
        } else {
            if (applicationUser.getLocation() != null) {
                location.setId(applicationUser.getLocation().getId());
            }
            applicationUser.setLocation(location);
        }

        return applicationUserRepository.save(applicationUser);
    }

    @Override
    public void deleteAuthenticatedUser() {
        var applicationUser = getCurrentlyAuthenticatedUser().orElseThrow(() -> new NotFoundException("No user currently logged in"));
        applicationUserRepository.delete(applicationUser);
    }

    @Override
    public void sendPasswordResetEmail(EmailResetDto emailResetDto) {
        var mailBody = "<h1>Reset your password</h1>\n"
            + "<p>Please click the following link to reset your password: "
            + "<a href=\"http://localhost:4200/reset-password?token=%s\">Reset Password</a></p>";

        try {
            var user = loadUserByUsername(emailResetDto.getEmail());

            var token = jwtTokenizer.getPasswordResetToken(user.getId());
            this.emailService.sendSimpleMail(
                emailResetDto.getEmail(),
                "Password Reset",
                mailBody.formatted(token)
            );
        } catch (UsernameNotFoundException e) {
            LOGGER.info("User with email {} not found", emailResetDto.getEmail());
        }
    }

    @Override
    public void resetPassword(ResetPasswordDto resetPasswordDto) throws JwtException {
        var userId = jwtVerifier.getUserIdFromPasswordResetToken(resetPasswordDto.getToken());
        var user = applicationUserRepository.findById(userId).orElseThrow(() -> new NotFoundException("No user with id %s found".formatted(userId)));

        user.setLocked(false);
        user.setFailedAuths(0);
        user.setPassword(passwordEncoder.encode(resetPasswordDto.getNewPassword()));

        applicationUserRepository.save(user);
    }
}
