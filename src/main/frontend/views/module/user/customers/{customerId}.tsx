import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { useForm } from '@vaadin/hilla-react-form';
import { useSignal } from '@vaadin/hilla-react-signals';
import {
  ComboBox,
  DatePicker,
  IntegerField,
  Select,
  TextField,
  Upload,
  UploadSuccessEvent,
} from '@vaadin/react-components';
import ButtonRC from 'Frontend/components/button/regular/ButtonRC';
import FromLayoutRC, { FromColumn, FromRow } from 'Frontend/components/from/from_layout/FromLayoutRC';
import BloodGroupEnum from 'Frontend/generated/com/itbd/protisthan/constant/enums/BloodGroupEnum';
import ReligionEnum from 'Frontend/generated/com/itbd/protisthan/constant/enums/ReligionEnum';
import CustomerDto from 'Frontend/generated/com/itbd/protisthan/db/dto/CustomerDto';
import CustomerDtoModel from 'Frontend/generated/com/itbd/protisthan/db/dto/CustomerDtoModel';
import Filter from 'Frontend/generated/com/vaadin/hilla/crud/filter/Filter';
import Matcher from 'Frontend/generated/com/vaadin/hilla/crud/filter/PropertyStringFilter/Matcher';
import Pageable from 'Frontend/generated/com/vaadin/hilla/mappedtypes/Pageable';
import { CustomerDtoCrudService } from 'Frontend/generated/endpoints';
import Direction from 'Frontend/generated/org/springframework/data/domain/Sort/Direction';
import {
  CountryDataProvider,
  GenderDataProvider,
  NameSeriesDataProvider,
} from 'Frontend/utils/combobox/ComboboxDataProvider';
import EnumConversion from 'Frontend/utils/StringUtils';
import { useEffect, useMemo } from 'react';
import { FaSave } from 'react-icons/fa';
import { FaClockRotateLeft } from 'react-icons/fa6';
import { useParams } from 'react-router-dom';

const config: ViewConfig = {
  menu: {
    title: 'Customer',
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
  item: CustomerDto | undefined;
};

const FromView: React.FC<FromViewProps> = ({ item }) => {
  const formImage = useSignal<string>('');
  const { value, field, model, read, submit, reset } = useForm(CustomerDtoModel, {
    onSubmit: async (values) => {
      console.log('values', values);
      // const genderId: string = values.gender;
      // const vv: CustomerDto = { ...values, gender: { id: parseInt(genderId, 10) } };
      // await CustomerDtoCrudService.save(vv);

      await CustomerDtoCrudService.save(values);
    },
  });

  useEffect(() => {
    console.log('editItem.value', item);
    read(item);
  }, [item]);

  useEffect(() => {
    if (value.id)
      formImage.value = `v1/content/image?imagePath=${btoa(`/org/user/customer/${value.id}/temp/200/${value.id}.png`)}`;
  }, [value.id]);

  const genderDataProvider = useMemo(() => GenderDataProvider, []);
  const countryDataProvider = useMemo(() => CountryDataProvider, []);
  const nameSeriesDataProvider = useMemo(() => NameSeriesDataProvider, []);

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
                <FromColumn header="Upload">
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
                      headers={`{"path": "/org/user/customer/${value.id || ''}", "filename": "${value.id || ''}.png" }`}
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
                        formImage.value = `v1/content/image?imagePath=${btoa(`/org/user/customer/${value.id}/temp/200/${value.id}.png`)}&mdt=${new Date().getTime().toFixed(0)}`;
                      }}
                    />
                  </div>
                </FromColumn>
                <FromColumn header="Personal Information">
                  <TextField label="First Name" {...field(model.firstName)} />
                  <TextField label="Last Name" {...field(model.lastName)} />
                  {/* <Select label="Gender" {...field(model.gender)} items={EnumConversion(GenderEnum)} /> */}
                  <ComboBox
                    label="Gender"
                    {...field(model.gender)}
                    dataProvider={genderDataProvider}
                    itemLabelPath="name"
                    itemValuePath="id"
                  />
                  <Select label="Religion" {...field(model.religion)} items={EnumConversion(ReligionEnum)} />
                  <DatePicker label="Birth Date" {...field(model.birthDate)} />
                  <Select label="Blood Group" {...field(model.bloodGroup)} items={EnumConversion(BloodGroupEnum)} />
                </FromColumn>
                <FromColumn header="Contact Information">
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
                </FromColumn>
                <FromColumn header="Others">
                  <ComboBox
                    label="Country"
                    {...field(model.country)}
                    dataProvider={countryDataProvider}
                    itemLabelPath="name"
                    itemValuePath="id"
                  />
                  <TextField label="Marital Status" {...field(model.maritalStatus)} />
                  <TextField label="Spouse Name" {...field(model.spouseName)} />
                  <IntegerField label="Children" {...field(model.children)} stepButtonsVisible min={0} max={32} />
                </FromColumn>
              </FromRow>
              <FromRow>
                <FromColumn header="Organization">
                  <ComboBox
                    label="Name Series"
                    {...field(model.namingSeries)}
                    dataProvider={nameSeriesDataProvider}
                    itemLabelPath="name"
                    itemValuePath="id"
                  />
                </FromColumn>
              </FromRow>
            </FromLayoutRC>
          </div>
          <div className="h-8" />
        </div>
      </main>
    </>
  );
};

const CustomerView = () => {
  const { customerId } = useParams();
  console.count('UserView');
  const dialogOpened = useSignal(false);
  const editItem = useSignal<CustomerDto | undefined>(undefined);

  useEffect(() => {
    const pagination: Pageable = {
      pageNumber: 0,
      pageSize: 10,
      sort: {
        orders: [{ property: 'id', direction: Direction.ASC, ignoreCase: false }],
      },
    };
    CustomerDtoCrudService.list(pagination, filterGenerator('and', 'id', customerId)).then((result) => {
      editItem.value = result ? result[0] : undefined;
    });
  }, [customerId]);

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

export default CustomerView;
