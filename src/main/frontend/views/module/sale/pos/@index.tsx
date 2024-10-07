import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Signal, useComputed, useSignal } from '@vaadin/hilla-react-signals';
import {
  Button,
  ComboBox,
  ComboBoxElement,
  DatePicker,
  DatePickerElement,
  NumberField,
  NumberFieldElement,
} from '@vaadin/react-components';
import { ConfirmDialog } from '@vaadin/react-components/ConfirmDialog.js';
import dateFnsFormat from 'date-fns/format';
import ButtonRC from 'Frontend/components/button/regular/ButtonRC';
import DataTableRC from 'Frontend/components/data/DataTableRC';
import { formatDateIso8601, parseDateIso8601 } from 'Frontend/components/datetime/DateTimeFormatRC';
import DialogModalRC from 'Frontend/components/dialog/DialogModalRC';
import ItemDtoModel from 'Frontend/generated/com/itbd/protisthan/db/dto/ItemDtoModel';
import OrderDto from 'Frontend/generated/com/itbd/protisthan/db/dto/OrderDto';
import SellQueueDto from 'Frontend/generated/com/itbd/protisthan/db/dto/redis/SellQueueDto';
import AndFilter from 'Frontend/generated/com/vaadin/hilla/crud/filter/AndFilter';
import PropertyStringFilter from 'Frontend/generated/com/vaadin/hilla/crud/filter/PropertyStringFilter';
import Matcher from 'Frontend/generated/com/vaadin/hilla/crud/filter/PropertyStringFilter/Matcher';
import { ItemDtoCrudService, OrderDtoCrudService, SellQueueController } from 'Frontend/generated/endpoints';
import { ItemDataProvider } from 'Frontend/utils/combobox/ComboboxDataProvider';
import React, { useEffect, useMemo, useRef } from 'react';
import { FaShoppingBag } from 'react-icons/fa';
import { FaCartShopping, FaList, FaPlus, FaTrash } from 'react-icons/fa6';
import { ToastContainer, toast } from 'react-toastify';
import CostNodeRC from './_CostNode';
import InvoiceBuilderRC from './_InvoiceBuilder';
import { CartDetails, CartItem, InvoiceDetails, ItemSelect } from './_ItemCartRC';
import ItemGridRC from './_ItemGrid';
import SellStateRC from './_SellState';
import 'react-toastify/dist/ReactToastify.css';

const config: ViewConfig = {
  menu: {
    title: 'POS',
    icon: 'sell',
  },
};

