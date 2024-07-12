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
import UomConversionFactorDto from 'Frontend/generated/com/itbd/protisthan/db/dto/UomConversionFactorDto';

import UomConversionFactorDtoModel from 'Frontend/generated/com/itbd/protisthan/db/dto/UomConversionFactorDtoModel';

import { UomConversionFactorDtoCrudService } from 'Frontend/generated/endpoints';
import { UomCategoryDataProvider } from 'Frontend/utils/combobox/ComboboxDataProvider';
import React, { useMemo } from 'react';
import {
  FaArrowsRotate,
  FaCalculator,
  FaFileCirclePlus,
  FaPenToSquare,
  FaRegSnowflake,
  FaScaleBalanced,
  FaTrash,
} from 'react-icons/fa6';
import { useLocation, useNavigate } from 'react-router-dom';

const config: ViewConfig = {
  menu: {
    title: 'Uom Conversion Factor',
    icon: 'dice',
  },
};

const UomConversionFactorsView = () => {
  console.count('UserView');
  const navigate = useNavigate();
  const location = useLocation();

  const selectedItems = useSignal<UomConversionFactorDto[]>([]);
  const dialogOpened = useSignal<boolean>(false);
  const isSidebarVisible = useSignal<boolean>(false);

  const autoGridRef = React.useRef<AutoGridRef>(null);

  function refreshGrid() {
    autoGridRef.current?.refresh();
  }

  const DialogView = () => {
    const uomCategoryDataProvider = useMemo(() => UomCategoryDataProvider, []);
    const { field, model, submit, clear, value, read } = useForm(UomConversionFactorDtoModel, {
      onSubmit: async (person) => {
        console.log(person);
        await UomConversionFactorDtoCrudService.save(person).then(() => {
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
              <ComboBox
                label="Category"
                {...field(model.category)}
                required
                dataProvider={uomCategoryDataProvider}
                itemLabelPath="id"
                itemValuePath="id"
              />
              <TextField label="From Uom" {...field(model.id.fromUomKey)} />
              <TextField label="To Uom" {...field(model.id.toUomKey)} />
              <TextField label="value" {...field(model.value)} />
            </FromColumn>
          </FromRow>
        </FromLayoutRC>
      </DialogModalRC>
    );
  };

  const ItemRender = ({ item }: { item: UomConversionFactorDto }) => {
    const { id } = item;
    return (
      <div className="text-blue-500 inline-flex items-center gap-2">
        <FaScaleBalanced className="text-gray-600 size-8 ring-2 ring-gray-300 p-1 rounded-full" />
        {item.category?.id ?? ''}
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
            service={UomConversionFactorDtoCrudService}
            model={UomConversionFactorDtoModel}
            className="h-full w-full overflow-auto bg-white/40"
            selectedItems={selectedItems.value}
            visibleColumns={['category.id', 'id.fromUomKey', 'id.toUomKey', 'value']}
            columnOptions={{
              'category.id': {
                header: 'Category',
                renderer: ItemRender,
              },
              value: {
                header: 'Conversion Factor',
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

  const ItemField = ({ item }: { item: UomConversionFactorDto }) => {
    return item ? (
      <TabGroup>
        <TabList className="sticky top-0 w-full px-4 pb-4 bg-gray-50 border-b">
          <div className="flex flex-row bg-gray-200 rounded-lg">
            <TabRC>Details</TabRC>
            {/* <TabRC>Accounts</TabRC>
            <TabRC>Others</TabRC> */}
          </div>
        </TabList>
        <TabPanels className="overflow-y-auto scroll-smooth px-4">
          <TabPanel>
            <div className="flex flex-col gap-2 divide-y flex-grow ">
              <div className="flex flex-col py-4">
                <span className="font-semibold text-lg">Personal Information</span>
                <DetailField title="From Uom" details={item.id?.fromUomKey ?? ''} icon={<FaScaleBalanced />} />
                <DetailField title="To Uom" details={item.id?.toUomKey ?? ''} icon={<FaScaleBalanced />} />
                <DetailField title="Category" details={item.category?.id ?? ''} icon={<FaRegSnowflake />} />
                <DetailField title="Value" details={item.value?.toString() ?? ''} icon={<FaCalculator />} />
              </div>
            </div>
          </TabPanel>
          <TabPanel>Content 2</TabPanel>
          <TabPanel>Content 3</TabPanel>
        </TabPanels>
      </TabGroup>
    ) : null;
  };

  const ItemDetails = ({ employee }: { employee: Signal<UomConversionFactorDto[]> }) => {
    const item = employee.value[0];
    return (
      <DetailSectionRC
        headerTitle={item?.id?.fromUomKey}
        isSidebarVisible={isSidebarVisible}
        className="w-full md:w-[40rem]"
      >
        {/* <header className="w-full">
          <div className="flex justify-between gap-4 px-4 py-2">
            <img
              src={`images/profile/${'default_profile.png'}`}
              className="size-28 rounded-full ring"
              alt="not_found"
            />
            <div className="flex flex-col grow items-left">
              <span className="font-semibold text-lg">{`Name: ${item?.id ?? ''}`}</span>
              <span className="inline-flex items-center rounded-md bg-green-50 px-2 py-1 text-sm font-medium text-green-700 ring-1 ring-inset ring-green-600/20">
                {`ID: ${item?.id ?? ''}`}
              </span>
            </div>
          </div>
        </header> */}
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
          name="Add UOM Conversion Factor"
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

export default UomConversionFactorsView;
