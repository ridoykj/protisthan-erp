import { ComboBoxDataProviderParams } from '@vaadin/react-components';
import Filter from 'Frontend/generated/com/vaadin/hilla/crud/filter/Filter';
import PropertyStringFilter from 'Frontend/generated/com/vaadin/hilla/crud/filter/PropertyStringFilter';
import Pageable from 'Frontend/generated/com/vaadin/hilla/mappedtypes/Pageable';

export function comboBoxLazyFilter(
  comboBoxDataProviderParams: ComboBoxDataProviderParams,
  type: string,
  property: PropertyStringFilter[]
) {
  const { page, pageSize, filter } = comboBoxDataProviderParams;
  const pagination: Pageable = {
    pageNumber: page,
    pageSize,
    sort: {
      orders: [],
    },
  };

  const filters: Filter = {
    '@type': type,
    children: property,
  };

  return { pagination, filters };
}

export function filterById(type: string, property: PropertyStringFilter[]) {
  const pagination: Pageable = {
    pageNumber: 0,
    pageSize: 1,
    sort: {
      orders: [],
    },
  };

  const filters: Filter = {
    '@type': type,
    children: property,
  };

  return { pagination, filters };
}
