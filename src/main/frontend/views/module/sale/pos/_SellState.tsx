import { Signal } from '@vaadin/hilla-react-signals';
import CostNodeRC from './_CostNode';
import { CartDetails } from './_ItemCartRC';

const SellStateRC = ({ cartDetails }: { cartDetails: Signal<CartDetails> }) => {
  const { totalItem, totalQty, totalAmount, totalSd, totalSc, totalDiscount, totalVat, totalNetAmount } =
    cartDetails.value;
  return (
    <>
      <CostNodeRC className="bg-green-600" lable="Total Item" value={totalItem?.toString()} />
      <CostNodeRC className="bg-green-600" lable="Total Qty" value={totalQty?.toString()} />
      <CostNodeRC className="bg-green-600" lable="Amount" value={totalAmount?.toFixed(2)} />
      <CostNodeRC className="bg-green-600" lable="SD." value={totalSd?.toFixed(2)} hints="Supplementary Duty" />
      <CostNodeRC className="bg-green-600" lable="SC." value={totalSc?.toFixed(2)} hints="Service Charge" />
      <CostNodeRC className="bg-green-600" lable="Discount" value={totalDiscount?.toFixed(2)} />
      <CostNodeRC className="bg-green-600" lable="VAT" value={totalVat?.toFixed(2)} />
      <CostNodeRC className="bg-green-600" lable="Net Amount" value={totalNetAmount?.toFixed(2)} />
      <CostNodeRC className="bg-green-600" lable="Exchange" value="0.0" />
    </>
  );
};

export default SellStateRC;
