import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { useForm } from '@vaadin/hilla-react-form';
import { useSignal } from '@vaadin/hilla-react-signals';
import { Button, ComboBox, Grid, GridColumn, TextField } from '@vaadin/react-components';
import ButtonRC from 'Frontend/components/button/regular/ButtonRC';
import FromLayoutRC, { FromColumn, FromRow } from 'Frontend/components/from/from_layout/FromLayoutRC';
import UserDto from 'Frontend/generated/com/itbd/protisthan/db/dto/UserDto';
import UserDtoModel from 'Frontend/generated/com/itbd/protisthan/db/dto/UserDtoModel';
import UserRoleDto from 'Frontend/generated/com/itbd/protisthan/db/dto/UserRoleDto';
import Filter from 'Frontend/generated/com/vaadin/hilla/crud/filter/Filter';
import Matcher from 'Frontend/generated/com/vaadin/hilla/crud/filter/PropertyStringFilter/Matcher';
import Pageable from 'Frontend/generated/com/vaadin/hilla/mappedtypes/Pageable';
import { UserDtoCrudService, UserRoleDtoCrudService } from 'Frontend/generated/endpoints';
import Direction from 'Frontend/generated/org/springframework/data/domain/Sort/Direction';
import { RoleDataProvider } from 'Frontend/utils/combobox/ComboboxDataProvider';
import React, { useEffect, useMemo } from 'react';

import { FaSave } from 'react-icons/fa';
import { FaClockRotateLeft, FaTrash } from 'react-icons/fa6';
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
  item: UserDto | undefined;
};

const UserRoles: React.FC<FromViewProps> = ({ item }) => {
  const roleDataProvider = useMemo(() => RoleDataProvider, []);
  const selectRole = useSignal<string>('');
  const items = useSignal<UserRoleDto[]>([]);
  const selectedPermissionGrid = useSignal<UserRoleDto[]>([]);

  useEffect(() => {
    if (item?.id) {
      UserRoleDtoCrudService.list(pagination, filterGenerator('and', 'id.userKey', item?.id.toString())).then(
        (result) => {
          items.value = result || [];
        }
      );
    }
  }, [item]);

  const DeleteRander = ({ item: deleteItem }: { item: UserRoleDto }) => {
    const { id } = deleteItem;
    return (
      <button
        type="button"
        className="text-red-500 hover:underline"
        title="Delete"
        onClick={async (e) => {
          await UserRoleDtoCrudService.delete(id);
        }}
      >
        <span className="sr-only">no-text</span>
        <FaTrash />
      </button>
    );
  };

  return (
    <>
      <div className="inline-flex items-baseline gap-4">
        <ComboBox
          label="Module"
          className="grow"
          dataProvider={roleDataProvider}
          itemLabelPath="id"
          itemValuePath="id"
          onValueChanged={(e) => {
            selectRole.value = e.detail.value;
          }}
        />
        <Button
          onClick={async () => {
            await UserRoleDtoCrudService.save({
              id: { userKey: item?.id, roleKey: selectRole.value },
              user: { id: item?.id },
              role: { id: selectRole.value },
              // roles: [{ id: selectRole.value }],
            }).then(() => {
              // items.value = [...items.value, { id: { userKey: item?.id, roleKey: selectRole.value } }];
            });
          }}
        >
          Add
        </Button>
      </div>
      <Grid
        items={items.value}
        theme="row-stripes"
        selectedItems={selectedPermissionGrid.value}
        detailsOpenedItems={selectedPermissionGrid.value}
        onActiveItemChanged={(event) => {
          const permission = event.detail.value;
          selectedPermissionGrid.value = permission ? [permission] : [];
        }}
      >
        <GridColumn path="id.roleKey" header="Role" />
        <GridColumn path="Action" header="Action" renderer={DeleteRander} />
      </Grid>
    </>
  );
};

const FromView: React.FC<FromViewProps> = ({ item }) => {
  const { field, model, read, submit, reset } = useForm(UserDtoModel, {
    onSubmit: async (values) => {
      await UserDtoCrudService.save(values);
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
                  <TextField label="Full Name" {...field(model.fullName)} />
                  <TextField label="Username" {...field(model.username)} />
                  <TextField label="Birth Date" {...field(model.birthDate)} />
                  <TextField label="Phone" {...field(model.phone)} />
                </FromColumn>
              </FromRow>
              <FromRow>
                <FromColumn>
                  <UserRoles item={item} />
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

const UserView = () => {
  const { userId } = useParams();
  console.count('UserView');
  const dialogOpened = useSignal(false);
  const editItem = useSignal<UserDto | undefined>(undefined);

  useEffect(() => {
    UserDtoCrudService.list(pagination, filterGenerator('and', 'id', userId)).then((result) => {
      editItem.value = result ? result[0] : undefined;
    });
  }, [userId]);

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

export default UserView;
