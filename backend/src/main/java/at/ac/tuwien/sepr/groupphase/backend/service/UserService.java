package at.ac.tuwien.sepr.groupphase.backend.service;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EmailResetDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.ResetPasswordDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserLoginDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserRegisterDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserSearchDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserUpdateDetailDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepr.groupphase.backend.exception.UnauthorizedException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;

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
     * @throws NotFoundException                                                   if the user does not exist
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
     * @param userUpdateDetailDto the new use data
     * @return the updated user details
     */
    ApplicationUser updateAuthenticatedUser(UserUpdateDetailDto userUpdateDetailDto);

    /**
     * Delete the currently authenticated user.
     */
    void deleteAuthenticatedUser();

    /**
     * Sends a password reset email to the user, if the user exists.
     *
     * @param emailResetDto a DTO with the email address of the user
     */
    void sendPasswordResetEmail(EmailResetDto emailResetDto);

    /**
     * Sets the password of the user to the new provided password, if the token is valid.
     *
     * @param resetPasswordDto a DTO with the reset token and the new password
     */
    void resetPassword(ResetPasswordDto resetPasswordDto);

    /**
     * Get the ApplicationUser of the given Authentication.
     *
     * @throws NotFoundException     if no User with the authentication name exists
     * @throws UnauthorizedException if the Authentication is not valid
     */
    ApplicationUser getUserFromAuthentication(Authentication authentication);


    /**
     * Finds users by search criteria.
     *
     * @param search the search criteria
     * @return the collection of users
     */
    Page<ApplicationUser> getUsersBySearch(UserSearchDto search, Pageable pageable);
}
