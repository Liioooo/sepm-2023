import { Component } from '@angular/core';
import { DEFAULT_PAGEABLE_STATE, PageableState } from '../../../types/pageable-request';
import { BehaviorSubject, combineLatest, debounceTime, distinctUntilChanged, map, Observable, switchMap } from 'rxjs';
import { UserDetailDto } from '../../../dtos/user-detail-dto';
import { UserSearchDto } from '../../../dtos/user-search-dto';
import { tap } from 'rxjs/operators';
import { UserService } from '../../../services/user.service';
import { EmailResetDto } from '../../../dtos/email-reset-dto';
import { ErrorResponseDto } from '../../../dtos/error-response-dto';
import { Router } from '@angular/router';
import { ToastService } from '../../../services/toast.service';
import { ErrorFormatterService } from '../../../services/error-formatter.service';
import { FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-management-users',
  templateUrl: './management-users.component.html',
  styleUrls: ['./management-users.component.scss']
})
export class ManagementUsersComponent {
  public pageableState: PageableState = DEFAULT_PAGEABLE_STATE;
  public users$: Observable<UserDetailDto[]>;
  public searchAttributes$ = new BehaviorSubject<UserSearchDto>({});
  private onPageChange$ = new BehaviorSubject<number>(0);

  constructor(
    private service: UserService,
    private formBuilder: FormBuilder,
    private router: Router,
    private toastService: ToastService,
    private errorFormatterService: ErrorFormatterService
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

  clickMethod(name: string, email: string  ) {
    if(confirm('Are you sure to request a password change for '+ name + '?')) {
      const emailResetDto: EmailResetDto = { email: email };
      this.sendEmail(emailResetDto);
    }
  }

  sendEmail(emailResetDto: EmailResetDto) {
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
}
