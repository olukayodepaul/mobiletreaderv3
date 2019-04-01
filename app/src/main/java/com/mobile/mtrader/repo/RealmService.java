package com.mobile.mtrader.repo;

import com.mobile.mtrader.model.ModelEmployees;
import com.mobile.mtrader.model.RealmConverterAddProducts;
import com.mobile.mtrader.model.RealmConverterCustomerList;
import com.mobile.mtrader.model.RealmConverterLogin;
import com.mobile.mtrader.model.RealmConverterRepBasketList;
import com.mobile.mtrader.model.RealmConverterSkuList;
import com.mobile.mtrader.model.RealmConverterUserModules;

import java.util.ArrayList;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmResults;

public class RealmService {

    private Realm realm;

    public RealmService(Realm realm) {
        this.realm = realm;
    }

    public void setUserModule(ModelEmployees loginModel) {
        realm.executeTransaction(realm -> {
            RealmConverterLogin auth = realm.where(RealmConverterLogin.class).equalTo("auth", 1).findFirst();
            if (auth == null) {
                /*
                RealmConverterLogin realmConverterLogin = realm.createObject(RealmConverterLogin.class);
                realmConverterLogin.setAuth(1);
                realmConverterLogin.setId(loginModel.id);
                realmConverterLogin.setDbroute(loginModel.dbroute);
                realmConverterLogin.setName(loginModel.name);
                realmConverterLogin.setCustno(loginModel.custno);
                realmConverterLogin.setEcode(loginModel.ecode);
                realmConverterLogin.setLat(loginModel.lat);
                realmConverterLogin.setLng(loginModel.lng);
                realmConverterLogin.setDepot_waiver(loginModel.depot_waiver);
                realmConverterLogin.setSellingunit(loginModel.dbroute);
                realmConverterLogin.setClockintime(loginModel.clokin);
                realmConverterLogin.setClockouttime(loginModel.clokout);
                for (int i = 0; i < loginModel.modules.size(); i++) {
                    RealmConverterUserModules realmConverterModules = realm.createObject(RealmConverterUserModules.class);
                    realmConverterModules.setAuto(realmConverterModules.getAuto());
                    realmConverterModules.setId(loginModel.modules.get(i).id);
                    realmConverterModules.setImageurl(loginModel.modules.get(i).imageurl);
                    realmConverterModules.setNav(loginModel.modules.get(i).nav);
                    realmConverterModules.setName(loginModel.modules.get(i).name);
                    realmConverterModules.setSimpleDateFormat(realmConverterModules.getSimpleDateFormat());
                    realmConverterLogin.modules.add(realmConverterModules);
                }
                for (int i = 0; i < loginModel.customers.size(); i++) {
                    RealmConverterCustomerList rCustList = realm.createObject(RealmConverterCustomerList.class);
                    rCustList.setAuto(rCustList.getAuto());
                    rCustList.setSimpleDateFormat(rCustList.getSimpleDateFormat());
                    rCustList.setId(loginModel.customers.get(i).id);
                    rCustList.setNotice(loginModel.customers.get(i).notice);
                    rCustList.setUrno(loginModel.customers.get(i).urno);
                    rCustList.setCustomerno(loginModel.customers.get(i).customerno);
                    rCustList.setOutletname(loginModel.customers.get(i).outletname);
                    rCustList.setLat(loginModel.customers.get(i).lat);
                    rCustList.setLng(loginModel.customers.get(i).lng);
                    rCustList.setSort(loginModel.customers.get(i).sort);
                    rCustList.setToken(loginModel.customers.get(i).token);
                    rCustList.setOutlet_waiver(loginModel.customers.get(i).outlet_waiver);
                    realmConverterLogin.customers.add(rCustList);
                }
                for (int i = 0; i < loginModel.basket.size(); i++) {
                    RealmConverterRepBasketList bBasketList = realm.createObject(RealmConverterRepBasketList.class);
                    bBasketList.setAuto(bBasketList.getAuto());
                    bBasketList.setSimpleDateFormat(bBasketList.getSimpleDateFormat());
                    bBasketList.setQty(Double.parseDouble(loginModel.basket.get(i).qty));
                    bBasketList.setId(loginModel.basket.get(i).id);
                    bBasketList.setPrice(loginModel.basket.get(i).price);
                    bBasketList.setProdcode(loginModel.basket.get(i).prodcode);
                    bBasketList.setProdname(loginModel.basket.get(i).prodname);
                    bBasketList.setSoq(loginModel.basket.get(i).soq);
                    realmConverterLogin.basket.add(bBasketList);
                }

                for (int i = 0; i < loginModel.product.size(); i++) {
                    RealmConverterSkuList uSkus = realm.createObject(RealmConverterSkuList.class);
                    uSkus.setAuto(uSkus.getAuto());
                    uSkus.setSimpleDateFormat(uSkus.getSimpleDateFormat());
                    uSkus.setSimpleTimeFormat(uSkus.getSimpleTimeFormat());
                    uSkus.setId(loginModel.product.get(i).id);
                    uSkus.setSeparator(loginModel.product.get(i).separator);
                    uSkus.setProductcode(loginModel.product.get(i).productcode);
                    uSkus.setProductname(loginModel.product.get(i).productname);
                    uSkus.setQty(loginModel.product.get(i).qty);
                    uSkus.setSoq(loginModel.product.get(i).soq);
                    uSkus.setPackprice(loginModel.product.get(i).packprice);
                    uSkus.setRollprice(loginModel.product.get(i).rollprice);
                    uSkus.setSeparatorname(loginModel.product.get(i).separatorname);
                    realmConverterLogin.product.add(uSkus);
                }
                */
            }
        });
    }

