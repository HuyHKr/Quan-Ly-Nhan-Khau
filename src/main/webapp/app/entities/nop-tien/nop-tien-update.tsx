import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IHoKhau } from 'app/shared/model/ho-khau.model';
import { getEntities as getHoKhaus } from 'app/entities/ho-khau/ho-khau.reducer';
import { IKhoanThu } from 'app/shared/model/khoan-thu.model';
import { getEntities as getKhoanThus } from 'app/entities/khoan-thu/khoan-thu.reducer';
import { INopTien } from 'app/shared/model/nop-tien.model';
import { getEntity, updateEntity, createEntity, reset } from './nop-tien.reducer';

export const NopTienUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const hoKhaus = useAppSelector(state => state.hoKhau.entities);
  const khoanThus = useAppSelector(state => state.khoanThu.entities);
  const nopTienEntity = useAppSelector(state => state.nopTien.entity);
  const loading = useAppSelector(state => state.nopTien.loading);
  const updating = useAppSelector(state => state.nopTien.updating);
  const updateSuccess = useAppSelector(state => state.nopTien.updateSuccess);

  const handleClose = () => {
    navigate('/nop-tien');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getHoKhaus({}));
    dispatch(getKhoanThus({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    if (values.soTien !== undefined && typeof values.soTien !== 'number') {
      values.soTien = Number(values.soTien);
    }
    values.ngayNop = convertDateTimeToServer(values.ngayNop);

    const entity = {
      ...nopTienEntity,
      ...values,
      hoKhau: hoKhaus.find(it => it.id.toString() === values.hoKhau.toString()),
      khoanThu: khoanThus.find(it => it.id.toString() === values.khoanThu.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          ngayNop: displayDefaultDateTime(),
        }
      : {
          ...nopTienEntity,
          ngayNop: convertDateTimeFromServer(nopTienEntity.ngayNop),
          hoKhau: nopTienEntity?.hoKhau?.id,
          khoanThu: nopTienEntity?.khoanThu?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="quanLyNhanKhauApp.nopTien.home.createOrEditLabel" data-cy="NopTienCreateUpdateHeading">
            <Translate contentKey="quanLyNhanKhauApp.nopTien.home.createOrEditLabel">Create or edit a NopTien</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="nop-tien-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('quanLyNhanKhauApp.nopTien.soTien')}
                id="nop-tien-soTien"
                name="soTien"
                data-cy="soTien"
                type="text"
              />
              <ValidatedField
                label={translate('quanLyNhanKhauApp.nopTien.ngayNop')}
                id="nop-tien-ngayNop"
                name="ngayNop"
                data-cy="ngayNop"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="nop-tien-hoKhau"
                name="hoKhau"
                data-cy="hoKhau"
                label={translate('quanLyNhanKhauApp.nopTien.hoKhau')}
                type="select"
              >
                <option value="" key="0" />
                {hoKhaus
                  ? hoKhaus.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.tenChuHo}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="nop-tien-khoanThu"
                name="khoanThu"
                data-cy="khoanThu"
                label={translate('quanLyNhanKhauApp.nopTien.khoanThu')}
                type="select"
              >
                <option value="" key="0" />
                {khoanThus
                  ? khoanThus.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.tenKhoanThu}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/nop-tien" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default NopTienUpdate;
