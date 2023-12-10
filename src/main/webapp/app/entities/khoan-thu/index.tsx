import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import KhoanThu from './khoan-thu';
import KhoanThuDetail from './khoan-thu-detail';
import KhoanThuUpdate from './khoan-thu-update';
import KhoanThuDeleteDialog from './khoan-thu-delete-dialog';

const KhoanThuRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<KhoanThu />} />
    <Route path="new" element={<KhoanThuUpdate />} />
    <Route path=":id">
      <Route index element={<KhoanThuDetail />} />
      <Route path="edit" element={<KhoanThuUpdate />} />
      <Route path="delete" element={<KhoanThuDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default KhoanThuRoutes;
