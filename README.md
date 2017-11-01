# shorturl

## Build

To compile, run all tests and generate a artifact run
```
    gradle clean build
```

A gradle wrapper is provided with the code so one could also run:
```
    ./gradlew clean build
```

An artifact is going to be generated at `build/libs/shorturl-0.0.1-SNAPSHOT.jar` and you can run the application
by calling it from the root directory of the project:
```
    build/libs/shorturl-0.0.1-SNAPSHOT.jar
```

This is going to start the embedded web server and make available the endpoints provided by the application, which are:
- POST /: accepts a url form parameter and creates a short url pointing to the provided original url;

Example:
```
curl -d url=https://example.com -i localhost:8080/
HTTP/1.1 201
Location: http://localhost:8080/Xqjkfs
Content-Length: 0
Date: Wed, 01 Nov 2017 12:22:09 GMT
```

- GET /{id}: redirect to the corresponding external url defined by the short url;

Example:
```
curl -i http://localhost:8080/Xqjkfs
HTTP/1.1 303
Location: https://example.com
Content-Length: 0
Date: Wed, 01 Nov 2017 12:22:44 GMT
```
