import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { authGuard } from './guards/auth.guard';
import { adminGuard } from './guards/admin.guard';
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
import { TicketSelectMode } from './types/ticket-select-mode';
import { NewsDetailComponent } from './components/news/news-detail/news-detail.component';
import { NewsComponent } from './components/news/news.component';
import { NewsCreateComponent } from './components/news/news-create/news-create.component';
import { ProfileOrderDetailComponent } from './components/profile-order-detail/profile-order-detail.component';
import {
  EventOverviewComponent
} from './components/management/management-events/event-overview/event-overview.component';
import { EventCreateComponent } from './components/management/management-events/event-create/event-create.component';
import {
  ManagementLocationsComponent
} from './components/management/management-locations/management-locations.component';
import {
  ManagementLocationCreateComponent
} from './components/management/management-locations/management-location-create/management-location-create.component';
import {
  ManagementLocationDetailsComponent
} from './components/management/management-locations/management-location-details/management-location-details.component';
import {
  ManagementLocationHallCreateComponent
} from './components/management/management-locations/halls/management-location-hall-create/management-location-hall-create.component';
import {
  ManagementUserCreateComponent
} from './components/management/management-users/management-user-create/management-user-create.component';
import { TicketValidationComponent } from './components/ticket-validation/ticket-validation.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'request-password-reset', component: RequestPasswordResetComponent },
  { path: 'reset-password', component: ResetPasswordComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'events', component: EventsComponent },
  {
    path: 'events/:id',
    canActivate: [authGuard],
    component: EventDetailComponent,
    data: { type: TicketSelectMode.SELECT_NEW }
  },
  {
    path: 'events/:id/buy',
    canActivate: [authGuard],
    component: EventCheckoutComponent,
    data: { type: CheckoutMode.BUY }
  },
  {
    path: 'events/:id/reserve',
    canActivate: [authGuard],
    component: EventCheckoutComponent,
    data: { type: CheckoutMode.RESERVE }
  },
  {
    path: 'events/:id/buy-reservation/:orderId',
    canActivate: [authGuard],
    component: EventDetailComponent,
    data: { type: TicketSelectMode.SELECT_RESERVED }
  },
  {
    path: 'events/:id/buy-reservation/:orderId/confirm',
    canActivate: [authGuard],
    component: EventCheckoutComponent,
    data: { type: CheckoutMode.BUY_RESERVATION }
  },
  {
    path: 'orders/:orderId/edit-tickets',
    canActivate: [authGuard],
    component: EventDetailComponent,
    data: { type: TicketSelectMode.UPDATE }
  },
  {
    path: 'orders/:orderId/edit-tickets/confirm',
    canActivate: [authGuard],
    component: EventCheckoutComponent,
    data: { type: CheckoutMode.UPDATE }
  },
  { path: 'locations', component: LocationsComponent },
  { path: 'news', canActivate: [authGuard], component: NewsComponent },
  { path: 'news/detail/:id', canActivate: [authGuard], component: NewsDetailComponent },
  { path: 'profile', canActivate: [authGuard], component: ProfileOverviewComponent },
  { path: 'profile/order-details/:orderId', canActivate: [authGuard], component: ProfileOrderDetailComponent },
  { path: 'profile/settings', canActivate: [authGuard], component: ProfileSettingsComponent },
  { path: 'management', canActivate: [adminGuard], component: ManagementComponent },
  { path: 'management/events', canActivate: [adminGuard], component: ManagementEventsComponent },
  { path: 'management/news', canActivate: [adminGuard], component: ManagementNewsComponent },
  { path: 'management/news/create', canActivate: [authGuard], component: NewsCreateComponent },
  { path: 'management/users', canActivate: [adminGuard], component: ManagementUsersComponent },
  { path: 'management/users/create', canActivate: [adminGuard], component: ManagementUserCreateComponent },
  { path: 'management/events/view/:id', canActivate: [adminGuard], component: EventOverviewComponent },
  { path: 'management/events/create', canActivate: [adminGuard], component: EventCreateComponent },
  { path: 'management/locations', canActivate: [adminGuard], component: ManagementLocationsComponent },
  { path: 'management/locations/create', canActivate: [adminGuard], component: ManagementLocationCreateComponent },
  {
    path: 'management/locations/:id/details',
    canActivate: [adminGuard],
    component: ManagementLocationDetailsComponent
  },
  {
    path: 'management/locations/:id/halls/create',
    canActivate: [adminGuard],
    component: ManagementLocationHallCreateComponent
  },
  { path: 'tickets/verify/:uuid', canActivate: [adminGuard], component: TicketValidationComponent },
  { path: '**', component: NotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
