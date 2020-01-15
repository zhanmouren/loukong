FROM harbor.tools.krdev.gdhwater.com/base/jdk:1.8
MAINTAINER dengjunj <dengjunj@gdhmail.com>
RUN mkdir /work
copy target/ebs*.jar /work/ebs_app.jar
ENV config_center_url http://10.13.1.248:14200
ENV tarant gdhk
ENV app app_manage
EXPOSE 9999
CMD ["java","-jar","/work/ebs_app.jar"]