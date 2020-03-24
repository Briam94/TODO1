import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ProductoComponent } from './components/producto/producto.component';
import { VentaComponent } from './components/venta/venta.component';
import { ReportesComponent } from './components/reportes/reportes.component';


const routes: Routes = [
  {
    path: 'producto', component: ProductoComponent
  },
  {
    path: 'venta', component: VentaComponent
  },
  {
    path: 'reporte', component: ReportesComponent
  },
  { path: '**', pathMatch: 'full', redirectTo: '/producto' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
