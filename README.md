# EnjoySdk V1.0.0 

## 项目概要
EnjoySdk 是基于杭州迈冲科技 Android 智能设备系统开发的一套方便客户进行二次开发的 SDK。拥有丰富的 Android 硬件控制接口，满足行业应用开发。
EnjoySdk 必须在集成了 EnjoySdk 的 Android 系统中才能使用，为了保持原生 Android 设备的兼容，我们提供了 EnjoyUtil 工具包，用于判断当前系统是否支持 EnjoySdk。

## 功能简介
EnjoySdk 提供多个硬件接口模块供客户二次开发。

各模块功能简介请看 [EnjoySdk 功能简介](https://github.com/Hangzhou-Maichong-Technology/EnjoySdk/tree/main/doc/功能简介.md)。
## 接入方法
### gradle 依赖
1. 在根目录 build.gradle 中添加 JitPack 仓库地址:
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
2. 添加 EnjoySdk 依赖
```
  // 添加 EnjoySDK gradle 依赖
  implementation 'com.github.Hangzhou-Maichong-Technology.Enjoy:enjoySDK:1.0.0'
```

### 本地 aar 依赖
若无法通过网络下载依赖，也可以直接使用 [EnjoySdk aar](https://github.com/Hangzhou-Maichong-Technology/EnjoySdk/tree/main/doc/aar) 。
下载 aar 文件后，放到 `app/libs` 目录中，并在 `build.gradle` 中添加如下代码:
```
  implementation files('libs/enjoySDK.aar')
```

## 快速入门
使用 EnjoySdk 前，需检查 Android 系统是否支持 EnjoySdk。
```
EnjoyUtil.isEnjoySupport();
```
确认支持后，即可使用 EnjoySdk 中的功能。
这里以 `McSecure` 举例说明: 
```
McSecure mcSecure = McSecure.getInstance(this);
int ret = mcSecure.setSecurePasswd("Abc12345", "Abc12345");
mcSecure.registSafeProgram("Abc12345");
```
首先，通过  `getSystemService` 获取 `SecureManager` 服务。服务获取成功后，即可使用该系统服务中提供的 Api 进行开发。
示例代码中，通过服务为迈冲智能设备设置安全密钥，并将当前应用注册为安全应用。
更多详细的代码请见 [EnjoySdk JavaDoc](https://github.com/Hangzhou-Maichong-Technology/EnjoySdk/tree/main/doc)。

![EnjoySdkJavaDoc](https://raw.githubusercontent.com/Hangzhou-Maichong-Technology/EnjoySdk/main/doc/EnjoySdkJavaDoc.png)

## 更新日志
- 2021/05/26: EnjoySdk 1.0.0 正式发布。
  提供丰富的 Android 硬件控制接口。