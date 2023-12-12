import { Component } from '@angular/core';
import { DEFAULT_PAGEABLE_STATE, PageableState } from '../../../types/pageable-request';
import { BehaviorSubject, combineLatest, debounceTime, distinctUntilChanged, map, Observable, switchMap } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { UserDetailDto } from '../../../dtos/user-detail-dto';
import { UserSearchDto } from '../../../dtos/user-search-dto';
import { tap } from 'rxjs/operators';
import { UserService } from '../../../services/user.service';

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
    private route: ActivatedRoute
  ) {
    this.users$ = combineLatest([this.searchAttributes$, this.searchTerm$, this.onPageChangeDistinct$]).pipe(
      debounceTime(250),
      switchMap(([searchAttributes, search, page]) => {
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

  get searchTerm$(): Observable<string> {
    return this.route.queryParams.pipe(
      map(params => params.search)
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
}
