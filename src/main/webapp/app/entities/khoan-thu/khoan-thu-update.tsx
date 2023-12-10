import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IKhoanThu } from 'app/shared/model/khoan-thu.model';
import { Loai } from 'app/shared/model/enumerations/loai.model';
import { getEntity, updateEntity, createEntity, reset } from './khoan-thu.reducer';

export const KhoanThuUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const khoanThuEntity = useAppSelector(state => state.khoanThu.entity);
  const loading = useAppSelector(state => state.khoanThu.loading);
  const updating = useAppSelector(state => state.khoanThu.updating);
  const updateSuccess = useAppSelector(state => state.khoanThu.updateSuccess);
  const loaiValues = Object.keys(Loai);

  const handleClose = () => {
    navigate('/khoan-thu');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
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

    const entity = {
      ...khoanThuEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          loai: 'BAT_BUOC',
          ...khoanThuEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="quanLyNhanKhauApp.khoanThu.home.createOrEditLabel" data-cy="KhoanThuCreateUpdateHeading">
            <Translate contentKey="quanLyNhanKhauApp.khoanThu.home.createOrEditLabel">Create or edit a KhoanThu</Translate>
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
                  id="khoan-thu-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('quanLyNhanKhauApp.khoanThu.maKhoanThu')}
                id="khoan-thu-maKhoanThu"
                name="maKhoanThu"
                data-cy="maKhoanThu"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('quanLyNhanKhauApp.khoanThu.tenKhoanThu')}
                id="khoan-thu-tenKhoanThu"
                name="tenKhoanThu"
                data-cy="tenKhoanThu"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('quanLyNhanKhauApp.khoanThu.donGia')}
                id="khoan-thu-donGia"
                name="donGia"
                data-cy="donGia"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('quanLyNhanKhauApp.khoanThu.loai')}
                id="khoan-thu-loai"
                name="loai"
                data-cy="loai"
                type="select"
              >
                {loaiValues.map(loai => (
                  <option value={loai} key={loai}>
                    {translate('quanLyNhanKhauApp.Loai.' + loai)}
                  </option>
                ))}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/khoan-thu" replace color="info">
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

export default KhoanThuUpdate;
