import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HeaderComponent} from './components/header/header.component';
import {FooterComponent} from './components/footer/footer.component';
import {HomeComponent} from './components/home/home.component';
import {LoginComponent} from './components/login/login.component';
import {NgbDateAdapter, NgbDateNativeAdapter, NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {httpInterceptorProviders} from './interceptors';
import {NgOptimizedImage} from '@angular/common';
import {ProfileOverviewComponent} from './components/profile-overview/profile-overview.component';
import {ManagementComponent} from './components/management/management.component';
import {ManagementEventsComponent} from './components/management/management-events/management-events.component';
import {ManagementNewsComponent} from './components/management/management-news/management-news.component';
import {ManagementUsersComponent} from './components/management/management-users/management-users.component';
import {NotFoundComponent} from './components/not-found/not-found.component';
import {ProfileSettingsComponent} from './components/profile-settings/profile-settings.component';
import {ToastsComponent} from './components/toasts/toasts.component';
import {RegisterComponent} from './components/register/register.component';
import {EventsComponent} from './components/events/events.component';
import {EventItemComponent} from './components/events/event-item/event-item.component';
import {EventSearchFieldsComponent} from './components/events/event-search-fields/event-search-fields.component';
import {
  ConfirmDeleteProfileModalComponent
} from './components/modal/confirm-delete-profile-modal/confirm-delete-profile-modal.component';
import {LocationsComponent} from './components/locations/locations.component';
import {LocationItemComponent} from './components/locations/location-item/location-item.component';
import {
  LocationSearchFieldsComponent
} from './components/locations/location-search-fields/location-search-fields.component';
import {EventDetailComponent} from './components/events/event-detail/event-detail.component';
import {RequestPasswordResetComponent} from './components/request-password-reset/request-password-reset.component';
import {ResetPasswordComponent} from './components/reset-password/reset-password.component';
import {NewsComponent} from './components/news/news.component';
import {NewsDetailComponent} from './components/news/news-detail/news-detail.component';
import {NewsListItemComponent} from './components/news/news-list-item/news-list-item.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    LoginComponent,
    RequestPasswordResetComponent,
    ResetPasswordComponent,
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
    NewsComponent,
    NewsDetailComponent,
    NewsListItemComponent
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
    {provide: NgbDateAdapter, useClass: NgbDateNativeAdapter}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
