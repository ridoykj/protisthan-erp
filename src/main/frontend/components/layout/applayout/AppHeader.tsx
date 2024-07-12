import { useViewConfig } from '@vaadin/hilla-file-router/runtime.js';
import { effect, signal } from '@vaadin/hilla-react-signals';
import AvatarControlRC from 'Frontend/components/layout/applayout/profile/AvaterControlRC';
import { useEffect, useState } from 'react';
import { FaBars } from 'react-icons/fa';
import { FaXmark } from 'react-icons/fa6';
// import { useRouteMetadata } from "Frontend/util/routing";

interface AppHeaderProps {
  openSideBar: () => void;
}

const defaultTitle = document.title;
const documentTitleSignal = signal('');
effect(() => {
  document.title = documentTitleSignal.value;
});

// Publish for Vaadin to use
(window as any).Vaadin.documentTitleSignal = documentTitleSignal;

const AppHeader = ({ openSideBar }: AppHeaderProps) => {
  // const currentTitle = useRouteMetadata()?.title ?? 'My App';
  const currentTitle = useViewConfig()?.title ?? defaultTitle;
  useEffect(() => {
    documentTitleSignal.value = currentTitle;
  }, [currentTitle]);

  const [iconState, setIconState] = useState(false);

  return (
    <div className="flex flex-row items-center">
      <button
        type="button"
        className="text-white px-4"
        onClick={() => {
          setIconState((e) => !e);
          openSideBar();
        }}
      >
        {iconState ? <FaXmark /> : <FaBars />}
      </button>
      <div className="flex flex-row grow items-center">
        <h1 className="text-white text-xl text-center">{currentTitle.replace(/s$/, '' || '') ?? 'hello'}</h1>
        <div className="grow">
          <AvatarControlRC />
        </div>
      </div>
    </div>
  );
};

export default AppHeader;
