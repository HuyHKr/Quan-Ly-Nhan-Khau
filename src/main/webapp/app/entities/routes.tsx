import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import HoKhau from './ho-khau';
import KhoanThu from './khoan-thu';
import NopTien from './nop-tien';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="ho-khau/*" element={<HoKhau />} />
        <Route path="khoan-thu/*" element={<KhoanThu />} />
        <Route path="nop-tien/*" element={<NopTien />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
