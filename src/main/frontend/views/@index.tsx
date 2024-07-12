import { useViewConfig } from '@vaadin/hilla-file-router/runtime.js';
import type { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { effect, signal } from '@vaadin/hilla-react-signals';
import MainLayout from 'Frontend/views/module/@layout';
import { useEffect } from 'react';
import '../main.css';

export const config: ViewConfig = {
  menu: {
    title: 'Main page',
    exclude: true,
  },
};

const defaultTitle = document.title;
const documentTitleSignal = signal('');
effect(() => {
  document.title = documentTitleSignal.value;
});

// Publish for Vaadin to use
(window as any).Vaadin.documentTitleSignal = documentTitleSignal;

const MainView = () => {
  const currentTitle = useViewConfig()?.title ?? defaultTitle;
  useEffect(() => {
    documentTitleSignal.value = currentTitle;
  }, [currentTitle]);

  return <MainLayout />;
};

export default MainView;
