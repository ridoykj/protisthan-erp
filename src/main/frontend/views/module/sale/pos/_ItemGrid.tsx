import { Signal } from '@vaadin/hilla-react-signals';
import { Grid, GridColumn, Tooltip } from '@vaadin/react-components';
import ItemDtoModel from 'Frontend/generated/com/itbd/protisthan/db/dto/ItemDtoModel';
import { FaTrash } from 'react-icons/fa6';
import { calculatePercentage, ItemCart } from './_ItemCartRC';

const ItemGridRC = ({
  addToCart,
  itemSelected,
  itemQty,
}: {
  addToCart: Signal<ItemCart[]>;
  itemSelected: Signal<ItemDtoModel>;
  itemQty: Signal<number>;
}) => {
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

  const itemRenderer = (item: ItemCart) => {
    return (
      <div className="inline-flex items-center gap-2">
        <div className="px-2">
          <FaTrash
            className="text-red-500 cursor-pointer w-4"
            onClick={() => {
              addToCart.value = addToCart.value.filter((cartItem) => cartItem.id !== item.id);
              console.log('Updated cart:', addToCart.value);
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
  const amountRenderer = (item: ItemCart) => {
    return (
      <CalculateTotal
        label={`${item.salePrice} X ${item.quantity}(qty)`}
        description={`= ${item.salePrice * item.quantity}`}
      />
    );
  };

  const costRenderer = (item: ItemCart) => {
    const totalAmount = item.salePrice * item.quantity;
    return (
      <CalculateTotal
        label={`${calculatePercentage(item.supplementaryDuty, totalAmount)} + ${calculatePercentage(item.serviceCharge, totalAmount)} - ${calculatePercentage(item.discount, totalAmount)}`}
        description={`= ${calculatePercentage(item.supplementaryDuty + item.serviceCharge - item.discount, totalAmount)}`}
      />
    );
  };

  const totalRenderer = (item: ItemCart) => {
    const totalAmount = item.salePrice * item.quantity;
    const cost = calculatePercentage(item.supplementaryDuty + item.serviceCharge - item.discount, totalAmount);
    const vat = calculatePercentage(item.vat, cost + totalAmount);
    return <CalculateTotal label={`${totalAmount} + ${cost} + ${vat}`} description={`= ${totalAmount + cost + vat}`} />;
  };

  const payableRenderer = (item: ItemCart) => {
    const totalAmount = item.salePrice * item.quantity;
    const cost = calculatePercentage(item.supplementaryDuty + item.serviceCharge - item.discount, totalAmount);
    const vat = calculatePercentage(item.vat, cost + totalAmount);
    return <span className="font-semibold">{(totalAmount + cost + vat).toFixed(2)}</span>;
  };
  return (
    <Grid
      className="h-full w-full overflow-auto bg-white/40"
      items={addToCart.value}
      theme="compact row-stripes"
      onActiveItemChanged={(e) => {
        console.log('Selected Items:', e.detail.value);
        const item = e.detail.value;
        if (item && item.name) {
          itemSelected.value = item as unknown as ItemDtoModel;
          itemQty.value = item.quantity;
        }
      }}
    >
      <GridColumn header="Name" renderer={(e) => itemRenderer(e.item)} resizable autoWidth />
      <GridColumn path="quantity" header="Quantity(qty)" resizable autoWidth />
      <GridColumn header="Amount" renderer={(e) => amountRenderer(e.item)} resizable autoWidth />
      <GridColumn header="Cost (SD + SC - Discount)" renderer={(e) => costRenderer(e.item)} resizable autoWidth />
      <GridColumn header="Total (Amount + Cost + Vat)" renderer={(e) => totalRenderer(e.item)} resizable autoWidth />
      <GridColumn header="Payable Cost" renderer={(e) => payableRenderer(e.item)} resizable autoWidth flexGrow={1} />
    </Grid>
  );
};

export default ItemGridRC;
