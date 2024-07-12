import React, { useEffect, useState } from 'react';
import { FaChevronUp } from 'react-icons/fa';
import { NavLink } from 'react-router-dom';
import RippleDivRC from '../../../effects/ripple/div/RippleDivRC';

const navCss = `relative flex flex-row items-center font-semibold 
focus:outline-none hover:bg-indigo-100 hover:no-underline rounded-full `;

const navLinkClasses = ({ isActive }: any) => `${isActive ? 'bg-indigo-100 text-indigo-700 ' : ''}${navCss}`;

const NavLinkItem = ({ name, icon, route }: { name: string; icon: any; route: string }) => {
  const handleClick = () => {
    document.title = name;
  };
  return (
    <NavLink className={navLinkClasses} to={route} onClick={handleClick}>
      <RippleDivRC className="w-full h-10 flex rounded-full items-center">
        <span className="inline-flex justify-center items-center ml-4">{icon}</span>
        <span className="ml-2 text-sm tracking-wide truncate">{name}</span>
      </RippleDivRC>
    </NavLink>
  );
};

// Dropdown button component
const DropdownButton = ({
  name,
  icon,
  toggleDropdown,
  route,
}: {
  name: string;
  icon: React.ReactNode;
  toggleDropdown: (state: boolean) => void;
  route: string;
}) => {
  const [isDropdownOpen, setIsDropdownOpen] = useState<boolean>(false);
  useEffect(() => {
    toggleDropdown(isDropdownOpen);
  }, [isDropdownOpen]);
  return (
    <button
      type="button"
      className={`${navCss} w-full flex justify-between items-center h-10 pr-6 ${isDropdownOpen ? ' bg-gray-200' : ''}`}
      onClick={() => {
        setIsDropdownOpen((state) => !state);
      }}
    >
      <div className="flex items-center">
        <span className="inline-flex justify-center items-center ml-4">{icon}</span>
        <NavLink className="ml-2 text-sm tracking-wide truncate hover:underline" to={route}>
          {name}
        </NavLink>
      </div>
      <FaChevronUp className={`transition-transform transform w-3 ${isDropdownOpen ? 'rotate-0' : 'rotate-180'}`} />
    </button>
  );
};

const RNavItemRC = ({
  name,
  icon,
  route,
  isChildren = false,
  children,
}: {
  name: string;
  icon: React.ReactNode;
  route: string;
  isChildren?: boolean;
  children?: React.ReactNode;
}) => {
  const [isDropdownOpen, setIsDropdownOpen] = useState<boolean>(false);
  return (
    <ul
      className={`mx-1 py-1 ml-3 mr-2 ${isChildren && isDropdownOpen ? 'bg-white rounded-2xl border border-gray-100' : ''}`}
    >
      {isChildren ? (
        <DropdownButton
          name={name}
          icon={icon}
          toggleDropdown={(state) => {
            setIsDropdownOpen(state);
          }}
          route={route}
        />
      ) : (
        <li>
          <NavLinkItem name={name} icon={icon} route={route} />
        </li>
      )}
      {isDropdownOpen && children}
    </ul>
  );
};

export default RNavItemRC;
