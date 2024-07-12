// import { AbstractModel } from '@vaadin/hilla-lit-form';
// import { FieldDirective } from '@vaadin/hilla-react-form';
// import FieldRC, { FieldType } from 'Frontend/components/from/from_layout/FieldRC';
// import React, { memo } from 'react';
// import { FromLayoutContext } from './FromLayoutRC';

// export interface ViewNode {
//   type?: 'row' | 'column';
//   item?: FieldType;
//   child?: ViewNode[];
// }

// interface FromLayoutJsonProps {
//   data: ViewNode[];
//   field: FieldDirective;
//   model: AbstractModel;
// }

// const FromLayoutJsonRC: React.FC<FromLayoutJsonProps> = ({ data, field, model }) => {
//   const renderView = (node: ViewNode, key: number): React.ReactNode => {
//     if (node.item) {
//       return (
//         <div key={node.item.fieldName} className="p-2">
//           <FieldRC item={node.item} />
//         </div>
//       );
//     }

//     if (node.child) {
//       const classes = node.type === 'row' ? 'flex flex-row' : 'flex flex-col';
//       return (
//         <div className={`${classes} border p-2 m-2`}>
//           {node.child.map((child, index) => (
//             <>{renderView(child, index)}</>
//           ))}
//         </div>
//       );
//     }
//     return null;
//   };

//   const buildFlatView = data.map((node, index) => renderView(node, index));

//   console.count('FromLayoutRC');
//   const contextValue = React.useMemo(() => ({ field, model }), [field, model]);
//   return (
//     <FromLayoutContext.Provider value={contextValue}>
//       <div>{buildFlatView}</div>
//     </FromLayoutContext.Provider>
//   );
// };

// export default memo(FromLayoutJsonRC);

// // const data: ViewNode[] = [
// //     {
// //         type: 'column',
// //         child: [
// //             {
// //                 type: 'row',
// //                 child: [
// //                     {
// //                         type: 'column',
// //                         child: [
// //                             {
// //                                 item: {
// //                                     fieldType: 'Data',
// //                                     fieldName: 'assign',
// //                                     label: 'Name',
// //                                 }
// //                             },
// //                             {
// //                                 item: {
// //                                     fieldType: 'Data',
// //                                     fieldName: 'name',
// //                                     label: 'Name',
// //                                 }
// //                             },
// //                             {
// //                                 item: {
// //                                     fieldType: 'Data',
// //                                     fieldName: 'comments',
// //                                     label: 'Name',
// //                                 }
// //                             }
// //                         ]
// //                     }
// //                 ]
// //             },
// //             {
// //                 type: 'row',
// //                 child: [
// //                     {
// //                         type: 'column',
// //                         child: [
// //                             {
// //                                 item: {
// //                                     fieldType: 'Data',
// //                                     fieldName: 'email',
// //                                     label: 'Name',
// //                                 }
// //                             },
// //                             {
// //                                 item: {
// //                                     fieldType: 'Data',
// //                                     fieldName: 'firstName',
// //                                     label: 'Name',
// //                                 }
// //                             },
// //                             {
// //                                 item: {
// //                                     fieldType: 'Data',
// //                                     fieldName: 'language',
// //                                     label: 'Name',
// //                                 }
// //                             }
// //                         ]
// //                     }, {
// //                         type: 'column',
// //                         child: [
// //                             {
// //                                 item: {
// //                                     fieldType: 'Data',
// //                                     fieldName: 'owner',
// //                                     label: 'Name',
// //                                 }
// //                             },
// //                             {
// //                                 item: {
// //                                     fieldType: 'Data',
// //                                     fieldName: 'phone',
// //                                     label: 'Name',
// //                                 }
// //                             }
// //                         ]
// //                     }
// //                 ]
// //             }
// //         ]
// //     }
// // ];
