import { ComboBoxDataProviderCallback, ComboBoxDataProviderParams } from '@vaadin/react-components';
import CategoryDtoModel from 'Frontend/generated/com/itbd/protisthan/db/dto/CategoryDtoModel';
import CountryDtoModel from 'Frontend/generated/com/itbd/protisthan/db/dto/CountryDtoModel';
import CustomerDtoModel from 'Frontend/generated/com/itbd/protisthan/db/dto/CustomerDtoModel';
import DepartmentDtoModel from 'Frontend/generated/com/itbd/protisthan/db/dto/DepartmentDtoModel';
import DesignationDtoModel from 'Frontend/generated/com/itbd/protisthan/db/dto/DesignationDtoModel';
import DocTypeDtoModel from 'Frontend/generated/com/itbd/protisthan/db/dto/DocTypeDtoModel';
import ItemDtoModel from 'Frontend/generated/com/itbd/protisthan/db/dto/ItemDtoModel';
import ItemGroupDtoModel from 'Frontend/generated/com/itbd/protisthan/db/dto/ItemGroupDtoModel';
import LanguageDtoModel from 'Frontend/generated/com/itbd/protisthan/db/dto/LanguageDtoModel';
import ModeOfPaymentDtoModel from 'Frontend/generated/com/itbd/protisthan/db/dto/ModeOfPaymentDtoModel';
import NameSeriesDtoModel from 'Frontend/generated/com/itbd/protisthan/db/dto/NameSeriesDtoModel';
import RoleDtoModel from 'Frontend/generated/com/itbd/protisthan/db/dto/RoleDtoModel';
import StockEntryTypeDtoModel from 'Frontend/generated/com/itbd/protisthan/db/dto/StockEntryTypeDtoModel';
import SupplierDtoModel from 'Frontend/generated/com/itbd/protisthan/db/dto/SupplierDtoModel';
import SupplierGroupDtoModel from 'Frontend/generated/com/itbd/protisthan/db/dto/SupplierGroupDtoModel';
import UomCategoryDtoModel from 'Frontend/generated/com/itbd/protisthan/db/dto/UomCategoryDtoModel';
import UomDtoModel from 'Frontend/generated/com/itbd/protisthan/db/dto/UomDtoModel';
import PropertyStringFilter from 'Frontend/generated/com/vaadin/hilla/crud/filter/PropertyStringFilter';
import Matcher from 'Frontend/generated/com/vaadin/hilla/crud/filter/PropertyStringFilter/Matcher';
import Pageable from 'Frontend/generated/com/vaadin/hilla/mappedtypes/Pageable';
import {
  CategoryDtoCrudService,
  CountryDtoCrudService,
  CustomerDtoCrudService,
  DepartmentDtoCrudService,
  DesignationDtoCrudService,
  DocTypeDtoCrudService,
  EmployeeDtoCrudService,
  GenderDtoCrudService,
  ItemDtoCrudService,
  ItemGroupDtoCrudService,
  LanguageDtoCrudService,
  ModeOfPaymentDtoCrudService,
  NameSeriesDtoCrudService,
  RoleDtoCrudService,
  StockEntryTypeDtoCrudService,
  SupplierDtoCrudService,
  SupplierGroupDtoCrudService,
  UomCategoryDtoCrudService,
  UomDtoCrudService,
} from 'Frontend/generated/endpoints';
import Direction from 'Frontend/generated/org/springframework/data/domain/Sort/Direction';
import EmployeeDtoModel from 'Frontend/generated/com/itbd/protisthan/db/dto/EmployeeDtoModel';
import { comboBoxLazyFilter } from './comboboxLazyFilterUtil';

export const GenderDataProvider = async (
  params: ComboBoxDataProviderParams,
  callback: ComboBoxDataProviderCallback<CountryDtoModel>
) => {
  const pageable: Pageable = {
    pageNumber: 0,
    pageSize: 10,
    sort: {
      orders: [{ property: 'idx', direction: Direction.ASC, ignoreCase: false }],
    },
  };
  const child: PropertyStringFilter[] = [
    {
      '@type': 'propertyString',
      propertyId: 'name',
      filterValue: params.filter,
      matcher: Matcher.CONTAINS,
    },
  ];
  const { filters } = comboBoxLazyFilter(params, 'and', child);
  return GenderDtoCrudService.list(pageable, filters).then((result: any) => {
    callback(result, result.length);
  });
};

export const CountryDataProvider = async (
  params: ComboBoxDataProviderParams,
  callback: ComboBoxDataProviderCallback<CountryDtoModel>
) => {
  const child: PropertyStringFilter[] = [
    {
      '@type': 'propertyString',
      propertyId: 'name',
      filterValue: params.filter,
      matcher: Matcher.CONTAINS,
    },
  ];
  const { pagination, filters } = comboBoxLazyFilter(params, 'and', child);

  return CountryDtoCrudService.list(pagination, filters).then((result: any) => {
    callback(result, result.length);
  });
};

