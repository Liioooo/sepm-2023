package at.ac.tuwien.sepr.groupphase.backend.service;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UpdateUserDetailDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserLoginDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserRegisterDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public interface UserService extends UserDetailsService {

    /**
     * Find a user in the context of Spring Security based on the email address
     * <br>
     * For more information have a look at this tutorial:
     * https://www.baeldung.com/spring-security-authentication-with-a-database
     *
     * @param email the email address
     * @return a Spring Security user
     * @throws UsernameNotFoundException is thrown if the specified user does not exists
     */
    @Override
    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;

    /**
     * Log in a user.
     *
     * @param userLoginDto login credentials
     * @return the JWT, if successful
     * @throws org.springframework.security.authentication.BadCredentialsException if credentials are bad
     * @throws at.ac.tuwien.sepr.groupphase.backend.exception.NotFoundException    if the user does not exist
     */
    String login(UserLoginDto userLoginDto);

    /**
     * Register a user.
     *
     * @param userLoginDto login credentials
     * @return the JWT, if successful
     */
    String register(UserRegisterDto userLoginDto);

    /**
     * Unlock a user account.
     *
     * @param userId id of the user to unlock
     */
    void unlockUser(long userId);

    /**
     * Check if a user is authenticated.
     *
     * @return if the user executing the current request is authenticated
     */
    boolean getIsAuthenticated();

    /**
     * Get the currently authenticated user.
     *
     * @return the currently authenticated user
     */
    Optional<ApplicationUser> getCurrentlyAuthenticatedUser();

    /**
     * Update the currently authenticated user.
     *
     * @param updateUserDetailDto the new use data
     * @return the updated user details
     */
    ApplicationUser updateAuthenticatedUser(UpdateUserDetailDto updateUserDetailDto);

    void deleteAuthenticatedUser();
}
