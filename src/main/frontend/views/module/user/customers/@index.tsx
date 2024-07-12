import { TabGroup, TabList, TabPanel, TabPanels } from '@headlessui/react';
import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { AutoGrid, AutoGridRef } from '@vaadin/hilla-react-crud';
import { useForm } from '@vaadin/hilla-react-form';
import { Signal, useSignal } from '@vaadin/hilla-react-signals';
import { ComboBox, DatePicker, TextField } from '@vaadin/react-components';
import SpeedDialRC, { SpeedDialNode } from 'Frontend/components/button/fab/SpeedDialRC';
import ButtonRC from 'Frontend/components/button/regular/ButtonRC';
import DialogModalRC from 'Frontend/components/dialog/DialogModalRC';
import DetailSectionRC from 'Frontend/components/from/details/DetailSectionRC';
import FromLayoutRC, { FromColumn, FromRow } from 'Frontend/components/from/from_layout/FromLayoutRC';
import TabRC from 'Frontend/components/tab/TabRC';
import CustomerDto from 'Frontend/generated/com/itbd/protisthan/db/dto/CustomerDto';
import CustomerDtoModel from 'Frontend/generated/com/itbd/protisthan/db/dto/CustomerDtoModel';
import { CustomerDtoCrudService } from 'Frontend/generated/endpoints';
import { CountryDataProvider, GenderDataProvider } from 'Frontend/utils/combobox/ComboboxDataProvider';
import React, { useMemo } from 'react';
import { FaCalendarDay, FaHome, FaPhone, FaSpa, FaTransgenderAlt, FaUserPlus } from 'react-icons/fa';
import {
  FaArrowsRotate,
  FaAt,
  FaCalendarDays,
  FaChild,
  FaDiaspora,
  FaDroplet,
  FaLayerGroup,
  FaMobile,
  FaPenToSquare,
  FaRegBookmark,
  FaRegCircleUser,
  FaTrash,
} from 'react-icons/fa6';
import { useLocation, useNavigate } from 'react-router-dom';

const config: ViewConfig = {
  menu: {
    title: 'Customers',
    icon: 'dice',
  },
};

