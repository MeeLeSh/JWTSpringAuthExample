# JWTSpringAuthExample

* required auth endpoint: __/api/users__ to get access endpoint need set in Headers:  "Authorization" : "Bearer {my_access_token}"
* login endpoint: __/auth/login__ with body x-www-form-urlencoded username and password, response: access and refresh tokens
* refresh token endpoint: __/api/token/refresh__ with Headers Authorization : refresh_token
