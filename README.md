# easyjob
分布式系统作业调度系统 easyjob by yanghr

# 功能：将项目中api包打包jar 放入分布式系统中， 将要执行的异步作业等继承job类，登陆api中的url 
# 即可观察job执行状态 ，同时调度系统发送kakfa,业务系统 监听topic，执行job.run方法开始作业。
（v1.0大致要完成的内容）

# 2019.11.10

至此平时零零总总抽了些时间 写一点 算一点。。。就算当做自己练手吧 有想法就慢慢实现它
虽然不是第一个造轮子的人 但在开发过程中或多或少有点收获。


# 2020.6.29 
目前架构已经搭好， api controller servcice dao 关于提交job作业的代码基本上写的差不多了
差kafka监听 执行job那一大块还没开工，目前考虑的command类大致在设计中
审计那块还没做， 今日上传git发布到远程仓库。

