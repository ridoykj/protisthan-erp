import { Signal } from '@vaadin/hilla-react-signals';
import { Grid, GridColumn, Tooltip } from '@vaadin/react-components';
import ItemDtoModel from 'Frontend/generated/com/itbd/protisthan/db/dto/ItemDtoModel';
import { useEffect } from 'react';
import { FaTrash } from 'react-icons/fa6';
import { calculatePercentage, CartDetails, CartItem, ItemSelect } from './_ItemCartRC';

const ItemGridRC = ({
  cartItems,
  addItem,
  onChange,
}: {
  cartItems: Signal<CartItem[]>;
  addItem: Signal<ItemSelect>;
  onChange: (cartDetails: CartDetails) => void;
}) => {
  useEffect(() => {
    const totalItem = cartItems.value.length;
    const totalQty = cartItems.value.reduce((sum, item) => sum + item.qty, 0);
    const totalAmount = cartItems.value.reduce((sum, item) => sum + item.salePrice * item.qty, 0);
    const totalSd = cartItems.value.reduce(
      (sum, item) => sum + calculatePercentage(item.supplementaryDuty, totalAmount),
      0
    );
    const totalSc = cartItems.value.reduce(
      (sum, item) => sum + calculatePercentage(item.serviceCharge, totalAmount),
      0
    );
    const totalDiscount = cartItems.value.reduce(
      (sum, item) => sum + calculatePercentage(item.discount, totalAmount),
      0
    );
    const cost = totalSd + totalSc - totalDiscount;
    const totalVat = cartItems.value.reduce((sum, item) => sum + calculatePercentage(item.vat, totalAmount + cost), 0);
    const totalNetAmount = totalAmount + cost + totalVat;

    onChange({
      totalItem,
      totalQty,
      totalAmount,
      totalSd,
      totalSc,
      totalDiscount,
      cost,
      totalVat,
      totalNetAmount,
    });
  }, [cartItems.value, onChange]);

  const CalculateTotal = ({ label, description, icon }: { label: string; description: string; icon?: any }) => {
    return (
      <div className="inline-flex items-center gap-2">
        {icon}
        <div className="flex flex-col">
          <span className="font-medium">{label}</span>
          <span className="font-extralight text-sm text-gray-500">{description}</span>
        </div>
      </div>
    );
  };

  const itemRenderer = (item: CartItem) => {
    return (
      <div className="inline-flex items-center gap-2">
        <div className="px-2">
          <FaTrash
            className="text-red-500 cursor-pointer w-4"
            onClick={() => {
              cartItems.value = cartItems.value.filter((cartItem) => cartItem.id !== item.id);
              console.log('Updated cart:', cartItems.value);
            }}
          />
        </div>
        <img
          src={`images/profile/${item.name !== undefined ? 'profile.jpg' : 'default_profile.png'}`}
          className="w-8 h-8 rounded-full"
          alt="not_found"
        />
        <div id={String(item.id)} className="flex flex-col">
          <span className="font-medium">{item.name}</span>
          <span className="font-extralight text-sm text-gray-500">{`Code: ${item.code} | Price: ${item.salePrice.toFixed(2)}${item.uom}`}</span>
        </div>
        <Tooltip for={String(item.id)} position="bottom-start" text="Wrap in “quotes” for exact phrase" />
      </div>
    );
  };

  const amountRenderer = (item: CartItem) => {
    return (
      <CalculateTotal label={`${item.salePrice} X ${item.qty}(qty)`} description={`= ${item.salePrice * item.qty}`} />
    );
  };

  const costRenderer = (item: CartItem) => {
    const totalAmount = item.salePrice * item.qty;
    return (
      <CalculateTotal
        label={`${calculatePercentage(item.supplementaryDuty, totalAmount)} + ${calculatePercentage(item.serviceCharge, totalAmount)} - ${calculatePercentage(item.discount, totalAmount)}`}
        description={`= ${calculatePercentage(item.supplementaryDuty + item.serviceCharge - item.discount, totalAmount)}`}
      />
    );
  };

  const totalRenderer = (item: CartItem) => {
    const totalAmount = item.salePrice * item.qty;
    const cost = calculatePercentage(item.supplementaryDuty + item.serviceCharge - item.discount, totalAmount);
    const vat = calculatePercentage(item.vat, cost + totalAmount);
    return <CalculateTotal label={`${totalAmount} + ${cost} + ${vat}`} description={`= ${totalAmount + cost + vat}`} />;
  };

  const payableRenderer = (item: CartItem) => {
    const totalAmount = item.salePrice * item.qty;
    const cost = calculatePercentage(item.supplementaryDuty + item.serviceCharge - item.discount, totalAmount);
    const vat = calculatePercentage(item.vat, cost + totalAmount);
    return <span className="font-semibold">{(totalAmount + cost + vat).toFixed(2)}</span>;
  };
  return (
    <Grid
      className="h-full w-full overflow-auto bg-white/40"
      items={cartItems.value}
      theme="compact row-stripes"
      onActiveItemChanged={(e) => {
        const itemE = e.detail.value;
        if (itemE && itemE.name) {
          addItem.value = { item: itemE as unknown as ItemDtoModel, qty: itemE.qty };
        }
      }}
    >
      <GridColumn header="Name" renderer={(e) => itemRenderer(e.item)} resizable autoWidth />
      <GridColumn path="qty" header="Quantity(qty)" resizable autoWidth />
      <GridColumn header="Amount" renderer={(e) => amountRenderer(e.item)} resizable autoWidth />
      <GridColumn header="Cost (SD + SC - Discount)" renderer={(e) => costRenderer(e.item)} resizable autoWidth />
      <GridColumn header="Total (Amount + Cost + Vat)" renderer={(e) => totalRenderer(e.item)} resizable autoWidth />
      <GridColumn header="Payable Cost" renderer={(e) => payableRenderer(e.item)} resizable autoWidth flexGrow={1} />
    </Grid>
  );
};

export default ItemGridRC;
