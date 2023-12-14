import { PublicFileDto } from '../dtos/public-file-dto';
import { inject } from '@angular/core';

const isoDateRegex = /\d{4}-[01]\d-[0-3]\dT[0-2]\d:[0-5]\d:[0-5]\d(\.\d+)?([+-][0-2]\d:[0-5]\d|Z)?/;

export function convertPublicFileUrlToAbsoluteUrl(publicFileDto: PublicFileDto, backendBaseUri: string): PublicFileDto {

  if (publicFileDto != null && !publicFileDto.url?.startsWith(backendBaseUri)) {
    publicFileDto.url = backendBaseUri + publicFileDto.url;
  }

  return publicFileDto;
}


