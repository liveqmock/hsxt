异常处理使用
1.全局异常web.xml 配置错误页面 404.jsp、500.jsp；
2.错误码全局定义；
3.所有控制层必须 extends BaseController类，BaseController 类处理异常返回对应的错误码ajax请求必须根据错误码处理（可根据错误码判断重定向至ErrorController控制层返回对应的错误提示页面）；
4.Service 实现类必须try catch 数据层（Mapper）抛出异常，根据异常抛出 数据层异常类（MapperException）；
5.Service 实现类根据业务可抛出Service层异常，异常类（ServiceException）；
6.可根据需要自定义异常类，异常类必须 extends BaseException类，子类必须添加构造函数参数（错误码）；
7.所有抛出异常类均BaseException类的子类，抛出子类必须填写对应的错误码；