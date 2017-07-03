# Shell guide
### A Simple Demo
```shell
1  #!/bin/sh
2  cd ~
3  mkdir littleShell
4  cd littleShell
5  for((i=0;i<10;i++));do
6        touch test_$i.txt
7  done
```
#### 示例解释
- 第1行：指定脚本解释器，这里用的是/bin/sh
- 第2行：切目录到当前用户的home 目录
- 第3行：新建文件夹
- 第4行：进入该目录
- 第5行：开始for循环
- 第6行：新建文件
- 第7行：结束

其中cd,mkdir,touch都是系统自带的命令，一般在/bin 或者/usr/bin 下面。
for do done 则是sh脚本语言的关键词。
### shell 和shell脚本的概念
shell 指的是一种应用程序，这个应用程序提工了一个界面，通过这个界可以访问操作系统的内核服务。
***shell脚本（shell script），是一种为shell编写的脚本程序。***
### 运行环境
shell编程只需要一个文本编辑器，以及一个能解释执行的解析器即可。
### OS
目前主流的操作系统都支持shell编程，linux默认安装了shell解释器。
## Mac os
Mac OS不仅带了***sh、bash***这两个最基础的解释器，还内置了ksh、csh、zsh等不常用的解释器。
### 脚本解释器
##### sh
即Bourne shell，POSIX（Portable Operating System Interface）标准的shell解释器，它的二进制文件路径通常是/bin/sh，由Bell Labs开发。
##### bash
Bash是Bourne shell的替代品，属GNU Project，二进制文件路径通常是/bin/bash。
### 高级语言
理论上，一种语言提供了解释器（而不仅仅是编译器），这门语言就可以胜任脚本编程.
Python这些年也成了一些linux发行版的预置解释器。

编译型语言，只要有解释器，也可以用作脚本编程，Java有第三方解释器Jshell.

### 如何选择shell编程语言

你只是想做一些备份文件、安装软件、下载数据之类的事情，学着使用sh，bash会是一个好主意。
shell只定义了一个非常简单的编程语言，所以，如果你的脚本程序复杂度较高，或者要操作的数据结构比较复杂，那么还是应该使用***Python***这样的脚本语言

### 环境兼容性
如果你的脚本是提供给别的用户使用，使用sh或者bash，你的脚本将具有最好的环境兼容性
python这些年也成了一些linux发行版的标配，
***至于mac os，它默认安装了perl、python、ruby、php、java等主流编程语言。***

## 我的第一个 shell
新建一文件扩张名可以为sh(并不影响执行，见到知道代表啥就可以)

输入的第一行一般为：
```shell
#!/bin/bash
```
“***#!***”是一个约定的标记，它告诉系统这个脚本需要什么解释器来执行。
### 运行shell
- 第1种：作为可执行程序
```shell
chmod +x test.sh
./test.sh
```
注意哦：一定要写成./test.sh，而不是test.sh，运行其它二进制的程序也一样，直接写test.sh，linux系统会去PATH里寻找有没有叫test.sh的，而只有/bin, /sbin, /usr/bin，/usr/sbin等在PATH里，你的当前目录通常不在PATH里，所以写成test.sh是会找不到命令的，要用./test.sh告诉系统说，就在当前目录找。
通过这种方式运行bash脚本，第一行一定要写对，好让系统查找到正确的解释器。
- 第2种：作为解释器参数运行
这种运行方式是，直接运行解释器，其参数就是shell脚本的文件名，如：
```shell
/bin/sh test.sh
```
这种方式运行的脚本，不需要在第一行指定解释器信息，写了也没用处。

## 变量
### 定义变量
定义变量时直接定义，如：
```shell
name="jaf"
```
注意：变量名和等号之间不能有空格哦
还可以用语句赋值
```shell
for file in `ls /etc`
```
###使用变量
使用定义过的变量，只要在变量名前面加美元符号即可，如：
```shell
name="jaf"
echo name
echo ${name}
```
变量名外面的花括号是可选的，加不加都行，加花括号是为了帮助解释器识别变量的边界，比如下面这种情况：
```shell
for skill in Ada Coffe Action Java; do
	echo "I am good at ${skill}Script"
done
```
***推荐给所有变量加上花括号***

###重定义变量
```shell
name="jaf"
echo $name
name="jaf2"
echo $name
```
###注释
以“#”开头的行就是注释，会被解释器忽略。

###双引号
```shell
name="jaf"
str="Hello, I know your are \"$name\"! \n"
```
##字符串操作
```shell
name="jaf"
str="hello, ${name} !"
echo $str
```
###获取字符串长度
```shell
#结果为：3
str="jaf"
echo ${#str}
```
###截取字符串
```shell
str="i love China"
echo ${str:1:3}
```
##流程控制
注意：
sh里的if [ $foo -eq 0 ]
因为方括号在这里是一个可执行程序，方括号后面必须加空格

###if else
####if
```shell
if condition
then
	command1 
	command2
fi
```
写成一行（适用于终端命令提示符）：
```shell
if `ps -ef | grep ssh`;  then echo hello; fi
```
####if else
```shell
if condition
then
	command1 
	command2
else
	command
fi
```
####for
```shell
for var in item1 item2 ... itemN
do
	command1
	command2
done
```
写成一行：
```shell
for var in item1 item2 ... itemN; do command1; command2… done;
```
####while
```shell
while condition
do
	command
done
```
####while true
```shell
while true
do
	command
done
```