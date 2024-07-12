import { Signal } from '@vaadin/hilla-react-signals';
import { Button, Dialog } from '@vaadin/react-components';
import React from 'react';

const DialogModalRC = ({
  headerTitle,
  children,
  opened,
  onSave,
}: {
  headerTitle: string;
  children: React.ReactNode;
  opened: Signal<boolean>;
  onSave: () => void;
}) => {
  return (
    <Dialog
      headerTitle={headerTitle}
      overlayClass="sm:w-screen md:w-[50vw]"
      modeless
      draggable
      resizable
      opened={opened.value}
      onOpenedChanged={({ detail }) => {
        opened.value = detail.value;
      }}
      footerRenderer={() => (
        <>
          <Button
            onClick={() => {
              // dialogShow.value = false;
              opened.value = false;
            }}
          >
            Cancel
          </Button>
          <Button theme="primary" onClick={onSave}>
            Add
          </Button>
        </>
      )}
    >
      <div className="max-w-full items-stretch w-screen">{children}</div>
    </Dialog>
  );
};

export default DialogModalRC;
