# recipe-management-system

# Spring Boot Project for Recipe Management System

## Business Requirements :

Create a microservice that allows users to manage their favorite recipes. It should allow adding,
updating, removing, and fetching recipes. Additionally, users should be able to filter available recipes
based on one or more of the following criteria:

1. Whether or not the dish is vegetarian
2. The number of servings
3. Specific ingredients (either includes or excludes)
4. Search within the instructions.

For example, the API should be able to handle the following search requests:
• All vegetarian recipes
• Recipes that can serve 4 persons and have “potatoes” as an ingredient
• Recipes without “salmon” as an ingredient that has “oven” in the instructions.

## Project Specifications :
This is a Spring Boot application and we have implemented a web service that has endpoints to perform CRUD operations on recipes and also have advanced search capabilities.

### Note: For the advanced search Querydsl or @Query annotation with native SQL queries can be also used. We are currently leveraging Specifications along with Spring Data JpaSpecificationExecutor for our criteria based search.

# System Design:
Recipe Management Service is microservice based on layered architecture and is a RESTful Web Service. This service can be deployed independently on premise or on cloud and can also be containerized to execute as docker containers. There are 4 layers from top to bottom:

## API Layer:
Top layer, which is main interface available for intgeration and interaction with front-end or end user to consume APIs

## Service Layer
This layer sits in between API layer and Data access layer with some utility functionality and business logic.
It's mainly responsible for interacting with Data Access Layer and transferring the recipes data as required by top and below layers.
It's just another module added to decouple business logic of recipes data transfer and mapping from/to API layer.
Further, service layer can be enhanced to support advanved features like Caching, Interacting with external Authorization Service etc. (Future scope)

## Data Access Layer:
Responsible to provide Object Relationship Mapping (ORM) between higher level recipe Java objects and persistence layer tables.
Springboot-starter-data-JPA module is used to implement mappings between objects and tables
This layer contains recipe entity classes and JPA repositories which implement lower level functionality of storing/retrieving recipes data.

## Persistence Layer:
This layer is responsible for physically storing the recipes data onto database table, we use in memory H2 database.


# Executing the project :

To execute, please download the zip of the project or clone the repository.

Import the unziped file or cloned repo into IDE of your choice.

Run mvn clean install to build and resolve dependencies needed for the application.

After build is successful, you can use above endpoints with respective authorization credentials and request payload on Postman.

Port : By default application runs on port 8080 . Kindly make sure the port is available.

Security : Currently no security is configured , so no need for any HTTP Basic or Transport layer security.

Application is using in memory H2 database. The connection details are : 
- `http://localhost:8882`
- spring.datasource.url=jdbc:h2:file:./data/demo
- spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
- spring.datasource.driverClassName = org.h2.Driver
- spring.datasource.platform=h2
- spring.datasource.username = sa
- spring.datasource.password =

# Endpoints:


- Get All Recipes
	- Get all recipe service is used to get list of all the recipes. Following is the api url. It is GET request. 

```
		GET 
		'Accept: application/json' 
		'/api/v1/recipe/'

```

- Find Recipe By Id
	- Find the recipe by id can be used to fetch particular Recipe. Following is the api url. It is GET request which accept ID as path parameter.
	
```	
		GET 
		'Accept: application/json' 
		'/api/v1/recipe/{id}'

```
	
- Delete Recipe By Id
	- Delete the recipe by id can be used to delete particular Recipe. Following is the api url. It is DELETE request which accept ID as path parameter.
	
```	
		GET 
		'Accept: application/json' 
		'/api/v1/recipe/{id}'

```
		
- Search Recipe By Criteria
	- This method searches for the recipe that matches the given search criteria. User can search by any field of the Recipe. Following are the fields of the recipe.
		- name
		- lastUpdated
		- search
		- instructions
		- isvegetarian
		
	- Search criteria needs field to search for with operator to apply on field. 
		- isVegetarian:true means all the recipes which are vegetarian.
		- serves>5 fetches the recipes which can be served to more than 5 person.
```
	GET 
	'Accept: application/json' 
	'/api/v1/recipe/searchRecipeByCriteria/isVegetarian%3Atrue'
```


