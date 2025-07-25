import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';

export interface OrderRequest {
    customerName: string;
    Cabs: string;
    Miles: number;
}

export interface OrderResponse {
    orderId: number;
    customerName: string;
    Cabs: string;
    Miles: number;
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