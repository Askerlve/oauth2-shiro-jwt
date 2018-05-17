# 接口说明，支持两种方式，两套接口
```
1.使用JWT生成Token，使用shiro实现鉴权
```
```
2.使用oauth2生成token，spring security实现鉴权
```

## 使用JWT生成Token，使用shiro实现鉴权

#### UMS返回参数说明
名称|类型|说明
---|---|---
type |int|请求状态(0:失败;1:成功)
messageCode|int|详情请移步错误码page
message|String|提示信息
result|Object|结果集

#### 获取token
```
POST /shiro/auth/token
```
输入参数|必须|类型|中文描述
---|---|---|---
applicationKey|yes|String|需要登录的项目key
userName|yes|String|用户名
password|yes|String|密码

返回Result|类型|中文描述
---|---|---
token|String|使用JWT生成的Token

>请求示例

```
curl -X POST "http://localhost:8030/auth/token" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"applicationKey\": \"urule\", \"password\": \"123456\", \"userName\": \"askerlve\"}"
```

>返回示例

```
{
  "type": 1,
  "messageCode": 200,
  "message": "操作成功!",
  "result": {
    "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhcHBsaWNhdGlvbktleSI6InVydWxlIiwiaXNzIjoiY21zIiwidXNlck5hbWUiOiJhc2tlcmx2ZSIsImV4cCI6MTUyNDYyNjQzMSwiaWF0IjoxNTI0NjI2MTMxfQ.q7HP26HAUVw7X7MF2Kjgqu49ky5DWJJAPR4xHAfuZ2Q"
  }
}
```

#### 获取权限列表

```
GET /shiro/auth/list/permission
```

输入参数|必须|类型|中文描述
---|---|---|---
Authorization|yes|String|认证Token

返回Result|类型|中文描述
---|---|---
id|int|资源id
parentId|int|父节点id
applicationId|int|所属系统id
name|String|资源名称
code|String|资源code
url|String|资源地址
description|String|资源描述
type|int|资源类型(0:系统;1：菜单;2:API)
icon|String|图标
order|int|排序
status|int|状态(0:禁用 1:启用 -3:垃圾桶 -4:删除)
createTime|Date|创建时间
createUserId|int|创建人id
updateTime|Date|更新时间
updateUserId|int|更新者id

> 请求示例

```$xslt
curl -X GET "http://localhost:8030/auth/list/permission" -H "accept: */*" -H "Authorization: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhcHBsaWNhdGlvbktleSI6InVydWxlIiwiaXNzIjoiY21zIiwidXNlck5hbWUiOiJhc2tlcmx2ZSIsImV4cCI6MTUyNDYyNjQzMSwiaWF0IjoxNTI0NjI2MTMxfQ.q7HP26HAUVw7X7MF2Kjgqu49ky5DWJJAPR4xHAfuZ2Q"
```

> 返回示例

```$xslt
{
  "type": 1,
  "messageCode": 200,
  "message": "操作成功!",
  "result": [
    {
      "id": 1,
      "parentId": null,
      "applicationId": 1,
      "name": "决策系统",
      "code": "DSQ",
      "url": "/urule",
      "description": "决策系统",
      "type": 0,
      "icon": null,
      "order": 2,
      "status": null,
      "createTime": "2018-04-20 17:14:34",
      "createUserId": 1,
      "updateTime": "2018-04-20 17:14:34",
      "updateUserId": 1
    },
    {
      "id": 4,
      "parentId": null,
      "applicationId": 1,
      "name": "用户管理",
      "code": "userManager",
      "url": "/urule/user",
      "description": "决策系统用户管理",
      "type": 1,
      "icon": null,
      "order": 0,
      "status": null,
      "createTime": "2018-04-24 18:39:51",
      "createUserId": 1,
      "updateTime": "2018-04-24 18:39:51",
      "updateUserId": 1
    }
  ]
}
```

#### 判断当前用户是否有权限

```$xslt
POST /shiro/auth/judge/permission
```

输入参数|必须|类型|中文描述
---|---|---|---
applicationKey|yes|String|需要登录的项目key
Authorization|yes|String|认证Token
urlAddress|yes|String|需要鉴权的地址

返回Result|类型|中文描述
---|---|---
isAllowed|boolean|true:有权限；false:无权限

>请求示例

```$xslt
curl -X POST "http://localhost:8030/auth/judge/permission" -H "accept: */*" -H "Authorization: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhcHBsaWNhdGlvbktleSI6InVydWxlIiwiaXNzIjoiY21zIiwidXNlck5hbWUiOiJhc2tlcmx2ZSIsImV4cCI6MTUyNDYzOTMzNSwiaWF0IjoxNTI0NjM5MDM1fQ.zA2J0GvIQGGp4tszhf515u-pFYRDwuU8zXuGK9AGo6I" -H "Content-Type: application/json" -d "{ \"urlAddress\": \"/urule\"}"
```

