import { useSignal } from '@vaadin/hilla-react-signals';
import React, { ReactElement, useContext } from 'react';
import { FaPlus } from 'react-icons/fa6';
import { Tooltip as ReactTooltip } from 'react-tooltip';

type ChildrenProps = {
  name: string;
  icon: ReactElement<any, any>;
  onClick?: () => void;
};

interface SpeedDialProps {
  tooltip?: boolean;
  children: React.ReactNode;
}

export const SpeedDialRCContext = React.createContext<boolean>(false);

export const SpeedDialNode: React.FC<ChildrenProps> = ({ name, icon, onClick }) => {
  const tooltip = useContext(SpeedDialRCContext);
  return (
    <li key={`child_${name}`} className="group/item inline-flex items-center hover:font-bold space-x-2">
      {!tooltip && (
        <span className="bg-gray-100 group-hover/item:bg-white font-medium rounded-xl py-1 px-4 border border-gray-200 focus:ring-4 focus:ring-gray-300 focus:outline-none shadow-lg shadow-gray-900/20 cursor-pointer">
          {name}
        </span>
      )}
      <button
        type="button"
        className=" relative w-[52px] h-[52px] bg-gray-100 group-hover/item:bg-white rounded-full border focus:ring-4 focus:ring-gray-300 focus:outline-none shadow-lg shadow-gray-900/20 hover:shadow-gray-900/40"
        data-tooltip-id={`speed_dial_child_${name}`}
        onClick={onClick}
      >
        {icon && React.cloneElement(icon, { className: 'w-5 h-5 mx-auto' })}
        <span className="sr-only">{name}</span>
      </button>
      {tooltip && <ReactTooltip id={`speed_dial_child_${name}`} place="left" content={name} />}
    </li>
  );
};

const SpeedDialRC: React.FC<SpeedDialProps> = ({ children, tooltip = false }) => {
  const isOpen = useSignal(false);
  return (
    <SpeedDialRCContext.Provider value={tooltip}>
      <div className="absolute bottom-5 end-5 group items-end z-50">
        {isOpen.value && <ul className="flex flex-col items-end mb-4 space-y-2">{children}</ul>}
        <div className="flex flex-col items-end">
          <button
            type="button"
            className="flex items-center justify-center text-white bg-pink-600 rounded-full w-14 h-14 hover:bg-pink-500  focus:ring-4 focus:ring-pink-300 focus:outline-none shadow-gray-900/20 hover:shadow-gray-900/40"
            onClick={() => {
              isOpen.value = !isOpen.value;
            }}
          >
            <FaPlus className="w-5 h-5 transition-transform group-hover:rotate-45" />
            <span className="sr-only">Open actions menu</span>
          </button>
        </div>
      </div>
    </SpeedDialRCContext.Provider>
  );
};

export default SpeedDialRC;
