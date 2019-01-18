
<h1 align="center">
  <br>
  <img src="https://github.com/ihuaylupo/assets/raw/master/illy.png" alt="Illary Huaylupo"></a>
  <br>
 
  <br>
</h1>

<h4 align="center">Amazon cognito integration with Java Spring Boot Application</h4>

<p align="center">
  <a href="https://github.com/ihuaylupo/cognitoAuthentication">
    <img src="https://travis-ci.org/codecentric/springboot-sample-app.svg?branch=master"
         alt="Gitter">
  </a>
  <a href="http://www.apache.org/licenses/LICENSE-2.0.html">
    <img src="http://img.shields.io/:license-apache-blue.svg">
  </a>
</p>

<p align="center">
  <a href="#key-features">Blog Post</a> •
  <a href="#initial-configuration">Initial Configuration</a> •
  <a href="#how-to-use">How To Use</a> •
  <a href="#services">Services</a> •
  <a href="#api-call-examples">API Call Examples</a> •
  <a href="#download">Contact</a> •
  <a href="#contributing">Contributing</a> •
  <a href="#license">License</a>
</p>

![screenshot](https://github.com/ihuaylupo/assets/raw/master/screenshot.png)

## Blog Post

[Java Integration with Amazon Cognito](https://gorillalogic.com/blog/java-integration-with-amazon-cognito/)

## Initial Configuration

* Add your AWS Credentials at src/main/resources/AwsCredentials.properties

<p align="center" >
AWS Config - Retrieve Access Key and Secret Key
<img src ="https://github.com/ihuaylupo/assets/raw/master/AWSConfig.png" /> <br/>
AWS Credentials - Spring Boot Application
&nbsp;&nbsp;&nbsp;&nbsp;<img src ="https://github.com/ihuaylupo/assets/raw/master/AWS%20Credentials%20File.png"/></p>


* Add your cognito pool and identity pool data.
<p align="center" >
<label align="center">AWS Config - Retrieve User Pool ID and Client ID  </label>
<img src ="https://github.com/ihuaylupo/assets/raw/master/Identity-Pool-Options-4.png" />
<p/>
<p align="center" >
<label align="center">AWS Config - Retrieve Identity Pool ID </label>
<img src ="https://github.com/ihuaylupo/assets/raw/master/IdentityPoold.png" />
<p/>
<p align="center" >
<label align="center">AWS Config Property File - Spring Boot Application</label>
<img src ="https://github.com/ihuaylupo/assets/raw/master/aws.properties.png" />
</p>

## How To Use

To clone and run this application, you'll need [Git](https://git-scm.com), [Maven](https://maven.apache.org/), [Java 8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html). From your command line:

```bash
# Clone this repository
$ git clone https://github.com/ihuaylupo/cognitoAuthentication

# Go into the repository
$ cd cognitoAuthentication

# Install dependencies
$ mvn install

# Run the app
$ java -jar target/CognitoAuthentication-0.0.1-SNAPSHOT.jar
```
## Services

My code contains the following services against Amazon Cognito:

* Login
* SignUp
* Sign Up confirmation
* Add User to group
* Delete User
* Sign Out
* Reset Password
* Confirm Reset Password

## API Call Examples

Login call 
![login](https://github.com/ihuaylupo/assets/raw/master/postman%20login.png)

Sample Code
![sampleCode](https://github.com/ihuaylupo/assets/raw/master/OAUTH2%20Security%20Controller.png)

## Contact

I'd like you send me an email on <illaryhs@gmail.com> about anything you'd want to say about this software or you can write me at the blog post.
I'd really appreciate it!

### Contributing
Feel free to file an issue if it doesn't work for your code sample. Thanks.


## License

Copyright (c) 2018 Illary Huaylupo
Licensed under the MIT license.

---
