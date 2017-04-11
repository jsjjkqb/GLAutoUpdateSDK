# 更新日志

### 2017-4-4 更新

- v1.0.2 发布
- 支持格式Rxjava2,json格式：
    {    
    
          "code": 0,
          "data": {
                "download_url": "http://musicfire-log.oss-cn-hangzhou.aliyuncs.com/G-NetLink/gl_debug/auto_update_apk/G-netlink_debug.apk",
                
                "force": true,
                 "update_content": "海量更新内容，请尽情体验",
                 "v_code": "10",
                 "v_name": "v1.0.0.16070810",
                 "app_name": "G-netlink_debug.apk",
                 "v_sha1": "7db76e18ac92bb29ff0ef012abfe178a78477534",
                 "v_size": 12365909
             }
      }

- 使用方式请看文档

### 2017-4-7更新:

- v1.1.4 生成 -SNAPSHOT 发布
- 支持Rxjava1
- 支持获取下载进度
- 处理上一版本使用的广播阻塞引起的卡顿
- 具备下载功能
- 使用方式请查看文档
- 取消兼容v1.0.2的接口，json格式修改为：

{

    "code": 0,
    "data": {
        "android": [
            {
                "download_url": "http://musicfire-log.oss-cn-hangzhou.aliyuncs.com/G-NetLink/gl_debug/auto_update_apk/G-netlink_debug.apk",
                "force": false,
                "update_content": "海量更新内容，请尽情体验",
                "v_code": "10",
                "v_name": "v1.0.0.16070810",
                "app_name": "G-netlink_debug.apk",
                "v_sha1": "7db76e18ac92bb29ff0ef012abfe178a78477534",
                "v_size": 12365909,
                "app_id": "package_id"
            }
        ],
        "iOS": [
            {
                "download_url": "http://musicfire-log.oss-cn-hangzhou.aliyuncs.com/G-NetLink/gl_debug/auto_update_apk/G-netlink_debug.apk",
                "force": false,
                "update_content": "海量更新内容，请尽情体验",
                "v_code": "10",
                "v_name": "v1.0.0.16070810",
                "app_name": "G-netlink_debug.apk",
                "v_sha1": "7db76e18ac92bb29ff0ef012abfe178a78477534",
                "v_size": 12365909,
                "app_id": "bundle_id"
            }
        ]
    }
}


### 2017-4-11更新:
- v1.1.5  发布
- 修复下载的notification抢占主进程的
- 进行6.0运行时权限判断，添加监听
- 使用方式请查看文档

###  更新中。。。。。


