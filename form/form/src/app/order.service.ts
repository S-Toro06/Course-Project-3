import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';

export interface OrderRequest {
    customerName: string;
    cabs: string;
    miles: number;
}

export interface OrderResponse {
    orderId: number;
    customerName: string;
    cabs: string;
    miles: number;
    totalPrice: number;
    status: string;
}

@Injectable({
    providedIn: 'root'
})
export class OrderService {
    private apiUrl = 'http://localhost:8080/api/orders';

    constructor(private http: HttpClient) {  }

    async createOrder(orderRequest: OrderRequest): Promise<OrderResponse> {
        return firstValueFrom(this.http.post<OrderResponse>(this.apiUrl, orderRequest));
    }
}