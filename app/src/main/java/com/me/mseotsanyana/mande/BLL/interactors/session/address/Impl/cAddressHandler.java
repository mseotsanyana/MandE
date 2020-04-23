package com.me.mseotsanyana.mande.BLL.interactors.session.address.Impl;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.domain.session.cAddressDomain;
import com.me.mseotsanyana.mande.DAL.ìmpl.session.cAddressRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.model.session.cAddressModel;
import com.me.mseotsanyana.mande.UTIL.BLL.cMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mseotsanyana on 2017/08/28.
 */

public class cAddressHandler extends cMapper<cAddressModel, cAddressDomain> {
    private static String TAG = cAddressHandler.class.getSimpleName();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private cAddressRepositoryImpl addressDBA;

    private Context context;

    private Gson gson = new Gson();

    public cAddressHandler(Context context) {
        addressDBA = new cAddressRepositoryImpl(context);
        this.context = context;
    }

    public cAddressHandler() {

    }

    public boolean addAddressFromExcel(cAddressDomain domain) {
        // map the business domain to the model
        cAddressModel model = this.DomainToModel(domain);
        return addressDBA.addAddressFromExcel(model);
    }

    public long createAddress(cAddressDomain domain) {
        // map the business domain to the model
        cAddressModel model = this.DomainToModel(domain);
        model.getSyncedDate();
        return addressDBA.createAddress(model);
    }

    public long updateAddress(cAddressDomain domain) {
        // map the business domain to the model
        cAddressModel model = this.DomainToModel(domain);
        return addressDBA.updateAddress(model);
    }

    public boolean deleteAddress(int addressID) {
        return addressDBA.deleteAddress(addressID);
    }

    public boolean deleteAddresses() {
        return addressDBA.deleteAddresses();
    }

    public cAddressDomain getAddressByID(int addressID) {
        cAddressModel model = addressDBA.getAddressByID(addressID);
        cAddressDomain domain = this.ModelToDomain(model);
        return domain;
    }

    public cAddressDomain getAddressByServerID(int serverID) {
        cAddressModel model = addressDBA.getAddressByServerID(serverID);
        cAddressDomain domain = this.ModelToDomain(model);
        return domain;
    }

    public ArrayList<cAddressDomain> getAddresses(int user_id,
                                                  int group,
                                                  int other,
                                                  int operations,
                                                  int[] status) {

        ArrayList<cAddressDomain> addressDomains = new ArrayList<>();

        Log.d(TAG, "USER ID = " + user_id + ", GROUP = " + group + ", OTHER = " + other +
                ", OPERATION = " + operations + ", STATUS = " + gson.toJson(status));

        List<cAddressModel> addressModels = addressDBA.getAddresses(user_id,
                group, other, operations, status);

        cAddressDomain domain;
        for (int i = 0; i < addressModels.size(); i++) {
            domain = this.ModelToDomain(addressModels.get(i));
            addressDomains.add(domain);
        }

        return addressDomains;
    }

    /* synchronization methods */

