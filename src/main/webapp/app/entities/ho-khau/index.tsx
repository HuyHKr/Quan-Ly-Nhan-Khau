import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import HoKhau from './ho-khau';
import HoKhauDetail from './ho-khau-detail';
import HoKhauUpdate from './ho-khau-update';
import HoKhauDeleteDialog from './ho-khau-delete-dialog';

const HoKhauRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<HoKhau />} />
    <Route path="new" element={<HoKhauUpdate />} />
    <Route path=":id">
      <Route index element={<HoKhauDetail />} />
      <Route path="edit" element={<HoKhauUpdate />} />
      <Route path="delete" element={<HoKhauDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default HoKhauRoutes;
