import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { useForm } from '@vaadin/hilla-react-form';
import { useSignal } from '@vaadin/hilla-react-signals';
import { NumberField } from '@vaadin/react-components';
import ButtonRC from 'Frontend/components/button/regular/ButtonRC';
import SwitchRC from 'Frontend/components/button/regular/SwitchRC';
import FromLayoutRC, { FromColumn, FromRow } from 'Frontend/components/from/from_layout/FromLayoutRC';
import ItemGroupDto from 'Frontend/generated/com/itbd/protisthan/db/dto/ItemGroupDto';
import ItemGroupDtoModel from 'Frontend/generated/com/itbd/protisthan/db/dto/ItemGroupDtoModel';
import Filter from 'Frontend/generated/com/vaadin/hilla/crud/filter/Filter';
import Matcher from 'Frontend/generated/com/vaadin/hilla/crud/filter/PropertyStringFilter/Matcher';
import Pageable from 'Frontend/generated/com/vaadin/hilla/mappedtypes/Pageable';
import { ItemGroupDtoCrudService } from 'Frontend/generated/endpoints';
import Direction from 'Frontend/generated/org/springframework/data/domain/Sort/Direction';
import {
  CategoryDataProvider,
  ItemGroupDataProvider,
  UomDataProvider,
} from 'Frontend/utils/combobox/ComboboxDataProvider';
import { useEffect, useMemo } from 'react';
import { FaSave } from 'react-icons/fa';
import { FaClockRotateLeft } from 'react-icons/fa6';
import { useParams } from 'react-router-dom';

const config: ViewConfig = {
  menu: {
    title: 'Item Group',
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
  item: ItemGroupDto | undefined;
};

const FromView: React.FC<FromViewProps> = ({ item }) => {
  const itemGroupDataProvider = useMemo(() => ItemGroupDataProvider, []);
  const uomDataProvider = useMemo(() => UomDataProvider, []);
  const categoryDataProvider = useMemo(() => CategoryDataProvider, []);
  const { field, model, read, submit, reset, value } = useForm(ItemGroupDtoModel, {
    onSubmit: async (values) => {
      await ItemGroupDtoCrudService.save(values);
    },
  });

  useEffect(() => {
    read(item);
  }, [item]);

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
                  <NumberField label="Name" {...field(model.name)} />
                  <SwitchRC
                    label="Group"
                    checked={value.isGroup}
                    onChange={(e) => {
                      value.isGroup = e;
                      read(value);
                    }}
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

const ItemGroupView = () => {
  const { itemGroupsId } = useParams();
  console.count('UserView');
  const dialogOpened = useSignal(false);
  const editItem = useSignal<ItemGroupDto | undefined>(undefined);

  useEffect(() => {
    ItemGroupDtoCrudService.list(pagination, filterGenerator('and', 'id', itemGroupsId)).then((result) => {
      editItem.value = result ? result[0] : undefined;
    });
  }, [itemGroupsId]);

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

export default ItemGroupView;
