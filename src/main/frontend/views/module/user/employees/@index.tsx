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
import EmployeeDto from 'Frontend/generated/com/itbd/protisthan/db/dto/EmployeeDto';
import EmployeeDtoModel from 'Frontend/generated/com/itbd/protisthan/db/dto/EmployeeDtoModel';
import { EmployeeDtoCrudService } from 'Frontend/generated/endpoints';
import {
  CountryDataProvider,
  DepartmentDataProvider,
  DesignationDataProvider,
  GenderDataProvider,
} from 'Frontend/utils/combobox/ComboboxDataProvider';
import React, { useMemo } from 'react';
import { FaCalendarDay, FaHome, FaMoneyBillAlt, FaPhone, FaSpa, FaTransgenderAlt, FaUserPlus } from 'react-icons/fa';
import {
  FaArrowsRotate,
  FaAt,
  FaCalendarDays,
  FaChild,
  FaCoins,
  FaDiaspora,
  FaDollarSign,
  FaDroplet,
  FaIdCard,
  FaLanguage,
  FaMobile,
  FaPenToSquare,
  FaPiggyBank,
  FaRegCircleUser,
  FaRegCreditCard,
  FaTrash,
  FaUser,
} from 'react-icons/fa6';
import { useLocation, useNavigate } from 'react-router-dom';

const config: ViewConfig = {
  menu: {
    title: 'Employee',
    icon: 'dice',
  },
};

