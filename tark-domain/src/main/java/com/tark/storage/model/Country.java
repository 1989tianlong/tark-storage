package com.tark.storage.model;

import com.tark.storage.common.base.BaseEntity;

import javax.persistence.*;

public class Country extends BaseEntity{

    /**
     * ����
     */
    private String countryname;

    /**
     * ����
     */
    private String countrycode;

    /**
     * ��ȡ����
     *
     * @return countryname - ����
     */
    public String getCountryname() {
        return countryname;
    }

    /**
     * ��������
     *
     * @param countryname ����
     */
    public void setCountryname(String countryname) {
        this.countryname = countryname;
    }

    /**
     * ��ȡ����
     *
     * @return countrycode - ����
     */
    public String getCountrycode() {
        return countrycode;
    }

    /**
     * ���ô���
     *
     * @param countrycode ����
     */
    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }
}