    public long createEntity(JSONObject jsonDomain) {
        cAddressDomain domain = new cAddressDomain();
        long id = -1;
        try {
            //domain.setAddressID(jsonDomain.getInt("addressID"));
            domain.setServerID(jsonDomain.getInt("serverID"));
            domain.setOwnerID(jsonDomain.getInt("ownerID"));
            domain.setGroupBITS(jsonDomain.getInt("groupBITS"));
            domain.setPermsBITS(jsonDomain.getInt("permsBITS"));
            domain.setStatusBITS(jsonDomain.getInt("statusBITS"));
            domain.setStreet(jsonDomain.getString("street"));
            domain.setCity(jsonDomain.getString("city"));
            domain.setProvince(jsonDomain.getString("province"));
            domain.setPostalCode(jsonDomain.getString("postalCode"));
            domain.setCountry(jsonDomain.getString("country"));
            domain.setCreatedDate(sdf.parse(jsonDomain.getString("createdDate")));
            domain.setModifiedDate(sdf.parse(jsonDomain.getString("modifiedDate")));
            domain.setSyncedDate(sdf.parse(jsonDomain.getString("syncedDate")));

            id = createAddress(domain);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return id;
    }

    public ArrayList<cAddressDomain> getEntities(int userID,
                                                 int groupBIT,
                                                 int otherBITS,
                                                 int permsBITS,
                                                 int[] statusBITS) {

        ArrayList<cAddressDomain> addressDomains = new ArrayList<>();

        Log.d(TAG, "USER ID = " + userID + ", GROUP = " + groupBIT +
                ", OTHER = " + otherBITS + ", OPERATION = " + permsBITS +
                ", STATUS = " + gson.toJson(statusBITS));

        List<cAddressModel> addressModels = addressDBA.getAddresses2Sync(userID,
                groupBIT, otherBITS, permsBITS, statusBITS);

        cAddressDomain domain;
        for (int i = 0; i < addressModels.size(); i++) {
            domain = this.ModelToDomain(addressModels.get(i));
            addressDomains.add(domain);
        }

        return addressDomains;
    }

    public long updateEntity(JSONObject jsonDomain) {
        cAddressDomain domain = new cAddressDomain();
        long result = -1;
        try {
            domain.setAddressID(jsonDomain.getInt("addressID"));
            domain.setServerID(jsonDomain.getInt("serverID"));
            domain.setOwnerID(jsonDomain.getInt("ownerID"));
            domain.setGroupBITS(jsonDomain.getInt("groupBITS"));
            domain.setPermsBITS(jsonDomain.getInt("permsBITS"));
            domain.setStatusBITS(jsonDomain.getInt("statusBITS"));
            domain.setStreet(jsonDomain.getString("street"));
            domain.setCity(jsonDomain.getString("city"));
            domain.setProvince(jsonDomain.getString("province"));
            domain.setPostalCode(jsonDomain.getString("postalCode"));
            domain.setCountry(jsonDomain.getString("country"));
            domain.setCreatedDate(sdf.parse(jsonDomain.getString("createdDate")));
            domain.setModifiedDate(sdf.parse(jsonDomain.getString("modifiedDate")));
            domain.setSyncedDate(sdf.parse(jsonDomain.getString("syncedDate")));


            result = updateAddress(domain);
            return result;

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }


    public long deleteEntity(JSONObject jsonDomain) {
        cAddressDomain domain = new cAddressDomain();
        long result = -1;

        try {
            domain.setAddressID(jsonDomain.getInt("addressID"));
            domain.setServerID(jsonDomain.getInt("serverID"));
            domain.setOwnerID(jsonDomain.getInt("ownerID"));
            domain.setGroupBITS(jsonDomain.getInt("groupBITS"));
            domain.setPermsBITS(jsonDomain.getInt("permsBITS"));
            domain.setStatusBITS(jsonDomain.getInt("statusBITS"));
            domain.setStreet(jsonDomain.getString("street"));
            domain.setCity(jsonDomain.getString("city"));
            domain.setProvince(jsonDomain.getString("province"));
            domain.setPostalCode(jsonDomain.getString("postalCode"));
            domain.setCountry(jsonDomain.getString("country"));
            domain.setCreatedDate(sdf.parse(jsonDomain.getString("createdDate")));
            domain.setModifiedDate(sdf.parse(jsonDomain.getString("modifiedDate")));
            domain.setSyncedDate(sdf.parse(jsonDomain.getString("syncedDate")));

            result = updateAddress(domain);
            return result;

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean purgeEntity(JSONObject jsonDomain) {
        int addressID = 0;
        try {
            addressID = jsonDomain.getInt("addressID");
            return deleteAddress(addressID);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public cAddressDomain getEntityByServerID(int serverID) {
        cAddressModel model = addressDBA.getAddressByServerID(serverID);
        cAddressDomain domain = this.ModelToDomain(model);
        return domain;
    }

    public int isServerID(int serverID) {
        return addressDBA.isServerID(serverID);
    }

    @Override
    protected cAddressModel DomainToModel(cAddressDomain domain) {
        cAddressModel model = new cAddressModel();
        model.setAddressID(domain.getAddressID());
        model.setServerID(domain.getServerID());
        model.setOwnerID(domain.getOwnerID());
        model.setGroupBITS(domain.getGroupBITS());
        model.setPermsBITS(domain.getPermsBITS());
        model.setStatusBITS(domain.getStatusBITS());
        model.setStreet(domain.getStreet());
        model.setCity(domain.getCity());
        model.setProvince(domain.getProvince());
        model.setPostalCode(domain.getPostalCode());
        model.setCountry(domain.getCountry());
        model.setCreatedDate(domain.getCreatedDate());
        model.setModifiedDate(domain.getModifiedDate());
        model.setSyncedDate(domain.getSyncedDate());

        return model;
    }

    @Override
    protected cAddressDomain ModelToDomain(cAddressModel model) {
        cAddressDomain domain = new cAddressDomain();
        domain.setAddressID(model.getAddressID());
        domain.setServerID(model.getServerID());
        domain.setOwnerID(model.getOwnerID());
        domain.setGroupBITS(model.getGroupBITS());
        domain.setPermsBITS(model.getPermsBITS());
        domain.setStatusBITS(model.getStatusBITS());
        domain.setStreet(model.getStreet());
        domain.setCity(model.getCity());
        domain.setProvince(model.getProvince());
        domain.setPostalCode(model.getPostalCode());
        domain.setCountry(model.getCountry());
        domain.setCreatedDate(model.getCreatedDate());
        domain.setModifiedDate(model.getModifiedDate());
        domain.setSyncedDate(model.getSyncedDate());

        return domain;
    }
}
