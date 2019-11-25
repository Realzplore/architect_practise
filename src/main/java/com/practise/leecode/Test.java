package com.practise.leecode;


import lombok.Data;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.StringUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author: realz
 * @package: com.practise
 * @date: 2019-08-23
 * @email: zlp951116@hotmail.com
 */
@Data
public class Test {
    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();

    private static String sqlTemplate = "insert into art_resource_detail (parent_id, resource_name, resource_type, is_enabled, is_deleted, created_date, last_modified_date, created_by, last_modified_by, category_type, resource_id, module_type, `key`, default_readable, default_enabled, resource_scene, resource_image, resource_order, resource_comment) values ({0},{1},{2},1,0,now(),now(),{3},{4},{5},{6},null,{7},1,1,0,null,1,null);\n";
    private static String selectIdSqlTemplate = "select @id:= rd.id from art_resource_detail rd where rd.resource_id = {0} limit 1;\n";
    private static String languageSqlTemplate = "insert into art_resource_detail_i18n (id, language, resource_name) values (@id,{0},{1});\n";
    private static String permissionInitSqlTemplate = "insert into art_resource_permission_init (id, function_code, permission_code, default_readable, created_by, created_date) values ({0},{1},{2},1,{3},now());\n";
    private static String funcPermissionDetailInsertSqlTemplate = "insert into art_func_permission_detail " +
            "select UUID_SHORT(),fpd.tenant_id,{0},{1},1,0,now(),now(),{2},{3},fpd.permission_id,{4} from art_func_permission_detail fpd " +
            "where fpd.permission_id not in (select fpd1.permission_id from art_func_permission_detail fpd1 where fpd1.function_code = {5}) and fpd.function_code = {6} group by permission_id;\n";

    public static void main1(String[] args) {
        buildResourceDetailSqlFile();
//        buildFuncPermissionDetailSqlFile();
    }

