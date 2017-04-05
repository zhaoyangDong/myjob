package com.hand.hap.liquibase

import org.springframework.core.io.support.PathMatchingResourcePatternResolver

/**
 * Created by hailor on 16/4/11.
 */
/**
 * Created by hailor on 16-3-9.
 */
class MigrationHelper {

    private static final INSTANCE = new MigrationHelper()

    static getInstance() { return INSTANCE }

    private MigrationHelper() {}

    //数据文件存储路径
    def copyToFolderPath = ""

    def currentDbType = ""


    def printClassPath(classLoader) {
        println "$classLoader"
        classLoader.getURLs().each { url ->
            println "- ${url.toString()}"
        }
        if (classLoader.parent) {
            printClassPath(classLoader.parent)
        }
    }

    def dbmigrate = { dbType="mysql",loadPlugins= ["com/hand/hap"], migrateType = ["table"] ->
        def projectName = "hapdbmigration"
        def dataMigrations = [:]
        def tableMigrations = []
        def dataScriptMigrations = []
        currentDbType = dbType
        copyToFolderPath = System.getProperty("java.io.tmpdir") + File.separator + projectName
        if (new File(copyToFolderPath).exists()) {
            new File(copyToFolderPath).deleteDir()
        }
        new File(copyToFolderPath).mkdirs()

        printClassPath this.class.classLoader

        System.properties.each { k, v ->
            println "$k = $v"
        }

        loadPlugins.each {
            //加载主项目Migration
            HashMap<String, ArrayList> result = prepare(it, dbType, migrateType)
            tableMigrations = tableMigrations + result.tableMigrations
            dataMigrations = dataMigrations + result.dataMigrations
            dataScriptMigrations = dataScriptMigrations+result.dataScriptMigrations
        }

        def dataMigrationsStr = dataMigrations.collect{key,value-> "${key}@${value}"}


        (tableMigrations+dataScriptMigrations).each {
            println("Loading file:" + it)
            include(file: it)
        }


    }


    private void copyFile(def fromStream, String to) {
        File toFile = new File(to);
        File parent = toFile.getParentFile();
        if (!parent.exists() && !parent.mkdirs()) {
            throw new IllegalStateException("Couldn't create dir: " + parent);
        }
        toFile << fromStream
        if (!toFile.exists()) {
            throw new IllegalStateException("Couldn't copy file to: " + to);
        }
    }

    private String getTargetPath(String origin) {
        return origin.split(".jar!")[1]
    }


    private HashMap<String, ArrayList> prepare(String plugin, String dbType, ArrayList<String> migrateType) {
        def dataMigrations = [:]
        def dataScriptMigrations = []
        def tableMigrations = []

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        if ((migrateType.contains("table") || migrateType.contains("all"))) {
            resolver.getResources("classpath*:" + plugin + "/**/db/" + "/*-table-migration.groovy").each {
                def targetPath = copyToFolderPath + getTargetPath(it.getURL().getPath())
                println("Copy file:" + it+" to:"+targetPath)
                copyFile(it.getInputStream(), targetPath)
                tableMigrations << targetPath
            }
        }

        if ((migrateType.contains("patch") || migrateType.contains("all"))) {

            resolver.getResources("classpath*:" + plugin + "/**/db/patch/**/*.sql").each {
                def targetPath = copyToFolderPath + getTargetPath(it.getURL().getPath())
                println("Copy SQL file:" + it+" to:"+targetPath)
                copyFile(it.getInputStream(), targetPath)
                dataMigrations[targetPath] = plugin
            }

            resolver.getResources("classpath*:" + plugin + "/**/db/" + "/*-patch.groovy").each {
                def targetPath = copyToFolderPath + getTargetPath(it.getURL().getPath())
                println("Copy file:" + it+" to:"+targetPath)
                copyFile(it.getInputStream(), targetPath)
                dataScriptMigrations << targetPath
            }
        }

        if ((migrateType.contains("data") || migrateType.contains("all"))) {

            resolver.getResources("classpath*:" + plugin + "/**/db/data/**/*.sql").each {
                def targetPath = copyToFolderPath + getTargetPath(it.getURL().getPath())
                println("Copy SQL file:" + it+" to:"+targetPath)
                copyFile(it.getInputStream(), targetPath)
                dataMigrations[targetPath] = plugin
            }

            resolver.getResources("classpath*:" + plugin + "/**/db/data/*.xlsx").each {
                def targetPath = copyToFolderPath + getTargetPath(it.getURL().getPath())
                println("Copy Excel file:" + it+" to:"+targetPath)
                copyFile(it.getInputStream(), targetPath)
                dataMigrations[targetPath] = plugin
            }

            resolver.getResources("classpath*:" + plugin + "/**/db/" + "/*-data-migration.groovy").each {
                def targetPath = copyToFolderPath + getTargetPath(it.getURL().getPath())
                println("Copy file:" + it+" to:"+targetPath)
                copyFile(it.getInputStream(), targetPath)
                dataScriptMigrations << targetPath
            }
        }




        return [dataScriptMigrations:dataScriptMigrations,tableMigrations: tableMigrations, dataMigrations: dataMigrations]
    }

    //工具函数
    String dataPath(String path) {
        (new File(copyToFolderPath + File.separator + path)).path
    }

    String dataPathWithType(String path) {
        (new File(copyToFolderPath + File.separator+currentDbType+ File.separator + path)).path
    }

    Boolean isDbType(String dbtype) {
        currentDbType.equals(dbtype)
    }

    String dbType() {
        currentDbType
    }
}
