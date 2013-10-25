package com.tentelemed.archipel.infra;

import org.hibernate.cfg.ImprovedNamingStrategy;

/**
 * Stratégie de nommage des tables et colonnes pour éviter les conflits de nom qq soit la bdd
 *
 * Created by IntelliJ IDEA.
 * User: msi
 * Date: 14 févr. 2011
 * Time: 16:51:17
 */
public class NamingStrategy extends ImprovedNamingStrategy {
    @Override
    public String classToTableName(String tableName) {
        return "t_"+super.tableName(tableName);
    }

    @Override
    public String propertyToColumnName(String columnName) {
        return "c_"+super.columnName(columnName);
    }

    @Override
    public String foreignKeyColumnName(String propertyName, String propertyEntityName, String propertyTableName, String referencedColumnName) {
        return "f_"+super.foreignKeyColumnName(propertyName, propertyEntityName, propertyTableName, referencedColumnName);
    }

    @Override
    public String joinKeyColumnName(String joinedColumn, String joinedTable) {
        return "f_"+super.joinKeyColumnName(joinedColumn, joinedTable);
    }

    @Override
    public String collectionTableName(String ownerEntity, String ownerEntityTable, String associatedEntity, String associatedEntityTable, String propertyName) {
        return "t_"+super.collectionTableName(ownerEntity, ownerEntityTable, associatedEntity, associatedEntityTable, propertyName);
    }

}