const EmployeesView = () => {
  console.count('UserView');
  const navigate = useNavigate();
  const location = useLocation();

  const selectedItems = useSignal<EmployeeDto[]>([]);

  const isSidebarVisible = useSignal<boolean>(false);
  const dialogOpened = useSignal<boolean>(false);

  const autoGridRef = React.useRef<AutoGridRef>(null);

  function refreshGrid() {
    autoGridRef.current?.refresh();
  }
  const genderDataProvider = useMemo(() => GenderDataProvider, []);
  const countryDataProvider = useMemo(() => CountryDataProvider, []);
  const departmentDataProvider = useMemo(() => DepartmentDataProvider, []);
  const designationDataProvider = useMemo(() => DesignationDataProvider, []);

  const DialogView = () => {
    const { field, model, submit, clear } = useForm(EmployeeDtoModel, {
      onSubmit: async (person) => {
        console.log(person);
        await EmployeeDtoCrudService.save(person).then(() => {
          // dialogOpened.value = false;
          clear();
          refreshGrid();
        });
      },
    });
    return (
      <DialogModalRC headerTitle="Add Employee" opened={dialogOpened} onSave={submit}>
        <FromLayoutRC>
          <FromRow>
            <FromColumn>
              <TextField label="Employee ID" {...field(model.employeeId)} />
              <TextField label="First Name" {...field(model.firstName)} />
              <TextField label="Last Name" {...field(model.lastName)} />
              <ComboBox
                label="Country"
                {...field(model.country)}
                dataProvider={countryDataProvider}
                itemLabelPath="name"
                itemValuePath="id"
              />
              {/* <DatePicker label="Birth Date" {...field(model.birthDate)} /> */}
              <ComboBox
                label="Gender"
                {...field(model.gender)}
                dataProvider={genderDataProvider}
                itemLabelPath="name"
                itemValuePath="id"
              />
              {/* <TextField label="Phone" {...field(model.phone)} /> */}
              <ComboBox
                label="Department"
                {...field(model.department)}
                dataProvider={departmentDataProvider}
                itemLabelPath="name"
                itemValuePath="id"
              />
              <ComboBox
                label="Designation"
                {...field(model.designation)}
                dataProvider={designationDataProvider}
                itemLabelPath="name"
                itemValuePath="id"
              />
              <DatePicker label="Hire Date" {...field(model.hireDate)} />
            </FromColumn>
          </FromRow>
        </FromLayoutRC>
      </DialogModalRC>
    );
  };

  const ItemRender = ({ item }: { item: EmployeeDto }) => {
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
        <header className="sticky top-0 z-10 pb-2 w-full">
          <div className="inline-flex justify-end space-x-2 w-full p-1 text-sm md:px-10 sm:px-0 bg-gray-50 shadow-sm">
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
            service={EmployeeDtoCrudService}
            model={EmployeeDtoModel}
            className="h-full w-full overflow-auto bg-white/40"
            selectedItems={selectedItems.value}
            visibleColumns={['firstName', 'email', 'birthDate', 'gender.name', 'phone']}
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

  const ItemField = ({ item }: { item: EmployeeDto }) => {
    return item ? (
      <TabGroup>
        <TabList className="sticky top-0 w-full px-4 pb-4 bg-gray-50 border-b">
          <div className="flex flex-row bg-gray-200 rounded-lg">
            <TabRC>Details</TabRC>
            <TabRC>Organization</TabRC>
            {/* <TabRC>Others</TabRC> */}
          </div>
        </TabList>
        <TabPanels className="overflow-y-auto scroll-smooth px-4">
          <TabPanel>
            <div className="flex flex-col gap-2 divide-y flex-grow ">
              <div className="flex flex-col py-4">
                <span className="font-semibold text-lg">Personal Information</span>
                <DetailField
                  title="Full Name"
                  details={`${item.firstName} ${item.lastName}`}
                  icon={<FaRegCircleUser />}
                />
                <DetailField title="Nationality" details={item.nationality ?? ''} icon={<FaIdCard />} />
                <DetailField title="Language" details={item.language?.name ?? ''} icon={<FaLanguage />} />
                <DetailField title="Gender" details={item.gender?.name ?? ''} icon={<FaTransgenderAlt />} />
                <DetailField title="Age" details={item.firstName ?? ''} icon={<FaCalendarDay />} />
                <DetailField title="Birth Date" details={item.birthDate ?? ''} icon={<FaCalendarDays />} />
                <DetailField title="Blood Group" details={item.bloodGroup ?? ''} icon={<FaDroplet />} />
                <DetailField title="Religion" details={item.religion ?? ''} icon={<FaDroplet />} />
              </div>
              <div className="flex flex-col py-4">
                <span className="font-semibold text-lg">Contact Information</span>
                <DetailField title="Email" details={item.email ?? ''} icon={<FaAt />} />
                <DetailField title="Phone" details={item.phone ?? ''} icon={<FaMobile />} />
                <DetailField title="Telephone" details={item.telephone ?? ''} icon={<FaPhone />} />
                <DetailField title="Present Address" details={item.presentAddress ?? ''} icon={<FaHome />} />
                <DetailField title="Permanent Address" details={item.permanentAddress ?? ''} icon={<FaHome />} />
              </div>
              <div className="flex flex-col py-4">
                <span className="font-semibold text-lg">Family Background</span>
                <DetailField title="Marital Status" details={item.maritalStatus ?? ''} icon={<FaSpa />} />
                <DetailField title="Spouse Name" details={item.spouseName ?? ''} icon={<FaDiaspora />} />
                <DetailField title="Childrens" details={item.children?.toString() ?? ''} icon={<FaChild />} />
                <DetailField title="Father Name" details={item.fatherName ?? ''} icon={<FaUser />} />
                <DetailField title="Mother Name" details={item.motherName ?? ''} icon={<FaUser />} />
              </div>
            </div>
          </TabPanel>
          <TabPanel>
            <div className="flex flex-col gap-2 divide-y flex-grow ">
              <div className="flex flex-col py-4">
                <span className="font-semibold text-lg">Account</span>
                <DetailField title="Bank Name" details={item.bankName ?? ''} icon={<FaPiggyBank />} />
                <DetailField title="Account No" details={item.bankAcNo ?? ''} icon={<FaRegCreditCard />} />
                <DetailField title="Salary Mode" details={item.salaryMode ?? ''} icon={<FaMoneyBillAlt />} />
                <DetailField title="Salary Currency" details={item.salaryCurrency ?? ''} icon={<FaCoins />} />
                <DetailField title="Salary" details={item.salary?.toString() ?? ''} icon={<FaDollarSign />} />
              </div>
            </div>
          </TabPanel>
          <TabPanel>Content 3</TabPanel>
        </TabPanels>
      </TabGroup>
    ) : null;
  };

  const ItemDetails = ({ employee }: { employee: Signal<EmployeeDto[]> }) => {
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
                {`ID: ${item?.employeeId ?? ''}`}
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
          name="Add Employee"
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

export default EmployeesView;
