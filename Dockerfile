FROM buildpack-deps:xenial-curl

MAINTAINER CScanner

RUN echo "deb http://mirrors.tuna.tsinghua.edu.cn/ubuntu/ xenial main restricted universe multiverse\ndeb http://mirrors.tuna.tsinghua.edu.cn/ubuntu/ xenial-security main restricted universe multiverse\ndeb http://mirrors.tuna.tsinghua.edu.cn/ubuntu/ xenial-updates main restricted universe multiverse\ndeb http://mirrors.tuna.tsinghua.edu.cn/ubuntu/ xenial-backports main restricted universe multiverse\ndeb http://mirrors.tuna.tsinghua.edu.cn/ubuntu/ xenial-proposed main restricted universe multiverse\ndeb-src http://mirrors.tuna.tsinghua.edu.cn/ubuntu/ xenial main restricted universe multiverse\ndeb-src http://mirrors.tuna.tsinghua.edu.cn/ubuntu/ xenial-security main restricted universe multiverse\ndeb-src http://mirrors.tuna.tsinghua.edu.cn/ubuntu/ xenial-updates main restricted universe multiverse\ndeb-src http://mirrors.tuna.tsinghua.edu.cn/ubuntu/ xenial-backports main restricted universe multiverse\ndeb-src http://mirrors.tuna.tsinghua.edu.cn/ubuntu/ xenial-proposed main restricted universe multiverse" >/etc/apt/sources.list

RUN apt-get update && \
apt-get install -y --no-install-recommends apt-utils \
openjdk-8-jdk-headless && \
rm -rf /var/lib/apt/lists/*

COPY tmp/EmbeddingLearning /scanner

ENV PATH /scanner/Bash:${PATH}
ENV LANG C.UTF-8

WORKDIR /workspace
ENTRYPOINT ["/bin/sh", "/scanner/bin/cscanner"]
