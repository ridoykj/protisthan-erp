import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { AutoGrid, AutoGridRef } from '@vaadin/hilla-react-crud';
import { useComputed, useSignal } from '@vaadin/hilla-react-signals';
import { Button, ComboBox, DatePicker, Grid, GridColumn, NumberField } from '@vaadin/react-components';
import ButtonRC from 'Frontend/components/button/regular/ButtonRC';
import ItemDto from 'Frontend/generated/com/itbd/protisthan/db/dto/ItemDto';
import OrderDetailDtoModel from 'Frontend/generated/com/itbd/protisthan/db/dto/OrderDetailDtoModel';
import OrderDto from 'Frontend/generated/com/itbd/protisthan/db/dto/OrderDto';
import AndFilter from 'Frontend/generated/com/vaadin/hilla/crud/filter/AndFilter';
import PropertyStringFilter from 'Frontend/generated/com/vaadin/hilla/crud/filter/PropertyStringFilter';
import Matcher from 'Frontend/generated/com/vaadin/hilla/crud/filter/PropertyStringFilter/Matcher';
import { OrderDetailDtoCrudService } from 'Frontend/generated/endpoints';
import {
  CustomerDataProvider,
  EmployeeDataProvider,
  ItemDataProvider,
  ModeOfPaymentDataProvider,
} from 'Frontend/utils/combobox/ComboboxDataProvider';
import React, { useMemo } from 'react';
import { FaCartShopping, FaPlus, FaTrash, FaUser } from 'react-icons/fa6';
import { Tooltip as ReactTooltip } from 'react-tooltip';

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
  const autoGridRef = React.useRef<AutoGridRef>(null);
  const selectedItems = useSignal<ItemDto[]>([]);

  const itemSelect = useSignal<number>(0);
  const addToCart = useSignal<ItemDto[]>([]);
  const orderInfo = useSignal<OrderDto>({} as OrderDto);

  const nameFilterValue = useSignal('');
  const filter = useComputed(() => {
    const categoryFilter: PropertyStringFilter = {
      propertyId: 'category',
      filterValue: orderInfo.value.id ? orderInfo.value.id.toString() : '0',
      matcher: Matcher.EQUALS,
      '@type': 'propertyString',
    };

    console.log('hello jhdgd');
    const andFilter: AndFilter = {
      '@type': 'and',
      children: [categoryFilter],
    };

    return andFilter;
  });

  const CostNode = ({
    lable,
    value,
    hints,
    className,
  }: {
    lable: string;
    value: string;
    hints?: string;
    className?: string;
  }) => {
    return (
      <div className={`${className} w-full rounded-lg place-content-center text-center font-bold`}>
        <h1 className="text-white " data-tooltip-id={`pos_${lable}`}>
          {lable}
        </h1>
        <h1 className="text-white ">{value}</h1>
        {hints && <ReactTooltip id={`pos_${lable}`} place="top" content={hints} />}
      </div>
    );
  };

  const ProductGrid = () => {
    return (
      <Grid
        className="h-full w-full overflow-auto bg-white/40"
        items={addToCart.value}
        selectedItems={selectedItems.value}
        onActiveItemChanged={(e) => {
          const item = e.detail.value;
          selectedItems.value = item ? [item] : [];
        }}
      >
        <GridColumn path="id" />
        <GridColumn path="name" />
      </Grid>
    );
  };

  return (
    <div className="flex flex-row h-full w-full">
      <section className="flex flex-col grow h-full px-4 pt-2 gap-2">
        <header className="flex flex-col xl:flex-row items-baseline gap-1 w-full border rounded-xl p-1">
          <div className="flex flex-col xl:flex-row w-full md:grow gap-2">
            <ComboBox
              label="Product"
              className="md:grow"
              placeholder='Search "Product"'
              // {...field(model.item)}
              dataProvider={itemDataProvider}
              itemLabelPath="name"
              itemValuePath="id"
              onValueChanged={(e) => {
                const item = e.detail.value;
                itemSelect.value = parseInt(item, 10);
              }}
            />
            <NumberField label="Quantity" stepButtonsVisible min={0} />
          </div>
          <div className="flex flex-row gap-2 ">
            <ButtonRC
              title="Add Cart"
              onClick={() => {
                OrderDetailDtoCrudService.save({
                  idItemKey: { id: itemSelect.value },
                  quantity: 1,
                });
              }}
            >
              <FaPlus />
            </ButtonRC>
            <Button
              className="bg-red-400 text-white hover:bg-red-600"
              onClick={() => {
                // cartItem.value = [];
              }}
            >
              <div className="inline-flex gap-3">
                <FaTrash />
                <span>Remove All</span>
              </div>
            </Button>
          </div>
        </header>
        <main className="flex grow border rounded-xl w-full">
          {/* <ProductGrid /> */}
          <AutoGrid
            className="h-full w-full overflow-auto bg-white/40"
            service={OrderDetailDtoCrudService}
            model={OrderDetailDtoModel}
            // experimentalFilter={filter.value}
            noHeaderFilters
          />
          {/* <Grid
            className="h-full w-full overflow-auto bg-white/40"
            items={cartItem.value}
            // selectedItems={selectedItems.value}
            onActiveItemChanged={(e) => {
              const item = e.detail.value;
              selectedItems.value = item ? [item] : [];
            }}
          >
            <GridColumn path="id" />
            <GridColumn path="name" />
          </Grid> */}
          {/* <div className="h-full w-full overflow-auto bg-white/40">
            {cartItem.value.map((item) => {
              return (
                <div key={item.id}>
                  <div className="flex flex-row items-center gap-2">
                    <span>{item.name}</span>
                    <span>{item.id}</span>
                  </div>
                </div>
              );
            })}
          </div> */}
        </main>
        <footer className="flex gap-3 h-14 items-stretch border rounded-xl p-1">
          <CostNode className="bg-green-600" lable="Total Item" value="0.0" />
          <CostNode className="bg-green-600" lable="Total Qty" value="0.0" />
          <CostNode className="bg-green-600" lable="Amount" value="0.0" />
          <CostNode className="bg-green-600" lable="SD." value="0.0" hints="Supplementary duty" />
          <CostNode className="bg-green-600" lable="SC." value="0.0" hints="Service Charge" />
          <CostNode className="bg-green-600" lable="Discount" value="0.0" />
          <CostNode className="bg-green-600" lable="VAT" value="0.0" />
          <CostNode className="bg-green-600" lable="Net Amount" value="0.0" />
          <CostNode className="bg-green-600" lable="Exchange" value="0.0" />
        </footer>
      </section>
      <aside className="flex flex-col z-30 overflow-hidden shadow-2xl duration-300 fixed md:relative w-full h-full md:max-w-96 p-2 bg-gray-50">
        <section className="flex flex-col w-full h-full overflow-y-auto gap-2 md:w-96">
          <header className="flex flex-col border gap-2 p-2 rounded-xl sticky top-0 bg-gray-50">
            <CostNode className="bg-indigo-600 p-2 text-2xl" lable="Total Cost" value="0.0" />
            <CostNode className="bg-indigo-600 p-1" lable="Last Purchase" value="0.0" />
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
