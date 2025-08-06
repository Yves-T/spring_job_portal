# Job Portal Integration Tests

This document describes the integration tests implemented for the Job Portal application.

## Overview

Integration tests verify that different components of the application work together correctly. These tests are more comprehensive than unit tests and closer to how the application will be used in production.

## Test Structure

The integration tests are organized by layer:

1. **Repository Tests**: Test the data access layer and database interactions
2. **Service Tests**: Test the business logic layer
3. **Controller Tests**: Test the web layer (not implemented yet due to security dependencies)

## Test Classes

### Repository Tests

- **JobPostActivityRepositoryIntegrationTest**: Tests CRUD operations and search functionality for job posts
  - Tests saving and retrieving job posts
  - Tests searching for jobs with various criteria

### Service Tests

- **JobPostActivityServiceIntegrationTest**: Tests the job post service layer
  - Tests adding new job posts
  - Tests retrieving job posts by ID
  - Tests retrieving all job posts
  - Tests searching for job posts

- **UsersTypeServiceIntegrationTest**: Tests the user type service layer
  - Tests retrieving all user types

## Test Configuration

- Tests use an H2 in-memory database for testing instead of the production database
- Test configuration is specified in `src/test/resources/application-test.properties`
- Tests are run with the `@ActiveProfiles("test")` annotation to use the test configuration

## Running the Tests

To run all tests:
```bash
mvn test
```

To run a specific test class:
```bash
mvn test -Dtest=JobPostActivityRepositoryIntegrationTest
```

## Future Improvements

1. Add controller integration tests with proper security configuration
2. Add more comprehensive test cases for edge conditions
3. Add integration tests for remaining services (UsersService, JobSeekerApplyService, etc.)
4. Improve search test cases to handle case sensitivity and other search parameters