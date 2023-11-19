import { NgModule } from '@angular/core';
import { mapToCanActivate, RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { AuthGuard } from './guards/auth.guard';
import { adminGuard } from './guards/admin.guard';
import { NewsListComponent } from './components/news-list/news-list.component';
import { ProfileOverviewComponent } from './components/profile-overview/profile-overview.component';
import { ManagementComponent } from './components/management/management.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'news', canActivate: mapToCanActivate([AuthGuard]), component: NewsListComponent },
  { path: 'profile', canActivate: mapToCanActivate([AuthGuard]), component: ProfileOverviewComponent },
  { path: 'management', canActivate: [adminGuard], component: ManagementComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
