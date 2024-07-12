import type { ViewConfig } from '@vaadin/hilla-file-router/types.js';

export const config: ViewConfig = {
  menu: {
    exclude: true,
  },
};
const ModeView = () => {
  return <div>hello world</div>;
}
export default ModeView;
