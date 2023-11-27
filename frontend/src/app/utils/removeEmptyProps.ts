export function removeEmptyProps<T>(obj: T): Partial<T> {
  for (const prop in obj) {
    if (!obj[prop]) {
      delete obj[prop];
    }
  }

  return obj;
}
