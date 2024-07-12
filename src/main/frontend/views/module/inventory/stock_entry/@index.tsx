import { TabGroup, TabList, TabPanel, TabPanels } from '@headlessui/react';
import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { AutoGrid, AutoGridRef } from '@vaadin/hilla-react-crud';
import { useForm } from '@vaadin/hilla-react-form';
import { Signal, useSignal } from '@vaadin/hilla-react-signals';
import { ComboBox, DatePicker, NumberField, TextField, TimePicker } from '@vaadin/react-components';
import SpeedDialRC, { SpeedDialNode } from 'Frontend/components/button/fab/SpeedDialRC';
import ButtonRC from 'Frontend/components/button/regular/ButtonRC';
import SwitchRC from 'Frontend/components/button/regular/SwitchRC';
import DialogModalRC from 'Frontend/components/dialog/DialogModalRC';
import DetailSectionRC from 'Frontend/components/from/details/DetailSectionRC';
import FromLayoutRC, { FromColumn, FromRow } from 'Frontend/components/from/from_layout/FromLayoutRC';
import TabRC from 'Frontend/components/tab/TabRC';
import StockEntryDto from 'Frontend/generated/com/itbd/protisthan/db/dto/StockEntryDto';

import StockEntryDtoModel from 'Frontend/generated/com/itbd/protisthan/db/dto/StockEntryDtoModel';

import { StockEntryDtoCrudService } from 'Frontend/generated/endpoints';
import { StockEntryTypeDataProvider, SupplierDataProvider } from 'Frontend/utils/combobox/ComboboxDataProvider';
import React, { useMemo } from 'react';
import { FaPiggyBank, FaStickyNote } from 'react-icons/fa';
import {
  FaAddressBook,
  FaAddressCard,
  FaArrowsRotate,
  FaCalendarCheck,
  FaCartShopping,
  FaChessKing,
  FaClock,
  FaEyeLowVision,
  FaFileCirclePlus,
  FaMoneyBill,
  FaPenToSquare,
  FaRegCircleUser,
  FaTrash,
} from 'react-icons/fa6';
import { useLocation, useNavigate } from 'react-router-dom';

const config: ViewConfig = {
  menu: {
    title: 'Stock Entry',
    icon: 'dice',
  },
};

