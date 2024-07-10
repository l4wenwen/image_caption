# ICAP (Image-Caption) 
基于图神经网络的图像描述系统。

## 后端接口实现

### 主要目录结构
```
├─src
│  ├─main
│  │  ├─java
│  │  │  └─com
│  │  │      └─example
│  │  │          └─imagecaptionbackend
│  │  │              │  ImageCaptionBackendApplication.java
│  │  │              │
│  │  │              ├─config
│  │  │              │      SwaggerConfig.java
│  │  │              │
│  │  │              ├─controller
│  │  │              │      ImageController.java
│  │  │              │      UserController.java
│  │  │              │
│  │  │              ├─entity
│  │  │              │      Image.java
│  │  │              │      User.java
│  │  │              │      UserImage.java
│  │  │              │
│  │  │              ├─repository
│  │  │              │      ImageRepository.java
│  │  │              │      UserImageRepository.java
│  │  │              │      UserRepository.java
│  │  │              │
│  │  │              └─service
│  │  │                      ImageService.java
│  │  │                      Test.java
│  │  │                      UserService.java
│  │  │
│  │  └─resources
│  │      │  application.properties
│  │      │  convert.py
│  │      │
│  │      └─image_caption
│  │          核心代码
│  │
│  └─test
└─target

```
主要采用 `Spring Boot + Jpa` 框架，方便快捷地执行数据库方法。

### 代码文件功能

在 `src/main/resources` 目录下：
1. `application.properties`是 Spring 应用的配置文件，主要配置连接数据库的地址、驱动、用户名和密码等。
2. `convert.py` 是转换脚本，将 base64 字符串转换为图片。
3. `image_caption` 即为核心代码目录。

在`src/main/java/com/example/imagecaptionbackend` 目录下：
1. `ImageCaptionBackendApplication.java`：Spring 应用的启动文件。
2. `config`：配置目录.
    1. `SwaggerConfig.java`：Swagger 配置，用于自动生成 API 文档。
3. `controller`：控制器目录。
    1. `ImageController.java`: 创建 RESTful 风格的图像管理接口。
    2. `UserController.java`: 创建 RESTful 风格的用户管理接口。
4. `entity`: 实体目录，Jpa 框架中的目录。
    1. `Image.java`: 图像实体，关联数据库中的图像信息表 image。
    2. `User.java`: 用户实体，关联数据库中的用户信息表 user。
    3. `UserImage.java`: 图像-用户实体，关联数据库中的图像-用户关系信息表 user_image。
5. `repository`: 仓库目录，Jpa 框架中的目录。
    1. `ImageRepository.java`: 图像仓库，继承了 `JpaRepository<Image,Long>`，能够调用 Jpa 中现有的方法执行数据库操作，管理 image 表。
    2. `UserRepository.java`: 同理，管理 user 表。
    3. `UserImageRepository.java`: 同理，管理 user_iamge 表.
6. `service`: 服务目录。
    1. `ImageService.java`: 实现图像管理有关的方法，实现了图像管理接口中的功能。
    2. `UserService.java`: 实现用户管理有关的方法，实现了用户管理接口中的功能。

### 后端系统启动
1. 配置核心代码需要的 python 环境，建议使用 conda。
2. 注意修改 `ImageService.java`中的代码，将其中有关路径改为运行机器中的路径：
```java
pb = new ProcessBuilder("D:\\App\\anaconda\\envs\\ICAP\\python.exe",
        "D:\\Projects\\ICAP\\Image-Caption-backend\\src\\main\\resources\\image_caption\\main.py");
pb = pb.directory(
        new File("D:\\Projects\\ICAP\\Image-Caption-backend\\src\\main\\resources\\image_caption"));
```
3. 在后端代码根目录 `Image-Caption-backend` 下，终端中执行如下指令启动 spring 应用：
```bash
mvn install
mvn spring-boot:run
```
4. 若终端最末一行出现类似如下输出，则启动成功：
```bash
Started ImageCaptionBackendApplication in 3.685 seconds (JVM running for 4.07)
```