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
import { NotFoundComponent } from './components/not-found/not-found.component';
import { ProfileSettingsComponent } from './components/profile-settings/profile-settings.component';
import { RegisterComponent } from './components/register/register.component';
import { EventsComponent } from './components/events/events.component';
import { EventDetailComponent } from './components/events/event-detail/event-detail.component';
import { LocationsComponent } from './components/locations/locations.component';
import { RequestPasswordResetComponent } from './components/request-password-reset/request-password-reset.component';
import { ResetPasswordComponent } from './components/reset-password/reset-password.component';
import { EventCheckoutComponent } from './components/events/event-checkout/event-checkout.component';
import { CheckoutMode } from './types/checkout-mode';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'request-password-reset', component: RequestPasswordResetComponent },
  { path: 'reset-password', component: ResetPasswordComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'events', component: EventsComponent },
  { path: 'events/:id', canActivate: [authGuard], component: EventDetailComponent },
  { path: 'events/:id/buy', canActivate: [authGuard], component: EventCheckoutComponent, data: { type: CheckoutMode.BUY } },
  { path: 'events/:id/reserve', canActivate: [authGuard], component: EventCheckoutComponent, data: { type: CheckoutMode.RESERVE } },
  { path: 'events/:id/buy-reservation/:reservationId', canActivate: [authGuard], component: EventCheckoutComponent, data: { type: CheckoutMode.BUY_RESERVATION } },
  { path: 'locations', component: LocationsComponent },
  { path: 'news', canActivate: [authGuard], component: NewsListComponent },
  { path: 'profile', canActivate: [authGuard], component: ProfileOverviewComponent },
  { path: 'profile/settings', canActivate: [authGuard], component: ProfileSettingsComponent },
  { path: 'management', canActivate: [adminGuard], component: ManagementComponent },
  { path: 'management/events', canActivate: [adminGuard], component: ManagementEventsComponent },
  { path: 'management/news', canActivate: [adminGuard], component: ManagementNewsComponent },
  { path: 'management/users', canActivate: [adminGuard], component: ManagementUsersComponent },
  { path: '**', component: NotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