const StockEntrysView = () => {
  console.count('UserView');
  const navigate = useNavigate();
  const location = useLocation();

  const selectedItems = useSignal<StockEntryDto[]>([]);
  const dialogOpened = useSignal<boolean>(false);
  const isSidebarVisible = useSignal<boolean>(false);

  const autoGridRef = React.useRef<AutoGridRef>(null);

  function refreshGrid() {
    autoGridRef.current?.refresh();
  }

  const DialogView = () => {
    const supplierDataProvider = useMemo(() => SupplierDataProvider, []);
    const stockEntryTypeDataProvider = useMemo(() => StockEntryTypeDataProvider, []);
    const { field, model, submit, clear, value, read } = useForm(StockEntryDtoModel, {
      onSubmit: async (person) => {
        console.log(person);
        await StockEntryDtoCrudService.save(person).then(() => {
          dialogOpened.value = false;
          clear();
          refreshGrid();
        });
      },
    });
    return (
      <DialogModalRC headerTitle="Add Item" opened={dialogOpened} onSave={submit}>
        <FromLayoutRC>
          <FromRow>
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
          </FromRow>
        </FromLayoutRC>
      </DialogModalRC>
    );
  };

  const ItemRender = ({ item }: { item: StockEntryDto }) => {
    const { id, name } = item;
    return (
      <div className="text-blue-500 inline-flex items-center gap-2">
        <FaCartShopping className="text-gray-600 size-8 ring-2 ring-gray-300 p-1 rounded-full" />
        {name}
      </div>
    );
  };

  const MainSection = () => {
    return (
      <section className="flex flex-col w-full h-full items-stretch ">
        <header className="sticky top-0  pb-2 w-full">
          <div className="inline-flex justify-end space-x-2 w-full p-1 text-sm md:px-10 sm:px-0 bg-gray-50">
            <ButtonRC
              onClick={() => {
                refreshGrid();
              }}
            >
              <FaArrowsRotate size={15} />
            </ButtonRC>
          </div>
        </header>
        <main className="w-full h-full">
          <AutoGrid
            ref={autoGridRef}
            service={StockEntryDtoCrudService}
            model={StockEntryDtoModel}
            className="h-full w-full overflow-auto bg-white/40"
            selectedItems={selectedItems.value}
            visibleColumns={[
              'name',
              'stockEntryType.id',
              'supplier.companyName',
              'postingDate',
              'isOpening',
              'isReturn',
              'isInspectionRequired',
              'totalAmount',
              'totalAdditionalCosts',
            ]}
            columnOptions={{
              name: {
                header: 'name',
                renderer: ItemRender,
              },
            }}
            onActiveItemChanged={(e) => {
              const item = e.detail.value;
              selectedItems.value = item ? [item] : [];
              isSidebarVisible.value = item !== null;
            }}
          />
        </main>
      </section>
    );
  };

  const DetailField: React.FC<{ title: string; details: string; icon: React.ReactNode }> = ({
    title,
    details,
    icon,
  }) => {
    return (
      <div className="inline-flex py-2 gap-4">
        <div className="bg-gray-200 min-w-14 size-14 flex items-center justify-center rounded-lg text-2xl">{icon}</div>
        <div className="flex flex-col">
          <span className="font-semibold text-gray-700">{title}</span>
          <span className="">{details}</span>
        </div>
      </div>
    );
  };

  const ItemField = ({ item }: { item: StockEntryDto }) => {
    return item ? (
      <TabGroup>
        <TabList className="sticky top-0 w-full px-4 pb-4 bg-gray-50 border-b">
          <div className="flex flex-row bg-gray-200 rounded-lg">
            <TabRC>Details</TabRC>
            <TabRC>Notes</TabRC>
            <TabRC>Others</TabRC>
          </div>
        </TabList>
        <TabPanels className="overflow-y-auto scroll-smooth px-4">
          <TabPanel>
            <div className="flex flex-col gap-2 divide-y flex-grow ">
              <div className="flex flex-col py-4">
                <span className="font-semibold text-lg">Personal Information</span>
                <DetailField title="Name" details={item.name ?? ''} icon={<FaAddressBook />} />
                <DetailField title="Supplier" details={item.supplier?.companyName ?? ''} icon={<FaRegCircleUser />} />
                <DetailField title="Stock Entry Type" details={item.stockEntryType?.id ?? ''} icon={<FaChessKing />} />
                <DetailField title="Address Display" details={item.addressDisplay ?? ''} icon={<FaAddressCard />} />
                <DetailField title="Posting Date" details={item.postingDate ?? ''} icon={<FaCalendarCheck />} />
                <DetailField title="Posting Time" details={item.postingTime ?? ''} icon={<FaClock />} />
                <DetailField
                  title="Additional Costs"
                  details={item.totalAdditionalCosts?.toString() ?? ''}
                  icon={<FaPiggyBank />}
                />
                <DetailField title="Total Amount" details={item.totalAmount?.toString() ?? ''} icon={<FaMoneyBill />} />
              </div>
            </div>
          </TabPanel>
          <TabPanel>
            <div className="flex flex-col gap-2 divide-y flex-grow ">
              <div className="flex flex-col py-4">
                <span className="font-semibold text-lg">Personal Information</span>
                <DetailField title="Credit Note" details={item.creditNote ?? ''} icon={<FaStickyNote />} />
                <DetailField title="Delivery Note No" details={item.deliveryNoteNo ?? ''} icon={<FaStickyNote />} />
                <DetailField title="Job Card" details={item.jobCard ?? ''} icon={<FaStickyNote />} />
                <DetailField title="Letter Head" details={item.letterHead ?? ''} icon={<FaStickyNote />} />
                <DetailField title="Credit Note" details={item.creditNote ?? ''} icon={<FaStickyNote />} />
                <DetailField title="Project" details={item.project ?? ''} icon={<FaStickyNote />} />
                <DetailField title="Purchase Order" details={item.purchaseOrder ?? ''} icon={<FaStickyNote />} />
                <DetailField
                  title="Purchase Receipt No"
                  details={item.purchaseReceiptNo ?? ''}
                  icon={<FaStickyNote />}
                />
                <DetailField title="Purpose" details={item.purpose ?? ''} icon={<FaStickyNote />} />
                <DetailField
                  title="Source Address Display"
                  details={item.sourceAddressDisplay ?? ''}
                  icon={<FaStickyNote />}
                />
                <DetailField
                  title="Target Address Display"
                  details={item.targetAddressDisplay ?? ''}
                  icon={<FaStickyNote />}
                />
                <DetailField title="Remarks" details={item.remarks ?? ''} icon={<FaStickyNote />} />
              </div>
            </div>
          </TabPanel>
          <TabPanel>
            <div className="flex flex-col gap-2 divide-y flex-grow ">
              <div className="flex flex-col py-4">
                <span className="font-semibold text-lg">Personal Information</span>
                <DetailField
                  title="Process Loss Percentage"
                  details={item.processLossPercentage?.toString() ?? ''}
                  icon={<FaPiggyBank />}
                />
                <DetailField
                  title="Process Loss Qty"
                  details={item.processLossQty?.toString() ?? ''}
                  icon={<FaPiggyBank />}
                />
                <DetailField
                  title="Total Incoming Value"
                  details={item.totalIncomingValue?.toString() ?? ''}
                  icon={<FaPiggyBank />}
                />
                <DetailField
                  title="Total Outgoing Value"
                  details={item.totalOutgoingValue?.toString() ?? ''}
                  icon={<FaPiggyBank />}
                />
                <DetailField
                  title="Add To Transit"
                  details={item.isAddToTransit ? 'Yes' : 'No' ?? ''}
                  icon={<FaEyeLowVision />}
                />
                <DetailField
                  title="Inspection Required"
                  details={item.isInspectionRequired ? 'Yes' : 'No' ?? ''}
                  icon={<FaEyeLowVision />}
                />
                <DetailField title="Opening" details={item.isOpening ? 'Yes' : 'No' ?? ''} icon={<FaEyeLowVision />} />
                <DetailField title="Return" details={item.isReturn ? 'Yes' : 'No' ?? ''} icon={<FaEyeLowVision />} />
                <DetailField
                  title="Posting Time"
                  details={item.isSetPostingTime ? 'Yes' : 'No' ?? ''}
                  icon={<FaEyeLowVision />}
                />
              </div>
            </div>
          </TabPanel>
        </TabPanels>
      </TabGroup>
    ) : null;
  };

  const ItemDetails = ({ employee }: { employee: Signal<StockEntryDto[]> }) => {
    const item = employee.value[0];
    return (
      <DetailSectionRC headerTitle={item?.name} isSidebarVisible={isSidebarVisible} className="w-full md:w-[40rem]">
        {/* <header className="w-full">
          <div className="flex justify-between gap-4 px-4 py-2">
            <img
              src={`images/profile/${'default_profile.png'}`}
              className="size-28 rounded-full ring"
              alt="not_found"
            />
            <div className="flex flex-col grow items-left">
              <span className="font-semibold text-lg">{`Name: ${item?.name ?? ''}`}</span>
              <span className="inline-flex items-center rounded-md bg-green-50 px-2 py-1 text-sm font-medium text-green-700 ring-1 ring-inset ring-green-600/20">
                {`ID: ${item?.id ?? ''}`}
              </span>
            </div>
          </div>
        </header> */}
        <main className="overflow-y-auto  h-full pt-2">
          <ItemField item={item} />
        </main>
        <footer className="p-2 border-t gap-2 flex flex-row">
          <ButtonRC
            onClick={() => {
              const selectedItem = selectedItems.value[0];
              if (selectedItem && selectedItem.id) {
                navigate(selectedItem.id.toString());
              }
            }}
          >
            <FaPenToSquare />
          </ButtonRC>
          <ButtonRC
            title="Delete"
            onClick={() => {}}
            className="bg-red-100 hover:bg-red-200 text-red-400 hover:text-red-500"
          >
            <FaTrash />
          </ButtonRC>
        </footer>
      </DetailSectionRC>
    );
  };

  return (
    <>
      <div className="flex flex-row w-full h-full items-stretch ">
        <MainSection />
        <ItemDetails employee={selectedItems} />
      </div>
      <SpeedDialRC>
        <SpeedDialNode
          name="Add Stock Entry"
          icon={<FaFileCirclePlus />}
          onClick={() => {
            dialogOpened.value = true;
          }}
        />
      </SpeedDialRC>
      <DialogView />
    </>
  );
};

export default StockEntrysView;