    public List<RealmConverterUserModules> getUserModules() {
        List<RealmConverterUserModules> result = new ArrayList<>();
        RealmResults<RealmConverterUserModules> res = realm.where(RealmConverterUserModules.class).findAll();
        if (res != null) {
            result.addAll(realm.copyFromRealm(res));
        }
        return result;
    }

    public List<RealmConverterCustomerList> getCustomerDetails() {
        List<RealmConverterCustomerList> result = new ArrayList<>();
        RealmResults<RealmConverterCustomerList> res = realm.where(RealmConverterCustomerList.class).sort("sort").findAllAsync();
        if (res != null) {
            result.addAll(realm.copyFromRealm(res));
        }
        return result;
    }

    public int getUserId() {
        int result = 0;
        RealmConverterLogin usersid = realm.where(RealmConverterLogin.class).findFirst();
        if (usersid != null) {
            result = usersid.getId();
        }
        return result;
    }

    public String getUserClockInTime() {
          String result = "";
        RealmConverterLogin clockin = realm.where(RealmConverterLogin.class).findFirst();
        if (clockin  != null) {
            result = clockin.getClockintime();
        }
        return result;
    }

    public void deteleCustomerList(int id) {
        realm.executeTransaction(realm -> {
            RealmResults<RealmConverterCustomerList> rows = realm.where(RealmConverterCustomerList.class).equalTo("id", id).findAll();
            if (rows != null) {
                rows.deleteFirstFromRealm();
            }
        });
    }

    public List<RealmConverterRepBasketList> getUserBasket() {

        ArrayList<RealmConverterRepBasketList> result = new ArrayList<>();
        RealmResults<RealmConverterRepBasketList> auth = realm.where(RealmConverterRepBasketList.class).sort("auto").findAll();
        if (auth != null) {
            result.addAll(realm.copyFromRealm(auth));
        }
        return result;

    }

    public double totalQty() {

        List<RealmConverterRepBasketList> list = realm.where(RealmConverterRepBasketList.class).findAll();
        if (list != null) {
            double rs = realm.where(RealmConverterRepBasketList.class).sum("qty").doubleValue();
            return rs;
        } else {
            return 0;
        }
    }

    public String depotGeoCordAndDepotWaiver() {

        String result = "";
        RealmConverterLogin usersid = realm.where(RealmConverterLogin.class).findFirst();
        if (usersid != null) {
            result = Double.toString(usersid.getLat()) + "~" + Double.toString(usersid.getLng());
        }
        return result;
    }

    public List<RealmConverterSkuList> getUserSku() {

        List<RealmConverterSkuList> result = new ArrayList<>();
        RealmResults<RealmConverterSkuList> auth = realm.where(RealmConverterSkuList.class).sort("separator")
                .findAll();
        if (auth != null) {
            result.addAll(realm.copyFromRealm(auth));
        }
        return result;
    }

