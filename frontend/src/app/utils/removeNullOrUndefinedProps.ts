export function removeNullOrUndefinedProps<T>(obj: T): Partial<T> {
  for (const prop in obj) {
    if (obj[prop] === null || obj[prop] === undefined) {
      delete obj[prop];
    }
  }

  return obj;
}
