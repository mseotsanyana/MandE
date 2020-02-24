package com.me.mseotsanyana.mande.UTILITY.BLL;

import android.content.Context;

import com.me.mseotsanyana.mande.UTILITY.DAL.cDateDBA;
import com.me.mseotsanyana.mande.UTILITY.DAL.cDateModel;

import java.util.ArrayList;
import java.util.List;

public class cDateHandler extends cMapper<cDateModel, cDateDomain>{
    private cDateDBA dateDBA;
    private Context context;

    public cDateHandler(Context context) {
        dateDBA = new cDateDBA(context);
        this.context = context;
    }

    public boolean deleteAllDates() {
        return dateDBA.deleteAllDates();
    }

    public boolean addDate(cDateDomain domain) {
        // map the business domain to the model
        cDateModel model = this.DomainToModel(domain);
        return dateDBA.addDate(model);
    }

    public ArrayList<cDateDomain> getDateList() {
        List<cDateModel> dateModel = dateDBA.getDateList();

        ArrayList<cDateDomain> dateDomain = new ArrayList<>();
        cDateDomain domain;

        for(int i = 0; i < dateModel.size(); i++) {
            domain = this.ModelToDomain(dateModel.get(i));
            dateDomain.add(domain);
        }

        return dateDomain;
    }

    @Override
    protected cDateModel DomainToModel(cDateDomain domain) {
        cDateModel model = new cDateModel();

        model.setDateID(domain.getDateID());
        model.setOwnerID(domain.getOwnerID());
        model.setYear(domain.getYear());
        model.setMonth(domain.getMonth());
        model.setQuarter(domain.getQuarter());
        model.setDayMonth(domain.getDayMonth());
        model.setDayWeek(domain.getDayWeek());
        model.setDayYear(domain.getDayYear());
        model.setDayWeekMonth(domain.getDayWeekMonth());
        model.setWeekYear(domain.getWeekYear());
        model.setWeekMonth(domain.getWeekMonth());
        model.setNameQuarter(domain.getNameQuarter());
        model.setNameMonth(domain.getNameMonth());
        model.setNameWeekDay(domain.getNameWeekDay());
        model.setCreateDate(domain.getCreateDate());

        return model;
    }

    @Override
    protected cDateDomain ModelToDomain(cDateModel model) {
        cDateDomain domain = new cDateDomain();

        domain.setDateID(model.getDateID());
        domain.setOwnerID(model.getOwnerID());
        domain.setYear(model.getYear());
        domain.setMonth(model.getMonth());
        domain.setQuarter(model.getQuarter());
        domain.setDayMonth(model.getDayMonth());
        domain.setDayWeek(model.getDayWeek());
        domain.setDayYear(model.getDayYear());
        domain.setDayWeekMonth(model.getDayWeekMonth());
        domain.setWeekYear(model.getWeekYear());
        domain.setWeekMonth(model.getWeekMonth());
        domain.setNameQuarter(model.getNameQuarter());
        domain.setNameMonth(model.getNameMonth());
        domain.setNameWeekDay(model.getNameWeekDay());
        domain.setCreateDate(model.getCreateDate());

        return domain;
    }
}
