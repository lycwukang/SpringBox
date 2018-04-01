SpringBox是什么
=
SpringBox可以将基于Spring框架的项目进行模块化的打包运行，尤其适用于多个项目的合并运行，各个项目之间可以维持独立的ApplicationContext生命周期。
<br>
SpringBox项目需要一个`容器`，容器可以是空的SpringMVC项目，也可以是包含业务功能的SpringMVC项目，容器中可以包含多个`插件`项目。
<br>
容器为插件创建新的ApplicationContext上下文，插件会把自己的bean注册进容器中，这样就在容器中提供了插件的入口，入口一般是@Controller，插件把自己的@Controller注册进容器中，容器在被tomcat运行时就可以调用插件暴露出来的接口。
<br>
SpringBox还提供了容器与模块之间的访问通道，因为插件会把自己的bean注册进容器中，所以在容器中可以使用@Resource或者@Autowrite获取到插件中定义的bean并使用他们。
<br>
插件本身也可以包含其他插件而成为其他插件的容器，容器也可以引用插件的插件。SpringBox只会为每个插件或者容器创建一个ApplicationContext上下文，当一个插件或者容器被多次引用是，会只创建一个ApplicationContext，多个引用者获取到同一份ApplicationContext。
![SpringBox结构](https://github.com/lycwukang/SpringBox/blob/master/imgs/SpringBox%E7%BB%93%E6%9E%84%E5%9B%BE.png)
