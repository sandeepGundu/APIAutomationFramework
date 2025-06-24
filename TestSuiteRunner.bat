::mvn -Dallure.results.directory=target/allure-results clean test
mvn clean test -Dcucumber.filter.tags="@smoke"
mvn allure:report