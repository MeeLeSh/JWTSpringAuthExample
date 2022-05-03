# JWTSpringAuthExample

* required auth endpoint: __/api/users__ to get access endpoint need set in Headers Authorization : access_token
* login endpoint: __/auth/login__ with body x-www-form-urlencoded username and password, response: access and refresh_token
* refresh token endpoint: __/api/token/refresh__ with Headers Authorization : refresh_token
