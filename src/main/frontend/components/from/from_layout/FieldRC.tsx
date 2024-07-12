import { AbstractModel } from '@vaadin/hilla-lit-form';
import { FieldDirective } from '@vaadin/hilla-react-form';
import {
  Checkbox,
  ComboBox,
  DatePicker,
  DateTimePicker,
  NumberField,
  PasswordField,
  Select,
  TextArea,
  TextField,
  Upload,
} from '@vaadin/react-components';
import ButtonRC from 'Frontend/components/button/regular/ButtonRC';
// import { FromLayoutContext } from './FromLayoutRC';

export interface FieldType {
  fieldType:
    | 'label'
    | 'text'
    | 'combobox'
    | 'checkbox'
    | 'switch'
    | 'upload'
    | 'date'
    | 'datetime'
    | 'smalltext'
    | 'longtext'
    | 'password'
    | 'int'
    | 'float'
    | 'select'
    | 'button';
  fieldName: string;
  label: string;
  required?: boolean;
  description?: string;
  options?: string[];
}

function toCamelCase(str: string | undefined): string {
  // return str?.replace(/(_\w)/g, (match) => match.toUpperCase().replace('_', '')) ?? '';
  return str ?? '';
}

function resolvePropertyModel(modelInstance: AbstractModel, path: string): AbstractModel {
  const currentModel: AbstractModel | undefined = modelInstance;
  return (currentModel as any)[path];
}

const FieldRC = ({
  item,
  className,
  custom,
  field,
  model,
}: {
  item: FieldType;
  className?: string;
  custom?: any;
  field: FieldDirective;
  model: AbstractModel;
}) => {
  // const contextData = useContext(FromLayoutContext);
  const contextData = true;
  if (contextData) {
    // const { field, model } = contextData;
    if (item.fieldName === 'email') {
      console.log('email field');
    }
    if (item.fieldName && custom && item.fieldName in custom) return custom[item.fieldName];
    switch (item.fieldType) {
      case 'label':
        return <span className={className}>{item.label}</span>;
      case 'text':
        return (
          <TextField
            label={item.label}
            helperText={item.description}
            required={item.required ?? false}
            className={className}
            {...field(resolvePropertyModel(model, toCamelCase(item.fieldName)))}
          />
        );
      case 'combobox':
        return (
          <ComboBox
            label={item.label}
            allowCustomValue
            helperText={item.description}
            required={item.required ?? false}
            className={className}
            {...field(resolvePropertyModel(model, toCamelCase(item.fieldName)))}
          />
        );
      case 'checkbox':
        return (
          <Checkbox
            label={item.label}
            disabled={item.required ?? false}
            className={className}
            {...field(resolvePropertyModel(model, toCamelCase(item.fieldName)))}
          />
        );
      case 'switch':
        return <div>no-item</div>;
      case 'upload':
        return (
          <>
            <p>{item.label}</p>
            <Upload
              method="PUT"
              target="/api/upload-handler"
              headers='{ "X-API-KEY": "7f4306cb-bb25-4064-9475-1254c4eff6e5" }'
            />
          </>
        );
      case 'date':
        return (
          <DatePicker
            label={item.label}
            helperText={item.description}
            required={item.required ?? false}
            // className={className}
            className="max-w-96"
            {...field(resolvePropertyModel(model, toCamelCase(item.fieldName)))}
          />
        );
      case 'datetime':
        return (
          <DateTimePicker
            label={item.label}
            helperText={item.description}
            required={item.required ?? false}
            // className={className}
            className="max-w-96"
            {...field(resolvePropertyModel(model, toCamelCase(item.fieldName)))}
          />
        );
      case 'smalltext':
        return (
          <TextArea
            label={item.label}
            helperText={item.description}
            required={item.required ?? false}
            className={className}
            {...field(resolvePropertyModel(model, toCamelCase(item.fieldName)))}
          />
        );
      case 'longtext':
        return (
          <TextArea
            label={item.label}
            helperText={item.description}
            required={item.required ?? false}
            className={className}
            {...field(resolvePropertyModel(model, toCamelCase(item.fieldName)))}
          />
        );
      case 'password':
        return (
          <PasswordField
            label={item.label}
            helperText={item.description}
            required={item.required ?? false}
            className={className}
            {...field(resolvePropertyModel(model, toCamelCase(item.fieldName)))}
          />
        );
      case 'int':
        return (
          <NumberField
            label={item.label}
            helperText={item.description}
            required={item.required ?? false}
            className={className}
            {...field(resolvePropertyModel(model, toCamelCase(item.fieldName)))}
          />
        );
      case 'float':
        return (
          <NumberField
            label={item.label}
            helperText={item.description}
            required={item.required ?? false}
            className={className}
            {...field(resolvePropertyModel(model, toCamelCase(item.fieldName)))}
          />
        );
      case 'select':
        return (
          <Select
            label={item.label}
            helperText={item.description}
            required={item.required ?? false}
            className={className}
            items={item.options?.map((option) => ({ label: option, value: option })) ?? []}
            {...field(resolvePropertyModel(model, toCamelCase(item.fieldName)))}
          />
        );
      case 'button':
        return (
          <ButtonRC
            onClick={() => {}}
            title={item.label}
            // disabled={item.readOnly ?? false}
            // className={className}
            // {...field(resolvePropertyModel(model, toCamelCase(item.fieldName)))}
          />
        );
      default:
        return null;
    }
  } else {
    return null;
  }
};

export default FieldRC;
