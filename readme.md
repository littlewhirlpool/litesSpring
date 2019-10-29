# 从零开始造spring

    包含IOC AOP功能

## IOC
    控制反转(Inversion Of Control) 面向对象编程的一种设计原则,降低耦合,常见方式DI(Dependency Injection)
    
    
这里提供三种方式实现DI
1. XML配置 bean的<property>
2. XML配置 bean的<constructor-arg>
3. 注解 



功能点:
通过配置文件得到bean的定义
通过配置文件得到bean
通过配置文件的属性配置设置bean属性的setter注入
通过配置文件的构造配置设置bean属性的setter注入
通过注解得到bean的定义
通过注解注入属性(字段方式不支持方法/构造)