>返回示例

```$xslt
{
  "type": 1,
  "messageCode": 200,
  "message": "操作成功!",
  "result": {
    "isAllowed": true
  }
}
```

## 使用oauth2生成Token，使用sping security实现鉴权

#### UMS返回参数说明
名称|类型|说明
---|---|---
type |int|请求状态(0:失败;1:成功)
messageCode|int|详情请移步错误码page
message|String|提示信息
result|Object|结果集

#### 获取token
```
POST /oauth/login
```
输入参数|必须|数据类型|参数类型|中文描述
---|---|---|---|---
Authorization|yes|String|header|Basic + " " + (客户端用户名 + ":" + 客户端密码进行base64编码)
Content-Type|yes|String|header|请求入参方式,只支持application/x-www-form-urlencoded或者form-data
username|yes|String|body|用户名
password|yes|String|body|密码

返回Result|数据类型|中文描述
---|---|---
additionalInformation|json对象|扩展对象
additionalInformation.jti|string|token唯一标识
expiration|long|过期时间戳
expired|boolean|是否过期
expiresIn|int|多少秒以后过期
refreshToken|json对象|刷新token信息
refreshToken.expiration|long|过期时间戳
refreshToken.value|string|refreshToken值
scope|string数组|授权作用域
tokenType|string|token类型
value|string|access_token值

>请求示例

```
curl -X POST \
  http://localhost:8030/oauth/login \
  -H 'Authorization: Basic dXJ1bGU6dXJ1bGU=' \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -d 'username=askerlve&password=123456'
```

>返回示例

```
{
    "message": "操作成功!",
    "messageCode": 200,
    "result": {
        "additionalInformation": {
            "jti": "a5bf41d7-3913-440f-8936-7b81485abd57"
        },
        "expiration": 1525423241889,
        "expired": false,
        "expiresIn": 599,
        "refreshToken": {
            "expiration": 1526718641889,
            "value": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MjY3MTg2NDEsInVzZXJfbmFtZSI6ImFza2VybHZlIiwianRpIjoiY2M3MGViODItMmI2My00NzcyLWE1ZTgtZjM2ZWFkNjM3YjBlIiwiY2xpZW50X2lkIjoidXJ1bGUiLCJzY29wZSI6WyJhbGwiXSwiYXRpIjoiYTViZjQxZDctMzkxMy00NDBmLTg5MzYtN2I4MTQ4NWFiZDU3In0.InynNTN3WBSaX8XD1QevKTFiQx65l_8lKFYt-HldOvI"
        },
        "scope": [
            "all"
        ],
        "tokenType": "bearer",
        "value": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MjU0MjMyNDEsInVzZXJfbmFtZSI6ImFza2VybHZlIiwianRpIjoiYTViZjQxZDctMzkxMy00NDBmLTg5MzYtN2I4MTQ4NWFiZDU3IiwiY2xpZW50X2lkIjoidXJ1bGUiLCJzY29wZSI6WyJhbGwiXX0.ugnpz0GvN0l42zdk9tOd7MvS3PvVIm4ZNe0f7bWrxh0"
    },
    "type": 1
}
```

#### oauth2方式刷新token

```
POST /oauth/token
```

输入参数|必须|数据类型|参数类型|中文描述
---|---|---|---|---
Authorization|yes|String|header|Basic + " " + (客户端用户名 + ":" + 客户端密码进行base64编码)
Content-Type|yes|String|header|请求入参方式,只支持application/x-www-form-urlencoded或者form-data
grant_type|yes|String|body|操作类型
refresh_token|yes|String|body|刷新token的值

返回Result|数据类型|中文描述
---|---|---
access_token|String|access_token值
jti|string|token唯一标识
expiresIn|int|多少秒以后过期
refreshToken|string|刷新token信息
scope|string|授权作用域
tokenType|string|token类型

>请求示例

```
curl -X POST \
  http://localhost:8030/oauth/token \
  -H 'Authorization: Basic dXJ1bGU6dXJ1bGU=' \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -d 'grant_type=refresh_token&refresh_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MjY3MTg2NDEsInVzZXJfbmFtZSI6ImFza2VybHZlIiwianRpIjoiY2M3MGViODItMmI2My00NzcyLWE1ZTgtZjM2ZWFkNjM3YjBlIiwiY2xpZW50X2lkIjoidXJ1bGUiLCJzY29wZSI6WyJhbGwiXSwiYXRpIjoiYTViZjQxZDctMzkxMy00NDBmLTg5MzYtN2I4MTQ4NWFiZDU3In0.InynNTN3WBSaX8XD1QevKTFiQx65l_8lKFYt-HldOvI'
```

