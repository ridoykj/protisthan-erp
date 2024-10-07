import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { useForm } from '@vaadin/hilla-react-form';
import { useSignal } from '@vaadin/hilla-react-signals';
import { ComboBox, NumberField, TextField, Upload, UploadSuccessEvent } from '@vaadin/react-components';
import ButtonRC from 'Frontend/components/button/regular/ButtonRC';
import SwitchRC from 'Frontend/components/button/regular/SwitchRC';
import FromLayoutRC, { FromColumn, FromRow } from 'Frontend/components/from/from_layout/FromLayoutRC';
import ItemDto from 'Frontend/generated/com/itbd/protisthan/db/dto/ItemDto';
import ItemDtoModel from 'Frontend/generated/com/itbd/protisthan/db/dto/ItemDtoModel';
import Filter from 'Frontend/generated/com/vaadin/hilla/crud/filter/Filter';
import Matcher from 'Frontend/generated/com/vaadin/hilla/crud/filter/PropertyStringFilter/Matcher';
import Pageable from 'Frontend/generated/com/vaadin/hilla/mappedtypes/Pageable';
import { ItemDtoCrudService } from 'Frontend/generated/endpoints';
import Direction from 'Frontend/generated/org/springframework/data/domain/Sort/Direction';
import {
  CategoryDataProvider,
  ItemGroupDataProvider,
  UomDataProvider,
} from 'Frontend/utils/combobox/ComboboxDataProvider';
import { useEffect, useMemo, useRef } from 'react';
import { FaSave } from 'react-icons/fa';
import { FaClockRotateLeft } from 'react-icons/fa6';
import { useParams } from 'react-router-dom';

