import { TabGroup, TabList, TabPanel, TabPanels } from '@headlessui/react';
import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { AutoGrid, AutoGridRef } from '@vaadin/hilla-react-crud';
import { useForm } from '@vaadin/hilla-react-form';
import { Signal, useSignal } from '@vaadin/hilla-react-signals';
import { ComboBox, TextField } from '@vaadin/react-components';
import SpeedDialRC, { SpeedDialNode } from 'Frontend/components/button/fab/SpeedDialRC';
import ButtonRC from 'Frontend/components/button/regular/ButtonRC';
import DialogModalRC from 'Frontend/components/dialog/DialogModalRC';
import DetailSectionRC from 'Frontend/components/from/details/DetailSectionRC';
import FromLayoutRC, { FromColumn, FromRow } from 'Frontend/components/from/from_layout/FromLayoutRC';
import TabRC from 'Frontend/components/tab/TabRC';
import SupplierDto from 'Frontend/generated/com/itbd/protisthan/db/dto/SupplierDto';
import SupplierDtoModel from 'Frontend/generated/com/itbd/protisthan/db/dto/SupplierDtoModel';

import { SupplierDtoCrudService } from 'Frontend/generated/endpoints';
import { CountryDataProvider, SupplierGroupDataProvider } from 'Frontend/utils/combobox/ComboboxDataProvider';
import React, { useMemo } from 'react';
import {
  FaAddressCard,
  FaArrowsRotate,
  FaAt,
  FaEarthAmericas,
  FaFax,
  FaFileCirclePlus,
  FaLockOpen,
  FaMapLocation,
  FaPenToSquare,
  FaPhone,
  FaRegCircleUser,
  FaTrash,
} from 'react-icons/fa6';
import { useLocation, useNavigate } from 'react-router-dom';

const config: ViewConfig = {
  menu: {
    title: 'Supplier',
    icon: 'dice',
  },
};

