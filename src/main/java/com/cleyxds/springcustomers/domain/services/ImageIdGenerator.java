package com.cleyxds.springcustomers.domain.services;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.UUID;

public class ImageIdGenerator implements IdentifierGenerator {

  public static final String GENERATOR_NAME = "IMAGE_ID_GENERATOR";

  @Override
  public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
    return UUID.randomUUID().toString().replace("-", "");
  }
}
