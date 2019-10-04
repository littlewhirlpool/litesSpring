package org.litespring.service.v4;

import org.litespring.beans.factory.annotation.Autowired;
import org.litespring.dao.v4.AccountDao;
import org.litespring.dao.v4.ItemDao;
import org.litespring.stereotype.Component;

import javax.xml.ws.soap.MTOM;

/**
 * @program: litespring->PetStoreService
 * @description:
 * @author: weizhenfang
 * @create: 2019-10-01 18:35
 **/
@Component(value = "petStore")
@MTOM(enabled=false,threshold = 6)
public class PetStoreService {
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private ItemDao itemDao;

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public ItemDao getItemDao() {
        return itemDao;
    }
}
