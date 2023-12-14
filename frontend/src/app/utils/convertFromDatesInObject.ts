export function convertFromDatesInObject<T extends object = object>(obj: T): T {
  // Iterate recursively through object and detect strings that look like dates, convert them to Date objects
  for (const key in obj) {
    if (!obj.hasOwnProperty(key)) {
      continue;
    }

    const element = obj[key];
    if (element instanceof Date) {
      (obj[key] as unknown) = element.toISOString();
    } else if (typeof element === 'object') {
      convertFromDatesInObject(element);
    }
  }

  return obj;
}
