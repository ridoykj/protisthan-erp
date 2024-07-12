import { createMenuItems } from '@vaadin/hilla-file-router/runtime.js';
import NavIcons from 'Frontend/components/layout/applayout/constants/NavIcon';
import NavItemRC from 'Frontend/components/layout/applayout/sidebar/NavItemRC';
import { treeBuilder, TreeNode } from 'Frontend/utils/NavTree';
import { memo } from 'react';

const AppNavItem = ({ className }: { className?: string }) => {
  console.log('rendering app main');
  const renderNavItem = (item: TreeNode) => {
    return (
      <NavItemRC
        key={item.name}
        name={item.name}
        icon={item.icon}
        route={item.route}
        isChildren={item.children.length > 0}
      >
        {' '}
        {item.children.map(renderNavItem)}
      </NavItemRC>
    );
  };

  const CreateNavItems = memo(() => {
    const icons = NavIcons;
    const items = createMenuItems().map(({ to, title, icon }) => {
      return {
        name: title ?? '',
        icon: icons[(icon as keyof typeof icons) ?? ''],
        route: to,
      };
    });
    const treeList = treeBuilder(items);
    return treeList[0].children.map(renderNavItem);
  });

  return (
    <div className={`flex flex-col h-full ${className ?? ''}`}>
      <div className="flex flex-row h-16 items-center justify-center">
        <img src="images/eco-factory.png" alt="" className="w-20 p-4" />
        <span
          className="text-4xl text-center font-kalpurush text-indigo-700 font-bold drop-shadow-[0_10px_10px_rgba(255, 255, 255, 0.8)]"
          lang="bn-BD"
        >
          প্রতিষ্ঠান
        </span>
      </div>
      <div className="overflow-y-auto scroll-smooth flex-grow border-y">
        <CreateNavItems />
      </div>
      <div className="sticky inset-x-0 bottom-0 border-r border-gray-100">
        <div className="flex items-center gap-2 bg-white p-4 hover:bg-gray-50">
          <img alt="" src="images/profile/profile.jpg" className="size-10 rounded-full object-cover" />
          <div>
            <p className="text-xs">
              <strong className="block font-medium">Ridoy Kumar Joy</strong>
              <span>ridoykj@gmail.com</span>
            </p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default memo(AppNavItem);
