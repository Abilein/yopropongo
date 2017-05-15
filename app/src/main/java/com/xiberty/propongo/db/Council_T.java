package com.xiberty.propongo.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by growcallisaya on 4/5/17.
 */

@Table(database = AppDatabase.class)
public class Council_T extends BaseModel {

    @PrimaryKey(autoincrement = true)
    public int id;
    @Column @Unique
    public String name;
    @Column
    public String department;
    @Column
    public String creation_date;
    @Column
    public int president;
    @Column
    public int vice_president;
    @Column
    public int secretary;
    @Column
    public int vocal_a;
    @Column
    public int vocal_b;
    @Column
    public String logo;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public int getPresident() {
        return president;
    }

    public void setPresident(int president) {
        this.president = president;
    }

    public int getVice_president() {
        return vice_president;
    }

    public void setVice_president(int vice_president) {
        this.vice_president = vice_president;
    }

    public int getSecretary() {
        return secretary;
    }

    public void setSecretary(int secretary) {
        this.secretary = secretary;
    }

    public int getVocal_a() {
        return vocal_a;
    }

    public void setVocal_a(int vocal_a) {
        this.vocal_a = vocal_a;
    }

    public int getVocal_b() {
        return vocal_b;
    }

    public void setVocal_b(int vocal_b) {
        this.vocal_b = vocal_b;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}