>返回示例

```
{
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MjU0MjQ0MTQsInVzZXJfbmFtZSI6ImFza2VybHZlIiwianRpIjoiYjAyODYzZmYtOTkwYy00OTNjLWFmZTktZjA4ZTBiYTgxZWU1IiwiY2xpZW50X2lkIjoidXJ1bGUiLCJzY29wZSI6WyJhbGwiXX0.Xat3d42TDTZpglFR7kL4wVwnA6JjHSP8HS1x_GESoE8",
    "token_type": "bearer",
    "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MjY3MTg2NDEsInVzZXJfbmFtZSI6ImFza2VybHZlIiwianRpIjoiY2M3MGViODItMmI2My00NzcyLWE1ZTgtZjM2ZWFkNjM3YjBlIiwiY2xpZW50X2lkIjoidXJ1bGUiLCJzY29wZSI6WyJhbGwiXSwiYXRpIjoiYjAyODYzZmYtOTkwYy00OTNjLWFmZTktZjA4ZTBiYTgxZWU1In0.--hu5-nLW_zcUVydksreihQV30aetLlxJsXwcYQXveA",
    "expires_in": 599,
    "scope": "all",
    "jti": "b02863ff-990c-493c-afe9-f08e0ba81ee5"
}
```

#### 自定义方式刷新token

```
POST /oauth/auth/refresh
```

输入参数|必须|数据类型|参数类型|中文描述
---|---|---|---|---
Authorization|yes|String|header|Basic + " " + (客户端用户名 + ":" + 客户端密码进行base64编码)
refresh_token|yes|String|body|刷新token的值

返回Result|数据类型|中文描述
---|---|---
access_token|String|access_token值
jti|string|token唯一标识
expiresIn|int|多少秒以后过期
refreshToken|string|刷新token信息
scope|string|授权作用域
tokenType|string|token类型

>请求示例

```
curl -X POST "http://localhost:8030/oauth/auth/refresh" 
-H "Authorization: Basic dXJ1bGU6dXJ1bGU=" 
-H "Content-Type: application/json" 
-d "{ \"refreshToken\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1Mjc2NzU4OTEsInVzZXJfbmFtZSI6ImFza2VybHZlIiwianRpIjoiNjBlM2YxOGQtNWFhMS00NjcwLWFiMGYtMGUwODc5ZWE5YjhiIiwiY2xpZW50X2lkIjoidXJ1bGUiLCJzY29wZSI6WyJhbGwiXSwiYXRpIjoiYTA0YTM1MzYtMGU3ZS00MDU1LTlmY2MtZjIzZTQ2NmU3Yzc3In0.xB7ciV8syq79VHLYOEM9R365J7VSPXdL0b0oPog1T4c\"}"
```

>返回示例

```
{
  "type": 1,
  "messageCode": 200,
  "message": "操作成功!",
  "result": {
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MjYzODA1MDksInVzZXJfbmFtZSI6ImFza2VybHZlIiwianRpIjoiNTM0ZDVhZDItMGQwMS00YjI5LTk2NTctYzU1YjlmZjIwNTBhIiwiY2xpZW50X2lkIjoidXJ1bGUiLCJzY29wZSI6WyJhbGwiXX0.l8NrCs8C0YLkD3qC797JEtED3_PMD689wzj30DvujWs",
    "token_type": "bearer",
    "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1Mjc2NzU4OTEsInVzZXJfbmFtZSI6ImFza2VybHZlIiwianRpIjoiNjBlM2YxOGQtNWFhMS00NjcwLWFiMGYtMGUwODc5ZWE5YjhiIiwiY2xpZW50X2lkIjoidXJ1bGUiLCJzY29wZSI6WyJhbGwiXSwiYXRpIjoiNTM0ZDVhZDItMGQwMS00YjI5LTk2NTctYzU1YjlmZjIwNTBhIn0.PT0Zkg1ftH1CSXGbkUp02iZRr68iqfmkI2xIHBzPtM8",
    "expires_in": 599,
    "scope": "all",
    "jti": "534d5ad2-0d01-4b29-9657-c55b9ff2050a"
  }
}
```

#### 登出
```$xslt
GET /oauth/logout
```

输入参数|必须|数据类型|参数类型|中文描述
---|---|---|---|---
Authorization|yes|String|header|Bearer + " " + token

返回Result|数据类型|中文描述
---|---|---
none|none|none


>请求示例

```$xslt
curl -X GET \
  http://localhost:8030/oauth/logout \
  -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MjU2NjE1MzEsInVzZXJfbmFtZSI6ImFza2VybHZlIiwianRpIjoiN2UyOWQ2YzQtYjQ4MC00YzI3LWIyY2EtN2YzZDgwY2E3N2RiIiwiY2xpZW50X2lkIjoidXJ1bGUiLCJzY29wZSI6WyJhbGwiXX0.Esj5eovax8H3uK9df8CID12ia9YkUPMWIgpa7zl7-gQ'
```

