import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { useForm } from '@vaadin/hilla-react-form';
import { useSignal } from '@vaadin/hilla-react-signals';
import { ComboBox, DatePicker, IntegerField, Select, TextField, Upload } from '@vaadin/react-components';
import ButtonRC from 'Frontend/components/button/regular/ButtonRC';
import FromLayoutRC, { FromColumn, FromRow } from 'Frontend/components/from/from_layout/FromLayoutRC';
import BloodGroupEnum from 'Frontend/generated/com/itbd/protisthan/constant/enums/BloodGroupEnum';
import ReligionEnum from 'Frontend/generated/com/itbd/protisthan/constant/enums/ReligionEnum';
import EmployeeDto from 'Frontend/generated/com/itbd/protisthan/db/dto/EmployeeDto';
import EmployeeDtoModel from 'Frontend/generated/com/itbd/protisthan/db/dto/EmployeeDtoModel';
import Filter from 'Frontend/generated/com/vaadin/hilla/crud/filter/Filter';
import Matcher from 'Frontend/generated/com/vaadin/hilla/crud/filter/PropertyStringFilter/Matcher';
import Pageable from 'Frontend/generated/com/vaadin/hilla/mappedtypes/Pageable';
import { EmployeeDtoCrudService } from 'Frontend/generated/endpoints';
import Direction from 'Frontend/generated/org/springframework/data/domain/Sort/Direction';
import {
  CountryDataProvider,
  DepartmentDataProvider,
  DesignationDataProvider,
  GenderDataProvider,
  LanguageDataProvider,
  NameSeriesDataProvider,
} from 'Frontend/utils/combobox/ComboboxDataProvider';
import EnumConversion from 'Frontend/utils/StringUtils';
import { useEffect, useMemo } from 'react';
import { FaSave } from 'react-icons/fa';
import { FaClockRotateLeft } from 'react-icons/fa6';
import { useParams } from 'react-router-dom';

const config: ViewConfig = {
  menu: {
    title: 'Employee',
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
  item: EmployeeDto | undefined;
};

const FromView: React.FC<FromViewProps> = ({ item }) => {
  const { field, model, read, submit, reset } = useForm(EmployeeDtoModel, {
    onSubmit: async (values) => {
      console.log('values', values);
      await EmployeeDtoCrudService.save(values);
    },
  });

  useEffect(() => {
    console.log('editItem.value', item);
    read(item);
  }, [item]);

  const genderDataProvider = useMemo(() => GenderDataProvider, []);
  const countryDataProvider = useMemo(() => CountryDataProvider, []);
  const languageDataProvider = useMemo(() => LanguageDataProvider, []);
  const nameSeriesDataProvider = useMemo(() => NameSeriesDataProvider, []);

  const departmentDataProvider = useMemo(() => DepartmentDataProvider, []);
  const designationDataProvider = useMemo(() => DesignationDataProvider, []);

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
                <FromColumn header="Personal Information">
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
                  <TextField label="First Name" {...field(model.firstName)} />
                  <TextField label="Last Name" {...field(model.lastName)} />
                  {/* <TextField label="Nationality" {...field(model.nationality)} /> */}
                  <ComboBox
                    label="Language"
                    {...field(model.language)}
                    dataProvider={languageDataProvider}
                    itemLabelPath="name"
                    itemValuePath="id"
                  />
                  <ComboBox
                    label="Gender"
                    {...field(model.gender)}
                    dataProvider={genderDataProvider}
                    itemLabelPath="name"
                    itemValuePath="id"
                  />
                  <DatePicker label="Birth Date" {...field(model.birthDate)} />
                  <Select label="Blood Group" {...field(model.bloodGroup)} items={EnumConversion(BloodGroupEnum)} />
                  <Select label="Religion" {...field(model.religion)} items={EnumConversion(ReligionEnum)} />
                </FromColumn>
                <FromColumn header="Contact Information">
                  <TextField label="Email" {...field(model.email)} />
                  <TextField label="Phone" {...field(model.phone)} />
                  <TextField label="Telephone" {...field(model.telephone)} />
                  <ComboBox
                    label="Country"
                    {...field(model.country)}
                    dataProvider={countryDataProvider}
                    itemLabelPath="name"
                    itemValuePath="id"
                  />
                  <TextField label="Present Address" {...field(model.presentAddress)} />
                  <TextField label="Permanent Address" {...field(model.permanentAddress)} />
                </FromColumn>
                <FromColumn header="Family Background">
                  <TextField label="Marital Status" {...field(model.maritalStatus)} />
                  <TextField label="Spouse Name" {...field(model.spouseName)} />
                  <IntegerField label="Children" {...field(model.children)} stepButtonsVisible min={0} max={32} />
                  <TextField label="Father Name" {...field(model.fatherName)} />
                  <TextField label="Mother Name" {...field(model.motherName)} />
                </FromColumn>
              </FromRow>
              <FromRow header="Organization">
                <FromColumn>
                  <ComboBox
                    label="Name Series"
                    {...field(model.namingSeries)}
                    dataProvider={nameSeriesDataProvider}
                    itemLabelPath="name"
                    itemValuePath="id"
                  />
                  <TextField label="Bank Name" {...field(model.permanentAddress)} />
                  <TextField label="Account No" {...field(model.permanentAddress)} />
                  <TextField label="Salary Mode" {...field(model.permanentAddress)} />
                  <TextField label="Salary Currency" {...field(model.permanentAddress)} />
                  <TextField label="Salary" {...field(model.permanentAddress)} />
                  <DatePicker label="Hire Date" {...field(model.hireDate)} />
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
          <div className="h-8" />
        </div>
      </main>
    </>
  );
};

const EmployeeView = () => {
  const { employeeId } = useParams();
  console.count('UserView');
  const dialogOpened = useSignal(false);
  const editItem = useSignal<EmployeeDto | undefined>(undefined);

  useEffect(() => {
    const pagination: Pageable = {
      pageNumber: 0,
      pageSize: 10,
      sort: {
        orders: [{ property: 'id', direction: Direction.ASC, ignoreCase: false }],
      },
    };

    EmployeeDtoCrudService.list(pagination, filterGenerator('and', 'id', employeeId)).then((result) => {
      editItem.value = result ? result[0] : undefined;
    });
  }, [employeeId]);

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

export default EmployeeView;