const PosView: React.FC = () => {
  const confirmDialogOpened = useSignal<boolean>(false);
  const queueListDialogOpened = useSignal<boolean>(false);

  const itemDataProvider = useMemo(() => ItemDataProvider, []);

  const itemSelect = useSignal<ItemSelect>({
    item: {} as ItemDtoModel,
    qty: 0,
  });

  const cartItems = useSignal<CartItem[]>([]);
  const cartDetails = useSignal<CartDetails>({} as CartDetails);
  const invDetails = useSignal<InvoiceDetails>({} as InvoiceDetails);
  const orderInfo = useSignal<OrderDto>({} as OrderDto);
  const sellQueue = useSignal<SellQueueDto[]>([]);

  const selectedDateValue = useSignal(dateFnsFormat(new Date(), 'yyyy-MM-dd'));

  const itemSelectRef = useRef<ComboBoxElement>(null);
  const itemQtyRef = useRef<NumberFieldElement>(null);
  const datePickerRef = useRef<DatePickerElement>(null);

  useEffect(() => {
    itemSelectRef.current?.focus();
  }, []);

  useEffect(() => {
    const datePicker = datePickerRef.current;
    if (datePicker) {
      datePicker.i18n = {
        ...datePicker.i18n,
        formatDate: formatDateIso8601,
        parseDate: parseDateIso8601,
      };
    }
  }, [datePickerRef.current]);

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

  function itemAdd({ item, qty }: ItemSelect) {
    // const { item, qty } = itemSelect.value;
    if (item.id === undefined) itemSelectRef.current?.focus();
    if (qty === 0 || item.id === undefined) return;
    const itemCart: CartItem = {
      ...item,
      uom: `(${item?.saleUom?.id})` || '',
      qty,
    } as unknown as CartItem;

    const itemExists = cartItems.value.some((itemE) => itemE.id === itemCart.id);
    if (itemExists) {
      cartItems.value = cartItems.value.map((itemE) => (itemE.id === itemCart.id ? { ...itemE, qty } : itemE));
      console.log('Updated item quantity in cart', cartItems.value);
    } else {
      cartItems.value = [...cartItems.value, itemCart];
      // console.log('Added new item to cart', addToCart.value);
    }
    itemSelect.value = { item: {} as ItemDtoModel, qty: 0 };
  }

  function fieldValidation(): boolean {
    const { saleDate, customerId, employeeId, paymentMode, paidAmount } = invDetails.value;
    if (customerId === undefined || Number.isNaN(customerId)) {
      toast.error('Customer is required!');
      return false;
    }
    if (employeeId === undefined || Number.isNaN(employeeId)) {
      toast.error('Employee is required!');
      return false;
    }
    if (saleDate === undefined || Number.isNaN(saleDate)) {
      toast.error('Sale Date is required!');
      return false;
    }
    if (paymentMode === undefined || Number.isNaN(paymentMode)) {
      toast.error('Payment Mode is required!');
      return false;
    }
    if (paidAmount === undefined || Number.isNaN(paidAmount)) {
      toast.error('Paid Amount Mode is required!');
      return false;
    }
    return true;
  }

  const restoreCartItem = (item: SellQueueDto | undefined) => {
    const { customerId } = invDetails.value;
    if (customerId === undefined) {
      toast.error('Customer is required!');
    } else {
      const items = item?.orderDetails;
      const ids = items?.map((it) => Number(it?.id?.idItemKey));

      ItemDtoCrudService.getItems(ids).then((data) => {
        console.log('Item:', data);
        data?.forEach((dataE) => {
          const qty = items?.find((it) => it?.id?.idItemKey === dataE.id)?.quantity;
          itemAdd({ item: { ...dataE } as unknown as ItemDtoModel, qty: Number(qty) });
        });
        toast.success('Item Restored Successfully!');
      });
    }
  };

  function placeOrder(): OrderDto {
    const { saleDate, customerId, employeeId, paymentMode } = invDetails.value;
    const order: OrderDto = {
      orderDate: saleDate,
      customer: { id: customerId },
      employee: { id: employeeId },
      modeOfPayment: { id: paymentMode },
      orderDetails: [
        ...cartItems.value.map((item) => {
          return {
            id: { idItemKey: item.id },
            quantity: item.qty,
            unitPrice: item.salePrice,
            discount: item.discount,
          };
        }),
      ],
    } as unknown as OrderDto;
    return order;
  }

  const InvoiceBill = ({ invoiceDetails }: { invoiceDetails: Signal<InvoiceDetails> }) => {
    return (
      <>
        <CostNodeRC
          className="bg-indigo-600 p-2 text-2xl"
          lable="Total Cost"
          value={String(invoiceDetails.value.totalCost?.toFixed(2))}
        />
        <CostNodeRC className="bg-indigo-600 p-1" lable="Last Purchase" value="0.0" />
      </>
    );
  };

  const ItemRenderer = (item: ItemDtoModel) => {
    const { name, code, salePrice, saleUom } = item;
    return (
      <div className="flex flex-row items-center gap-2">
        <img
          src={`images/profile/${name !== undefined ? 'profile.jpg' : 'default_profile.png'}`}
          className="w-8 h-8 rounded-full"
          alt="not_found"
        />
        <div className="flex flex-col">
          <span className="font-medium">{`${name}`}</span>
          <span className="font-extralight text-sm text-gray-500">{`Code: ${code} | Price: ${salePrice}(${saleUom?.id})`}</span>
        </div>
      </div>
    );
  };

  const QueueListDialog = () => {
    return (
      <DialogModalRC headerTitle="Queue List" opened={queueListDialogOpened} onSave={() => {}}>
        <div className="m-2 font-semibold h-full">
          <DataTableRC<SellQueueDto>
            data={sellQueue.value}
            rowId="id"
            columns={[
              {
                label: 'Last Queue',
                key: 'queueDate',
                render: (value: SellQueueDto) => (
                  <span>{dateFnsFormat(new Date(`${value.queueDate}`), 'hh:mm aa MM/dd/yyyy')}</span>
                ),
              },
              {
                label: 'Last Cost',
                key: 'totalCost',
                render: (value: SellQueueDto) => <span>{value.totalCost?.toFixed(2)}</span>,
              },
              {
                label: 'Items',
                key: 'totalItem',
              },
              {
                label: 'Restore',
                key: 'action' as keyof SellQueueDto,
                render: (value: SellQueueDto) => (
                  <FaShoppingBag
                    className="size-10 rounded-full ring-1 ring-gray-200 p-3 hover:bg-gray-500 hover:text-white"
                    onClick={() => restoreCartItem(value)}
                  />
                ),
              },
            ]}
          />
        </div>
      </DialogModalRC>
    );
  };

  const QueueConfirmDialog = () => {
    return (
      <ConfirmDialog
        header="Import Again"
        confirmText="Import"
        cancelText="Cancel"
        cancelButtonVisible
        opened={confirmDialogOpened.value}
        onOpenedChanged={(e) => {
          confirmDialogOpened.value = e.detail.value;
        }}
        onConfirm={() => {
          const { saleDate, customerId, employeeId, paymentMode, invDiscount, invService, totalCost } =
            invDetails.value;
          const orderE: SellQueueDto = {
            orderDate: saleDate,
            customerId,
            employeeId,
            paymentMode,
            invDiscount,
            invService,
            totalCost,
            totalItem: cartItems.value.length,
            orderDetails: [
              ...cartItems.value.map((item) => {
                return {
                  id: { idItemKey: item.id },
                  quantity: item.qty,
                  unitPrice: item.salePrice,
                  discount: item.discount,
                };
              }),
            ],
          };
          SellQueueController.putInQueue(orderE);
        }}
        onCancel={() => {
          confirmDialogOpened.value = false;
        }}
      >
        Do you want to Import again!
      </ConfirmDialog>
    );
  };

  return (
    <div className="flex flex-row h-full w-full">
      <section className="flex flex-col grow h-full px-4 pt-2 gap-2 md:min-w-96">
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
              selectedItem={itemSelect.value && itemSelect.value.item.id ? itemSelect.value.item : undefined}
              renderer={({ item }) => ItemRenderer(item)}
              onSelectedItemChanged={(e) => {
                const item = e.detail.value;
                itemSelect.value = { ...itemSelect.value, item: item || ({} as ItemDtoModel) };
                itemQtyRef.current?.focus();
              }}
            />
            <NumberField
              ref={itemQtyRef}
              label="Quantity"
              stepButtonsVisible
              min={0}
              defaultValue={0}
              value={String(itemSelect.value.qty)}
              onKeyDown={(e) => {
                if (e.key === 'Enter') {
                  const qty = e.currentTarget.value;
                  const { item } = itemSelect.value;
                  itemSelect.value = { item, qty: Number(qty) };
                  itemAdd({ item, qty: Number(qty) });
                }
              }}
            />
          </div>
          <div className="flex flex-row gap-2 ">
            <ButtonRC
              title="Cart"
              onClick={() => {
                if (itemQtyRef.current) {
                  const qty = itemQtyRef.current.value;
                  const { item } = itemSelect.value;
                  itemSelect.value = { item, qty: Number(qty) };
                  itemAdd({ item, qty: Number(qty) });
                }
              }}
            >
              <FaPlus />
            </ButtonRC>
            <Button
              className="bg-red-400 text-white hover:bg-red-600 "
              onClick={() => {
                cartItems.value = [];
                toast.success('Cart Cleared!');
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
          <ItemGridRC
            cartItems={cartItems}
            addItem={itemSelect}
            onChange={(e) => {
              cartDetails.value = e;
            }}
          />
        </main>
        <footer className="flex sm:gap-1 md:gap-3 h-14 items-stretch border rounded-xl m-1">
          <SellStateRC cartDetails={cartDetails} />
        </footer>
      </section>
      <aside className="flex flex-col z-30 overflow-hidden shadow-2xl duration-300 fixed md:relative w-full h-full md:max-w-96 p-2 bg-gray-50">
        <section className="flex flex-col w-full h-full overflow-y-auto gap-2">
          <header className="flex flex-col border gap-2 p-2 rounded-xl sticky top-0 bg-gray-50">
            <InvoiceBill invoiceDetails={invDetails} />
          </header>
          <main className="h-full">
            <InvoiceBuilderRC
              cartDetails={cartDetails}
              onChange={(e) => {
                invDetails.value = e;
              }}
            />
          </main>
          <footer className="flex flex-col border gap-2 p-2 rounded-xl">
            <div className="flex flex-row items-center gap-4">
              <DatePicker
                ref={datePickerRef}
                label="Sale Date:"
                className="w-full"
                placeholder="DD-MM-YYYY"
                helperText="Format: DD-MM-YYYY"
                // value={invDetails.value.saleDate}
                onValueChanged={(event) => {
                  // selectedDateValue.value = event.detail.value;
                  invDetails.value = { ...invDetails.value, saleDate: event.detail.value };
                }}
              />
              <ButtonRC
                title="Queue"
                onClick={() => {
                  const { customerId } = invDetails.value;
                  if (customerId === undefined || Number.isNaN(customerId)) {
                    toast.error('Customer is required!');
                  } else {
                    confirmDialogOpened.value = true;
                  }
                }}
              />
              <button
                type="button"
                className="p-4 ring-1 ring-gray-400 hover:bg-gray-100 rounded-full"
                onClick={() => {
                  const { customerId, employeeId } = invDetails.value;
                  if (customerId === undefined || Number.isNaN(customerId)) {
                    toast.error('Customer is required!');
                  } else {
                    const order: SellQueueDto = {
                      customerId,
                      employeeId,
                    };
                    SellQueueController.getQueueList(order).then((data) => {
                      if (data !== undefined) {
                        const filteredData = data.filter((item) => item !== undefined) as SellQueueDto[];
                        sellQueue.value = filteredData;
                        queueListDialogOpened.value = true;
                      }
                    });
                  }
                }}
              >
                <FaList />
                <span className="sr-only">no context</span>
              </button>
            </div>
            <Button
              theme="primary"
              onClick={() => {
                if (!fieldValidation()) return;
                const order = placeOrder();
                if (order !== undefined && order.orderDetails !== undefined && order.orderDetails.length > 0) {
                  OrderDtoCrudService.save(order);
                } else {
                  toast.error('Order Items are empty!');
                }
              }}
            >
              <div className="inline-flex gap-3">
                <FaCartShopping />
                <span>Place Order</span>
              </div>
            </Button>
          </footer>
        </section>
      </aside>
      <QueueListDialog />
      <QueueConfirmDialog />
      <ToastContainer position="top-center" autoClose={2000} />
    </div>
  );
};

export default PosView;
