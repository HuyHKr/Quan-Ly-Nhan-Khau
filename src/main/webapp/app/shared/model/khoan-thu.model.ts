import { Loai } from 'app/shared/model/enumerations/loai.model';

export interface IKhoanThu {
  id?: number;
  maKhoanThu?: string;
  tenKhoanThu?: string;
  donGia?: string;
  loai?: keyof typeof Loai | null;
}

export const defaultValue: Readonly<IKhoanThu> = {};