>返回示例

```$xslt
{
    "message": "操作成功!",
    "messageCode": 200,
    "type": 1
}
```

#### 获取权限列表

```$xslt
POST /oauth/auth/list/permission
```

输入参数|必须|数据类型|参数类型|中文描述
---|---|---|---|---
Authorization|yes|String|header|Bearer + " " + token
applicationKey|yes|String|body|要拉取的项目key

返回Result|类型|中文描述
---|---|---
id|int|资源id
parentId|int|父节点id
applicationId|int|所属系统id
name|String|资源名称
code|String|资源code
url|String|资源地址
description|String|资源描述
type|int|资源类型(0:系统;1：菜单;2:API)
icon|String|图标
order|int|排序
status|int|状态(0:禁用 1:启用 -3:垃圾桶 -4:删除)
createTime|Date|创建时间
createUserId|int|创建人id
updateTime|Date|更新时间
updateUserId|int|更新者id

>请求示例

```$xslt
curl -X POST "http://localhost:8030/oauth/auth/list/permission" \
    -H "accept: */*" \
    -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MjU2NjQxNTgsInVzZXJfbmFtZSI6ImFza2VybHZlIiwianRpIjoiMTUxNWUzM2MtYjdhNS00OWRjLTg2MWItMTdjZDc1Y2YwMWUxIiwiY2xpZW50X2lkIjoidXJ1bGUiLCJzY29wZSI6WyJhbGwiXX0.cyGqSJDThvvlqokRdHSl1fMtraVAJm5O3XGSjDiKMtk" \
    -H "Content-Type: application/json" \
    -d "{ \"applicationKey\": \"urule\"}"
```

>返回示例

```$xslt
{
  "type": 1,
  "messageCode": 200,
  "message": "操作成功!",
  "result": [
    {
      "id": 1,
      "parentId": 0,
      "applicationId": 1,
      "name": "决策系统",
      "code": "DSQ",
      "url": "/urule",
      "description": "决策系统",
      "type": 0,
      "icon": "tbd",
      "order": 2,
      "status": 1,
      "createTime": "2018-04-20 17:14:34",
      "createUserId": 1,
      "updateTime": "2018-04-20 17:14:34",
      "updateUserId": 1
    },
    {
      "id": 4,
      "parentId": 1,
      "applicationId": 1,
      "name": "用户管理",
      "code": "userManager",
      "url": "/urule/user",
      "description": "决策系统用户管理",
      "type": 1,
      "icon": "tbd",
      "order": 0,
      "status": 1,
      "createTime": "2018-04-24 18:39:51",
      "createUserId": 1,
      "updateTime": "2018-04-24 18:39:51",
      "updateUserId": 1
    },
    {
      "id": 5,
      "parentId": 4,
      "applicationId": 1,
      "name": "用户新增",
      "code": "userAdd",
      "url": "/urule/user/add",
      "description": "决策系统用户新增",
      "type": 2,
      "icon": "tbd",
      "order": 0,
      "status": 1,
      "createTime": "2018-04-24 18:46:15",
      "createUserId": 1,
      "updateTime": "2018-04-24 18:46:15",
      "updateUserId": 1
    }
  ]
}
```

#### 判断当前用户是否有权限

```$xslt
POST /oauth/auth/judge/permission
```

输入参数|必须|数据类型|参数类型|中文描述
---|---|---|---|---
Authorization|yes|String|header|Bearer + " " + token
applicationKey|yes|String|body|要判断的url所属项目key
urlAddress|yes|String|body|要判断的url

返回Result|类型|中文描述
---|---|---
isAllowed|boolean|true:有权限；false:无权限

>请求示例

```$xslt
curl -X POST "http://localhost:8030/oauth/auth/judge/permission" \
    -H "accept: */*" 
    -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MjU2NjQxNTgsInVzZXJfbmFtZSI6ImFza2VybHZlIiwianRpIjoiMTUxNWUzM2MtYjdhNS00OWRjLTg2MWItMTdjZDc1Y2YwMWUxIiwiY2xpZW50X2lkIjoidXJ1bGUiLCJzY29wZSI6WyJhbGwiXX0.cyGqSJDThvvlqokRdHSl1fMtraVAJm5O3XGSjDiKMtk" 
    -H "Content-Type: application/json" 
    -d "{ \"applicationKey\": \"urule\", \"urlAddress\": \"/urule\"}"
```

>返回示例

```$xslt
{
  "type": 1,
  "messageCode": 200,
  "message": "操作成功!",
  "result": {
    "isAllowed": true
  }
}
```


