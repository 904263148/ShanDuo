package com.yapin.shanduo.db;

import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.entity.AccountHistory;
import com.yapin.shanduo.model.entity.AccountHistoryDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * 作者：L on 2018/4/21 0021 10:11
 */
public class DBManager {

    /*
     *  保存账号历史
     * */
    public static void setAccountHistory(AccountHistory bean){
        ShanDuoPartyApplication.getDaoSession().getAccountHistoryDao().insertOrReplaceInTx(bean);
    }

    /*
     *  删除账号历史
     * */
    public static void deleteAccountHistory(){
        ShanDuoPartyApplication.getDaoSession().getAccountHistoryDao().deleteAll();
    }

    /*
     *  获取账号历史
     * */
    public static List<AccountHistory> getAccountHistory(){
        QueryBuilder<AccountHistory> queryBuilder = ShanDuoPartyApplication.getDaoSession().getAccountHistoryDao().queryBuilder();
        queryBuilder.orderDesc(AccountHistoryDao.Properties.Id);
        return queryBuilder.list();
    }
}