const config: ViewConfig = {
  menu: {
    title: 'Customer',
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
  item: ItemDto | undefined;
};

const FromView: React.FC<FromViewProps> = ({ item }) => {
  const formImage = useSignal<string>('');
  const itemGroupDataProvider = useMemo(() => ItemGroupDataProvider, []);
  const uomDataProvider = useMemo(() => UomDataProvider, []);
  const categoryDataProvider = useMemo(() => CategoryDataProvider, []);
  const { field, model, read, submit, reset, value } = useForm(ItemDtoModel, {
    onSubmit: async (values) => {
      await ItemDtoCrudService.save(values);
    },
  });

  useEffect(() => {
    if (item) read(item);
  }, [item]);

  useEffect(() => {
    if (value.id)
      formImage.value = `v1/content/image?imagePath=${btoa(`/org/inventory/items/${value.id}/temp/200/${value.id}.png`)}`;
  }, [value.id]);

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

      <main>
        <div className="w-full md:px-10 sm:px-0 ">
          <div className="flex flex-col h-full p-2 border rounded-xl shadow-sm max-w-full items-stretch px-3">
            <FromLayoutRC>
              <FromRow header="Details">
                <FromColumn>
                  <TextField label="Name" {...field(model.name)} />
                  <ComboBox
                    label="Uom"
                    {...field(model.saleUom)}
                    dataProvider={uomDataProvider}
                    itemLabelPath="id"
                    itemValuePath="id"
                  />
                  <NumberField label="Unit Price" {...field(model.unitPrice)} />
                  <NumberField label="Sale Price" {...field(model.salePrice)} />
                  <TextField label="Code" {...field(model.code)} />
                  <ComboBox
                    label="Category"
                    {...field(model.category)}
                    dataProvider={categoryDataProvider}
                    itemLabelPath="name"
                    itemValuePath="id"
                  />
                  <ComboBox
                    label="Group"
                    {...field(model.itemGroup)}
                    dataProvider={itemGroupDataProvider}
                    itemLabelPath="name"
                    itemValuePath="id"
                  />
                </FromColumn>
                <FromColumn>
                  <NumberField label="Vat(%)" {...field(model.vat)} />
                  <NumberField label="Service Charge(%)" {...field(model.serviceCharge)} />
                  <NumberField label="Supplementary Duty(%)" {...field(model.supplementaryDuty)} />
                  <NumberField label="Discount(%)" {...field(model.discount)} />
                </FromColumn>
                <FromColumn>
                  <SwitchRC
                    label="Disabled"
                    checked={value.isDisabled}
                    onChange={(e) => {
                      value.isDisabled = e;
                      read(value);
                    }}
                  />
                  <SwitchRC
                    label="Fixed Asset"
                    checked={value.isFixedAsset}
                    onChange={(e) => {
                      value.isFixedAsset = e;
                      read(value);
                    }}
                  />
                  <SwitchRC
                    label="Purchase Item"
                    checked={value.isPurchaseItem}
                    onChange={(e) => {
                      value.isPurchaseItem = e;
                      read(value);
                    }}
                  />
                  <SwitchRC
                    label="Sales Item"
                    checked={value.isSalesItem}
                    onChange={(e) => {
                      value.isSalesItem = e;
                      read(value);
                    }}
                  />
                  <SwitchRC
                    label="Stock Item"
                    checked={value.isStockItem}
                    onChange={(e) => {
                      value.isStockItem = e;
                      read(value);
                    }}
                  />
                  <div className="text-blue-500 inline-flex items-center gap-2 border-b p-2">
                    <img
                      src={formImage.value}
                      onError={(e: any) => {
                        e.target.src = `images/profile/default_profile.png`;
                      }}
                      className="size-28 rounded-full ring object-cover"
                      alt="not_found"
                    />
                    <Upload
                      capture="camera"
                      method="POST"
                      target="v1/content/upload/image"
                      headers={`{"path": "/org/inventory/items/${value.id || ''}", "filename": "${value.id || ''}.png" }`}
                      // onUploadBefore={async (e: UploadBeforeEvent) => {
                      //     const file = e.detail.file;
                      //     // e.preventDefault();
                      //     console.log('file', file);
                      //     // if (form.value) {
                      //     //   form.value.avatarBase64 = await readAsDataURL(file);
                      //     // }
                      // }}
                      onUploadBefore={(e) => {
                        console.log('before', e);
                        formImage.value = '';
                        // refreshGrid();
                      }}
                      onUploadSuccess={(e: UploadSuccessEvent) => {
                        const { file } = e.detail;
                        console.log('file s', file);
                        formImage.value = `v1/content/image?imagePath=${btoa(`/org/inventory/items/${value.id}/temp/200/${value.id}.png`)}&mdt=${new Date().getTime().toFixed(0)}`;
                      }}
                    />
                  </div>
                </FromColumn>
                {/* <FromColumn header="Contact Information">
                  <TextField label="Email" {...field(model.email)} />
                  <TextField label="Phone" {...field(model.phone)} />
                  <TextField label="Telephone" {...field(model.telephone)} />
                  <TextField label="Present Address" {...field(model.homeAddress)} />
                  <TextField label="Permanent Address" {...field(model.officeAddress)} />
                </FromColumn>
                <FromColumn header="Family Background">
                  <TextField label="Marital Status" {...field(model.maritalStatus)} />
                  <TextField label="Spouse Name" {...field(model.spouseName)} />
                  <IntegerField label="Children" {...field(model.children)} stepButtonsVisible min={0} max={32} />
                </FromColumn> */}
              </FromRow>
            </FromLayoutRC>
          </div>
          <div className="h-8" />
        </div>
      </main>
    </>
  );
};

const ItemView = () => {
  const { itemId } = useParams();
  console.count('UserView');
  const dialogOpened = useSignal(false);
  const editItem = useSignal<ItemDto | undefined>(undefined);

  useEffect(() => {
    ItemDtoCrudService.list(pagination, filterGenerator('and', 'id', itemId)).then((result) => {
      editItem.value = result ? result[0] : undefined;
    });
  }, [itemId]);

  return (
    <>
      <header />
      <main>
        <FromView item={editItem.value} />
      </main>
      <footer />
    </>
  );
};

export default ItemView;
