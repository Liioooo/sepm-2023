const isoDateRegex = /\d{4}-[01]\d-[0-3]\dT[0-2]\d:[0-5]\d:[0-5]\d(\.\d+)?([+-][0-2]\d:[0-5]\d|Z)?/;

export function convertToDatesInObject<T extends object = object>(obj: T): T {
  // Iterate recursively through object and detect strings that look like dates, convert them to Date objects
  for (const key in obj) {
    if (!obj.hasOwnProperty(key)) {
      continue;
    }

    const element = obj[key];
    if (typeof element === 'string' && isoDateRegex.test(element)) {
      const date = new Date(element);
      if (!date) {
        continue;
      }

      (obj[key] as unknown) = date;
    } else if (typeof element === 'object') {
      convertToDatesInObject(element);
    }
  }

  return obj;
}
