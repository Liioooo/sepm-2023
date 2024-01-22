import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { NgbDateAdapter, NgbDateNativeAdapter, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { httpInterceptorProviders } from './interceptors';
import { NgOptimizedImage } from '@angular/common';
import { NewsComponent } from './components/news/news.component';
import { NewsDetailComponent } from './components/news/news-detail/news-detail.component';
import { NewsListItemComponent } from './components/news/news-list-item/news-list-item.component';
import { ProfileOverviewComponent } from './components/profile-overview/profile-overview.component';
import { ManagementComponent } from './components/management/management.component';
import { ManagementEventsComponent } from './components/management/management-events/management-events.component';
import { ManagementNewsComponent } from './components/management/management-news/management-news.component';
import { ManagementUsersComponent } from './components/management/management-users/management-users.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { ProfileSettingsComponent } from './components/profile-settings/profile-settings.component';
import { ToastsComponent } from './components/toasts/toasts.component';
import { RegisterComponent } from './components/register/register.component';
import { EventsComponent } from './components/events/events.component';
import { EventItemComponent } from './components/events/event-item/event-item.component';
import { EventSearchFieldsComponent } from './components/events/event-search-fields/event-search-fields.component';
import {
  ConfirmDeleteProfileModalComponent
} from './components/modal/confirm-delete-profile-modal/confirm-delete-profile-modal.component';
import { LocationsComponent } from './components/locations/locations.component';
import { LocationItemComponent } from './components/locations/location-item/location-item.component';
import {
  LocationSearchFieldsComponent
} from './components/locations/location-search-fields/location-search-fields.component';
import { EventDetailComponent } from './components/events/event-detail/event-detail.component';
import { RequestPasswordResetComponent } from './components/request-password-reset/request-password-reset.component';
import { ResetPasswordComponent } from './components/reset-password/reset-password.component';
import {
  NewsSearchFieldsComponent
} from './components/management/management-news/news-search-fields/news-search-fields.component';
import {
  UsersSearchFieldsComponent
} from './components/management/management-users/users-search-fields/users-search-fields.component';
import {
  EventOverviewComponent
} from './components/management/management-events/event-overview/event-overview.component';
import { PiechartComponent } from './components/home/piechart/piechart.component';
import { EventCheckoutComponent } from './components/events/event-checkout/event-checkout.component';
import { OrderItemComponent } from './components/profile-overview/order-item/order-item.component';
import { EventCreateComponent } from './components/management/management-events/event-create/event-create.component';
import { NewsCreateComponent } from './components/news/news-create/news-create.component';
import { ProfileOrderDetailComponent } from './components/profile-order-detail/profile-order-detail.component';
import { RequestPasswordChangeModalComponent } from './components/modal/request-password-change-modal/request-password-change-modal.component';
import {
  ManagementLocationsComponent
} from './components/management/management-locations/management-locations.component';
import {
  ManagementLocationCreateComponent
} from './components/management/management-locations/management-location-create/management-location-create.component';
import { ManagementLocationDetailsComponent } from './components/management/management-locations/management-location-details/management-location-details.component';
import { ManagementLocationHallCreateComponent } from './components/management/management-locations/halls/management-location-hall-create/management-location-hall-create.component';
import { ManagementUserCreateComponent } from './components/management/management-users/management-user-create/management-user-create.component';
import { TicketValidationComponent } from './components/ticket-validation/ticket-validation.component';
import { ContactAdminComponent } from './components/contact-admin/contact-admin.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    LoginComponent,
    RequestPasswordResetComponent,
    ResetPasswordComponent,
    NewsComponent,
    NewsDetailComponent,
    NewsListItemComponent,
    ProfileOverviewComponent,
    EventsComponent,
    EventItemComponent,
    EventSearchFieldsComponent,
    ManagementComponent,
    ManagementEventsComponent,
    ManagementNewsComponent,
    ManagementUsersComponent,
    NotFoundComponent,
    ProfileSettingsComponent,
    ToastsComponent,
    RegisterComponent,
    ConfirmDeleteProfileModalComponent,
    LocationsComponent,
    LocationItemComponent,
    LocationSearchFieldsComponent,
    EventDetailComponent,
    PiechartComponent,
    EventDetailComponent,
    EventCheckoutComponent,
    OrderItemComponent,
    NewsCreateComponent,
    ProfileOrderDetailComponent,
    NewsCreateComponent,
    NewsSearchFieldsComponent,
    UsersSearchFieldsComponent,
    EventOverviewComponent,
    EventCreateComponent,
    ManagementLocationsComponent,
    ManagementLocationCreateComponent,
    ManagementLocationDetailsComponent,
    ManagementLocationHallCreateComponent,
    RequestPasswordChangeModalComponent,
    ManagementUserCreateComponent,
    ContactAdminComponent,
    ManagementUserCreateComponent,
    TicketValidationComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgbModule,
    FormsModule,
    NgOptimizedImage
  ],
  providers: [
    httpInterceptorProviders,
    { provide: NgbDateAdapter, useClass: NgbDateNativeAdapter }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
