
# 连接openfire配置
1,部署项目
配置sharegroup_rest.properties中的属性
2.检查openfire
   2.1插件是否已经成功安装REST API插件
   2.2服务器->服务器设置->REST-API
     Enabled - REST API requests will be processed.
     Secret key auth - REST API authentication over specified secret key.
     Allowed IP Addresses:
     以上3个参数是否已经设置，是否与外围调用服务配置一至

