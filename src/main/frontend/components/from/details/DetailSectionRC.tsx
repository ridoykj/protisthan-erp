import { Signal } from '@vaadin/hilla-react-signals';
import ButtonRC from 'Frontend/components/button/regular/ButtonRC';
import React from 'react';
import { FaXmark } from 'react-icons/fa6';

const DetailSectionRC: React.FC<{
  headerTitle?: string;
  children: React.ReactNode;
  isSidebarVisible: Signal<boolean>;
  className?: string;
}> = ({ headerTitle, children, isSidebarVisible, className }) => {
  return (
    <aside
      className={`overflow-hidden z-30 shadow-2xl duration-300 fixed md:relative ${isSidebarVisible.value ? className : 'w-0'}`}
    >
      <main className="flex flex-col w-full h-full ">
        <div className="inline-flex justify-between items-center w-full border-b border-l p-1 shadow-sm">
          <span className="font-semibold text-lg pl-2">{headerTitle}</span>
          <ButtonRC
            onClick={() => {
              isSidebarVisible.value = false;
            }}
          >
            <FaXmark size={15} />
          </ButtonRC>
        </div>
        {children}
      </main>
    </aside>
  );
};

export default DetailSectionRC;
