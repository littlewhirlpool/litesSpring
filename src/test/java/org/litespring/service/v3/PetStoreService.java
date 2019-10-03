package org.litespring.service.v3;

import org.litespring.dao.v2.AccountDao;
import org.litespring.dao.v2.ItemDao;

/**
 * @program: litespring->PetStoreService
 * @description:
 * @author: weizhenfang
 * @create: 2019-10-01 18:35
 **/
public class PetStoreService {
    private AccountDao accountDao;
    private ItemDao itemDao;
    private int version;

    public PetStoreService (AccountDao accountDao , ItemDao itemDao  ){
        this.accountDao = accountDao;
        this.itemDao = itemDao;
        this.version = -1;
    }

    public PetStoreService (AccountDao accountDao , ItemDao itemDao , int version){
        this.accountDao = accountDao;
        this.itemDao = itemDao;
        this.version = version;
    }

    public AccountDao getAccountDao() {
        return accountDao;
    }


    public ItemDao getItemDao() {
        return itemDao;
    }



    public int getVersion() {
        return version;
    }

}
