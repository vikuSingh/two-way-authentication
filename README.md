
#Welcome to Two-way Authentication
#Procedure to Run this application

Go to the src/main/java -->  com.vikas.twowayauthentication -- > open TwoWayAuthenticationApplication.java
Right click on that and do Run as Java Application.
 
Once server is get start Pls. try to access this application with below address : 

   http://localhost:8080/login
   if your want to change is server port then please add this below command in application.yaml available src/main/resources
 spring
   server
     port: <your desire port>


#####################################
Files
#####################################
1. open application.yaml location --> src/main/resources/
2. change your database name, username and password with your’s

    password: your-password
    url: jdbc:mysql://localhost:3306/your-database-name
    username: your-username


3. do the change in smtp server with your’s account details in application.yaml
 
    host: smtp.gmail.com // this is valid for gmail. if you are using some other smtp server then changes accordingly 
    password: your-password
    port: 587
    properties:
      mail:
        smtp:
          auth: true
          connectiontimeout: 5000
          starttls:
            enable: true
          timeout: 5000
          writetimeout: 5000
    username: your-username


4. change google client id and client secrets with your’s
    
       google:
            clientId: -- your google client id --
            clientSecret: -- your google client secrets --

5. change facebook client id and client secrets with your’s

         facebook:
            clientId: -- your facebook client id --
            clientSecret: -- your facebook client secrets --


7. In fecture inhancement add other social media account in the application.yaml file

8. Ready to go.

 
 ![twowaylogin](https://github.com/vikuSingh/two-way-authentication/assets/20941580/a134adcb-6532-4ff7-a1b5-7faa67d69abc)
![twootppage](https://github.com/vikuSingh/two-way-authentication/assets/20941580/23e2c6b8-9f41-4bf2-aacf-d20e52b44745)
![twodashboard](https://github.com/vikuSingh/two-way-authentication/assets/20941580/b2fa865e-edf6-4c81-b2a4-e99bead241ff)
![otppage](https://github.com/vikuSingh/two-way-authentication/assets/20941580/4c8b04e6-e118-4dac-ba4b-1304ffcf9366)
![twowayRegistration](https://github.com/vikuSingh/two-way-authentication/assets/20941580/c79332a8-2448-46d7-8cd0-4c664009adfe)
![twowaylogin](https://github.com/vikuSingh/two-way-authentication/assets/20941580/105d9543-5f66-4746-ab61-6140f143545c)




