# bluvalt-java-adapter
A Java implementation of Bluvalt Delivery Platform Standard to use as starting point for services integration

# Integrate a JAVA application with Bluvalt

#Overview
The Bluvalt Service Provider Adapter (Bluvalt-SPA) Framework allows a service provider to on board a service with minimum effort. 
The framework is extensible that allows to further customize to suit a particular business need. 
We’d like to think that our default adapter implementations should be sufficient enough to suit your needs but we also know that every business has unique requirements. 
This framework will allow you to build your own adapters and can easily integrate with Bluvalt.

The following are the main components of Bluvalt-SPA
## Authentication 
provides Open ID Connect SSO for User Id against Bluvalt Connect.

## Bluvalt Event Listener 
Event handler that listens and understands events coming from Bluvalt. The handler will create java object based on Event type and made it available in the application for further processing.
Here is the Basic Flow for Event Listener

### Application Prerequisite
The Bluvalt-SPA Framework is built entirely using Java as the programming language.  To get started developers are expecting to have experience with the following technologies.
Understanding of the Java programming language.
Understating of JSON.
Worked with Maven and Eclipse,intellij etc.
Developers having experience with the following technologies is greatly helpful
Understanding of Spring Framework (Spring MVC, Spring Security).
Understanding of Hibernate.
Worked with GSON
### Application Structure
The application uses Maven for compiling and application life cycle. The project contains the pom.xml and it already defined the needed dependencies. 
Bluvalt-SP adapter is intended to be deployed on Servlet container like Tomcat, Glassfish etc. The Application follows standard Spring MVC framework structure. Please see http://docs.spring.io/spring-framework/docs/current/spring-framework-reference/html/mvc.html for details.


### Authentication
Authentication is provided by using Spring Security. User authentication is accomplished by a custom Authentication filter com.stcs.spa.auth.OpenIDCAuthenticationFilter which extends org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter  class.
This custom filter will be invoked for any access to the service by the end User. It is a processor of browser-based HTTP-based authentication requests and is configured in the file spa-dispatcher-servlet.xml as below
<bean id="openIdConnectAuthenticationFilter"
class="com.stcs.spa.auth.OpenIDCAuthenticationFilter"> 
        <property name="authenticationManager" ref="opAuthenticationManager" />
</bean>
 
The class uses bluvalt-spa\src\main\java\config.properties to get the properties for a particular service provider. The service providers have to update these properties before running the application. Below are the properties required to be filled in.
 
redirect_uri=http://redirect_url
client_id=881c5907-c7e8-483b-a8b8-510528aaf4da
discovery_endpoint=http://sso.cloudbeta1.stcs.com.sa/connect/.well-known/openid-configuration
client_secret=X7bxjjXpUr3kZ4bbZ1Pwp_6oEMHM5-h5CDp0bgy3erflui_-
event_reponse_url=http://86.60.0.90/v1/events/{event_id}/
 
### Authentication Process
 
The filter requires that you set the authenticationManager property. An AuthenticationManager is required to process the authentication request tokens created by implementing classes.
This filter will intercept a request and attempt to perform authentication from that request if the request matches the setRequiresAuthenticationRequestMatcher(RequestMatcher).
Authentication is performed by the attemptAuthentication method, which must be implemented by subclasses. In Bluvalt-SPA case the subclass is com.stcs.spa.auth.OpenIDCAuthenticationFilter
In attemptAuthentication  actual  authentication is performed.
The implementation should do one of the following:
Return a populated authentication token for the authenticated user, indicating successful authentication
Return null, indicating that the authentication process is still in progress. Before returning, the implementation should perform any additional work required to complete the process.
Throw an AuthenticationException if the authentication process fails
 
No Change is needed by developer to do authentication, Once the config properites is filled. The application should be able to authenticate against Bluvalt Open ID Connect.
If the service application have a particular URL Pattern which the SP want to use it as secure resources build for Service consumer. The SP can create a http namespace like below, Here any url starting with /app will need authentication from Bluvalt open ID.
security:http pattern="/app/*" auto-config="false"
              entry-point-ref="authenticationEntryPoint" use-expressions="true">       
security:http>
 
### Extending Bluvalt-SP Adapter
SPServices.java is an interface can be implemented to perform service specific operation. A default implementation DefaultSPServices.java is provided it can be completely discarded and replaced.  
There are three methods that needed to be implemented by the service provider class that implements SPServices:

handleEvent method will be called by the event listener and will parse and understand the event. The service providers have to call their custom code from here. Once the event is parsed the SP will take the necessary actions based on the event type.
The second method processEvent.The actual processing of the Event Occurs here. The input for this method could be anything. It is the implementor choice to pass whatever he wants.   For Automated processing of Event this Method can be called from HandleEvent method to propagate the Event status to SDP. The method have to call sendEventResponse to update the Event status on SDP.
The third method is validateUser. This method is called by the OpenIDCAuthenticationFilter to validate the incoming user requesting authentication. Here the Service provider have to call thier internal user registry (DB,LDAP etc.) to verify if the user is valid. The method return Boolean indicating if the user is valid or not.
