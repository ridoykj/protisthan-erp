import { TabGroup, TabList, TabPanel, TabPanels } from '@headlessui/react';
import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { AutoGrid, AutoGridRef } from '@vaadin/hilla-react-crud';
import { useForm } from '@vaadin/hilla-react-form';
import { useComputed, useSignal } from '@vaadin/hilla-react-signals';
import {
  Button,
  ComboBox,
  DatePicker,
  FormLayout,
  NumberField,
  SplitLayout,
  TextField,
  TimePicker,
  Upload,
} from '@vaadin/react-components';
import ButtonRC from 'Frontend/components/button/regular/ButtonRC';
import SwitchRC from 'Frontend/components/button/regular/SwitchRC';
import FromLayoutRC, { FromColumn, FromRow } from 'Frontend/components/from/from_layout/FromLayoutRC';
import TabRC from 'Frontend/components/tab/TabRC';
import StockEntryDetailDto from 'Frontend/generated/com/itbd/protisthan/db/dto/StockEntryDetailDto';
import StockEntryDetailDtoModel from 'Frontend/generated/com/itbd/protisthan/db/dto/StockEntryDetailDtoModel';
import StockEntryDto from 'Frontend/generated/com/itbd/protisthan/db/dto/StockEntryDto';
import StockEntryDtoModel from 'Frontend/generated/com/itbd/protisthan/db/dto/StockEntryDtoModel';
import AndFilter from 'Frontend/generated/com/vaadin/hilla/crud/filter/AndFilter';
import Filter from 'Frontend/generated/com/vaadin/hilla/crud/filter/Filter';
import PropertyStringFilter from 'Frontend/generated/com/vaadin/hilla/crud/filter/PropertyStringFilter';
import Matcher from 'Frontend/generated/com/vaadin/hilla/crud/filter/PropertyStringFilter/Matcher';
import Pageable from 'Frontend/generated/com/vaadin/hilla/mappedtypes/Pageable';
import { StockEntryDetailDtoCrudService, StockEntryDtoCrudService } from 'Frontend/generated/endpoints';
import Direction from 'Frontend/generated/org/springframework/data/domain/Sort/Direction';
import {
  ItemDataProvider,
  StockEntryTypeDataProvider,
  SupplierDataProvider,
  UomDataProvider,
} from 'Frontend/utils/combobox/ComboboxDataProvider';
import React, { useEffect, useMemo } from 'react';

import { FaSave } from 'react-icons/fa';
import { FaClockRotateLeft, FaTrash } from 'react-icons/fa6';
import { useParams } from 'react-router-dom';

const config: ViewConfig = {
  menu: {
    title: 'Stock Entry',
  },
};
const pagination: Pageable = {
  pageNumber: 0,
  pageSize: 10,
  sort: {
    orders: [{ property: 'id', direction: Direction.ASC, ignoreCase: false }],
  },
};

function filterGenerator(type: string, property: string, filter: string | undefined) {
  const filters: Filter = {
    '@type': type,
    children: [
      {
        '@type': 'propertyString',
        propertyId: property,
        filterValue: filter,
        matcher: Matcher.EQUALS,
      },
    ],
  };
  return filters;
}

type FromViewProps = {
  item: StockEntryDto | undefined;
};