const CustomersView = () => {
  console.count('UserView');
  const navigate = useNavigate();
  const location = useLocation();

  const selectedItems = useSignal<CustomerDto[]>([]);

  const dialogOpened = useSignal<boolean>(false);
  const isSidebarVisible = useSignal<boolean>(false);

  const autoGridRef = React.useRef<AutoGridRef>(null);

  function refreshGrid() {
    autoGridRef.current?.refresh();
  }

  const genderDataProvider = useMemo(() => GenderDataProvider, []);
  const countryDataProvider = useMemo(() => CountryDataProvider, []);

  const DialogView = () => {
    const { field, model, submit, clear } = useForm(CustomerDtoModel, {
      onSubmit: async (person) => {
        console.log(person);
        await CustomerDtoCrudService.save(person).then(() => {
          dialogOpened.value = false;
          clear();
          refreshGrid();
        });
      },
    });
    return (
      <DialogModalRC headerTitle="Add Customer" opened={dialogOpened} onSave={submit}>
        <FromLayoutRC>
          <FromRow>
            <FromColumn>
              <TextField label="Customer ID" {...field(model.customerId)} />
              <TextField label="First Name" {...field(model.firstName)} />
              <TextField label="Last Name" {...field(model.lastName)} />
              <DatePicker label="Birth Date" {...field(model.birthDate)} />
              <ComboBox
                label="Gender"
                {...field(model.gender)}
                dataProvider={genderDataProvider}
                itemLabelPath="name"
                itemValuePath="id"
              />
              <ComboBox
                label="Country"
                {...field(model.country)}
                dataProvider={countryDataProvider}
                itemLabelPath="name"
                itemValuePath="id"
              />
              <TextField label="Phone" {...field(model.phone)} />
            </FromColumn>
          </FromRow>
        </FromLayoutRC>
      </DialogModalRC>
    );
  };

  const ItemRender = ({ item }: { item: CustomerDto }) => {
    const { id, firstName, lastName } = item;
    return (
      <div className="text-blue-500 inline-flex items-center gap-2">
        <img
          src={`images/profile/${id !== undefined ? 'profile.jpg' : 'default_profile.png'}`}
          className="w-8 h-8 rounded-full"
          alt="not_found"
        />
        {`${firstName} ${lastName}`}
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
            service={CustomerDtoCrudService}
            model={CustomerDtoModel}
            className="h-full w-full overflow-auto bg-white/40"
            selectedItems={selectedItems.value}
            visibleColumns={['firstName', 'birthDate', 'gender.name', 'phone', 'email']}
            columnOptions={{
              firstName: {
                header: 'Full Name',
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

  const ItemField = ({ item }: { item: CustomerDto }) => {
    function calculateAge(birthDateString: string): number {
      const today = new Date();
      const birthDate = new Date(birthDateString);
      let age = today.getFullYear() - birthDate.getFullYear();
      const m = today.getMonth() - birthDate.getMonth();
      if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
        age--;
      }
      return age;
    }
    return item ? (
      <TabGroup>
        <TabList className="sticky top-0 w-full px-4 pb-4 bg-gray-50 border-b">
          <div className="flex flex-row bg-gray-200 rounded-lg">
            <TabRC>Details</TabRC>
            <TabRC>Others</TabRC>
          </div>
        </TabList>
        <TabPanels className="overflow-y-auto scroll-smooth px-4">
          <TabPanel>
            <div className="flex flex-col gap-2 divide-y flex-grow ">
              <div className="flex flex-col py-4">
                <span className="font-semibold text-lg">Personal Information</span>
                <DetailField
                  title="Full Name"
                  details={` ${item.firstName} ${item.lastName}`}
                  icon={<FaRegCircleUser />}
                />
                <DetailField
                  title="Name Series"
                  details={item.namingSeries?.shortName ?? ''}
                  icon={<FaRegBookmark />}
                />
                <DetailField title="Religion" details={item.religion ?? ''} icon={<FaLayerGroup />} />
                <DetailField title="Gender" details={item.gender?.name ?? ''} icon={<FaTransgenderAlt />} />
                <DetailField
                  title="Age"
                  details={calculateAge(item.birthDate ?? '1970-01-01').toString()}
                  icon={<FaCalendarDay />}
                />
                <DetailField title="Birth Date" details={item.birthDate ?? ''} icon={<FaCalendarDays />} />
                <DetailField title="Blood Group" details={item.bloodGroup ?? ''} icon={<FaDroplet />} />
              </div>
              <div className="flex flex-col py-4">
                <span className="font-semibold text-lg">Contact Information</span>
                <DetailField title="Email" details={item.email ?? ''} icon={<FaAt />} />
                <DetailField title="Phone" details={item.phone ?? ''} icon={<FaMobile />} />
                <DetailField title="Telephone" details={item.telephone ?? ''} icon={<FaPhone />} />
                <DetailField title="Country" details={item.country?.name ?? ''} icon={<FaHome />} />
                <DetailField title="Present Address" details={item.homeAddress ?? ''} icon={<FaHome />} />
                <DetailField title="Permanent Address" details={item.officeAddress ?? ''} icon={<FaHome />} />
              </div>
            </div>
          </TabPanel>
          <TabPanel>
            <div className="flex flex-col gap-2 divide-y flex-grow ">
              <div className="flex flex-col py-4">
                <span className="font-semibold text-lg">Family Background</span>
                <DetailField title="Marital Status" details={item.maritalStatus ?? ''} icon={<FaSpa />} />
                <DetailField title="Spouse Name" details={item.spouseName ?? ''} icon={<FaDiaspora />} />
                <DetailField title="Childrens" details={item.children?.toString() ?? ''} icon={<FaChild />} />
              </div>
            </div>
          </TabPanel>
        </TabPanels>
      </TabGroup>
    ) : null;
  };

  const ItemDetails = ({ employee }: { employee: Signal<CustomerDto[]> }) => {
    const item = employee.value[0];
    return (
      <DetailSectionRC
        headerTitle={item?.firstName}
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
              <span className="font-semibold text-lg">{`Name: ${item?.firstName ?? ''}`}</span>
              <span className="inline-flex items-center rounded-md bg-green-50 px-2 py-1 text-sm font-medium text-green-700 ring-1 ring-inset ring-green-600/20">
                {`ID: ${item?.customerId ?? ''}`}
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
          name="Add Customer"
          icon={<FaUserPlus />}
          onClick={() => {
            dialogOpened.value = true;
          }}
        />
      </SpeedDialRC>
      <DialogView />
    </>
  );
};

export default CustomersView;
