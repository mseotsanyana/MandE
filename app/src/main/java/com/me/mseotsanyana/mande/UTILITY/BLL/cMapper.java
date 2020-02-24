package com.me.mseotsanyana.mande.UTILITY.BLL;

/**
 * Created by mseotsanyana on 2016/07/13.
 */
public abstract class cMapper<TModel, TDomain>
{
    private TDomain domain;
    private TModel model;

    public TDomain getDomain() {
        return domain;
    }

    public void setDomain(TDomain domain) {
        this.domain = domain;
    }

    public TModel getModel() {
        return model;
    }

    public void setModel(TModel model) {
        this.model = model;
    }

    protected abstract TModel DomainToModel(TDomain domain);
    protected abstract TDomain ModelToDomain(TModel model);

}
