# MatchGuessr
MatchGuessr is a web app that allows users to guess the results of upcoming football matches (more sports in the future potentially). This project just serves as a fun way for people to bet on matches without actually spending any money.

## Contributing
Help is gladly appreciated, just follow these steps to contribute:
1. Go to the dev branch and fork it
3. Clone the project on your own machine
4. Commit and push changes to your own branch
5. Submit a pull request to the dev branch and wait for review

*make sure to merge the latest upstream before the pull request*

### Contributing to the backend:
Making changes to the backend is a complicated process and requires setting up your own postgresql database and mocking your own data

Data is pulled from https://www.football-data.org/ so I recommend you to make a free account

When setting up the database to connect to your postgresql databse please set up your application.properties:
```
spring.datasource.url=jdbc:postgresql://<database-url>
spring.datasource.username=<username>
spring.datasource.password=<password>
spring.jpa.hibernate.ddl-auto=create-drop (can change if you want)
api.key=<football-data.org api key>
jwt.secret=<secret>
```
