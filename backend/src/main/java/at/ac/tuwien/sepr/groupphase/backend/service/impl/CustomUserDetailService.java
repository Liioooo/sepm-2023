package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EmailResetDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.ResetPasswordDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserLocationDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserLoginDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserRegisterDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserSearchDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserUpdateDetailDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserUpdateManagementDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.UserLocationMapper;
import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.entity.UserLocation;
import at.ac.tuwien.sepr.groupphase.backend.enums.UserRole;
import at.ac.tuwien.sepr.groupphase.backend.exception.ConflictException;
import at.ac.tuwien.sepr.groupphase.backend.exception.ForbiddenException;
import at.ac.tuwien.sepr.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepr.groupphase.backend.exception.UnauthorizedException;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;
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

    private static final int MAX_ALLOWED_FAILED_AUTHS = 5;

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
        ApplicationUser applicationUser;
        try {
            applicationUser = loadUserByUsername(userLoginDto.getEmail());
        } catch (UsernameNotFoundException e) {
            throw new NotFoundException(e.getMessage(), e);
        }

        if (!passwordEncoder.matches(userLoginDto.getPassword(), applicationUser.getPassword())) {
            applicationUser.setFailedAuths(applicationUser.getFailedAuths() + 1);

            if (applicationUser.getFailedAuths() >= MAX_ALLOWED_FAILED_AUTHS) {
                applicationUser.setLocked(true);
            }
            applicationUserRepository.save(applicationUser);
            if (applicationUser.getFailedAuths() < 5) {
                throw new BadCredentialsException("Username or password is incorrect");
            } else if (applicationUser.getFailedAuths() >= 5) {
                throw new BadCredentialsException("Incorrectly entered password too many times. Account is now locked.");
            }
        }

        if (!applicationUser.isAccountNonLocked() || !applicationUser.isCredentialsNonExpired() || !applicationUser.isAccountNonExpired()) {
            throw new BadCredentialsException("Account is locked");
        }

        applicationUser.setFailedAuths(0);
        applicationUserRepository.save(applicationUser);

        return generateTokenForUser(applicationUser);
    }

    @Override
    public String register(UserRegisterDto userRegisterDto) {
        ApplicationUser user = createUser(
            userRegisterDto.getFirstName(),
            userRegisterDto.getLastName(),
            userRegisterDto.getEmail(),
            userRegisterDto.getPassword(),
            userRegisterDto.getLocation(),
            UserRole.ROLE_USER,
            false
        );

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
    public ApplicationUser updateAuthenticatedUser(UserUpdateDetailDto userUpdateDetailDto) {
        var applicationUser = getCurrentlyAuthenticatedUser().orElseThrow(() -> new NotFoundException("No user currently logged in"));

        applicationUser.setEmail(userUpdateDetailDto.getEmail());
        applicationUser.setFirstName(userUpdateDetailDto.getFirstName());
        applicationUser.setLastName(userUpdateDetailDto.getLastName());

        if (userUpdateDetailDto.getPassword() != null) {
            applicationUser.setPassword(passwordEncoder.encode(userUpdateDetailDto.getPassword()));
        }

        UserLocation location = userLocationMapper.userLocationDtoToUserLocation(userUpdateDetailDto.getLocation());
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
    public ApplicationUser updateUser(UserUpdateManagementDto userUpdateManagementDto, ApplicationUser authenticatedUser) {
        if (Objects.equals(userUpdateManagementDto.getId(), authenticatedUser.getId())) {
            throw new ForbiddenException("Admin user cannot lock/unlock own account");
        }

        ApplicationUser toUpdate = applicationUserRepository
            .findById(userUpdateManagementDto.getId()).orElseThrow(() -> new NotFoundException("No user with id %d found".formatted(userUpdateManagementDto.getId())));

        if (userUpdateManagementDto.getIsLocked() != null) {
            toUpdate.setLocked(userUpdateManagementDto.getIsLocked());
            if (!userUpdateManagementDto.getIsLocked()) { // in case of unlock
                toUpdate.setFailedAuths(0);
            }
        }

        return applicationUserRepository.save(toUpdate);
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
            + "<a href=\"http://localhost:4200/#/reset-password?token=%s\">Reset Password</a></p>";

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

    @Override
    public ApplicationUser getUserFromAuthentication(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("Not authenticated");
        }
        try {
            return loadUserByUsername(authentication.getName());
        } catch (UsernameNotFoundException e) {
            throw new NotFoundException("User not found");
        }
    }

    @Override
    public Page<ApplicationUser> getUsersBySearch(UserSearchDto search, Pageable pageable) {
        return this.applicationUserRepository.findUserBySearchCriteria(search, pageable);
    }

    @Override
    public ApplicationUser createUserAsAdmin(UserCreateDto userCreateDto) {
        return createUser(
            userCreateDto.getFirstName(),
            userCreateDto.getLastName(),
            userCreateDto.getEmail(),
            userCreateDto.getPassword(),
            userCreateDto.getLocation(),
            userCreateDto.getRole(),
            userCreateDto.getIsLocked()
        );
    }

    private ApplicationUser createUser(String firstName, String lastName, String email, String password, UserLocationDto location, UserRole role, Boolean isLocked) {
        var user = ApplicationUser.builder()
            .firstName(firstName)
            .lastName(lastName)
            .email(email)
            .password(passwordEncoder.encode(password))
            .location(userLocationMapper.userLocationDtoToUserLocation(location))
            .role(role)
            .isLocked(isLocked)
            .build();

        try {
            return applicationUserRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Email is already in use", ex);
        }
    }

}
