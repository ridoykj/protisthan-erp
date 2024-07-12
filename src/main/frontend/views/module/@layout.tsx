import { useViewConfig } from '@vaadin/hilla-file-router/runtime.js';
import type { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { effect, Signal, signal } from '@vaadin/hilla-react-signals';
import AppHeader from 'Frontend/components/layout/applayout/AppHeader';
import AppNavItem from 'Frontend/components/layout/applayout/AppNavItem';
import Placeholder from 'Frontend/utils/placeholder/Placeholder';
import { Suspense, useEffect, useState } from 'react';
import { Outlet, useLocation, useNavigate } from 'react-router-dom';

const config: ViewConfig = {
  menu: {
    title: 'customer',
    exclude: true,
  },
};

const vaadin = window.Vaadin as {
  documentTitleSignal: Signal<string>;
};

vaadin.documentTitleSignal = signal('');
effect(() => {
  document.title = vaadin.documentTitleSignal.value;
});

const MainLayout = () => {
  console.log('rendering app main layout');
  const currentTitle = useViewConfig()?.title ?? '';
  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    vaadin.documentTitleSignal.value = currentTitle;
  });
  const [isSidebarVisible, setSidebarVisible] = useState(false);

  const showSideNav = () => {
    setSidebarVisible((e) => !e);
  };
  return (
    <div className="flex flex-row h-full bg-slate-50 text-gray-800">
      <aside
        className={`inline-flex overflow-hidden z-50 shadow-2xl h-full duration-300 fixed md:relative ${isSidebarVisible ? 'w-full md:w-96' : 'w-0'}`}
      >
        <AppNavItem className={`bg-gray-100 ${isSidebarVisible ? 'w-3/4 md:w-96' : 'w-0'}`} />
        <button
          type="button"
          className="bg-gray-800/20 w-1/3 md:w-0"
          onClick={showSideNav}
          aria-label="Toggle Sidebar"
        />
      </aside>
      <div className="flex flex-col overflow-hidden w-screen">
        <nav className="bg-indigo-700 p-3">
          <AppHeader openSideBar={showSideNav} />
        </nav>
        <main className="flex-1 overflow-auto ">
          <Suspense fallback={<Placeholder />}>
            <Outlet />
          </Suspense>
        </main>
      </div>
    </div>
  );
};
export default MainLayout;
