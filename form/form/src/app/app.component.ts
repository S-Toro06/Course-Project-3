import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { OrderService, OrderRequest, OrderResponse } from './order.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'Cab Order System';

  orderRequest: OrderRequest = {
    customerName: '',
    cabs: '',
    miles: 1
  };

  orderResponse: OrderResponse | null = null;
  errorMessage: string = '';
  loading: boolean = false;

  constructor(private orderService: OrderService) {}

  async onSubmit() {
    if (this.isFormValid()) {
      this.loading = true;
      this.errorMessage = '';
      this.orderResponse = null;

      try {
        this.orderResponse = await this.orderService.createOrder(this.orderRequest);
        this.loading = false;
      } catch (error: any) {
        this.loading = false;
        if (error.error && error.error.message) {
          this.errorMessage = error.error.message;
        } else if (error.error && error.error.fieldErrors) {
          this.errorMessage = 'Please check the following fields: ' +
          Object.entries(error.error.fieldErrors)
          .map(([field, message]) => `${field}: ${message}`)
          .join(', ')
        } else {
          this.errorMessage = 'Failed to create order. Please try again.';
        }
      }
    }
  }

  isFormValid(): boolean {
    return this.orderRequest.customerName.trim() !== '' &&
    this.orderRequest.cabs.trim() !== '' &&
    this.orderRequest.miles > 0;
  }

  clearForm() {
    this.orderRequest = {
      customerName: '',
      cabs: '',
      miles: 1
    };
    this.orderResponse = null;
    this.errorMessage = '';
  }

}
