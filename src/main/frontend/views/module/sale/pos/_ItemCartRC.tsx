export interface ItemCart {
  id: number;
  name: string;
  code: string;
  salePrice: number;
  uom: string;
  supplementaryDuty: number;
  serviceCharge: number;
  vat: number;
  discount: number;
  quantity: number;
}

export function calculatePercentage(part: number, whole: number): number {
  if (part * whole === 0) {
    return 0; // Avoid division by zero
  }
  return (part * whole) / 100;
}