    private static void buildFuncPermissionDetailSqlFile() {
        File file = new File("/Users/realz/Downloads/汇联易-中控菜单翻译、权限控制信息收集 (1).xlsx");
        File sqlFile = new File("/Users/realz/Downloads/funcPermissionDetail.sql");

        try (Workbook workbook = WorkbookFactory.create(file);
             OutputStream out = new FileOutputStream(sqlFile)) {
            Sheet sheet = workbook.getSheetAt(1);
            Map<String, String> resourceKeyMap = new HashMap<>();
            for (Row row : sheet) {
                if (row.getRowNum() < 1 || row.getPhysicalNumberOfCells() < 8) {
                    continue;
                }
                String menuName = row.getCell(0).getStringCellValue();
                String key = row.getCell(2).getStringCellValue();
                double resourceIdNumber = row.getCell(3).getNumericCellValue();
                String level = row.getCell(4).getStringCellValue();
                String permissionCode = row.getCell(7).getStringCellValue();

                if (StringUtils.isEmpty(menuName)) {
                    continue;
                }
                String operationType = row.getCell(8).getStringCellValue();

                DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
                String resourceId = decimalFormat.format(resourceIdNumber);

                //put into map
                resourceKeyMap.put(resourceId, key);

                //菜单注释
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("-- ").append(menuName).append(" key : ").append(key).append(", resourceId : ").append(resourceId).append(", permissionCode : ").append(permissionCode).append("\n");
                if ("三级菜单".equals(level)) {
                    String parentId = resourceId.substring(0, resourceId.length() - 4);
                    String parentKey = resourceKeyMap.get(parentId);
                    String grandParentId = parentId.substring(0, parentId.length() - 4);
                    String grandParentKey = resourceKeyMap.get(grandParentId);

                    String grantParentInsertSql = MessageFormat.format(funcPermissionDetailInsertSqlTemplate, grandParentId, "'RW'", "'1'", "'1'", "'" + grandParentKey + "'", "'" + grandParentKey + "'", "'" + key + "'");
                    String parentInsertSql = MessageFormat.format(funcPermissionDetailInsertSqlTemplate, parentId, "'RW'", "'1'", "'1'", "'" + parentKey + "'", "'" + parentKey + "'", "'" + key + "'");

                    stringBuilder.append(grantParentInsertSql);
                    stringBuilder.append(parentInsertSql);
                }

                stringBuilder.append("\n");
                out.write(stringBuilder.toString().getBytes());
            }
        } catch (InvalidFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void buildResourceDetailSqlFile() {
        File file = new File("/Users/realz/Downloads/汇联易-中控菜单翻译、权限控制信息收集 (1).xlsx");
        File resourceDetailSqlFile = new File("/Users/realz/Downloads/resourceDetail.sql");
        File resourcePermissionInitSqlFile = new File("/Users/realz/Downloads/resourcePermissionInit.sql");

        try (Workbook workbook = WorkbookFactory.create(file);
             OutputStream out = new FileOutputStream(resourceDetailSqlFile);
             OutputStream initOut = new FileOutputStream(resourcePermissionInitSqlFile)) {
            Sheet sheet = workbook.getSheetAt(1);

            Map<String, List<String>> permissionInitMap = new LinkedHashMap<>();
            permissionInitMap.put("superAdmin", new ArrayList<>());
            permissionInitMap.put("basicOperation", new ArrayList<>());
            permissionInitMap.put("superFinance", new ArrayList<>());
            permissionInitMap.put("ticketCommissioner", new ArrayList<>());
            permissionInitMap.put("reservationSupervisor", new ArrayList<>());
            permissionInitMap.put("dataSpecialist", new ArrayList<>());
            permissionInitMap.put("receiptCollector", new ArrayList<>());

            for (Row row : sheet) {
                if (row.getRowNum() < 1) {
                    continue;
                }
                String menuName = row.getCell(0).getStringCellValue();
                String menuEnName = row.getCell(1).getStringCellValue();
                String key = row.getCell(2).getStringCellValue();
                double resourceIdNumber = row.getCell(3).getNumericCellValue();
                String resourceType = row.getCell(5).getStringCellValue();
                String permissionType = row.getCell(6).getStringCellValue();
                String permissionCode = row.getCell(7).getStringCellValue();

                if (StringUtils.isEmpty(menuName)) {
                    continue;
                }

                if (!StringUtils.isEmpty(permissionCode)) {
                    if (permissionCode.contains("|")) {
                        String[] permissionCodeList = permissionCode.split("\\|");
                        for (int i = 0; i < permissionCodeList.length; i++) {
                            permissionInitMap.get(permissionCodeList[i]).add(key);
                        }
                    }else {
                        permissionInitMap.get(permissionCode).add(key);
                    }

                }

                DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
                String resourceId = decimalFormat.format(resourceIdNumber);

                if (StringUtils.isEmpty(permissionType)) {
                    permissionType = "DEFAULT";
                }

                //计算parentId
                String parentId = "0";
                if (resourceId.length() > 4) {
                    parentId = resourceId.substring(0, resourceId.length() - 4);
                }


                //菜单注释
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("-- ").append(menuName).append("\n");

                //insert语句
                String insertSql = MessageFormat.format(sqlTemplate, parentId, "'" + menuName + "'", "'" + resourceType + "'", "'1'", "'operation'", "'" + permissionType + "'", resourceId, "'" + key + "'");
                stringBuilder.append(insertSql);

                //select id语句
                String selectIdSql = MessageFormat.format(selectIdSqlTemplate, resourceId);
                stringBuilder.append(selectIdSql);

                //多语言sql语句
                String cnSql = MessageFormat.format(languageSqlTemplate, "'zh_cn'", "'" + menuName + "'");
                stringBuilder.append(cnSql);
                String enSql = MessageFormat.format(languageSqlTemplate, "'en'", "'" + menuEnName + "'");
                stringBuilder.append(enSql);

                System.out.println("-- " + menuName + "sql构建完成。");

                out.write(stringBuilder.toString().getBytes());
            }

            int id = 1;
            for (Map.Entry entry : permissionInitMap.entrySet()) {
                String permissionCode = (String) entry.getKey();
                List<String> initList = (List<String>) entry.getValue();
                if (CollectionUtils.isEmpty(initList)) {
                    continue;
                }
                for (int i = 0; i < initList.size(); i++) {
                    String initSql = MessageFormat.format(permissionInitSqlTemplate, id++, "'" + initList.get(i) + "'", "'" + permissionCode + "'", "'1'");
                    initOut.write(initSql.getBytes());
                    System.out.println(initList.get(i));
                }
            }

        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    private static void buildResourcePermissionInitSqlFile() {
        File file = new File("/Users/realz/Downloads/汇联易-中控菜单翻译、权限控制信息收集 (1).xlsx");
    }
}
