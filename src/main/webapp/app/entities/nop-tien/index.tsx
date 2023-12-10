import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import NopTien from './nop-tien';
import NopTienDetail from './nop-tien-detail';
import NopTienUpdate from './nop-tien-update';
import NopTienDeleteDialog from './nop-tien-delete-dialog';

const NopTienRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<NopTien />} />
    <Route path="new" element={<NopTienUpdate />} />
    <Route path=":id">
      <Route index element={<NopTienDetail />} />
      <Route path="edit" element={<NopTienUpdate />} />
      <Route path="delete" element={<NopTienDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default NopTienRoutes;
