import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { useForm } from '@vaadin/hilla-react-form';
import { useSignal } from '@vaadin/hilla-react-signals';
import { Button, ComboBox, Grid, GridColumn, TextField } from '@vaadin/react-components';
import ButtonRC from 'Frontend/components/button/regular/ButtonRC';
import SwitchRC from 'Frontend/components/button/regular/SwitchRC';
import FromLayoutRC, { FromColumn, FromRow } from 'Frontend/components/from/from_layout/FromLayoutRC';
import DocPermDto from 'Frontend/generated/com/itbd/protisthan/db/dto/DocPermDto';
import DocPermDtoModel from 'Frontend/generated/com/itbd/protisthan/db/dto/DocPermDtoModel';
import RoleDto from 'Frontend/generated/com/itbd/protisthan/db/dto/RoleDto';
import RoleDtoModel from 'Frontend/generated/com/itbd/protisthan/db/dto/RoleDtoModel';
import Filter from 'Frontend/generated/com/vaadin/hilla/crud/filter/Filter';
import Matcher from 'Frontend/generated/com/vaadin/hilla/crud/filter/PropertyStringFilter/Matcher';
import Pageable from 'Frontend/generated/com/vaadin/hilla/mappedtypes/Pageable';
import { DocPermDtoCrudService, RoleDtoCrudService } from 'Frontend/generated/endpoints';
import Direction from 'Frontend/generated/org/springframework/data/domain/Sort/Direction';
import { DocTypeDataProvider } from 'Frontend/utils/combobox/ComboboxDataProvider';
import { useEffect, useMemo } from 'react';
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
  item: RoleDto | undefined;
};

const RolePermissions: React.FC<FromViewProps> = ({ item }) => {
  const docTypeDataProvider = useMemo(() => DocTypeDataProvider, []);
  const items = useSignal<DocPermDto[]>([]);
  const selectDocType = useSignal<string>('');

  const selectedPermissionGrid = useSignal<DocPermDto[]>([]);

  useEffect(() => {
    if (item?.id) {
      DocPermDtoCrudService.list(pagination, filterGenerator('and', 'id.roleKey', item?.id)).then((result) => {
        items.value = result || [];
      });
    }
  }, [item]);

  const DeleteRander = ({ item: deleteItem }: { item: DocPermDto }) => {
    const { id } = deleteItem;
    return (
      <button
        type="button"
        className="text-red-500 hover:underline"
        title="Delete"
        onClick={async (e) => {
          await DocPermDtoCrudService.delete(id);
        }}
      >
        <span className="sr-only">no-text</span>
        <FaTrash />
      </button>
    );
  };

  const PermissionRender = ({ item: permissionItem }: { item: DocPermDto }) => {
    const { id } = permissionItem;

    const { field, model, read, value, submit } = useForm(DocPermDtoModel, {
      onSubmit: async (values) => {
        values.role = undefined;
        values.docType = undefined;
        await DocPermDtoCrudService.save(values);
      },
    });

    useEffect(() => {
      read(permissionItem);
    }, [permissionItem]);

    return (
      <div className="inline-flex gap-4 ">
        <div className="grid  grid-cols-4 gap-2 grow divide-x divide-y border px-4">
          <SwitchRC
            label="Select"
            checked={value.isSelect}
            onChange={(e) => {
              value.isSelect = e;
              read(value);
            }}
          />
          <SwitchRC
            label="Read"
            checked={value.isRead}
            onChange={(e) => {
              value.isRead = e;
              read(value);
            }}
          />
          <SwitchRC
            label="Write"
            checked={value.isWrite}
            onChange={(e) => {
              value.isWrite = e;
              read(value);
            }}
          />
          <SwitchRC
            label="Create"
            checked={value.isCreate}
            onChange={(e) => {
              value.isCreate = e;
              read(value);
            }}
          />
          <SwitchRC
            label="Delete"
            checked={value.isDelete}
            onChange={(e) => {
              value.isDelete = e;
              read(value);
            }}
          />
          <SwitchRC
            label="Print"
            checked={value.isPrint}
            onChange={(e) => {
              value.isPrint = e;
              read(value);
            }}
          />
          <SwitchRC
            label="Report"
            checked={value.isReport}
            onChange={(e) => {
              value.isReport = e;
              read(value);
            }}
          />
          <SwitchRC
            label="Import"
            checked={value.isImport}
            onChange={(e) => {
              value.isImport = e;
              read(value);
            }}
          />
          <SwitchRC
            label="Export"
            checked={value.isExport}
            onChange={(e) => {
              value.isExport = e;
              read(value);
            }}
          />
        </div>
        <Button onClick={submit}>Update</Button>
      </div>
    );
  };

  return (
    <>
      <div className="inline-flex items-baseline gap-4">
        <ComboBox
          label="Module"
          className="grow"
          dataProvider={docTypeDataProvider}
          itemLabelPath="id"
          itemValuePath="id"
          onValueChanged={(e) => {
            selectDocType.value = e.detail.value;
          }}
        />
        <Button
          onClick={async () => {
            await DocPermDtoCrudService.save({
              id: { docTypeKey: selectDocType.value, roleKey: item?.id },
              role: item,
              docType: { id: selectDocType.value },
            });
          }}
        >
          Add
        </Button>
      </div>
      <Grid
        items={items.value}
        theme="row-stripes"
        rowDetailsRenderer={PermissionRender}
        selectedItems={selectedPermissionGrid.value}
        detailsOpenedItems={selectedPermissionGrid.value}
        onActiveItemChanged={(event) => {
          const permission = event.detail.value;
          selectedPermissionGrid.value = permission ? [permission] : [];
        }}
      >
        <GridColumn path="id.docTypeKey" header="Module" />
        <GridColumn path="Action" header="Action" renderer={DeleteRander} />
      </Grid>
    </>
  );
};

const FromView: React.FC<FromViewProps> = ({ item }) => {
  const { field, model, read, submit, reset, value } = useForm(RoleDtoModel, {
    onSubmit: async (values) => {
      await RoleDtoCrudService.save(values);
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
                  <TextField label="Name" {...field(model.id)} />
                  <SwitchRC
                    label="Disabled"
                    checked={value.isDisabled}
                    onChange={(e) => {
                      value.isDisabled = e;
                      read(value);
                    }}
                  />
                  <SwitchRC
                    label="Bulk Actions"
                    checked={value.isBulkActions}
                    onChange={(e) => {
                      value.isBulkActions = e;
                      read(value);
                    }}
                  />
                  <SwitchRC
                    label="Desk Access"
                    checked={value.isDeskAccess}
                    onChange={(e) => {
                      value.isDeskAccess = e;
                      read(value);
                    }}
                  />
                  <SwitchRC
                    label="Notifications"
                    checked={value.isNotifications}
                    onChange={(e) => {
                      value.isNotifications = e;
                      read(value);
                    }}
                  />
                </FromColumn>
              </FromRow>
              <FromRow>
                <FromColumn>
                  <RolePermissions item={item} />
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

const RoleView = () => {
  const { roleId } = useParams();
  console.count('UserView');
  const dialogOpened = useSignal(false);
  const editItem = useSignal<RoleDto | undefined>(undefined);

  useEffect(() => {
    RoleDtoCrudService.list(pagination, filterGenerator('and', 'id', roleId)).then((result) => {
      editItem.value = result ? result[0] : undefined;
    });
  }, [roleId]);

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

export default RoleView;
