import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { authGuard } from './guards/auth.guard';
import { adminGuard } from './guards/admin.guard';
import { NewsListComponent } from './components/news-list/news-list.component';
import { ProfileOverviewComponent } from './components/profile-overview/profile-overview.component';
import { ManagementComponent } from './components/management/management.component';
import { ManagementEventsComponent } from './components/management/management-events/management-events.component';
import { ManagementNewsComponent } from './components/management/management-news/management-news.component';
import { ManagementUsersComponent } from './components/management/management-users/management-users.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'news', canActivate: [authGuard], component: NewsListComponent },
  { path: 'profile', canActivate: [authGuard], component: ProfileOverviewComponent },
  { path: 'management', canActivate: [adminGuard], component: ManagementComponent },
  { path: 'management/events', canActivate: [adminGuard], component: ManagementEventsComponent },
  { path: 'management/news', canActivate: [adminGuard], component: ManagementNewsComponent },
  { path: 'management/users', canActivate: [adminGuard], component: ManagementUsersComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