export const DocTypeDataProvider = async (
  params: ComboBoxDataProviderParams,
  callback: ComboBoxDataProviderCallback<DocTypeDtoModel>
) => {
  const child: PropertyStringFilter[] = [
    {
      '@type': 'propertyString',
      propertyId: 'id',
      filterValue: params.filter,
      matcher: Matcher.CONTAINS,
    },
  ];
  const { pagination, filters } = comboBoxLazyFilter(params, 'and', child);

  return DocTypeDtoCrudService.list(pagination, filters).then((result: any) => {
    callback(result, result.length);
  });
};

export const RoleDataProvider = async (
  params: ComboBoxDataProviderParams,
  callback: ComboBoxDataProviderCallback<RoleDtoModel>
) => {
  const child: PropertyStringFilter[] = [
    {
      '@type': 'propertyString',
      propertyId: 'id',
      filterValue: params.filter,
      matcher: Matcher.CONTAINS,
    },
  ];
  const { pagination, filters } = comboBoxLazyFilter(params, 'and', child);

  return RoleDtoCrudService.list(pagination, filters).then((result: any) => {
    callback(result, result.length);
  });
};

export const DepartmentDataProvider = async (
  params: ComboBoxDataProviderParams,
  callback: ComboBoxDataProviderCallback<DepartmentDtoModel>
) => {
  const child: PropertyStringFilter[] = [
    {
      '@type': 'propertyString',
      propertyId: 'name',
      filterValue: params.filter,
      matcher: Matcher.CONTAINS,
    },
  ];
  const { pagination, filters } = comboBoxLazyFilter(params, 'and', child);

  return DepartmentDtoCrudService.list(pagination, filters).then((result: any) => {
    callback(result, result.length);
  });
};

export const DesignationDataProvider = async (
  params: ComboBoxDataProviderParams,
  callback: ComboBoxDataProviderCallback<DesignationDtoModel>
) => {
  const child: PropertyStringFilter[] = [
    {
      '@type': 'propertyString',
      propertyId: 'name',
      filterValue: params.filter,
      matcher: Matcher.CONTAINS,
    },
  ];
  const { pagination, filters } = comboBoxLazyFilter(params, 'and', child);

  return DesignationDtoCrudService.list(pagination, filters).then((result: any) => {
    callback(result, result.length);
  });
};

export const SupplierGroupDataProvider = async (
  params: ComboBoxDataProviderParams,
  callback: ComboBoxDataProviderCallback<SupplierGroupDtoModel>
) => {
  const child: PropertyStringFilter[] = [
    {
      '@type': 'propertyString',
      propertyId: 'name',
      filterValue: params.filter,
      matcher: Matcher.CONTAINS,
    },
  ];
  const { pagination, filters } = comboBoxLazyFilter(params, 'and', child);

  return SupplierGroupDtoCrudService.list(pagination, filters).then((result: any) => {
    callback(result, result.length);
  });
};

export const LanguageDataProvider = async (
  params: ComboBoxDataProviderParams,
  callback: ComboBoxDataProviderCallback<LanguageDtoModel>
) => {
  const child: PropertyStringFilter[] = [
    {
      '@type': 'propertyString',
      propertyId: 'name',
      filterValue: params.filter,
      matcher: Matcher.CONTAINS,
    },
  ];
  const { pagination, filters } = comboBoxLazyFilter(params, 'and', child);

  return LanguageDtoCrudService.list(pagination, filters).then((result: any) => {
    callback(result, result.length);
  });
};

export const NameSeriesDataProvider = async (
  params: ComboBoxDataProviderParams,
  callback: ComboBoxDataProviderCallback<NameSeriesDtoModel>
) => {
  const child: PropertyStringFilter[] = [
    {
      '@type': 'propertyString',
      propertyId: 'name',
      filterValue: params.filter,
      matcher: Matcher.CONTAINS,
    },
  ];
  const { pagination, filters } = comboBoxLazyFilter(params, 'and', child);

  return NameSeriesDtoCrudService.list(pagination, filters).then((result: any) => {
    callback(result, result.length);
  });
};

export const UomCategoryDataProvider = async (
  params: ComboBoxDataProviderParams,
  callback: ComboBoxDataProviderCallback<UomCategoryDtoModel>
) => {
  const child: PropertyStringFilter[] = [
    {
      '@type': 'propertyString',
      propertyId: 'id',
      filterValue: params.filter,
      matcher: Matcher.CONTAINS,
    },
  ];
  const { pagination, filters } = comboBoxLazyFilter(params, 'and', child);

  return UomCategoryDtoCrudService.list(pagination, filters).then((result: any) => {
    callback(result, result.length);
  });
};

export const UomDataProvider = async (
  params: ComboBoxDataProviderParams,
  callback: ComboBoxDataProviderCallback<UomDtoModel>
) => {
  const child: PropertyStringFilter[] = [
    {
      '@type': 'propertyString',
      propertyId: 'id',
      filterValue: params.filter,
      matcher: Matcher.CONTAINS,
    },
  ];
  const { pagination, filters } = comboBoxLazyFilter(params, 'and', child);

  return UomDtoCrudService.list(pagination, filters).then((result: any) => {
    callback(result, result.length);
  });
};

