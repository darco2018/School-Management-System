# School manager
An application for running a school. 
The application was started as the final project during my participation in a **`Coders Lab` Java backend course**.

#### Current functionality
##### Entities
* Student - can be added, deleted, edited
* Grade - can be added
* Teacher - can be added, deleted, edited
* Subject - can be added, deleted, edited
* School form (class) - can be added, deleted, edited

##### Manipulations
* A Teacher can be linked with a Subject 
* A Teacher/Subject can be linked with a School form to create a Lesson (eg Maths-John Brown-1A(First Year)
* A Student can be linked with a School form
* A Grade can be linked with a Student

##### Views
* School form panel - you can view all Subject/Teacher pairs 
* School form panel - ou can view all Students in a given School form
* Teacher panel - you can view all Subjects and School forms for a given Teacher 
* Grade panel - you can view all Subject/Teacher/Schoolform trios
* Grade panel - you can view all Students with their Grades in a given School form 

##### Security
* There are 6 user roles : ADMIN, SCHOOLADMIN, DEV, STUDENT, TEACHER, PARENT - connected with the following paths:
* /dev/, /admin/, /schooladmin/, /studentuser/, /teacheruser/, /parentuser/
* form logging is enabled
* password hashed with JCrypt
* custom UserDetailsService to enable adding custom properties to Spring Security USerDetails kept in session
* with <form:form> tag and @EnableWebSecurity the CsrfToken is automatically included 

#### Technologies and tools used
```
* Spring Boot 2.0.1, Spring-Core 5, Spring-Webmvc 5, Spring-Data 2, Spring Security
* Hibernate 5, Hibernate Validator 6
* JUnit 4, AssertJ 3, Mockito 2
* JSP
* Bootstrap 3, HTML, CSS
* MySQL, H2
* Tomcat
* Maven, Spring Tool Suite
* Lombok
* SpotBugs, SonarLint,
```
#### NOTE
The project may or may not be continued as I picked up this project as an exercise to practice a few new (for me) points:
- Spring SEcurity
- inheritance mapping with Hibernate
- biderectional many-to-many relations with a link table
- soft deletions
- integration and unit testing for controller, service, JPA, repository layers
- H2 for testing
- DTO between service and front-end
- repositories extending a base repository