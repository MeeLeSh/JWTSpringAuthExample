# JWTSpringAuthExample

required auth endpoint: http://localhost:8080/api/users to get access endpoint need set in Headers Authorization : access_token 
login endpoint: /auth/login with body x-www-form-urlencoded username and password, response: access and refresh_tokens
refresh token endpoint: /api/token/refresh with Headers Authorization : refresh_token
