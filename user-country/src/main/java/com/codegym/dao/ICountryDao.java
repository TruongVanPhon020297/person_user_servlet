package com.codegym.dao;

import com.codegym.model.Country;

import java.util.List;

public interface ICountryDao {
    List<Country> selectAllCountry();
}
