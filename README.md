# GLAutoUpdateSDK
自动化更新sdk

## 自动更新sdk以下的功能完成：

		（1）.自定义通知栏：下载进度显示，完成，安装，
		（2）.提示对话框：更新提示，安装提示，
		（3）.安装：调用进入系统安装界面，
		（4）.对外接口：自定义更新可配置接口和默认更新接口，

## 集成步骤

1. 添加依赖

<--Add it in your root build.gradle at the end of repositories-->

    	allprojects {
		repositories {
			maven { url 'https://jitpack.io' }
		}
	}
    	
<--Add the dependency-->

			dependencies {
					compile 'com.github.User:Repo:Tag'
			}
	eg: compile 'com.github.ecarx-dev:GLAutoUpdateSDK:1.0.1'
 
2. 添加清单文件权限：

<--网络权限-->

       <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
	   
	   <uses-permission android:name="android.permission.INTERNET"/>
      
<--在SDCard中创建与删除文件权限--> 
     
     <--uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/>
     
<-- 往SDCard写入数据权限 -->
      
     <--uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
3. 调用接口

## 检测更新接口地址：

    UpdateConstants.CHECKURL_RELEASE;//上线接口
    UpdateConstants.CHECKURL_DEBUG;//调试接口
    UpdateConstants.CHECKURL_TEST;//测试接口

## 调用方法：
一般自动更新放到MainActivity里面的

	  AutoUpdateSDKManager.init(getApplicationContext(), UpdateConstants.CHECKURL_RELEASE, new GLCallback<Boolean>() {
                @Override
                public void onResult(Boolean aBoolean) {
                    LogTool.d("init onResult : " +aBoolean);
                }
                @Override
                public void onError(int i) {
                    LogTool.d("init  onError : " +i);
                }
                @Override
                public void onException(Throwable throwable) {
                }
            }).check(MainActivity.this);





