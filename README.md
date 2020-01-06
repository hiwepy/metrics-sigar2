# metrics-sigar2

Metrics + Hyperic Sigar 实现系统级别的监控，实时获取服务器运行期间CPU、内存、网络等信息

##整合GitHub项目： 
https://github.com/cb372/metrics-sigar
https://github.com/yangengzhe/sigar-system_runtime

##更新Metrics版本 :
https://mvnrepository.com/artifact/io.dropwizard.metrics/metrics-core

##更新 Sigar版本 :
https://mvnrepository.com/artifact/org.hyperic/sigar

#介绍

利用java程序检查服务器或主机的运行时信息，包括操作系统、CPU使用情况、内存使用情况、硬盘使用情况以及网卡、网络信息。主要的办法有两种：第一种，使用jdk1.6以上自动的功能，实现数据的获取，但是该方法局限性较大，而且获得的数据也比较少，尤其是内存信息不够准确。所以，在此不讨论该方法。第二种，使用第三方的jar包进行获取，通过直接调用操作系统的api来获取系统相关数据。

本文则主要介绍一种通过Hyperic-hq产品的基础包sigar.jar来实现服务器状态数据的获取。Sigar.jar包是通过本地方法来调用操作系统API来获取系统相关数据。

http://www.i3geek.com/archives/1184

#Sigar库文件下载：
https://sourceforge.net/projects/sigar/

#Native library

附：底层支持文件与系统对应表

Linux AMD/Intel 32位：libsigar-x86-linux.so
Linux AMD/Intel 64位：libsigar-amd64-linux.so
Linux PowerPC 32位：libsigar-ppc-linux.so
Linux PowerPC 64位： libsigar-ppc64-linux.so
Linux Itanium 64位：libsigar-ia64-linux.so
Linux zSeries 64位：libsigar-s390x-linux.so
Windows AMD/Intel 32位：sigar-x86-winnt.dll
Windows AMD/Intel 64位：sigar-amd64-winnt.dll
AIX PowerPC 32位：libsigar-ppc-aix-5.so
AIX PowerPC 64位：libsigar-ppc64-aix-5.so
HP-UX PA-RISC 32位：libsigar-pa-hpux-11.sl
HP-UX Itanium 64位：libsigar-ia64-hpux-11.sl
Solaris Sparc 32位：libsigar-sparc-solaris.so
Solaris Sparc 64位：libsigar-sparc64-solaris.so
Solaris AMD/Intel 32位：libsigar-x86-solaris.so
Solaris AMD/Intel 64位：libsigar-amd64-solaris.so
Mac OS X PowerPC/Intel 32位：libsigar-universal-macosx.dylib
Mac OS X PowerPC/Intel 64位：libsigar-universal64-macosx.dylib
FreeBSD 5.x AMD/Intel 32位：libsigar-x86-freebsd-5.so
FreeBSD 6.x AMD/Intel 64位：libsigar-x86-freebsd-6.so
FreeBSD 6.x AMD/Intel 64位：libsigar-amd64-freebsd-6.so 



#问题解答

1、mac下eclipse中配置java.library.path

Mac下，eclipse中的配置方法：项目右键->属性->java build path->选择Libraries中的sigar.jar->在展开的Native library location中选择底层文件所在目录即可

2、网络传输速率

jar包中不提供网络传输速率的方法，需要采集两次，自行计算出传输速率

3、局域网中无法连接

在网络传输中，当有多个网卡进行切换时，rmi会出现无法连接的错误。建议更换IP地址访问。

如：服务器处于两个局域网 192.168.191.* 和 172.29.131.* ,客户端处于 192.168.191.* 之中。当客户端连接192.168.191.* 时可用连接，但是响应超时。但是连接 172.29.131.* 时则可以正常使用

