import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/ho-khau">
        <Translate contentKey="global.menu.entities.hoKhau" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/khoan-thu">
        <Translate contentKey="global.menu.entities.khoanThu" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/nop-tien">
        <Translate contentKey="global.menu.entities.nopTien" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
