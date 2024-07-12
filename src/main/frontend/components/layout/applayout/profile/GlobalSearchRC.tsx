// import {useNavigate} from 'react-router-dom';
// import {ComboBoxDataProviderParams} from "@vaadin/react-components";
// import Pageable from "@vaadin/hilla-react-crud/types/com/vaadin/hilla/mappedtypes/Pageable";
// import Filter from "@vaadin/hilla-react-crud/types/com/vaadin/hilla/crud/filter/Filter";
// import Direction from "@vaadin/hilla-react-crud/types/org/springframework/data/domain/Sort/Direction";
// import Matcher from "@vaadin/hilla-react-crud/types/com/vaadin/hilla/crud/filter/PropertyStringFilter/Matcher";
//
// function GlobalSearchRC() {
//     const navigate = useNavigate();
//
//     function queryFilter(params: ComboBoxDataProviderParams, fieldName: string): [Pageable, Filter] {
//         const {page, pageSize, filter: filterE} = params;
//
//         const pagination: Pageable = {
//             pageNumber: page,
//             pageSize,
//             sort: {
//                 orders: [{property: 'name', direction: Direction.ASC, ignoreCase: false}],
//             },
//         };
//
//         const filter: Filter = {
//             '@type': 'and',
//             children: [
//                 {
//                     '@type': 'propertyString',
//                     propertyId: fieldName,
//                     filterValue: filterE,
//                     matcher: Matcher.CONTAINS,
//                 },
//             ],
//         };
//         return [pagination, filter];
//     }
//
//     function lazyCount(params: ComboBoxDataProviderParams, count: number) {
//         const size = (params.page + (count < params.pageSize ? 0 : 1)) * params.pageSize;
//         return size + count;
//     }
//
//     // const documentTypeProvider = useMemo(
//     //   () =>
//     //     async (
//     //       params: ComboBoxDataProviderParams,
//     //       callback: ComboBoxDataProviderCallback<RoleProfileDtoModel>
//     //     ) => {
//     //       const [pagination, filter] = queryFilter(params, 'name');
//     //       // DocTypeDtoCrudService.list(pagination, filter).then((result: any) => {
//     //       //   callback(result, lazyCount(params, result.length));
//     //       // });
//     //       callback([], 10)
//     //     },
//     //   []
//     // );
//
//     return (
//         <div>
//             {/*<ComboBox*/}
//             {/*  dataProvider={documentTypeProvider}*/}
//             {/*  // label="Document Type"*/}
//             {/*  itemLabelPath="name"*/}
//             {/*  itemValuePath="name"*/}
//             {/*  className="w-full md:w-80 bg-white rounded-3xl h-10"*/}
//             {/*  clearButtonVisible*/}
//             {/*  onValueChanged={(e) => {*/}
//             {/*    navigate(`/m/${e.detail.value?.toLowerCase().replace(/\s/g, '-')}` ?? '');*/}
//             {/*  }}*/}
//             {/*>*/}
//             {/*  <Icon slot="prefix" icon="vaadin:search" />*/}
//             {/*</ComboBox>*/}
//         </div>
//     );
// }
//
// export default GlobalSearchRC;
