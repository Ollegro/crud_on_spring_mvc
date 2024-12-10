FROM tomcat:10.1.33

COPY /target/root.war /usr/local/tomcat/webapps/

