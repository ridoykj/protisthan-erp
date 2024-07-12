import { TabGroup, TabList, TabPanel, TabPanels } from '@headlessui/react';
import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { AutoGrid, AutoGridRef } from '@vaadin/hilla-react-crud';
import { useForm } from '@vaadin/hilla-react-form';
import { Signal, useSignal } from '@vaadin/hilla-react-signals';
import { TextField } from '@vaadin/react-components';
import SpeedDialRC, { SpeedDialNode } from 'Frontend/components/button/fab/SpeedDialRC';
import ButtonRC from 'Frontend/components/button/regular/ButtonRC';
import DialogModalRC from 'Frontend/components/dialog/DialogModalRC';
import DetailSectionRC from 'Frontend/components/from/details/DetailSectionRC';
import FromLayoutRC, { FromColumn, FromRow } from 'Frontend/components/from/from_layout/FromLayoutRC';
import TabRC from 'Frontend/components/tab/TabRC';
import ItemGroupDto from 'Frontend/generated/com/itbd/protisthan/db/dto/ItemGroupDto';
import ItemGroupDtoModel from 'Frontend/generated/com/itbd/protisthan/db/dto/ItemGroupDtoModel';

import { ItemGroupDtoCrudService } from 'Frontend/generated/endpoints';
import {
  CategoryDataProvider,
  ItemGroupDataProvider,
  UomDataProvider,
} from 'Frontend/utils/combobox/ComboboxDataProvider';
import React, { useMemo } from 'react';
import {
  FaAddressBook,
  FaArrowsRotate,
  FaBagShopping,
  FaFileCirclePlus,
  FaPenToSquare,
  FaScaleUnbalanced,
  FaTrash,
} from 'react-icons/fa6';
import { useLocation, useNavigate } from 'react-router-dom';

const config: ViewConfig = {
  menu: {
    title: 'Item Group',
    icon: 'dice',
  },
};

const ItemGroupsView = () => {
  console.count('UserView');
  const navigate = useNavigate();
  const location = useLocation();

  const selectedItems = useSignal<ItemGroupDto[]>([]);
  const dialogOpened = useSignal<boolean>(false);
  const isSidebarVisible = useSignal<boolean>(false);

  const autoGridRef = React.useRef<AutoGridRef>(null);

  function refreshGrid() {
    autoGridRef.current?.refresh();
  }

  const DialogView = () => {
    const itemGroupDataProvider = useMemo(() => ItemGroupDataProvider, []);
    const uomDataProvider = useMemo(() => UomDataProvider, []);
    const categoryDataProvider = useMemo(() => CategoryDataProvider, []);

    const { field, model, submit, clear } = useForm(ItemGroupDtoModel, {
      onSubmit: async (person) => {
        console.log(person);
        await ItemGroupDtoCrudService.save(person).then(() => {
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
              {/* <NumberField label="Unit Price" {...field(model.unitPrice)} />
              <NumberField label="Sale Price" {...field(model.salePrice)} />
              <TextField label="Code" {...field(model.code)} />
              <ComboBox
                label="Category"
                {...field(model.category)}
                dataProvider={categoryDataProvider}
                itemLabelPath="id"
                itemValuePath="id"
              />
              <ComboBox
                label="Group"
                {...field(model.itemGroup)}
                dataProvider={itemGroupDataProvider}
                itemLabelPath="id"
                itemValuePath="id"
              />
              <ComboBox
                label="Uom"
                {...field(model.saleUom)}
                dataProvider={uomDataProvider}
                itemLabelPath="id"
                itemValuePath="id"
              /> */}
            </FromColumn>
          </FromRow>
        </FromLayoutRC>
      </DialogModalRC>
    );
  };

  const ItemRender = ({ item }: { item: ItemGroupDto }) => {
    const { id, name } = item;
    return (
      <div className="text-blue-500 inline-flex items-center gap-2">
        <FaBagShopping className="text-gray-600 size-8 ring-2 ring-gray-300 p-1 rounded-full" />
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
            service={ItemGroupDtoCrudService}
            model={ItemGroupDtoModel}
            className="h-full w-full overflow-auto bg-white/40"
            selectedItems={selectedItems.value}
            visibleColumns={['name', 'isGroup', 'parentItemGroup.name']}
            columnOptions={{
              name: {
                header: 'Name',
                renderer: ItemRender,
              },
              'parentItemGroup.name': {
                header: 'Parent Item Group',
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

  const ItemField = ({ item }: { item: ItemGroupDto }) => {
    return item ? (
      <TabGroup>
        <TabList className="sticky top-0 w-full px-4 pb-4 bg-gray-50 border-b">
          <div className="flex flex-row bg-gray-200 rounded-lg">
            <TabRC>Details</TabRC>
          </div>
        </TabList>
        <TabPanels className="overflow-y-auto scroll-smooth px-4">
          <TabPanel>
            <div className="flex flex-col gap-2 divide-y flex-grow ">
              <div className="flex flex-col py-4">
                <span className="font-semibold text-lg">Personal Information</span>
                <DetailField title="Name" details={item.name ?? ''} icon={<FaAddressBook />} />
                <DetailField title="Group" details={item.isGroup ? 'Yes' : 'No' ?? ''} icon={<FaScaleUnbalanced />} />
                <DetailField title="UOM" details={item.parentItemGroup?.name ?? ''} icon={<FaScaleUnbalanced />} />
              </div>
            </div>
          </TabPanel>
        </TabPanels>
      </TabGroup>
    ) : null;
  };

  const ItemDetails = ({ employee }: { employee: Signal<ItemGroupDto[]> }) => {
    const item = employee.value[0];
    return (
      <DetailSectionRC headerTitle={item?.name} isSidebarVisible={isSidebarVisible} className="w-full md:w-[40rem]">
        <header className="w-full">
          <div className="flex justify-center gap-4 px-4 py-2">
            <img
              src={`images/profile/${'default_profile.png'}`}
              className="size-28 rounded-full ring"
              alt="not_found"
            />
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
          name="Add Item"
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

export default ItemGroupsView;
