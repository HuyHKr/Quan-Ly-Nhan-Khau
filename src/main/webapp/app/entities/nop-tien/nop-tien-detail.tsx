import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './nop-tien.reducer';

export const NopTienDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const nopTienEntity = useAppSelector(state => state.nopTien.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="nopTienDetailsHeading">
          <Translate contentKey="quanLyNhanKhauApp.nopTien.detail.title">NopTien</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{nopTienEntity.id}</dd>
          <dt>
            <span id="soTien">
              <Translate contentKey="quanLyNhanKhauApp.nopTien.soTien">So Tien</Translate>
            </span>
          </dt>
          <dd>{nopTienEntity.soTien}</dd>
          <dt>
            <span id="ngayNop">
              <Translate contentKey="quanLyNhanKhauApp.nopTien.ngayNop">Ngay Nop</Translate>
            </span>
          </dt>
          <dd>{nopTienEntity.ngayNop ? <TextFormat value={nopTienEntity.ngayNop} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="quanLyNhanKhauApp.nopTien.hoKhau">Ho Khau</Translate>
          </dt>
          <dd>{nopTienEntity.hoKhau ? nopTienEntity.hoKhau.tenChuHo : ''}</dd>
          <dt>
            <Translate contentKey="quanLyNhanKhauApp.nopTien.khoanThu">Khoan Thu</Translate>
          </dt>
          <dd>{nopTienEntity.khoanThu ? nopTienEntity.khoanThu.tenKhoanThu : ''}</dd>
        </dl>
        <Button tag={Link} to="/nop-tien" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/nop-tien/${nopTienEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default NopTienDetail;
