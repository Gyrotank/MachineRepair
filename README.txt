PROJECT OVERVIEW
----------------

The project is a web-application simulating the operation of a machine repairing company.

There are three types of actors: clients, managers and administrators.

Clients (all new users who sign up become clients by default) can create orders (either first-time 
or repeated if they need to repair the machine they have had repaired before) and pay when the order 
is ready.

Managers can confirm/decline pending (newly created) orders and set orders status to 'ready', which 
is an indication that it's time for the client to pay for them.

Administrators can view the contents of the database and manually create and edit new entities (users, clients,
serviceable machines, machines, repair types and orders), manage users' credentials by assigning/removing their  
roles. They can also do everything managers can.



USED TOOLS AND TECHNOLOGIES
---------------------------

Maven 3.2.2
Spring MVC Framework 4.1.0
Spring Security 3.2.5
Hibernate 4.2.8
Log4j 1.2.17
JUnit 4.11



DEPLOYING AND RUNNING THE PROJECT
---------------------------------

In order to run the project, you will need:

1. Web server: Tomcat 7,8
2. JRE 1.8
3. Database server (MySQL recommended)
4. Maven 3.2.2

Steps to deploy and run the project:
1. Download the project and unzip it

2. Prepare Tomcat application server
2.1. Open file CATALINA_HOME/conf/tomcat-users.xml
2.2. If you haven't already, create the user with roles 'manager-gui' and 'manager-script'. This is done by adding 
the required information to the block designated by <tomcat-users>...</tomcat-users> tags.
For example:
<tomcat-users ...>
	<role rolename="manager-script" />
	<role rolename="manager-gui" />
	<user username="manager" password="pass" roles="manager-gui,manager-script" />
</tomcat_users>
2.3. Save the file

3. Preparation of Maven 
3.1. Open the file MAVEN_HOME/conf/settings.xml
3.2. Add information about your Tomcat server inside the <servers>...</servers> tags. For example:
  <servers>
  ...
  	<server>
		<id>localhost-Tomcat7</id>
		<username>maven</username>
		<password>maven</password>
	</server>
  </servers>
3.3. Save the file.

4. Preparation of pom.xml
4.1. In pom.xml find the following block of properties and set the correct parameters 
corresponding to your web server and database settings.
For example:
	<properties>
		<db.name>machinerepair</db.name>
		<db.driver>com.mysql.jdbc.Driver</db.driver>
		<db.url>jdbc:mysql://localhost/</db.url>
		<db.url.name>${db.url}${db.name}</db.url.name> 
		<db.username>root</db.username>
		<db.password>root</db.password>
		<db.dialect>org.hibernate.dialect.MySQLDialect</db.dialect>
		<tomcat.url>http://localhost:8080</tomcat.url>
		<tomcat.deploy.url>${tomcat.url}/manager/text</tomcat.deploy.url>
		<tomcat.deploy.server>localhost-Tomcat7</tomcat.deploy.server>
	</properties>
4.2. Save this file

5. Start Tomcat 

6. Go to the root folder of the project

7. Run maven using the command: 
mvn db:drop db:create db:schema db:data clean tomcat7:deploy

This command will drop the database if it exists, create the database if it doesn't exist, create 
the database tables and fill them with the data according to SQL scripts located in folders specified in
<dbSchemaScriptsDirectory> and <dbDataScriptsDirectory> tags respectfully.

8. If you need to fully redeploy the project, use next command:
mvn db:drop db:create db:schema db:data clean tomcat7:redeploy

9. If you don't need or want to reinitialize the DB, use the following command:
mvn clean tomcat7:redeploy


USING THE APPLICATION
---------------------

You can test the application's functionality by using predefined user credentials, such as:

login:   | password:  | role
------------------------------------
admin    | kokoko     | Administrator
manager1 | koko       | Manager
proletar | qwerty     | User with active and finished orders
мао      | мао        | User with cyrillic login and password 
         |            | and name in Chinese (for UTF-8 testing)