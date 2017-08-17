数据迁移的步骤：

1.从2.0版本的GCS数据库中导出所有有效的企业客户号；文件命名为info.txt

2.从2.0版本的Hbase数据库中导出所有表格的数据；接着导入到1.1.x版本的hbase数据库中

3.运行测试用例MigrateDataServiceTest中的testMigrateData()方法