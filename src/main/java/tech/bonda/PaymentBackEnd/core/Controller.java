package tech.bonda.PaymentBackEnd.core;

import tech.bonda.PaymentBackEnd.entities.account.Account;

import java.util.Collection;

public interface Controller {
    Object create(Object object);

    Collection<Object> getAll();

    Object get(long id);

    Object update(long id, Object object);

    void delete(long id);

}
