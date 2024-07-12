import { Field, Label, Switch } from '@headlessui/react';

const SwitchRC = ({
  label,
  checked,
  onChange,
}: {
  label: string;
  checked?: boolean;
  onChange?(check: boolean): void;
}) => {
  return (
    <Field className="inline-flex gap-4 py-1">
      <Switch
        checked={checked}
        onChange={onChange}
        className="group relative flex h-7 w-14 cursor-pointer rounded-full bg-black/20 p-1 transition-colors duration-200 ease-in-out focus:outline-none data-[focus]:outline-1 data-[focus]:outline-white data-[checked]:bg-blue-700/80"
      >
        <span
          aria-hidden="true"
          className="pointer-events-none inline-block size-5 translate-x-0 rounded-full bg-white ring-0 shadow-lg transition duration-200 ease-in-out group-data-[checked]:translate-x-7"
        />
      </Switch>
      <Label>{label}</Label>
    </Field>
  );
};

export default SwitchRC;