export const StockEntryTypeDataProvider = async (
  params: ComboBoxDataProviderParams,
  callback: ComboBoxDataProviderCallback<StockEntryTypeDtoModel>
) => {
  const child: PropertyStringFilter[] = [
    {
      '@type': 'propertyString',
      propertyId: 'id',
      filterValue: params.filter,
      matcher: Matcher.CONTAINS,
    },
  ];
  const { pagination, filters } = comboBoxLazyFilter(params, 'and', child);

  return StockEntryTypeDtoCrudService.list(pagination, filters).then((result: any) => {
    callback(result, result.length);
  });
};

export const SupplierDataProvider = async (
  params: ComboBoxDataProviderParams,
  callback: ComboBoxDataProviderCallback<SupplierDtoModel>
) => {
  const child: PropertyStringFilter[] = [
    {
      '@type': 'propertyString',
      propertyId: 'companyName',
      filterValue: params.filter,
      matcher: Matcher.CONTAINS,
    },
  ];
  const { pagination, filters } = comboBoxLazyFilter(params, 'and', child);

  return SupplierDtoCrudService.list(pagination, filters).then((result: any) => {
    callback(result, result.length);
  });
};

export const CustomerDataProvider = async (
  params: ComboBoxDataProviderParams,
  callback: ComboBoxDataProviderCallback<CustomerDtoModel>
) => {
  const child: PropertyStringFilter[] = [
    {
      '@type': 'propertyString',
      propertyId: 'customerId',
      filterValue: params.filter,
      matcher: Matcher.CONTAINS,
    },
  ];
  const { pagination, filters } = comboBoxLazyFilter(params, 'and', child);

  return CustomerDtoCrudService.list(pagination, filters).then((result: any) => {
    callback(result, result.length);
  });
};

export const EmployeeDataProvider = async (
  params: ComboBoxDataProviderParams,
  callback: ComboBoxDataProviderCallback<EmployeeDtoModel>
) => {
  const child: PropertyStringFilter[] = [
    {
      '@type': 'propertyString',
      propertyId: 'employeeId',
      filterValue: params.filter,
      matcher: Matcher.CONTAINS,
    },
  ];
  const { pagination, filters } = comboBoxLazyFilter(params, 'and', child);

  return EmployeeDtoCrudService.list(pagination, filters).then((result: any) => {
    callback(result, result.length);
  });
};

export const ModeOfPaymentDataProvider = async (
  params: ComboBoxDataProviderParams,
  callback: ComboBoxDataProviderCallback<ModeOfPaymentDtoModel>
) => {
  const child: PropertyStringFilter[] = [
    {
      '@type': 'propertyString',
      propertyId: 'name',
      filterValue: params.filter,
      matcher: Matcher.CONTAINS,
    },
  ];
  const { pagination, filters } = comboBoxLazyFilter(params, 'and', child);

  return ModeOfPaymentDtoCrudService.list(pagination, filters).then((result: any) => {
    callback(result, result.length);
  });
};

export const ItemDataProvider = async (
  params: ComboBoxDataProviderParams,
  callback: ComboBoxDataProviderCallback<ItemDtoModel>
) => {
  const { filter, page, pageSize } = params;
  const child: PropertyStringFilter[] = [
    {
      '@type': 'propertyString',
      propertyId: filter.startsWith('#') ? 'code' : 'name',
      filterValue: filter.startsWith('#') ? filter.substring(1, filter.length) : filter,
      matcher: Matcher.CONTAINS,
    },
  ];

  const pagination: Pageable = {
    pageNumber: page,
    pageSize,
    sort: {
      orders: [{ direction: Direction.ASC, property: 'name', ignoreCase: true }],
    },
  };

  const { filters } = comboBoxLazyFilter(params, 'and', child);

  return ItemDtoCrudService.list(pagination, filters).then((result: any) => {
    callback(result, result.length);
  });
};

export const ItemGroupDataProvider = async (
  params: ComboBoxDataProviderParams,
  callback: ComboBoxDataProviderCallback<ItemGroupDtoModel>
) => {
  const child: PropertyStringFilter[] = [
    {
      '@type': 'propertyString',
      propertyId: 'name',
      filterValue: params.filter,
      matcher: Matcher.CONTAINS,
    },
  ];
  const { pagination, filters } = comboBoxLazyFilter(params, 'and', child);

  return ItemGroupDtoCrudService.list(pagination, filters).then((result: any) => {
    callback(result, result.length);
  });
};

export const CategoryDataProvider = async (
  params: ComboBoxDataProviderParams,
  callback: ComboBoxDataProviderCallback<CategoryDtoModel>
) => {
  const child: PropertyStringFilter[] = [
    {
      '@type': 'propertyString',
      propertyId: 'name',
      filterValue: params.filter,
      matcher: Matcher.CONTAINS,
    },
  ];
  const { pagination, filters } = comboBoxLazyFilter(params, 'and', child);

  return CategoryDtoCrudService.list(pagination, filters).then((result: any) => {
    callback(result, result.length);
  });
};
