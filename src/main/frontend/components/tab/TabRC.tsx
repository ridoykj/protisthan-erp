import { Tab } from '@headlessui/react';

export const TabRC = ({ children }: { children: React.ReactNode }) => {
  return (
    <Tab className="w-full rounded-lg  py-1 m-1 px-3 text-sm/6 font-semibold text-gray-500 focus:outline-none data-[selected]:text-gray-800 data-[selected]:bg-white/80 data-[hover]:bg-white/80 data-[selected]:data-[hover]:bg-white/100 data-[focus]:outline-1 data-[focus]:outline-white">
      {children}
    </Tab>
  );
};

export default TabRC;
