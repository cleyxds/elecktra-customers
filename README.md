
# Customers

A custom REST api for manage customers data 


![Apache License](https://img.shields.io/github/license/cleyxds/elecktra-customers)
![Last Commit](https://img.shields.io/github/last-commit/cleyxds/elecktra-customers)
![](https://img.shields.io/github/languages/top/cleyxds/elecktra-customers?color=%23B07219)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-Profile-%230A66C2)](https://www.linkedin.com/in/cleyson-barbosa-7b4245162)

## Features

- Multi-user
- Stateless Authentication
- Cloud-native ready
- Microservice architecture

## Appendix

Docker image automatically creates the folders for the database and images on the root folder 

## API Reference

### Customers

#### Create customer

```http
  POST /customers
```

| Body       | Type     | Description  |
| :--------- | :------- | :----------- |
| `name`     | `string` | **Required** |
| `username` | `string` | **Required** |
| `email`    | `string` | **Required** |
| `password` | `string` | **Required** |

#### List customers

```http
  GET /customers
```

| Parameter       | Type     | Description                  |
| :-------------- | :------- | :--------------------------- |
| `Authorization` | `string` | **Required** Valid JWT Token |

#### Get customer

```http
  GET /customers/${id}
```

| Parameter       | Type     | Description                  |
| :-------------- | :------- | :--------------------------- |
| `id`            | `string` | **Required** Customer id     |
| `Authorization` | `string` | **Required** Valid JWT Token |

#### Update customer

```http
  PUT /customers/${id}
```

| Parameter       | Type     | Description                  |
| :-------------- | :------- | :--------------------------- |
| `id`            | `string` | **Required** Customer id     |
| `Authorization` | `string` | **Required** Valid JWT Token |

| Body       | Type     | Description  |
| :--------  | :------- | :----------- |
| `username` | `string` | **Required** |
| `email`    | `string` | **Required** |

#### Delete customer

```http
  DELETE /customers/${id}
```

| Parameter       | Type     | Description                  |
| :-------------- | :------- | :--------------------------- |
| `id`            | `string` | **Required** Customer id     |
| `Authorization` | `string` | **Required** Valid JWT Token |

### Images

#### Create customer image

```http
  POST /images
```

| Parameter       | Type     | Description                  |
| :-------------- | :------- | :--------------------------- |
| `id`            | `string` | **Required** Customer id     |

| Multipart  | Type     | Description  |
| :--------- | :------- | :----------- |
| `file`     | `File`   | **Required** |

#### List customers images

```http
  GET /images
```

| Parameter       | Type     | Description                  |
| :-------------- | :------- | :--------------------------- |
| `Authorization` | `string` | **Required** Valid JWT Token |

#### Get customer image

```http
  GET /images/customers/${id}
```

| Parameter       | Type     | Description                  |
| :-------------- | :------- | :--------------------------- |
| `id`            | `string` | **Required** Customer id     |
| `Authorization` | `string` | **Required** Valid JWT Token |
## Environment Variables

To run this project, you will need to add the following environment variables to your .env file

`APP_PROFILE` *dev:prod*

`SECRET_KEY` *secret jwt key*


## Tech Stack

**Server:** Java, Spring Boot


## Authors

- [@cleyxds](https://www.github.com/cleyxds)
