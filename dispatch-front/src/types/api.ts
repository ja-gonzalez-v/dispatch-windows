export interface AvailableDate {
  date: string;
}

export interface TimeSlot {
  id: number;
  startTime: string;
  endTime: string;
  price: number;
  remainingCapacity: number;
}

export interface ReservationResponse {
  id: number;
  orderId: string;
  status: string;
}
