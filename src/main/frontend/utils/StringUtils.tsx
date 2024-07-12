function convertToTitleCase(inputString: string): string {
  const stringWithSpaces = inputString.replace(/_/gu, ' ');
  const words = stringWithSpaces.split(' ');
  const titleCaseWords = words.map((word) => word.charAt(0).toUpperCase() + word.slice(1).toLowerCase());
  return titleCaseWords.join(' ');
}

export default function EnumConversion(o: object) {
  return Object.keys(o).map((value) => ({
    label: convertToTitleCase(value),
    value,
  }));
}
