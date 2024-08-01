import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { useComputed, useSignal } from '@vaadin/hilla-react-signals';
import {
  Button,
  ComboBox,
  ComboBoxElement,
  DatePicker,
  NumberField,
  NumberFieldElement,
} from '@vaadin/react-components';
import ButtonRC from 'Frontend/components/button/regular/ButtonRC';
import ItemDtoModel from 'Frontend/generated/com/itbd/protisthan/db/dto/ItemDtoModel';
import OrderDto from 'Frontend/generated/com/itbd/protisthan/db/dto/OrderDto';
import AndFilter from 'Frontend/generated/com/vaadin/hilla/crud/filter/AndFilter';
import PropertyStringFilter from 'Frontend/generated/com/vaadin/hilla/crud/filter/PropertyStringFilter';
import Matcher from 'Frontend/generated/com/vaadin/hilla/crud/filter/PropertyStringFilter/Matcher';
import {
  CustomerDataProvider,
  EmployeeDataProvider,
  ItemDataProvider,
  ModeOfPaymentDataProvider,
} from 'Frontend/utils/combobox/ComboboxDataProvider';
import React, { useEffect, useMemo, useRef } from 'react';
import { FaCartShopping, FaPlus, FaTrash, FaUser } from 'react-icons/fa6';
import CostNodeRC from './_CostNode';
import { ItemCart } from './_ItemCartRC';
import ItemGridRC from './_ItemGrid';
import SellStateRC from './_SellState';

const config: ViewConfig = {
  menu: {
    title: 'POS',
    icon: 'sell',
  },
};

