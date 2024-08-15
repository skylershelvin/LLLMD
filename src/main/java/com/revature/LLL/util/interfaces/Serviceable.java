package com.revature.LLL.util.interfaces;

import java.util.List;

public interface Serviceable<O> {
    List<O> findAll();
    O create(O newObject);
    O findById(int id);
    Boolean update(O updatedObject);
    Boolean delete(O deletedObject);
}