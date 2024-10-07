import ItemDtoModel from 'Frontend/generated/com/itbd/protisthan/db/dto/ItemDtoModel';

export interface CartItem {
  id: number;
  recordVersion: number;
  name: string;
  code: string;
  salePrice: number;
  uom: string;
  supplementaryDuty: number;
  serviceCharge: number;
  vat: number;
  discount: number;
  qty: number;
}

export interface CartDetails {
  totalItem: number;
  totalQty: number;
  totalAmount: number;
  totalSd: number;
  totalSc: number;
  totalDiscount: number;
  cost: number;
  totalVat: number;
  totalNetAmount: number;
}

export interface InvoiceDetails {
  customerId?: number;
  employeeId?: number;
  paymentMode?: number;
  invDiscount: number;
  invService: number;
  totalCost: number;
  paidAmount: number;
  dueAmount: number;
  saleDate: string;
}

export interface ItemSelect {
  item: ItemDtoModel;
  qty: number;
}

export function calculatePercentage(part: number, whole: number): number {
  if (part * whole === 0) {
    return 0; // Avoid division by zero
  }
  return (part * whole) / 100;
}
