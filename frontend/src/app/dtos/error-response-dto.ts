export interface ErrorResponseDto {
  timestamp: string;
  error: string;
  subErrors: { [key: string]: string };
  status: number;
}
