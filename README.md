# cmsclient
android编写的客户端
大二实训作业

项目需求：
软件需求介绍
课程管理系统是一款面向学生、教师、管理员的用于管理课程的软件系统。面向学生提
供学生课程查看、课表查看、选课等功能；面向教师提供教师课程查看、课表查看、课程
设、课程维护等功能；面向管理员提供用户管理、班级管理等功能。系统支持用户通过客户
端软件登录到系统，使用客户端软件进行各项操作，支持多用户的同时操作。系统的主要功
能有：
1) 用户登录
学生、教师、管理员通过用户名和密码登录到系统，用户名为学号或者工号。不同类型
的用户登录后进入相应的模块，如学生登录后进入学生模块、教师登录后进入教师模块、管
理员登录后进入管理员模块。
2) 用户注册
新用户在使用系统前需注册用户，提供用户名（学号或工号）、姓名、密码、用户类型
（学生或教师）、班级（当用户为学生时）、电话。用户提交注册信息后，等待管理员审核通
过后才能使用系统。
3) 用户审核
管理员审批新注册用户，审核结果为通过或不通过。若审核通过，用户可登录使用系统；
若审核不通过，用户无法登录使用系统。
4) 学生课程（课表）查看
学生课程列表展示学生的所有课程，课程信息包含：课程编号、课程名、课
程类型（选修或必修）、学分、使用教材、任课教师、上课地点、上课时间。
课表查看通过课表的形式展示学生的所有课程，时间范围为周一至周日，每
日的课程范围为第 1 次课至第 9 次课。
5) 学生选课
学生可以先搜索、查看可选的选修课，再进行选课。学生通过已选课程列表
查看已选的课程，可以取消已选的课程。
6) 教师课程（课表）查看
教师课程列表展示教师的所有开设课程，包括必修课和选修课。教师能够查看课程的详
细信息，包括必修课的班级、选修课的学生信息等。
教师课表展示教师开设的课程，形式与学生课表一致。
7) 教师创建课程
教师能创建必修课或选修课，创建课程要提供课程的基本信息：课程编号、课程名、课
程类型（选修或必修）、学分、使用教材、任课教师、上课地点、上课时间。必修课程要提
供开课班级，可以是单个或多个班级。
8) 教师课程维护
教师能够维护自己开设的课程，包括修改课程和删除课程。修改课程中可以修改：学分、
26
教材、上课地点、上课时间、班级（必修课）。
9) 用户管理
管理员使用此功能管理用户，包括查询用户、创建用户、修改用户和删除用户。管理员
可以先查询用户，再进行用户修改或删除操作。
查询用户支持通过多个条件查询用户，包括：用户类型、学号或工号、姓名和班级。查
询条件中：用户类型为学生或教师；学号、工号、班级查询为精确匹配；姓名查询为模糊匹
配。若有多个查询条件，条件之间的关系为“与”逻辑。若指定班级查询，则展示该班级的
所有学生。
创建用户包括创建学生和教师用户。
修改用户支持修改用户姓名、班级（当用户为学生时）、密码。
10) 班级信息维护
管理员通过此功能维护班级信息，包括创建班级、查看班级信息和删除班级。