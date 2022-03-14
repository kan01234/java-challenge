# java-challenge

## Local

### Run

run `bootRun`

or run from command line

```bash
./gradlew bootRun
```

the server will start on port 8080

### Swagger UI

http://localhost:8080/webjars/swagger-ui/index.html

## Changes

### Bump Spring Boot version to 2.7.9
Spring boot 2.7.9 is latest version of Spring boot 2.
According to [release note](https://spring.io/blog/2023/02/23/spring-boot-2-7-9-available-now), this version contains some bug fix, documentation improvements, and dependency upgrades.

Here are some reasons for consider upgrading to 2.7.9 of Spring Boot:

1. Security updates: The latest version of Spring Boot includes the latest security patches and updates, which help to protect your application from potential security vulnerabilities.
2. Bug fixes: Each new release of Spring Boot includes bug fixes and other improvements that can help to improve the stability and performance of your application.
3. New features: The latest version of Spring Boot includes new features and enhancements that can improve the functionality of your application and make it easier to develop and maintain.
4. Support: As new versions of Spring Boot are released, older versions may no longer be supported. Upgrading to the latest version ensures that you have access to the latest support and resources.
5. Performance improvements: Each new release of Spring Boot includes performance improvements that can help to speed up your application and reduce resource usage.
6. Dependency updates: The latest version of Spring Boot includes updates to its dependencies, which can improve the compatibility and stability of your application.

### Migrate Spring Webflux

Spring Webflux is a reactive web framework that provides a non-blocking, event-driven programming model for building applications.
Here are some reasons for consider using Spring Webflux:

1. High performance: Spring Webflux is designed to handle high traffic and high load scenarios by using non-blocking I/O and reactive programming. This allows your application to handle more requests per second, reduce latency and improve overall performance.
2. Scalability: Reactive programming model used in Spring Webflux allows your application to scale horizontally with less hardware. This is because it is designed to handle large number of requests with fewer threads, reducing the overhead of context switching.
3. Support for functional programming: Spring Webflux provides support for functional programming, which can make your code more concise, easier to read and maintain. This allows you to write more declarative and reactive code that is easier to reason about.

### Migrate WebFlux Security

WebFlux Security is a security module in Spring WebFlux that provides authentication and authorization support for reactive web applications.
It allows to secure reactive endpoints and provides a range of features to ensure the security of your application.

### Logger

Using a logger instead of System.out has several advantages:

1. Flexibility: A logger provides greater flexibility in controlling log output. You can configure the logger to write logs to a file, to a database, or to the console, among other options. With System.out, you can only write logs to the console.
2. Configurability: A logger allows you to configure the log output based on the level of severity. You can set different log levels for different parts of your code and configure the logger to ignore less severe log messages. This makes it easier to identify and debug issues in your code.
3. Performance: Using System.out to write logs can be slower than using a logger, particularly in high-traffic applications. A logger is designed to be more performant and optimized for writing logs.
4. Debugging: A logger provides a more structured and consistent way of logging information, making it easier to debug your application. You can include additional information in your logs, such as the date and time, thread ID, and exception stack trace, which can be very helpful in identifying and resolving issues.
5. Best practices: Using a logger is considered a best practice in software development. It makes it easier to maintain and manage logs, and it provides a more professional and standardized way of logging information.

### Add Controller Advice

Controller Advice is a feature in Spring Boot that allows to define global error handling for RESTful APIs.
Here are some reasons for consider using Controller Advice:

1. Centralized Error Handling: With Controller Advice, you can define a single location to handle all error conditions in your application. This makes it easier to manage error handling and ensures a consistent response format across your entire API.
2. Exception Handling: Controller Advice allows you to define how to handle exceptions that may occur during the processing of a request. This can help prevent crashes or unexpected behavior in your application, improving the user experience.
3. Custom Error Responses: By using Controller Advice, you can customize error responses for different HTTP status codes or exception types. This allows you to provide more informative and helpful error messages to your users.
4. Reusability: Controller Advice can be used across multiple controllers or endpoints, making it easy to reuse error handling logic and avoid duplicating code.
5. Improved Maintainability: Using Controller Advice can help improve the maintainability of your application by separating error handling logic from your controller code. This makes it easier to modify or extend your error handling logic as your application evolves.

### Add Validation in Controller

Adding validation on RESTful API controller has several benefits:

1. Data Integrity: Validation helps ensure that the data being passed to the API is valid, accurate and complete. This can help prevent errors and inconsistencies in your data.
2. Improved User Experience: Validation can help provide a better user experience by providing immediate feedback to the user if the input is invalid. This can help users correct their input and avoid confusion and frustration.
3. Security: Validation can help prevent security vulnerabilities, such as SQL injection and Cross-site scripting (XSS), by ensuring that the input data is properly formatted and does not contain malicious code.
4. Compliance: Many industries and organizations have regulations and standards that require data validation, such as HIPAA in the healthcare industry and PCI DSS in the payment card industry. Adding validation to your RESTful API can help ensure compliance with these regulations.
5. Reduced Errors: Validation can help reduce errors and improve the reliability of your API. By validating data before processing it, you can avoid errors and exceptions that could lead to downtime or other issues.

### Add Cache

Adding a cache to application has several benefits:

1. Improved Performance: Caching can significantly improve application performance by reducing the time it takes to retrieve and process data. When data is stored in the cache, subsequent requests can be served from the cache instead of having to retrieve the data from the database or other sources.
2. Reduced Latency: By reducing the time it takes to retrieve data, caching can also reduce latency. This can lead to a better user experience, particularly in applications that require real-time data.
3. Scalability: Caching can help improve application scalability by reducing the load on the database or other data sources. This can help ensure that your application can handle large volumes of traffic and data.
4. Improved Availability: Caching can help improve application availability by providing a backup source of data in case the primary data source is unavailable. This can help ensure that your application remains available and responsive to users.

### Add DTO

Using DTOs (Data Transfer Objects) instead of Entities in application has several benefits:

1. Security: By using DTOs, you can avoid exposing sensitive data that may be present in your Entities to external systems or even to other parts of your application. This can help reduce the risk of data breaches and unauthorized access to your data.
2. Flexibility: DTOs allow you to decouple your database schema from the API contract, providing more flexibility to change the data model as needed. You can also customize DTOs to better fit the needs of the API consumers, without affecting the underlying data model.
3. Performance: DTOs can help reduce the amount of data that needs to be transferred between the client and the server, which can improve performance. By only transferring the data that is needed, you can reduce the network latency and improve the overall responsiveness of your application.
4. Maintainability: DTOs can help improve the maintainability of your code by providing a clear separation of concerns. You can use DTOs to encapsulate data and logic related to specific use cases, making it easier to manage and maintain your code over time.
5. Versioning: By using DTOs, you can provide versioning for your API contract. This can help you avoid breaking changes to your API, as you can introduce new versions of DTOs without affecting the underlying data model.

### Add pagination

Using pagination instead of returning all records has several benefits:

1. Improved Performance: By limiting the number of records returned in a single query, pagination can significantly improve the performance of your API. This can help reduce the load on your server, and improve the overall responsiveness of your application.
2. Reduced Network Latency: By reducing the amount of data that needs to be transferred between the client and the server, pagination can help reduce network latency and improve the overall speed of your API.
3. Improved User Experience: Pagination can help improve the user experience of your API by providing a more manageable and organized way of presenting large datasets. Users can easily navigate through different pages of data and find what they are looking for more easily.
4. Reduced Memory Usage: By limiting the number of records returned in a single query, pagination can help reduce the amount of memory needed to process the results. This can help improve the stability and reliability of your application.
5. Scalability: Pagination can help improve the scalability of your API by allowing you to efficiently process large datasets without overloading your server or database.

### Add javadoc

Adding Javadoc has several benefits:

1. Code Documentation: Javadoc provides a way to document your code and communicate its functionality to other developers. By adding Javadoc to your code, you can provide detailed explanations of your code's behavior, making it easier for other developers to understand and use your code.
2. Improved Readability: Javadoc provides a structured and standardized way of documenting your code, making it easier to read and understand. This can be especially helpful for complex or large codebases.
3. Automated Documentation: Javadoc can be used to generate automated documentation for your code. This makes it easier for other developers to understand how to use your code and reduces the amount of time spent on manual documentation.
4. IDE Integration: Many IDEs, such as Eclipse and IntelliJ, support Javadoc, providing integrated documentation for your code. This makes it easier for developers to understand how to use your code without having to refer to external documentation.
5. Better Collaboration: By adding Javadoc to your code, you can improve collaboration with other developers. Javadoc provides a standardized way of documenting code, making it easier for developers to work together and understand each other's code.

### Migrate JUnit5

JUnit 5 is the latest version of the JUnit testing framework and offers several advantages over its predecessor, JUnit 4.
Here are some reasons for consider using JUnit 5:

1. Improved architecture: JUnit 5 has a more modular architecture, which allows developers to use only the modules they need, reducing the overall size of the library and improving performance.
2. Improved extension model: JUnit 5 has a more flexible extension model that allows developers to extend the framework and add new functionality more easily.
3. Improved support for parameterized tests: JUnit 5 provides better support for parameterized tests, making it easier to test your code with multiple input values.
4. Better support for Java 8 features: JUnit 5 takes advantage of Java 8 features, such as lambdas and streams, making it easier to write test code and reduce boilerplate.
5. Improved assertions: JUnit 5 provides a new set of assertion methods, making it easier to write more expressive and readable test code.

### Using Mock in Testing

Mocks are useful in testing because they allow to isolate and test individual units of code in application, without relying on external dependencies or services.
Here are some reasons for consider using Mock:

1. Speed: Using mocks allows you to run tests more quickly, as you don't have to set up and tear down complex external services or dependencies.
2. Isolation: By using mocks, you can isolate the code you are testing and focus on testing that specific code in a controlled environment. This makes it easier to identify and debug issues in your code.
3. Reproducibility: Because mocks provide a consistent and predictable environment for testing, you can reproduce test results reliably, making it easier to identify and fix bugs.
4. Independence: Using mocks allows you to test code independently of other parts of your application, making it easier to test and debug individual units of code.
5. Scalability: As your application grows and becomes more complex, using mocks can help you manage and maintain the complexity of your testing process.

### Adding Unit Test and Integration Test

Adding unit tests and integration tests to code has several benefits:

1. Improved Code Quality: Unit tests and integration tests can help you identify and fix bugs and errors in your code. They can also help you identify code that is difficult to maintain, making it easier to improve the overall quality of your code.
2. Early Detection of Issues: By catching errors early in the development process, unit tests and integration tests can save you time and effort in the long run. They can help you catch issues before they become more difficult and expensive to fix.
3. Increased Confidence: Writing tests gives you confidence in your code. You can test your code in various scenarios and ensure that it works as expected. This can give you the confidence to make changes to your code without fear of breaking existing functionality.
4. Easier Refactoring: Refactoring code can be a time-consuming and error-prone process. By writing tests, you can ensure that your refactored code works as expected and does not introduce new bugs or errors.
5. Collaboration: Tests can help improve collaboration between team members. They provide a clear definition of expected behavior, making it easier for team members to work together and understand each other's code.

### OpenApi Documentation

OpenAPI documentation is a machine-readable specification for describing RESTful web APIs. It provides a standardized way to describe the endpoints, input and output parameters, responses, authentication, and other details of an API.
Here are some benefits of adding OpenAPI documentation:

1. Improved documentation: OpenAPI documentation provides a standardized way to document your API, making it easier for developers to understand how to interact with your API and reducing the learning curve.
2. Better communication: With OpenAPI documentation, both developers and non-technical stakeholders can quickly understand the endpoints, input and output parameters, and responses of the API. This makes communication about the API more effective and efficient.
3. Increased interoperability: OpenAPI documentation follows a standard format, making it easier to integrate with other systems that also use OpenAPI, as they can automatically generate client code and documentation from the OpenAPI specification.
4. Better testing and validation: OpenAPI documentation can be used to generate automated tests for your API, ensuring that it conforms to the documented contract and preventing regression issues. Additionally, OpenAPI documentation can be used to validate incoming requests to ensure they adhere to the documented contract.
5. Improved development experience: With OpenAPI documentation, developers can quickly see the API endpoints, input and output parameters, and responses, making it easier to develop and test against the API.

### Github Action

GitHub Actions is a continuous integration/continuous deployment (CI/CD) platform that automates your software development workflows right within your GitHub repository.
Here are some reasons for consider using Github Action:

1. Automation: GitHub Actions provides a way to automate repetitive tasks and workflows, such as building and testing your code, deploying it to different environments, or running code analysis tools.
2. Integration: GitHub Actions integrates seamlessly with the rest of the GitHub ecosystem, including pull requests, issues, and other collaboration tools. This allows you to automate your workflows without leaving the GitHub platform.
3. Customization: GitHub Actions is highly customizable and allows you to create your own custom workflows and actions to fit your specific needs. This means you can build a workflow that is tailored to your team's unique development process.
4. Visibility: GitHub Actions provides detailed logs and status updates for each workflow run, allowing you to easily track the progress of your builds and deployments. This helps you quickly identify issues and resolve them before they become a problem.

### Migrate to JaCoCo

JaCoCo is a code coverage tool for Java applications, and it provides detailed information about how much of your code is being executed during testing.
Here are some reasons of mirage to JaCoCo:

1. Line coverage: The percentage of code lines that have been executed during testing.
2. Branch coverage: The percentage of code branches that have been executed during testing.
3. Instruction coverage: The percentage of code instructions that have been executed during testing.
4. Complexity coverage: The percentage of code blocks that have been executed during testing.
5. Method coverage: The percentage of methods that have been executed during testing.
6. Class coverage: The percentage of classes that have been executed during testing.

### Migrate to spotless

Spotless is a code formatting and linting tool for Java and other JVM-based languages that can help to enforce consistent coding styles and improve the overall quality of your code.
Here are some reasons for consider using Spotless:

1. Consistency: With Spotless, you can enforce a consistent code formatting style across your entire codebase, which can make it easier to read and maintain.
2. Automation: Spotless can be integrated into your build process, so you can automatically format your code before it is committed or deployed. This can save time and reduce the likelihood of formatting errors.
3. Customization: Spotless is highly configurable, so you can customize the formatting rules to fit your specific project needs.
4. Linting: In addition to formatting, Spotless also supports linting rules for identifying potential code issues or errors.
5. Integration: Spotless integrates with popular build tools like Gradle and Maven, making it easy to incorporate into your existing workflow.

### Migrate to gradle

Both Gradle and Maven are popular build tools for Java applications.
Here are some reasons for migrate to gradle:

1. Flexibility: Gradle offers more flexibility than Maven, allowing you to customize your build process to fit your specific needs. Gradle also allows you to use a wide range of programming languages and tools, making it a more versatile build tool.
2. Performance: Gradle is generally faster than Maven when it comes to building large and complex projects. This is because Gradle builds are incremental, meaning that only the necessary parts of the project are built, whereas Maven builds are always done from scratch.
3. Configuration: Gradle uses a Groovy-based configuration language, which many developers find more expressive and easier to read and write than Maven's XML-based configuration.
4. Integration with IDEs: Gradle integrates well with popular IDEs like IntelliJ and Eclipse, making it easier to develop and test your code.
5. Dependency management: Gradle's dependency management system is more flexible than Maven's, allowing you to define dependencies based on different configurations and scopes.

## What I will do if I have time

After received the test, I only have about 1-2 days to work for it due to my vacation schedule.
For the tasks I will do if I have time, please check [TODO](#TODO)

## TODO

- [x] logger
- [x] controller error handling
- [x] caching for query
- [x] DTO
- [x] pojo validation
- [x] Pagination
- [x] unit test
- [x] integration test
- [x] security module
- [x] java doc improvement
- [x] swagger doc improvement
- [x] CI things, gitHub action, java lint etc.
- [ ] encrypt authentication data in config file
- [ ] profile based config
- [ ] role / scope based authentication

## Your experience in Java

As a 7-year experienced backend developer in Java,
- I have developed and maintained high-performance and scalable web applications using frameworks such as Spring Boot and Kafka.
- I have also worked with various databases, implemented RESTful APIs, integrated with third-party services, and implemented security features such as OAuth2 and JWT.
- I have experience working in agile development environments, leading small development teams, and mentoring junior developers.
