import { Signal } from '@vaadin/hilla-react-signals';
import CostNodeRC from './_CostNode';
import { calculatePercentage, ItemCart } from './_ItemCartRC';

const SellStateRC = ({ addToCart }: { addToCart: Signal<ItemCart[]> }) => {
  const totalItem = addToCart.value.length;
  const totalQty = addToCart.value.reduce((sum, item) => sum + item.quantity, 0);
  const totalAmount = addToCart.value.reduce((sum, item) => sum + item.salePrice * item.quantity, 0);
  const totalSd = addToCart.value.reduce(
    (sum, item) => sum + calculatePercentage(item.supplementaryDuty, totalAmount),
    0
  );
  const totalSc = addToCart.value.reduce((sum, item) => sum + calculatePercentage(item.serviceCharge, totalAmount), 0);
  const totalDiscount = addToCart.value.reduce((sum, item) => sum + calculatePercentage(item.discount, totalAmount), 0);
  const cost = totalSd + totalSc - totalDiscount;
  const totalVat = addToCart.value.reduce((sum, item) => sum + calculatePercentage(item.vat, totalAmount + cost), 0);
  const totalNetAmount = totalAmount + cost + totalVat;
  return (
    <>
      <CostNodeRC className="bg-green-600" lable="Total Item" value={totalItem.toString()} />
      <CostNodeRC className="bg-green-600" lable="Total Qty" value={totalQty.toString()} />
      <CostNodeRC className="bg-green-600" lable="Amount" value={totalAmount.toFixed(2)} />
      <CostNodeRC className="bg-green-600" lable="SD." value={totalSd.toFixed(2)} hints="Supplementary Duty" />
      <CostNodeRC className="bg-green-600" lable="SC." value={totalSc.toFixed(2)} hints="Service Charge" />
      <CostNodeRC className="bg-green-600" lable="Discount" value={totalDiscount.toFixed(2)} />
      <CostNodeRC className="bg-green-600" lable="VAT" value={totalVat.toFixed(2)} />
      <CostNodeRC className="bg-green-600" lable="Net Amount" value={totalNetAmount.toFixed(2)} />
      <CostNodeRC className="bg-green-600" lable="Exchange" value="0.0" />
    </>
  );
};

export default SellStateRC;
