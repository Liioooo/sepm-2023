import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs/operators';
import { jwtDecode } from 'jwt-decode';
import { Globals } from '../global/globals';
import { UserLoginDto } from '../dtos/user-login-dto';
import { UserDetailDto } from '../dtos/user-detail-dto';
import { UpdateUserDetailDto } from '../dtos/update-user-detail-dto';
import { EmailResetDto } from '../dtos/email-reset-dto';
import { ResetPasswordDto } from '../dtos/reset-password-dto';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private authBaseUri: string = this.globals.backendUri + '/authentication';

  private myUserBaseUri: string = this.globals.backendUri + '/my-user';

  private userDataSubject$: BehaviorSubject<UserDetailDto> = new BehaviorSubject<UserDetailDto>(null);

  constructor(private httpClient: HttpClient, private globals: Globals) {
    this.initUserData();
  }


  /**
   * Login in the user. If it was successful, a valid JWT token will be stored
   *
   * @param loginDto User data
   */
  loginUser(loginDto: UserLoginDto): Observable<string> {
    return this.httpClient.post(`${this.authBaseUri}/login`, loginDto, { responseType: 'text' })
      .pipe(
        tap((authResponse: string) => this.setToken(authResponse)),
        tap(() => this.fetchUserDetails())
      );
  }

  registerUser(user: UpdateUserDetailDto): Observable<string> {
    return this.httpClient.post(`${this.authBaseUri}/register`, user, { responseType: 'text' })
      .pipe(
        tap((authResponse: string) => this.setToken(authResponse)),
        tap(() => this.fetchUserDetails())
      );
  }

  sendPasswordResetEmail(emailResetDto: EmailResetDto): Observable<void> {
    return this.httpClient.post<void>(`${this.authBaseUri}/send-password-reset-email`, emailResetDto, { responseType: 'json' });
  }

  resetPassword(resetPasswordDto: ResetPasswordDto): Observable<void> {
    return this.httpClient.post<void>(`${this.authBaseUri}/reset-password`, resetPasswordDto, { responseType: 'json' });
  }

  /**
   * Check if a valid JWT token is saved in the localStorage
   */
  isLoggedIn() {
    return !!this.token && (this.getTokenExpirationDate(this.token).valueOf() > new Date().valueOf());
  }

  logoutUser() {
    localStorage.removeItem('authToken');
    this.userDataSubject$.next(null);
  }

  get token() {
    return localStorage.getItem('authToken');
  }

  get userDetails$(): Observable<UserDetailDto> {
    return this.userDataSubject$.asObservable();
  }

  /**
   * Returns the user role based on the current token
   */
  getUserRole() {
    if (this.token != null) {
      const decoded: any = jwtDecode(this.token);
      const authInfo: string[] = decoded.rol;
      if (authInfo.includes('ROLE_ADMIN')) {
        return 'ADMIN';
      } else if (authInfo.includes('ROLE_USER')) {
        return 'USER';
      }
    }
    return 'UNDEFINED';
  }

  private setToken(authResponse: string) {
    localStorage.setItem('authToken', authResponse);
  }

  private getTokenExpirationDate(token: string): Date {

    const decoded: any = jwtDecode(token);
    if (decoded.exp === undefined) {
      return null;
    }

    const date = new Date(0);
    date.setUTCSeconds(decoded.exp);
    return date;
  }

  private initUserData() {
    if (this.isLoggedIn()) {
      setTimeout(() => this.fetchUserDetails(), 0);
    }
  }

  private fetchUserDetails() {
    this.httpClient.get<UserDetailDto>(this.myUserBaseUri, { responseType: 'json' }).subscribe(data => {
      this.userDataSubject$.next(data);
    });
  }

  updateMyUser(user: UpdateUserDetailDto): Observable<UserDetailDto> {
    return this.httpClient.put<UserDetailDto>(this.myUserBaseUri, user, { responseType: 'json' })
      .pipe(tap(_user => this.userDataSubject$.next(_user)));
  }

  deleteMyUser(): Observable<void> {
    return this.httpClient.delete<void>(this.myUserBaseUri, { responseType: 'json' })
      .pipe(
        tap(() => this.logoutUser())
      );
  }

}
