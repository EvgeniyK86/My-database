package org.myfirstdatabase.mapper;

public interface Mapper<F, T> {

    T mapFrom(F object);
}
