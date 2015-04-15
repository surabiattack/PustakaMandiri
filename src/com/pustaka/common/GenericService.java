/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pustaka.common;

import java.util.Date;

/**
 *
 * @author dwi
 */
public class GenericService {
    protected void updateCreationInfo(BaseAudit entity, String user) {
            Date currentTime = new Date();
            entity.setCreateBy(user);
            entity.setCreateDate(currentTime);
    }

    protected void updateLastModInfo(BaseAudit entity, String user) {
            Date currentTime = new Date();
            entity.setUpdateBy(user);
            entity.setUpdateDate(currentTime);
    }
}