    public void updateClockin( int taskid,String timeiD) {
        realm.executeTransaction(realm -> {
            RealmConverterLogin rows = realm.where(RealmConverterLogin.class).findFirstAsync();
            if (rows != null) {
                if(taskid==1){
                    rows.setClockintime(timeiD);
                }else{
                    rows.setClockouttime(timeiD);
                }
            }
        });
    }

    public String skuQtyBalance(String productCode) {
        String bal = "0.0";
        RealmResults<RealmConverterSkuList> chgr = realm.where(RealmConverterSkuList.class)
                .equalTo("productcode", productCode)
                .findAllAsync();
        if (chgr != null) {
            bal = chgr.first().getQty();
        }
        return bal;
    }


    public String totalInvertoryEntries() {
        double rs = 0.0;
        List<RealmConverterAddProducts> list = realm.where(RealmConverterAddProducts.class)
                .equalTo("status", 1)
                .findAll();
        if (list != null) {
            rs = realm.where(RealmConverterAddProducts.class)
                    .equalTo("status", 1)
                    .sum("inventory").doubleValue();
        }
        return Double.toString(rs);
    }

    public String totalPricingEntries() {
        int rs = 0;
        List<RealmConverterAddProducts> list = realm.where(RealmConverterAddProducts.class)
                .equalTo("status", 1)
                .findAll();
        if (list != null) {
            rs = realm.where(RealmConverterAddProducts.class)
                    .equalTo("status", 1)
                    .sum("pricing").intValue();
        }
        return Integer.toString(rs);
    }

