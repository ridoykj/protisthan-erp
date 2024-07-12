import { ViewConfig } from '@vaadin/hilla-file-router/types.js';

const ErrorView = () => {
  return <div>Page not found</div>;
};

export default ErrorView;

export const config: ViewConfig = {
  route: '*',
  title: 'Not Found',
  menu: {
    exclude: true,
  },
};
