FROM tomcat:10.1.26
COPY /target/root.war /usr/local/tomcat/webapps

