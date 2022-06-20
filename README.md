# recipe-management-system

repayments
Spring Boot Project for Recipe Management System

Business Requirements :

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

Project Specifications :
This is a Spring Boot application and we have implemented a web service that has one endpoint to generate a borrower plan via HTTP in JSON.

Executing the project :
To execute, please download the zip of the project or clone the repository.

Import the unziped file or cloned repo into IDE of your choice.

Run mvn clean install to build and resolve dependencies needed for the application.

After build is successful, you can use above endpoints with respective authorization credentials and request payload on Postman.

Port : By default application runs on port 8080 . Kindly make sure the port is available.

HTTP Method : POST

Media Type : application/json

Security : Currently no security is configured , so no need for any HTTP Basic or Transport layer security .

#Endpoints:

