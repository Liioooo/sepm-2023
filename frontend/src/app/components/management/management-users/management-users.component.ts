import { Component } from '@angular/core';
import { DEFAULT_PAGEABLE_STATE, PageableState } from '../../../types/pageable-request';
import { BehaviorSubject, combineLatest, debounceTime, distinctUntilChanged, map, Observable, switchMap } from 'rxjs';
import { UserSearchDto } from '../../../dtos/user-search-dto';
import { tap } from 'rxjs/operators';
import { UserService } from '../../../services/user.service';
import { EmailResetDto } from '../../../dtos/email-reset-dto';
import { ErrorResponseDto } from '../../../dtos/error-response-dto';
import { Router } from '@angular/router';
import { ToastService } from '../../../services/toast.service';
import { ErrorFormatterService } from '../../../services/error-formatter.service';
import {
  RequestPasswordChangeModalComponent
} from '../../modal/request-password-change-modal/request-password-change-modal.component';
import { MODAL_DISMISSED, ModalService } from '../../../services/modal.service';
import { UserListDto } from '../../../dtos/user-list-dto';
import { UserUpdateManagementDto } from '../../../dtos/user-update-management-dto';
import { UserRole } from '../../../enums/userRole';


@Component({
  selector: 'app-management-users',
  templateUrl: './management-users.component.html',
  styleUrls: ['./management-users.component.scss']
})
export class ManagementUsersComponent {
  public pageableState: PageableState = DEFAULT_PAGEABLE_STATE;
  public users$: Observable<UserListDto[]>;
  public searchAttributes$ = new BehaviorSubject<UserSearchDto>({});
  private onPageChange$ = new BehaviorSubject<number>(0);

  constructor(
    private service: UserService,
    private router: Router,
    private toastService: ToastService,
    private errorFormatterService: ErrorFormatterService,
    private modalService: ModalService
  ) {
    this.users$ = combineLatest([this.searchAttributes$, this.onPageChangeDistinct$]).pipe(
      debounceTime(250),
      switchMap(([searchAttributes, page]) => {
        return this.service.getUsers({
          ...searchAttributes
        }, { page, size: 20 });
      }),
      tap(page => {
        this.pageableState = {
          pageSize: 20,
          currentPage: page.currentPage + 1,
          totalPages: page.totalPages,
          totalElements: page.totalElements
        };
      }),
      map(page => page.content)
    );
  }

  private get onPageChangeDistinct$(): Observable<number> {
    return this.onPageChange$.pipe(
      distinctUntilChanged()
    );
  }

  onPageChange(newPage: number) {
    this.onPageChange$.next(newPage - 1);
  }

  lockUser(user: UserListDto): void {
    const userUpdateInfo: UserUpdateManagementDto = {
      id: user.id,
      isLocked: true
    };

    this.updateUser(userUpdateInfo, user, 'locked');
  }

  unlockUser(user: UserListDto): void {
    const userUpdateInfo: UserUpdateManagementDto = {
      id: user.id,
      isLocked: false
    };

    this.updateUser(userUpdateInfo, user, 'unlocked');
  }

  private updateUser(userUpdateInfo: UserUpdateManagementDto, user: UserListDto, actionStr: string) {
    this.service.updateUser(userUpdateInfo).subscribe({
      next: updatedUser => {
        this.toastService.showSuccess('Success', `User ${user.firstName}, ${user.lastName} ${actionStr}`);
        user.isLocked = updatedUser.isLocked; // instantly reflect change
      },
      error: err => {
        this.toastService
          .showError('Error', this.errorFormatterService.format(err['error'] as ErrorResponseDto));
      }
    });
  }

  clickSendEmail(name: string, email: string) {
    const emailResetDto: EmailResetDto = { email: email };
    this.sendEmail(emailResetDto, name);
  }

  async sendEmail(emailResetDto: EmailResetDto, name: string) {
    const sendRequest = await this.modalService.showModal(RequestPasswordChangeModalComponent, name);

    if (sendRequest === false || sendRequest === MODAL_DISMISSED) {
      return;
    }
    console.log('Try to send email for user: ' + emailResetDto.email);
    this.service.sendPasswordResetEmail(emailResetDto).subscribe({
      next: () => {
        console.log('Successfully sent email for user: ' + emailResetDto.email);
        this.toastService.showSuccess('Success', 'The Password Change Request has been sent to:  ' + emailResetDto.email);
        this.router.navigate(['/management/users']);
      },
      error: err => {
        this.toastService.showError('Error', this.errorFormatterService.format(err['error'] as ErrorResponseDto));
      }
    });
  }

  getStatusClass(user: UserListDto): string[] {
    let classes: string[] = ['badge'];

    if (!user.isLocked) {
      classes.push('text-bg-success');
    } else {
      classes.push('text-bg-danger');
    }

    return classes;
  }

  getStatus(user: UserListDto): string {
    return user.isLocked ? 'Disabled' : 'Enabled';
  }

  getRole(user: UserListDto): string {
    switch (user.role) {
      case UserRole.ROLE_ADMIN:
        return 'Admin';
      case UserRole.ROLE_USER:
        return 'User';
    }
  }
}