const PosView: React.FC = () => {
  const itemDataProvider = useMemo(() => ItemDataProvider, []);
  const customerDataProvider = useMemo(() => CustomerDataProvider, []);
  const employeeDataProvider = useMemo(() => EmployeeDataProvider, []);
  const modeOfPaymentDataProvider = useMemo(() => ModeOfPaymentDataProvider, []);

  const itemSelect = useSignal({
    item: {} as ItemDtoModel,
    qty: 0,
  });

  const itemSelected = useSignal<ItemDtoModel>({} as ItemDtoModel);
  const itemQty = useSignal<number>(0);

  const addToCart = useSignal<ItemCart[]>([]);
  const orderInfo = useSignal<OrderDto>({} as OrderDto);

  const itemSelectRef = useRef<ComboBoxElement>(null);
  const itemQtyRef = useRef<NumberFieldElement>(null);

  useEffect(() => {
    itemSelectRef.current?.focus();
  }, []);

  const nameFilterValue = useSignal('');
  const filter = useComputed(() => {
    const categoryFilter: PropertyStringFilter = {
      propertyId: 'category',
      filterValue: orderInfo.value.id ? orderInfo.value.id.toString() : '0',
      matcher: Matcher.EQUALS,
      '@type': 'propertyString',
    };

    const andFilter: AndFilter = {
      '@type': 'and',
      children: [categoryFilter],
    };

    return andFilter;
  });

  const itemRenderer = (item: ItemDtoModel) => {
    const { name, code, salePrice, saleUom } = item;
    return (
      <div className="flex flex-row items-center gap-2">
        <img
          src={`images/profile/${name !== undefined ? 'profile.jpg' : 'default_profile.png'}`}
          className="w-8 h-8 rounded-full"
          alt="not_found"
        />
        <div className="flex flex-col">
          <span className="font-medium">{`${item.name}`}</span>
          <span className="font-extralight text-sm text-gray-500">{`Code: ${code} | Price: ${salePrice}(${item.saleUom?.id})`}</span>
        </div>
      </div>
    );
  };

  function itemAdd() {
    // const { item, qty } = itemSelect.value;
    const item = itemSelected.value;
    if (item.id === undefined) itemSelectRef.current?.focus();
    if (itemQty.value === 0 || item.id === undefined) return;
    const itemCart: ItemCart = {
      ...item,
      uom: `(${item?.saleUom?.id})` || '',
      quantity: itemQty.value,
    } as unknown as ItemCart;

    const itemExists = addToCart.value.some((itemE) => itemE.id === itemCart.id);
    if (itemExists) {
      addToCart.value = addToCart.value.map((itemE) =>
        itemE.id === itemCart.id
          ? {
              ...itemE,
              quantity: itemQty.value,
            }
          : itemE
      );
      console.log('Updated item quantity in cart', addToCart.value);
    } else {
      addToCart.value = [
        ...addToCart.value,
        {
          ...itemCart,
        },
      ];
      console.log('Added new item to cart', addToCart.value);
    }
    itemSelected.value = {} as ItemDtoModel;
    itemQty.value = 0;
  }

  return (
    <div className="flex flex-row h-full w-full">
      <section className="flex flex-col grow h-full px-4 pt-2 gap-2">
        <header className="flex flex-col xl:flex-row items-baseline gap-1 w-full border rounded-xl p-1">
          <div className="flex flex-col xl:flex-row w-full md:grow gap-2">
            <ComboBox
              ref={itemSelectRef}
              label="Item"
              className="md:grow"
              placeholder='Search Item by "Name/#Code"'
              clearButtonVisible
              dataProvider={itemDataProvider}
              itemLabelPath="name"
              // itemValuePath="name"
              selectedItem={itemSelected.value && itemSelected.value.id ? itemSelected.value : undefined}
              renderer={({ item }) => itemRenderer(item)}
              onSelectedItemChanged={(e) => {
                const item = e.detail.value;
                itemSelected.value = item || ({} as ItemDtoModel);
                console.log('item', item);
                itemQtyRef.current?.focus();
              }}
            />
            <NumberField
              ref={itemQtyRef}
              label="Quantity"
              stepButtonsVisible
              min={0}
              defaultValue={0}
              value={String(itemQty.value)}
              onValueChanged={(e) => {
                itemQty.value = Number(e.detail.value);
              }}
              onKeyDown={(e) => {
                if (e.key === 'Enter') itemAdd();
              }}
            />
          </div>
          <div className="flex flex-row gap-2 ">
            <ButtonRC
              title="Cart"
              onClick={() => {
                itemAdd();
              }}
            >
              <FaPlus />
            </ButtonRC>
            <Button
              className="bg-red-400 text-white hover:bg-red-600 "
              onClick={() => {
                addToCart.value = [];
              }}
            >
              <span className="inline-flex gap-2">
                <FaTrash />
                Remove All
              </span>
            </Button>
          </div>
        </header>
        <main className="flex grow border rounded-xl w-full">
          <ItemGridRC addToCart={addToCart} itemSelected={itemSelected} itemQty={itemQty} />
        </main>
        <footer className="flex sm:gap-1 md:gap-3 h-14 items-stretch border rounded-xl m-1">
          <SellStateRC addToCart={addToCart} />
        </footer>
      </section>
      <aside className="flex flex-col z-30 overflow-hidden shadow-2xl duration-300 fixed md:relative w-full h-full md:max-w-96 p-2 bg-gray-50">
        <section className="flex flex-col w-full h-full overflow-y-auto gap-2 md:w-96">
          <header className="flex flex-col border gap-2 p-2 rounded-xl sticky top-0 bg-gray-50">
            <CostNodeRC className="bg-indigo-600 p-2 text-2xl" lable="Total Cost" value="0.0" />
            <CostNodeRC className="bg-indigo-600 p-1" lable="Last Purchase" value="0.0" />
          </header>
          <main className="h-full w-full flex flex-col gap-2">
            <div className="flex flex-col border gap-2 p-2 rounded-xl">
              <h1 className=" bg-green-600 text-white rounded-lg place-content-center text-center font-bold py-2">
                Bill Information
              </h1>
              <div>
                <div className="flex flex-row items-baseline">
                  <span className="flex grow w-full">Customer ID:</span>
                  <ComboBox
                    className="flex grow w-full"
                    placeholder='Search "Customer ID"'
                    // {...field(model.item)}
                    dataProvider={customerDataProvider}
                    itemLabelPath="id"
                    itemValuePath="id"
                    renderer={(item) => {
                      const { id, firstName, lastName } = item.item;
                      return (
                        <div className="text-blue-500 inline-flex items-center gap-2">
                          <FaUser className="text-gray-600 size-8 ring-2 ring-gray-300 p-1 rounded-full" />
                          {`${id} - ${firstName} ${lastName} `}
                        </div>
                      );
                    }}
                  />
                </div>
              </div>
              <div>
                <div className="flex flex-row items-baseline">
                  <span className="flex grow w-full">Stuff ID:</span>
                  <ComboBox
                    className="flex grow w-full"
                    placeholder='Search "Stuff ID"'
                    // {...field(model.item)}
                    dataProvider={employeeDataProvider}
                    itemLabelPath="id"
                    itemValuePath="id"
                    renderer={(item) => {
                      const { id, firstName, lastName } = item.item;
                      return (
                        <div className="text-blue-500 inline-flex items-center gap-2">
                          <FaUser className="text-gray-600 size-8 ring-2 ring-gray-300 p-1 rounded-full" />
                          {`${id} - ${firstName} ${lastName}`}
                        </div>
                      );
                    }}
                  />
                </div>
              </div>
            </div>
            <div className="flex flex-col border gap-2 p-2 rounded-xl">
              <h1 className=" bg-green-600 text-white rounded-lg place-content-center text-center font-bold py-2">
                Invoice
              </h1>
              <div>
                <ComboBox
                  label="Mode Of Payment:"
                  className="w-full"
                  // {...field(model.item)}
                  dataProvider={modeOfPaymentDataProvider}
                  itemLabelPath="name"
                  itemValuePath="id"
                />
                <div className="flex flex-row items-baseline gap-2 w-full">
                  <NumberField label="Discount:" min={0} max={1000} className="w-36">
                    <span slot="suffix">%</span>
                  </NumberField>
                  <NumberField min={0} className="w-full">
                    <span slot="suffix">:amount</span>
                  </NumberField>
                </div>
                <div className="flex flex-row items-baseline gap-2 w-full">
                  <NumberField label="Service:" min={0} max={1000} className="w-36">
                    <span slot="suffix">%</span>
                  </NumberField>
                  <NumberField min={0} className="w-full">
                    <span slot="suffix">:amount</span>
                  </NumberField>
                </div>
                <NumberField label="Paid Amount:" min={0} className="w-full">
                  <span slot="suffix">:amount</span>
                </NumberField>
                <h1 className="bg-green-500/20 text-green-700 font-bold text-center p-1 rounded-lg">Change: 0.00</h1>
              </div>
              <div>
                <div className="flex flex-row items-baseline gap-4">
                  <DatePicker label="Sale Date:" className="w-full" />
                  <ButtonRC title="Queue" />
                </div>
              </div>
              <Button theme="primary">
                <div className="inline-flex gap-3">
                  <FaCartShopping />
                  <span>Place Order</span>
                </div>
              </Button>
            </div>
          </main>
        </section>
      </aside>
    </div>
  );
};

export default PosView;