const SuppliersView = () => {
  console.count('UserView');
  const navigate = useNavigate();
  const location = useLocation();

  const selectedItems = useSignal<SupplierDto[]>([]);
  const dialogOpened = useSignal<boolean>(false);
  const isSidebarVisible = useSignal<boolean>(false);

  const autoGridRef = React.useRef<AutoGridRef>(null);

  function refreshGrid() {
    autoGridRef.current?.refresh();
  }

  const DialogView = () => {
    const supplierGroupDataProvider = useMemo(() => SupplierGroupDataProvider, []);
    const countryDataProvider = useMemo(() => CountryDataProvider, []);
    const { field, model, submit, clear } = useForm(SupplierDtoModel, {
      onSubmit: async (person) => {
        console.log(person);
        await SupplierDtoCrudService.save(person).then(() => {
          dialogOpened.value = false;
          clear();
          refreshGrid();
        });
      },
    });
    return (
      <DialogModalRC headerTitle="Add Supplier" opened={dialogOpened} onSave={submit}>
        <FromLayoutRC>
          <FromRow>
            <FromColumn>
              <ComboBox
                label="Supplier Group"
                {...field(model.supplierGroup)}
                dataProvider={supplierGroupDataProvider}
                itemLabelPath="name"
                itemValuePath="id"
              />
              <TextField label="Company Name" {...field(model.companyName)} />
              <TextField label="Contact Name" {...field(model.contactName)} />
              <TextField label="Contact Title" {...field(model.contactTitle)} />
              <ComboBox
                label="Country"
                {...field(model.country)}
                dataProvider={countryDataProvider}
                itemLabelPath="name"
                itemValuePath="id"
              />
              <TextField label="Full Address" {...field(model.address)} />
              <TextField label="Phone" {...field(model.phone)} />
              <TextField label="Email" {...field(model.email)} />
            </FromColumn>
          </FromRow>
        </FromLayoutRC>
      </DialogModalRC>
    );
  };

  const ItemRender = ({ item }: { item: SupplierDto }) => {
    const { id, companyName } = item;
    return (
      <div className="text-blue-500 inline-flex items-center gap-2">
        <img
          src={`images/profile/${id !== undefined ? 'profile.jpg' : 'default_profile.png'}`}
          className="w-8 h-8 rounded-full"
          alt="not_found"
        />
        {companyName}
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
            service={SupplierDtoCrudService}
            model={SupplierDtoModel}
            className="h-full w-full overflow-auto bg-white/40"
            selectedItems={selectedItems.value}
            visibleColumns={[
              'companyName',
              'supplierGroup.name',
              'contactName',
              'contactTitle',
              'country.name',
              'phone',
              'email',
            ]}
            columnOptions={{
              companyName: {
                header: 'Company Name',
                renderer: ItemRender,
              },
              'supplierGroup.name': {
                header: 'Supplier Group',
              },
              'country.name': {
                header: 'Country',
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

  const ItemField = ({ item }: { item: SupplierDto }) => {
    return item ? (
      <TabGroup>
        <TabList className="sticky top-0 w-full px-4 pb-4 bg-gray-50 border-b">
          <div className="flex flex-row bg-gray-200 rounded-lg">
            <TabRC>Details</TabRC>
            <TabRC>Contact</TabRC>
          </div>
        </TabList>
        <TabPanels className="overflow-y-auto scroll-smooth px-4">
          <TabPanel>
            <div className="flex flex-col gap-2 divide-y flex-grow ">
              <div className="flex flex-col py-4">
                <DetailField title="Company Name" details={item.companyName ?? ''} icon={<FaRegCircleUser />} />
                <DetailField title="Supplier Group" details={item.supplierGroup?.name ?? ''} icon={<FaLockOpen />} />
                <DetailField title="Contact Title" details={item.contactName ?? ''} icon={<FaAddressCard />} />
                <DetailField title="Contact Title" details={item.contactTitle ?? ''} icon={<FaAddressCard />} />
                <DetailField title="Country" details={item.country?.name ?? ''} icon={<FaEarthAmericas />} />
                <DetailField title="City" details={item.city ?? ''} icon={<FaMapLocation />} />
                <DetailField title="Postal Code" details={item.postalCode ?? ''} icon={<FaMapLocation />} />
                <DetailField title="Full Address" details={item.address ?? ''} icon={<FaMapLocation />} />
              </div>
            </div>
          </TabPanel>
          <TabPanel>
            <div className="flex flex-col gap-2 divide-y flex-grow ">
              <div className="flex flex-col py-4">
                <DetailField title="Phone" details={item.phone ?? ''} icon={<FaPhone />} />
                <DetailField title="Email" details={item.email ?? ''} icon={<FaAt />} />
                <DetailField title="Fax" details={item.fax ?? ''} icon={<FaFax />} />
                <DetailField title="Homepage" details={item.homepage ?? ''} icon={<FaEarthAmericas />} />
              </div>
            </div>
          </TabPanel>
          <TabPanel>Content 3</TabPanel>
        </TabPanels>
      </TabGroup>
    ) : null;
  };

  const ItemDetails = ({ employee }: { employee: Signal<SupplierDto[]> }) => {
    const item = employee.value[0];
    return (
      <DetailSectionRC
        headerTitle={item?.companyName}
        isSidebarVisible={isSidebarVisible}
        className="w-full md:w-[40rem]"
      >
        <header className="w-full">
          <div className="flex justify-between gap-4 px-4 py-2">
            <img
              src={`images/profile/${'default_profile.png'}`}
              className="size-28 rounded-full ring"
              alt="not_found"
            />
            <div className="flex flex-col grow items-left">
              <span className="font-semibold text-lg">{`Name: ${item?.companyName ?? ''}`}</span>
              <span className="inline-flex items-center rounded-md bg-green-50 px-2 py-1 text-sm font-medium text-green-700 ring-1 ring-inset ring-green-600/20">
                {`${item?.address ?? ''}`}
              </span>
            </div>
          </div>
        </header>
        <main className="overflow-y-auto  h-full">
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
          name="Add Supplier"
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

export default SuppliersView;
