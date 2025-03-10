import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginPageComponent } from './pages/login-page/login-page.component';
import { RegisterPageComponent } from './pages/register-page/register-page.component';
import { publicGuard } from './guards/public.guard';

const routes: Routes = [
    {
        // localhost:4200/auth/
        path: '',
        component: LoginPageComponent,
        children: [
            {path: 'login', component: LoginPageComponent, canActivate: [publicGuard]},
            {path: 'register', component: RegisterPageComponent},
            {path: '**', redirectTo: 'login'},
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
})
export class AuthRoutingModule { }
