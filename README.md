User Management for HelthCare system

## User Service

### Login

`POST` http://localhost:8080/user-service/users/login

**Headers**: `{Content-Type: application/json, role_id: 1}`

Roles are: `1` - admin, `2` - patient, `3` - doctor

**Body**:

```{
    "email": "manisha@gmail.com",
    "password": "abc123"
}
```

<h4>Response </h4>:

**Success (200)**:

```
{
    "timestamp": "2020-04-19T06:45:06.219Z[UTC]",
    "token": "967b13c3-72c0-4b34-bd47-c50d7eab99ea.1587278706102",
    "user": {
        "createdAt": "2020-04-14Z",
        "email": "sanuri@gmail.com",
        "role": {
            "name": "doctor",
            "roleId": 3
        },
        "updatedAt": "2020-04-18Z",
        "userId": 4,
        "username": "Sanuri"
    }
}
```

**Failed - `Server error 500`**:

```
{
    "documentation": "https://github.com/Blasanka/appointment-service/tree/master/README.md",
    "errorCode": 500
}
```

**Failed - `Authentication Failed (401 Unauthorized)`**:

```
[
    "Invalid email address",
    "Password cannot be less than six characters"
]
```

*Please note that token generation is not completed according to proper mechanism (not valid JWT) for the time being and there is no token expiration, Just to complete generated unique id and stored it as token for all request validation*

### Get all users

`GET` http://localhost:8080/user-service/users

Headers: `{Content-Type: application/json, x-authorization: 5f26b9d8-651e-4bbe-805f-0850edfab84d.1587272695700}`


Output:

```
[
	{
        "createdAt": "2020-04-23Z",
        "email": "bla@gmail.com",
        "role": {
            "name": "guest",
            "roleId": 4
        },
        "updatedAt": "2020-04-18Z",
        "userId": 1,
        "username": "bl"
    },
]
```

### Get a user

`GET` http://localhost:8080/user-service/users/1

Headers: `{"Content-Type": "application/json", "x-authorization": "5f26b9d8-651e-4bbe-805f-0850edfab84d.1587272695700"}`

### Add new users

Endpoint:
`POST` <a href="http://localhost:8080/user-service/users/">
http://localhost:8080/user-service/users/</a>

Headers:
`{"Content-Type": "application/json", "x-authorization": "5f26b9d8-651e-4bbe-805f-0850edfab84d.1587272695700", "role_id": 2}`

Body:

```
{
    "patientId": 1,
    "doctorId": 1,
    "hospitalId": 1,
    "userId": 5,
    "date": "2010-05-11",
    "time": "2012-05-21"
}
```

Output:

**Success** `201 Created`
**Failed** `400 Bad Request`,  `500 Internal Server Error`


### Update users

Endpoint:
`PUT` <a href="http://localhost:8080/user-service/users/38">
http://localhost:8080/user-service/users/38</a>

Headers:
`{"Content-Type": "application/json", "x-authorization": "5f26b9d8-651e-4bbe-805f-0850edfab84d.1587272695700"}`

Body:

```
{
    "doctorId": 1,
    "hospitalId": 1,
    "date": "2010-05-11",
    "time": "2012-05-21"
}
```

Output:

**Success** `204 No Content`
**Failed** `401 Unauthorized`, `400 Bad Request`,  `500 Internal Server Error`


### Delete users

Endpoint:
`DELETE` <a href="http://localhost:8080/user-service/users/2">
http://localhost:8080/user-service/users/2</a>

Headers:
`{"Content-Type": "application/json", "x-authorization": "5f26b9d8-651e-4bbe-805f-0850edfab84d.1587272695700"}`

Output:


**Success** `204 No Content`
**Failed** `401 Unauthorized`, `400 Bad Request`,  `500 Internal Server Error`
