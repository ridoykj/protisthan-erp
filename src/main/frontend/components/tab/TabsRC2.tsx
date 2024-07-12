import { useState } from 'react';

interface TabProps {
  label: string;
  content: React.ReactNode;
}

interface TabsProps {
  tabs: TabProps[];
}

const TabsRC2: React.FC<TabsProps> = ({ tabs }) => {
  const [activeTab, setActiveTab] = useState(0);

  return (
    <div className="w-full">
      <header className="sticky top-0 border-b border-gray-200 bg-green-400">
        {tabs.map((tab, index) => (
          <button
            key={index}
            type="button"
            className={`py-2 px-4 ${
              index === activeTab ? 'border-b-2 border-blue-500 text-blue-500' : 'text-gray-500'
            }`}
            onClick={() => setActiveTab(index)}
          >
            {tab.label}
          </button>
        ))}
      </header>
      <main className="p-4 bg-red-300">{tabs[activeTab].content}</main>
    </div>
  );
};

export default TabsRC2;
