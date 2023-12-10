import { IHoKhau } from 'app/shared/model/ho-khau.model';
import { IKhoanThu } from 'app/shared/model/khoan-thu.model';

export interface INopTien {
  id?: number;
  soTien?: number | null;
  ngayNop?: string | null;
  hoKhau?: IHoKhau | null;
  khoanThu?: IKhoanThu | null;
}

export const defaultValue: Readonly<INopTien> = {};
