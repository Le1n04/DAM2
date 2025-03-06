import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LayoutPageComponent } from './pages/layout-page/layout-page.component';
import { NewPageComponent } from '../heroes/pages/new-page/new-page.component';
import { SearchPageComponent } from '../heroes/pages/search-page/search-page.component';
import { ListPageComponent } from '../heroes/pages/list-page/list-page.component';
import { HeroPageComponent } from '../heroes/pages/hero-page/hero-page.component';

const routes: Routes = [
    {
        // localhost:4200/auth/
        path: '',
        component: LayoutPageComponent,
        children: [
            {path:'new-hero', component: NewPageComponent},
            {path:'search', component: SearchPageComponent},
            {path:'edit', component: NewPageComponent},
            {path:'list', component:ListPageComponent },
            {path:'id', component: HeroPageComponent},
            {path: '**', redirectTo: 'list'},
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
})
export class AuthRoutingModule { }
