import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './ho-khau.reducer';

export const HoKhauDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const hoKhauEntity = useAppSelector(state => state.hoKhau.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="hoKhauDetailsHeading">
          <Translate contentKey="quanLyNhanKhauApp.hoKhau.detail.title">HoKhau</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{hoKhauEntity.id}</dd>
          <dt>
            <span id="maHoKhau">
              <Translate contentKey="quanLyNhanKhauApp.hoKhau.maHoKhau">Ma Ho Khau</Translate>
            </span>
          </dt>
          <dd>{hoKhauEntity.maHoKhau}</dd>
          <dt>
            <span id="tenChuHo">
              <Translate contentKey="quanLyNhanKhauApp.hoKhau.tenChuHo">Ten Chu Ho</Translate>
            </span>
          </dt>
          <dd>{hoKhauEntity.tenChuHo}</dd>
          <dt>
            <span id="diaChi">
              <Translate contentKey="quanLyNhanKhauApp.hoKhau.diaChi">Dia Chi</Translate>
            </span>
          </dt>
          <dd>{hoKhauEntity.diaChi}</dd>
        </dl>
        <Button tag={Link} to="/ho-khau" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/ho-khau/${hoKhauEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default HoKhauDetail;