const StockItemRender = ({ stockEntry }: { stockEntry: StockEntryDto | undefined }) => {
  const responsiveSteps = [
    { minWidth: '0', columns: 1 },
    { minWidth: '500px', columns: 2 },
  ];

  const itemDataProvider = useMemo(() => ItemDataProvider, []);
  const uomDataProvider = useMemo(() => UomDataProvider, []);

  const selectedItems = useSignal<StockEntryDetailDto[]>([]);

  const autoGridRef = React.useRef<AutoGridRef>(null);

  function refreshGrid() {
    autoGridRef.current?.refresh();
  }

  const { field, model, read, submit, reset, value, clear } = useForm(StockEntryDetailDtoModel, {
    onSubmit: async (values) => {
      values.stockEntry = stockEntry;
      await StockEntryDetailDtoCrudService.save(values).then(() => {
        refreshGrid();
        clear();
      });
    },
  });

  const DeleteRander = ({ item }: { item: StockEntryDetailDto }) => {
    const { id } = item;
    return (
      <button
        type="button"
        className="text-red-500 hover:underline"
        title="Delete"
        onClick={(e) => {
          StockEntryDetailDtoCrudService.delete(id).then((result) => {
            refreshGrid();
            clear();
          });
        }}
      >
        <span className="sr-only">no-text</span>
        <FaTrash />
      </button>
    );
  };

  const filter = useComputed(() => {
    const categoryFilter: PropertyStringFilter = {
      propertyId: 'stockEntry.id',
      filterValue: stockEntry?.id?.toString() || '',
      matcher: Matcher.EQUALS,
      '@type': 'propertyString',
    };

    const andFilter: AndFilter = {
      '@type': 'and',
      children: [categoryFilter],
    };

    return andFilter;
  });

  return (
    <div className="flex flex-col gap-2 divide-y h-full w-full">
      <div className="flex flex-col py-4 h-full w-full">
        <SplitLayout className="h-full">
          <FormLayout responsiveSteps={responsiveSteps} className="w-[30%]">
            <ComboBox
              label="Item"
              {...field(model.item)}
              dataProvider={itemDataProvider}
              itemLabelPath="name"
              itemValuePath="id"
            />
            <ComboBox
              label="Uom"
              {...field(model.uom)}
              dataProvider={uomDataProvider}
              itemLabelPath="id"
              itemValuePath="id"
            />
            <NumberField label="Qty" {...field(model.qty)} />
            {/* <TextField label="Additional Cost" {...field(model.additionalCost)} /> */}
            <NumberField label="Amount" {...field(model.amount)} />
            <TextField label="Barcode" {...field(model.barcode)} />
            <TextField label="Batch No" {...field(model.batchNo)} />
            <TextField label="Serial No" {...field(model.serialNo)} />
            <SwitchRC
              label="Finished Item"
              checked={value.isFinishedItem}
              onChange={(e) => {
                value.isFinishedItem = e;
                read(value);
              }}
            />
            <SwitchRC
              label="Scrap Item"
              checked={value.isScrapItem}
              onChange={(e) => {
                value.isScrapItem = e;
                read(value);
              }}
            />
            <Button onClick={submit}>Update</Button>
          </FormLayout>
          <AutoGrid
            ref={autoGridRef}
            service={StockEntryDetailDtoCrudService}
            model={StockEntryDetailDtoModel}
            className="h-full w-full overflow-auto bg-white/40"
            experimentalFilter={filter.value}
            noHeaderFilters
            selectedItems={selectedItems.value}
            visibleColumns={[
              'item.name',
              'uom.id',
              'qty',
              'amount',
              'barcode',
              'batchNo',
              'serialNo',
              'isFinishedItem',
              'isScrapItem',
              'idx',
            ]}
            columnOptions={{
              name: {
                header: 'name',
                // renderer: ItemRender,
              },
              isFinishedItem: {
                header: 'Finished Item',
              },
              isScrapItem: {
                header: 'Scrap Item',
              },
              idx: {
                header: 'Action',
                filterable: false,
                sortable: false,
                resizable: true,
                renderer: DeleteRander,
              },
            }}
            onActiveItemChanged={(e) => {
              const itemE = e.detail.value;
              selectedItems.value = itemE ? [itemE] : [];
              read(itemE);
              // isSidebarVisible.value = item !== null;
            }}
          />
        </SplitLayout>
      </div>
    </div>
  );
};

