import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './khoan-thu.reducer';

export const KhoanThuDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const khoanThuEntity = useAppSelector(state => state.khoanThu.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="khoanThuDetailsHeading">
          <Translate contentKey="quanLyNhanKhauApp.khoanThu.detail.title">KhoanThu</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{khoanThuEntity.id}</dd>
          <dt>
            <span id="maKhoanThu">
              <Translate contentKey="quanLyNhanKhauApp.khoanThu.maKhoanThu">Ma Khoan Thu</Translate>
            </span>
          </dt>
          <dd>{khoanThuEntity.maKhoanThu}</dd>
          <dt>
            <span id="tenKhoanThu">
              <Translate contentKey="quanLyNhanKhauApp.khoanThu.tenKhoanThu">Ten Khoan Thu</Translate>
            </span>
          </dt>
          <dd>{khoanThuEntity.tenKhoanThu}</dd>
          <dt>
            <span id="donGia">
              <Translate contentKey="quanLyNhanKhauApp.khoanThu.donGia">Don Gia</Translate>
            </span>
          </dt>
          <dd>{khoanThuEntity.donGia}</dd>
          <dt>
            <span id="loai">
              <Translate contentKey="quanLyNhanKhauApp.khoanThu.loai">Loai</Translate>
            </span>
          </dt>
          <dd>{khoanThuEntity.loai}</dd>
        </dl>
        <Button tag={Link} to="/khoan-thu" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/khoan-thu/${khoanThuEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default KhoanThuDetail;