    public double totalAmount() {
        double rs = 0.0;
        List<RealmConverterAddProducts> list = realm.where(RealmConverterAddProducts.class)
                .equalTo("status", 1)
                .equalTo("separator","1")
                .findAll();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                String[] rst = Double.toString(list.get(i).getOrder()).split("\\.");
                rs += (Integer.parseInt(rst[1]) * list.get(i).getPackprice()) + (Integer.parseInt(rst[0]) * list.get(i).getRollprice());
            }
        }
        return rs;
    }

    public String totalOrderEntries() {
        double rs = 0.0;
        List<RealmConverterAddProducts> list = realm.where(RealmConverterAddProducts.class)
                .equalTo("status", 1)
                .equalTo("separator","1")
                .findAll();
        if (list != null) {
            rs = realm.where(RealmConverterAddProducts.class)
                    .equalTo("status", 1)
                    .sum("order")
                    .doubleValue();
        }
        return Double.toString(rs);
    }

    public List<RealmConverterAddProducts> getSalesEntry() {
        List<RealmConverterAddProducts> result = new ArrayList<>();
        RealmResults<RealmConverterAddProducts> chgr = realm.where(RealmConverterAddProducts.class)
                .equalTo("status",1)
                .sort("separator")
                .findAllAsync();
        if (chgr != null) {
            result.addAll(realm.copyFromRealm(chgr));
        }
        return result;
    }

    public void deletePreviousSalesList() {
        realm.executeTransaction(realm -> {
            RealmResults<RealmConverterAddProducts> rows = realm.where(RealmConverterAddProducts.class)
                    .equalTo("status", 1)
                    .findAll();
            if (rows != null) {
                rows.deleteAllFromRealm();
            }
        });
    }

    public List<RealmConverterAddProducts> getAllSalesItems(String customer_id) {
        List<RealmConverterAddProducts> result = new ArrayList<>();
        RealmResults<RealmConverterAddProducts> chg = realm.where(RealmConverterAddProducts.class)
                .equalTo("customer_id", customer_id)
                .findAllAsync();
        if(chg!=null){
            result.addAll(realm.copyFromRealm(chg));
        }
        return result;
    }

    public List<RealmConverterAddProducts> getSalesEntryGroup() {
        List<RealmConverterAddProducts> result = new ArrayList<>();
        RealmResults<RealmConverterAddProducts> chgr = realm.where(RealmConverterAddProducts.class)
                .distinct("customer_id")
                .equalTo("status", 2)
                .findAllAsync();
        if (chgr != null) {
            result.addAll(realm.copyFromRealm(chgr));
        }
        return result;
    }

    public List<RealmConverterAddProducts> getSalesItemsEntries(String customerno) {
        List<RealmConverterAddProducts> result = new ArrayList<>();
        RealmResults<RealmConverterAddProducts> chgr = realm.where(RealmConverterAddProducts.class)
                .equalTo("customer_id", customerno)
                .findAllAsync();
        if (chgr != null) {
            result.addAll(realm.copyFromRealm(chgr));
        }
        return result;
    }

    public int getUnPostSales(String custid, int status, int r_status) {
        int i = 0;
        Long result = realm.where(RealmConverterAddProducts.class)
                .equalTo("status",status)
                .equalTo("s_status",r_status)
                .equalTo("customer_id", custid)
                .count();

        if (result != null) {
            i = Integer.parseInt(String.valueOf(result));
        }
        return i;
    }

    public void setSalesEntriesConverter(String product_name, String product_code, String separator, String soq, String qty,
                                         double rollprice, double packprice, String order, String customer_id, String customer_name,
                                         String inventory, String pricing, int status, int s_status, String separatorname) {


        realm.executeTransaction(realm -> {
            RealmResults<RealmConverterAddProducts> chgr = realm.where(RealmConverterAddProducts.class).findAllAsync();
            if (chgr != null) {
                RealmConverterAddProducts chg = new RealmConverterAddProducts();
                chg.setEntrydate(chg.getEntrydate());
                chg.setProduct_name(product_name);
                chg.setProduct_code(product_code);
                chg.setSeparator(separator);
                chg.setSoq(soq);
                chg.setQty(qty);
                chg.setRollprice(rollprice);
                chg.setPackprice(packprice);
                if (order.equals("")) {
                    chg.setOrder(0.0);
                } else {
                    chg.setOrder(Double.parseDouble(order));
                }
                chg.setCustomer_id(customer_id);
                chg.setCustomer_name(customer_name);

                if (inventory.equals("")) {
                    chg.setInventory(0.0);
                } else {
                    chg.setInventory(Double.parseDouble(inventory));
                }

                if (pricing.equals("")) {
                    chg.setPricing(0);
                } else {
                    chg.setPricing(Integer.parseInt(pricing));
                }
                chg.setStatus(status);
                chg.setS_status(s_status);
                chg.setSeparatorname(separatorname);
                realm.insert(chg);
            }
        });
    }

    public List<RealmConverterAddProducts> getSalesEntriesData() {
        List<RealmConverterAddProducts> result = new ArrayList<>();
        RealmResults<RealmConverterAddProducts> chgr = realm.where(RealmConverterAddProducts.class)
                .equalTo("status", 1)
                .findAllAsync();
        if (chgr != null) {
            result.addAll(realm.copyFromRealm(chgr));
        }
        return result;
    }

    public void updateServerPush(String customer_id, String product_code,
                                 int qtypack, int qtyroll, String entrytype) {
        realm.executeTransaction(realm -> {
            RealmConverterAddProducts chgr = realm.where(RealmConverterAddProducts.class)
                    .equalTo("product_code", product_code)
                    .equalTo("customer_id", customer_id).findFirstAsync();
            if (chgr != null) {
                chgr.setStatus(2);
                chgr.setS_status(2);
            }
            if(entrytype.equals("own brands")) {
                RealmConverterSkuList chgTotal = realm.where(RealmConverterSkuList.class)
                        .equalTo("productcode", product_code)
                        .findFirstAsync();

                String convert = (Integer.toString(qtypack)+"."+Integer.toString(qtyroll));
                double total = Double.parseDouble(convert);

                if(chgTotal!=null) {
                    chgTotal.setQty(Double.toString(Double.parseDouble(chgTotal.getQty())-total));
                }
            }
        });
    }

    public void updateServerUnPush() {
        realm.executeTransaction(realm -> {
            RealmResults<RealmConverterAddProducts> chgr = realm.where(RealmConverterAddProducts.class)
                    .equalTo("status", 1).findAll();
            if (chgr != null) {
                chgr.first().setStatus(2);
            }
        });
    }

    public long totalSkuEntered() {
        long chgr = realm.where(RealmConverterAddProducts.class)
                .equalTo("status", 1)
                .equalTo("separator", "1")
                .count();
        return chgr;
    }

    public void closeConnection() {
        if (realm != null) {
            realm.close();
        }
    }

}
