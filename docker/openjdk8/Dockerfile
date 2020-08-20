FROM ubuntu
LABEL maintainer=zern
# 安装mercurial版本管理工具并下载源码
RUN apt-get update  \
	&& apt-get install -y mercurial \
	&& hg clone http://hg.openjdk.java.net/jdk8u/jdk8u/
RUN cd jdk8u && bash ./get_source.sh 

# 设置个时区，避免下一个命令提示输入时区
RUN echo Asia/Shanghai > /etc/timezone \
    && ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
# 安装编译依赖
RUN apt-get install -y libx11-dev libxext-dev libxrender-dev libxtst-dev libxt-dev libcups2-dev libfreetype6-dev libasound2-dev libfontconfig1-dev ccache make gdb gcc wget unzip zip 

# 下载openjdk7作为boot-jdk
RUN wget -q https://download.java.net/openjdk/jdk7u75/ri/openjdk-7u75-b13-linux-x64-18_dec_2014.tar.gz \
	&& mkdir -p /opt/jdk/ \
    && tar -zxf openjdk-7u75-b13-linux-x64-18_dec_2014.tar.gz -C /opt/jdk/ \
    && rm -rf openjdk-7u75-b13-linux-x64-18_dec_2014.tar.gz
RUN mv jdk8u /opt/jdk/openjdk8u
WORKDIR /opt/jdk/openjdk8u
CMD bash
