package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper
dbType = MigrationHelper.getInstance().dbType()
databaseChangeLog(logicalFilePath:"patch.groovy"){

    changeSet(author: "xiangyuqi", id: "20161009-xiangyuqi-1") {
        addColumn(tableName:"SYS_USER"){
            column(name:"LAST_LOGIN_DATE",type:"datetime",remarks:"最后一次登录时间")
            column(name:"LAST_PASSWORD_UPDATE_DATE",type:"datetime",remarks:"最后一次修改密码时间")
        }
    }
    changeSet(author: "zhizheng.yang", id: "20161025-zhizheng.yang-1") {
        addColumn(tableName:"SYS_LOV"){
            column(name:"CUSTOM_SQL",type:"clob",remarks:"自定义sql")
        }
    }
    changeSet(author: "zhizheng.yang", id: "20161104-zhizheng.yang") {
        addColumn(tableName:"SYS_LOV"){
            column(name:"QUERY_COLUMNS",type:"int",defaultValue:"1",remarks:"查询框列数")
        }
    }

    changeSet(author: "xiangyu.qi",id:"20161109-xiangyuqi-1"){
        renameColumn (tableName:"SYS_IF_CONFIG_LINE_TL",columnDataType:"VARCHAR(255)",oldColumnName:"HEADER_ID",newColumnName:"LINE_ID")
        addUniqueConstraint(tableName:"SYS_IF_CONFIG_HEADER_B", columnNames:"INTERFACE_CODE", constraintName:"SYS_IF_CONFIG_HEADER_U1")
    }

    changeSet(author: "jialong.zuo",id:"20161110-jialongzuo-1"){
        addUniqueConstraint(tableName:"HR_ORG_UNIT_B", columnNames:"UNIT_CODE", constraintName:"HR_ORG_UNIT_U1")
    }
    changeSet(author: "jialong.zuo",id:"20161129-jialongzuo-1"){
        addColumn(tableName: "SYS_CODE_VALUE_B"){
            column(name:"ORDER_SEQ",type:"int",defaultValue:"10")
        }
    }

    changeSet(author: "xiangyuqi", id: "20161221-xiangyuqi-1") {
        addColumn(tableName:"SYS_USER"){
            column(name:"FIRST_LOGIN",type:"varchar(1)",remarks:"是否第一次登录")
        }
        addDefaultValue(tableName: "SYS_USER",columnName:"FIRST_LOGIN",columnDataType:"varchar", defaultValue:"Y")
    }
    changeSet(author: "zhizheng.yang",id:"20161221-yangzhizheng-1"){
        addColumn(tableName: "SYS_LOV_ITEM"){
            column(name:"CONDITION_FIELD_LABEL_WIDTH",type:"decimal")
        }
    }
    changeSet(author: "zhizhengyang",id:"20170106-yangzhizheng-1"){
        addColumn(tableName: "SYS_LOV"){
            column(name:"CUSTOM_URL",type:"varchar(255)",remarks:"自定义URL")
        }
    }

}
