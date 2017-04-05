# Liquibase功能实现及测试说明

1. 在主项目hap的中添加了com/hand/hap/db/liquibase.groovy这个入口脚本
2. 不同的数据库类型需求可以在com/hand/hap/db/liquibase.groovy中修改
3. 所有数据库相关的内容位于db子项目中
4. db子项目的liquibase脚本位于com/hand/hap/db目录下,脚本分为两类
    a. 建表 以table-migration.groovy结尾
    b. 初始化数据脚本,以data-migration.groovy结尾
5. db下的data可以存放各数据库的建表及初始化数据SQL,各类型数据库的脚本文件数量,及名称必须相同.


## 测试

### Oracle

修改com/hand/hap/db/liquibase.groovy的类型为oracle,然后执行(db的各参数请根据实际情况修改)
mvn process-resources -D skipLiquibaseRun=false -D db.driver=oracle.jdbc.driver.OracleDriver -D db.url=jdbc:oracle:thin:@192.168.115.136:1521:HAP -Ddb.user=hap_dev -Ddb.password=hap_dev

### Mysql

修改com/hand/hap/db/liquibase.groovy的类型为mysql,然后执行(db的各参数请根据实际情况修改)
mvn process-resources -D skipLiquibaseRun=false -D db.driver=com.mysql.jdbc.Driver -D db.url=jdbc:mysql://127.0.0.1:3306/hap2 -Ddb.user=root -Ddb.password=root