const FromView: React.FC<FromViewProps> = ({ item }) => {
  const supplierDataProvider = useMemo(() => SupplierDataProvider, []);
  const stockEntryTypeDataProvider = useMemo(() => StockEntryTypeDataProvider, []);
  const { field, model, read, submit, reset, value } = useForm(StockEntryDtoModel, {
    onSubmit: async (values) => {
      await StockEntryDtoCrudService.save(values);
    },
  });

  useEffect(() => {
    read(item);
  }, [item]);

  return (
    <>
      <header className="sticky top-0 z-10 pb-2">
        <div className="inline-flex justify-end space-x-2 w-full p-1 text-sm md:px-10 sm:px-0 bg-gray-50 shadow-sm">
          <ButtonRC title="Reset" onClick={reset}>
            <FaClockRotateLeft size={15} />
          </ButtonRC>
          <ButtonRC title="Update" onClick={submit}>
            <FaSave size={15} />
          </ButtonRC>
        </div>
      </header>

      <main className="grow">
        <div className="w-full h-full md:px-10 sm:px-0 ">
          <div className="flex flex-col h-full p-2 border rounded-xl shadow-sm max-w-full items-stretch px-3">
            <TabGroup className="h-full flex flex-col">
              <TabList className="sticky top-0 w-full px-4 pb-4 bg-gray-50 border-b">
                <div className="flex flex-row bg-gray-200 rounded-lg">
                  <TabRC>Details</TabRC>
                  <TabRC>Items</TabRC>
                  {/* <TabRC>Others</TabRC> */}
                </div>
              </TabList>
              <TabPanels className="overflow-y-auto scroll-smooth px-4 grow">
                <TabPanel>
                  <div className="flex flex-col gap-2 divide-y flex-grow ">
                    <div className="flex flex-col py-4">
                      <FromLayoutRC>
                        <FromRow header="Details">
                          <FromColumn>
                            <TextField label="Name" {...field(model.name)} />
                            <ComboBox
                              label="Supplier"
                              {...field(model.supplier)}
                              dataProvider={supplierDataProvider}
                              itemLabelPath="companyName"
                              itemValuePath="id"
                            />
                            <ComboBox
                              label="Stock Entry Type"
                              {...field(model.stockEntryType)}
                              dataProvider={stockEntryTypeDataProvider}
                              itemLabelPath="id"
                              itemValuePath="id"
                            />
                            <TextField label="Address Display" {...field(model.addressDisplay)} />
                            <DatePicker label="Posting Date" {...field(model.postingDate)} />
                            <TimePicker label="Posting Time" {...field(model.postingTime)} />
                            <NumberField label="Additional Costs" {...field(model.totalAdditionalCosts)} />
                            <NumberField label="Total Amount" {...field(model.totalAmount)} />
                          </FromColumn>

                          <FromColumn>
                            <TextField label="Credit Note" {...field(model.creditNote)} />
                            <TextField label="Delivery Note No" {...field(model.deliveryNoteNo)} />
                            <TextField label="Job Card" {...field(model.jobCard)} />
                            <TextField label="Letter Head" {...field(model.letterHead)} />
                          </FromColumn>

                          <FromColumn>
                            <TextField label="Project" {...field(model.project)} />
                            <TextField label="Purchase Order" {...field(model.purchaseOrder)} />
                            <TextField label="Purchase Receipt No" {...field(model.purchaseReceiptNo)} />
                            <TextField label="Purpose" {...field(model.purpose)} />
                            <TextField label="Source Address Display" {...field(model.sourceAddressDisplay)} />
                            <TextField label="Target Address Display" {...field(model.targetAddressDisplay)} />
                            <TextField label="Remarks" {...field(model.remarks)} />
                          </FromColumn>
                          <FromColumn>
                            <NumberField label="Process Loss Percentage" {...field(model.processLossPercentage)} />
                            <NumberField label="Process Loss Qty" {...field(model.processLossQty)} />

                            <NumberField label="Total Incoming Value" {...field(model.totalIncomingValue)} />
                            <NumberField label="Total Outgoing Value" {...field(model.totalOutgoingValue)} />
                          </FromColumn>
                          <FromColumn>
                            <SwitchRC
                              label="Add To Transit"
                              checked={value.isAddToTransit}
                              onChange={(e) => {
                                value.isAddToTransit = e;
                                read(value);
                              }}
                            />
                            <SwitchRC
                              label="Inspection Required"
                              checked={value.isInspectionRequired}
                              onChange={(e) => {
                                value.isInspectionRequired = e;
                                read(value);
                              }}
                            />
                            <SwitchRC
                              label="Opening"
                              checked={value.isOpening}
                              onChange={(e) => {
                                value.isOpening = e;
                                read(value);
                              }}
                            />
                            <SwitchRC
                              label="Return"
                              checked={value.isReturn}
                              onChange={(e) => {
                                value.isReturn = e;
                                read(value);
                              }}
                            />
                            <SwitchRC
                              label="Posting Time"
                              checked={value.isSetPostingTime}
                              onChange={(e) => {
                                value.isSetPostingTime = e;
                                read(value);
                              }}
                            />
                          </FromColumn>
                          <FromColumn header="Upload">
                            <div className="text-blue-500 inline-flex items-center gap-2 border-b p-2">
                              <img
                                src={`images/profile/${'default_profile.png'}`}
                                className="size-28 rounded-full ring"
                                alt="not_found"
                              />
                              <Upload
                                method="PUT"
                                target="/api/upload-handler"
                                headers='{ "X-API-KEY": "7f4306cb-bb25-4064-9475-1254c4eff6e5" }'
                              />
                            </div>
                          </FromColumn>
                        </FromRow>
                      </FromLayoutRC>
                    </div>
                  </div>
                </TabPanel>
                <TabPanel className="h-full">
                  <StockItemRender stockEntry={item} />
                </TabPanel>
                <TabPanel>Content 3</TabPanel>
              </TabPanels>
            </TabGroup>
          </div>
        </div>
      </main>
    </>
  );
};

const StockEntryView = () => {
  const { stockEntryId } = useParams();
  const dialogOpened = useSignal(false);
  const editItem = useSignal<StockEntryDto | undefined>(undefined);

  useEffect(() => {
    StockEntryDtoCrudService.list(pagination, filterGenerator('and', 'id', stockEntryId)).then((result) => {
      editItem.value = result ? result[0] : undefined;
    });
  }, [stockEntryId]);

  return (
    <>
      <header />
      <main className="h-full flex flex-col items-stretch">
        <FromView item={editItem.value} />
      </main>
      <footer />
    </>
  );
};

export default StockEntryView;
