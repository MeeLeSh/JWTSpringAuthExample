# JWTSpringAuthExample

* required auth endpoint: /api/users to get access endpoint need set in Headers Authorization : access_token
* login endpoint: /auth/login with body x-www-form-urlencoded username and password, response: access and refresh_token
* refresh token endpoint: /api/token/refresh with Headers Authorization : refresh_token
