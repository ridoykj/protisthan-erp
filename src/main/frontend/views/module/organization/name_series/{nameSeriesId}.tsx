import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { useForm } from '@vaadin/hilla-react-form';
import { useSignal } from '@vaadin/hilla-react-signals';
import { Select, TextField } from '@vaadin/react-components';
import ButtonRC from 'Frontend/components/button/regular/ButtonRC';
import FromLayoutRC, { FromColumn, FromRow } from 'Frontend/components/from/from_layout/FromLayoutRC';
import NameSeriesEnum from 'Frontend/generated/com/itbd/protisthan/constant/enums/NameSeriesEnum';
import NameSeriesDto from 'Frontend/generated/com/itbd/protisthan/db/dto/NameSeriesDto';
import NameSeriesDtoModel from 'Frontend/generated/com/itbd/protisthan/db/dto/NameSeriesDtoModel';
import Filter from 'Frontend/generated/com/vaadin/hilla/crud/filter/Filter';
import Matcher from 'Frontend/generated/com/vaadin/hilla/crud/filter/PropertyStringFilter/Matcher';
import Pageable from 'Frontend/generated/com/vaadin/hilla/mappedtypes/Pageable';
import { NameSeriesDtoCrudService } from 'Frontend/generated/endpoints';
import Direction from 'Frontend/generated/org/springframework/data/domain/Sort/Direction';
import EnumConversion from 'Frontend/utils/StringUtils';
import { useEffect } from 'react';
import { FaSave } from 'react-icons/fa';
import { FaClockRotateLeft } from 'react-icons/fa6';
import { useParams } from 'react-router-dom';

const config: ViewConfig = {
  menu: {
    title: 'Name Series',
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
  item: NameSeriesDto | undefined;
};

const FromView: React.FC<FromViewProps> = ({ item }) => {
  const { field, model, read, submit, reset } = useForm(NameSeriesDtoModel, {
    onSubmit: async (values) => {
      await NameSeriesDtoCrudService.save(values);
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
                {/* <FromColumn header="Upload">
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
                </FromColumn> */}
                <FromColumn>
                  <TextField label="Name" {...field(model.name)} />
                  <TextField label="Short Name" {...field(model.shortName)} />
                  <Select label="Type" {...field(model.type)} items={EnumConversion(NameSeriesEnum)} />
                  <TextField label="Description" {...field(model.description)} />
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

const NameSeriesView = () => {
  const { nameSeriesId } = useParams();
  console.count('UserView');
  const dialogOpened = useSignal(false);
  const editItem = useSignal<NameSeriesDto | undefined>(undefined);

  useEffect(() => {
    NameSeriesDtoCrudService.list(pagination, filterGenerator('and', 'id', nameSeriesId)).then((result) => {
      editItem.value = result ? result[0] : undefined;
    });
  }, [nameSeriesId]);

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

export default NameSeriesView;
