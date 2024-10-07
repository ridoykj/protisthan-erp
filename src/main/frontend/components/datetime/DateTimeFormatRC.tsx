import { DatePickerDate } from '@vaadin/react-components';
import dateFnsFormat from 'date-fns/format';
import dateFnsParse from 'date-fns/parse';

export function formatDateIso8601(dateParts: DatePickerDate) {
  const { year, month, day } = dateParts;
  const date = new Date(year, month, day);

  return dateFnsFormat(date, 'dd-MM-yyyy');
}

export function parseDateIso8601(inputValue: string) {
  const date = dateFnsParse(inputValue, 'dd-MM-yyyy', new Date());

  return { year: date.getFullYear(), month: date.getMonth(), day: date.getDate() };
}
