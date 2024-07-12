// import { AbstractModel } from '@vaadin/hilla-lit-form';
// import { FieldDirective } from '@vaadin/hilla-react-form';
// import React, { Children, memo } from 'react';

// interface ViewRendererProps {
//   children: React.ReactNode;
//   field: FieldDirective;
//   model: AbstractModel;
// }

// interface FromLayoutProps {
//   field: FieldDirective;
//   model: AbstractModel;
// }

// export const FromLayoutContext = React.createContext<FromLayoutProps | undefined>(undefined);

// const columnCss: { [key: number]: string } = {
//   1: 'w-full',
//   2: 'grid grid-rows-1 md:grid-cols-2 gap-4',
//   3: 'grid grid-rows-1 md:grid-cols-2 lg:grid-cols-3 gap-4',
//   4: 'grid grid-rows-1 md:grid-cols-2 lg:grid-cols-4 gap-4',
// };

// export const FromRow: React.FC<{ children: React.ReactNode; header?: string }> = ({ children, header }) => {
//   const childrenCount = Children.count(children);
//   const rowCss = childrenCount <= 3 ? columnCss[childrenCount] : columnCss[4];
//   return (
//     <section className="flex flex-col">
//       {header && <span className="border-b pl-2 font-semibold text-lg">{header}</span>}
//       <div className={rowCss}>{children}</div>
//     </section>
//   );
// };

// export const FromColumn: React.FC<{ children: React.ReactNode; header?: string }> = ({ children, header }) => {
//   return (
//     <section className="flex flex-col border">
//       {header && <span className="border-b pl-2 font-semibold text-lg">{header}</span>}
//       <div className="flex flex-col p-2">{children}</div>
//     </section>
//   );
// };

// const FromLayoutRC: React.FC<ViewRendererProps> = ({ children, field, model }) => {
//   const contextValue = React.useMemo(() => ({ field, model }), [field, model]);
//   return <FromLayoutContext.Provider value={contextValue}>{children}</FromLayoutContext.Provider>;
// };

// export default memo(FromLayoutRC);
